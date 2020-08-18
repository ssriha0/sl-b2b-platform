package com.servicelive.orderfulfillment.test.helper.jbpm;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class JbpmDao {
    protected EntityManager entityMgr;
    
    @PersistenceContext()
    public void setEntityManager(EntityManager entityMgr) {
        this.entityMgr = entityMgr;
    }

    @Transactional(readOnly = true)
    public JbpmExecution findJbpmExecution(String serviceOrderKey) {
        Query qry = entityMgr.createQuery("SELECT j FROM JbpmExecution j WHERE j.key = :key");
        qry.setParameter("key", serviceOrderKey);
        try {
            return (JbpmExecution) qry.getSingleResult();
        } catch (javax.persistence.NoResultException e) {}

        return null;
    }
}
