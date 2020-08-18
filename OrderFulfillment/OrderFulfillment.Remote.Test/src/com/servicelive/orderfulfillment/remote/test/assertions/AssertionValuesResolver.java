package com.servicelive.orderfulfillment.remote.test.assertions;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AssertionValuesResolver implements ApplicationContextAware {
    ApplicationContext context;

    public TestCommandAssertionValues getCommandAssertionValues(TestCommand command) {
        return (TestCommandAssertionValues) context.getBean(command.name());
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
