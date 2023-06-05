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

package com.makeid.makeflow.workflow.context;



import com.makeid.makeflow.workflow.config.ProcessEngineConfigurationImpl;
import com.makeid.makeflow.workflow.interceptor.CommandContext;
import com.makeid.makeflow.workflow.service.ExecutionService;
import com.makeid.makeflow.workflow.service.FlowInstService;
import com.makeid.makeflow.workflow.service.RuntimeService;

import java.util.Stack;

/**
 * @author Tom Baeyens
 * @author Daniel Meyer
 */
public class Context {
	
	protected static ThreadLocal<Stack<CommandContext>> commandContextThreadLocal = new ThreadLocal<>();
	protected static ThreadLocal<Stack<ProcessEngineConfigurationImpl>> processEngineConfigurationStackThreadLocal = new ThreadLocal<>();
	protected static ThreadLocal<Stack<ExecutionContext>> executionContextStackThreadLocal = new ThreadLocal<>();

	protected static ProcessEngineConfigurationImpl globalProcessEngineConfigurationImpl;
		
	public static CommandContext getCommandContext() {
		Stack<CommandContext> stack = getStack(commandContextThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	public static void setCommandContext(CommandContext commandContext) {
		getStack(commandContextThreadLocal).push(commandContext);
	}

	public static void removeCommandContext() {
		getStack(commandContextThreadLocal).pop();
	}
	
	public static void setProcessEngineConfiguration(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		getStack(processEngineConfigurationStackThreadLocal).push(
				processEngineConfiguration);
	}

	public static ProcessEngineConfigurationImpl getProcessEngineConfiguration() {
		Stack<ProcessEngineConfigurationImpl> stack = getStack(processEngineConfigurationStackThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}
	
	public static void removeProcessEngineConfiguration() {
		getStack(processEngineConfigurationStackThreadLocal).pop();
	}
	
	public static void setGlobalProcessEngineConfiguration(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		globalProcessEngineConfigurationImpl = processEngineConfiguration;
	}

	public static ProcessEngineConfigurationImpl getGlobalProcessEngineConfiguration() {
		return globalProcessEngineConfigurationImpl;
	}

	public static boolean isExecutionContextActive() {
		Stack<ExecutionContext> stack = executionContextStackThreadLocal.get();
		return stack != null && !stack.isEmpty();
	}
	
	public static ExecutionContext getExecutionContext() {
		return getStack(executionContextStackThreadLocal).peek();
	}

	public static void setExecutionContext(ActivityExecution execution) {
		getStack(executionContextStackThreadLocal).push(
				new ExecutionContext(execution));
	}

	public static void removeExecutionContext() {
		getStack(executionContextStackThreadLocal).pop();
	}

	protected static <T> Stack<T> getStack(ThreadLocal<Stack<T>> threadLocal) {
		Stack<T> stack = threadLocal.get();
		if (stack == null) {
			stack = new Stack<T>();
			threadLocal.set(stack);
		}
		return stack;
	}
	public static FlowInstService getFlowInstService(){
		return getGlobalProcessEngineConfiguration().getFlowInstService();
	}
	
	public static RuntimeService getRuntimeService(){
		return getGlobalProcessEngineConfiguration().getRuntimeService();
	}
	
	public static ExecutionService getExecutionService(){
		return getGlobalProcessEngineConfiguration().getExecutionService();
	}
	
	public static ActivityService getActivityService(){
		return getGlobalProcessEngineConfiguration().getActivityService();
	}

	public static SubFlowGenealogyDao getSubflowGenealogyDao(){
		return getGlobalProcessEngineConfiguration().getSubflowGenealogyDao();
	}
	public static InitialDataService getInitialDataService() {
		return getGlobalProcessEngineConfiguration().getInitialDataService();
	}
	
	public static TaskService getTaskService() {
		return getGlobalProcessEngineConfiguration().getTaskService();
	}

	public static AskOpinionService getAskOpinionService() {
		return getGlobalProcessEngineConfiguration().getAskOpinionService();
	}
	
	public static AsyncTaskExecutor getAsyncTaskExecutor(){
		return  getGlobalProcessEngineConfiguration().getAsyncTaskExecutor();
	}
	
	public static ErrorMessageDao getErrorMessageDao(){
		return getGlobalProcessEngineConfiguration().getErrorMessageDao();
	}

	public static FlowExceptionService getFlowExceptionService(){
		return getGlobalProcessEngineConfiguration().getFlowExceptionService();
	}
	
	public static CcListDao getCcListDao(){
		return  getGlobalProcessEngineConfiguration().getCcListDao();
	}


	public static FlowAbandonService getFlowAbandonService(){
		return getGlobalProcessEngineConfiguration().getFlowAbandonService();
	}

	public static ExecutionControlInfoDao getExecutionControlInfoDao(){
		return  getGlobalProcessEngineConfiguration().getExecutionControlInfoDao();
	}
	// TODO 这里要思考放哪，暂时放这儿
	public static FlowInstRelatedInfoDao getFlowInstRelatedInfoDao() {
		return getGlobalProcessEngineConfiguration().getFlowInstRelatedInfoDao();
	}
	public static TemplateFlowStatService getTemplateFlowStatService() {
		return getGlobalProcessEngineConfiguration().getTemplateFlowStatService();
	}
	public static FlowInstDao getFlowInstDao() {
		return getGlobalProcessEngineConfiguration().getFlowInstDao();
	}
	public static CloudflowTemplateRPCClientService getTemplateRPCClientService() {
		return getGlobalProcessEngineConfiguration().getTemplateRPCClientService();
	}
	public static OvertimeNoticeService getOvertimeNoticeService() {
		return getGlobalProcessEngineConfiguration().getOvertimeNoticeService();
	}
	public static OvertimeActivityDao getOvertimeActivityDao() {
		return getGlobalProcessEngineConfiguration().getovertimeActivityDao();
	}
	public static CloudflowFormRPCClientService getFormRPCService(){
		return getGlobalProcessEngineConfiguration().getformRpcService();
	}
	public static ParallelTaskExecutor getParallelTaskExecutor() {
		return getGlobalProcessEngineConfiguration().getParallelTaskExecutor();
	}
	public static OpenOrgAndUserRPCClientService getOpenOrgAndUserService(){
		return getGlobalProcessEngineConfiguration().getOpenOrgAndUserService();
	}

	public static DraftService getDraftService() {
		return getGlobalProcessEngineConfiguration().getDraftService();
	}

	public static BlockOperationService getBlockOperationService(){
		return getGlobalProcessEngineConfiguration().getBlockOperationService();
	}

	public static RobotService getRobotService(){
		return getGlobalProcessEngineConfiguration().getRobotService();
	}
}
