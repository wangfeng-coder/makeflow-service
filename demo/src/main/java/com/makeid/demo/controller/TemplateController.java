package com.makeid.demo.controller;

import com.makeid.demo.VO.PublishTemplateVO;
import com.makeid.demo.VO.Result;
import com.makeid.makeflow.MakeFlowApplication;
import com.makeid.makeflow.template.service.third.FlowTemplateServiceThird;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-10-07
 */

@RestController
@RequestMapping("template")
public class TemplateController {

    /**
     * 发布模板
     * @param publishTemplateVO
     * @return
     */
    @RequestMapping("publish")
    public Result<Long> publish(@RequestBody PublishTemplateVO publishTemplateVO) {
        FlowTemplateServiceThird flowProcessTemplateService = MakeFlowApplication.getFlowProcessTemplateService();
        Long publish = flowProcessTemplateService.publish(publishTemplateVO.getXml(), publishTemplateVO.getCodeId(), publishTemplateVO.getTitle(), publishTemplateVO.getUserId());
        return Result.ok(publish);
    }


}
