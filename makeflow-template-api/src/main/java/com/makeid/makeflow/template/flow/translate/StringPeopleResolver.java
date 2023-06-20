package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;

import java.util.Arrays;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 直接提取出字符串
 * @create 2023-06-19
 */
public class StringPeopleResolver implements PeopleResolver{
    @Override
    public List<PeopleHolder> resolve(String express) {
        PeopleHolder peopleHolder = new PeopleHolder();
        peopleHolder.setType(PeopleTypeEnum.PERSON.type);
        String[] split = express.split(",");
        peopleHolder.setFids(Arrays.asList(split));
        return Arrays.asList(peopleHolder);
    }

    /**
     * 优先级最低,都没有解析到再用它解析
     * @return
     */
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
