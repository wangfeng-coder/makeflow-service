package com.makeid.makeflow.template.entity.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.template.entity.FlowProcessTemplateEntity;
import lombok.Data;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
@Data
@TableName("makeflow_template")
public class FlowProcessTemplateEntityImpl extends BaseEntity implements FlowProcessTemplateEntity {

    private String name;

    private String flowProcessCodeId;

    private String state;

    private boolean max;

    private int version;

    private String dataType;

    private String templateData;


}
