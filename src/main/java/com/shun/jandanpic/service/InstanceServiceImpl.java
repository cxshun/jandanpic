package com.shun.jandanpic.service;

import com.shun.jandanpic.constant.InstanceConstant;
import com.shun.jandanpic.domain.Instance;
import com.shun.jandanpic.exception.BusinessException;
import com.shun.jandanpic.mapper.BaseMapper;
import com.shun.jandanpic.mapper.InstanceMapper;
import com.shun.jandanpic.search.InstanceSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * shun
 * 2018-01-27
 **/
@Service
public class InstanceServiceImpl extends BaseServiceImpl<Instance> implements InstanceService{

    @Autowired
    private InstanceMapper instanceMapper;

    @Override
    BaseMapper getMapper() {
        return instanceMapper;
    }

    @Override
    public boolean insert(Instance instance) throws BusinessException{
        InstanceSearch instanceSearch = new InstanceSearch();
        instanceSearch.setStatus(InstanceConstant.Status.RUNNING);
        instanceSearch.setSize(1);
        instanceSearch.setOffset(1);
        List<Instance> instanceList = this.instanceMapper.list(instanceSearch);
        if (instanceList.size() > 0) {
            throw new BusinessException("已经有正在跑的任务了...");
        }

        return super.insert(instance);
    }
}
