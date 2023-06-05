package com.makeid.makeflow.template.flow.model.settings;
/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-23
*/
public class ApprovalSettings {

    protected ReturnSetting returnSetting;

    /**
     * 审批人和上一个节点相同的时候，自动审批
     */
    protected boolean neighborAutoPass;

    /**
     * 没有审批人的时候 结束审批
     */
    protected boolean noApproverEndProcess;
}
