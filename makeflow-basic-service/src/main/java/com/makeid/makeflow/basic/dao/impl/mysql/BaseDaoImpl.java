package com.makeid.makeflow.basic.dao.impl.mysql;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.makeid.makeflow.basic.dao.BaseDao;
import com.makeid.makeflow.basic.entity.Entity;
import com.makeid.makeflow.basic.utils.SnowflakeIdGenerator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description mysql
 * @create 2023-07-12
 */
public abstract class BaseDaoImpl<T extends Entity,M extends BatchMapper<T>> implements BaseDao<T> {

    private static SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1,1);

    protected abstract M getMapper();

    @Override
    public void save(T t) {
        save(Collections.singletonList(t));
    }

    @Override
    public void save(List<T> collections) {
        if(CollectionUtils.isEmpty(collections)) {
            return;
        }
        getMapper().insertOrUpdateBath(collections);
    }


    @Override
    public T create(String creator) {
        T t = create();
        t.setCreator(creator);
        t.setUpdator(creator);
        return t;
    }

    public T create() {
        T t = doCreate();
        fillBasicProperty(t);
        return t;
    }



    @Override
    public T findById(Long id) {
        return getMapper().selectById(id);
    }

    protected void fillBasicProperty(T t) {
        if (Objects.isNull(t.getId())) {
            t.setId(generateId());
        }
        t.setUpdateTime(new Date());
        t.setCreateTime(new Date());
    }

    private long generateId() {
        long l = snowflakeIdGenerator.nextId();
        return l;
    }

}
