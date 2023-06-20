package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import org.springframework.core.Ordered;

import java.util.*;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-19
 */
public class PeopleResolvers {

    private static final TreeSet<PeopleResolver> resolvers = new TreeSet<>(Comparator.comparingInt(Ordered::getOrder));

    static {
        resolvers.add(new DepartmentPeopleResolver());
        resolvers.add(new RolePeopleResolver());
        resolvers.add(new ElPeopleResolver());
        resolvers.add(new StringPeopleResolver());
    }

    /**
     * 编列解析器 得到结果直接返回
     * @param express
     * @return
     */
   public static List<PeopleHolder> resolve(String express) {
       for (PeopleResolver resolver : resolvers) {
           List<PeopleHolder> result = resolver.resolve(express);
           if(Objects.nonNull(result)) {
               return result;
           }
       }
       return Collections.emptyList();
    }
}
