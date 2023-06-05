package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.workflow.behavior.ActivityBehavior;

import java.util.List;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
public class BaseActivityInst extends ProcessElementInst implements PvmActivity{
    @Override
    public PvmTransition findDefaultOutgoingTransition() {
        return null;
    }

    @Override
    public List<PvmTransition> findOutgoingTransitions() {
        return null;
    }

    @Override
    public ActivityBehavior findActivityBehavior() {
        return null;
    }


}
