package com.servicelive.orderfulfillment.dao;

import com.servicelive.orderfulfillment.domain.LeadProcess;

public interface ILeadProcessDao {
	public LeadProcess getLeadProcess(String leadId);
	public void save(LeadProcess lp);
	public void update(LeadProcess lp);
	public LeadProcess getLeadProcessWithLock(String soId);
}
