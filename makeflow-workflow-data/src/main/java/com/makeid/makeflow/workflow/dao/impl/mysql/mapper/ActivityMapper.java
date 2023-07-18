package com.makeid.makeflow.workflow.dao.impl.mysql.mapper;

import com.makeid.makeflow.basic.dao.impl.mysql.BatchMapper;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.impl.ActivityEntityImpl;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-14
 */
@Mapper
public interface ActivityMapper extends BatchMapper<ActivityEntityImpl> {
}
