package com.mg.app;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MockLogin {
    private static final String fileName = "captcha.jpg";
    private static final String captchaUrl = "http://10.145.208.183:8086/captcha?t=";
    private static final String surl = "http://10.145.208.183:8086/account/login";
    public void login() throws Exception {

        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
         * java.net.URL and //java.net.URLConnection
         */
        URL url = new URL(surl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
/**
 * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
 */
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "GBK");
        //其中的memberName和password也是阅读html代码得知的，即为表单中对应的参数名称
        String pStr = "email=t&password=&captcha=";
        out.write(pStr); // post的关键所在！
// remember to clean up
        out.flush();
        out.close();

// 取得cookie，相当于记录了身份，供下次访问时使用
        String cookieVal = connection.getHeaderField("Set-Cookie");
    }

    //获得网站的验证码及COOKIE
    public static HashMap<String, String> getCode(String uri) {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
            String cookie = con.getHeaderField("set-cookie");
//            ImageIO.write(ImageIO.read(con.getInputStream()), "JPG", new File(fileName));
            String code = "";//identifyCode();
            map.put("cookie", cookie); //cookie=JSESSIONID=16yjdmlj4l1g81jqe39c41nooc;
            map.put("code", code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        getCode(captchaUrl);
    }
}
