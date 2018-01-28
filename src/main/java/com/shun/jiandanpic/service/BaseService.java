package com.shun.jiandanpic.service;

import com.shun.jiandanpic.util.PageInfo;

import java.util.List;

/**
 * shun
 * 2018-01-28
 **/
public interface BaseService<T> {

    boolean insert(T t);

    boolean update(T t);

    boolean delete(long id);

    List<T> list(PageInfo page);

}
