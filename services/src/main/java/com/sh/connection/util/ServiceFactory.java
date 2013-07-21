package com.sh.connection.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum ServiceFactory {

	INSTANCE;

	private ApplicationContext context;

	private ServiceFactory() {
		try {
			context = new ClassPathXmlApplicationContext(
			        "applicationContext.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> T getBean(Class<T> beanClass) {
		return INSTANCE.context.getBean(beanClass);
	}

	public ApplicationContext getContext() {
		return INSTANCE.context;
	}
}
