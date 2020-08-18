package com.servicelive.orderfulfillment.test.helper.dao;

import com.servicelive.domain.buyer.Buyer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ServiceLiveDao {
    protected EntityManager entityMgr;

    @PersistenceContext()
    public void setEntityManager(EntityManager entityMgr) {
        this.entityMgr = entityMgr;
    }

    public Buyer findBuyerById(int id) {
        return entityMgr.find(Buyer.class, id);
    }
}
