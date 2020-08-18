package com.servicelive.marketplatform.dao;

import com.servicelive.marketplatform.notification.domain.NotificationTemplate;

public interface INotificationTemplateDao extends INotificationEntityDao {
    public NotificationTemplate findById(long id);
}
