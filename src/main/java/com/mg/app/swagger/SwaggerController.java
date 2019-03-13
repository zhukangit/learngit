package com.mg.app.swagger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.app.eop.ReqDTO;
import com.mg.app.swagger.service.SwaggerService;
import com.mg.app.util.HTTPHelper;
import com.mg.app.util.ObjUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class SwaggerController {
    @Autowired
    SwaggerService swaggerService;
    // jackson转换工具
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @RequestMapping("/getSwagger")
    public void getFiles(@RequestBody ReqDTO dto, HttpServletResponse res) throws Exception {
        if (null != dto) {
//            ClassPathResource classPathResource = new ClassPathResource("download");

//            String commonPath = classPathResource.getURL().getPath() + "/";
            String path = "";
            String directoryPath = ObjUtil.getRandom("swagger");
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            path = directory.getPath() + "\\";
            String data = HTTPHelper.callRs("", dto);
            Map root = objectMapper.readValue(data, Map.class);
            List<Map> tags = (List) root.get("tags");

            for (Map tag : tags) {
                String tagName = tag.get("name").toString();
                String yaml = swaggerService.slipt(root, tagName);

//                            System.out.println(classPathResource.getPath());
                File file = new File(path + tagName + ".txt");
                if (!file.exists()) {
                    file.createNewFile();//如果文件不存在，就创建该文件
                }
                Files.write(Paths.get(file.getPath()), yaml.getBytes());
            }
            String zipName = "swaggeryaml.zip";
            ObjUtil.generateZip(zipName, path);


            InputStream inputStream = null;
            ServletOutputStream out = null;

            inputStream = new FileInputStream(path + zipName);
            res.setContentType("multipart/form-data");
            res.setHeader("Content-Disposition", "attachment;filename="
                    + zipName);
            res.setCharacterEncoding("utf-8");// 此句非常关键,不然excel文档全是乱码
            out = res.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中
            while ((bytesToRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
            out.flush();
            out.close();
            inputStream.close();
            ObjUtil.deleteDir(path);
        }
    }


    public static void main(String[] args) {
        try {
            OutputStream os = new FileOutputStream("C:\\workshop\\mt\\target\\classes\\a.zip");
//            List<File> files = new ArrayList<>();
            String filepath = "C:\\workshop\\mt\\target\\classes\\download";

            File file = new File(filepath);
            File[] files = file.listFiles();
        //    generateZip(os, files);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
