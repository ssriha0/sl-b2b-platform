package com.servicelive.orderfulfillment.notification;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;

public interface INotificationTaskBuilder {
    public abstract ServiceOrderNotificationTask buildNotificationTask(NotificationTaskBuilderContext taskBuilderContext);
}
