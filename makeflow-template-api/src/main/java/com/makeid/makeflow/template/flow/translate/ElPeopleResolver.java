package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.template.util.ElUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description el表达式解析出来
 * @create 2023-06-19
 */
public class ElPeopleResolver implements PeopleResolver {
    @Override
    public boolean match(String express) {
        return ElUtil.isELExpression(express);
    }

    @Override
    public List<PeopleHolder> resolve(String express) {
        if (ElUtil.isELExpression(express)) {
            ArrayList<PeopleHolder> peopleHolders = new ArrayList<>();
            PeopleHolder peopleHolder = new PeopleHolder();
            String variableKey = ElUtil.extractExpression(express);
            peopleHolder.setType(PeopleTypeEnum.EL.type);
            peopleHolder.setFids(Arrays.asList(variableKey));
            peopleHolders.add(peopleHolder);
            return peopleHolders;
        }
        return Collections.emptyList();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
