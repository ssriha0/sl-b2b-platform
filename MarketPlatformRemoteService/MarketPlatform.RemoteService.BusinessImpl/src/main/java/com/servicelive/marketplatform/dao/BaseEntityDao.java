package com.servicelive.marketplatform.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.servicelive.marketplatform.common.domain.AbstractDomainEntity;

public class BaseEntityDao implements INotificationEntityDao {
    protected EntityManager entityMgr;

    @PersistenceContext()
    public void setEntityManager(EntityManager entityMgr) {
        this.entityMgr = entityMgr;
    }

    public void save(AbstractDomainEntity notificationEntity) {
        entityMgr.persist(notificationEntity);
    }
}
