package com.makeid.makeflow.workflow.process.activity;

import com.makeid.makeflow.workflow.process.ProcessElementImpl;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.process.PvmTransition;
import com.makeid.makeflow.workflow.process.TransitionImpl;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description  所有节点的父类
 * @create 2023-06-12
 */
public abstract class CoreActivity extends ProcessElementImpl {



    public abstract CoreActivity findActivity(String codeId);




}
