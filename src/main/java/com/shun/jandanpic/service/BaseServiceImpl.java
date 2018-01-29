package com.shun.jandanpic.service;

import com.shun.jandanpic.mapper.BaseMapper;
import com.shun.jandanpic.util.PageInfo;

import java.util.List;

/**
 * shun
 * 2018-01-27
 **/
public abstract class BaseServiceImpl<T> implements BaseService<T>{

    abstract BaseMapper getMapper();

    public boolean insert(T t) {
        return this.getMapper().insert(t);
    }

    public boolean update(T t) {
        return this.getMapper().update(t);
    }

    public boolean delete(long id) {
        return this.getMapper().delete(id);
    }

    public List<T> list(PageInfo page) {
        return this.getMapper().list(page);
    }

}
