package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.process.PvmScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        Set<String> userIds = new LinkedHashSet<>();
        for (PeopleHolder peopleHolder : peopleHolders) {
            for (PersonHolderCollector personHolderCollector : personHolderCollectorList) {
                List<String> collect = personHolderCollector.collect(peopleHolder, pvmScope);
                userIds.addAll(collect);
            }
        }
        return new ArrayList<>(userIds);
    }
}
