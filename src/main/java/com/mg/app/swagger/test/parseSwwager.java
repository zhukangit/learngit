package com.mg.app.swagger.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.app.swagger.service.SwaggerService;
import com.mg.app.util.ObjUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/1/21 10:36
 * @功能说明： 功能描述
 */
public class parseSwwager {

    public static void main(String[] args) {
        fileUtils fu = new fileUtils();
        //
        String data = fu.readToString("E:\\kehuyinqin-xiangmu2\\mt\\资产能力.txt");
       // String data = fu.readToString("C:\\workshop\\mt\\account-controller.txt");
        //System.out.print(s);
         final ObjectMapper objectMapper = new ObjectMapper();

        Map root = null;
        try {
            root = objectMapper.readValue(data, Map.class);
            SwaggerService swaggerService = new SwaggerService();
            List<Map> tags = (List) root.get("tags");
            String path = "";
            String directoryPath = ObjUtil.getRandom("swagger");
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            path = directory.getPath() + "\\";
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
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
