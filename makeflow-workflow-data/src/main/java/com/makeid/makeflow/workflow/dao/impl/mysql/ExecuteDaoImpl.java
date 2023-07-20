package com.makeid.makeflow.workflow.dao.impl.mysql;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mysql.BaseDaoImpl;
import com.makeid.makeflow.workflow.dao.ExecuteEntityDao;
import com.makeid.makeflow.workflow.dao.impl.mysql.mapper.ActivityMapper;
import com.makeid.makeflow.workflow.dao.impl.mysql.mapper.ExecuteMapper;
import com.makeid.makeflow.workflow.entity.impl.ExecuteEntityImpl;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-17
 */
@Dao
public class ExecuteDaoImpl extends BaseDaoImpl<ExecuteEntityImpl, ExecuteMapper> implements ExecuteEntityDao<ExecuteEntityImpl> {

    @Resource
    private ExecuteMapper mapper;

    @Override
    public ExecuteEntityImpl doCreate() {
        ExecuteEntityImpl executeEntity = new ExecuteEntityImpl();
        return executeEntity;
    }

    @Override
    public List<ExecuteEntityImpl> findByParentId(Long parentId) {
        List<ExecuteEntityImpl> executeEntities = mapper.selectList(Wrappers.lambdaQuery(ExecuteEntityImpl.class)
                .eq(ExecuteEntityImpl::getParentId, parentId));
        return executeEntities;
    }

    @Override
    public List<ExecuteEntityImpl> findByIds(List<Long> executionIds) {
        return mapper.selectBatchIds(executionIds);
    }

    @Override
    protected ExecuteMapper getMapper() {
        return mapper;
    }
}
