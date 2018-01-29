package com.shun.jandanpic.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2018-01-29
 * <br/>==========================
 */
public class HttpUtils {

    /**
     * 下载指定的页面
     * @param url
     * @return
     */
    public static String downloadUrl(String url) {
        if (!url.startsWith("http:")) {
            url = "http:" + url;
        }



        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding","gzip,deflate,sdch");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection","keep-alive");
        httpGet.setHeader("Host", "jandan.net");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        try {
            HttpResponse response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LoggerUtils.error(e, "获取页面url:[{}]出错", url);
            return null;
        }
    }

}
