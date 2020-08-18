package com.servicelive.orderfulfillment;

import java.util.List;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.dao.IServiceOrderProcessDao;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.jbpm.IServiceOrderWFManager;

public class BaseServiceOrderBO {

	protected IServiceOrderWFManager workflowManager;
	protected IServiceOrderDao serviceOrderDao;
	protected IServiceOrderProcessDao serviceOrderProcessDao;

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
	public void setServiceOrderProcessDao(IServiceOrderProcessDao serviceOrderProcessDao) {
		this.serviceOrderProcessDao = serviceOrderProcessDao;
	}
	public void setWorkflowManager(IServiceOrderWFManager workflowManager) {
		this.workflowManager = workflowManager;
	}
	
	
	protected String getServiceOrderGroupProcessId(String groupId){
		// get process record from the database
		List<ServiceOrderProcess> soProcesses = serviceOrderProcessDao.getProcessMapByGroupId(groupId);
		if (soProcesses == null || soProcesses.size() == 0)
			throw new ServiceOrderException("Did not find any service order process for this group id");
		ServiceOrderProcess sop = soProcesses.get(0);
		
		// get process id for the group order
		String pid = sop.getGroupProcessId();
		if (pid == null) 
			throw new ServiceOrderException("Process id isn't found for the order");
		
		return pid;
	}
}
