package com.makeid.makeflow.template.service.third;

import com.makeid.makeflow.template.entity.FlowProcessTemplateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-11
 */
@Component
public class FlowTemplateServiceThird {

    @Autowired
    private com.makeid.makeflow.template.service.FlowProcessTemplateService flowProcessTemplateService;

    /**
     * 发布模板
     * @param xml
     * @param templateCodeId
     * @param templateName
     * @param userId
     * @return 数据库id
     */
    public Long publish(String xml, String templateCodeId, String templateName, String userId) {
        FlowProcessTemplateEntity publish = flowProcessTemplateService.publish(xml, templateCodeId, templateName, userId);
        return publish.getId();
    }




}
