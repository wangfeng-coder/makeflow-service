package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.template.flow.translate.PeopleResolver;
import com.makeid.makeflow.workflow.process.PvmScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-19
 */
@Component
public class RolePersonHolderCollector extends AbstractPersonHolderCollector{

    //注入获取角色的对象

    @Override
    public boolean match(String type) {
        return PeopleTypeEnum.ROLE.type.equals(type);
    }

    @Override
    public List<String> doCollect(PeopleHolder peopleHolder, PvmScope pvmScope) {
        //TODO 通过角色id获取具体人员集合
        return Collections.emptyList();
    }
}
