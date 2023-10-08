package com.makeid.makeflow.workflow.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 流程详情
 * @create 2023-07-10
 */
@Getter
@Setter
public class FlowDetailVO {

    private String creator;

    private Date createTime;

    private String apply;

    private Date applyTime;

    private String status;

    private List<ActivityVO> activityVOList;

}
