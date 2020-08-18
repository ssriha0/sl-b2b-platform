package com.newco.marketplace.business.businessImpl.so.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.BackgroundCheckParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.client.IClientBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpdateBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.persistence.iDao.logging.ILoggingDao;
import com.newco.marketplace.persistence.iDao.skillTree.MarketplaceSearchDAO;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.utils.MoneyUtil;
//import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.SecurityUtil;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.routing.tiered.schedulermanager.TierRoutingSchedulerManager;
import com.servicelive.routing.tiered.services.TierRouteController;

public class ServiceOrderUpdateBO implements IServiceOrderUpdateBO {
	
	private static final Logger logger = Logger.getLogger(ServiceOrderUpdateBO.class);
	
	private IServiceOrderBO serviceOrderBO;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private ServiceOrderDao serviceOrderDAO;
	private ILoggingDao loggingDAO;
	
	private IBuyerSOTemplateBO soTemplateBO;
	private IProviderSearchBO provSearchObj;
	private IMasterCalculatorBO masterCalcObj;
	private TierRouteController trRouteController;
	private TierRoutingSchedulerManager trSchedulerMgr;
	
	private IClientBO clientBO;
	
	
	/**
	 * Expects new custom reference objects to insert for the service order
	 */
	public void updateCustomRef(List<ServiceOrderCustomRefVO> newRefs, ServiceOrder so, SecurityContext securityContext) throws BusinessServiceException {
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
			throw new BusinessServiceException("Error updating service custom ref, the service order is not in an open state.");
		}
		for (ServiceOrderCustomRefVO ref : newRefs) {
			ref.setsoId(so.getSoId());
			//delete any custom refs for key to prevent dups
			try {
				serviceOrderDAO.deleteCustomRefByBuyerRefID(ref);
				serviceOrderDAO.insertCustomRef(ref);
			} catch (Exception e) {
				logger.error("Ignoring - no business rule. Error inserting new custom ref for so:" + so.getSoId(), e);
			}
		}
		
	}

	/**
	 * Deletes existing parts and inserts the parts list passed in.
	 * This should not be done for assurant - using buyer feature set to indicate ignore ws parts updates
	 * 
	 */
	public void updateParts(List<Part> parts, ServiceOrder so, SecurityContext securityContext) throws BusinessServiceException {
		Boolean updateParts = buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.WebServices.UPDATE_PARTS);
		if (null != updateParts && updateParts.booleanValue()) {
			if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
			throw new BusinessServiceException("Error updating service parts, the service order is not in an open state.");
			}
			try {
				serviceOrderDAO.deleteParts(so.getSoId());
				//remove any existing pickup locations from the DB - these will be recreated when we insert parts
				if (so.getParts() != null && so.getParts().size() > 0) {
					for (Part part : so.getParts()) {
						if (part.getPickupLocation() != null) {
							serviceOrderDAO.deleteContactLocation(so.getSoId(), part.getPickupContact().getContactId(),part.getPickupLocation().getLocationId());
							serviceOrderDAO.deletePartPickupPhones(so.getSoId(), part.getPickupContact().getContactId());
							serviceOrderDAO.deletePartPickupContact(so.getSoId(), part.getPickupContact().getContactId());
							serviceOrderDAO.deletePartPickupLocation(so.getSoId(), part.getPickupLocation().getLocationId());						
							serviceOrderBO.processChangePartPickupLocation(so.getSoId(), securityContext);
						}
					}
				}
			} catch (DataServiceException e1) {
				logger.error(e1.getMessage(), e1);
			}
			if (parts != null && parts.size() > 0) {
				for (Part part : parts) {
					part.setSoId(so.getSoId());
					try {
						if (part.getPickupLocation() != null) {
							part.setProviderBringPartInd(new Boolean(true));
						}
						serviceOrderDAO.insertPart(part);
					} catch (DataServiceException e) {
						logger.error("Ignoring - no business rule. Error inserting new parts for so:" + so.getSoId(), e);
					}
				}
			}
		}
	}

	/**
	 * Different rules are applied if tasks are deleted or added
	 */
	public ProcessResponse updateTasks(List<ServiceOrderTask> tasksDeleted, List<ServiceOrderTask> tasksAdded, ServiceOrder so, Double spendLimitLabor, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse response = new ProcessResponse();
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
			throw new BusinessServiceException("Error updating service tasks, the service order is not in an open state.");
		}
		boolean updatePrice = false;
		//delete tasks
		if (tasksDeleted != null && tasksDeleted.size() > 0) {
			for (ServiceOrderTask task : tasksDeleted) {
				serviceOrderDAO.deleteTask(task);
				updatePrice = true;
			}
		}
		//add tasks
		if (tasksAdded != null && tasksAdded.size() > 0) {
			for (ServiceOrderTask task : tasksAdded) {
				MarketplaceSearchDAO skillDAO = (MarketplaceSearchDAO) MPSpringLoaderPlugIn.getCtx().getBean("marketPlaceDao");
				SkillNodeVO nodeVo = new SkillNodeVO();
				if (task.getParentId() == null) {
					nodeVo.setParentNodeId(task.getSkillNodeId());
				}
				else {
					nodeVo.setParentNodeId(task.getParentId());
				}
				nodeVo = skillDAO.getParentForNode(nodeVo);
				int primarySkillID = 0;
				if (nodeVo != null) {
					primarySkillID = (int) nodeVo.getParentNodeId();
				}
				
				if (primarySkillID == so.getPrimarySkillCatId().intValue()) {
					updatePrice = true;
				}
				task.setSoId(so.getSoId());
				try {
//					RandomGUID random = new RandomGUID();
					//task.setTaskId(random.generateGUID().intValue());
					serviceOrderDAO.insertTask(task);
				} catch (Exception e) {
					logger.error("Ignoring - no business rules for error " + e.getMessage(), e);
					response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
					response.setMessage(e.getMessage());
				}
			}
		}
		switch (so.getWfStateId().intValue()) {
		case OrderConstants.DRAFT_STATUS:
			if (updatePrice) {
				so.setSpendLimitLabor(spendLimitLabor);
				try {
					serviceOrderDAO.updateLimit(so);
				} catch (DataServiceException e) {
					logger.error("Ignoring - no business rule. Error updating price for so:" + so.getSoId(), e);
				}
			}
			else {
				response = serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.RESEARCH_SUBSTATUS, securityContext);
			}
			break;
		case OrderConstants.ROUTED_STATUS:
			try {
				if (updatePrice) {
					so.setSpendLimitLabor(spendLimitLabor);
					serviceOrderDAO.updateLimit(so);
				}
				response = serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.RESEARCH_SUBSTATUS, securityContext);
			} catch (DataServiceException e) {
				logger.error("Ignoring - no business rule. Error updating price for so:" + so.getSoId(), e);
				response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
				response.setMessage(e.getMessage());
			}
			break;
		case OrderConstants.ACCEPTED_STATUS:
			response = serviceOrderBO.processChangeOfScope(so, securityContext);
			break;
		case OrderConstants.ACTIVE_STATUS:
			response = serviceOrderBO.processChangeOfScope(so, securityContext);
			break;
		}
		if (response.getCode() == null || response.getCode().length() == 0) {
			response.setCode(ServiceConstants.VALID_RC);
		}
		return response;
	}
	
	/**
	 * Update service order schedule or send reschedule request depending on order status
	 */
	public ProcessResponse updateSchedule(ServiceOrder so, ServiceOrder updatedSO,	SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse response = null;
		Boolean updateSchedule = buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.WebServices.UPDATE_SCHEDULE);
		if (null != updateSchedule && updateSchedule.booleanValue()) {
			if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
					|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
					|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
				throw new BusinessServiceException("Error updating service schedule, the service order is not in an open state.");
			}
			
			
			// Allow updating of schedule for draft individual or child of Group Orders.  This will handle
			// the case where a child order is Expired.
			if (so.getWfStateId().intValue() == OrderConstants.DRAFT_STATUS || StringUtils.isNotBlank(so.getGroupId()))
			{
				try {
					response = new ProcessResponse();
					so.setServiceDate1(updatedSO.getServiceDate1());
					so.setServiceDate2(updatedSO.getServiceDate2());
					so.setServiceTimeStart(updatedSO.getServiceTimeStart());
					so.setServiceTimeEnd(updatedSO.getServiceTimeEnd());
					so.getServiceLocation().setZip(updatedSO.getServiceLocation().getZip());
					serviceOrderBO.GivenTimeZoneToGMT(so);
					serviceOrderDAO.updateSOReschedule(so);
					response.setCode(ServiceConstants.VALID_RC);
				} catch (DataServiceException e) {
					logger.error("Ignoring - no business rule. Error updating schedule for so:" + so.getSoId(), e);
					response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
				}
			}
			else if (so.getWfStateId().intValue() == OrderConstants.ROUTED_STATUS) {
				try {
					//business rule per Scott W. Do not perform reschedule if it was 
					//rescheduled on the frontend already. We'll check history for reschedule events.
					if (!wasRescheduledBefore(so)) {
						response = new ProcessResponse();
						so.setServiceDate1(updatedSO.getServiceDate1());
						so.setServiceDate2(updatedSO.getServiceDate2());
						so.setServiceTimeStart(updatedSO.getServiceTimeStart());
						so.setServiceTimeEnd(updatedSO.getServiceTimeEnd());
						serviceOrderDAO.updateSOReschedule(so);
						response.setCode(ServiceConstants.VALID_RC);
					}
				} catch (DataServiceException e) {
					logger.error("Ignoring - no business rule. Error updating schedule for so:" + so.getSoId(), e);
					response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
				}
			}
			else if (so.getWfStateId().intValue() == OrderConstants.ACCEPTED_STATUS || so.getWfStateId().intValue() == OrderConstants.ACTIVE_STATUS) {
				//check history for schedule changes
				try {
					if (!wasRescheduledBefore(so)) {
						//request expects service dates in local time and converts them to GMT
						//WS injects with GMT already so set the service order timezone to GMT
						response = serviceOrderBO.requestRescheduleSO(so.getSoId(), updatedSO.getServiceDate1(), 
								updatedSO.getServiceDate2(), updatedSO.getServiceTimeStart(), updatedSO.getServiceTimeEnd(),
								so.getWfSubStatusId(), securityContext.getRoleId(), so.getBuyer().getBuyerId(), null, false, securityContext);
						if (so.getWfStateId().intValue() == OrderConstants.ACTIVE_STATUS) {
							try {
								serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.TIME_WINDOW_SUBSTATUS, securityContext);
							} catch (BusinessServiceException e) {
								logger.error("Ignoring - no business rule. Error updating price for so:" + so.getSoId(), e);
								response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
								response.setMessage("Error updating substatus");
							}
						}
					}
				}
				catch (Exception e) {
					logger.error("Ignoring - no business rule. Error updating schedule for so:" + so.getSoId(), e);
					response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
				}
			}
			if (response == null) {
				response = new ProcessResponse();
			}
			if (response.getCode() == null || response.getCode().length() == 0) {
				response.setCode(ServiceConstants.VALID_RC);
			}
		}
		return response;
	}
	
	private boolean wasRescheduledBefore(ServiceOrder so) throws DataServiceException {
		boolean wasRescheduledBefore = false;
		//check history for schedule changes
		List<SoChangeDetailVo> history = loggingDAO.getSOChangeDetail(so.getSoId());
		for (SoChangeDetailVo change : history) {
			if (change.getChgComment().indexOf("reschedule") >= 0) {
				wasRescheduledBefore = true;
				break;
			}
		}
		return wasRescheduledBefore;
	}

	public ProcessResponse updateServiceContact(ServiceOrder updatedServiceOrder, ServiceOrder so, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse response = new ProcessResponse();
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
			throw new BusinessServiceException("Error updating service contact, the service order is not in an open state.");
		}
		updatedServiceOrder.getServiceContact().setSoId(so.getSoId());
		updatedServiceOrder.getServiceContact().setContactId(so.getServiceContact().getContactId());
		if(updatedServiceOrder.getServiceContact() != null && updatedServiceOrder.getServiceContact().getPhones() != null)
		{
			for (PhoneVO phone : updatedServiceOrder.getServiceContact().getPhones())
			{
				phone.setSoContactId(so.getServiceContact().getContactId());
				phone.setSoId(so.getSoId());
//				RandomGUID random = new RandomGUID();
//				try {
//					phone.setSoContactPhoneId(random.generateGUID().intValue());
//				} catch (Exception e) {
//					logger.error("Ignoring - no business rule. Error updating service contact for so:" + so.getSoId(), e);
//					throw new BusinessServiceException(e.getMessage());
//				}
			}
		}
		int daoResponse = serviceOrderDAO.updateServiceOrderContact(so.getSoId(), updatedServiceOrder.getServiceContact());
		if (daoResponse == 0) {
			response.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			response.setMessage("Unable to update service contact");
		}
		if (response.getCode() == null || response.getCode().length() == 0) {
			response.setCode(ServiceConstants.VALID_RC);
		}
		return response;
	}
	
	public void updateContact(String soId, Contact contact)
	{
		serviceOrderDAO.updateServiceOrderContact(soId, contact);
	}

	/**
	 * AOP Trigger for alert provider on this method
	 */
	public ProcessResponse updateServiceLocation(ServiceOrder updatedServiceOrder, ServiceOrder so, String templateName, Double spendLimitLabor, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse response = new ProcessResponse();
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS
				|| so.getWfStateId() == OrderConstants.COMPLETED_STATUS) {
			throw new BusinessServiceException("Error updating service location, the service order is not in an open state.");
		}
		else {
			updatedServiceOrder.getServiceLocation().setSoId(so.getSoId());
			updatedServiceOrder.getServiceLocation().setLocationId(so.getServiceLocation().getLocationId());
			serviceOrderDAO.updateServiceLocation(so.getSoId(), updatedServiceOrder.getServiceLocation());
		}
		if (!so.getServiceLocation().getZip().equals(updatedServiceOrder.getServiceLocation().getZip())) {
			so.setSpendLimitLabor(spendLimitLabor);
			try {
				serviceOrderDAO.updateLimit(so);
			} catch (DataServiceException e) {
				logger.error("Ignoring - no business rule. Error updating price for so:" + so.getSoId(), e);
			}
			boolean addNote = false;
			switch (so.getWfStateId().intValue()) {
			case OrderConstants.ROUTED_STATUS:
				if (buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.AUTO_ROUTE)) {
					serviceOrderDAO.deleteRoutedProviders(so.getSoId());
					so.getServiceLocation().setZip(updatedServiceOrder.getServiceLocation().getZip());
					/* for TR remove scheduled job, do tier routing this is fine in case of single order*/
					if(so.getGroupId()==null && trRouteController.isTierRoutingRequried(so.getSpnId(), so.getBuyer().getBuyerId())){
						System.out.println("STOP TIER ROUTE SCHEDULER FOR SOID after Update ServiceLocation: " + so.getSoId());
						trSchedulerMgr.stopAndRemoveJob(so.getSoId());
						trRouteController.route(so.getSoId(), false, SPNConstants.TR_REASON_ID_RESTART_TIER_ROUTING);
					} else {
						response = routeServiceOrder(so, templateName, securityContext);
					}	
				}
				else {
					response = serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.FOLLOWUP_SUBSTATUS, securityContext);
					addNote = true;
				}
				break;
			case OrderConstants.ACCEPTED_STATUS:
				response = serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.FOLLOWUP_SUBSTATUS, securityContext);
				addNote = true;
				break;
			case OrderConstants.ACTIVE_STATUS:
				response = serviceOrderBO.updateSOSubStatus(so.getSoId(), OrderConstants.FOLLOWUP_SUBSTATUS, securityContext);
				addNote = true;
				break;
			}
			if (addNote) {
				String noteMessage = "Contact the provider, the service zip code has changed please verify that provider can still complete service.";
				ServiceOrderNote note = new ServiceOrderNote();
				note.setCreatedByName(so.getBuyer().getUserName());
				note.setCreatedDate(new Date());
				note.setNote(noteMessage);
				note.setRoleId(new Integer(OrderConstants.BUYER_ROLEID));
				note.setEntityId(so.getBuyerResourceId());
				note.setSoId(so.getSoId());
				note.setNoteTypeId(new Integer(3));
				note.setPrivateId(1);
//				RandomGUID random = new RandomGUID();
				
				try {
					//note.setNoteId(random.generateGUID().longValue());
					serviceOrderDAO.insertNote(note);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		if (response.getCode() == null || response.getCode().length() == 0) {
			response.setCode(ServiceConstants.VALID_RC);
		}
		return response;
	}
	
	public ProcessResponse routeServiceOrder(ServiceOrder so, String templateName, SecurityContext securityContext) {
		BuyerSOTemplateDTO template = null;
		if (templateName != null) {
			template = soTemplateBO.loadBuyerSOTemplate(so.getBuyer().getBuyerId(), templateName);
		}
		
		List<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();

		//setup filters
		ZipParameterBean zipBean = new ZipParameterBean();
        zipBean.setRadius(40);
		zipBean.setZipcode(so.getServiceLocation().getZip());
		zipBean.setCredentialId(so.getPrimarySkillCatId());
		ratingParamBeans.add(zipBean);
		
		BackgroundCheckParameterBean backgroundBean = new BackgroundCheckParameterBean();
		backgroundBean.setBackgroundCheck(1);
		ratingParamBeans.add(backgroundBean);
		
		//setup filters
		ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
		provSearchVO.setBuyerID(so.getBuyer().getBuyerId());
		provSearchVO.setServiceLocation(so.getServiceLocation());
		provSearchVO.setServiceOrderID(so.getSoId());
		
		if (so.getPrimarySkillCatId() != null) {
			ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
			List<Integer> skillTypes = new ArrayList<Integer>();
			if(null != so.getTasks() && so.getTasks().size() > 0){
				// If we have tasks then assign the SubCategory/Category to the list
				// else select the main category
				for(ServiceOrderTask taskDto : so.getTasks()){
					if (taskDto.getSubCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} else if (taskDto.getCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} else {
						skillNodeIds.add(so.getPrimarySkillCatId());
					}
					if (taskDto.getServiceTypeId() != null) {
						skillTypes.add(taskDto.getServiceTypeId());
					}
				}
				provSearchVO.setSkillNodeIds(skillNodeIds);	
				provSearchVO.setSkillServiceTypeId(skillTypes);
			}
			else {
				skillNodeIds.add(so.getPrimarySkillCatId());
				provSearchVO.setSkillNodeIds(skillNodeIds);
			}
		}
		if (template != null && template.getSpnId() != null) {
			provSearchVO.setSpnID(template.getSpnId());
			provSearchVO.setIsNewSpn(template.getIsNewSpn());
		}
		ArrayList<ProviderResultVO> resultsAL = provSearchObj.getProviderList(provSearchVO); 	//do initial search
		ArrayList<ProviderResultVO> resultsALCopy = new ArrayList<ProviderResultVO>(resultsAL);
		
		//apply filters and get refined results
		List<ProviderResultVO> filteredResultsAL = masterCalcObj.getFilteredProviderList((ArrayList) ratingParamBeans, resultsAL);

		while(filteredResultsAL.size() == 0 && ratingParamBeans.size() > 0) {
			ratingParamBeans.remove(ratingParamBeans.size()-1);
			//the following can not be a List due to interface sig
			ArrayList<ProviderResultVO> resultsALCopy1 = new ArrayList<ProviderResultVO>(resultsALCopy);
			filteredResultsAL = masterCalcObj.getFilteredProviderList((ArrayList) ratingParamBeans, resultsALCopy1);
		}
		
		List<Integer> routedResourceIds = new ArrayList<Integer>();
		for (ProviderResultVO vo : filteredResultsAL) {
			routedResourceIds.add(vo.getResourceId());
		}
		if(so.getWfStateId()!=null && so.getWfStateId()== OrderConstants.ROUTED_STATUS)
			return serviceOrderBO.processReRouteSO(so.getBuyer().getBuyerId(), so.getSoId(), routedResourceIds, null , securityContext);
		else
			return serviceOrderBO.processRouteSO(so.getBuyer().getBuyerId(), so.getSoId(), routedResourceIds, OrderConstants.NON_TIER , securityContext);
	}
	
	public void updateProviderInstructions(ServiceOrder so, String instructions, SecurityContext securityContext) {
		serviceOrderDAO.updateProviderInstructions(so.getSoId(), so.getProviderInstructions() + "\n" + instructions);
	}
	
	public void updateDescription(ServiceOrder so, String description, SecurityContext securityContext) {
		try {
			serviceOrderDAO.updateSowDs(so.getSoId(), description);
		} catch (DataServiceException e1) {
			logger.error(e1.getMessage(), e1);
		}
		String noteMessage = "Service order description changed via webservice. Please validate and take appropriate action.";
		ServiceOrderNote note = new ServiceOrderNote();
		note.setSubject("WS Update");
		note.setCreatedByName(OrderConstants.NEWCO_DISPLAY_SYSTEM);
		note.setCreatedDate(new Date());
		note.setNote(noteMessage);
		note.setRoleId(new Integer(OrderConstants.BUYER_ROLEID));
		note.setSoId(so.getSoId());
		note.setEntityId(so.getBuyerResourceId());
		note.setNoteTypeId(new Integer(3));
		note.setPrivateId(1);
//		RandomGUID random = new RandomGUID();
		
		try {
			//note.setNoteId(random.generateGUID().longValue());
			serviceOrderDAO.insertNote(note);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ProcessResponse updateTaskNameComments(ServiceOrder matchingSO,List<ServiceOrderTask> modifiedList,
											SecurityContext securityContext) throws BusinessServiceException {
		// right now its implemented only for Assurant -- overwritten in AssurantUpdateBO
		 ProcessResponse processResp = new ProcessResponse();
		 return processResp;
	}
	
	
	public int updateSowTitle(ServiceOrder so, String newTitle, SecurityContext securityContext) {
		int count = 0;
		try {
			count = serviceOrderDAO.updateSowTitle(so.getSoId(), newTitle);
			logger.info("The title of Service Order#" + so.getSoId() + " is updated.");
		} catch (DataServiceException e1) {
			logger.error(e1.getMessage(), e1);
		}
		return count;
	}

	/**
	 * Updates the price
	 * @param updatedSO
	 * @param so
	 * @param changed
	 * @param securityContext
	 * @throws BusinessServiceException 
	 */
	public ProcessResponse updatePrice(ServiceOrder updatedSO,ServiceOrder so,Map<String, Object> changed, SecurityContext securityContext) throws BusinessServiceException {
		if (so.getWfStateId() == OrderConstants.CANCELLED_STATUS
				|| so.getWfStateId() == OrderConstants.CLOSED_STATUS) {
			throw new BusinessServiceException("Error updating service price, the service order is not in an open state.");
		}
		ProcessResponse processResp = new ProcessResponse();
		updatedSO.setSoId(so.getSoId());
		updatedSO.setGroupId(so.getGroupId());
		updatedSO.setWfStateId(so.getWfStateId());
		updatedSO.setFundingTypeId(so.getFundingTypeId());
		
		String increaseOrDecreased=StringUtils.EMPTY;
		Double changedPrice = 0.0;
		if(changed.containsKey(OrderConstants.PRICE_DECREASE)){
			increaseOrDecreased = OrderConstants.DECREASED;
			changedPrice = (Double) changed.get(OrderConstants.PRICE_DECREASE);
			//Need to update the new spendLimitLabor as (oldSpendLimitLabor-decreaseInPrice), since so_hdr update is done using these.
			updatedSO.setSpendLimitLabor(MoneyUtil.subtract(so.getSpendLimitLabor(),changedPrice));
		}else if(changed.containsKey(OrderConstants.PRICE_INCREASE)){
			increaseOrDecreased = OrderConstants.INCREASED;
			changedPrice = (Double) changed.get(OrderConstants.PRICE_INCREASE);
			//Need to update the new spendLimitLabor as (oldSpendLimitLabor+increaseInPrice), since so_hdr update is done using these.
			updatedSO.setSpendLimitLabor(MoneyUtil.add(so.getSpendLimitLabor(),changedPrice));
		}
		processResp=serviceOrderBO.processUpdateSpendLimitforWS(updatedSO, changed,securityContext);
		if (ServiceConstants.VALID_RC.equals(processResp.getCode())) {
			double newTotalSpendLimit =MoneyUtil.add(updatedSO.getSpendLimitLabor(),updatedSO.getSpendLimitParts());
			double oldTotalSpendLimit =MoneyUtil.add(so.getSpendLimitLabor(),so.getSpendLimitParts() );				

			SecurityContext systemSecurityContext = SecurityUtil.getSystemSecurityContext();
			//For use in AOP mapper
			systemSecurityContext.getRoles().setRoleId(systemSecurityContext.getRoleId());
			StringBuilder message = new StringBuilder();
			message.append("Maximum Spend Limit - $").append(oldTotalSpendLimit)
				   .append(".\nNew Maximum Spend Limit - $").append(newTotalSpendLimit)
				   .append(".\nThe labor spend limit of $").append(changedPrice)
				   .append(" has been ").append(increaseOrDecreased)
				   .append(" towards the scope change update.");
			serviceOrderBO.processAddNote(so.getBuyerResourceId(),OrderConstants.BUYER_ROLEID,
					so.getSoId(), OrderConstants.SPEND_LIMIT_CHANGE_SUBJECT,message.toString(),OrderConstants.SO_NOTE_GENERAL_TYPE,OrderConstants.NEWCO_DISPLAY_SYSTEM,
					OrderConstants.NEWCO_DISPLAY_SYSTEM,so.getBuyerResourceId(),OrderConstants.SO_NOTE_PUBLIC_ACCESS,false,false,systemSecurityContext);
		}else{
			logger.info("The SO Price was not updated."+processResp.getMessages().get(0));
		}
		return processResp;
	} 
	
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public ServiceOrderDao getServiceOrderDAO() {
		return serviceOrderDAO;
	}

	public void setServiceOrderDAO(ServiceOrderDao serviceOrderDAO) {
		this.serviceOrderDAO = serviceOrderDAO;
	}

	public ILoggingDao getLoggingDAO() {
		return loggingDAO;
	}

	public void setLoggingDAO(ILoggingDao loggingDAO) {
		this.loggingDAO = loggingDAO;
	}

	public IBuyerSOTemplateBO getSoTemplateBO() {
		return soTemplateBO;
	}

	public void setSoTemplateBO(IBuyerSOTemplateBO soTemplateBO) {
		this.soTemplateBO = soTemplateBO;
	}

	public IProviderSearchBO getProvSearchObj() {
		return provSearchObj;
	}

	public void setProvSearchObj(IProviderSearchBO provSearchObj) {
		this.provSearchObj = provSearchObj;
	}

	public IMasterCalculatorBO getMasterCalcObj() {
		return masterCalcObj;
	}

	public void setMasterCalcObj(IMasterCalculatorBO masterCalcObj) {
		this.masterCalcObj = masterCalcObj;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public IClientBO getClientBO() {
		return clientBO;
	}

	public void setClientBO(IClientBO clientBO) {
		this.clientBO = clientBO;
	}

	public TierRouteController getTrRouteController() {
		return trRouteController;
	}

	public void setTrRouteController(TierRouteController trRouteController) {
		this.trRouteController = trRouteController;
	}

	public TierRoutingSchedulerManager getTrSchedulerMgr() {
		return trSchedulerMgr;
	}

	public void setTrSchedulerMgr(TierRoutingSchedulerManager trSchedulerMgr) {
		this.trSchedulerMgr = trSchedulerMgr;
	}
//This method sets the sku  indicator column into  the so_workflow_controls table for service order created using sku
	
	public void  updateSkuIndicator(String soId) 
	{
		
			 serviceOrderDAO.updateSkuIndicator(soId);
			logger.info("The sku indicator is updated in the table for so id#" + soId);
		
	}
	
	//This  method to fetch the sku indicator column of  so_workflow_controls table to identify the type of  serive order
	public Boolean fetchSkuIndicatorFromSoWorkFlowControl(String soID)
	{		Boolean fetchSkuIndicatorFromWfmc=false;
		try {
			fetchSkuIndicatorFromWfmc=serviceOrderDAO.fetchSkuIndicatorFromSoWorkFlowControl(soID);
		} catch (Exception e) {
			logger.error("Exception :" + e);
			
		}
		return fetchSkuIndicatorFromWfmc;
	}
}
