package com.mg.app.eop.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.app.eop.entity.PropertiesDTO;
import com.mg.app.eop.service.YamRender;
import com.mg.app.eop.util.FileUtil;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/2/13 10:27
 * @功能说明： 功能描述
 */
public class ExportEopExlsFile {

    public   static  void main (String[] arg)
    {
       /* FileUtil  fu=new FileUtil();
        YamRender yr=new YamRender();
        String fileData;
        Map rootDoc = new HashMap();
        Map datas = new HashMap();
        Configuration cfg = new Configuration();
        // 设置文件所在目录的路径
        try {
            cfg.setDirectoryForTemplateLoading(new File("C:\\workshop\\mt\\src\\main\\resources\\templates"));//模板路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder ssheet = new StringBuilder();
        final ObjectMapper objectMapper = new ObjectMapper();
        Map jbp =new HashMap();
        try {
            fileData = fu.readToString("C:\\workshop\\parent-master\\swagger201902110341252\\account-controller.txt");
//接口描述
            jbp = objectMapper.readValue(fileData, Map.class);
            rootDoc.put("description", ((Map)jbp.get("info")).get("description"));
//            //接口名称
//            datas.put("title", modulesName);
            //接口中文名称
           String apiName= StringUtils.substringBefore((String)((List<Map>)jbp.get("tags")).get(0).get("name"),"-");
            rootDoc.put("title", ((Map)jbp.get("info")).get("description"));
            //接口地址
            String apiBaseUrl=jbp.get("basePath").toString();
            String apiUrl=null;
            datas.put("url",apiBaseUrl );
            //请求方式
            datas.put("method", "post");

            List<PropertiesDTO> reqs = new ArrayList<>();
            List<PropertiesDTO> ress = new ArrayList<>();
            PropertiesDTO  propertiesDTO=new PropertiesDTO();
            propertiesDTO.setName("test");
            propertiesDTO.setDescription("贪吃货");
            propertiesDTO.setLevel("1");
            propertiesDTO.setValue("值2");
            propertiesDTO.setShowTime("1900-01-01");
            reqs.add(propertiesDTO);
            ress.add(propertiesDTO);
            //renderApiRowReqestList
            datas.put("resList", ress);
            List<PropertiesDTO> reqFullData = new ArrayList<>();
            List<PropertiesDTO> resFullData = new ArrayList<>();
            String resDemo="这是全是骗子";
            String reqDemo="这里是欢乐101";
            datas.put("resDemo", resDemo);
            datas.put("reqDemo", reqDemo);


            // 行渲染
            ssheet.append(yr.render("/rowdemo.xml", datas, cfg));
            // sheet渲染
            //出入参
            rootDoc.put("rows", ssheet.toString());
            //模块名称
            String interfaceName = apiName.substring(0, 1).toUpperCase() + apiName.substring(1);
            rootDoc.put("interfaceName", interfaceName);

            //文件渲染
            yr.renderFile("/worksheet.xml", rootDoc, cfg,"C:\\workshop\\mt\\xls\\"+apiName+".xls");
        }
        catch (Exception e )
        {
            System.out.println( e.toString());
        }


*/

        test();
    }
public static void test()
{


    FileUtil  fu=new FileUtil();
    YamRender yr=new YamRender();
    String fileData;
    Map rootDoc = new HashMap();
    Map datas = new HashMap();
    Configuration cfg = new Configuration();
    // 设置文件所在目录的路径
    try {
        cfg.setDirectoryForTemplateLoading(new File("C:\\workshop\\mt\\src\\main\\resources\\templates"));//模板路径
    } catch (IOException e) {
        e.printStackTrace();
    }
    StringBuilder ssheet = new StringBuilder();
    final ObjectMapper objectMapper = new ObjectMapper();
    Map jbp =new HashMap();
    try {
        fileData = fu.readToString("C:\\workshop\\parent-master\\swagger201902110341252\\account-controller.txt");

        yr.renderApiList(fileData,cfg);

        List<PropertiesDTO> reqFullData = new ArrayList<>();
        List<PropertiesDTO> resFullData = new ArrayList<>();
        String resDemo="这是全是骗子";
        String reqDemo="这里是欢乐101";
        datas.put("resDemo", resDemo);
        datas.put("reqDemo", reqDemo);


        // 行渲染
        ssheet.append(yr.render("/rowdemo.xml", datas, cfg));
        // sheet渲染
        //出入参
        rootDoc.put("rows", ssheet.toString());
        //模块名称
        String interfaceName = "test";
        rootDoc.put("interfaceName", interfaceName);

        //文件渲染
        yr.renderFile("/worksheet.xml", rootDoc, cfg,"C:\\workshop\\mt\\xls\\"+interfaceName+".xls");
    } catch(Exception e)
    {
System.out.println(e.toString());
    }
}
}
