package com.shun.jandanpic.service;

import com.shun.jandanpic.util.PageInfo;

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
