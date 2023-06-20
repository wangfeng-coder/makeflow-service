package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.process.PvmScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 来自于el表达式的人
 * @create 2023-06-16
 */
@Component
public class ELPersonHolderCollector extends AbstractPersonHolderCollector{

    private final String type = PeopleTypeEnum.EL.type;

    @Override
    public boolean match(String type) {
        return this.type.equals(type);
    }

    @Override
    public List<String> doCollect(PeopleHolder peopleHolder, PvmScope pvmScope) {
        //得到得是el表达式得参数名集合
        List<String> fids = peopleHolder.getFids();
        Map<String, Object> variables = pvmScope.getVariables();
        List result = fids.stream()
                .map(variables::get)
                .filter(Objects::nonNull)
                .filter(v -> v instanceof String)
                .collect(Collectors.toList());
        return (List<String>) result;
    }
}
