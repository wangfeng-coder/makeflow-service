package com.makeid.makeflow.template.dao.mongo;

import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mongo.BaseDaoImpl;
import com.makeid.makeflow.template.dao.FlowProcessTemplateDao;
import com.makeid.makeflow.template.entity.FlowProcessTemplateEntity;
import com.makeid.makeflow.template.entity.impl.FlowProcessTemplateEntityImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
@Dao
public class FlowProcessTemplateDaoImpl extends BaseDaoImpl<FlowProcessTemplateEntity> implements FlowProcessTemplateDao<FlowProcessTemplateEntity> {
    @Override
    public FlowProcessTemplateEntity findLatestByCodeId(String templateCodeId) {
        Query query= Query.query(Criteria.where("flowProcessCodeId").is(templateCodeId).and("max").is(true))
                .with(Sort.by(Sort.Direction.DESC, "createTime"));
        FlowProcessTemplateEntity one = mongoTemplate.findOne(query, FlowProcessTemplateEntityImpl.class);
        return one;
    }

    @Override
    public FlowProcessTemplateEntity create() {
        FlowProcessTemplateEntityImpl flowProcessTemplateEntity = new FlowProcessTemplateEntityImpl();
        fillBasicProperty(flowProcessTemplateEntity);
        return flowProcessTemplateEntity;
    }
}
