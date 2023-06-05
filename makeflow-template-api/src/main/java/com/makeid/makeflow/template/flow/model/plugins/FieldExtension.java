package com.makeid.makeflow.template.flow.model.plugins;

import java.io.Serializable;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-26
*/
public class FieldExtension implements Serializable {

    protected String fieldName;

    protected Object value;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
