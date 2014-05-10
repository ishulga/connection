package com.sh.connection.repos;

import com.sh.connection.ApplicationConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextTest {

    @Test
    public void testContext() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Assert.assertNotNull(context);
    }
}
