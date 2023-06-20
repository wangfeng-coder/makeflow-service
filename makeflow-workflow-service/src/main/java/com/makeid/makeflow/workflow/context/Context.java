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
import com.makeid.makeflow.workflow.service.ActivityService;
import com.makeid.makeflow.workflow.service.ExecutionService;
import com.makeid.makeflow.workflow.service.FlowInstService;
import com.makeid.makeflow.workflow.service.RuntimeService;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Stack;

/**
 * @author Tom Baeyens
 * @author Daniel Meyer
 */
public class Context {
	
	protected static ThreadLocal<Stack<CommandContext>> commandContextThreadLocal = new ThreadLocal<>();
	protected static ThreadLocal<Stack<ProcessEngineConfigurationImpl>> processEngineConfigurationStackThreadLocal = new ThreadLocal<>();

	protected static ThreadLocal<UserContext> currentUserContextThreadLocal =  new ThreadLocal<>();

	protected static ProcessEngineConfigurationImpl globalProcessEngineConfigurationImpl;

	public static UserContext getCurrentUser() {
		return currentUserContextThreadLocal.get();
	}

	public static void setCurrentUser(UserContext userContext){
		currentUserContextThreadLocal.set(userContext);
	}

	public static void removeCurrentUser() {
		 currentUserContextThreadLocal.remove();
	}

	public static String getUserId(){
		UserContext userContext = currentUserContextThreadLocal.get();
		Assert.notNull(userContext);
		return userContext.getUserId();
	}
		
	public static CommandContext getCommandContext() {
		Stack<CommandContext> stack = getStack(commandContextThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	public static void setGlobalProcessEngineConfiguration(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		globalProcessEngineConfigurationImpl = processEngineConfiguration;
	}

	public static ProcessEngineConfigurationImpl getGlobalProcessEngineConfiguration() {
		return globalProcessEngineConfigurationImpl;
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
}
