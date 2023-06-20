package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.process.PvmScope;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-16
 */
public interface PersonHolderCollector {

    List<String> collect(PeopleHolder peopleHolder, PvmScope pvmScope);
}
