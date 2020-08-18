package com.servicelive.orderfulfillment.dao;

import java.util.List;

import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public interface IServiceOrderProcessDao {
	public ServiceOrderProcess getServiceOrderProcess(String soId);
	public void save(ServiceOrderProcess sop);
	public void update(ServiceOrderProcess sop);
    public List<ServiceOrderProcess> findLikeServiceOrders(ServiceOrder serviceOrder);
	public List<ServiceOrderProcess> getProcessMapByGroupId(String groupId);
	public List<ServiceOrderProcess> getProcessMapByGroupIdWithLock(String groupId);
	public boolean unlockByGroupId(String groupId);
	public ServiceOrderProcess getServiceOrderProcessWithLock(String soId);

    public void markGroupNotSearchable(String soGroupId);
	public SOWorkflowControls getSOWorkflowControls(String soId);
}
