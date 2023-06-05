package com.makeid.makeflow.template.flow.model.plugins;

import com.makeid.makeflow.template.flow.model.base.Element;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-26
*/
public abstract class BasePlug  extends Element {

    /**
     * 流程模板模型id
     */
    protected String processId;


    /**
     * 模板中元素id
     */
    protected String codeId;

    /**
     * 是否启用
     */
    protected int status;

    /**
     * 插件类型
     * {@link PlugTypeEnum}
     */
    protected  String type;



}
