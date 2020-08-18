package com.newco.marketplace.business.businessImpl.so.order;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.SecurityUtil;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class OMSUpdateBO extends BaseUpdateBO implements IProBuyerUpdateBO {

	public ProcessResponse updateServiceOrder(ServiceOrder matchingSO, ServiceOrder updatedSO, Map<String, Object> buyerParameters, SecurityContext securityContext)
			throws BusinessServiceException {
		
		// First: Add message notes if they exist
		if (updatedSO.getSoNotes() != null && !updatedSO.getSoNotes().isEmpty()) {
			addNotes(updatedSO.getSoNotes(), matchingSO.getSoId(), matchingSO.getBuyerResourceId());
		}
		
		// Process client status
		String clientStatus = (String)buyerParameters.get(OrderConstants.CommonProBuyerParameters.CLIENT_STATUS);
		ProcessResponse response = new ProcessResponse();
		boolean stopProcessing = false;
		if (clientStatus != null && clientStatus.length() > 0) {
			stopProcessing = processClientStatus(matchingSO, updatedSO, securityContext, clientStatus);
		}
		
		if (!stopProcessing) {
			updatedSO.setClientStatus(clientStatus);
			Double soProjectBalance = serviceOrderBO.getSOProjectBalance(matchingSO.getSoId());
			updatedSO.setSoProjectBalance(soProjectBalance);
			response = proceedWithCompareAndUpdate(matchingSO, updatedSO, buyerParameters, response, securityContext);
			changed = matchingSO.compareJobCodesAndPrice(updatedSO);
			if ((changed.containsKey(OrderConstants.JOBCODE_ADDED) || changed.containsKey(OrderConstants.JOBCODE_DELETED)) &&
					(changed.containsKey(OrderConstants.PRICE_INCREASE)|| changed.containsKey(OrderConstants.PRICE_DECREASE))) {
				ProcessResponse priceResponse = updatePrice(updatedSO,  matchingSO,changed,securityContext);		
				response = rollupResponse(response, priceResponse);
			}			
			if (changed.containsKey(OrderConstants.JOBCODE_ADDED) || changed.containsKey(OrderConstants.JOBCODE_DELETED)) {
				ProcessResponse taskResponse = updateJobCodes(matchingSO, updatedSO, securityContext, changed);
				response = rollupResponse(response, taskResponse);
			}
		}
		
		if (response.getCode() == null || response.getCode().equals("") || !response.isError()) {
			response.setCode(ServiceConstants.VALID_RC);
			response.setObj(matchingSO.getSoId());
		}
		
		return response;
	}

	protected boolean processClientStatus(ServiceOrder so, ServiceOrder updateServiceOrder, SecurityContext securityContext, String clientStatus) throws BusinessServiceException {
		boolean stopProcessing = false;
		//State of the service live service order
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				) {
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
					serviceOrderBO.processCancelSOInAcceptedForWS(so.getSoId(), securityContext);					
				}
				break;
			case OrderConstants.ACTIVE_STATUS:
				if (clientStatus.equals(OrderConstants.ClientStatus.CLOSED)
						|| clientStatus.equals(OrderConstants.ClientStatus.VOIDED)) {
					serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.VALIDATE_POS_CANCELLATION, securityContext);
				}else if(clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)){
					serviceOrderBO.processCancelSOInActiveForWS(so.getSoId(), securityContext);
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
			if (clientStatus.equals(OrderConstants.ClientStatus.CANCELLED)){
				stopProcessing = true;
			} 
		}
		return stopProcessing;
	}
	
	/**
	 * Different rules are applied if tasks are deleted or added
	 */
	/**
	 * Updates the JobCodes as Tasks. This is triggered only when a Jobcode is added/Deleted
	 * @param matchingSO
	 * @param updatedSO
	 * @param response
	 * @param securityContext
	 * @param changed
	 * @return ProcessResponse
	 * @throws BusinessServiceException
	 */
	protected ProcessResponse updateJobCodes(ServiceOrder so, ServiceOrder updatedSO, 
			SecurityContext securityContext, Map<String, Object> changed) throws BusinessServiceException {
		List<ServiceOrderTask> tasksDeleted = null;
		ProcessResponse response = new ProcessResponse();
		if (changed.containsKey(OrderConstants.JOBCODE_DELETED)) {
			tasksDeleted = (List<ServiceOrderTask>) changed.get(OrderConstants.JOBCODE_DELETED);
		}
		List<ServiceOrderTask> tasksAdded = null;
		if (changed.containsKey(OrderConstants.JOBCODE_ADDED)) {
			tasksAdded = (List<ServiceOrderTask>) changed.get(OrderConstants.JOBCODE_ADDED);
		}
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS) {
			throw new BusinessServiceException("Error updating service tasks, the service order is not in an open state.");
		}
		//call for logging purpose
		serviceOrderBO.processUpdateTask(so,securityContext);
		//delete tasks
		if (tasksDeleted != null && tasksDeleted.size() > 0) {
			for (ServiceOrderTask task : tasksDeleted) {
				serviceOrderDao.deleteTask(task);
			}
		}
		//add tasks
		if (tasksAdded != null && tasksAdded.size() > 0) {
			for (ServiceOrderTask task : tasksAdded) {
				task.setSoId(so.getSoId());
				try {
//					RandomGUID random = new RandomGUID();
//					task.setTaskId(random.generateGUID().intValue());
					if (task.getSkillNodeId() != null
							&& task.getSkillNodeId() <= 0) {
						task.setSkillNodeId(null);
					}
					if (task.getServiceTypeId() != null
							&& task.getServiceTypeId() == -1) {
						task.setServiceTypeId(null);
					}
					serviceOrderDao.insertTask(task);
				} catch (Exception e) {
					logger.error("Ignoring - no business rules for error " + e.getMessage(), e);
					response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
					response.setMessage(e.getMessage());
				}
			}
		}
		switch (so.getWfStateId().intValue()) {
			case OrderConstants.ROUTED_STATUS:
				response = serviceOrderBO.processReRouteSOForWS(so, securityContext);
				break;
			case OrderConstants.COMPLETED_STATUS:
			//	if completed_date!=null, remove final price values
				if(so.getCompletedDate() != null){
					so.setLaborFinalPrice(0.0);
					so.setPartsFinalPrice(0.0);
					try {
						serviceOrderDao.updateFinalPrice(so);
					} catch (DataServiceException e) {
						throw new BusinessServiceException(
								"General Exception @ServiceOrderUpdateBO.updateTasks() due to"+ e.getMessage(), e);
					}
				}
			//DO NOT PUT break here. The code below should also be executed for completed status.
			case OrderConstants.ACCEPTED_STATUS:
			case OrderConstants.ACTIVE_STATUS:
			case OrderConstants.PROBLEM_STATUS:				
				//change status to Problem and substatus to Scope Change
				SecurityContext systemSecurityContext = SecurityUtil.getSystemSecurityContext();
				serviceOrderBO.reportProblem(so.getSoId(),OrderConstants.SCOPE_CHANGE, OrderConstants.PROBLEM_ADDITIONAL_COMMENT, systemSecurityContext.getVendBuyerResId(), systemSecurityContext.getRoleId(), OrderConstants.OMS_UPDATE, systemSecurityContext.getUsername(),true, systemSecurityContext);
				response = serviceOrderBO.processChangeOfScope(so, securityContext);			
				break;		
		}
		if (response.getCode() == null || response.getCode().length() == 0) {
			response.setCode(ServiceConstants.VALID_RC);
		}
		return response;
	}
	/**
	 * Updates the price
	 * @param updatedSO
	 * @param matchingSO
	 * @param changed
	 * @param securityContext
	 * @throws BusinessServiceException
	 */
	protected ProcessResponse updatePrice(ServiceOrder updatedSO,ServiceOrder matchingSO,Map<String, Object> changed,SecurityContext securityContext)
			throws BusinessServiceException {		
		return soUpdateBO.updatePrice(updatedSO,matchingSO, changed, securityContext);
	}
		
	/**
	 * Updates the task
	 * @param updatedSO
	 * @param matchingSO
	 * @param changed
	 * @param securityContext
	 * @throws BusinessServiceException
	 */
	@Override
	protected ProcessResponse updateTasks(ServiceOrder updatedSO,ServiceOrder matchingSO,SecurityContext securityContext,Map<String, Object> changed)
			throws BusinessServiceException {		
		// right now its implemented for Assurant/Pro buyers , this is triggered when any change occurs in the so_tasks data--
		ProcessResponse processResp = new ProcessResponse();		
		return processResp;
	}

}
