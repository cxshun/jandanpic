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
public interface PageExtractor {

    /**
     * 根据xpath抽取指定页面的链接
     * @param url
     * @param xpath
     * @return
     */
    List<String> extractXPath(String url, String xpath);

}
