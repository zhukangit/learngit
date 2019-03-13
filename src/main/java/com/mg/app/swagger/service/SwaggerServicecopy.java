package com.mg.app.swagger.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.app.util.ObjUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SwaggerServicecopy {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    List<Map> listMap;
    Map<String, Map> pathMap = new HashMap<>();

    public String slipt(Map root, String tagName) throws JsonProcessingException {
        Map sw = new HashMap();
//        List<String> dtoList = new ArrayList<>();
//        Set<String> dtoSet = new HashSet<>();
        Map<String, String> dtoMap = new HashMap<>();
        List<Map> tagsN = new ArrayList<>();
        Map<String, Map> pathsN = new HashMap<>();
        Map definitionsN = new HashMap();
        sw.put("swagger", root.get("swagger"));
        sw.put("info", root.get("info"));
        sw.put("host", root.get("host"));
        sw.put("basePath", root.get("basePath"));

        List<Map> tags = (List) root.get("tags");
        Map<String, Object> paths = (Map) root.get("paths");
        Map<String, Object> definitions = (Map) root.get("definitions");
        sw.put("definitions", definitionsN);

        //根据指定接口名称遍历tag
        sw.put("tags", tagsN);
        for (Map jbo : tags) {
            if (!ObjUtil.isEmpty(jbo)) {
                String tag = (String) jbo.get("name");
                if (tag.equals(tagName)) {
                    tagsN.add(jbo);
                    break;
                }
            }

        }
        List<String> definitionName = new ArrayList();
        for (String key : paths.keySet()) {
            Map<String, Map> methodType = (Map<String, Map>) paths.get(key);
            for (Map value : methodType.values()) {
                List<String> tagsL = (List) value.get("tags");
                if (ObjUtil.collectionIsNull(tagsL)) {
                    continue;
                }
                //如果为此同一tag，则获取其所有关联的tags、paths、definitions
                if (tagName.equals(tagsL.get(0))) {
                    pathsN.put(key, methodType);
                    //获得入参信息
                    List parameters = (List) value.get("parameters");
                    if (!ObjUtil.collectionIsNull(parameters)) {
                        //获取每条入参信息，如果schema为body则获取到DTO名称，到definitions节点中取的
                        dtoMap=(Map)getMapChild(value,"originalRef",dtoMap);
                  /*      for (Object map : parameters) {
                            Map paramsMap = (Map) map;
                            Map schemaMap = (Map) paramsMap.get("schema");
                            if (!ObjUtil.collectionIsNull(schemaMap)) {
                                String reqDto = (String) schemaMap.get("originalRef");
                                //  String reqDto = (String) schemaMap.get("originalRef");
//                                dtoSet.add(reqDto);
                                dtoMap.put(reqDto, reqDto);
                            }
                        }*/
                    }
                    //获得出参信息
                    Map<String, Object> responses = (Map) value.get("responses");
              /*      if (!ObjUtil.collectionIsNull(responses)) {
                        //获取200响应的出参信息，取到DTO名称，到definitions节点中取的
                        Map<String, Object> succMap = (Map) responses.get("200");
                        if (!ObjUtil.collectionIsNull(succMap)) {
                            Map schemaMap = (Map) succMap.get("schema");
                            Map itemsMap = (Map) schemaMap.get("items");
                            String reqDto;
                            if (!ObjUtil.collectionIsNull(itemsMap)) {
                                reqDto = (String) itemsMap.get("originalRef");
                                // System.out.println("old:"+reqDto);
                            } else {
                                reqDto = (String) schemaMap.get("originalRef");
                                //    System.out.println("old1:"+reqDto);
                            }
//                            dtoSet.add(reqDto);
                            if (!ObjUtil.isEmpty(reqDto)) {
                                dtoMap.put(reqDto, reqDto);

                            }

                        }
                    }*/


                }
            }
        }

        pathMap = pathsN;
        //删除冗余元素
        Object test = delMapChild(pathsN);
   //     System.out.println(test);
        sw.put("paths", pathsN);


        Map<String,String>   loopMap =new HashMap<>();
        for(String key:dtoMap.keySet())
        {
            if(!ObjUtil.isEmpty(definitions.get(key))) {
                loopMap = getRelationDto(key, definitions, loopMap);
            }
        }
        dtoMap.putAll(loopMap);
        ObjUtil.deleteDuplicate2(dtoMap);
        for (String key : dtoMap.keySet()) {
            if (ObjUtil.isEmpty(key)) {
                continue;
            }
           System.out.println(key);
            Map dtoMapR = getDefinition(key, definitionsN, definitions);
            definitionsN.put(key, dtoMapR);
        }

        String ret_val = objectMapper.writeValueAsString(sw);
        return ret_val;
    }
   private Map<String,String>  getRelationDto(String parenDtoName,Map<String,Object> target,Map ddMap)
   {
       Map<String,String > dtoMap=null;
       for (String defDto:target.keySet())
       {
           if (ObjUtil.isEmpty(defDto)) {
               continue;
           }
           if (!defDto.equals(parenDtoName)) {
               continue;
           }
           Map  defDtoMap=( Map)target.get(defDto);

           dtoMap=(Map)  getMapChild(defDtoMap,"originalRef",ddMap);

       }
       return dtoMap;
   }
    private Map<String, Object> getDefinition(String parentDtoName, Map<String, Object> target, Map<String, Object> source) {
        Map<String, Object> dtoMap = null;
        for (String key : source.keySet()) {
            if (ObjUtil.isEmpty(key)) {
                continue;
            }
            if (!key.equals(parentDtoName)) {
                continue;
            }
            dtoMap = (Map) source.get(key);
            if (!ObjUtil.isEmpty(dtoMap)) {
                return dtoMap;
            }
        }
        return dtoMap;
    }

    private String getDefinitions(String parentDtoName, Map<String, Object> target, Map<String, Object> source) {
        for (String key : source.keySet()) {
            if (ObjUtil.isEmpty(key)) {
                continue;
            }
            if (!key.equals(parentDtoName)) {
                continue;
            }
            Map rootDto = (Map) source.get(key);
            Map<String, Map> propertiesDto = (Map) rootDto.get("properties");
            if (ObjUtil.collectionIsNull(propertiesDto)) {
                continue;
            }
            for (String fieldName : propertiesDto.keySet()) {
                Map<String, Object> filedTypeMap = propertiesDto.get(fieldName);
                String filedType = (String) filedTypeMap.get("type");

                if (ObjUtil.isEmpty(filedType)) {
                    String dtoName = (String) filedTypeMap.get("originalRef");
//                    dtoSet.add(dtoName);
                    if (!dtoName.equals("PaasReferenceObject") && !dtoName.equals("Number") && !dtoName.equals("Object")) {
                        target.put(dtoName, rootDto);
                        getDefinitions(dtoName, target, source);
                    }
                } else if (filedType.equals("array")) {
                    Map<String, String> items = (Map<String, String>) filedTypeMap.get("items");
                    String dtoName = items.get("originalRef");
//                    dtoSet.add(dtoName);
                    if (!ObjUtil.isEmpty(dtoName) && !dtoName.equals("Object")) {
                        getDefinitions(dtoName, target, source);
                        target.put(dtoName, rootDto);
                    }
                }
            }
        }

        return null;
    }

    /**
     * 递归获取map元素
     *
     * @param obj
     * @return
     */
    public Object getMapChild(Object obj,String key,Map  keyMap) {
        Map<String, Object> map = (Map<String, Object>) obj;

        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        Map map2 = new HashMap();
        while (it.hasNext()) {
            Map.Entry<String, Object> en = it.next();
            if (!(en.getValue() instanceof Map||en.getValue() instanceof List)) {

                if (key.equals(en.getKey())) {
                    System.out.println(en.getValue());
                    if(! ObjUtil.isEmpty(en.getValue())) {
                        keyMap.put(en.getValue(), en.getValue());
                    }
                }
            }
            else if ((en.getValue() instanceof List)) {

                    ArrayList listMap = (ArrayList) en.getValue();
                    for(Object lo:listMap) {
                        if(lo instanceof Map||lo instanceof List) {
                            getMapChild(lo,key,keyMap);
                        }
                    }

            }
            else {
                if ((en.getValue() instanceof Map)) {
                    map2 = (Map) en.getValue();
                    getMapChild(map2,key,keyMap);
                }
            }
        }
        return keyMap;

    }

    /**
     * 递归删除map元素
     *
     * @param obj
     * @return
     */
    public Object delMapChild(Object obj) {
        Map<String, Object> map = (Map<String, Object>) obj;

        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        Map map2 = new HashMap();
        while (it.hasNext()) {
            Map.Entry<String, Object> en = it.next();
            if (!(en.getValue() instanceof Map||en.getValue() instanceof List)) {
                if ("parameters".equals(en.getKey()))
                {
                    System.out.println("断点");
                }
                if ("originalRef".equals(en.getKey())) {
                    System.out.println(en.getValue());
                    it.remove();
                }
            }
            else if ((en.getValue() instanceof List)) {

                ArrayList listMap = (ArrayList) en.getValue();
                for(Object lo:listMap) {
                    if(lo instanceof Map||lo instanceof List) {
                        delMapChild(lo);
                    }
                }

            }
            else {
                if ((en.getValue() instanceof Map)) {
                    map2 = (Map) en.getValue();
                    delMapChild(map2);
                }
            }
        }
        return obj;

    }

}
