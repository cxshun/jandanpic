package com.shun.jandanpic.processor;

import com.shun.jandanpic.constant.InstanceConstant;
import com.shun.jandanpic.domain.Instance;
import com.shun.jandanpic.exception.BusinessException;
import com.shun.jandanpic.search.InstanceSearch;
import com.shun.jandanpic.service.InstanceService;
import com.shun.jandanpic.util.LoggerUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private InstanceService instanceService;

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

    private static final WebDriver driver;
    static {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\shun\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.setProxy(null);
        driver = new ChromeDriver(options);
    }

    @Override
    public List<String> extractXPath(String url, String xpath) {
        driver.get(url);

        List<String> resultList = new ArrayList<>();
        List<WebElement> elementList = driver.findElements(By.xpath(xpath));
        elementList.forEach(webElement -> {
            resultList.add(webElement.getAttribute(xpathMap.get(xpath)));
        });

        return resultList;
    }

    private static final String PAGE_XPATH = "//div[@class='cp-pagenavi']//a[@class!='previous-comment-page']";
    private static final String IMAGE_XPATH = "//div[@class='row']/div[@class='text']/p/img";

    private static final Map<String, String> xpathMap;
    static {
        xpathMap = new HashMap<>();
        xpathMap.put(PAGE_XPATH, "href");
        xpathMap.put(IMAGE_XPATH, "src");
    }

    private static final List<String> processedPageUrlList = new ArrayList<>();
    private Pattern pagePattern = Pattern.compile("page-(\\d*)");

    public void crawl(List<String> pageUrlList, Integer fromPage, Integer toPage) {
        pageUrlList.stream().distinct().forEach(pageUrl -> {
            if (!processedPageUrlList.contains(pageUrl)) {
                LoggerUtils.info("正在爬取页面:[{}]", pageUrlList);

                //通过正则表达式，匹配URL中的页码
                Matcher matcher = pagePattern.matcher(pageUrl);
                if (matcher.find()) {
                    int page = Integer.parseInt(matcher.group(1));
                    if (page >= fromPage && page <= toPage) {
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
                    }
                }
                crawl(extractXPath(pageUrl, PAGE_XPATH), fromPage, toPage);
            }
        });

        //更新运行实例的状态
        InstanceSearch instanceSearch = new InstanceSearch();
        instanceSearch.setStatus(InstanceConstant.Status.RUNNING);
        List<Instance> instanceList = instanceService.list(instanceSearch);
        instanceList.forEach(instance -> {
            try {
                instanceService.update(instance);
            } catch (BusinessException e) {
                LoggerUtils.error(e, e.getMessage());
            }
        });
    }

}
