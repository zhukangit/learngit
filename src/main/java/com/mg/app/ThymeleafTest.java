//package com.mg.app;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
////import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
//
//import javax.servlet.ServletContext;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class ThymeleafTest {
////    @Autowired
////    ThymeleafViewResolver thymeleafViewResolver;
//    private static TemplateEngine templateEngine = new TemplateEngine();
//    ApplicationContext applicationContext;
//
//    @RequestMapping("/get/{template}")
//    public String getData(@PathVariable String template) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", "aaaa");
//        params.put("id", 1);
//        String a = render("templates/" + template, params);
//        System.out.println(a);
//        return a;
//    }
//
//    ;
//
//    public void render() {
////        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
////        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
//
//    }
//
//    private static void initializeTemplateEngine() {
////        ServletContext sc = getServletContext();
////        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
////        // XHTML is the default mode, but we set it anyway for better understanding of code
////        templateResolver.setTemplateMode("XHTML");
////        // This will convert "home" to "/WEB-INF/templates/home.html"
////        templateResolver.setPrefix("/WEB-INF/templates/");
////        templateResolver.setSuffix(".html");
////        // Template cache TTL=1h. If not set, entries would be cached until expelled by LRU
////        templateResolver.setCacheTTLMs(3600000L);
////
////        templateEngine = new TemplateEngine();
////        templateEngine.setTemplateResolver(templateResolver);
//
//    }
//
//    /**
//     * 使用 Thymeleaf 渲染 HTML
//     *
//     * @param template HTML模板
//     * @param params   参数
//     * @return 渲染后的HTML
//     */
//    public String render(String template, Map<String, Object> params) {
//        Context context = new Context();
//        context.setVariables(params);
//        return templateEngine.process(template, context);
//    }
//
//}
