package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mongo.BaseDaoImpl;
import com.makeid.makeflow.workflow.dao.FlowInstEntityDao;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.impl.FlowInstEntityImpl;
import org.springframework.stereotype.Repository;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Dao
public class FlowInstDaoImpl extends BaseDaoImpl<FlowInstEntity> implements FlowInstEntityDao<FlowInstEntity> {

    @Override
    public FlowInstEntity doCreate() {
        FlowInstEntityImpl flowInstEntity = new FlowInstEntityImpl();
        return flowInstEntity;
    }
}
