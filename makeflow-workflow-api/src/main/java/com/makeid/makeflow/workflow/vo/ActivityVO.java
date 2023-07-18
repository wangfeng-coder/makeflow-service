package com.makeid.makeflow.workflow.vo;

import com.makeid.makeflow.workflow.entity.ActivityEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-10
 */
@Getter
@Setter
public class ActivityVO {

    private String name;

    private String status;

    private String preActivityId;

    private List<TaskVO> taskVOList;


    public static ActivityVO from(ActivityEntity activity,List<TaskVO> taskVOList) {
        ActivityVO activityVO = new ActivityVO();
        activityVO.setName(activity.getName());
        activityVO.setStatus(activity.getStatus());
        activityVO.setPreActivityId(activity.getPreActivityId());
        activityVO.setTaskVOList(taskVOList);
        return activityVO;
    }
}
