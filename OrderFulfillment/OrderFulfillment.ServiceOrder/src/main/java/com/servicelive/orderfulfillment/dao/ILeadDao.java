package com.servicelive.orderfulfillment.dao;

import javax.persistence.EntityManager;

import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.LeadElement;

public interface ILeadDao {
	public LeadHdr getLeadObject(String leadId);
    public void setEntityManager(EntityManager em);
	public void save(LeadElement element);
	public void saveLead(LeadHdr lead);
	public void update(LeadElement element);
	public void updateLead(LeadHdr lead);
	public void delete( LeadElement element );
	public void refresh(LeadElement element);	
}