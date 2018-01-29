package com.shun.jandanpic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2018-01-29
 * <br/>==========================
 */
public class LoggerUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    public static void info(String msg, Object... args) {
        logger.info(msg, args);
    }

    public static void error(Throwable e, String msg, Object ... args) {
        logger.error(msg, args, e);
    }

    public static void debug(String msg, Object... args) {
        logger.error(msg, args);
    }

}
