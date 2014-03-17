package com.sh.connection.repos;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sh.connection.ApplicationConfig;

public class ContextTest {
	
	@Test
	public void testContext(){
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		Assert.assertNotNull(context);
	}
}
