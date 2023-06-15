package com.makeid.makeflow.workflow.dao;

import com.makeid.makeflow.workflow.entity.ExecuteEntity;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public interface ExecuteEntityDao extends BaseDao<ExecuteEntity>{
    List<ExecuteEntity> findByParentId(String parentId);
}
