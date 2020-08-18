package com.servicelive.marketplatform.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.servicelive.marketplatform.serviceorder.domain.ServiceOrder;

public class ServiceOrderDao implements IServiceOrderDao {
	protected EntityManager entityMgr;

    @PersistenceContext()
    public void setEntityManager(EntityManager entityMgr) {
        this.entityMgr = entityMgr;
    }
	
	public ServiceOrder findById(String serviceOrderId) {
		return entityMgr.find(ServiceOrder.class, serviceOrderId);
	}

}
