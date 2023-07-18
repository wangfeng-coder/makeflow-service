package com.makeid.makeflow.template.flow.translate;


import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

class DepartmentPeopleResolverTest  {



    @Test
    void resolve() {
        DepartmentPeopleResolver departmentPeopleResolver = new DepartmentPeopleResolver();
        List<PeopleHolder> resolve = departmentPeopleResolver.resolve("D_1,D_2,D_3,D_4");
        Assert.notEmpty(resolve,"部门解析结果不能为空");
        Assert.noNullElements(resolve,"collections不能有null");
        for (PeopleHolder peopleHolder : resolve) {
            Assert.notNull(peopleHolder);
            String type = peopleHolder.getType();
            Assert.notNull(peopleHolder.getFids());
            Assert.isTrue(PeopleTypeEnum.DEPARTMENT.type.equals(type),"类型不匹配");
        }

    }

    @Test
    void testString() {
        String s = new String("222");
        String substring = s.substring(0, 2);
        Assertions.assertTrue(substring.equals("sub_string"));
    }
}