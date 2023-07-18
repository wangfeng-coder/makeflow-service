package com.makeid.makeflow.template.dao.mysql.mapper;

import com.makeid.makeflow.basic.dao.impl.mysql.BatchMapper;
import com.makeid.makeflow.template.entity.impl.FlowProcessTemplateEntityImpl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-11
 */
@Mapper
public interface FlowProcessTemplateMapper extends BatchMapper<FlowProcessTemplateEntityImpl> {

}
