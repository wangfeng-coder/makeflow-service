package com.makeid.makeflow.template.dao.mysql;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mysql.BaseDaoImpl;
import com.makeid.makeflow.template.dao.FlowProcessTemplateDao;
import com.makeid.makeflow.template.dao.mysql.mapper.FlowProcessTemplateMapper;
import com.makeid.makeflow.template.entity.FlowProcessTemplateEntity;
import com.makeid.makeflow.template.entity.impl.FlowProcessTemplateEntityImpl;

import javax.annotation.Resource;


/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
@Dao
public class FlowProcessTemplateDaoImpl extends BaseDaoImpl<FlowProcessTemplateEntityImpl,FlowProcessTemplateMapper> implements FlowProcessTemplateDao<FlowProcessTemplateEntityImpl> {

    @Resource
    private FlowProcessTemplateMapper mapper;


    @Override
    public FlowProcessTemplateEntity findLatestByCodeId(String templateCodeId) {
        return mapper.selectOne(Wrappers.lambdaQuery(FlowProcessTemplateEntityImpl.class)
                .eq(FlowProcessTemplateEntityImpl::getFlowProcessCodeId,templateCodeId)
                .eq(FlowProcessTemplateEntityImpl::isMax,true));
    }

    @Override
    public FlowProcessTemplateEntityImpl create() {
        FlowProcessTemplateEntityImpl flowProcessTemplateEntity = new FlowProcessTemplateEntityImpl();
        fillBasicProperty(flowProcessTemplateEntity);
        return flowProcessTemplateEntity;
    }

    @Override
    protected FlowProcessTemplateMapper getMapper() {
        return mapper;
    }
}
