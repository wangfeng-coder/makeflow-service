package com.makeid.makeflow.template.service;

import com.makeid.makeflow.template.entity.FlowProcessTemplateEntity;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 流程模板相关信息获取
 * @create 2023-05-29
 */
public interface FlowProcessTemplateService {


    FlowProcessTemplate getFlowProcessDefinition(String processId);

    /**
     * 获取当前最新模板版本
     * @param codeId
     * @return
     */
    FlowProcessTemplate findFlowProcessTemplateLastly(String codeId);

    FlowProcessTemplateEntity publish(String xml, String templateCodeId, String templateName,String userId);
}
