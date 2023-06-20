package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public class AbstractActivityBehavior implements ActivityBehavior{

    protected final JumpActivityBehavior jumpActivityBehavior = new JumpActivityBehavior();


    @Override
    public void execute(ActivityPvmExecution execution) {
        doExecute(execution);
    }

    protected void doExecute(ActivityPvmExecution execution) {
        leave(execution);
    }

    protected void leave(ActivityPvmExecution execution) {
        jumpActivityBehavior.performOutgoingBehavior(execution);
    }


   protected boolean isComplete(List<TaskEntity> taskInstList) {
        return taskInstList.stream().allMatch(taskInst -> TaskStatusEnum.DONE.status == taskInst.getStatus());
   }

    /**
     * 任务为运行
     * @param taskEntities
     */
    protected void runningAllTask(List<TaskEntity> taskEntities) {
        for (TaskEntity taskEntity : taskEntities) {
            taskEntity.setStatus(TaskStatusEnum.DOING.status);
        }
    }
}
