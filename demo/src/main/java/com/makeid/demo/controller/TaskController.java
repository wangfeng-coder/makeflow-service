package com.makeid.demo.controller;

import com.makeid.demo.VO.Result;
import com.makeid.makeflow.MakeFlowApplication;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.vo.TaskVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-10-07
 */
@RestController
@RequestMapping("task")
public class TaskController {

    @GetMapping("listDoing")
    public Result<List<TaskEntity>> listDoing(String userId) {
        List<TaskEntity> taskEntities = MakeFlowApplication.getTaskServiceThird().listDoingTasks(userId);
        return Result.ok(taskEntities);
    }


}
