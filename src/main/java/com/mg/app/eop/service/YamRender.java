package com.mg.app.eop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.app.eop.entity.PropertiesDTO;
import com.mg.app.util.ObjUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

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
public class YamRender {
    /**
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:57
     * @功能说明： api 列表
     */
    public Map renderApiList(String fileData,Configuration cfg) {
        Map fDatas = new HashMap();
        Map datas = new HashMap();
        Map pathApi =new HashMap<>();
        Map actionApi=new HashMap();
        Map rootDoc = new HashMap();
        List<Map> listApi =new ArrayList<Map>();
        Map pathApiData;
        StringBuilder ssheet = new StringBuilder();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            fDatas = objectMapper.readValue(fileData, Map.class);
            pathApi=(Map)fDatas.get("paths");

            for (Object key1 :pathApi.keySet())
            {
                //动作列表
                actionApi=(Map)pathApi.get(key1.toString());
                if(ObjUtil.isEmpty(actionApi))
                {}
                else {
                    for(Object k:actionApi.keySet()) {
                        //请求地址
                        datas.put("url", key1);
                        //请求方式
                        datas.put("method", k);
                        pathApiData = (Map) actionApi.get(k.toString());

                        datas.putAll(renderApiRowReqestList((Map)actionApi.get(k), key1.toString(), fDatas));
                        datas.putAll(renderApiRowResponse((Map)actionApi.get(k), fDatas));
                        // 行渲染
                        ssheet.append(render("/rowdemo.xml", datas, cfg));
                        rootDoc.put("rows", ssheet.toString());
                        //模块名称
                        String interfaceName = "test";
                        rootDoc.put("interfaceName", interfaceName);
                        rootDoc.put("description", "");
                        rootDoc.put("title", "");
                        //文件渲染
                        renderFile("/worksheet.xml", rootDoc, cfg,"C:\\workshop\\mt\\xls\\"+interfaceName+k.toString()+".xls");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;

    }


    /**
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:57
     * @功能说明：  遍历引用dto属性
     */
    public  void  findDtoProperty( List<PropertiesDTO> req ,Map fileData,String refDto,String  parLevel,List<PropertiesDTO> fData) {
       Map dtoParam = new HashMap();
       Map dtoMap = new HashMap();
       Map  listParam= new HashMap();
       String refDtro ;
       Integer  childLevel=0;
       String cLevel;
        String localDto = null;
        PropertiesDTO propertiesDTO=new PropertiesDTO();
        //DTO定义解析
       Map definitionMap= (Map)  fileData.get("definitions");
        dtoMap=(Map)definitionMap.get(refDto);
        if (ObjUtil.isEmpty(dtoMap))
        {
            System.out.println("definitions结构为空"+refDto);
            return;
        }
        dtoParam =(Map)dtoMap.get("properties");
        //  在引用 dto的属性中仍然有引用dto
       if(!ObjUtil.isEmpty(dtoParam))
       {

           for (Object param:dtoParam.keySet())
           {

              Map paramm=(Map)dtoParam.get(param.toString());

               childLevel++;
               cLevel= parLevel.toString()+"."+childLevel.toString();
               //判断 是否引用对象
               if (!ObjUtil.isEmpty(paramm.get("items")))
               {
                   refDtro=(String)( (Map)paramm.get("items")).get("$ref");
                   if(ObjUtil.isEmpty(refDtro))
                   {//非引用对象


                       propertiesDTO.setName(paramm.toString());

                       // propertiesDTO.setDescription(((Map)paramm.get(param.toString())).get("description").toString());
                       propertiesDTO.setLevel(cLevel);
                       propertiesDTO.setValue("");
                       propertiesDTO.setShowTime("0-1");

                       fData.add(propertiesDTO);
                   }
                   else
                   {//引用对象
                       localDto=refDtro.substring(14);
                       propertiesDTO.setName(paramm.toString());

                       // propertiesDTO.setDescription(((Map)paramm.get(param.toString())).get("description").toString());
                       propertiesDTO.setLevel(cLevel);
                       propertiesDTO.setValue("");
                       propertiesDTO.setShowTime("0-1");

                       fData.add(propertiesDTO);
                       findDtoProperty(req,fileData, localDto,cLevel,fData);
                   }

               }
               else// 普通属性处理
               {


                   propertiesDTO.setName(paramm.toString());

                   // propertiesDTO.setDescription(((Map)paramm.get(param.toString())).get("description").toString());
                   propertiesDTO.setLevel(cLevel);
                   propertiesDTO.setValue("");
                   propertiesDTO.setShowTime("0-1");

                   fData.add(propertiesDTO);

               }


           }
       }

    }

    /**
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:57
     * @功能说明： api 请求行记录列表渲染   */
    public Map renderApiRowReqestList(Map fileData,String key,Map fData) {
        Map datas = new HashMap();
        Map params=new HashMap();
        List<Map>   reParam=new ArrayList<>();
        List<PropertiesDTO> reqs = new ArrayList<>();
        List<PropertiesDTO> ress = new ArrayList<>();
        PropertiesDTO propertiesDTO=new PropertiesDTO();
        Integer  parentLevel=0;

        final ObjectMapper objectMapper = new ObjectMapper();
        try {


                //入参解析
                reParam = (ArrayList<Map>) fileData.get("parameters");
                for (Map par : reParam) {
                    parentLevel++;
                    //判断参数为基础属性  还是引擎对象
                    if (!ObjUtil.isEmpty(par.get("schema"))) {
                        String refDto = ((Map) par.get("schema")).get("$ref").toString().substring(14);
                        //引用对象
                        propertiesDTO.setName(par.get("name").toString());
                        propertiesDTO.setDescription(par.get("description").toString());
                        propertiesDTO.setLevel(parentLevel.toString());
                        propertiesDTO.setValue("");
                        propertiesDTO.setShowTime("0-1");
                        reqs.add(propertiesDTO);
                        //引用对象属性列表
                        findDtoProperty(reqs, fData, refDto, parentLevel.toString(),reqs);


                    } else {
                        propertiesDTO.setName(par.get("name").toString());
                        propertiesDTO.setDescription(par.get("description").toString());
                        propertiesDTO.setLevel(parentLevel.toString());
                        propertiesDTO.setValue("");
                        propertiesDTO.setShowTime("0-1");
                        reqs.add(propertiesDTO);
                    }

                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        datas.put("reqList", reqs);
        return datas;
    }
    /**
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:57
     * @功能说明： api 请求行记录列表渲染   */
    public Map renderApiRowResponse(Map fileData,Map fData) {
        Map datas = new HashMap();
        Map params=new HashMap();
        Map   reParam=new HashMap();
        List<PropertiesDTO> reqs = new ArrayList<>();
        List<PropertiesDTO> ress = new ArrayList<>();
        PropertiesDTO propertiesDTO=new PropertiesDTO();
        Integer  parentLevel=0;

        final ObjectMapper objectMapper = new ObjectMapper();
        try {




                //出参解析
                reParam=(Map)fileData.get("responses");
               Map  par=(Map)reParam.get("200");
                {
                    //判断参数为基础属性  还是引擎对象
                    if(!ObjUtil.isEmpty(par.get("schema")))
                    {
                       if (!ObjUtil.isEmpty(((Map)par.get("schema")).get("$ref")))
                        { String refDto=((Map)par.get("schema")).get("$ref").toString().substring(14);
                            //引用对象
                            if(!ObjUtil.isEmpty(par.get("name"))) {
                                propertiesDTO.setName(par.get("name").toString());
                                propertiesDTO.setDescription(par.get("description").toString());
                                propertiesDTO.setLevel(parentLevel.toString());
                                propertiesDTO.setValue("");
                                propertiesDTO.setShowTime("0-1");
                                reqs.add(propertiesDTO);
                            }
                            //引用对象属性列表
                            findDtoProperty(reqs,fData,refDto,parentLevel.toString(),reqs);}
                           else
                        {
                          /*  propertiesDTO.setName("");
                            propertiesDTO.setDescription("");
                            propertiesDTO.setLevel(parentLevel.toString());
                            propertiesDTO.setValue("");
                            propertiesDTO.setShowTime("0-1");
                            reqs.add(propertiesDTO);*/

                        }



                    }
                    else {
                        if(!ObjUtil.isEmpty(par.get("name"))) {
                            propertiesDTO.setName(par.get("name").toString());
                            propertiesDTO.setDescription(par.get("description").toString());
                            propertiesDTO.setLevel(parentLevel.toString());
                            propertiesDTO.setValue("");
                            propertiesDTO.setShowTime("0-1");
                            reqs.add(propertiesDTO);
                        }
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        datas.put("resList", reqs);
        return datas;
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
     * @throws
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
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:57
     * @功能说明： 渲染内容落地文件
     */
    public File renderFile(String templateName, Map<?, ?> templateMap, Configuration cfg,String fileName) throws IOException, UnsupportedEncodingException, TemplateException {

        File file = new File(fileName);
        freemarker.template.Template temp = cfg.getTemplate(templateName);
        Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
        temp.process(templateMap, w);
        w.close();
        return file;
    }

}
