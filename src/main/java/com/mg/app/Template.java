//package com.mg.app;
//
//import freemarker.cache.TemplateLoader;
//import freemarker.core.ParseException;
//import freemarker.template.*;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.ui.freemarker.SpringTemplateLoader;
//
//import java.io.*;
//import java.util.Locale;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class Template {
//    private Map<String, Configuration> cfgmap = new ConcurrentHashMap();
//
//    public static void main(String[] args) {
//        Template t = new Template();
//        t.renderTemplate()
//    }
//
//    public String renderTemplate(File dir, String templateName, Map<?, ?> templateMap) throws IOException, TemplateException {
//        long start = System.currentTimeMillis();
//        Configuration cfg = this.buildcfg("filesystem", dir, (ResourceLoader) null, (String) null);
//        String out = this.render(templateName, templateMap, cfg);
//        return out;
//    }
//
//    public String render(String templateName, Map<?, ?> templateMap, Configuration cfg) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, UnsupportedEncodingException, TemplateException {
//        String out = "";
//        freemarker.template.Template temp = cfg.getTemplate(templateName);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        Writer writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//        temp.process(templateMap, writer);
//        writer.flush();
//        out = os.toString("UTF-8");
//        os.close();
//        writer.close();
//        return out;
//    }
//
//    public Configuration buildcfg(String type, File dir, ResourceLoader resourceLoader, String templateLoaderPath) throws IOException {
//        if (type.equals("filesystem")) {
//            templateLoaderPath = dir.getPath();
//        }
//
//        Configuration cfg = (Configuration) this.cfgmap.get(templateLoaderPath);
//        if (cfg == null) {
//            Version ver = new Version("2.3.26-incubating");
//            cfg = new Configuration(ver);
//            cfg.setLocale(new Locale("zh_CN"));
//            cfg.setEncoding(Locale.getDefault(), "UTF-8");
//            cfg.setDefaultEncoding("UTF-8");
//            if (type.equals("filesystem")) {
//                cfg.setDirectoryForTemplateLoading(dir);
//            } else if (type.equals("jar")) {
//                TemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, templateLoaderPath);
//                cfg.setTemplateLoader(templateLoader);
////                cfg.setTemplateUpdateDelayMilliseconds(this.updateDelay);
//            }
//
//            cfg.setObjectWrapper((new DefaultObjectWrapperBuilder(ver)).build());
//            this.cfgmap.put(templateLoaderPath, cfg);
//        }
//
//        return cfg;
//    }
//}
