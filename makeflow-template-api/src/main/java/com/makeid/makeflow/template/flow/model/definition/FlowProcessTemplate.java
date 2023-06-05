package com.makeid.makeflow.template.flow.model.definition;

import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.template.flow.model.base.Element;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
*@program makeflow-service
*@description 流程定义模板
*@author feng_wf
*@create 2023-05-19
*/
@Setter
@Getter
public class FlowProcessTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表单模板的id
     */
    private String flowTemplateId;// 模板Id

    private String flowTemplateCodeId;

    /**
     * 模板名称
     */
    private String name;// 名称

    /**
     * 状态
     *  -1 删除 0草稿 1启用
     */
    private String state;

    /**
     * 模板中的各个元素
     */
    private List<Element>  elements;


    private Integer version;// 版本号


    public StartActivity findInitial() {
        Element start = elements.stream().filter(element -> element instanceof StartActivity)
                .findFirst()
                .get();
        return (StartActivity) start;
    }
}
