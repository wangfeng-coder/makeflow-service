package com.makeid.makeflow.workflow.vo;

import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import lombok.Builder;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-10
 */
@Setter
@Accessors(chain = true)
public class FlowDetailBuilder {

    private FlowInstEntity flowInst;

    private List<ActivityEntity> activityEntityList;

    private List<TaskEntity> taskEntityList;


    public FlowDetailVO buildFlowDetailVO() {
        Assert.notNull(flowInst);
        Assert.notNull(activityEntityList);
        Assert.notNull(taskEntityList);
        Map<String, List<TaskVO>> taskMap = taskEntityList.stream()
                .map(TaskVO::from)
                .collect(Collectors.groupingBy(TaskVO::getActivitId));
        List<ActivityVO> activityVOList = activityEntityList.stream()
                .map(activity -> ActivityVO.from(activity, taskMap.get(activity.getId())))
                .collect(Collectors.toList());
        FlowDetailVO flowDetailVO = new FlowDetailVO();
        flowDetailVO.setApplyTime(flowInst.getApplyTime());
        flowDetailVO.setCreator(flowInst.getCreator());
        flowDetailVO.setCreateTime(flowInst.getCreateTime());
        flowDetailVO.setActivityVOList(activityVOList);
        return flowDetailVO;
    }
}
