package com.mg.app.eop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.app.eop.entity.EopActionPropertiesDTO;
import com.mg.app.eop.entity.EopPropertiesDTO;
import com.mg.app.swagger.test.fileUtils;
import com.mg.app.util.ObjUtil;
import freemarker.template.Configuration;

import java.io.*;
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
 * @创建日期： 2019/2/13 9:57
 * @功能说明： 功能描述
 */
public class EopService {

    Integer  i=0;
    String codeHead="API_CRMCUS_T";//应用编号头
    String apiBasePath="/cus/customerorder";//基础路径
    String apiUrl="HTTP://10.145.167.229:8080";//环境地址
    String engineName="客户引擎";//引擎名称
    static String yamFileName="C:\\workshop\\mt\\swagger\\政企关系.txt";//文件地址
    String apiResTime="5";  //5秒超时
    String apiCode;//api编码
    String apiAction;//api动作
    static String fileTemplate="C:\\workshop\\mt\\src\\main\\resources\\templates";
    static Configuration cfg = new Configuration();
    fileUtils  fu=new fileUtils();
    final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:57
     * @功能说明： api  swagger文件解析
     */

    public Map yamFileParser(String yamFileName,int moduleSeq) {
         Map yamMap =new HashMap();
        String data = fu.readToString(yamFileName);
        Map root = null;

        try {
            root = objectMapper.readValue(data, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        yamMap.putAll(yamlApiList(root,moduleSeq));


     return yamMap;

    }
    /**
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:57
     * @功能说明：  swagger文件中api 列表提取
     */

    public  Map  yamlApiList( Map fileMap,int moduleSeq)
    {

        YamRender yr=new YamRender();
        Map datas = new HashMap();
        Map tags = (Map) fileMap.get("paths");
        apiBasePath="cus"+fileMap.get("basePath").toString();
        List<EopActionPropertiesDTO> apiActionList = new ArrayList<>();
        List<EopPropertiesDTO> apiList = new ArrayList<>();
        List<String>  apiNameList=new ArrayList<>();
        List<String>  apiActList=new ArrayList<>();
        Map apiMap=new HashMap();
        String ttname = null;

        for (Object o :tags.keySet())
        {

            String[] keyValue  = o.toString().split("\\/");
            if (keyValue.length==2)
            {

                apiNameList.add(o.toString());
                System.out.println(o.toString());
            }
            else if (keyValue.length==3)
            {
                if(o.toString().indexOf("Id")!=-1)
                {
                    apiActList.add(o.toString());
                    System.out.println("action:"+o.toString());
                }
                else
                {
                    apiNameList.add(o.toString());
                    System.out.println(o.toString());
                }

            }
        }
        //插入对象
        for (String o :apiNameList)
        {  i++;
            EopPropertiesDTO  eopPDTO =new EopPropertiesDTO();
            EopActionPropertiesDTO eopActionDTO=new EopActionPropertiesDTO();
            apiCode=codeHead+String.format("%05d", moduleSeq+i);
            String tagName=o.toString();

            eopPDTO.setApiCenterName("crmcus");
            eopPDTO.setApiCode(apiCode);
            eopPDTO.setApiPath(apiBasePath);
            eopPDTO.setApiProdUrl(apiUrl);
            eopPDTO.setApiResName(tagName);
            eopPDTO.setName(tagName);
            eopPDTO.setApiResTime("5");

            //提取名称
            Map ssm=(Map)tags.get(tagName);

            for (Object os:ssm.keySet())
            {
                ttname=((Map)ssm.get(os.toString())).get("summary").toString();
            }
            eopPDTO.setName("客户引擎"+ttname);
            apiList.add(eopPDTO);
            //   插入action
            Map actionM=(Map)tags.get(tagName);
            if(ObjUtil.isEmpty(actionM))
            {
                System.out.println("空了");
            }
            for (Object action:actionM.keySet()) {
                eopActionDTO=new EopActionPropertiesDTO();
                apiAction=action.toString();
                eopActionDTO.setApiAction(apiAction);
                eopActionDTO.setApiCode(apiCode);
                eopActionDTO.setApiResPath(tagName);
                apiActionList.add(eopActionDTO);
                eopPDTO.setListActiondto(apiActionList);

            }


            for(String ao:apiActList)
            {
                if (ao.indexOf(o.toString())!=-1)
                {
                    Map actionMap=(Map)tags.get(ao.toString());

                    for (Object action:actionMap.keySet()) {

                        eopActionDTO=new EopActionPropertiesDTO();
                        apiAction=action.toString();
                        eopActionDTO.setApiAction(apiAction);
                        eopActionDTO.setApiCode(apiCode);
                        eopActionDTO.setApiResPath(ao.toString());
                        apiActionList.add(eopActionDTO);
                        eopPDTO.setListActiondto(apiActionList);
                        tagName=((Map)((Map) tags.get(ao.toString())).get(apiAction)).get("summary").toString();
                    }
                }
            }


        }
//渲染
        //文件渲染
        try {
            datas.put("apiList", apiList);
            datas.put("apiActionList", apiActionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
return datas;
    }

    public Map merge(Map eopFileData, Map eopData) {
        List<EopActionPropertiesDTO> tarActionList = new ArrayList<>();
        List<EopPropertiesDTO> tarApiList = new ArrayList<>();
        List<EopActionPropertiesDTO> allActionList = new ArrayList<>();
        List<EopPropertiesDTO> allApiList = new ArrayList<>();
        List<EopActionPropertiesDTO> sourceActionList = new ArrayList<>();
        List<EopPropertiesDTO> sourceApiList = new ArrayList<>();
        tarApiList=(List<EopPropertiesDTO>) eopFileData.get("apiList");
        tarActionList=(List<EopActionPropertiesDTO>) eopFileData.get("apiActionList");
        sourceActionList=(List<EopActionPropertiesDTO>) eopData.get("apiActionList");
        sourceApiList=(List<EopPropertiesDTO>) eopData.get("apiList");
           if (ObjUtil.isEmpty(tarActionList)||ObjUtil.isEmpty(tarApiList))
           {}
           else
           {
               allActionList.addAll(tarActionList);
               allApiList.addAll(tarApiList);
           }

           allActionList.addAll(sourceActionList);
           allApiList.addAll(sourceApiList);
           eopFileData.put("apiList",allApiList);
           eopFileData.put("apiActionList",allActionList);

return eopFileData;
    }
}
