package com.shun.jandanpic.service;

import com.shun.jandanpic.exception.BusinessException;
import com.shun.jandanpic.search.BaseSearch;
import com.shun.jandanpic.util.PageInfo;

import java.util.List;

/**
 * shun
 * 2018-01-28
 **/
public interface BaseService<T> {

    boolean insert(T t) throws BusinessException;

    boolean update(T t) throws BusinessException;

    boolean delete(long id) throws BusinessException;

    List<T> list(BaseSearch search);

}
