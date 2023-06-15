package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.workflow.dao.ActivityDao;
import com.makeid.makeflow.workflow.dao.ExecuteEntityDao;
import com.makeid.makeflow.workflow.dao.impl.BaseDaoImpl;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Repository
public class ExecuteDaoImpl extends BaseDaoImpl<ExecuteEntity> implements ExecuteEntityDao {

    @Override
    public List<ExecuteEntity> findByParentId(String parentId) {
        return null;
    }
}