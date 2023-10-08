package com.makeid.makeflow.workflow.cmd;


import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.workflow.constants.ActivityTypeBehaviorEnum;
import com.makeid.makeflow.workflow.constants.ErrCodeEnum;
import com.makeid.makeflow.workflow.constants.FlowMapParamKeys;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.exception.TaskException;
import com.makeid.makeflow.workflow.interceptor.Command;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.TransitionImpl;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.apache.commons.lang3.StringUtils;


import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractCommand<T> implements Command<T>, CheckCommand,
		LockCommand, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Map<String, Object> variables;


	
	public AbstractCommand() {
		// TODO Auto-generated constructor stub
	}

	protected AbstractCommand(Map<String, Object> variables) {
		this.variables = variables;
	}


	protected Map<String, Object> getVariables() {
		if (this.variables == null) {
			this.variables = new HashMap<>();
		}
		return this.variables;
	}

	@Override
	public Long getProcessInstanceId() {
		return (Long) this.getVariables().get(FlowMapParamKeys.FLOW_INST_ID_KEY);
	}
	
	@Override
	public String getOpLockKey() {
		return (String) this.getVariables().get(FlowMapParamKeys.FLOW_OP_LOCK_KEY);
	}




	protected void validateTaskHandler(String handler, TaskEntity...taskEntities) {
		for (TaskEntity taskEntity : taskEntities) {
			if(!StringUtils.equals(handler,taskEntity.getHandler())) {
				throw new TaskException(ErrCodeEnum.HANDLER_INCONSISTENT);
			}
		}
	}

	protected void validateTaskDoingStatus(TaskEntity ...taskEntities) {
		for (TaskEntity taskEntity : taskEntities) {
			if(!StringUtils.equals(taskEntity.getStatus(), TaskStatusEnum.DOING.status)) {
				throw new TaskException(ErrCodeEnum.TASK_STATUS_NOT_DOING);
			}
		}
	}

	protected List<ActivityEntity> findActivities(List<TaskEntity> taskEntities) {
		List<Long> activityIds = taskEntities.stream()
				.map(TaskEntity::getActivityId)
				.distinct()
				.collect(Collectors.toList());
		List<ActivityEntity> activityEntities =  Context.getActivityService().findByIds(activityIds);
		return activityEntities;
	}

	protected ActivityEntity findActivity(Long activityId) {
		return Context.getActivityService().findById(activityId);
	}

	protected TaskEntity findTaskEntity(Long taskId) {
		return Context.getTaskService().findTaskById(taskId);
	}

	protected List<ExecuteEntity> findExecution(List<ActivityEntity> activityEntities) {
		List<Long> executionIds = activityEntities.stream().map(ActivityEntity::getExecutionId)
				.distinct()
				.collect(Collectors.toList());
		List<ExecuteEntity> executions = Context.getExecutionService().findByIds(executionIds);
		return executions;
	}


	protected ExecuteEntity findExecution(Long executionId) {
		return  Context.getExecutionService().findById(executionId);
	}

	protected ProcessInstanceExecution transferProcessInstanceExecution(ExecuteEntity executeEntity) {
		ProcessInstanceExecution processInstanceExecution = Context.getExecutionService().transferExecution(executeEntity);
		processInstanceExecution.addVariables(this.getVariables());
		return  processInstanceExecution;
	}

	protected ProcessInstanceExecution findProcessInstanceExecution(Long executionId) {
		ExecuteEntity execution = findExecution(executionId);
		return transferProcessInstanceExecution(execution);
	}

	/**
	 *  流程继续运行
	 * @param processInstanceExecution
	 */
	protected void continueRun(ProcessInstanceExecution processInstanceExecution) {
		processInstanceExecution.activate();
		processInstanceExecution.completedTask();
	}

	/**
	 * 自由设置下个节点
	 * @param currentActivity
	 * @param targetActivityCodeId
	 * @param processDefinition
	 */
	protected void freeLinkNextActivity(ActivityImpl currentActivity, String targetActivityCodeId, ProcessDefinitionImpl processDefinition) {
		//修改当前节点的连线 连到目标节点
		String codeId = "jump_" + UUID.randomUUID();
		TransitionImpl transition = new TransitionImpl(codeId, processDefinition);
		transition.setSourceCodeId(currentActivity.getCodeId());
		transition.setTargetCodeId(targetActivityCodeId);
		FlowNode flowNode = processDefinition.findFlowNode(targetActivityCodeId);
		if (ElementTypeEnum.ACTIVITYTYPE_START.getType().equals(flowNode.getElementType())){
			//退回到开始节点 特殊处理下 新建一个 restart activity
			ActivityImpl targetActivity = new ActivityImpl(targetActivityCodeId, processDefinition);
			targetActivity.setActivityType(ActivityTypeBehaviorEnum.RESTART.activityType);
			targetActivity.setActivityBehavior(ActivityTypeBehaviorEnum.RESTART.activityBehavior);
			transition.changeTargetActivity(targetActivity);
		}
		currentActivity.setOutGoingTransitions(Arrays.asList(transition));
	}

}
