package com.mg.app.util;

import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ObjUtil {
    public static boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    /**
     * 集合非空判断
     *
     * @param
     * @return
     * @author 杨敏
     * @date 2018年8月8日
     */
    public static boolean collectionIsNull(Object collection) {
        boolean isNull = false;
        if (isEmpty(collection)) {
            isNull = true;
            return isNull;
        }
        if (collection instanceof Map) {
            Map map = (Map) collection;
            isNull = map.isEmpty();
        } else if (collection instanceof List) {
            List list = (List) collection;
            isNull = list.isEmpty();
        } else if (collection instanceof String[]) {
            String[] list = (String[]) collection;
            if (list != null || list.length > 0) {
                isNull = false;
            }
        } else if (collection instanceof Set) {
            Set set = (Set) collection;
            isNull = set.isEmpty();
        }
        return isNull;
    }

    /**
     * 打包成zip包
     */
    public static void generateZip(String zipPath, String sourcePath) throws Exception {
        File downloadPath = new File(sourcePath);
        File[] files = downloadPath.listFiles();
        OutputStream os = new FileOutputStream(sourcePath + zipPath);
        ZipOutputStream out = null;
        try {
            byte[] buffer = new byte[1024];
            //生成的ZIP文件名为Demo.zip
            out = new ZipOutputStream(os);
            //需要同时下载的两个文件result.txt ，source.txt
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                //读入需要下载的文件的内容，打包到zip文件
                while ((len = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static String getRandom(String type) {
        int num = (int) (1 + Math.random() * (10 - 1 + 1));
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        String finalName = getSourcePath() + "\\" + type + sf.format(new Date()) + num;
        return finalName;
    }


    public static String getSourcePath() {
        return System.getProperty("user.dir");//ObjUtil.class.getResource("/").getPath();
    }
    /**
     *
     * 利用set不可重复特性进行判断
     *
     * @param map
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void deleteDuplicate2(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        Set<String> set = new HashSet<String>();
        for (Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> entry = iterator.next();
            if (set.contains(entry.getValue())) {
                iterator.remove();
                continue;
            } else {
                set.add(entry.getValue());
            }
        }
    }

    public static boolean deleteDir(String path) {
//        File file = new File(path);
//        if (!file.exists()) {//判断是否待删除目录是否存在
//            System.err.println("The dir are not exists!");
//            return false;
//        }
//
//        String[] content = file.list();//取得当前目录下所有文件和文件夹
//        if (content.length > 0) {
//            for (String name : content) {
//                File temp = new File(path, name);
//                if (temp.isDirectory()) {//判断是否是目录
//                    deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
//                    temp.delete();//删除空目录
//                } else {
//                    if (!temp.delete()) {//直接删除文件
//                        System.err.println("Failed to delete " + name);
//                    }
//                }
//            }
//        } else {
//
//        }
//        file.delete();
        return true;
    }


}
