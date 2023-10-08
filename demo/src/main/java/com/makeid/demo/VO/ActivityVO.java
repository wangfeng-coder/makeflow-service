package com.makeid.demo.VO;

import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.vo.TaskVO;

import java.util.Date;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-10-07
 */
public class ActivityVO {

    private Date createTime;

    private Date completeTime;

    private String status;

    private String title;

    private List<TaskEntity> taskList;
}
