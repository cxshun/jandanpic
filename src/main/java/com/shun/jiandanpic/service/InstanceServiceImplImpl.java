package com.shun.jiandanpic.service;

import com.shun.jiandanpic.domain.Instance;
import com.shun.jiandanpic.mapper.BaseMapper;
import com.shun.jiandanpic.mapper.InstanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * shun
 * 2018-01-27
 **/
@Service
public class InstanceServiceImplImpl extends BaseServiceImpl<Instance> implements InstanceService{

    @Autowired
    private InstanceMapper instanceMapper;

    @Override
    BaseMapper getMapper() {
        return instanceMapper;
    }

}
