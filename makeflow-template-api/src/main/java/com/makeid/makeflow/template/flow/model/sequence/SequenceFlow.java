package com.makeid.makeflow.template.flow.model.sequence;

import com.makeid.makeflow.template.flow.model.base.FlowElement;

/**
*@program makeflow-service
*@description 线
*@author feng_wf
*@create 2023-05-29
*/
public class SequenceFlow extends FlowElement {

    protected  String sourceCodeId;

    protected String targetCodeId;

    protected String conditionExpression;

    public String getSourceCodeId() {
        return sourceCodeId;
    }

    public void setSourceCodeId(String sourceCodeId) {
        this.sourceCodeId = sourceCodeId;
    }

    public String getTargetCodeId() {
        return targetCodeId;
    }

    public void setTargetCodeId(String targetCodeId) {
        this.targetCodeId = targetCodeId;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }
}
