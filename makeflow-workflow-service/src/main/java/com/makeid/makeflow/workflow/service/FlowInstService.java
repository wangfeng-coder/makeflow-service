package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.workflow.dao.FlowInstDao;
import com.makeid.makeflow.workflow.dao.FlowInstEntityDao;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.impl.FlowInstEntityImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Service
public class FlowInstService {

    @Resource
    private FlowInstEntityDao flowInstEntityDao;

    public void save(FlowInstEntity flowInstEntity) {
        flowInstEntityDao.save(flowInstEntity);
    }

    public FlowInstEntity create() {
        return flowInstEntityDao.create();
    }
}