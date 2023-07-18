package com.makeid.makeflow.basic.dao.impl.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义批量插入或更新Mapper
 *
 * @author zhoujingbo
 * @version 1.0
 * @date 2022/12/22
 */
public interface BatchMapper<T> extends BaseMapper<T> {

    /**
     * 自定义批量插入
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int insertBatch(@Param("list") List<T> list);

    /**
     * 自定义批量新增或更新
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int insertOrUpdateBath(@Param("list") List<T> list);

}
