package com.shun.jandanpic;

import com.shun.jandanpic.constant.InstanceConstant;
import com.shun.jandanpic.domain.Instance;
import com.shun.jandanpic.exception.BusinessException;
import com.shun.jandanpic.processor.JiandanCrawler;
import com.shun.jandanpic.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * shun
 * 2018-01-28
 **/
@Controller
@RequestMapping("/instance")
public class InstanceController {

    @Autowired
    private InstanceService instanceService;
    @Autowired
    private JiandanCrawler crawler;

    /**
     * 增加运行记录
     * @param fromPage
     * @param toPage
     * @return
     */
    @RequestMapping("process")
    @ResponseBody
    public String process(@RequestParam(value = "fromPage", required = false) Integer fromPage, @RequestParam(value = "toPage", required = false) Integer toPage) {
        Instance instance = new Instance();
        instance.setCreateTime(new Date());
        instance.setFromPage(fromPage);
        instance.setToPage(toPage);
        instance.setStatus(InstanceConstant.Status.RUNNING);
        try {
            instanceService.insert(instance);
        } catch (BusinessException e) {
            return "已经有正在跑的任务了";
        }

        new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1)).execute(
                () -> crawler.crawl(Collections.singletonList("http://jandan.net/ooxx/page-104#comments"), fromPage, toPage));
        return "success";
    }

}
