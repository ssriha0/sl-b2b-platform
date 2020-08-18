package com.servicelive.marketplatform.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.servicelive.marketplatform.common.domain.AbstractDomainEntity;

public class InMemoryNotificationTaskDao implements INotificationEntityDao {

    Map<Long, AbstractDomainEntity> storage = new HashMap<Long, AbstractDomainEntity>();

    public void setEntityManager(EntityManager em) {
    }

    public void save(AbstractDomainEntity notificationEntity) {
        storage.put(notificationEntity.getId(), notificationEntity);
    }
}
