package com.shun.jandanpic.processor;

import java.util.List;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2018-01-29
 * <br/>==========================
 */
public interface DownloadProcessor {

    /**
     * 下载指定的URL图片
     * @param urlList
     */
    void process(List<String> urlList);

}
