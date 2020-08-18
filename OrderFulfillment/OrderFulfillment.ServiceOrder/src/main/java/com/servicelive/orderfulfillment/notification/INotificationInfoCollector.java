package com.servicelive.orderfulfillment.notification;

import java.util.Map;

public interface INotificationInfoCollector {
    public NotificationInfo collectNotificationInfo(Long notificationTemplateId, Map<String,Object> processVariablesClone);
}
