package com.makeid.makeflow.template.dao;

import com.makeid.makeflow.basic.dao.BaseDao;
import com.makeid.makeflow.template.entity.FlowProcessTemplateEntity;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public interface FlowProcessTemplateDao<T extends FlowProcessTemplateEntity> extends BaseDao<T> {
    FlowProcessTemplateEntity findLatestByCodeId(String templateCodeId);

}
