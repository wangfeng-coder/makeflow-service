package com.makeid.makeflow.workflow.dao.impl.mysql;

import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mysql.BaseDaoImpl;
import com.makeid.makeflow.workflow.dao.FlowInstEntityDao;
import com.makeid.makeflow.workflow.dao.impl.mysql.mapper.FlowInstMapper;
import com.makeid.makeflow.workflow.entity.impl.FlowInstEntityImpl;

import javax.annotation.Resource;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-17
 */
@Dao
public class FlowInstDaoImpl extends BaseDaoImpl<FlowInstEntityImpl, FlowInstMapper> implements FlowInstEntityDao<FlowInstEntityImpl> {

    @Resource
    private FlowInstMapper mapper;

    @Override
    public FlowInstEntityImpl create() {
        FlowInstEntityImpl flowInstEntity = new FlowInstEntityImpl();
        fillBasicProperty(flowInstEntity);
        return flowInstEntity;
    }

    @Override
    protected FlowInstMapper getMapper() {
        return mapper;
    }
}
