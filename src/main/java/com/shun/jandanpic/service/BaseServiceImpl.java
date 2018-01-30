package com.shun.jandanpic.service;

import com.shun.jandanpic.exception.BusinessException;
import com.shun.jandanpic.mapper.BaseMapper;
import com.shun.jandanpic.search.BaseSearch;
import com.shun.jandanpic.util.PageInfo;

import java.util.List;

/**
 * shun
 * 2018-01-27
 **/
public abstract class BaseServiceImpl<T> implements BaseService<T>{

    abstract BaseMapper getMapper();

    public boolean insert(T t) throws BusinessException{
        return this.getMapper().insert(t);
    }

    public boolean update(T t) throws BusinessException{
        return this.getMapper().update(t);
    }

    public boolean delete(long id) throws BusinessException{
        return this.getMapper().delete(id);
    }

    public List<T> list(BaseSearch search) {
        return this.getMapper().list(search);
    }

}
