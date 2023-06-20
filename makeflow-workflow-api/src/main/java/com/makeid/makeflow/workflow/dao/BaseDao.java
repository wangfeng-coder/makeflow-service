package com.makeid.makeflow.workflow.dao;

import java.util.Collection;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public interface BaseDao<T> {

    void save(T t);

    void save(List<T> collections);

    T create();



    T create(String creator);

    T findById(String id);


}
