package com.mg.app.eop.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.app.eop.entity.EopActionPropertiesDTO;
import com.mg.app.eop.entity.EopPropertiesDTO;
import com.mg.app.eop.service.EopService;
import com.mg.app.eop.service.YamRender;
import com.mg.app.eop.util.FileUtil;
import com.mg.app.swagger.service.SwaggerService;
import com.mg.app.swagger.test.fileUtils;
import com.mg.app.util.ObjUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
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
 * @创建日期： 2019/2/13 10:27
 * @功能说明： 功能描述
 */
public class importEopFile {
    Integer  i=0;
    String codeHead="API_CRMCUS_T";//应用编号头
    String apiBasePath="";//基础路径
    String apiUrl="HTTP://10.145.167.229:8080";//环境地址
    String engineName="客户引擎";//引擎名称
    static String yamFileName="";//文件地址
    String apiResTime="5";  //5秒超时
    String apiCode;//api编码
    String apiAction;//api动作
    String eopFileExportFile="C:\\workshop\\mt\\xls\\test0305.xls";  //导出文件
    static String fileTemplate="C:\\workshop\\mt\\src\\main\\resources\\templates";
    String templateFileName="/eopautotest.xml";//模版名称
    static Configuration cfg = new Configuration();
    YamRender yr=new YamRender();
    EopService  es =new EopService();
    public void batchYamParser()
    {
        String FilePath="C:\\workshop\\mt\\swagger";
        int i=0;
        Map eopData=new HashMap();
        Map eopFileData=new HashMap();
        List<File>  fileList=new ArrayList<>();
        //获取文件列表
        fileList=FileUtil.getFileList(FilePath);
        try {
            cfg.setDirectoryForTemplateLoading(new File(fileTemplate));//模板路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析文件生成相关格式
        for(File files:fileList)
        { i++;
            eopData=es.yamFileParser(files.getName(),i);
            eopFileData=es.merge(eopFileData,eopData);

        }
        try {
            yr.renderFile(templateFileName, eopFileData, cfg,eopFileExportFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
    public static  void main(String[] arg) {
        importEopFile  ie=new importEopFile();


        ie.batchYamParser();
    }





}
