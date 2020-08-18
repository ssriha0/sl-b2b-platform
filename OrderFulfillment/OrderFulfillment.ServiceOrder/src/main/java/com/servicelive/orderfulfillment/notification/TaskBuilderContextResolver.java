package com.servicelive.orderfulfillment.notification;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TaskBuilderContextResolver implements ApplicationContextAware {
    private ApplicationContext context;

    public NotificationTaskBuilderContext resolve(String taskBuilderContextName) {
        if (StringUtils.isNotBlank(taskBuilderContextName) && context.containsBean(taskBuilderContextName)) {
            return (NotificationTaskBuilderContext) context.getBean(taskBuilderContextName);
        }
        return null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
