package com.makeid.makeflow.template.service;

import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 流程模板相关信息获取
 * @create 2023-05-29
 */
public interface FlowProcessTemplateService {


    FlowProcessTemplate getFlowProcessDefinition(String processId);


}
