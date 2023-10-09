package com.makeid.makeflow;

import com.makeid.makeflow.template.service.third.FlowTemplateServiceThird;
import com.makeid.makeflow.workflow.event.EventRegister;
import com.makeid.makeflow.workflow.event.FlowEventListener;
import com.makeid.makeflow.workflow.service.third.TaskServiceThird;
import com.makeid.makeflow.workflow.service.third.WorkflowServiceThird;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-10
 */
@Slf4j
public class MakeFlowApplication {

    private static ApplicationContext flowContext;


    /**
     *
     * @return 外部操作流程对象
     */
    public static WorkflowServiceThird getWorkFlowService() {
        return flowContext.getBean(WorkflowServiceThird.class);
    }

    /**
     *
     * @return
     */
    public static TaskServiceThird getTaskServiceThird() {
        return flowContext.getBean(TaskServiceThird.class);
    }


    /**
     *
     * @return 外部操作流程模板对象
     */
    public static FlowTemplateServiceThird getFlowProcessTemplateService() {
        return flowContext.getBean(FlowTemplateServiceThird.class);
    }

    public synchronized static void  setFlowContext(ApplicationContext applicationContext) throws BeansException {
        if(Objects.isNull(flowContext)) {
            flowContext = applicationContext;
        }
    }

    public static ApplicationContext getFlowContext() {
        return flowContext;
    }

    /**
     * 注册listener到flow容器中的事件中心
     * @param flowEventListenerList
     */
    public synchronized static void registerMakeFLowEventListener(List<FlowEventListener> flowEventListenerList) {
        if (Objects.nonNull(flowContext)) {
            EventRegister register = flowContext.getBean(EventRegister.class);
            for (FlowEventListener flowEventListener : flowEventListenerList) {
                register.register(flowEventListener);
            }
        }
    }
}
