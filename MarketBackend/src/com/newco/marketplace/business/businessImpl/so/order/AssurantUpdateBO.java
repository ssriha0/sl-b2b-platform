package com.newco.marketplace.business.businessImpl.so.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
//import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.SecurityUtil;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class AssurantUpdateBO extends BaseUpdateBO implements IProBuyerUpdateBO {

	public ProcessResponse updateServiceOrder(ServiceOrder matchingSO, ServiceOrder updatedSO, Map<String, Object> buyerParameters, SecurityContext securityContext)
			throws BusinessServiceException {
		
		String clientStatus = (String)buyerParameters.get(OrderConstants.CommonProBuyerParameters.CLIENT_STATUS);
		
		// Check for SP ReOpen scenario
		if (OrderConstants.ClientStatus.UPDATED.equals(clientStatus)) {
			String clientIncidentId = matchingSO.getExternalSoId();
			List<ServiceOrderCustomRefVO> resolvedSOsForIncident =
				serviceOrderBO.getServiceOrdersByCustomRefValue(OrderConstants.INCIDENT_REFERNECE_KEY, clientIncidentId, null, null);
			if (resolvedSOsForIncident != null && !resolvedSOsForIncident.isEmpty()) {
				for (ServiceOrderCustomRefVO customRef : resolvedSOsForIncident) {
					if (customRef.getSoStateId() != null &&
							(customRef.getSoStateId() == OrderConstants.COMPLETED_STATUS || customRef.getSoStateId() == OrderConstants.CLOSED_STATUS ||
							customRef.getSoStateId() == OrderConstants.CANCELLED_STATUS || customRef.getSoStateId() == OrderConstants.VOIDED_STATUS 
							|| customRef.getSoStateId() == OrderConstants.DELETED_STATUS)) {
						// Create New SO with this incident id; triggering the AOP for generating the SP ReOpen Output file alert
						ProcessResponse processResponse = serviceOrderBO.processIncidentReopen(updatedSO, new Integer(0), securityContext);
						return processResponse;
					}
				}
			}
		}
		
		// Process Client Status
		boolean stopProcessing = false;
		if (StringUtils.isNotBlank(clientStatus)) {
			stopProcessing = processClientStatus(matchingSO, updatedSO, securityContext, clientStatus);
		}
		if(stopProcessing){
			String subject = "Incident Update";
			String note= "Update Request has been received but not processed.Please review and take appropriate action.";
			addNote(subject, note, matchingSO.getSoId(), matchingSO.getBuyerResourceId(), Integer.valueOf(OrderConstants.SO_NOTE_PRIVATE_ACCESS));
		}
		
		ProcessResponse response = new ProcessResponse();
		if (!stopProcessing) {
			// Add message notes if they exist
			if (updatedSO.getSoNotes() != null && !updatedSO.getSoNotes().isEmpty()) {
				addNotes(updatedSO.getSoNotes(), matchingSO.getSoId(), matchingSO.getBuyerResourceId());
			}
			// Compare saved and updated SO; and apply updates.
			response = proceedWithCompareAndUpdate(matchingSO, updatedSO, buyerParameters, response, securityContext);
		}
		
		if (StringUtils.isBlank(response.getCode()) || !response.isError()) {
			response.setCode(ServiceConstants.VALID_RC);
			response.setObj(matchingSO.getSoId());
		}
		
		return response;
	}

	protected boolean processClientStatus(ServiceOrder so, ServiceOrder updateServiceOrder, SecurityContext securityContext, String clientStatus)
			throws BusinessServiceException {
		
		boolean stopProcessing = false;
		
		//State of the service live service order
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS
				|| so.getWfStateId() == OrderConstants.EXPIRED_STATUS
				|| so.getWfStateId() == OrderConstants.VOIDED_STATUS
				|| so.getWfStateId() == OrderConstants.DELETED_STATUS) {
			stopProcessing = true;
		} else {
			switch (so.getWfStateId().intValue()) {
			case OrderConstants.DRAFT_STATUS:
				if (clientStatus.equals(OrderConstants.ClientStatus.CLOSED)
						|| clientStatus.equals(OrderConstants.ClientStatus.VOIDED)
						|| clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)) {
					SecurityContext systemSecurityContext = SecurityUtil.getSystemSecurityContext();
					serviceOrderBO.deleteDraftSO(so.getSoId(), systemSecurityContext);
					stopProcessing = true;
				} 
				break;
			case OrderConstants.ROUTED_STATUS:
				if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)
						|| clientStatus.equals(OrderConstants.ClientStatus.VOIDED)) {
					serviceOrderBO.processVoidSOForWS(so.getBuyer().getBuyerId(), so.getSoId(), securityContext);
					stopProcessing = true;
				}
				else if (clientStatus.equals(OrderConstants.ClientStatus.CLOSED)
						|| clientStatus.equals(OrderConstants.ClientStatus.EDITED)) {
					serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.FOLLOWUP_SUBSTATUS, securityContext);
				}
				break;
			case OrderConstants.ACCEPTED_STATUS:
				if (!clientStatus.equals(OrderConstants.ClientStatus.WAITING_SERVICE)
						&& !clientStatus.equals(OrderConstants.ClientStatus.ASSIGNED_TECH)) {
					// Set need Attention substatus
					if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)
							|| clientStatus.equals(OrderConstants.ClientStatus.CLOSED)
							|| clientStatus.equals(OrderConstants.ClientStatus.VOIDED)){
						serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
						// send assurant alert Email
						serviceOrderBO.assurantAlertForNeedsAttentionSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
					}
				}
				break;
			case OrderConstants.PROBLEM_STATUS:
				if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)
						|| clientStatus.equals(OrderConstants.ClientStatus.CLOSED)
						|| clientStatus.equals(OrderConstants.ClientStatus.VOIDED)) {
					serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
					// send assurant alert Email
					serviceOrderBO.assurantAlertForNeedsAttentionSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
				}if(clientStatus.equals(OrderConstants.ClientStatus.UPDATED)){
					stopProcessing = true;
				}
				break;	
			case OrderConstants.ACTIVE_STATUS:
				if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)
						|| clientStatus.equals(OrderConstants.ClientStatus.CLOSED)
						|| clientStatus.equals(OrderConstants.ClientStatus.VOIDED)) {
					
					serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
					// send assurant alert Email
					serviceOrderBO.assurantAlertForNeedsAttentionSubStatus(so.getSoId(), OrderConstants.NEEDS_ATTENTION_SUBSTATUS, securityContext);
				}
				break;
			}
			if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)
					|| clientStatus.equals(OrderConstants.ClientStatus.CLOSED)
					|| clientStatus.equals(OrderConstants.ClientStatus.VOIDED)) {
				String subject = "WS Update";
				String note = "Service status has been changed to " + clientStatus + " via webservice. Please validate and take appropriate action.";
				addNote(subject, note, so.getSoId(), so.getBuyerResourceId(), Integer.valueOf(OrderConstants.SO_NOTE_PRIVATE_ACCESS));
			}
		}
		return stopProcessing;
	}
	
	/**
	 * Deletes existing parts and inserts the parts list passed in.
	 * This should not be done for assurant - using buyer feature set to indicate ignore ws parts updates
	 * 
	 */
	@Override
	public void updateParts(List<Part> parts, ServiceOrder so, SecurityContext securityContext) throws BusinessServiceException {
		Boolean updateParts = buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.WebServices.UPDATE_PARTS);
		List<Part> updatedPartList = new ArrayList<Part>();
		
		if (!isValidStateForUpdate(so)) {
			updateParts = false;
		}
		
		if (null != updateParts && updateParts.booleanValue()) {
			if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
			throw new BusinessServiceException("Error updating service parts, the service order is not in an open state.");
			}
			try {
				// before deleting parts, replace new Order's shipping method with Old Order value
				// as we don't want to update shipping method value 
				ServiceOrder oldSO  = serviceOrderBO.getServiceOrder(so.getSoId());
								
				if (oldSO.getParts() != null && oldSO.getParts().size() > 0) {
					Part oldPart = oldSO.getParts().get(0);
					String oldShippingMethod = ServiceLiveStringUtils.getKeyVal(oldPart.getPartDs(), "ShippingMethod", "!", ":"); 
					oldShippingMethod = oldShippingMethod!= null ? oldShippingMethod : "";
					String oldShippingMethodSubStr = oldShippingMethod;
					if((oldShippingMethod.substring(0, 2)).indexOf(")")>=0){
						oldShippingMethodSubStr = oldShippingMethod.substring(2);
					}
					
					
					if (parts != null && parts.size() > 0) {
						for (Part part : parts) {
							String newPartDs = part.getPartDs();
							String newShippingMethod = ServiceLiveStringUtils.getKeyVal(newPartDs, "ShippingMethod", "!", ":"); 
							newShippingMethod = newShippingMethod!= null ? newShippingMethod.trim() : "";
							String newShippingMethodSubStr = newShippingMethod;
							if((newShippingMethod.substring(0, 2)).indexOf(")")>=0){
								newShippingMethodSubStr = newShippingMethod.substring(2);
							}
							newPartDs = newPartDs.replaceAll(newShippingMethodSubStr, oldShippingMethodSubStr); 
							part.setPartDs(newPartDs);
							updatedPartList.add(part);
						}
						
					}
					
				}
				
				serviceOrderDao.deleteParts(so.getSoId());
				//remove any existing pickup locations from the DB - these will be recreated when we insert parts
				if (so.getParts() != null && so.getParts().size() > 0) {
					for (Part part : so.getParts()) {
						if (part.getPickupLocation() != null) {
							serviceOrderDao.deleteContactLocation(so.getSoId(), part.getPickupContact().getContactId(),part.getPickupLocation().getLocationId());
							serviceOrderDao.deletePartPickupContact(so.getSoId(), part.getPickupContact().getContactId());
							serviceOrderDao.deletePartPickupLocation(so.getSoId(), part.getPickupLocation().getLocationId());						
							serviceOrderBO.processChangePartPickupLocation(so.getSoId(), securityContext);
						}
					}
				}
			} catch (DataServiceException e1) {
				logger.error(e1.getMessage(), e1);
			}
			if (updatedPartList != null && updatedPartList.size() > 0) {
				for (Part part : updatedPartList) {
					part.setSoId(so.getSoId());
					try {
						if (part.getPickupLocation() != null) {
							part.setProviderBringPartInd(new Boolean(true));
						}
						serviceOrderDao.insertPart(part);
					} catch (DataServiceException e) {
						logger.error("Ignoring - no business rule. Error inserting new parts for so:" + so.getSoId(), e);
					}
				}
			}
		}
	}
	
	/**
	 * Updates the service location information for the service order. This method implements all the specific business
	 * requirements for updating service location information of Assurant orders.
	 * 
	 * @param matchingSO
	 * @param updatedSO
	 * @param response
	 * @param securityContext
	 * @param bo
	 * @param templateName
	 * @return
	 * @throws BusinessServiceException
	 */
	@Override
	protected ProcessResponse updateServiceLocation(ServiceOrder matchingSO,
			ServiceOrder updatedSO, ProcessResponse response,
			SecurityContext securityContext, String templateName) throws BusinessServiceException {
		
		//Do not update zip code, so get the old zip code if it's changed
		if (!matchingSO.getServiceLocation().getZip().equals(updatedSO.getServiceLocation().getZip())) {
			updatedSO.getServiceLocation().setZip(matchingSO.getServiceLocation().getZip());
		}
		//Do not update zip4 code, so get the old zip4 code if it's changed
		if (!matchingSO.getServiceLocation().getZip4().equals(updatedSO.getServiceLocation().getZip4())) {
			updatedSO.getServiceLocation().setZip4(matchingSO.getServiceLocation().getZip4());
		}
		return super.updateServiceLocation(matchingSO, updatedSO, response, securityContext, templateName);
	}

	/* Override default logic defined in BaseUpdateBO
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.businessImpl.so.order.BaseUpdateBO#updateCustomRef(java.util.List, com.newco.marketplace.dto.vo.serviceorder.ServiceOrder, com.newco.marketplace.auth.SecurityContext)
	 */
	@Override
	protected void updateCustomRef(List<ServiceOrderCustomRefVO> changedCustomRefs, ServiceOrder matchingSO, SecurityContext securityContext)
			throws BusinessServiceException {
		
		// Never update PARTS LABOR FLAG field
		findAndRemoveCustomRefField(changedCustomRefs, OrderConstants.CUSTOM_REF_PARTS_LABOR_FLAG);
		
		// If SO is not in "Draft" status, do not update the "CLASS CODE"
		if (matchingSO.getWfStateId() != null && matchingSO.getWfStateId() != OrderConstants.DRAFT_STATUS) {
			findAndRemoveCustomRefField(changedCustomRefs, OrderConstants.CUSTOM_REF_CLASS_CODE);
		}
		
		// Invoke default logic if there is any custom reference field to update
		if (changedCustomRefs.size() > 0) {
			super.updateCustomRef(changedCustomRefs, matchingSO, securityContext);
		}
	}
	
	private void findAndRemoveCustomRefField(List<ServiceOrderCustomRefVO> customRefs, String refType) {
		int customRefIndex = -1;
		int refIndex = 0;
		for (ServiceOrderCustomRefVO customRef : customRefs) {
			if (refType.equals(customRef.getRefType())) {
				customRefIndex = refIndex;
			}
			++refIndex;
		}
		if (customRefIndex != -1) {
			customRefs.remove(customRefIndex);
		}
	}
	
	@Override
	public ProcessResponse updateTaskNameComments(ServiceOrder matchingSO,List<ServiceOrderTask> modifiedList, 
														SecurityContext securityContext) throws BusinessServiceException {
		
		ProcessResponse response = new ProcessResponse();
		if (matchingSO.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| matchingSO.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| matchingSO.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
			throw new BusinessServiceException("Error updating service tasks Name Comments, the service order is not in an open state.");
		}
		
		if (!isValidStateForUpdate(matchingSO)) {
			String message = "cannot update Parts for Assurant as Status of order is Routed/Accepted/Active";
			response = getProcessResponse(ServiceConstants.VALID_RC, message);
			return response;
		}
				
		List<ServiceOrderTask> oldTaskList = matchingSO.getTasks();
		//delete old tasks and insert modified task as new one
		if (modifiedList != null && modifiedList.size() > 0) {
			for (ServiceOrderTask newTask : modifiedList) {
				if(oldTaskList!= null && oldTaskList.size()>0){
					for(ServiceOrderTask existingTask : oldTaskList){
						if (newTask.getSkillNodeId().intValue() == existingTask.getSkillNodeId().intValue()
								&& newTask.getServiceTypeId().intValue() == existingTask.getServiceTypeId().intValue()) {
							
							serviceOrderDao.deleteTask(existingTask);
							ServiceOrderTask taskTobeInserted = existingTask;
							taskTobeInserted.setSoId(matchingSO.getSoId());
							taskTobeInserted.setTaskComments(newTask.getTaskComments());
							taskTobeInserted.setTaskName(newTask.getTaskName());
							try {
								//RandomGUID random = new RandomGUID();
								//taskTobeInserted.setTaskId(random.generateGUID().intValue());
								serviceOrderDao.insertTask(taskTobeInserted);
							} catch (Exception e) {
								logger.error("Ignoring - no business rules for error " + e.getMessage(), e);
								response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
								response.setMessage(e.getMessage());
							}
							
						}
						
					}
				}

			}
		}
		
		return response;
	}
	
	/**
	 * This method implements the superclass's updateSowTitle method.
	 * 
	 * @param matchingSO
	 * @param string
	 * @param securityContext
	 */
	@Override
	protected void updateSowTitle(ServiceOrder matchingSO, String newTitle,
			SecurityContext securityContext) {
		// If SO is in "Draft" status, update the sow title
		if (matchingSO.getWfStateId() != null && matchingSO.getWfStateId() == OrderConstants.DRAFT_STATUS) {
			soUpdateBO.updateSowTitle(matchingSO, newTitle, securityContext);
		}
	}
	
	/**
	 * Updates the tasks
	 * 
	 * @param matchingSO
	 * @param updatedSO
	 * @param response
	 * @param securityContext
	 * @param changed
	 * @return ProcessResponse
	 * @throws BusinessServiceException
	 */
	@Override
	protected ProcessResponse updateTasks(ServiceOrder matchingSO, ServiceOrder updatedSO, 
			SecurityContext securityContext, Map<String, Object> changed) throws BusinessServiceException {
		if (isValidStateForUpdate(matchingSO)) {
			return super.updateTasks(matchingSO, updatedSO, securityContext, changed);
		}
		else{
			String message = "cannot update task for Assurant as Status of order is Routed/Accepted/Active";
			ProcessResponse response = getProcessResponse(ServiceConstants.VALID_RC, message);
			return response;
		}
	}

	/**
	 * Gets a process response object
	 * 
	 * @param infoMessage
	 * @return
	 */
	private ProcessResponse getProcessResponse(String code, String message) {
		logger.info(message);
		ProcessResponse response = new ProcessResponse();
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

	/**
	 * Checks if the service order is in updateable status
	 * 
	 * @param matchingSO
	 * @return true if the service is not in routed/accepted/active state
	 */
	private boolean isValidStateForUpdate(ServiceOrder matchingSO) {
		if(matchingSO.getWfStateId() == OrderConstants.ROUTED_STATUS
				|| matchingSO.getWfStateId() == OrderConstants.ACCEPTED_STATUS
				|| matchingSO.getWfStateId() == OrderConstants.ACTIVE_STATUS){
			return false;
		}
		return true;
	}

}
