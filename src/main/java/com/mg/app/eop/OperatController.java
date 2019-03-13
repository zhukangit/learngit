package com.mg.app.eop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mg.app.eop.entity.PropertiesDTO;
import com.mg.app.eop.util.DataHelper;
import com.mg.app.util.HTTPHelper;
import com.mg.app.util.ObjUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class OperatController {
    private static final String host = "http://180.166.2.4:7777/";
//    private static Map list = new HashMap();
    @Autowired
    Configuration cfg;
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);

    /**
     * 文件下载入口
     *
     * @param dto
     * @param res
     * @throws Exception
     */
    @RequestMapping("/getFile")
    public void getFile(@RequestBody ReqDTO dto, HttpServletResponse res) throws Exception {
        Map list = new HashMap();
        if (null != dto) {
            String data = HTTPHelper.callRs("", dto);

            StringBuilder ssheet = new StringBuilder();
            String modulesName = "";
            //获取modules对象 只有一个
            JSONArray jm = (JSONArray) JSONObject.parseObject(data).getJSONObject("data").get("modules");
//            ClassPathResource classPathResource = new ClassPathResource("download");
//            String commonPath = classPathResource.getURL().getPath() + "/";
            String path = "";
            String directoryPath = ObjUtil.getRandom("rap");
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            path = directory.getPath() + "\\";

            for (Object obj : jm) {
//                fixedThreadPool.submit(new Runnable() {
//                    @Override
//                    public void run() {
                        try {
                            JSONObject jbo = (JSONObject) obj;
                            String mName = jbo.getString("name");

                            String content = generateSheet(jbo, dto.getGenerateUrl(), list);

//                            System.out.println(classPathResource.getPath());
                            File file = new File(path + mName + ".xml");
                            if (!file.exists()) {
                                file.createNewFile();//如果文件不存在，就创建该文件
                            }
                            Files.write(Paths.get(file.getPath()), content.getBytes());

//                            StringBuffer stringBuffer = new StringBuffer();
//                            Files.lines(Paths.get(file), StandardCharsets.UTF_8).forEach(stringBuffer::append);

//
//                            File file = generateSheet(jbo);
//                            InputStream inputStream = null;
//                            ServletOutputStream out = null;
//
//                            inputStream = new FileInputStream(file);
//                            res.setContentType("text/xml");
//                            res.setHeader("Content-Disposition", "attachment;filename="
//                                    + file.getName());
//                            res.setCharacterEncoding("utf-8");// 此句非常关键,不然excel文档全是乱码
//                            out = res.getOutputStream();
//                            byte[] buffer = new byte[512]; // 缓冲区
//                            int bytesToRead = -1;
//                            // 通过循环将读入的Excel文件的内容输出到浏览器中
//                            while ((bytesToRead = inputStream.read(buffer)) != -1) {
//                                out.write(buffer, 0, bytesToRead);
//                            }
//                            out.flush();
//                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e);
                        }
                    }
            File file = new File(path + "list.xml");

            Map lists = new HashMap();
            lists.put("lists", list);
            String listContent = render("/list.xml",lists,cfg);
            Files.write(Paths.get(file.getPath()), listContent.getBytes());
            String zipName = "eop.zip";
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
//                });
//            }
        }
    }

//    /**
//     * 请求外部服务（请求rap2 服务）
//     *
//     * @param dto
//     * @return
//     */
//    public String callRs(ReqDTO dto) {
//        String result = "";
//        try {
//            if (null != dto) {
//                CloseableHttpClient httpClient = HttpClients.createDefault();
//                HttpClientContext context = HttpClientContext.create();
//                HttpRequestBase httpBase = new HttpGet(host + dto.getPath());
//                httpBase.setHeader("Cookie", dto.getCookie());
////            httpPost.addHeader("Content-Type", "application/json");
//                httpBase.addHeader("Content-type", "application/json; charset=utf-8");
//
//                httpBase.setHeader("Accept", "application/json");
//                CloseableHttpResponse response = null;
//                switch (dto.getMethod()) {
//                    case "POST":
//                        HttpPost httpPost = (HttpPost) httpBase;
//                        httpPost.setEntity(new StringEntity(JSON.toJSONString(dto.getVal()), Charset.forName("UTF-8")));
//                        response = httpClient.execute(httpBase);
//                        break;
//                    case "GET":
//                        HttpGet httpGet = (HttpGet) httpBase;//new HttpGet(host + dto.getPath());
//                        response = httpClient.execute(httpGet);
//                        break;
//                }
//                HttpEntity entity = response.getEntity();
//                String responseContent = EntityUtils.toString(entity, "UTF-8");
//
//                response.close();
//                httpClient.close();
//                result = responseContent;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return result;
//    }

    /**
     * 拼装worksheet
     *
     * @param jbo
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    private String generateSheet(JSONObject jbo, String generateUrl, Map list) throws IOException, TemplateException {
        Map rootDoc = new HashMap();
        StringBuilder ssheet = new StringBuilder();
        String modulesName = jbo.getString("name");
        String modulesDescription = jbo.getString("description");
        //获取所有接口列表
        JSONArray ji = (JSONArray) jbo.get("interfaces");
        for (Object obj : ji) {
            JSONObject jbp = (JSONObject) obj;
            Map datas = new HashMap();
            //接口描述
            rootDoc.put("description", jbp.getString("description"));
//            //接口名称
//            datas.put("title", modulesName);
            //接口中文名称
            rootDoc.put("title", jbp.getString("name"));
            //接口地址
            datas.put("url", jbp.getString("url"));
            //请求方式
            datas.put("method", jbp.getString("method"));
            //获取该接口下所有属性
            JSONArray jp = (JSONArray) jbp.get("properties");
            list.put(jbp.getString("name"), generateUrl + jbp.getString("url"));
            List<PropertiesDTO> reqs = new ArrayList<>();
            List<PropertiesDTO> ress = new ArrayList<>();
            datas.put("reqList", reqs);
            datas.put("resList", ress);
            List<PropertiesDTO> reqFullData = new ArrayList<>();
            List<PropertiesDTO> resFullData = new ArrayList<>();
            for (Object jbvs : jp) {
                PropertiesDTO proper = JSON.parseObject(jbvs.toString(), PropertiesDTO.class);
                JSONObject jbv = (JSONObject) jbvs;
//                if (proper.getName().equals("_root_")) {
//                }
                switch (proper.getScope()) {
                    case "request":
                        if (!proper.getName().equals("_root_")) {
                            reqs.add(proper);
                        }
                        reqFullData.add(proper);
                        break;
                    case "response":
                        if (!proper.getName().equals("_root_")) {
                            ress.add(proper);
                        }
                        resFullData.add(proper);
                        break;
                }
            }

            //TODO 递归
            String resDemo = DataHelper.generator(resFullData, -1 ,false);
            String reqDemo = "";//DataHelper.generator(new ArrayList<>(), reqs);
            switch (jbp.getString("method")){
                case "POST":
                    reqDemo = DataHelper.generator(reqFullData, -1 ,false);
                    break;
                case "GET":
                    reqDemo = jbp.getString("url") + DataHelper.generatorGet(reqs);
                    break;
                case "PATCH":
                    reqDemo = DataHelper.generator(reqFullData, -1 ,false);
                    break;
            }
//            Collections.sort(reqs);
//            Collections.sort(ress);
            Collections.sort(reqs, new Comparator<PropertiesDTO>() {
                @Override
                public int compare(PropertiesDTO o1, PropertiesDTO o2) {
                    return o2.getSort()>o1.getSort()? -1:(o1.getSort()==o2.getSort()? 0:1);
                }
            });
            Collections.sort(ress, new Comparator<PropertiesDTO>() {
                @Override
                public int compare(PropertiesDTO o1, PropertiesDTO o2) {
                    return o2.getSort()>o1.getSort()? -1:(o1.getSort()==o2.getSort()? 0:1);
                }
            });
            datas.put("resDemo", resDemo);
            datas.put("reqDemo", reqDemo);

            ssheet.append(render("/rowdemo.xml", datas, cfg));
        }


        //出入参
        rootDoc.put("rows", ssheet.toString());
        //模块名称
        String interfaceName = modulesName.substring(0, 1).toUpperCase() + modulesName.substring(1);
        rootDoc.put("interfaceName", interfaceName);
        //模块描述
//        rootDoc.put("modulesDescription", modulesDescription);

        return render("/worksheet.xml", rootDoc, cfg);
    }

    /**
     * 修改或者新增操作
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @RequestMapping("/op")
    public String op(@RequestBody ReqDTO dto) throws Exception {
        String result = "";
        if (null != dto) {
            result = HTTPHelper.callRs(host, dto);
        }
        return result;
    }

    /**
     * 渲染worksheet
     *
     * @param templateName
     * @param templateMap
     * @param cfg
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws TemplateException
     */
    public String render(String templateName, Map<?, ?> templateMap, Configuration cfg) throws IOException, UnsupportedEncodingException, TemplateException {
        String out = "";
        freemarker.template.Template temp = cfg.getTemplate(templateName);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Writer writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        temp.process(templateMap, writer);
        writer.flush();
        out = os.toString("UTF-8");
        os.close();
        writer.close();
        return out;
    }

    /**
     * 渲染完整Excel
     *
     * @param templateName
     * @param templateMap
     * @param cfg
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws TemplateException
     */
    public File renderFile(String templateName, Map<?, ?> templateMap, Configuration cfg) throws IOException, UnsupportedEncodingException, TemplateException {
        String name = "temp" + (int) (Math.random() * 100000) + ".xml";
        File file = new File(name);
        freemarker.template.Template temp = cfg.getTemplate(templateName);
        Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
        temp.process(templateMap, w);
        w.close();
        return file;
    }

}
