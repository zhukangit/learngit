package com.mg.app.util;

import com.alibaba.fastjson.JSON;
import com.mg.app.eop.ReqDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

public class HTTPHelper {
    /**
     * 请求外部服务（请求rap2 服务）
     *
     * @param dto
     * @return
     */
    public static String callRs(String host, ReqDTO dto) {
        String result = "";
        try {
            if (null != dto) {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpClientContext context = HttpClientContext.create();
                HttpRequestBase httpBase = new HttpGet(host + dto.getPath());
                httpBase.setHeader("Cookie", dto.getCookie());
//            httpPost.addHeader("Content-Type", "application/json");
                httpBase.addHeader("Content-type", "application/json; charset=utf-8");

                httpBase.setHeader("Accept", "application/json");
                CloseableHttpResponse response = null;
                switch (dto.getMethod()) {
                    case "POST":
                        HttpPost httpPost = (HttpPost) httpBase;
                        httpPost.setEntity(new StringEntity(JSON.toJSONString(dto.getVal()), Charset.forName("UTF-8")));
                        response = httpClient.execute(httpBase);
                        break;
                    case "GET":
                        HttpGet httpGet = (HttpGet) httpBase;//new HttpGet(host + dto.getPath());
                        response = httpClient.execute(httpGet);
                        break;
                }
                HttpEntity entity = response.getEntity();
                String responseContent = EntityUtils.toString(entity, "UTF-8");

                response.close();
                httpClient.close();
                result = responseContent;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
