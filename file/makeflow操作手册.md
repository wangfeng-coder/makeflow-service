- 引入流程引擎依赖

```xml
        <dependency>
            <groupId>com.makeid.makeflow</groupId>
            <artifactId>makeflow-service-Application</artifactId>
            <version>1.0</version>
        </dependency>


```

- 引入流程引擎需要的驱动（mysql,mongodb二选一）



```xml
        <!-- MySQL驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
            <scope>provided</scope>
        </dependency>
     <!--   <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongo-java-driver</artifactId>
            <version>${mongo.version}</version>
            <scope>provided</scope>
        </dependency>
-->
```

- 在对应启动类或者配置类上加入@ImportMakeFlow注解 启动流程引擎容器

```java
@ImportMakeFlow
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
```

- 根据实际情况 在yaml中 完善流程引擎的配置参数如数据库数据源，redis相关配置   

```yml
# MongoDB
makeflow:
  data:
    dataType: mysql
    mongo:
      replicaSet: 127.0.0.1:27017
      dbname: makeflow
      connectionsPerHost: 200
      threadsAllowedToBlockForConnectionMultiplier: 10
      maxWaitTime: 4000
      connectTimeout: 4000
      socketTimeout: 5000
      autoConnectRetry: true
      readPreference: primaryPreferred
      username: root
      password: root
      authdb: admin
    mysql:
      druid-data-source:
        username: root
        password: root
        url: jdbc:mysql://127.0.0.1:3306/make_flow?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true&useSSL=false&allowPublicKeyRetrieval=true
        driver-class-name: com.mysql.cj.jdbc.Driver
        type: com.alibaba.druid.pool.DruidDataSource
        initialSize: 5
        minIdle: 5
        maxActive: 20
        maxWait: 60000
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
  redis:
    server:
      # 单机：stand-alone
      host: 127.0.0.1
      password: 123456
      port: 6379
      database: 0
      type: stand-alone
      #     哨兵：sentinel ；主从： 或者 master-slave  集群：cluster
      #        master: mymaster
      #        nodes: 172.16.39.18:26376,172.16.39.18:26377,172.16.39.18:26378
      #        password: csp172@eb5g
      #        database: 0
      #        type: sentinel
      #     集群：cluster
      #    nodes: 172.16.39.18:26376,172.16.39.18:26377,172.16.39.18:26378
      #    password: csp172@eb5g
      #    database: 0
      #    type: cluster
      #获取锁最长等待时间,单位秒
      waitTime: 10
      #锁过期最长时间，单位秒
      leaseTime: 10
```

- 通过门面MakeFlowApplication获取对应管理流程的对象

```java
Long s = MakeFlowApplication.getWorkFlowService().submitFlow(submitVO.getCodeId(), submitVO.getFlowInstId(), submitVO.getUserId(), submitVO.getVariables());
```

- 可自主实现FlowEventListener 并监听自己想关心的事件

```java
package com.makeid.demo.listener;

import com.makeid.makeflow.workflow.delegate.DelegateExecuteReader;
import com.makeid.makeflow.workflow.delegate.DelegateTaskReader;
import com.makeid.makeflow.workflow.event.FlowEventListener;
import com.makeid.makeflow.workflow.event.ProcessStartedEvent;
import com.makeid.makeflow.workflow.event.TaskDoneEvent;
import com.makeid.makeflow.workflow.event.TaskRunningEvent;
import com.makeid.makeflow.workflow.eventbus.FlowSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-demo
 * @description
 * @create 2023-10-09
 */
@Slf4j
@Component
public class TaskListener implements FlowEventListener {

    @FlowSubscribe(sync = false)
    public void business1(TaskRunningEvent taskRunningEvent) {
        List<? extends DelegateTaskReader> data = taskRunningEvent.getData();
        data.stream().forEach(delegateTaskReader -> {
            log.info("监听器收到  任务已经运行【{}】",delegateTaskReader.getName());
        });
    }

    @FlowSubscribe(sync = false)
    public void business1(TaskDoneEvent taskDoneEvent) {
        List<? extends DelegateTaskReader> data = taskDoneEvent.getData();
        data.stream().forEach(delegateTaskReader -> {
            log.info("监听器收到 任务已经完成【{}】 处理人【{}】",delegateTaskReader.getName(),delegateTaskReader.getHandler());
        });
    }
}

```

- 实现源码

```url
https://github.com/wangfeng-coder/makeflow-service.git
```

- 实现demo

```url
 https://github.com/wangfeng-coder/makeflow-demo.git
```


