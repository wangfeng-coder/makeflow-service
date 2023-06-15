package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.constants.TaskTypeEnum;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 开始节点的任务
 * @create 2023-06-07
 */
public class StartTaskInst extends TaskInst{

    public StartTaskInst() {
        this.taskType = TaskTypeEnum.START_TASK.taskType;
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
        this.setStatus(TaskStatusEnum.NOT_INIT.status);
    }
}
