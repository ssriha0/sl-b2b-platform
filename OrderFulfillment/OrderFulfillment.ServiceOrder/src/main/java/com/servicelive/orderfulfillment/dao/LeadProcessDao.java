package com.servicelive.orderfulfillment.dao;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.LeadProcess;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class LeadProcessDao implements ILeadProcessDao {

	protected final Logger logger = Logger.getLogger(getClass());

    private EntityManager em;
    private int retries = 5;

    @PersistenceContext()
    public void setEntityManager(EntityManager em) {
    	this.em = em;
    }

	public LeadProcess getLeadProcess(String leadId){
		return em.find(LeadProcess.class, leadId);
	}	

	public void save(LeadProcess lp){
		em.persist(lp);
	}    

	public void update(LeadProcess lp) {
		em.merge(lp);
	}
	
	public LeadProcess getLeadProcessWithLock(String leadId){
		LeadProcess lp = em.getReference(LeadProcess.class, leadId);
		int r = 0;
		while(true){
			r++;
			boolean repeat = false;
			try{
				em.lock(lp, LockModeType.WRITE);
			}catch(Exception e){
				repeat = true;
			}
			if(!repeat) break;
			if(r > retries) throw new ServiceOrderException("Could not get the lock on the Lead Process for a lead after " + retries + " number of tries. Giving up.");
		}
		return lp;
	}
	
}
