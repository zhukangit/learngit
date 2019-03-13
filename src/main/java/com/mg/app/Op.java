//package com.mg.app;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import freemarker.template.Configuration;
//import freemarker.template.TemplateException;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class Op {
//    private static final String fileName = "captcha.jpg";
//    private static final String captchaUrl = "http://10.145.208.183:8086/captcha?t=";
//    private static final String surl = "http://10.145.208.183:8086/account/login";
//    private static final String sss = "http://10.145.208.183:8086/repository/get?id=85";
//    private static final String sid = "koa.sid=lTQf6KKt3fUUue68Q4gF2sKVtmHm-wRZ; koa.sid.sig=Ne-rYHqSnrlPdRiiKbhvEKCUzAw";
//    private static final String sig = "koa.sid.sig=2rDb9yI7OY4NW8wEKoR5zyWrSZA; path=/; expires=Tue, 09 Oct 2018 04:33:03 GMT; httponly";
//    private static final String host = "http://10.145.208.183:8086/";
//    @Autowired
//    Configuration cfg;
//
//    private File split(String data) throws IOException, TemplateException {
//        StringBuilder ssheet = new StringBuilder();
//        String modulesName = "";
//        //获取modules对象 只有一个
//        JSONArray jm = (JSONArray) JSONObject.parseObject(data).getJSONObject("data").get("modules");
//        System.out.println(jm);
//        JSONObject jbo = (JSONObject) jm.get(1);
//        for (Object obj : jm) {
//
//        }
//        modulesName = jbo.getString("name");
//        //获取所有接口列表
//        JSONArray ji = (JSONArray) jbo.get("interfaces");
////        System.out.println(ji);
//        for (Object obj : ji) {
//            JSONObject jbp = (JSONObject) obj;
//            Map datas = new HashMap();
//            //接口描述
//            datas.put("description", jbp.getString("description"));
//            //接口名称
//            datas.put("title", modulesName);
//            //接口中文名称
//            datas.put("zhdesc", jbp.getString("name"));
//            //接口地址
//            datas.put("url", jbp.getString("url"));
//            //获取该接口下所有属性
//            JSONArray jp = (JSONArray) jbp.get("properties");
//            System.out.println(jp);
//
//            List<RequestPam> reqs = new ArrayList<>();
//            List<ResponsePam> ress = new ArrayList<>();
//            datas.put("reqList", reqs);
//            datas.put("resList", ress);
//            for (Object jbvs : jp) {
//                JSONObject jbv = (JSONObject) jbvs;
//                if (jbv.getString("name").equals("_root_")) {
//                    continue;
//                }
//                switch (jbv.getString("scope")) {
//                    case "request":
//                        RequestPam req = new RequestPam();
//                        req.setName(jbv.getString("name"));
//                        req.setDescription(jbv.getString("description"));
//                        reqs.add(req);
//                        break;
//                    case "response":
//                        ResponsePam res = new ResponsePam();
//                        res.setName(jbv.getString("name"));
//                        res.setDescription(jbv.getString("description"));
//                        ress.add(res);
//                        break;
//                }
//            }
//            ssheet.append(render("worksheet.xml", datas, cfg));
//        }
//        Map rootDoc = new HashMap();
//        rootDoc.put("workSheet", ssheet.toString());
//
//        return renderFile("blank.xml", rootDoc, cfg);
//    }
//
//    @RequestMapping("/getFile")
//    public void getFile(@RequestBody ReqDTO dto, HttpServletResponse res) throws IOException {
//        String result = "";
//        if (null != dto) {
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpClientContext context = HttpClientContext.create();
//            HttpRequestBase httpPost = new HttpGet(host + dto.getPath());
//            httpPost.setHeader("Cookie", dto.getCookie());
////            httpPost.addHeader("Content-Type", "application/json");
//            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
//
//            httpPost.setHeader("Accept", "application/json");
//
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            System.out.println(response.getStatusLine().getStatusCode() + "\n");
//            HttpEntity entity = response.getEntity();
//            String responseContent = EntityUtils.toString(entity, "UTF-8");
//            System.out.println(responseContent);
//
//            response.close();
//            httpClient.close();
//            result = responseContent;
//        }
//        try {
//            InputStream inputStream = null;
//            ServletOutputStream out = null;
//            File file = split(result);
//            inputStream = new FileInputStream(file);
//            res.setContentType("text/xml");
//            res.setHeader("Content-Disposition", "attachment;filename="
//                    + file.getName());
//            res.setCharacterEncoding("utf-8");// 此句非常关键,不然excel文档全是乱码
//            out = res.getOutputStream();
//            byte[] buffer = new byte[512]; // 缓冲区
//            int bytesToRead = -1;
//            // 通过循环将读入的Excel文件的内容输出到浏览器中
//            while ((bytesToRead = inputStream.read(buffer)) != -1) {
//                out.write(buffer, 0, bytesToRead);
//            }
//            out.flush();
////            PrintWriter out = res.getWriter();
////            out.flush();
//            out.close();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
////        try {
////            return split(result);
////        } catch (TemplateException e) {
////            e.printStackTrace();
////        }
//    }
//
//    @RequestMapping("/getData")
//    public String getData(@RequestBody ReqDTO dto) throws IOException {
//        String result = "";
//        if (null != dto) {
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpClientContext context = HttpClientContext.create();
//            HttpRequestBase httpPost = new HttpGet(host + dto.getPath());
//            httpPost.setHeader("Cookie", dto.getCookie());
////            httpPost.addHeader("Content-Type", "application/json");
//            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
//
//            httpPost.setHeader("Accept", "application/json");
//
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            System.out.println(response.getStatusLine().getStatusCode() + "\n");
//            HttpEntity entity = response.getEntity();
//            String responseContent = EntityUtils.toString(entity, "UTF-8");
//            System.out.println(responseContent);
//
//            response.close();
//            httpClient.close();
//            result = responseContent;
//        }
//
////        try {
////            return split(result);
////        } catch (TemplateException e) {
////            e.printStackTrace();
////        }
//        return null;
//    }
//
//    @RequestMapping("/login")
//    public String loginMock() throws Exception {
//        URL url = new URL(sss);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
////        con.setRequestProperty("Cookie", sig);
//        con.setRequestProperty("Cookie", sid);
////        System.out.println(con.getResponseMessage());
//        String result = "";
//        StringBuffer buffer = new StringBuffer();
//        BufferedReader reader = null;            // 定义BufferedReader输入流来读取URL的响应
//        reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
//        String line = null;
//        while ((line = reader.readLine()) != null) {
//            buffer.append(line);
//        }
//        result = buffer.toString();
//        System.out.println(result);
//
//        return result;
//    }
//
//    @RequestMapping("/op")
//    public String op(@RequestBody ReqDTO dto) throws Exception {
////        URL url = new URL(host + path);
////        HttpURLConnection con = (HttpURLConnection) url.openConnection();
////        con.setRequestMethod(method); // 以Post方式提交表单，默认get方式
//        String result = "";
//        if (null != dto) {
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpClientContext context = HttpClientContext.create();
//            HttpPost httpPost = new HttpPost(host + dto.getPath());
//            httpPost.setHeader("Cookie", dto.getCookie());
////            httpPost.addHeader("Content-Type", "application/json");
//            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
//
//            httpPost.setHeader("Accept", "application/json");
//
//            httpPost.setEntity(new StringEntity(JSON.toJSONString(dto.getVal()), Charset.forName("UTF-8")));
////            httpPost.setEntity(new StringEntity(JSON.toJSONString(dto.getVal())));
//
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            System.out.println(response.getStatusLine().getStatusCode() + "\n");
//            HttpEntity entity = response.getEntity();
//            String responseContent = EntityUtils.toString(entity, "UTF-8");
//            System.out.println(responseContent);
//
//            response.close();
//            httpClient.close();
//            result = responseContent;
//
//
////            httpPost.setHeader("Host", "newhome.400gb.com");
////            httpPost.setHeader("Origin", "http://newhome.400gb.com");
////            httpPost.setHeader("Referer", "http://newhome.400gb.com/?item=files&action=index");
////            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36 CoolNovo/2.0.9.19");
////            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
////            CloseableHttpResponse response = httpclient.execute(httpPost, context);
////            StringBuffer buffer = new StringBuffer();
////            BufferedReader reader = null;            // 定义BufferedReader输入流来读取URL的响应
////            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
////            String line = null;
////            while ((line = reader.readLine()) != null) {
////                buffer.append(line);
////            }
////            result = buffer.toString();
////            System.out.println(result);
//        }
//        return result;
//    }
//
////    @RequestMapping("resder")
////    public String render() {
////        String a = "";
////        Map root = new HashMap();
//////        root.put("username", "Big Joe");
//////        root.put("id", 1);
////        List ll = new ArrayList();
////        ll.add(1);
////        ll.add(2);
////        ll.add(3);
////        ll.add(4);
////        root.put("lis", ll);
////        try {
////            a = render("csb.xml", root, cfg);
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (TemplateException e) {
////            e.printStackTrace();
////        }
////        return a;
////    }
//
//    public String render(String templateName, Map<?, ?> templateMap, Configuration cfg) throws IOException, UnsupportedEncodingException, TemplateException {
//        String out = "";
//        freemarker.template.Template temp = cfg.getTemplate(templateName);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        Writer writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//        temp.process(templateMap, writer);
//        writer.flush();
//        out = os.toString("UTF-8");
//        os.close();
//        writer.close();
//        return out;
//    }
//
//    public File renderFile(String templateName, Map<?, ?> templateMap, Configuration cfg) throws IOException, UnsupportedEncodingException, TemplateException {
//        String name = "temp" + (int) (Math.random() * 100000) + ".xml";
//        File file = new File(name);
//        freemarker.template.Template temp = cfg.getTemplate(templateName);
//        Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
//        temp.process(templateMap, w);
//        w.close();
//        return file;
////        ByteArrayOutputStream os = new ByteArrayOutputStream();
////        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
////        // 生成文件
////        temp.process(templateMap, out);
////        // 关闭流
////        out.flush();
////        out.close();
//    }
//
//    //获得网站的验证码及COOKIE
//    public static HashMap<String, String> getCode(String uri) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        try {
//            URL url = new URL(uri);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
//            String cookie = con.getHeaderField("set-cookie");
////            ImageIO.write(ImageIO.read(con.getInputStream()), "JPG", new File(fileName));
//            String code = "";//identifyCode();
//            map.put("cookie", cookie); //cookie=JSESSIONID=16yjdmlj4l1g81jqe39c41nooc;
//            map.put("code", code);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//    }
//
//}
