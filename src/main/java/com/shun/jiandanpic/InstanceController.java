package com.shun.jiandanpic;

import com.shun.jiandanpic.domain.Instance;
import com.shun.jiandanpic.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * shun
 * 2018-01-28
 **/
@Controller
@RequestMapping("/instance")
public class InstanceController {

    @Autowired
    private InstanceService instanceService;

    /**
     * 增加运行记录
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("process")
    public String process(@RequestParam("page") int page, @RequestParam("size") int size) {
        Instance instance = new Instance();
        instance.setCreateTime(new Date());
        instance.setPage(page);
        instance.setSize(size);
        instanceService.insert(instance);
        return null;
    }

}
