package com.makeid.makeflow.workflow.event;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

@Component
public class EventRegister{

    private static final Logger log = LoggerFactory
            .getLogger(EventRegister.class);

    private static EventBus syncEventBus;
    private static AsyncEventBus asyncEventBus;

    @Autowired
    private List<FlowEventListener> eventListenerList;

    // 在使用构造器注入的时候, 需要注意避免循环依赖问题, setter 不会
    // spring 中的循环依赖问题的解决只是在单例模式下
    @Autowired
    public EventRegister(Executor AsyncEventBusExecutor) {
        syncEventBus = new EventBus("流程引擎event同步消息总线");
        asyncEventBus = new AsyncEventBus(AsyncEventBusExecutor,
                (exception, context) -> {
                    log.error("context:{},exception:", JSONObject.toJSONString(context), exception);
                });
    }

    public void register(Object subscriber, boolean async) {
        if (async) {
            asyncEventBus.register(subscriber);
        } else {
            syncEventBus.register(subscriber);
        }
    }

    public void register(Object subscriber) {
        asyncEventBus.register(subscriber);
        syncEventBus.register(subscriber);
    }

    public static void post(Object event) {
        post(event, true); // 异步
    }

    public static void post(Object event, boolean async) {
        if (async) {
            asyncEventBus.post(event);
        } else {
            syncEventBus.post(event);
        }
    }

    @PostConstruct
    public void registerListeners() {
        eventListenerList.forEach(this::register);
    }

    public void test(){

        Class<? extends EventBus> aClass = syncEventBus.getClass();
        try {
            Field subscribers = aClass.getDeclaredField("subscribers");
            subscribers.setAccessible(true);
            Object o = subscribers.get(syncEventBus);
            Class<?> aClass1 = o.getClass();
            Field subscribers1 = aClass1.getDeclaredField("subscribers");
            subscribers1.setAccessible(true);
            Map map = (ConcurrentMap<Class<?>, CopyOnWriteArraySet<?>>)subscribers1.get(o);

            System.out.println();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
