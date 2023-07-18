package com.makeid.makeflow.workflow.dao;

import com.makeid.makeflow.basic.dao.BaseDao;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public interface ExecuteEntityDao<T extends ExecuteEntity> extends BaseDao<T> {
    List<? extends ExecuteEntity> findByParentId(String parentId);

    List<? extends ExecuteEntity> findByIds(List<String> executionIds);
}
