
package com.makeid.makeflow.basic.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	public static ApplicationContext applicationContext; 

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * 返回bean
	 *
	 * @param beanName beanName
	 * @return bean
	 */
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	/**
	 * 获取bean
	 *
	 * @param c c
	 * @param <T> 泛型
	 * @return bean
	 */
	public static <T> T getBean(Class<T> c) {
		return applicationContext.getBean(c);
	}


	/**
	 * 获取bean
	 * @param c c
	 * @param  name 名称
	 * @param <T> 泛型
	 * @return T 泛型
	 */
	public static <T> T getBean(String name, Class<T> c) {
		return applicationContext.getBean(name, c);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

}