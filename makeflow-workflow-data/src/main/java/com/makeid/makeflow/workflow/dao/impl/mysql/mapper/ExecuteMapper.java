package com.makeid.makeflow.workflow.dao.impl.mysql.mapper;

import com.makeid.makeflow.basic.dao.impl.mysql.BatchMapper;
import com.makeid.makeflow.workflow.entity.impl.ExecuteEntityImpl;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-17
 */
@Mapper
public interface ExecuteMapper extends BatchMapper<ExecuteEntityImpl> {
}
