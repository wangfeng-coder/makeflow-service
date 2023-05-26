package com.makeid.makeflow.template.flow.template;

import com.makeid.makeflow.basic.entity.Entity;
import com.makeid.makeflow.template.flow.base.Element;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
*@program makeflow-service
*@description 流程定义模板
*@author feng_wf
*@create 2023-05-19
*/
public class ProcessTemplate extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表单模板的id
     */
    private String formTemplateId;// 模板Id

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
    @Transient
    private List<Element>  elements;

    private Integer version;// 版本号

    public String getFormTemplateId() {
        return formTemplateId;
    }

    public void setFormTemplateId(String formTemplateId) {
        this.formTemplateId = formTemplateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
