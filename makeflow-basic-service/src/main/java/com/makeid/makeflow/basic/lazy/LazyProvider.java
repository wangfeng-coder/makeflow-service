package com.makeid.makeflow.basic.lazy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-07
 */
public class LazyProvider {

    public static  <T> T provide(String id,Class[] argumentTypes, Object[] arguments, Function<String,T> supplier, Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            T t;
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if(Objects.isNull(t)) {
                    t = supplier.apply(id);
                }
                return  method.invoke(t,objects);
            }
        });
        if (Objects.isNull(arguments)) {
            return (T)enhancer.create();
        }
        return (T)enhancer.create(argumentTypes,arguments);
    }

    /**
     * 无参构造
     * @param id
     * @param supplier
     * @param clazz
     * @return
     * @param <T>
     */
    public static  <T> T provide(String id, Function<String,T> supplier, Class<T> clazz) {
        return provide(id,null,null,supplier,clazz);
    }

}
