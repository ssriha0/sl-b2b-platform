package com.servicelive.orderfulfillment.notification;

import java.util.Map;

public interface INotificationValuesMapBuilder {
    public Map<String, String> buildMap(NotificationInfo notificationInfo);
}
