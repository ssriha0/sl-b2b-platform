package com.servicelive.orderfulfillment.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.LeadElement;

public class LeadDao implements ILeadDao {
	protected final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext()
    private EntityManager em;
    
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public LeadHdr getLeadObject(String leadId){
		return em.find(LeadHdr.class, leadId);
	}
		
	public void save(LeadElement element ) {
		em.persist(element);
	}
	
	public void saveLead(LeadHdr lead) {
		em.persist(lead);
	}
	
	public void update(LeadElement element){
		logger.info("Before merging of info");
		logger.info("Before merger resource id");
		em.merge(element);
	}
	public void updateLead(LeadHdr leadHdr){
		logger.info("Before merging of info from cancellation");
		em.merge(leadHdr);
		logger.info("After merging of info from cancellation");
	}
	public void delete(LeadElement element ) {
		em.remove(element);
	}
	
	public void refresh(LeadElement element){
		em.refresh(element);
	}

}