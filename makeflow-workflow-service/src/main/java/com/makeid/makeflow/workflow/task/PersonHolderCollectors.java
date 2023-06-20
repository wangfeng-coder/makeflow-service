package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.process.PvmScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-19
 */
@Component
public class PersonHolderCollectors {


    private final List<PersonHolderCollector> personHolderCollectorList;

    public PersonHolderCollectors(List<PersonHolderCollector> personHolderCollectorList) {
        this.personHolderCollectorList = personHolderCollectorList;
    }

    public  List<String> collect(List<PeopleHolder> peopleHolders, PvmScope pvmScope) {
        List<String> userIds = new ArrayList<>();
        for (PeopleHolder peopleHolder : peopleHolders) {
            for (PersonHolderCollector personHolderCollector : personHolderCollectorList) {
                List<String> collect = personHolderCollector.collect(peopleHolder, pvmScope);
                userIds.addAll(collect);
            }
        }
        return userIds;
    }
}
