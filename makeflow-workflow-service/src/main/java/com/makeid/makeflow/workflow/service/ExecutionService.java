package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.workflow.dao.ExecuteEntityDao;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Service
public class ExecutionService {

    @Resource
    private ExecuteEntityDao executeEntityDao;

    public ExecuteEntity create(){
        return executeEntityDao.create();
    }

    public void save(ExecuteEntity executeEntity) {
        executeEntityDao.save(executeEntity);
    }

}
