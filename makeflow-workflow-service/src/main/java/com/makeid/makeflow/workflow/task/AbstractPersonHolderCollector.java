package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.process.PvmScope;

import java.util.Collections;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-16
 */
public abstract class AbstractPersonHolderCollector implements PersonHolderCollector{

    public abstract boolean match(String type);

    @Override
    public final List<String> collect(PeopleHolder peopleHolder, PvmScope pvmScope) {
        if(!match(peopleHolder.getType())) {
            return Collections.emptyList();
        }
        return doCollect(peopleHolder, pvmScope);
    }

    public abstract   List<String> doCollect(PeopleHolder peopleHolder,PvmScope pvmScope) ;
}
