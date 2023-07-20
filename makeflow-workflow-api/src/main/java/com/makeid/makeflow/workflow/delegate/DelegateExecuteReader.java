package com.makeid.makeflow.workflow.delegate;


import java.util.Date;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public interface DelegateExecuteReader extends BasicDataReader{

    Long getFlowInstId();

    Long getParentId();

    String getStatus();

    Date getStartTime();

    Date getEndTime();

    String getActivityCodeId();

    Long getActivityId();

    Map<String,Object> getVariables();


    Long getDefinitionId();
}
