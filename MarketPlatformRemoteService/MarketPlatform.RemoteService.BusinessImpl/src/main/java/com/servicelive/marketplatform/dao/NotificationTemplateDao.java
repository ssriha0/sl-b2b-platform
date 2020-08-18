package com.servicelive.marketplatform.dao;

import com.servicelive.marketplatform.notification.domain.NotificationTemplate;

public class NotificationTemplateDao extends BaseEntityDao implements INotificationTemplateDao {
    public NotificationTemplate findById(long id) {
        return entityMgr.find(NotificationTemplate.class, id);
    }
}
