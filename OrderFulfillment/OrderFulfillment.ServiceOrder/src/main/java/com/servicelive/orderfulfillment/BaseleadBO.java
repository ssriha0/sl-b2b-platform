package com.servicelive.orderfulfillment;

import com.servicelive.orderfulfillment.dao.ILeadDao;
import com.servicelive.orderfulfillment.dao.ILeadProcessDao;
import com.servicelive.orderfulfillment.jbpm.ILeadWFManager;

public class BaseleadBO {
	
	protected ILeadWFManager workflowManager;
	protected ILeadDao leadDao;
	protected ILeadProcessDao leadProcessDao;
	
	public void setWorkflowManager(ILeadWFManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	public void setLeadDao(ILeadDao leadDao) {
		this.leadDao = leadDao;
	}

	public void setLeadProcessDao(ILeadProcessDao leadProcessDao) {
		this.leadProcessDao = leadProcessDao;
	}
}
