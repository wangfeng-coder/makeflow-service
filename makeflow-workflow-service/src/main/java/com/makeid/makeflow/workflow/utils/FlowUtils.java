package com.makeid.makeflow.workflow.utils;

import com.alibaba.fastjson.JSONArray;
import com.makeid.makeflow.workflow.constants.MakeflowRedisConstants;
import com.yunzhijia.cloudflow.common.util.JedisUtil;
import com.yunzhijia.cloudflow.form.entity.FormInstance;
import com.yunzhijia.cloudflow.form.vo.output.ShareInfoVo;
import com.yunzhijia.cloudflow.template.constant.FlowParameter;
import com.yunzhijia.cloudflow.template.constant.FormParameter;
import com.yunzhijia.cloudflow.template.constant.flow.FlowType;
import com.yunzhijia.cloudflow.template.flow.authority.AllowFlowUpdateSettings;
import com.yunzhijia.cloudflow.template.flow.parallelapprove.DispatchAction;
import com.yunzhijia.cloudflow.template.flow.parallelapprove.ParallelApprove;
import com.yunzhijia.cloudflow.template.flow.sequense.BaseSequenseFlow;
import com.yunzhijia.cloudflow.template.flow.settings.OpinionSettings;
import com.yunzhijia.cloudflow.workflow.adapter.CloudflowFormRPCClientService;
import com.yunzhijia.cloudflow.workflow.adapter.CloudflowTemplateRPCClientService;
import com.yunzhijia.cloudflow.workflow.approver.command.DispatchRoleApproverOrgMapCommand;
import com.yunzhijia.cloudflow.workflow.constants.ActivityMapParamKeys;
import com.yunzhijia.cloudflow.workflow.constants.CloudflowConstants;
import com.yunzhijia.cloudflow.workflow.constants.MapParamKeys;
import com.yunzhijia.cloudflow.workflow.constants.URLConstants;
import com.yunzhijia.cloudflow.workflow.dao.FlowInstRelatedInfoDao;
import com.yunzhijia.cloudflow.workflow.entity.*;
import com.yunzhijia.cloudflow.workflow.enums.*;
import com.yunzhijia.cloudflow.workflow.service.impl.GroupOperationNameSettings;
import com.yunzhijia.cloudflow.workflow.util.autoApproval.AutoApprovalCmdProcess;
import com.yunzhijia.cloudflow.workflow.util.autoApproval.AutoApprovalContext;
import com.yunzhijia.cloudflow.workflow.vo.*;
import com.yunzhijia.cloudflow.workflow.vo.parallel.DispatchTypeEnum;
import com.yunzhijia.cloudflow.workflow.vo.startflow.StartFlowContext;
import com.yunzhijia.cloudflow.workflow.vo.subflow.StartSubFlowActionVo;
import com.yunzhijia.cloudflow.workflow.vo.subflow.SubflowAction;
import com.yunzhijia.platform.common.rpc.core.context.ContextUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class FlowUtils {


	public static boolean isSolidFlow(FlowInst flowInst) {
		return null!=flowInst?FlowType.FLOWTEMPLATE_TYPE_SOLID.equals(flowInst.getFlowTemplateType()):false;
	}

	public static boolean isFlowCanShare(FlowInst flowInst,FlowInstRelatedInfo info){
		if(null!=flowInst){
			//安全模式的不能分享，就算开启了分享功能
			if(flowInst.isSafeMode()){
				return false;
			}else if(null!=info){
				return info.isOpenShare();
			}
			return true;
		}
		return false;
	}
	/**
	 * 是否处理人
	 * 
	 * @param task
	 * @param openId
	 * @return
	 */
	public static boolean isTaskHandler(Task task, String openId) {
		if (task != null && openId != null && openId.equals(task.getHandler())) {
			return true;
		}
		return false;
	}

	/**
	 * 任务是否进行中
	 * 
	 * @param task
	 * @return
	 */
	public static boolean isTaskDoing(Task task) {
		if (task != null && task.getStatus() == TaskStatus.DOING)
			return true;
		return false;
	}

	/**
	 * 任务所在组是否处理中
	 * 
	 * @param group
	 * @return
	 */
	public static boolean isGroupDoing(Group group) {
		if (group == null || group.getGroupStatus() != ActivityStatus.DOING) {
			return false;
		}
		return true;
	}

	/**
	 * 是否开始节点
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isStartActivity(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		if (ActivityType.START.toString().equals(activityInst.getActivityType())
				|| ActivityType.RESTART.toString().equals(activityInst.getActivityType())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否用户节点
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isUserActivity(ActivityInst activityInst) {
		if (ActivityType.START.toString().equals(activityInst.getActivityType())
				|| ActivityType.RESTART.toString().equals(activityInst.getActivityType())
				|| ActivityType.FREE.toString().equals(activityInst.getActivityType())
				|| ActivityType.CUSTOMFREE.toString().equals(activityInst.getActivityType())
				|| ActivityType.SIGN.toString().equals(activityInst.getActivityType())
				|| ActivityType.USER.toString().equals(activityInst.getActivityType())
				|| ActivityType.ROBOT.toString().equals(activityInst.getActivityType())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否结束节点
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isEndActivity(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		if (ActivityType.END.toString().equals(activityInst.getActivityType())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否自由流
	 * 
	 * @param flowInst
	 * @return
	 */
	public static boolean isFreeFlow(FlowInst flowInst) {
		if (flowInst != null && FlowParameter.FLOWTEMPLATE_TYPE_FREE.equals(flowInst.getFlowTemplateType())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否自定义自由流--可定制会签、或签等节点
	 * @param flowInst
	 * @return
	 */
	public static boolean isCustomFreeFlow(FlowInst flowInst) {
		if (flowInst != null && CloudflowConstants.FLOWTEMPLATE_TYPE_CUSTOM_FREE.equals(flowInst.getFlowTemplateType())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否自由流 或者 自定义自由流
	 * @param flowInst
	 * @return
	 */
	public static boolean isFreeOrCustomFlow(FlowInst flowInst) {
		return isFreeFlow(flowInst) || isCustomFreeFlow(flowInst);
	}

	/**
	 * 是否开启自由退回
	 * 
	 * @param activityInst
	 *            节点实例
	 * @return
	 */
	public static boolean isOpenReturn(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		// 自由退回开关是否开启
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(CloudflowConstants.ACTIVITY_INSTANCE_CAN_RETURN_KEY);
			if (object != null) {
				return (boolean) object;
			}
		}
		return true;
	}


	/**
	 * 是否开启 编辑发文
	 * 
	 * @param activityInst
	 * @return
	 */
	// public static boolean isOpenDispatchEdit(ActivityInst activityInst) {
	// if(activityInst==null) return false;
	// Map<String, Object> detail = activityInst.getDetail();
	// if(detail!=null) {
	// Object object = detail.get(ActivityMapParamKeys.ACTIVITY_DISPATCH_EDIT);
	// if(object!=null) {
	// return (boolean)object;
	// }
	// }
	// return false;
	// }

	/**
	 * 是否开启 发文套红
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isOpenDispatchSetRed(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(ActivityMapParamKeys.ACTIVITY_DISPATCH_SET_RED);
			if (object != null) {
				return (boolean) object;
			}
		}
		return false;
	}

	/**
	 * 是否开启 发文盖章
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isOpenDispatchSeal(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(ActivityMapParamKeys.ACTIVITY_DISPATCH_SEAL);
			if (object != null) {
				return (boolean) object;
			}
		}
		return false;
	}
	
	/**
	 * 是否开启 在线OFFICE控件签章操作
	 * @param activityInst
	 * @return
	 */
	public static boolean isOpenOnlineOfficeSeal(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(ActivityMapParamKeys.ACTIVITY_ONLINEOFFICE_SEAL);
			if (object != null) {
				return (boolean) object;
			}
		}
		return false;
	}

	/**
	 * 是否发文相关节点(拥有发文操作的节点)
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isDispatchActivity(ActivityInst activityInst) {
		return isOpenDispatchSetRed(activityInst) || isOpenDispatchSeal(activityInst);
	}
	
	/**
	 * 是否拥有在线OFFICE控件签章操作节点
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isOnlineOfficeActivity(ActivityInst activityInst) {
		return isOpenOnlineOfficeSeal(activityInst);
	}
	
	/**
	 * 节点是否超时可以自动提交
	 * update by lhk 2020/06/23 新增批示意见登记不能自动提交
	 * @param activityInst
	 * @return
	 */
	public static boolean isActivityCanOvertimeCommit(ActivityInst activityInst) {
		if(isDispatchActivity(activityInst) || isOnlineOfficeActivity(activityInst)
				//新增如果是节点开启了征求意见回复完才能往下走的设置，也不让其自动同意
				||ActivityUtil.isOpenAllReply(activityInst)
				//新增如果是人工节点也不让其自动同意
				||ActivityUtil.isManualActivity(activityInst) || canCommentsRegister(activityInst) || FlowUtils.isNeedAutoFill(activityInst)
				||ActivityUtil.isActivityCanHandSign(activityInst) || ActivityUtil.isActivityCanFingerPrint(activityInst)) {
			return false;
		}
		if(ActivityUtil.activityOpenTimeOutAutoSubmitToNextEvenIfEdit(activityInst)){
			return true;
		}
		//如果没开启节点可编辑下，可超时自动审批，要去查询是否有编辑
		CloudflowTemplateRPCClientService service = SpringContextUtil.getBean(CloudflowTemplateRPCClientService.class);
		String flowTemplateId = activityInst.getFlowTemplateId();
		String activityCodeId = activityInst.getActivityCodeId();
		return !service.isActivityEditable(flowTemplateId, activityCodeId);
	}
	
	public static boolean isActivityGroupCanOvertimeCommit(ActivityInst activityInst,Group group ) {
		if(isDispatchActivity(activityInst) || isOnlineOfficeActivity(activityInst) || canCommentsRegister(activityInst) || FlowUtils.isNeedAutoFill(activityInst)) {
			return false;
		}
		if(ActivityUtil.activityOpenTimeOutAutoSubmitToNextEvenIfEdit(activityInst)){
			return true;
		}
		//如果没开启节点可编辑下，可超时自动审批，要去查询是否有编辑
		String groupCodeId = group.getCodeId();
		CloudflowTemplateRPCClientService service = SpringContextUtil.getBean(CloudflowTemplateRPCClientService.class);
		String flowTemplateId = activityInst.getFlowTemplateId();
		String activityCodeId = activityInst.getActivityCodeId();
		return !service.isActivityGroupEditable(flowTemplateId, activityCodeId, groupCodeId);
	}
	
	/**
	 * @Description:判断操作组是否开启了增加审批人选项开关
	 * @param activityInst
	 * @param groupId
	 * @return boolean
	 * @exception:
	 * @author: Administrator
	 * @time:2017年6月27日 下午2:20:47
	 */
	public static boolean isGroupAddApprover(ActivityInst activityInst, String groupId) {
		if (StringUtils.isBlank(groupId) || null == activityInst || isStartActivity(activityInst)) {
			return false;
		}
		List<Group> handleGroups = activityInst.getHandleGroups();
		Group taskGroup = null;
		// 获取对应的操作组
		if (CollectionUtils.isNotEmpty(handleGroups)) {
			for (Group group : handleGroups) {
				if (groupId.equals(group.getGroupId())) {
					taskGroup = group;
				}
			}
		}
		if (null != taskGroup) {
			List<String> operations = taskGroup.getSwitchOperations();
			// 如果该操作组的操作开关为空，返回false
			if (CollectionUtils.isEmpty(operations)) {
				return false;
			}
			if (operations.contains(CloudflowConstants.ACTIVITY_INSTANCE_GROUP_CAN_ADD_APPROVER)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Description:是否开启了指纹审批
	 * @param activityInst
	 * @return boolean
	 * @exception:
	 * @author: lusheng
	 * @time:2017年6月15日 上午10:42:37
	 */
	public static boolean isOpenFingerPrint(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(CloudflowConstants.ACTIVITY_INSTANCE_CAN_FINGER_PRINT);
			if (object != null) {
				return (boolean) object;
			}
		}
		return false;
	}

	/**
	 * @Description:是否开启不同意
	 * @param activityInst
	 * @return boolean
	 * @exception:
	 * @author: lusheng
	 * @time:2018年3月5日 下午5:00:47
	 */
	public static boolean isDisagreeOpen(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(CloudflowConstants.ACTIVITY_INSTANCE_CAN_DISAGREE);
			if (object != null) {
				return (boolean) object;
			}
		}
		return false;
	}

	/**
	 * 是否可以手写签名
	 * @param activityInst
	 * @return
	 */
	public static boolean isHandSign(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(CloudflowConstants.ACTIVITY_HAND_SIGN);
			if (object != null) {
				return (boolean) object;
			}
		}
		return false;
	}

	/**
	 * 是否发起人可以在完成节点修改流程
	 * @param activityInst
	 * @return
	 */
	public static boolean isFlowUpdate(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(CloudflowConstants.ACTIVITY_UPDATE_FLOW);
			if (object != null) {
				AllowFlowUpdateSettings allowFlowUpdateSettings = ((AllowFlowUpdateSettings) object);
				if(null !=allowFlowUpdateSettings){
					return allowFlowUpdateSettings.isInitiatorFlowUpdate();
				}
			}
		}
		return false;
	}

	/**
	 * 流程状态是否可用(删除、废弃、不同意视为不可用)
	 *
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowActive(FlowInst flowInst) {
		if (isFlowDeleted(flowInst) || isFlowAbandonned(flowInst) || isFlowDisagree(flowInst)) {
			return false;
		}
		return true;
	}

	/**
	 * 流程是否退回到开始节点
	 *
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowReturnedToStart(FlowInst flowInst) {
		if (flowInst != null && flowInst.getStatus() == FlowStatus.RETURNED) {
			return true;
		}
		return false;
	}

	/**
	 * 流程是否支持撤回退回
	 *
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowSupportRecallReturned(FlowInst flowInst) {
		if (flowInst != null && flowInst.isSupportRecallReturned()) {
			return true;
		}
		return false;
	}

	/**
	 * 流程是否在运行中
	 * 
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowRunning(FlowInst flowInst) {
		if (flowInst != null && flowInst.getStatus() == FlowStatus.RUNNING) {
			return true;
		}
		return false;
	}

	/**
	 * 流程是否已完成
	 * 
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowFinished(FlowInst flowInst) {
		if (flowInst != null && flowInst.getStatus() == FlowStatus.FINISH) {
			return true;
		}
		return false;
	}

	/**
	 * 流程是否废弃
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowAbandonned(FlowInst flowInst){
		if (flowInst != null && flowInst.getStatus() == FlowStatus.ABANDON) {
			return true;
		}
		return false;
	}
	/**
	 * 流程是否结束（包括流程审批通过与流程审批不通过）
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowEnded(FlowInst flowInst) {
		
		if(FlowStatus.FINISH.equals(flowInst.getStatus()) || FlowStatus.DISAGREE.equals(flowInst.getStatus())) return true;
		return false;
	}

	/**
	 * 流程是否已挂起
	 * 
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowSuspended(FlowInst flowInst) {
		if (flowInst != null && flowInst.getStatus() == FlowStatus.SUSPEND) {
			return true;
		}
		return false;
	}
	
	/**
	 * 流程暂存状态
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowUnSubmit(FlowInst flowInst){
		if (flowInst != null && flowInst.getStatus() == FlowStatus.UNSUBMIT) {
			return true;
		}
		return false;
	}

	/**
	 * 是否发起人
	 * 
	 * @param flowInst
	 * @param openId
	 * @return
	 */
	public static boolean isSponsor(FlowInst flowInst, String openId) {
		if (flowInst != null && openId != null && openId.equals(flowInst.getCreator())) {
			return true;
		}

		return false;
	}

	/**
	 * 是否会签
	 * 
	 * @param checkType
	 * @return
	 */
	public static boolean isJoinSignGroup(String checkType) {
		if (FlowParameter.CHECKTYPE_JOIN_SIGN.equals(checkType))
			return true;
		return false;
	}

	/**
	 * 是否逐签
	 * 
	 * @param checkType
	 * @return
	 */
	public static boolean isOrderSignGroup(String checkType) {
		if (FlowParameter.CHECKTYPE_ORDER_SIGN.equals(checkType))
			return true;
		return false;
	}

	/**
	 * 是否或签
	 * 
	 * @param checkType
	 * @return
	 */
	public static boolean isOrSignGroup(String checkType) {
		if (FlowParameter.CHECKTYPE_OR_SIGN.equals(checkType))
			return true;
		return false;
	}

	/**
	 * 按创建时间排序
	 * 
	 * @param taskVos
	 */
	public static void sortTaskListByCreateTime(List<TaskListVo> taskVos) {
		if (CollectionUtils.isEmpty(taskVos))
			return;
		Collections.sort(taskVos, new Comparator<TaskListVo>() {
			@Override
			public int compare(TaskListVo o1, TaskListVo o2) {
				if (o1 == null || o2 == null)
					return 1;
				if (o1.getCreateTime().getTime() - o2.getCreateTime().getTime() > 0) {
					return -1;
				}
				return 1;
			}
		});
	}

	/**
	 * 按创建时间排序
	 * 
	 * @param taskVos
	 */
	public static void sortWebTaskListByCreateTime(List<WebTaskListVo> taskVos,String direction) {
		if (CollectionUtils.isEmpty(taskVos))
			return;
		if(CloudflowConstants.ORDER_DIRECTION_ASC.equalsIgnoreCase(direction)) {
			//升序
			Collections.sort(taskVos, new Comparator<WebTaskListVo>() {
				@Override
				public int compare(WebTaskListVo o1, WebTaskListVo o2) {
					if (o1 == null || o2 == null)
						return 1;
					if (o1.getCreateTime().getTime() - o2.getCreateTime().getTime() < 0) {
						return -1;
					}
					return 1;
				}
			});
			return;
		}

		//降序
		Collections.sort(taskVos, new Comparator<WebTaskListVo>() {
			@Override
			public int compare(WebTaskListVo o1, WebTaskListVo o2) {
				if (o1 == null || o2 == null)
					return 1;
				if (o1.getCreateTime().getTime() - o2.getCreateTime().getTime() > 0) {
					return -1;
				}
				return 1;
			}
		});
	}

	/**
	 * 按创建时间对流程数据排序
	 * 
	 * @param flowInstVos
	 */
	public static void sortFlowByCreateTime(List<FlowInstVo> flowInstVos) {
		if (CollectionUtils.isEmpty(flowInstVos))
			return;
		Collections.sort(flowInstVos, new Comparator<FlowInstVo>() {
			@Override
			public int compare(FlowInstVo o1, FlowInstVo o2) {
				if (o1 == null || o2 == null)
					return 1;
				if (o1.getCreateTime().getTime() - o2.getCreateTime().getTime() > 0) {
					return -1;
				}
				return 1;
			}
		});
	}

	/**
	 * 按最后处理时间排序
	 * 
	 * @param flowInstVos
	 */
	public static void sortFlowByLastTime(List<FlowInstVo> flowInstVos) {
		if (CollectionUtils.isEmpty(flowInstVos))
			return;
		Collections.sort(flowInstVos, new Comparator<FlowInstVo>() {
			@Override
			public int compare(FlowInstVo o1, FlowInstVo o2) {
				if (o1 == null || o2 == null)
					return 1;
				long o1_time = o1.getLastTime() != null ? o1.getLastTime().getTime() : 0;
				long o2_time = o2.getLastTime() != null ? o2.getLastTime().getTime() : 0;
				if (o1_time - o2_time > 0) {
					return -1;
				}
				return 1;
			}
		});
	}

	/**
	 * 构造代办消息审批标题，格式： 主标题(副标题)
	 * 
	 * @param title
	 * @param subTitle
	 * @return
	 */
	public static String constructionMsgTitle(String title, String subTitle) {
		StringBuffer msgTitle = new StringBuffer();
		if(title!=null) msgTitle.append(title);
		if (StringUtils.isNotBlank(subTitle)) {
			msgTitle.append("(").append(subTitle).append(")");
		}
		return msgTitle.toString();
	}

	/**
	 * 按链表结构重新排序(从开始节点开始查找，必须包含开始节点)--倒序，开始节点在最后
	 * 
	 * @param flowInstVos
	 */
	public static List<ActivityInst> sortActivityByLink(List<ActivityInst> activityInsts) {

		ActivityInst startActivityInst = null;
		String startActivityType = ActivityType.START.toString();
		Map<String, ActivityInst> activityId_inst = new HashMap<>();
		for (ActivityInst activityInst : activityInsts) {
			if (startActivityType.equals(activityInst.getActivityType())) {
				startActivityInst = activityInst;
			}
			activityId_inst.put(activityInst.getId(), activityInst);
		}
		if (startActivityInst == null)
			return activityInsts;

		List<ActivityInst> orderActivityInsts = new ArrayList<ActivityInst>();
		orderActivityInsts.add(startActivityInst);

		List<String> nextActivityInstIds = startActivityInst.getNextActivityInstIds();
		while (CollectionUtils.isNotEmpty(nextActivityInstIds)) {
			ActivityInst nextActivityInst = activityId_inst.get(nextActivityInstIds.get(0));
			if (nextActivityInst != null) {
				if (ActivityType.XOR.toString().equals(nextActivityInst.getActivityType())) {
					// 兼容旧数据 节点撤回 再退回 ...
					// 退回节点会有两个下一个节点，其中一个xor节点的next节点是recall状态的，将不会被找出来
					List<String> nextUserActivityInstIds = nextActivityInst.getNextActivityInstIds();
					ActivityInst nextUserActivityInst = null;
					if (CollectionUtils.isNotEmpty(nextUserActivityInstIds)) {
						nextUserActivityInst = activityId_inst.get(nextUserActivityInstIds.get(0));
					}
					if (nextUserActivityInst == null) {
						nextActivityInst = null;
					}
				}

				if (nextActivityInst != null) {
					orderActivityInsts.add(0, nextActivityInst);
					nextActivityInstIds = nextActivityInst.getNextActivityInstIds();
				}
			}

			if (nextActivityInst == null && nextActivityInstIds.size() == 2) {
				// 为了兼容旧数据，在退回的时候没有把原来的next节点清除
				nextActivityInst = activityId_inst.get(nextActivityInstIds.get(1));
				if (nextActivityInst != null) {
					orderActivityInsts.add(0, nextActivityInst);
					nextActivityInstIds = nextActivityInst.getNextActivityInstIds();
				}
			}

			if (nextActivityInst == null) {
				nextActivityInstIds = null;
			}
		}
		return orderActivityInsts;
	}


	/**
	 * 包含并行审批节点内部节点；按链表结构重新排序(从开始节点开始查找，必须包含开始节点)--倒序，开始节点在最后
	 * @Author jessica_fan
	 * @Description
	 * @Date 09:52 2020/3/16
	 * @Param [activityInsts]
	 **/
	public static List<ActivityInst> sortIncParallelActivityByLink(List<ActivityInst> activityInsts) {
		ActivityInst startActivityInst = null;
		String startActivityType = ActivityType.START.toString();
		Map<String, ActivityInst> activityId_inst = new HashMap<>();
		for (ActivityInst activityInst : activityInsts) {
			if (startActivityType.equals(activityInst.getActivityType())) {
				startActivityInst = activityInst;
			}
			activityId_inst.put(activityInst.getId(), activityInst);
		}
		if (startActivityInst == null)
			return activityInsts;

		List<ActivityInst> orderActivityInsts = new ArrayList<>();
		orderActivityInsts.add(startActivityInst);

		List<String> nextActivityInstIds = startActivityInst.getNextActivityInstIds();
		while (CollectionUtils.isNotEmpty(nextActivityInstIds)) {
			ActivityInst nextActivityInst = activityId_inst.get(nextActivityInstIds.get(0));
			if (nextActivityInst != null) {
				//并行节点，要找出他自己下面的节点
				if (ActivityType.XOR.toString().equals(nextActivityInst.getActivityType()) || ActivityType.PARALLEL.toString().equals(nextActivityInst.getActivityType())){
					//判断是否是并行节点，获取里面的子节点排序
					if(ActivityType.PARALLEL.toString().equals(nextActivityInst.getActivityType())){
						sortParallelActivitiesByParallelId(activityId_inst,nextActivityInst,orderActivityInsts);
					}
					// 兼容旧数据 节点撤回 再退回 ...
					// 退回节pa点会有两个下一个节点，其中一个xor节点的next节点是recall状态的，将不会被找出来
					List<String> nextUserActivityInstIds = nextActivityInst.getNextActivityInstIds();
					ActivityInst nextUserActivityInst = null;
					if (CollectionUtils.isNotEmpty(nextUserActivityInstIds)) {
						nextUserActivityInst = activityId_inst.get(nextUserActivityInstIds.get(0));
					}
					if (nextUserActivityInst == null) {
						nextActivityInst = null;
					}
				}

				if (nextActivityInst != null) {
					orderActivityInsts.add(0, nextActivityInst);
					nextActivityInstIds = nextActivityInst.getNextActivityInstIds();
				}
			}

			if (nextActivityInst == null && nextActivityInstIds.size() == 2) {
				// 为了兼容旧数据，在退回的时候没有把原来的next节点清除
				nextActivityInst = activityId_inst.get(nextActivityInstIds.get(1));
				if (nextActivityInst != null) {
					orderActivityInsts.add(0, nextActivityInst);
					nextActivityInstIds = nextActivityInst.getNextActivityInstIds();
				}
			}

			if (nextActivityInst == null) {
				nextActivityInstIds = null;
			}
		}
		return orderActivityInsts;
	}

	private static List<ActivityInst> sortParallelActivitiesByParallelId(Map<String, ActivityInst> activityMap, ActivityInst parallelActivity, List<ActivityInst> orderActivityInsts) {
		ActivityInst paralelStartActivityInst = activityMap.get(parallelActivity.getParallelStartInstId());
		if (paralelStartActivityInst == null) return Collections.emptyList();
		orderActivityInsts.add(0,paralelStartActivityInst);
		List<String> parallerNextActivityInstIds = paralelStartActivityInst.getNextActivityInstIds();

		//并行节点内部只有一个FORK网关
		ActivityInst parallelNextActivityInst = activityMap.get(parallerNextActivityInstIds.get(0));
		if (parallelNextActivityInst != null) {
			if (ActivityType.FORK.toString().equals(parallelNextActivityInst.getActivityType())) {
				//FORK网关后面多个分发节点或者多个用户节点
				List<String> nextParallelActivityInstIds = parallelNextActivityInst.getNextActivityInstIds();
				for (String parallelActivityInstId : nextParallelActivityInstIds) {
					ActivityInst parallelSubActivityInst = activityMap.get(parallelActivityInstId);
					orderActivityInsts.add(0, parallelSubActivityInst);
					//--------------------------------处理并行几点内部的节点------------------------------
					List<String> nextUserActivityInstIds = parallelSubActivityInst.getNextActivityInstIds();
					while (CollectionUtils.isNotEmpty(nextUserActivityInstIds)) {
						ActivityInst nextActivityInst = activityMap.get(nextUserActivityInstIds.get(0));
						if (nextActivityInst != null) {
							//并行节点，要找出他自己下面的节点
							if (ActivityType.XOR.toString().equals(nextActivityInst.getActivityType())) {
								// 退回节pa点会有两个下一个节点，其中一个xor节点的next节点是recall状态的，将不会被找出来
								List<String> nextUserActivityInstIds1 = nextActivityInst.getNextActivityInstIds();
								ActivityInst nextUserActivityInst1 = null;
								if (CollectionUtils.isNotEmpty(nextUserActivityInstIds1)) {
									nextUserActivityInst1 = activityMap.get(nextUserActivityInstIds.get(0));
								}
								if (nextUserActivityInst1 == null) {
									nextActivityInst = null;
								}
							}

							if (nextActivityInst != null) {
								orderActivityInsts.add(0, nextActivityInst);
								nextUserActivityInstIds = nextActivityInst.getNextActivityInstIds();
							}
						}

						if (nextActivityInst == null) {
							nextUserActivityInstIds = null;
						}
					}
					//--------------------------------------------------------------
				}
			}
		}
		return orderActivityInsts;
	}

	public static void deleteCache(String key) {
		if (StringUtils.isBlank(key))
			return;
		JedisUtil.del(key);
	}

	/**
	 * 将shareInfo对象转为 key:value 格式
	 * 
	 * @param shareInfoVo
	 * @return map
	 */
	public static List<Map<String, String>> getShareInfoContent(ShareInfoVo shareInfoVo,int cellNum) {
		if (shareInfoVo == null)
			return Collections.emptyList();
		List<Map<String, String>> result = new ArrayList<>();
		Map<String, String> primaryContent = shareInfoVo.getPrimaryContent();
		if (MapUtils.isNotEmpty(primaryContent)) {
			LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();
			tempMap.put(primaryContent.get("key"), primaryContent.get("value"));
			result.add(tempMap);
		}
		Map<String, String> cellMap = getShareInfoCellContent(shareInfoVo, cellNum);
		if(MapUtils.isNotEmpty(cellMap)) {
			result.add(cellMap);
		}
		return result;
	}
	public static Map<String, String> getShareInfoCellContent(ShareInfoVo shareInfoVo,int showNum) {
		if (shareInfoVo == null)
			return Collections.emptyMap();

		LinkedHashMap<String, String> tempMap = new LinkedHashMap<>();
		List<Map<String, String>> cellContents = shareInfoVo.getCellContents();
		if (CollectionUtils.isEmpty(cellContents)) {
			return Collections.emptyMap();
		}
		int tempNum = 0;
		for (Map<String, String> map : cellContents) {
			if(tempNum>showNum) {
				break;
			}
			tempNum++;
			if(null!=map.get("key")) {
				tempMap.put(map.get("key"), map.get("value"));
			}
		}
		return tempMap;
	}
	
	public static void sortWebFlowByCreateTime(List<WebFlowInstVo> flowInstVos,String direction) {
		if (CollectionUtils.isEmpty(flowInstVos))
			return;
		if(CloudflowConstants.ORDER_DIRECTION_ASC.equalsIgnoreCase(direction)) {
			//升序
			Collections.sort(flowInstVos, new Comparator<WebFlowInstVo>() {
				@Override
				public int compare(WebFlowInstVo o1, WebFlowInstVo o2) {
					if (o1 == null || o2 == null)
						return 1;
					if (o1.getCreateTime().getTime() - o2.getCreateTime().getTime() < 0) {
						return -1;
					}
					return 1;
				}
			});
			return;
		}
		//降序
		Collections.sort(flowInstVos, new Comparator<WebFlowInstVo>() {
			@Override
			public int compare(WebFlowInstVo o1, WebFlowInstVo o2) {
				if (o1 == null || o2 == null)
					return 1;
				if (o1.getCreateTime().getTime() - o2.getCreateTime().getTime() > 0) {
					return -1;
				}
				return 1;
			}
		});
	}

	/**
	 * 根据最后处理时间。。或者最后获取时间排序
	 * 
	 * @param flowInstVos
	 * @param direction 排序方向
	 */
	public static void sortWebFlowByLastTime(List<WebFlowInstVo> flowInstVos,String direction) {
		if (CollectionUtils.isEmpty(flowInstVos))
			return;
		if(CloudflowConstants.ORDER_DIRECTION_ASC.equalsIgnoreCase(direction)) {
			//升序
			Collections.sort(flowInstVos, new Comparator<WebFlowInstVo>() {
				@Override
				public int compare(WebFlowInstVo o1, WebFlowInstVo o2) {
					if (o1 == null || o2 == null)
						return 1;
					long o1_time = o1.getLastTime() != null ? o1.getLastTime().getTime() : 0;
					long o2_time = o2.getLastTime() != null ? o2.getLastTime().getTime() : 0;
					if (o1_time - o2_time < 0) {
						return -1;
					}
					return 1;
				}
			});
			return;
		}
		//降序
		Collections.sort(flowInstVos, new Comparator<WebFlowInstVo>() {
			@Override
			public int compare(WebFlowInstVo o1, WebFlowInstVo o2) {
				if (o1 == null || o2 == null)
					return 1;
				long o1_time = o1.getLastTime() != null ? o1.getLastTime().getTime() : 0;
				long o2_time = o2.getLastTime() != null ? o2.getLastTime().getTime() : 0;
				if (o1_time - o2_time > 0) {
					return -1;
				}
				return 1;
			}
		});
	}

	public static String getFormDept(FormInstance form) {
		if (null == form) {
			return null;
		}
		String code = null;
		@SuppressWarnings("unchecked")
		List<String> deptCodes = (List<String>) form.getWidgetValue().get(FormParameter.WIDGET_SYS_CODEID_DEPT);
		if (CollectionUtils.isNotEmpty(deptCodes)) {
			code = deptCodes.get(0);
		}
		return code;
	}

	/**
	 * 按orderNo排序
	 * 
	 * @param taskVos
	 */
	public static void sortTasksByOrderNo(List<Task> tasks) {
		if (CollectionUtils.isEmpty(tasks) || tasks.size()<2)
			return;
		Collections.sort(tasks, new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				if (o1 == null || o2 == null)
					return 1;
				if (o1.getOrderNo() - o2.getOrderNo() < 0) {
					return -1;
				}
				return 1;
			}
		});
	}

	/**
	 * 按处理时间排序
	 * 
	 * @param tasks
	 */
	public static void sortTasksByHandleTime(List<Task> tasks) {
		if (CollectionUtils.isEmpty(tasks) || tasks.size()<2)
			return;
		Collections.sort(tasks, new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				if (o1 == null || o2 == null)
					return 1;
				long o1_time = o1.getHandleTime() != null ? o1.getHandleTime().getTime() : 0;
				long o2_time = o2.getHandleTime() != null ? o2.getHandleTime().getTime() : 0;
				if (o1_time - o2_time < 0) {
					return -1;
				}
				return 1;
			}
		});
	}
	
	/**
	 * @Description:返回该cclist是否组里面的抄送
	 * @param ccList
	 * @return boolean
	 * @exception:
	 * @author: lusheng
	 * @time:2017年11月13日 下午5:11:28
	 */
	public static boolean isGroupCcList(CcList ccList) {
		if (null != ccList) {
			String handleGroupId = ccList.getHandleGroupId();
			if (StringUtils.isNotBlank(handleGroupId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Description:通过cclist获得一个cclistVo
	 * @param cclist
	 * @param userInfoMap
	 * @return CcListVo
	 * @exception:
	 * @author: lusheng
	 * @time:2017年11月13日 下午7:19:57
	 */
	public static CcListVo getCcListVoFromCcList(CcList cclist, Map<String, UserSimpleInfo> userInfoMap) {
		CcListVo vo = CcListVo.changeFromCcList(cclist);
		paintUserInfoToCcList(vo, userInfoMap,cclist.getEntrustMap());
		return vo;
	}
	public static List<CopyHandler> getCopyHandlerFromOids(Collection<String> oids,Map<String, UserSimpleInfo> userInfoMap){
		if(CollectionUtils.isEmpty(oids)||MapUtils.isEmpty(userInfoMap)) {
			return null;
		}
		List<CopyHandler> result = new ArrayList<>();
		for (String oid : oids) {
			UserSimpleInfo user = userInfoMap.get(oid);
			if(null!=user) {
				CopyHandler handler = new CopyHandler();
				handler.setCompanyName(user.getCompanyName());
				handler.setName(user.getName());
				handler.setPhoto(user.getPhotoUrl());
				handler.setOpenId(user.getOpenId());
				handler.setOid(user.getoId());
				result.add(handler);
			}
		}
		return result;
	}

	public static void paintUserInfoToCcList(CcListVo cclist, Map<String, UserSimpleInfo> userInfoMap,Map<String, List<String>> entrustMap) {
		if (null == cclist || MapUtils.isEmpty(userInfoMap)) {
			return;
		}
		
		List<CopyHandler> handlers = cclist.getHandlers();
		for (CopyHandler copyHandler : handlers) {
			String oid = copyHandler.getOid();
			if (StringUtils.isNotBlank(oid)) {
				UserSimpleInfo user = userInfoMap.get(oid);
				if (null != user) {
					String name = user.getName();
					String photoUrl = user.getPhotoUrl();
					String openId = user.getOpenId();
					copyHandler.setName(name);
					copyHandler.setOpenId(openId);
					copyHandler.setPhoto(photoUrl);
					copyHandler.setCompanyName(user.getCompanyName());
					copyHandler.setDept(user.getDepartment());
				}
			}
			if(MapUtils.isNotEmpty(entrustMap)) {
				List<String> consigners = entrustMap.get(oid);
				if(CollectionUtils.isNotEmpty(consigners)) {
					List<String> consignerNames = new ArrayList<>();
					for (String consigner : consigners) {
						UserSimpleInfo user = userInfoMap.get(consigner);
						if(user!=null) consignerNames.add(user.getName());
					}
					if(consignerNames.size()>0) copyHandler.setConsigners(consignerNames);
				}
			}
		}
	}
	
	/**
	 * 标记流程发生改变
	 * @param flowInstId
	 */
	public static void markFLowChangeToCache(String flowInstId) {
		RedisUtils.setEx(RedisKeyUtils.genKey(MakeflowRedisConstants.REDIS_LOCK_MAKEFLOW_ENGINE_KEY_TAG, flowInstId), flowInstId, 12, TimeUnit.SECONDS);
	}
	
	/**
	 * 判断短时间内流程是否发生改变
	 * 
	 * @param flowInstId
	 * @return
	 */
	public static boolean checkFLowChangeInSecondFromCache(String flowInstId) {
		return JedisUtil.get(JedisUtil.genKey(CloudflowConstants.WORKFLOW_CHANGE_KEY_TAG, flowInstId)) != null;
	}
	
	/**
	 * 标记该表单对应的流程刚创建
	 * @param formInstId
	 */
	public static void markFLowCreatedByFormInstIdToCache(String formInstId) {
		JedisUtil.setex(JedisUtil.genKey(CloudflowConstants.WORKFLOW_CREATED_BY_FORMINSTID_KEY_TAG, formInstId), formInstId, 10);
	}
	
	/**
	 * 流程是否刚创建
	 * @param formInstId
	 * @return
	 */
	public static boolean checkFLowCreatedByFormInstIdFromCache(String formInstId) {
		return JedisUtil.get(JedisUtil.genKey(CloudflowConstants.WORKFLOW_CREATED_BY_FORMINSTID_KEY_TAG, formInstId)) != null;
	}
	
	/**
	 * 标记流程讨论信息发生改变
	 * 
	 * @param flowInstId
	 */
	public static void markFLowFeedbackChangeToCache(String flowInstId) {
		JedisUtil.setex(JedisUtil.genKey(CloudflowConstants.WORKFLOW_FEEDBACK_CHANGE_KEY_TAG, flowInstId), flowInstId, 10);
	}

	/**
	 * 判断短时间内流程讨论信息是否发生改变
	 * 
	 * @param flowInstId
	 * @return
	 */
	public static boolean checkFLowFeedbackChangeInSecondFromCache(String flowInstId) {
		return JedisUtil.get(JedisUtil.genKey(CloudflowConstants.WORKFLOW_FEEDBACK_CHANGE_KEY_TAG, flowInstId)) != null;
	}
	
	/**
	 * 标记流程产生异常
	 * @param flowInstId
	 */
	public static void markFLowExceptionToCache(String flowInstId) {
		JedisUtil.setex(JedisUtil.genKey(CloudflowConstants.WORKFLOW_EXCEPTION_CREATE_KEY_TAG, flowInstId), flowInstId, 10);
	}
	
	/**
	 * 判断短时间内流程是否产生异常
	 * 
	 * @param flowInstId
	 * @return
	 */
	public static boolean checkFLowExceptionInSecondFromCache(String flowInstId) {
		return JedisUtil.get(JedisUtil.genKey(CloudflowConstants.WORKFLOW_EXCEPTION_CREATE_KEY_TAG, flowInstId)) != null;
	}

	/**
	 * @Description:判断是否不同意的流程
	 * @param flow
	 * @return boolean
	 * @exception:
	 * @author: lusheng
	 * @time:2018年3月5日 下午4:23:43
	 */
	public static boolean isFlowDisagree(FlowInst flow) {
		return FlowStatus.DISAGREE.equals(flow.getStatus());
	}

	public static List<BaseSequenseFlow> sortRuleSequenceList(List<BaseSequenseFlow> sequenseTemp) {
		return SequenceUtil.sortSequenceFlow(sequenseTemp);
	}

	/**
	 * 这块的逻辑已经搬迁到AutoApprovalCmdProcess，方法已弃用
	 * @param info
	 * @param nowActivity 加了这个参数，说明这个加签节点不能被自动审批，但是其他节点可以，这块做下处理
	 * @param nowGroupCodeId 当前组，判断组是否有编辑权限
	 * @return
	 */
	@Deprecated
	public static boolean isFlowAutoApproval(FlowInstRelatedInfo info,ActivityInst nowActivity,String nowGroupCodeId) {
		AutoApprovalContext ctx = new AutoApprovalContext(nowActivity,info,nowGroupCodeId);
		return AutoApprovalCmdProcess.createInst().canHandlerAutoApproval(ctx);
	}

	/**
	 * 是否开启意见登记
	 * @param activityInst
	 * @return
	 */
	public static boolean canCommentsRegister(ActivityInst activityInst) {
		if (activityInst == null) return false;
		// 节点是否开启登记功能
		Map<String, Object> detail = activityInst.getDetail();
		if (detail == null) return false;
		Object result = detail.get(CloudflowConstants.ACTIVITY_INSTANCE_CAN_COMMENTS_REGISTER);
		if (result instanceof Boolean)
			return (Boolean) result;
		return false;
	}

	/**
	 * 是否需要自动赋值
	 *
	 * @param activityInst
	 * @return
	 */
	public static boolean isNeedAutoFill(ActivityInst activityInst) {
		if(activityInst == null) return false;
		// 节点是否设置了标签
		Map<String, Object> detail = activityInst.getDetail();
		if(detail == null) return false;
		Object result = detail.get(CloudflowConstants.ACTIVITY_INSTANCE_NEED_AUTO_FILL);
		if ((result instanceof Boolean))
			return (Boolean) result;
		return false;
	}

	/**
	 * 是否自动赋值过
	 * @param activityInst
	 * @return
	 */
	public static boolean isAutoFill(ActivityInst activityInst) {
		if(activityInst == null) return false;
		// 是否已赋值过
		FlowInstRelatedInfoDao flowInstRelatedInfoDao = SpringContextUtil.getBean(FlowInstRelatedInfoDao.class);
		FlowInstRelatedInfo flowInstRelatedInfo = flowInstRelatedInfoDao.findByFlowInstId(activityInst.getFlowInstId());
		if (flowInstRelatedInfo != null &&
				(flowInstRelatedInfo.getAutoFillBookActivityInstIds() != null &&
						flowInstRelatedInfo.getAutoFillBookActivityInstIds().contains(activityInst.getId()))) {
			return true;
		}
		return false;
	}

	public static boolean canBatchApprove(ActivityInst activityInst){
        if(!FlowUtils.isOpenFingerPrint(activityInst)&&!FlowUtils.isStartActivity(activityInst)
                &&!FlowUtils.isOpenDispatchSeal(activityInst)&&!FlowUtils.isOpenDispatchSetRed(activityInst)
                &&! FlowUtils.isOpenOnlineOfficeSeal(activityInst)
				//人工节点也不让其快捷同意
				&&! ActivityUtil.isManualActivity(activityInst)
				// 意见登记节点不支持批量同意
				&&! FlowUtils.canCommentsRegister(activityInst)
				// 需要自动赋值节点
				&& !FlowUtils.isNeedAutoFill(activityInst)
				// 不是分发集成模式
				&& !ActivityUtil.isOpenDispatchInherit(activityInst)
				// 没设置征求且全部回复
				&& isSetAllReplyThenAgree(activityInst)
				// 编号节点不支持快捷同意
				&& !isIssuedNumberNode(activityInst)
				// 节点需要手写签名不支持快捷同意
				&& !isHandSign(activityInst)
				){
            return true;
        }
        return false;
    }

	/**
	 *
	 * @param activityInst
	 * @return 	null->false
	 * 			设置了->false
	 * 			其余情况->true
	 */
    public static boolean isSetAllReplyThenAgree(ActivityInst activityInst){
		if(null==activityInst){
			return false;
		}
		OpinionSettings settings = ActivityUtil.getOpinionSettings(activityInst);
		if(null!=settings&&settings.isAllReply()){
			return false;
		}
		return true;
	}

	/**
	 * 流程是否跨企业(截止当前版本，只支持模板发布到子企业 发起流程， 可以根据发起人eid(creatorEid)与模板所属eid(eid)来判断是否跨企业)
	 * 支持主企业发起的流程,可以选择生态圈的其他企业的人审批,跨企业判断增加是否有生态圈成员控件 update by hqg 20180526
	 * @param flowInst
	 * @author hqg by 20180502
	 * @param info 
	 * @return true 是
	 */
	public static boolean isFlowCrossEnterprise(FlowInst flowInst, FlowInstRelatedInfo info) {
		if(StringUtils.isNotBlank(flowInst.getCreatorEid()) && !flowInst.getCreatorEid().equals(flowInst.getEid())) return true;
		if(info!=null) {
			return info.isPublishToOtherEnp() || info.isHasEcosphereMember();
		}
		return false;
	}

	/**
	 * 判断流程是否跨企业
	 * @param flowInst
	 * @return
	 */
	public static boolean isFlowCrossEnterprise(FlowInst flowInst){
		return isFlowCrossEnterprise(flowInst,null);
	}
	public static boolean isFlowDeleted(FlowInst flowInst) {
		if(null!=flowInst&&0!=flowInst.getDeleteStatus()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否参与流程的人的eid （判断是否有权限访问单据） 
	 * @param eid
	 * @param flowInst
	 * @param info
	 * @return
	 */
	public static boolean isParticipantEid(String eid,FlowInst flowInst,FlowInstRelatedInfo info) {
		
		if(eid.equals(flowInst.getEid()) || eid.equals(flowInst.getCreatorEid())) return true;
		
		if(info!=null && CollectionUtils.isNotEmpty(info.getParticipantEids()) && info.getParticipantEids().contains(eid)) {
			return true;
		}
		
		return false;
	}
	public static boolean isflowSmartReturned(FlowInstRelatedInfo info) {
		if(null==info) {
			return false;
		}
		return info.isSmartReturned();
	}

	/**
	 * 流程是否是智能退回到开始节点
	 * @param flowInst
	 * @return
	 */
	public static boolean smartReturnToRestart(FlowInst flowInst){
		if(null==flowInst){
			return false;
		}
		//退回状态，且submitTime不为空
		return FlowStatus.RETURNED.equals(flowInst.getStatus())&&null!=flowInst.getSubmitTime();
	}
	/**
	 * 标记流程还在流转过程中
	 * @param flowInstId
	 */
	public static void markFlowRunningAsync(String flowInstId) {
		JedisUtil.setex(JedisUtil.genKey(CloudflowConstants.WORKFLOW_FLOW_RUNNING_KEY_TAG, flowInstId), flowInstId, 30);
	}
	
	/**
	 * 标记流程还在流转过程中
	 * @param flowInstId
	 */
	public static boolean checkFlowRunning(String flowInstId) {
		return JedisUtil.get(JedisUtil.genKey(CloudflowConstants.WORKFLOW_FLOW_RUNNING_KEY_TAG, flowInstId)) != null;
	}
	/**
	 * 清除流程流转标记
	 * @param flowInstId
	 */
	public static void removeFlowRunning(String flowInstId) {
		JedisUtil.del(JedisUtil.genKey(CloudflowConstants.WORKFLOW_FLOW_RUNNING_KEY_TAG, flowInstId));
	}
	
	/**
	 * 是否参与人身份
	 * @param identity
	 * @return
	 */
	public static boolean isParticipantIdentity(IdentityType identity) {
		if(identity==null) return false;
		
		return IdentityType.CREATOR.equals(identity) || IdentityType.TODO.equals(identity) 
				|| IdentityType.CC.equals(identity) || IdentityType.DONE.equals(identity)
				|| IdentityType.RECALL.equals(identity);
	}
	
	/**
	 * 是否开启 超时设置
	 * 
	 * @param activityInst
	 * @return
	 */
	public static boolean isOpenTimeout(ActivityInst activityInst) {
		if (activityInst == null)
			return false;
		Map<String, Object> detail = activityInst.getDetail();
		if (detail != null) {
			Object object = detail.get(ActivityMapParamKeys.ACTIVITY_OPEN_TIMEOUT);
			if (object != null) {
				return (boolean) object;
			}
		}
		return false;
	}


	public static String getFlowDetailUrl(String cloudflowHost,String formTemplateId,String formInstId,String flowInstId) {
		String SPRIT = "/";
		StringBuilder url = new StringBuilder(cloudflowHost).append(URLConstants.CLOUDFLOW_MOBILE).append(SPRIT).append(formTemplateId).append(SPRIT)
				.append(formInstId).append(SPRIT).append(flowInstId).append("?opentype=111");
		return url.toString();
	}
	
	public static Map<String, Object> buildCcFlowRecord(int ccType,Date receiveTime) {
		Map<String, Object> record = new HashMap<>();
		record.put("ccType", ccType);
		record.put("receiveTime", receiveTime.getTime());
		return record;
	}
	
	/**
	 * 是否开启 校验创建代办
	 * @return
	 */
	public static boolean isOpenCheckCreateTodo() {
		return JedisUtil.get("cloudflow_workflow_check_create_todo_111") != null;
	}
	
	/**
	 * @param key cloudflow_workflow_check_create_todo_111:校验生成代办 CloudflowConstants.REDIS_LOCK_WORKFLOW_SCHEDULE_KEY_TAG_RESTATS:重新统计模板分类流程数
	 * @param open true 开启  false 关闭
	 */
	public static void openOrCloseByKey(String key,int open) {
		if(open==1) JedisUtil.set(key, "true");
		else JedisUtil.del(key);
	}


	public static boolean isFlowCanAbandonByCreator(Task task, ActivityInst taskActivityInst, FlowInst flowInst, FlowInstRelatedInfo info){
		if(null==flowInst||null==info||null==taskActivityInst||null==task){
			return false;
		}
		//任务是doing，且是发起人本人
		if(TaskStatus.DOING.equals(task.getStatus())&&StringUtils.equals(ContextUtils.getOid(),task.getHandler())){
			//如果处于开始节点（待提交、退回）
			if(ActivityUtil.isStartActivity(taskActivityInst)||ActivityUtil.isRestartActivity(taskActivityInst)){
				if(FlowStatus.TOSUBMIT.equals(flowInst.getStatus())
						||FlowStatus.RETURNED.equals(flowInst.getStatus())
						//退回后发起在撤回，流程状态是running。。。。。
						||FlowStatus.RUNNING.equals(flowInst.getStatus())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否加签的任务
	 * @param task
	 * @return
	 */
	public static boolean isSignTask(Task task){
		//普通节点加签是tasktype是sign，高级加钱task是turn，但是activityType是sign
		return null!=task? TaskType.SIGN.name().equals(task.getTaskType())||ActivityType.SIGN.name().equals(task.getActivityType()):false;
	}

	/**
	 * 查找当前节点是否有设置特殊的审批组自定义操作文案
	 * @param formCodeId
	 * @param activityCodeId
	 * @return
	 */
	public static List<GroupOperationNameSettings> getGroupOperationNameSettingsFormCache(String formCodeId, String activityCodeId) {
		if(StringUtils.isEmpty(formCodeId) || StringUtils.isEmpty(activityCodeId)) return null;
		String value = JedisUtil.get(JedisUtil.genKey(CloudflowConstants.WORKFLOW_FLOW_RUNNING_KEY_TAG, formCodeId,activityCodeId));
		if(StringUtils.isNotEmpty(value)) {
			JSONArray jsonArray = JSONArray.parseArray(value);
			List<GroupOperationNameSettings> settings = jsonArray.toJavaList(GroupOperationNameSettings.class);
			return settings;
		}
		return null;
	}

	/**
	 * 设置当前节点特殊的依次审批组自定义操作文案
	 * @param formCodeId
	 * @param activityCodeId
	 * @param settings
	 */
	public static void setGroupOperationNameSettingsToCache(String formCodeId, String activityCodeId,List<GroupOperationNameSettings> settings) {
		if(StringUtils.isEmpty(formCodeId) || StringUtils.isEmpty(activityCodeId)) return;
		String key = JedisUtil.genKey(CloudflowConstants.WORKFLOW_FLOW_RUNNING_KEY_TAG,formCodeId,activityCodeId);
		String value = JSONArray.toJSONString(settings);
		JedisUtil.set(key,value);
	}

	/**
	 * 判断dispatchaction是否需要节点名称前缀
	 * @param action
	 * @return
	 */
	public static boolean isDispatchActivityNameHasPrefix(DispatchAction action){
		if(null==action){
			return false;
		}
		String dispatchType = action.getDispatchType();
		String activityNamePrefix = action.getActivityNamePrefix();
		//如果不是拆分的，那么未false
		if(!ParallelApprove.ActivityType.SPLIT.equals(action.getActivityType())){
			return false;
		}
		if(DispatchTypeEnum.department.name().equals(dispatchType)&&DispatchAction.ActivityNamePrefixType.DEPARTMENT_TYPE.equals(activityNamePrefix)){
			return true;
		}
		if(DispatchTypeEnum.businessUnit.name().equals(dispatchType)&&DispatchAction.ActivityNamePrefixType.BUSINESS_UNIT_TYPE.equals(activityNamePrefix)){
			return true;
		}
		return false;
	}

	public static Map<String,String> getActivityUserMappingOrgRole(DispatchRoleApproverOrgMapCommand.OrgOidSet oidSet){
		LinkedHashMap<String, Set<String>> orgUsersMap = oidSet.getOrgOidMap();
		Map<String,String> oidOrgMap = new HashMap<>();
		//这块得知道oid对应哪个部门，然后分拆的节点才知道节点名称带上什么部门
		if(null!=orgUsersMap){
			Iterator<Map.Entry<String, Set<String>>> iterator = orgUsersMap.entrySet().iterator();
			while (iterator.hasNext()){
				Map.Entry<String, Set<String>> next = iterator.next();
				String orgId = next.getKey();
				Set<String> set = next.getValue();
				if(CollectionUtils.isNotEmpty(set)){
					for (String oid:set) {
						oidOrgMap.putIfAbsent(oid,orgId);
//						oidOrgMap.put(oid,orgId);
					}
				}
			}
		}
		return oidOrgMap;
	}

	/**
	 * 判断是不是智能审批的审批任务
	 *
	 * @param task
	 * @return 返回true表示这个任务是智能审批的审批任务
	 */
	public static boolean isCloudflowTask(Task task) {
		return task != null && StringUtils.isEmpty(task.getBizId());
	}

	/**
	 * 判断是不是原生集成任务
	 *
	 * @param task
	 * @return 返回true是原生集成任务
	 */
	public static boolean isThirdTask(Task task) {
		return task != null && StringUtils.isNotEmpty(task.getBizId());
	}

	/**
	 * 判断是不是智能审批的流程
	 *
	 * @param flowInst
	 * @return 返回true表示这个流程实例是智能审批原生数据，非接口生成
	 */
	public static boolean isCloudflowFlow(FlowInst flowInst) {
		return flowInst!=null && StringUtils.isEmpty(flowInst.getFlowId());
	}

	/**
	 * 判断是不是智能审批的抄送
	 *
	 * @param ccFlow
	 * @return 返回true表示这个任务是智能审批的抄送
	 */
    public static boolean isCloudflowCcFlow(CcFlow ccFlow) {
		return ccFlow!=null && StringUtils.isEmpty(ccFlow.getBizId());
    }
	/**
	 * 是否开通暂存功能
	 * @param eid
	 * @return
	 */
	public static boolean isOpenTemporarySave(String eid) {
		CloudflowFormRPCClientService cloudflowFormRPCClientService = SpringContextUtil.getBean(CloudflowFormRPCClientService.class);
		return cloudflowFormRPCClientService.isOpenTemporarySave(eid);
	}

	/**
	 * 按链表排序之后，只取开始节点是active 状态到最新节点的数据（必须保证activi维护正确）
	 *
	 * @param activityInsts
	 */
	public static List<ActivityInst> getActiveActivitysAndSortByLink(List<ActivityInst> activityInsts) {


		List<ActivityInst> sortActivitys = sortActivityByLink(activityInsts);
		Iterator<ActivityInst> iterator = sortActivitys.iterator();
		while (iterator.hasNext()) {
			ActivityInst activityInst = iterator.next();
			if(isStartActivity(activityInst) && activityInst.isActive()) {
				break;
			}
			iterator.remove();
		}

		return sortActivitys;
	}

	/**
	 * 判断节点是否为编号节点
	 *
	 * @param activityInst 节点实例
	 * @return true-编号节点；false-不是编号节点
	 */
	public static boolean isIssuedNumberNode(ActivityInst activityInst) {
		if (activityInst == null) {
			return false;
		}

		Map<String, Object> detail = activityInst.getDetail();
		if (detail == null) {
			return false;
		}

		Object object = detail.get(ActivityMapParamKeys.ACTIVITY_ISSUES_NUMBER_NODE);
		if (object != null) {
			return (boolean) object;
		}

		return false;
	}

	/**
	 * 是否轻云发起单据
	 *
	 * @param flowInst
	 * @return
	 */
	public static boolean isLightCloudFlow(FlowInst flowInst) {
		return MapParamKeys.SOURCE_LIGHT_CLOUD.equals(flowInst.getFsource());
	}

	/**
	 * 子流程停留开始节点
	 * @param action
	 * @return
	 */
	public static boolean isStartSubFlowTaskAndStay(StartSubFlowActionVo action) {
		if(action==null) return  false;
		String actionType = action.getAction();
		if(SubflowAction.STAY.getType().equals(actionType)){
			return true;
		}
		return false;
	}

	/**
	 * 普通流程启动是否停留在开始
	 *
	 * @param startFlowContext
	 * @return
	 */
	public static boolean isStartCommonFlowStayStart(StartFlowContext startFlowContext) {
		// 普通流程
		if (startFlowContext != null && startFlowContext.isStayStart()) {
			return true;
		}
		return false;
	}

	/**
	 * 流程启动是否停留在开始
	 *
	 * @param action
	 * @param startFlowContext
	 * @return
	 */
	public static boolean isStartFlowStayStart(StartSubFlowActionVo action, StartFlowContext startFlowContext) {
		// 1：先判断子流程
		if (isStartSubFlowTaskAndStay(action)) {
			return true;
		}
		// 2：再判断普通流程
		if (isStartCommonFlowStayStart(startFlowContext)) {
			return true;
		}
		return false;
	}
}
