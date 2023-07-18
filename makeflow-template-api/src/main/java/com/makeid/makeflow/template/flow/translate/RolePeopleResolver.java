package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;

import java.util.Arrays;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 解析角色的id,匹配的是DTS现有逻辑
 * @create 2023-06-19
 */
public class RolePeopleResolver implements PeopleResolver {
    @Override
    public boolean match(String express) {
        return express.startsWith("R_");
    }

    @Override
    public List<PeopleHolder> resolve(String express) {
        boolean isRole = express.startsWith("R_");
        if(isRole) {
            String[] split = express.split(",");
            PeopleHolder peopleHolder = new PeopleHolder();
            peopleHolder.setFids(Arrays.asList(split));
            peopleHolder.setType(PeopleTypeEnum.ROLE.type);
            return Arrays.asList(peopleHolder);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
