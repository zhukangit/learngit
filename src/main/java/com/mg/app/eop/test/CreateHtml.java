package com.mg.app.eop.test;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/2/12 9:47
 * @功能说明： 功能描述
 */

        
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
public class CreateHtml {
        public static void main(String[] args) {
        try {
        //创建一个合适的Configration对象
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File("C:\\workshop\\mt\\src\\main\\resources\\templates"));
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setDefaultEncoding("UTF-8"); //这个一定要设置，不然在生成的页面中 会乱码
        //获取或创建一个模版。
        Template template = configuration.getTemplate("static.html");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("description", "我正在学习使用Freemarker生成静态文件！");
        
        List<String> nameList = new ArrayList<String>();
        nameList.add("陈靖仇");
        nameList.add("玉儿");
        nameList.add("宇文拓");
        paramMap.put("nameList", nameList);
        
        Map<String, Object> weaponMap = new HashMap<String, Object>();
        weaponMap.put("first", "轩辕剑");
        weaponMap.put("second", "崆峒印");
        weaponMap.put("third", "女娲石");
        weaponMap.put("fourth", "神农鼎");
        weaponMap.put("fifth", "伏羲琴");
        weaponMap.put("sixth", "昆仑镜");
        weaponMap.put("seventh", null);
        paramMap.put("weaponMap", weaponMap);
        
        Writer writer= new OutputStreamWriter(new FileOutputStream("success.html"),"UTF-8");
        template.process(paramMap, writer);
        
        System.out.println("恭喜，生成成功~~");
        } catch (IOException e) {
        e.printStackTrace();
        } catch (TemplateException e) {
        e.printStackTrace();
        }
        
        }
        }
       