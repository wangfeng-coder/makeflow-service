package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.flow.constants.PeopleTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElPeopleResolverTest {

    @Test
    void resolve() {
        ElPeopleResolver elPeopleResolver = new ElPeopleResolver();
        List<PeopleHolder> resolve = elPeopleResolver.resolve("${王峰}");
        Assert.notEmpty(resolve);
        String type = resolve.get(0).getType();
        List<String> fids = resolve.get(0).getFids();
        Assert.isTrue(PeopleTypeEnum.EL.type.equals(type));
        Assert.isTrue(fids.size()==1);
        Assert.isTrue(fids.get(0).equals("王峰"));
    }
}