package com.shun.jiandanpic.mapper;

import com.shun.jiandanpic.util.PageInfo;

import java.util.List;

/**
 * shun
 * 2018-01-27
 **/
public interface BaseMapper<T> {

    /**
     * 新增记录
     * @param t
     * @return
     */
    boolean insert(T t);

    /**
     * 更新记录
     * @param t
     * @return
     */
    boolean update(T t);

    /**
     * 删除指定记录
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 获取记录列表
     * @param page
     * @return
     */
    List<T> list(PageInfo page);
}
