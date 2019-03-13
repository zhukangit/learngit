package com.mg.app.eop.util;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/3/8 9:14
 * @功能说明： 功能描述
 */
public class StrUtil {
    public static HashSet<String> sameSubString(String s1, String s2){

        ArrayList<String> sub1 = new ArrayList<String>();//保存s1的子串
        ArrayList<String> sub2 = new ArrayList<String>();//保存s2的子串
        HashSet<String> result = new HashSet<String>();//保存相同的子串

        //求s1的子串
        for(int i = 1;i<s1.length();i++){
            for(int j = 0;j<=s1.length()-i;j++){
                sub1.add(s1.substring(j,j+i));
            }
        }

        //求s2的子串
        for(int i = 1;i<s2.length();i++){
            for(int j = 0;j<=s2.length()-i;j++){
                sub2.add(s2.substring(j,j+i));
            }
        }

        System.out.println("\"AABBC\"的子串 ： " + sub1);
        System.out.println("\"ABBCC\"的子串 ： " + sub2);

        for(String s: sub1)
            if(sub2.contains(s))
                result.add(s);

        for(String s: sub2)
            if(sub1.contains(s))
                result.add(s);

        return result;
    }
}
