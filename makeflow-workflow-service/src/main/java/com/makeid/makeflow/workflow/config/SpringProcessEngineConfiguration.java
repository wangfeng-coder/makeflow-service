package com.makeid.makeflow.workflow.config;

import com.makeid.makeflow.workflow.context.Context;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/***
 * 
 * @author feihui
 *
 */
@Component
public class SpringProcessEngineConfiguration extends
		ProcessEngineConfigurationImpl implements ApplicationListener<ContextRefreshedEvent>{

	public SpringProcessEngineConfiguration(){}
	
//	public static void run(ApplicationContext applicationContext){
//	}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();;
        this.buildProcessEngine();
        Context.setGlobalProcessEngineConfiguration(this);
    }
	
	

}
