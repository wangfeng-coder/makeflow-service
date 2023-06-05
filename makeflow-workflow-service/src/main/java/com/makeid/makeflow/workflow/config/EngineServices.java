/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makeid.makeflow.workflow.config;

import com.makeid.makeflow.template.service.FlowProcessTemplateService;
import com.yunzhijia.cloudflow.workflow.adapter.OpenOrgAndUserRPCClientService;
import com.yunzhijia.cloudflow.workflow.dao.*;
import com.yunzhijia.cloudflow.workflow.pvm.interceptor.CommandExecutor;
import com.yunzhijia.cloudflow.workflow.pvm.service.ActivityService;
import com.yunzhijia.cloudflow.workflow.pvm.service.ExecutionService;
import com.yunzhijia.cloudflow.workflow.pvm.service.RuntimeService;
import com.yunzhijia.cloudflow.workflow.service.FlowExceptionService;
import com.yunzhijia.cloudflow.workflow.service.TaskService;
import com.yunzhijia.cloudflow.workflow.service.impl.FlowAbandonService;
import com.yunzhijia.cloudflow.workflow.service.impl.InitialDataService;
import com.yunzhijia.cloudflow.workflow.service.local.AskOpinionService;
import com.yunzhijia.cloudflow.workflow.service.local.RobotService;
import com.yunzhijia.cloudflow.workflow.thread.AsyncTaskExecutor;
import com.yunzhijia.cloudflow.workflow.util.PushUtil;


/**
 * Interface implemented by all classes that expose the Activiti services.
 * 
 * feihui
 */
public interface EngineServices {

	RuntimeService getRuntimeService();
	
	ExecutionService getExecutionService();

	FlowProcessTemplateService getFlowProcessDefinitionService();
	
	ActivityService getActivityService();

	AskOpinionService getAskOpinionService();
	
	TaskService getTaskService();
	
	InitialDataService getInitialDataService();

	AsyncTaskExecutor getAsyncTaskExecutor();

	
	
	FlowExceptionService getFlowExceptionService();
	
	ErrorMessageDao getErrorMessageDao();
	
	CcListDao getCcListDao();

	ExecutionControlInfoDao getExecutionControlInfoDao();
	
	CommandExecutor getCommandExecutor();
	PushUtil getPushUtil();
	
	FlowInstRelatedInfoDao getFlowInstRelatedInfoDao();
	FlowInstDao getFlowInstDao();
	FlowAbandonService getFlowAbandonService();

	OpenOrgAndUserRPCClientService getOpenOrgAndUserService();

	RobotService getRobotService();
}
