package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-19
 */
public class DepartmentPeopleResolver implements PeopleResolver{
    @Override
    public boolean match(String express) {
        return express.startsWith("D_");
    }

    @Override
    public List<PeopleHolder> resolve(String express) {
        String[] split = express.split(",");
        PeopleHolder peopleHolder = new PeopleHolder();
        peopleHolder.setFids(Arrays.asList(split));
        peopleHolder.setType(PeopleTypeEnum.DEPARTMENT.type);
        return Arrays.asList(peopleHolder);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
