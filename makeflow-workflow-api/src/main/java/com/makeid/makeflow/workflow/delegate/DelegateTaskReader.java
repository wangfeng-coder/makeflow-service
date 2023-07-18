package com.makeid.makeflow.workflow.delegate;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public interface DelegateTaskReader extends BasicDataReader{

    String getName();


    /***
     * 处理人
     * @return
     */
    String getHandler();


    /**
     * 任务类型
     * @return
     */
    String getTaskType();


    /**
     * 完成时间
     * @return
     */
    Date getCompleteTime();



    String getStatus();

    /**
     * 任务状态
     */
    void setStatus(String status);


    String getFlowInstId();


    String getActivityCodeId();


    String getActivityId();

    String getOpinion();

}
