package com.makeid.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

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
}