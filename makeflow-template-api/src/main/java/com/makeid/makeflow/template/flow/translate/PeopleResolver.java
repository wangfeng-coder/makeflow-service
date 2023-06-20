package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 从xml字符中转换成对应PeopleHolder
 * @create 2023-06-19
 */
public interface PeopleResolver extends Ordered {

    List<PeopleHolder> resolve(String express);
}
