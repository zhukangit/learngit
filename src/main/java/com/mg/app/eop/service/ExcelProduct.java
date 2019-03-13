package com.mg.app.eop.service;

import com.mg.app.eop.util.FileUtil;

import java.io.File;
import java.util.List;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/2/13 9:50
 * @功能说明： 功能描述
 */
public class ExcelProduct {
    public   static  void  main(String[] dd)
    {
        List<File> temp;
        String strPath="C:\\workshop\\parent-master\\swagger201902110341252" ;//资源目录
        temp=FileUtil.getFileList(strPath);
        for (File fs:temp)
        {
            System.out.println("文件名："+fs.getName());
        }
    }

}
