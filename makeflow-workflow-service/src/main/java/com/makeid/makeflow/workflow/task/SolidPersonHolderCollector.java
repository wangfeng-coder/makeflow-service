package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.process.PvmScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-19
 */
@Component
public class SolidPersonHolderCollector extends AbstractPersonHolderCollector{

    @Override
    public boolean match(String type) {
        return PeopleTypeEnum.PERSON.type.equals(type);
    }

    @Override
    public List<String> doCollect(PeopleHolder peopleHolder, PvmScope pvmScope) {
        return peopleHolder.getFids();
    }
}
