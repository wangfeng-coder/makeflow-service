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

import com.makeid.makeflow.basic.utils.SpringContextUtils;
import com.makeid.makeflow.template.service.FlowProcessTemplateService;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.interceptor.*;
import com.makeid.makeflow.workflow.service.*;

import com.makeid.makeflow.workflow.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public abstract class ProcessEngineConfigurationImpl extends
		ProcessEngineConfiguration {

	private static Logger log = LoggerFactory
			.getLogger(ProcessEngineConfigurationImpl.class);

	// SERVICES
	// /////////////////////////////////////////////////////////////////
	@Resource
	protected FlowInstService flowInstService;
	
	@Resource
	protected RuntimeService runtimeService;

	@Resource
	protected ExecutionService executionService;

	@Resource
	protected ActivityService activityService;

	@Resource
	protected TaskService taskService;

	@Resource
	protected FlowProcessTemplateService flowProcessDefinitionService;

	@Resource
	protected FlowTransactionInterceptor flowTransactionInterceptor;


	// COMMAND EXECUTORS
	// ////////////////////////////////////////////////////////

	protected CommandConfig defaultCommandConfig;


	/**
	 * the configurable list which will be
	 * {@link #initInterceptorChain(List) processed} to build the
	 * {@link #commandExecutor}
	 */
	protected List<CommandInterceptor> customPreCommandInterceptors;
	protected List<CommandInterceptor> customPostCommandInterceptors;

	protected List<CommandInterceptor> commandInterceptors;

	/** this will be initialized during the configurationComplete() */
	protected CommandExecutor commandExecutor;

	//protected CommandContextFactory commandContextFactory;

	protected CommandInterceptor commandInvoker;



	public void buildProcessEngine() {
		log.info("初始化数据");
		init();
	}

	protected void init() {
		initCommandExecutors();
		initServices();
	}

	protected void initCommandExecutors() {
		/** 注意下面方法的调用顺序 **/
		initDefaultCommandConfig();
		initCommandInvoker();
		initCommandInterceptors();
		initCommandExecutor();
	}

	protected void initDefaultCommandConfig() {
		log.info("initDefaultCommandConfig");
		if (defaultCommandConfig == null) {
			defaultCommandConfig = new CommandConfig();
		}
	}

	
	protected void initCommandInvoker() {
		log.info("initCommandInvoker");
		if (commandInvoker == null) {
			commandInvoker = new CommandInvoker();
		}
	}

	protected void initCommandInterceptors() {
		log.info("initCommandInterceptors");
		if (commandInterceptors == null) {
			commandInterceptors = new ArrayList<CommandInterceptor>();
			if (customPreCommandInterceptors != null) {
				commandInterceptors.addAll(customPreCommandInterceptors);
			}
			commandInterceptors.addAll(getDefaultCommandInterceptors());
			if (customPostCommandInterceptors != null) {
				commandInterceptors.addAll(customPostCommandInterceptors);
			}
			commandInterceptors.add(commandInvoker);
		}
	}
	
	protected Collection<? extends CommandInterceptor> getDefaultCommandInterceptors() {
		List<CommandInterceptor> interceptors = new ArrayList<CommandInterceptor>();
		interceptors.add(new LogInterceptor());
		//增加流程级锁拦截器
		interceptors.add(new LockInterceptor());
		//借助spring aop完成事务控制
		interceptors.add(this.flowTransactionInterceptor);
//		interceptors.add(new CheckInterceptor());
	/*	interceptors.add(new CommandContextInterceptor(commandContextFactory,
				this));*/
		return interceptors;
	}

	
	protected void initCommandExecutor() {
		log.info("initCommandExecutor");
		if (commandExecutor == null) {
			CommandInterceptor first = initInterceptorChain(commandInterceptors);
			commandExecutor = new CommandExecutorImpl(
					getDefaultCommandConfig(), first);
		}
	}

	protected CommandInterceptor initInterceptorChain(
			List<CommandInterceptor> chain) {
		log.info("initInterceptorChain");
		if (chain == null || chain.isEmpty()) {
			throw new EngineException(
					"invalid command interceptor chain configuration: " + chain);
		}
		for (int i = 0; i < chain.size() - 1; i++) {
			chain.get(i).setNext(chain.get(i + 1));
		}
		return chain.get(0);
	}
	
	protected void initServices() {
		log.info("initServices");
		initService(runtimeService);
		initService(executionService);
		initService(activityService);
	}

	protected void initService(Object service) {
		if (service instanceof ServiceImpl) {
			((ServiceImpl) service).setCommandExecutor(commandExecutor);
			((ServiceImpl) service).setProcessEngineConfiguration(this);
		}
	}

	
	/** getter and setter **/


	public CommandConfig getDefaultCommandConfig() {
		return defaultCommandConfig;
	}


	@Override
	public ExecutionService getExecutionService() {
		return executionService;
	}

	@Override
	public FlowProcessTemplateService getFlowProcessDefinitionService() {
		return flowProcessDefinitionService;
	}

	@Override
	public ActivityService getActivityService() {
		return activityService;
	}	
	
	@Override
	public RuntimeService getRuntimeService(){
		return this.runtimeService;
	}
	
	@Override
	public TaskService getTaskService(){
		return taskService;
	}

	public FlowInstService getFlowInstService() {
		return flowInstService;
	}
}
