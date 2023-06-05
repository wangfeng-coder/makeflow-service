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
import com.makeid.makeflow.workflow.service.FlowInstService;
import com.yunzhijia.cloudflow.workflow.adapter.CloudflowFormRPCClientService;
import com.yunzhijia.cloudflow.workflow.adapter.CloudflowTemplateRPCClientService;
import com.yunzhijia.cloudflow.workflow.adapter.OpenOrgAndUserRPCClientService;
import com.yunzhijia.cloudflow.workflow.dao.*;
import com.yunzhijia.cloudflow.workflow.dao.overtime.OvertimeActivityDao;
import com.yunzhijia.cloudflow.workflow.pvm.delegate.DefaultDelegateInterceptor;
import com.yunzhijia.cloudflow.workflow.pvm.exception.ActivitiException;
import com.yunzhijia.cloudflow.workflow.pvm.interceptor.*;
import com.yunzhijia.cloudflow.workflow.pvm.service.ActivityService;
import com.yunzhijia.cloudflow.workflow.pvm.service.ExecutionService;
import com.yunzhijia.cloudflow.workflow.pvm.service.RuntimeService;
import com.yunzhijia.cloudflow.workflow.pvm.service.impl.ServiceImpl;
import com.yunzhijia.cloudflow.workflow.service.FlowExceptionService;
import com.yunzhijia.cloudflow.workflow.service.TaskService;
import com.yunzhijia.cloudflow.workflow.service.TemplateFlowStatService;
import com.yunzhijia.cloudflow.workflow.service.impl.FlowAbandonService;
import com.yunzhijia.cloudflow.workflow.service.impl.InitialDataService;
import com.yunzhijia.cloudflow.workflow.service.impl.overtime.OvertimeNoticeService;
import com.yunzhijia.cloudflow.workflow.service.inner.BlockOperationService;
import com.yunzhijia.cloudflow.workflow.service.local.AskOpinionService;
import com.yunzhijia.cloudflow.workflow.service.local.DraftService;
import com.yunzhijia.cloudflow.workflow.service.local.RobotService;
import com.yunzhijia.cloudflow.workflow.thread.AsyncTaskExecutor;
import com.yunzhijia.cloudflow.workflow.thread.ParallelTaskExecutor;
import com.yunzhijia.cloudflow.workflow.util.PushUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected ExecutionService executionService;
	@Autowired
	protected ActivityService activityService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected InitialDataService initialDataService;

	@Autowired
	protected FlowProcessTemplateService flowProcessDefinitionService;

	@Autowired
	protected ErrorMessageDao errorMessageDao;
	@Autowired
	protected FlowExceptionService flowExceptionService;
	@Autowired
	protected CcListDao ccListDao;
	@Autowired
	protected ExecutionControlInfoDao executionControlInfoDao;
	@Autowired
	protected  FlowInstRelatedInfoDao  flowInstRelatedInfoDao;
	@Autowired
	protected AsyncTaskExecutor asyncTaskExecutor;
	@Autowired
	protected SubFlowGenealogyDao subFlowGenealogyDao;
	@Autowired
	protected PushUtil pushUtil;
	@Autowired
	private TemplateFlowStatService templateFlowStatService;
	@Autowired
	private CloudflowTemplateRPCClientService templateRpcService;
	@Autowired
	private OvertimeNoticeService overtimeNoticeService;
	@Autowired
	protected FlowInstDao flowInstDao;
	@Autowired
	protected OvertimeActivityDao overtimeActivityDao;

	@Autowired
	private CloudflowFormRPCClientService cloudflowFormRPCClientService;
	@Autowired
	private FlowAbandonService flowAbandonService;
	@Autowired
	private OpenOrgAndUserRPCClientService openOrgAndUserRPCClientService;
	// COMMAND EXECUTORS
	// ////////////////////////////////////////////////////////

	protected CommandConfig defaultCommandConfig;

	@Autowired
	private DraftService draftService;
	@Autowired
	private BlockOperationService blockOperationService;
	@Autowired
	private AskOpinionService askOpinionService;
	@Autowired
	private RobotService robotService;
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

	protected CommandContextFactory commandContextFactory;

	protected CommandInterceptor commandInvoker;

	protected DelegateInterceptor delegateInterceptor;


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
//		interceptors.add(new CheckInterceptor());
		interceptors.add(new CommandContextInterceptor(commandContextFactory,
				this));
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
			throw new ActivitiException(
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
	
	
	protected void initDelegateInterceptor() {
		log.info("initDelegateInterceptor");
		if (delegateInterceptor == null) {
			delegateInterceptor = new DefaultDelegateInterceptor();
		}
	}
	
	/** getter and setter **/


	public CommandConfig getDefaultCommandConfig() {
		return defaultCommandConfig;
	}
	

	public DelegateInterceptor getDelegateInterceptor() {
		return delegateInterceptor;
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
	public InitialDataService getInitialDataService() {
		return initialDataService;
	}
	
	@Override
	public TaskService getTaskService(){
		return taskService;
	}

	@Override
	public AskOpinionService getAskOpinionService(){
		return askOpinionService;
	}
	
	@Override
	public FlowExceptionService getFlowExceptionService(){
		return flowExceptionService;
	}
	
	@Override
	public AsyncTaskExecutor getAsyncTaskExecutor(){
		return asyncTaskExecutor;
	}
	
	@Override
	public ErrorMessageDao getErrorMessageDao(){
		return errorMessageDao;
	}
	
	@Override
	public CcListDao getCcListDao(){
		return ccListDao;
	}
	
	@Override
	public ExecutionControlInfoDao getExecutionControlInfoDao(){
		return executionControlInfoDao;
	}
	
	@Override
	public CommandExecutor getCommandExecutor() {
		return this.commandExecutor;
	}

	@Override
	public PushUtil getPushUtil() {
		return pushUtil;
	}

	@Override
	public FlowInstRelatedInfoDao getFlowInstRelatedInfoDao() {
		return flowInstRelatedInfoDao;
	}

	public TemplateFlowStatService getTemplateFlowStatService() {
		return templateFlowStatService;
	}
	@Override
	public FlowInstDao getFlowInstDao() {
		return flowInstDao;
	}

	public CloudflowTemplateRPCClientService getTemplateRPCClientService() {
		return templateRpcService;
	}
	public OvertimeNoticeService getOvertimeNoticeService() {
		return overtimeNoticeService;
	}

	public OvertimeActivityDao getovertimeActivityDao() {
		return overtimeActivityDao;
	}

	public CloudflowFormRPCClientService getformRpcService(){
		return cloudflowFormRPCClientService;
	}
	public FlowAbandonService getFlowAbandonService(){
		return flowAbandonService;
	}

    public SubFlowGenealogyDao getSubflowGenealogyDao(){
		return subFlowGenealogyDao;
	}

	public ParallelTaskExecutor getParallelTaskExecutor() {
		return parallelTaskExecutor;
	}
	@Override
	public OpenOrgAndUserRPCClientService getOpenOrgAndUserService(){
		return openOrgAndUserRPCClientService;
	}

	public DraftService getDraftService() {
		return draftService;
	}

    public BlockOperationService getBlockOperationService() {
    	return blockOperationService;
	}

	@Override
	public RobotService getRobotService(){
		return robotService;
	}

	public FlowInstService getFlowInstService() {
		return flowInstService;
	}
}
