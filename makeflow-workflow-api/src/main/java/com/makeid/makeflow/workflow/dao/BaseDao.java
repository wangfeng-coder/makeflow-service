package com.makeid.makeflow.workflow.dao;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public interface BaseDao<T> {

    void save(T t);

    T create();

    T findById(String id);


}
