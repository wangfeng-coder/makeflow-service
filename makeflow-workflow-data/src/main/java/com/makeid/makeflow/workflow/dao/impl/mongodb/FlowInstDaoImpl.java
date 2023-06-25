package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.workflow.dao.FlowInstEntityDao;
import com.makeid.makeflow.workflow.dao.impl.BaseDaoImpl;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.impl.ExecuteEntityImpl;
import com.makeid.makeflow.workflow.entity.impl.FlowInstEntityImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Repository
public class FlowInstDaoImpl extends BaseDaoImpl<FlowInstEntity> implements FlowInstEntityDao {

    @Override
    public FlowInstEntity create() {
        FlowInstEntityImpl flowInstEntity = new FlowInstEntityImpl();
        fillBasicProperty(flowInstEntity);
        return flowInstEntity;
    }
}
