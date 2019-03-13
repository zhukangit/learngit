package com.mg.app.eop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/2/13 9:39
 * @功能说明： 功能描述
 */
public class FileUtil {
    /**
     *      *
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:39
     * @功能说明： 文件列表
     */
    public static List getFileList(String strPath) {
        File fileDir = new File(strPath);
        List<File>   fileList=new ArrayList<>();
        if (null != fileDir && fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();

            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    // 如果是文件夹 继续读取
                    if (files[i].isDirectory()) {
                        getFileList(files[i].getAbsolutePath());
                    } else {
                        String strFileName = files[i].getAbsolutePath();
                        if (null != strFileName && !strFileName.endsWith(".jar") && !strFileName.endsWith(".cmd")
                                && !strFileName.endsWith(".xlsx")) {
                            System.out.println("---" + strFileName);
                            fileList.add(files[i]);
                        }
                    }
                }

            } else {
                if (null != fileDir) {
                    String strFileName = fileDir.getAbsolutePath();
                    // 排除jar cmd 和 xlsx （根据需求需要）
                    if (null != strFileName && !strFileName.endsWith(".jar") && !strFileName.endsWith(".cmd")
                            && !strFileName.endsWith(".xlsx")) {
                        System.out.println("---" + strFileName);
                        fileList.add(fileDir);
                    }
                }
            }
        }

   return   fileList;
    }

    /**
     *      *
     * @author ideal
     * @version Revision 版本号
     * @版权： 版权所有 (c) 2018
     * @创建日期： 2019/2/13 9:39
     * @功能说明： 文件内容读取为字符串
     */


    FileInputStream in;
    public    String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();

        byte[] filecontent = new byte[filelength.intValue()];
        try {
            in = new FileInputStream(file);
            in.read(filecontent);
            return new String(filecontent, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
