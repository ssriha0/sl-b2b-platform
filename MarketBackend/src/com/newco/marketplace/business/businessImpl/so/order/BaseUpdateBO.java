package com.newco.marketplace.business.businessImpl.so.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpdateBO;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public abstract class BaseUpdateBO extends BaseOrderBO {

	public IServiceOrderBO serviceOrderBO;
	public ServiceOrderDao serviceOrderDao;
	public IBuyerFeatureSetBO buyerFeatureSetBO;
	public IServiceOrderUpdateBO soUpdateBO;
	public Map<String, Object> changed;
	
	protected ProcessResponse proceedWithCompareAndUpdate(ServiceOrder matchingSO, ServiceOrder updatedSO, Map<String, Object> buyerParameters,
															ProcessResponse response, SecurityContext securityContext)
			throws BusinessServiceException {

		//use comparable to identify change areas
		changed = matchingSO.compareTo(updatedSO);
		
		//check state of the so and process changes		
		//For Assurant, for completed status updates aren't processed.Hence code wont reach here.
		//For OMS, only process Task updates for completed status.
		if( matchingSO.getWfStateId() != OrderConstants.COMPLETED_STATUS){
			if (changed.containsKey("RESCHEDULE")) {
				ProcessResponse rescheduleResponse = soUpdateBO.updateSchedule(matchingSO, updatedSO, securityContext);
				response = rollupResponse(response, rescheduleResponse);
			}
			
			if (changed.containsKey("LOCATION")) {
				String templateName = (String)buyerParameters.get(OrderConstants.CommonProBuyerParameters.TEMPLATE_NAME);
				response = updateServiceLocation(matchingSO, updatedSO, response,
						securityContext, templateName);
			}
			
			if (changed.containsKey("CONTACT")) {
				ProcessResponse contactResponse = soUpdateBO.updateServiceContact(updatedSO, matchingSO, securityContext);
				response = rollupResponse(response, contactResponse);
			}
			if (changed.containsKey("PARTS")) {
				List<Part> parts = (List<Part>) changed.get("PARTS");
				updateParts(parts, matchingSO, securityContext);
			}
			
			if (changed.containsKey("CUSTOM_REF")) {
				updateCustomRef((List<ServiceOrderCustomRefVO>) changed.get("CUSTOM_REF"), matchingSO, securityContext);
			}
			if (changed.containsKey("PROVIDER_INSTRUCTIONS")) {
				soUpdateBO.updateProviderInstructions(matchingSO, (String) changed.get("PROVIDER_INSTRUCTIONS"), securityContext);
			}
			if (changed.containsKey("SO_DESCRIPTION")) {
				soUpdateBO.updateDescription(matchingSO, (String) changed.get("SO_DESCRIPTION"), securityContext);
			}
			if (changed.containsKey("TASKS_NAME_COMMENTS")) {
				updateTaskNameComments(matchingSO, (List<ServiceOrderTask>) changed.get("TASKS_NAME_COMMENTS"), securityContext);
			}		
			if (changed.containsKey("SO_TITLE")) {
				updateSowTitle(matchingSO, (String) changed.get("SO_TITLE"), securityContext);
			}
		} 		
		//specifying update task towards the end so that the substatus is not updated by other update methods
		if (changed.containsKey("TASKS_DELETED") || changed.containsKey("TASKS_ADDED")) {
			ProcessResponse  taskResponse = updateTasks(matchingSO, updatedSO, securityContext, changed);
			response = rollupResponse(response, taskResponse);
		}
		return response;
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
	@SuppressWarnings("unchecked")
	protected ProcessResponse updateTasks(ServiceOrder matchingSO,
			ServiceOrder updatedSO, SecurityContext securityContext, Map<String, Object> changed)
			throws BusinessServiceException {
		List<ServiceOrderTask> tasksDeleted = null;
		ProcessResponse response = new ProcessResponse();
		if (changed.containsKey("TASKS_DELETED")) {
			tasksDeleted = (List<ServiceOrderTask>) changed.get("TASKS_DELETED");
		}
		List<ServiceOrderTask> tasksAdded = null;
		if (changed.containsKey("TASKS_ADDED")) {
			tasksAdded = (List<ServiceOrderTask>) changed.get("TASKS_ADDED");
		}
		ProcessResponse taskResponse = soUpdateBO.updateTasks(tasksDeleted, tasksAdded, matchingSO, updatedSO.getSpendLimitLabor(), securityContext);
		response = rollupResponse(response, taskResponse);
		return response;
	}

	/**
	 * Default logic of updating custom reference; delete all old fields, insert new ones
	 * 
	 * @param changedCustomRefs
	 * @param matchingSO
	 * @param securityContext
	 * @throws BusinessServiceException
	 */
	protected void updateCustomRef(List<ServiceOrderCustomRefVO> changedCustomRefs, ServiceOrder matchingSO, SecurityContext securityContext)
			throws BusinessServiceException {
		soUpdateBO.updateCustomRef(changedCustomRefs, matchingSO, securityContext);
	}
	
	public  void updateParts(List<Part> parts, ServiceOrder matchingSO,
			SecurityContext securityContext) throws BusinessServiceException{
		soUpdateBO.updateParts(parts, matchingSO, securityContext);
		
	}

	/**
	 * Updates the service location information for the service order. If there is any specific business requirements
	 * for updating service location information, implement those business requirements in client specific updateBO.
	 * ex. AssurantUpdateBO.java
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
	protected ProcessResponse updateServiceLocation(ServiceOrder matchingSO,
			ServiceOrder updatedSO, ProcessResponse response,
			SecurityContext securityContext, String templateName) throws BusinessServiceException {
		ProcessResponse locationResponse = soUpdateBO.updateServiceLocation(updatedSO, matchingSO, templateName, updatedSO.getSpendLimitLabor(), securityContext);
		response = rollupResponse(response, locationResponse);
		return response;
	}
	
	/**
	 * If the title of the scope of work of service order needs to be updated, this method
	 * needs to be implemented by the subclass.
	 * 
	 * @param matchingSO
	 * @param newTitle
	 * @param securityContext
	 */
	protected void updateSowTitle(ServiceOrder matchingSO, String newTitle,
			SecurityContext securityContext) {
		// TODO Subclass will implement this method if required
		
	}
	
	protected void addNotes(List<ServiceOrderNote> updatedSONotes, String soId, Integer buyerResourceId) {
		for (ServiceOrderNote note : updatedSONotes) {
			//RandomGUID random = new RandomGUID();
			try {
				//note.setNoteId(random.generateGUID().longValue());
				note.setSoId(soId);
				note.setPrivateId(1);
				note.setEntityId(buyerResourceId);
				serviceOrderDao.insertNote(note);
			} catch (Exception e) {
				// Log error, and try adding remaining notes
				logger.error("Error saving note - no business rule for exception" + e.getMessage(), e);
			}
		}
	}
	
	protected void addNote(String subject, String note, String soId, Integer buyerResourceId, Integer privateInd) {
		
		ServiceOrderNote soNote = new ServiceOrderNote();
		soNote.setSubject(subject);
		soNote.setCreatedByName(OrderConstants.NEWCO_DISPLAY_SYSTEM);
		soNote.setCreatedDate(new Date());
		soNote.setNote(note);
		soNote.setRoleId(new Integer(OrderConstants.BUYER_ROLEID));
		soNote.setSoId(soId);
		soNote.setEntityId(buyerResourceId);
		soNote.setNoteTypeId(new Integer(3));
		soNote.setPrivateId(privateInd);
		//RandomGUID random = new RandomGUID();
		
		try {
			//soNote.setNoteId(random.generateGUID().longValue());
			serviceOrderDao.insertNote(soNote);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	
	protected ProcessResponse rollupResponse(ProcessResponse response, ProcessResponse in) {
		if (in != null && response != null) {
			if (in.getMessages() != null) {
				response.appendMessages(in.getMessages());
			}
			if (in.getWarnings() != null) {
				if (response.getWarnings() == null) {
					response.setWarnings(new ArrayList<String>());
				}
				response.getWarnings().addAll(in.getWarnings());
			}
			if (in.isError()) {
				response.setCode(in.getCode());
			}
		}
		return response;
	}
	
	public ProcessResponse updateTaskNameComments(ServiceOrder matchingSO,List<ServiceOrderTask> modifiedList,
			SecurityContext securityContext) throws BusinessServiceException {
		// right now its implemented only for Assurant -- overwritten in AssurantUpdateBO
		ProcessResponse processResp = new ProcessResponse();
		
		return processResp;
	}
		
	@Override
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	@Override
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	/**
	 * @return the soUPBOAOP
	 */
	public IServiceOrderUpdateBO getSoUpdateBO() {
		return soUpdateBO;
	}

	/**
	 * @param soUpdateBO the soUpdateBO to set
	 */
	public void setSoUpdateBO(IServiceOrderUpdateBO soUpdateBO) {
		this.soUpdateBO = soUpdateBO;
	}
}
