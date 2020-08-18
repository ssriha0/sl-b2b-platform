package com.servicelive.marketplatform.serviceinterface;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.notification.domain.NotificationTemplate;

public interface IMarketPlatformNotificationBO {
    public void queueNotificationTask(ServiceOrderNotificationTask serviceOrderNotificationTask);
    public NotificationTemplate retrieveNotificationTemplate(Long templateId);
}
