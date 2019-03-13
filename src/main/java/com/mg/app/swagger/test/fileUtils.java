package com.mg.app.swagger.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/1/21 10:29
 * @功能说明： 功能描述
 */
public class fileUtils {
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
