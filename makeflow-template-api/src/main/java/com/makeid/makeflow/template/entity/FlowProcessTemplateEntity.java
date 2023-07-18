package com.makeid.makeflow.template.entity;

import com.makeid.makeflow.basic.entity.Entity;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
@Document(collection = "makeflow_flowTemplate")
@CompoundIndexes({@CompoundIndex(name = "flowProcessCodeId",def = "{'flowProcessCodeId':1}")})
public interface FlowProcessTemplateEntity extends Entity {

    String getFlowProcessCodeId();

    String getName();

    void setName(String name);

    void setFlowProcessCodeId(String flowProcessCodeId);

    String getState();

    void setState(String state);

    boolean isMax();

    void setMax(boolean max);


    void setVersion(int version);

    int getVersion();
    String getDataType();

    void setDataType(String dataType);

    String getTemplateData();

    void setTemplateData(String templateData);


}
