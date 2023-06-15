package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.workflow.dao.ExecuteEntityDao;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public ExecuteEntity findById(String s) {
        return executeEntityDao.findById(s);
    }

    public List<ExecuteEntity> findByParentId(String parentId) {
        return executeEntityDao.findByParentId(parentId);
    }
}
