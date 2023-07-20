package com.makeid.makeflow.workflow.delegate;


import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public interface DelegateActivityReader extends BasicDataReader {


    String getName();


    String getActivityType();



    Date getStartTime();


    Date getEndTime();


    String getStatus();


    Long getFlowInstId();



    Long getExecutionId();


    Long getParentId();


    Long getDefinitionId();

    Long getPreActivityId();


}
