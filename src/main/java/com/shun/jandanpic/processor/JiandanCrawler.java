package com.shun.jandanpic.processor;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.shun.jandanpic.util.HttpUtils;
import com.shun.jandanpic.util.LoggerUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2018-01-29
 * <br/>==========================
 */
@Service
public class JiandanCrawler implements DownloadProcessor, PageExtractor{
    @Override
    public void process(List<String> urlList) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        urlList.forEach(url -> {
            if (!url.startsWith("http://")) {
                url = "http:" + url;
            }

            HttpGet httpGet = new HttpGet(url);
            FileOutputStream fos = null;
            try {
                HttpResponse response = httpClient.execute(httpGet);
                byte[] bytes = EntityUtils.toByteArray(response.getEntity());

                LoggerUtils.info("正在下载:[{}]", url);
                fos = new FileOutputStream(new File("e://jandan/" + url.substring(url.lastIndexOf("/") + 1)));
                fos.write(bytes);
            } catch (IOException e) {
                LoggerUtils.error(e, "下载错误:[{}]", url);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    LoggerUtils.error(e, e.getMessage());
                }
            }
        });
    }

    @Override
    public List<String> extractXPath(String url, String xpath) {
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
        driver.get(url);

        List<String> resultList = new ArrayList<>();
        List<WebElement> elementList = driver.findElements(By.xpath(xpath));
        elementList.forEach(webElement -> {
            resultList.add(webElement.getAttribute(xpathMap.get(xpath)));
        });

        return resultList;
    }

    private static final String PAGE_XPATH = "//div[@class='cp-pagenavi']//a";
    private static final String IMAGE_XPATH = "//div[@class='row']/div[@class='text']/p/img";

    private static final Map<String, String> xpathMap;
    static {
        xpathMap = new HashMap<>();
        xpathMap.put(PAGE_XPATH, "href");
        xpathMap.put(IMAGE_XPATH, "src");
    }

    private static final List<String> processedPageUrlList = new ArrayList<>();

    public void crawl(String starterUrl) {
        LoggerUtils.info("正在爬取页面:[{}]", starterUrl);
        List<String> urlList = extractXPath(starterUrl, PAGE_XPATH);
        urlList.forEach(pageUrl -> {
            if (!processedPageUrlList.contains(pageUrl)) {
                processedPageUrlList.add(pageUrl);
                //暂停5秒，避免对煎蛋服务器造成太大压力
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    LoggerUtils.error(e, e.getMessage());
                }
                LoggerUtils.info("正在抽取:[{}]的图片地址", pageUrl);
                List<String> imageUrlList = extractXPath(pageUrl, IMAGE_XPATH);
                process(imageUrlList);

                crawl(pageUrl);
            }
        });
    }

}
