package com.shun.jandanpic.exception;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2018-01-30
 * <br/>==========================
 */
public class BusinessException extends Exception{

    private String msg;

    public BusinessException(String format, Object... args) {
        this.msg = String.format(format, args);
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
