package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import lombok.Getter;
import lombok.Setter;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Getter
@Setter
public class StartActivityInst extends BaseActivityInst implements PvmActivity{


    private StartActivity startActivity;

    public static StartActivityInst transfer(StartActivity startActivity) {
        StartActivityInst startActivityInst = new StartActivityInst();
        startActivityInst.setStartActivity(startActivity);
        return startActivityInst;
    }
}
