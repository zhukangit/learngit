package com.mg.app.eop.util;

import com.mg.app.eop.entity.PropertiesDTO;
import com.mg.app.util.ObjUtil;

import java.util.List;

public class DataHelper {
    private static int sort = 1;

    //    private static
    public static String generator(List<PropertiesDTO> resource, int rootId, boolean getRoot) {
        StringBuilder sj = new StringBuilder("{");

        int first = 1;
        int second = 1;
        for (PropertiesDTO pro : resource) {
            if (pro.getParentId() == rootId && !pro.getName().equals("_root_")) {
                pro.setLevel(String.valueOf(first));
                pro.setSort(sort);
                if (pro.getType().equals("Array")) {
                    pro.setShowTime("0-N");
                } else {
                    pro.setShowTime("0-1");
                }

                String c = generatorJson(resource, pro.getId(), pro.getName(), String.valueOf(first));
                if (ObjUtil.isEmpty(c)) {
                    String value = pro.getValue();
                    if (ObjUtil.isEmpty(value)) {
                        value = "";
                    }
                    sj.append(String.format("\"%s\":\"%s\",", pro.getName(), value));
                } else {
//                    sj.append(String.format("\"%s\":%s,", pro.getName(), c));
                    if (pro.getType().equals("Array")) {
                        sj.append(String.format("\"%s\":[%s],", pro.getName(), c));
                    } else {
                        sj.append(String.format("\"%s\":%s,", pro.getName(), c));
                    }
                }
                first++;
                sort++;
            } else if (!getRoot && pro.getName().equals("_root_")) {
                String c = generator(resource, pro.getId(), true);

                sj.deleteCharAt(sj.length() - 1);
                sj.append("[" + c + "]");
            }
        }
        if (sj.lastIndexOf(",") == sj.length() - 1) {
            sj.deleteCharAt(sj.length() - 1);
            sj.append("}");
        }
        return sj.toString();
    }

    private static String generatorJson(List<PropertiesDTO> resource, int parentId, String key, String first) {
//        level = level + ".1";
        String showTime = "0-1";
        int second = 1;
        StringBuilder child = new StringBuilder();
        for (PropertiesDTO pro : resource) {
            if (pro.getParentId() == parentId) {
                pro.setLevel(first + "." + second);
                pro.setSort(sort);
                if (pro.getType().equals("Array")) {
                    pro.setShowTime("0-N");
                } else {
                    pro.setShowTime("0-1");
                }

                String cn = generatorJson(resource, pro.getId(), pro.getName(), first + "." + second);
                if (ObjUtil.isEmpty(cn)) {
                    String value = pro.getValue();
                    if (ObjUtil.isEmpty(value)) {
                        value = "";
                    }
                    child.append(String.format("\"%s\":\"%s\",", pro.getName(), value));
                } else {
                    if (pro.getType().equals("Array")) {
                        child.append(String.format("\"%s\":[%s],", pro.getName(), cn));
                    } else {
                        child.append(String.format("\"%s\":%s,", pro.getName(), cn));
                    }

                }
                second++;
                sort++;
            }
        }
        if (child.length() > 0) {
            child.deleteCharAt(child.length() - 1);
//            if(pro.){
//
//            }
            return "{" + child.toString() + "}";
        }
        return null;
    }

    public static String generatorGet(List<PropertiesDTO> resource) {
        StringBuilder sGet = new StringBuilder("?");
        for (PropertiesDTO pro : resource) {
            sGet.append(pro.getName() + "=" + pro.getValue() + "&");
        }
        return sGet.deleteCharAt(sGet.length() - 1).toString();
    }

}
