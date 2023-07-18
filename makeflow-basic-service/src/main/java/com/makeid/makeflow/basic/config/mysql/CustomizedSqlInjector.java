package com.makeid.makeflow.basic.config.mysql;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义方法SQL注入器
 *
 * @author zhoujingbo
 * @version 1.0
 * @date 2022/12/22
 */
public class CustomizedSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留mybatis plus自带方法，
     * 可以先获取super.getMethodList()，再添加add
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertBatchMethod());
        methodList.add(new InsertOrUpdateBath());
        return methodList;
    }

}
