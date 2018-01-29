package com.shun.jandanpic;

import com.shun.jandanpic.processor.JiandanCrawler;
import com.shun.jandanpic.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("process")
    public String process(/*@RequestParam(value = "page", required = false) int page, @RequestParam(value = "size", required = false) int size/)**/) {
        /*Instance instance = new Instance();
        instance.setCreateTime(new Date());
        instance.setPage(page);
        instance.setSize(size);
        instanceService.insert(instance);*/

        crawler.crawl("http://jandan.net/ooxx");
        return "success";
    }

}
