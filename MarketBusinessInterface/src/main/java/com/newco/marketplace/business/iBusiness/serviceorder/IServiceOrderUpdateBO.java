package com.newco.marketplace.business.iBusiness.serviceorder;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IServiceOrderUpdateBO {

	ProcessResponse updateSchedule(ServiceOrder so, ServiceOrder updatedSO, SecurityContext securityContext) throws BusinessServiceException;

	ProcessResponse updateServiceContact(ServiceOrder updatedOrder, ServiceOrder so, SecurityContext securityContext) throws BusinessServiceException;
	public void updateContact(String soId, Contact contact);
	
	void updateCustomRef(List<ServiceOrderCustomRefVO> newRefs, ServiceOrder so, SecurityContext securityContext) throws BusinessServiceException;

	void updateParts(List<Part> newParts, ServiceOrder so, SecurityContext securityContext) throws BusinessServiceException;
	
	ProcessResponse updateTasks(List<ServiceOrderTask> tasksDeleted, List<ServiceOrderTask> tasksAdded, ServiceOrder so, Double spendLimitLabor, SecurityContext securityContext) throws BusinessServiceException;
	
	ProcessResponse updateServiceLocation(ServiceOrder updatedOrder, ServiceOrder so, String templateName, Double spendLimitLabor, SecurityContext securityContext) throws BusinessServiceException;
	
	void updateProviderInstructions(ServiceOrder so, String instructions, SecurityContext securityContext);
	
	void updateDescription(ServiceOrder so, String description, SecurityContext securityContext);

	// update  name & comments for each task -- this  check and update is only for Assurant for now
	ProcessResponse updateTaskNameComments(ServiceOrder matchingSO,List<ServiceOrderTask> modifiedList, SecurityContext securityContext) throws BusinessServiceException;
	/**
	 * Updates the price
	 * @param updatedSO
	 * @param matchingSO
	 * @param securityContext
	 * @param changed
	 * @throws BusinessServiceException
	 */
	ProcessResponse updatePrice(ServiceOrder updatedSO,ServiceOrder matchingSO,Map<String, Object> changed,SecurityContext securityContext) throws BusinessServiceException ; 
	
	/**
	 * Updates the scope of work title for the service order
	 * 
	 * @param so Service order for which title needs to be updated
	 * @param newTitle Updated title
	 * @param securityContext
	 */
	int updateSowTitle(ServiceOrder so, String newTitle, SecurityContext securityContext);
	//SL 17504 Creating a method to update the sku indicator column of  so_workflow_controls table for edit serive order
	public void updateSkuIndicator(String soId) ;
	//This  method to fetch the sku indicator column of  so_workflow_controls table to identify the type of  serive order
	public Boolean fetchSkuIndicatorFromSoWorkFlowControl(String soID);
}
