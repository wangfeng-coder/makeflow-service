package com.makeid.demo;

import com.makeid.makeflow.MakeFlowApplication;
import com.makeid.makeflow.workflow.dao.ExecuteEntityDao;
import com.makeid.makeflow.workflow.dao.impl.mysql.mapper.ExecuteMapper;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.entity.impl.ExecuteEntityImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.HashMap;

@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
@Slf4j
public class DemoServiveTest {

    @Autowired
    private DemoServive demoServive;
    @Test
    public void publish() {
        String publish = demoServive.publish();
        Assert.notNull(publish);
        log.info("发布成功【{}】",publish);
    }

    @Test
    public void submit() {
        String apply = demoServive.apply();
        log.info("提交流程成功【{}】",apply);
    }

    @Test
    public void save() {
        ExecuteEntityDao executeEntityDao = MakeFlowApplication.getFlowContext().getBean(ExecuteEntityDao.class);
        ExecuteMapper executeMapper = MakeFlowApplication.getFlowContext().getBean(ExecuteMapper.class);
        ExecuteEntityImpl o = (ExecuteEntityImpl)executeEntityDao.create();
        o.setVariables(new HashMap<>());
        o.setFlowInstId("1");
        o.setActivityCodeId("1");
        o.setActivityId("2");
        o.setDefinitionId("2");
        o.setStatus("1");
        int insert = executeMapper.insert(o);
        Assert.isTrue(insert>0);
    }

}