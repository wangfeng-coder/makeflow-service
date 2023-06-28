package com.makeid.makeflow.workflow.task;

import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-16
 */
public class TaskTypeMap {

    private static final Map<String,String> taskTypeMaps = new HashMap<>();

    static {
        taskTypeMaps.put(ElementTypeEnum.ACTIVITYTYPE_START.getType(),"start");
        taskTypeMaps.put("restart", "restart");
        taskTypeMaps.put(ElementTypeEnum.ACTIVITYTYPE_MULTIAPPROVAL.getType(), "approval");
    }

    public static String map(String elementType) {
        String taskType = taskTypeMaps.get(elementType);
        Assert.notNull(taskType);
        return taskType;
    }
}
