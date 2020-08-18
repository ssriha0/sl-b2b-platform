package com.servicelive.marketplatform.dao;

import javax.persistence.EntityManager;

import com.servicelive.marketplatform.common.domain.AbstractDomainEntity;

public interface INotificationEntityDao {
    public void setEntityManager(EntityManager em);
    public void save(AbstractDomainEntity notificationEntity);
}
