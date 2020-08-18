/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider.SOAssignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider.SOAssignProviderResponse;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.AssignOrderException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.ordermanagement.services.OrderManagementService;

/**
 * This class would act as a Servicer class for SO Assign Provider
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAssignProviderService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOAssignProviderService.class);
	private static final Integer CONTACT_TYPE_ID_PRIMARY = 10;
	private static final Integer ENTITY_TYPE_ID_PROVIDER = 20;
	private OrderManagementService managementService;
	private IServiceOrderBO serviceOrderBo;
	private OrderManagementMapper omMapper ;

	public SOAssignProviderService() {
		super(PublicAPIConstant.SO_ASSIGN_PROVIDER_REQUEST_XSD,
				PublicAPIConstant.SO_ASSIGN_PROVIDER_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOAssignProviderRequest.class, SOAssignProviderResponse.class);
		super.addMoreClass(Contacts.class);
		super.addMoreClass(Contact.class);
	}

	/**
	 * Method which implements the main logic for Assigning a provider.
	 * @param apiVO {@link APIRequestVO} 
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside SOAssignProviderService.execute()");
		SOAssignProviderResponse soAssignProviderResponse = new SOAssignProviderResponse();
		Results results = null;
		boolean isInside = Boolean.FALSE;
		Contact soContact = null;
		
		SOAssignProviderRequest assignProviderRequest = (SOAssignProviderRequest) apiVO.getRequestFromPostPut();
		String providerId = apiVO.getProviderId();
		String soId = apiVO.getSOId();
		Integer resourceId = assignProviderRequest.getResourceId();	
		Integer loggedInResource = 0;
		if(null != assignProviderRequest.getIdentification()){
			loggedInResource = assignProviderRequest.getIdentification().getId();
		}
		if (null != providerId && null != soId) {
			try {
				SecurityContext securityContext = getSecurityContextForVendor(loggedInResource);
				Integer firmId = apiVO.getProviderIdInteger();
				
				Results validateResourceResults = validateResource(securityContext, firmId, loggedInResource);
				if(validateResourceResults != null){
					soAssignProviderResponse.setResults(validateResourceResults);
					return soAssignProviderResponse;
				}
				
				ServiceOrder so = serviceOrderBo.getServiceOrder(soId);
				//validations
				//whether so is in accepted, active or problem
				if(OrderConstants.ACCEPTED_STATUS == so.getWfStateId() || 
						OrderConstants.ACTIVE_STATUS == so.getWfStateId() || OrderConstants.PROBLEM_STATUS == so.getWfStateId()){
					
					List<RoutedProvider> routedProviderList = so.getRoutedResources();
					//whether so is routed to this provider
					for(RoutedProvider provider : routedProviderList){
						if (resourceId.intValue() == provider.getResourceId().intValue()) {	
							//whether this provider	is already assigned
							if(null != provider.getProviderRespId() && 1 == provider.getProviderRespId()){
								results = Results.getError(ResultsCode.ASSIGN_PROVIDER_ERROR.getMessage(), ResultsCode.ASSIGN_PROVIDER_ERROR.getCode());
								soAssignProviderResponse.setResults(results);
								return soAssignProviderResponse;
							}
							
							Integer role = securityContext.getRoleId();
							Integer entityId = securityContext.getVendBuyerResId();
							String userFirstName=  securityContext.getRoles().getFirstName();
							String userLastName =  securityContext.getRoles().getLastName();
							
							soContact = getsoContact(resourceId, soId);
							//setting so logging object
							SoLoggingVo soLoggingVO = new SoLoggingVo();
							soLoggingVO.setServiceOrderNo(soId);
							soLoggingVO.setOldValue(null);
							soLoggingVO.setNewValue(resourceId.toString());
							if(null != soContact){
								soLoggingVO.setFirstName(soContact.getFirstName().trim());
								soLoggingVO.setLastName(soContact.getLastName().trim());
								soLoggingVO.setEmail(soContact.getEmail().trim());
								soLoggingVO.setContactId(soContact.getContactId());
								soLoggingVO.setComment("Has been assigned to " + soContact.getFirstName() +" " + soContact.getLastName() + 
									"(" +  soLoggingVO.getNewValue() + ")");
							}
							else{
								soLoggingVO.setComment("Has been assigned to " +  soLoggingVO.getNewValue());
							}
							soLoggingVO.setCreatedDate(Calendar.getInstance().getTime());
							soLoggingVO.setModifiedDate(Calendar.getInstance().getTime());
							soLoggingVO.setCreatedByName(userFirstName+" "+userLastName);
							soLoggingVO.setModifiedBy(securityContext.getUsername());
							soLoggingVO.setRoleId(role);
							soLoggingVO.setEntityId(entityId);
							soLoggingVO.setValueName(Constants.ASSIGNMENT_NOTE_SUBJECT);
							soLoggingVO.setActionId(Integer.parseInt(Constants.SO_ACTION.SERVICE_ORDER_ASSIGN));
								
							//setting notes object
							ServiceOrderNote soNote = new ServiceOrderNote();
							soNote.setSoId(soLoggingVO.getServiceOrderNo());
							soNote.setCreatedDate(soLoggingVO.getCreatedDate());
							soNote.setSubject(Constants.ASSIGNMENT_NOTE_SUBJECT);
							soNote.setRoleId(soLoggingVO.getRoleId());
							soNote.setNote(soLoggingVO.getComment());
							soNote.setCreatedByName(userLastName+", "+userFirstName);
							soNote.setModifiedBy(soLoggingVO.getModifiedBy());
							soNote.setModifiedDate(soLoggingVO.getModifiedDate());
							soNote.setNoteTypeId(Integer.valueOf(2));
							soNote.setEntityId(soLoggingVO.getEntityId());
							soNote.setPrivateId(Integer.valueOf(0));
								
							//assign provider
							managementService.assignProvider(resourceId, soId, soLoggingVO, soNote, soContact);
							
							soAssignProviderResponse = omMapper.mapAssignProviderResponse();
							isInside = true;
							break;
						}
					}
					if(!isInside){
						results = Results.getError(ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH.getMessage(), 
													ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH.getCode());
						soAssignProviderResponse.setResults(results);
					}
				} else {
					results = Results.getError(ResultsCode.ASSIGN_STATE_ERROR.getMessage(), ResultsCode.ASSIGN_STATE_ERROR.getCode());
					soAssignProviderResponse.setResults(results);
				}
			} catch (AssignOrderException exc) {
				results = Results.getError(ResultsCode.ASSIGN_ERROR_ALREADY_ASSIGNED.getMessage(),ResultsCode.ASSIGN_ERROR_ALREADY_ASSIGNED.getCode());
				soAssignProviderResponse.setResults(results);
				return soAssignProviderResponse;
			} catch (DataServiceException ex) {
				logger.error("Exceeption is SO Assign Provider",ex);	
				results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				soAssignProviderResponse.setResults(results);
				return soAssignProviderResponse;
			} catch (Exception ex) {
				results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				soAssignProviderResponse.setResults(results);
				return soAssignProviderResponse;
			}
		}
		logger.info("Leaving SOAssignProviderService.execute()");
		return soAssignProviderResponse;
	}
	
	/**
	 * Fetches {@link Contact} details from DB.<br>
	 * EntityTypeId = 20, ContactTypeId = 10.
	 * @param resourceId : Resource Id of the resource to whom SO to be assigned.
	 * @param soId : Service order Id
	 * @return {@link Contact}
	 * **/
	private Contact getsoContact(Integer resourceId, String soId) {
		Contact soContact = managementService.getRoutedResources(soId,resourceId);
		soContact.setSoId(soId);
		soContact.setEntityTypeId(ENTITY_TYPE_ID_PROVIDER);
		soContact.setContactTypeId(CONTACT_TYPE_ID_PRIMARY);
		soContact.setEntityId(resourceId);
		soContact.setCreatedDate(new Timestamp(new Date().getTime()));
		soContact.setModifiedDate(new Timestamp(new Date().getTime()));
		return soContact;
	}
	
	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}


	public OrderManagementMapper getOmMapper() {
		return omMapper;
	}

	public void setOmMapper(OrderManagementMapper omMapper) {
		this.omMapper = omMapper;
	}

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}

	//SLT-4572
	Results validateResource(SecurityContext securityContext, Integer firmId, Integer resourceId){
		
		Results results = null;
		
		
			if(null == securityContext){
				
				results = Results.getError(ResultsCode.IDENTIFICATION_INVALID_RESOURCE_ERROR.getMessage(), 
						ResultsCode.IDENTIFICATION_INVALID_RESOURCE_ERROR.getCode());
				
			} else if(null != securityContext && null != securityContext.getCompanyId() 
					&& !firmId.equals(securityContext.getCompanyId() )){
				
					String errorMessage = ResultsCode.IDENTIFICATION_INVALID_PROVIDER_RESOURCE_ERROR.getMessage();
					errorMessage = errorMessage.replaceAll("resourceId", resourceId.toString())
												.replaceAll("providerId", firmId.toString());
					results = Results.getError(errorMessage, 
							ResultsCode.IDENTIFICATION_INVALID_PROVIDER_RESOURCE_ERROR.getCode());
			}
		
		return results;
	}


}