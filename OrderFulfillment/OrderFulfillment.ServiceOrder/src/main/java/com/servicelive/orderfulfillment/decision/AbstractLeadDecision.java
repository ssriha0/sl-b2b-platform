package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.dao.ILeadDao;
import com.servicelive.orderfulfillment.domain.LeadHdr;

public abstract class AbstractLeadDecision implements DecisionHandler {
   
	ILeadDao leadDao;

    protected LeadHdr getLeadObject(OpenExecution execution) {
        String leadId = (String) execution.getVariable(ProcessVariableUtil.PVKEY_LEAD_ID);
        return leadDao.getLeadObject(leadId);
    }

	public void setLeadDao(ILeadDao leadDao) {
		this.leadDao = leadDao;
	}    
}
