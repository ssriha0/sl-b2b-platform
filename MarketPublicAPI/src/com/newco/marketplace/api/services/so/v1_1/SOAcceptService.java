/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOAcceptMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.acceptSO.SOAcceptFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.acceptSO.SOAcceptResponse;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.provider.IResourceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.ordermanagement.services.OrderManagementService;

/**
 * This class is a service class for Accept Service Order
 * Accepts grouped SO also.   
 * @author Infosys
 * @version 1.0
 */
public class SOAcceptService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(SOCancelService.class);
	private IServiceOrderBO serviceOrderBO;
	private IOrderGroupBO orderGroupBO;
	private SOAcceptMapper acceptMapper;
	private OFHelper ofHelper;
	private IResourceBO resourceBO;
	private OrderManagementService managementService;

	/**
	 * Constructor
	 */

	public SOAcceptService() {
		super(PublicAPIConstant.acceptSO.ACCEPTREQUEST_XSD, PublicAPIConstant.acceptSO.ACCEPTRESPONSE_XSD,
				PublicAPIConstant.SOACCEPTRESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SOACCEPTRESPONSE_SCHEMALOCATION, SOAcceptFirmRequest.class,
				SOAcceptResponse.class);
	}

	/**
	 * This method dispatches the cancel Service order request.
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		LOGGER.info("Entering SOAcceptService.execute()");
		Results results = null;
		Integer vendorId;
		SOAcceptFirmRequest request  = (SOAcceptFirmRequest)apiVO.getRequestFromPostPut();
		//String firmId = (String) apiVO.getProperty(APIRequestVO.PROVIDERID);
		String soId = (String)apiVO.getProperty(APIRequestVO.SOID);
		SOAcceptResponse soAcceptResponse = new SOAcceptResponse();
		SecurityContext securityContext = null;
		Integer intResourceId;
		//Resource id of Vendor Admin
		Integer vendorResourceId = request.getIdentification().getId();
		String warningMsg = "";
		//Denotes SO to be accepted for a particular resource or Firm.
		boolean acceptByFirmInd = request.getAcceptByFirmInd();
		//If SO to be accepted at firm level set resource Id = vendor admin's resource id 
		Integer resourseId = request.getResourceId();
		Integer preferenceInd = request.getPreferenceInd();
		if(!acceptByFirmInd && null == resourseId){
			return createErrorResponse("Resource ID required.");
		}
		intResourceId = acceptByFirmInd?vendorResourceId:resourseId;
		try {
			//Security context of Vendor
			securityContext = getSecurityContextForVendor(intResourceId);
			if(null==securityContext){ 
				LOGGER.error("SecurityContext is null");
				results = Results.getError(
						ResultsCode.INVALID_OR_MISSING_PARAM.getMessage(),
						ResultsCode.INVALID_OR_MISSING_PARAM.getCode());
				soAcceptResponse.setResults(results);
				return soAcceptResponse;
			}else{
				vendorId = securityContext.getCompanyId();
				Map<String, String> requestMap = (Map<String, String>) apiVO.getProperty(PublicAPIConstant.REQUEST_MAP);
				String groupIndParam ="";
				if(null != requestMap && requestMap.containsKey(PublicAPIConstant.GROUP_IND_PARAM)){
					groupIndParam = requestMap.get(PublicAPIConstant.GROUP_IND_PARAM);
				}
				String groupId;
				//ServiceOrder so;
				if (StringUtils.isNotBlank(groupIndParam) && PublicAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupIndParam)) {
					groupId = soId;
				}else{
					groupId= "";
					//so = serviceOrderBO.getServiceOrder(soId);
				}
				//Validate resource Id
				warningMsg = validateRequest(soId,groupId,vendorId,intResourceId,acceptByFirmInd);
				if(StringUtils.isBlank(warningMsg)){
					//Fetch service order when request parameters are valid
					//serviceOrder = serviceOrderBO.getServiceOrderForAPI(soId);
					//if(null != serviceOrder){
						//Check whether SO is grouped or Not and process it separately
						//String groupId = serviceOrder.getGroupId();
						if (StringUtils.isNotBlank(groupId)) {
							//Accept Grouped SO
							return acceptGroupedOrder(groupId, intResourceId, acceptByFirmInd, securityContext);
						}else{
							//Accept Individual SO
							return acceptServiceOrder(soId, intResourceId, acceptByFirmInd, preferenceInd,securityContext);
						}
//					}else{
//						//When SO fetching failed (can be invalid SO also?)
//						results = Results.getError("Failed to fetch SO details",
//								ResultsCode.INTERNAL_SERVER_ERROR.getCode());
//						soAcceptResponse.setResults(results);
//					}
				}else{
					//If request parameters are invalid, return error response
					return createErrorResponse(warningMsg);
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Exception Occurred inside SOAcceptService.execute"
					+ ex);
			results = Results.getError(ex.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soAcceptResponse.setResults(results);
		}
		//Return SO Accept Service Response
		return soAcceptResponse;
	}

	/**
	 * Method to accept individual Service Order.
	 * @param soId : Service Order to be accepted.
	 * @param resourceId : Resource Id for whom SO to be accepted.
	 * @param securityContext of Vendor
	 * @param serviceOrder {@link ServiceOrder}
	 * @return {@link IAPIResponse} {@link SOAcceptResponse} 
	 * */
	private IAPIResponse acceptServiceOrder(String soId, Integer resourceId, boolean acceptByFirmInd, Integer preferenceInd, SecurityContext securityContext) {
		ProcessResponse processResponse = null;
		//Set terms and condition to 10 for provider SO accept
		Integer termAndCond = PublicAPIConstant.acceptSO.TERMS_AND_COND_IND_PROVIDER_ACCEPT;
		Integer vendorId = securityContext.getCompanyId();
		//Integer resourceId = securityContext.getVendBuyerResId();
		if(ofHelper.isNewSo(soId)){
			//For "NEW" SOs.
			// check the validity for acceptance
			processResponse = new ProcessResponse();
			boolean acceptable = Boolean.FALSE;
			try{
				//Check whether SO in Edit Mode
				boolean isSOInEditMode = serviceOrderBO.isSOInEditMode(soId);
				if (isSOInEditMode) {
					//Set warning message when SO in Edit Mode.
					processResponse.setCode(ServiceConstants.USER_ERROR_RC);
					processResponse.setMessage(OrderConstants.ORDER_BEING_EDITED);
				} else {	
					ServiceOrder soObj = serviceOrderBO.getServiceOrder(soId);
					//ServiceOrder soObj = serviceOrder;
					//Check whether SO in acceptable state
					//TODO rewrite this method which uses only wfstateId and grroupId.  
					acceptable = serviceOrderBO.determineAcceptability(true, processResponse, soObj);
					SignalType signalType = null;
					int providerId = 0;
					//Set Signals according to whether SO to be accepted for Firm or Resource
					signalType = SignalType.ACCEPT_FOR_FIRM;
					if(!acceptByFirmInd){
						providerId =resourceId;
						signalType = SignalType.ACCEPT_ORDER;
					}
					if(acceptable){
						//Create OF request for SO Accept
						OrderFulfillmentRequest ofRequest = createOFRequestForAcceptance(vendorId, providerId, termAndCond, securityContext,acceptByFirmInd);
						OrderFulfillmentResponse responseOF = ofHelper.runOrderFulfillmentProcess(soId, signalType, ofRequest);
						//Create Process response from Of response 
						processResponse = acceptMapper.mapProcessResponse(responseOF);
					}else{
						return createErrorResponse("Service order "+soId+" is not in acceptable state");
					}
				}
			} catch (BusinessServiceException bse) {
				LOGGER.debug("Exception thrown accepting SO", bse);
				return createErrorResponse(bse.getMessage());
			}
		}else{
			//For "OLD" individual Service Order
			processResponse = serviceOrderBO.processAcceptServiceOrder(
					soId, resourceId, vendorId, termAndCond, true, false, true,
					securityContext);
			if (processResponse.isError()) {
				return createAcceptResponse(processResponse);
			} else {
				serviceOrderBO.sendallProviderResponseExceptAccepted(soId, securityContext);
			}
		}
		//Create and return AcceptResponse
		if (preferenceInd!=null && processResponse.getCode() == ServiceConstants.VALID_RC){
			 ServiceDatetimeSlot serviceDatetimeSlot=null;
			try {
				serviceDatetimeSlot = serviceOrderBO.getSODateTimeSlot(soId,  preferenceInd);
			} catch (DataServiceException e) {
				LOGGER.debug("Exception thrown accepting SO Date Time Slot", e);
				return createErrorResponse(e.getMessage());
			}
			 if (serviceDatetimeSlot!=null)
				try {
					serviceOrderBO.updateAcceptedServiceDatetimeSlot(serviceDatetimeSlot);
				} catch (BusinessServiceException e) {
					LOGGER.debug("Exception thrown accepting SO Date Time Slot", e);
					return createErrorResponse(e.getMessage());
				}
		}
		return createAcceptResponse(processResponse);
	}

	/**
	 * Method which accepts a Groped SO for both firm and Resource
	 * @param groupId : Group Id of SO
	 * @param resourceId : Resource id for whom the SO is to be accepted
	 * @param acceptByFirmInd : True when accept for firm and False when accept for resource
	 * @param securityContext {@link SecurityContext} of the vendor
	 * @return {@link IAPIResponse} {@link SOAcceptResponse} with Success/ error code and messages.
	 */
	private IAPIResponse acceptGroupedOrder(String groupId, Integer resourceId, boolean  acceptByFirmInd,SecurityContext securityContext) {
		Integer vendorId = securityContext.getCompanyId();
		//Integer resourceId = securityContext.getVendBuyerResId();
		SignalType signalType;
		OrderFulfillmentResponse ofResponse;
		ProcessResponse processResponse = null;
		//Set terms and condition ind to 10 for provider servicer order accept
		Integer termAndCond = PublicAPIConstant.acceptSO.TERMS_AND_COND_IND_PROVIDER_ACCEPT;
		try{
			if(ofHelper.isNewGroup(groupId)){
				//If SO is "new" use OF code 
				//Create OF Request
				OrderFulfillmentRequest ofRequest = createOFRequestForAcceptance(vendorId, resourceId, termAndCond, securityContext,acceptByFirmInd);
				//Use proper Signals based on whether SO to be accepted for resource / Firm.
				signalType = acceptByFirmInd?SignalType.ACCEPT_GROUP_FOR_FIRM : SignalType.ACCEPT_GROUP;
				ofResponse = ofHelper.runOrderFulfillmentGroupProcess(groupId,signalType, ofRequest);
				//Create Process response from OFResponse
				processResponse = acceptMapper.mapProcessResponse(ofResponse);
			}else{
				//If SO is "OLD" use legacy code (deprecated)
				return acceptGroupedOrderLegacy(groupId,securityContext, termAndCond);
			}
		}catch (BusinessServiceException e) {
			//Create Error Response when Order Processing fails.
			LOGGER.info("error occured while accepting grouped Order grouped Order-->" + groupId);
			createErrorResponse(e.getMessage());
		}
		return createAcceptResponse(processResponse);
	}

	/**
	 * Legacy code for accepting "OLD" grouped Service order.
	 * <br>
	 * @param groupId : Group Id of SO
	 * @param acceptByFirmInd : True when accept for firm and False when accept for resource
	 * @param securityContext {@link SecurityContext} of the vendor
	 * @return {@link SOAcceptResponse}
	 * **/
	private SOAcceptResponse acceptGroupedOrderLegacy(String groupId, SecurityContext securityContext, Integer termAndCond) throws BusinessServiceException {
		List<ServiceOrderSearchResultsVO> groupedOrdsList = null;
		ProcessResponse processResponse;
		try {
			groupedOrdsList = orderGroupBO.getServiceOrdersForGroup(groupId);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new BusinessServiceException(e);
		}
		// iterate through to check if any of the child SO's are in edit mode.
		for (ServiceOrderSearchResultsVO soResult : groupedOrdsList) {
			if (soResult.getLockEditInd() != null && soResult.getLockEditInd().equals(Integer.valueOf(OrderConstants.SO_EDIT_MODE_FLAG))){
				return createErrorResponse(OrderConstants.ORDER_BEING_EDITED);
			}
		}
		Integer resourceId = securityContext.getVendBuyerResId();
		Integer vendorId = securityContext.getCompanyId();
		// iterate through all orders in group, call accept Service Order for each SO
		for (ServiceOrderSearchResultsVO soResult : groupedOrdsList) {
			//String childSOId = soResult.getSoId();
			processResponse = serviceOrderBO.processAcceptServiceOrder(OrderConstants.ORDER_BEING_EDITED,resourceId, vendorId, termAndCond, true, false, false,
					securityContext);
			if(processResponse.isError()){
				return createAcceptResponse(processResponse);
			}
		}
		processResponse = orderGroupBO.processAcceptGroupOrder(groupId, resourceId,
				vendorId, termAndCond, true, securityContext);
		orderGroupBO.sendallProviderResponseExceptAcceptedForGroup(groupId,
				securityContext);
		return createAcceptResponse(processResponse);
	}


	/**
	 * Creates SOAcceptService Response from Process response
	 * @param processResponse {@link ProcessResponse}: Will have Error/Success code set
	 * @return {@link SOAcceptResponse}
	 * */
	private SOAcceptResponse createAcceptResponse(ProcessResponse processResponse) {
		SOAcceptResponse response = new SOAcceptResponse();
		if(processResponse == null) {
			//Create error response when ProcessResponse is null.
			response.setResults(Results.getError(
					ResultsCode.SO_ACCEPT_FAILURE.getMessage(), 
					ResultsCode.SO_ACCEPT_FAILURE.getCode()));
		} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
			//Create Success response when Valid code is set in process response 
			response.setResults(Results.getSuccess(
					ResultsCode.ACCEPT_RESULT_CODE.getMessage()));					
		} else {
			List<String> eMsgs = processResponse.getMessages();
			StringBuilder eMsg = new StringBuilder();
			for(String msg: eMsgs) {
				eMsg = eMsg.append("; " + msg);
			}
			LOGGER.error("Error occured in Accepting SO, error code: " 
					+ processResponse.getCode() 
					+ ", messages: " + eMsg);
			response.setResults(Results.getError(eMsgs.get(0), 
					ResultsCode.FAILURE.getCode()));			
		}
		return response;
	}

	
	/**
	 * Validates accept SO request values. <br>
	 * Validates whether resource id is a valid Routed resource
	 * @param soId : Service order to be accepted.
	 * @param groupId 
	 * @param vendorId : Firm Id
	 * @param acceptByFirmInd : True: When SO to be accepted for firm and False when for resource
	 * @param intResourceId : Resource Id for whom the SO to be accepted. (It will be vendor admin resource 
	 * id <br> when acceptByFirmInd is true)
	 * @return Error/ Warning messages after validation. Empty string is returned on successful validation 
	 * **/
	private String validateRequest(String soId, String groupId,
			Integer vendorId, Integer intResourceId, boolean acceptByFirmInd) {
		if (!acceptByFirmInd) {
			// Check whether given resource id is one of routed resource when
			// Request is for accept SO for resource
			try {
				List<ProviderResultVO> routedProviders;
				// Fetch all routed resource id for the SO.
				if (StringUtils.isNotBlank(groupId)) {
					routedProviders = managementService.getEligibleProvidersForGroup(vendorId.toString(),soId);
				} else {
					routedProviders = managementService.getEligibleProviders(vendorId.toString(), soId);
				}
				int resourseId = intResourceId.intValue();
				boolean validId = Boolean.FALSE;
				if (null != routedProviders && 0 < routedProviders.size()) {
					for (ProviderResultVO providerResultVO : routedProviders) {
						if (providerResultVO.getResourceId() == resourseId) {
							validId = Boolean.TRUE;
							break;
						}
					}
					if (!validId) {
						return CommonUtility.getMessage(PublicAPIConstant.ACCEPT_INVALID_RESOURCE);
					}
				} else {
					return CommonUtility.getMessage(PublicAPIConstant.ACCEPT_INVALID_RESOURCE);
				}
			} catch (Exception e) {
				LOGGER.error(e);
				return CommonUtility.getMessage(PublicAPIConstant.ACCEPT_INVALID_RESOURCE);
			}
		} 
		return "";
	}

	/**
	 * Creates error response when request parameter validation failed.
	 * @param rejectResponse {@link ProcessResponse}
	 * @return {@link RejectSOResponse}
	 * */
	private SOAcceptResponse createErrorResponse(String msg) {
		SOAcceptResponse soAcceptResponse = new SOAcceptResponse();
		Results results = Results.getError(msg,
				ServiceConstants.USER_ERROR_RC);
		if (results == null) {
			results = Results.getError(msg,
					ServiceConstants.SYSTEM_ERROR_RC);
		}
		soAcceptResponse.setResults(results);
		return soAcceptResponse;
	}

	/**
	 * Creates OF Request for Service Order Accept.
	 * @param vendorId : Firm Id
	 * @param resourceId ; Resource Id for whom SO to be accepted. (It will be Vendor admin's resource id when acceptByFirmInd is true )
	 * @param termAndCond - Will be 10 
	 * @param securityContext {@link SecurityContext} of Firm
	 * @param acceptByFirmInd - True: When SO to be accepted for firm and False when for resource
	 * @return {@link OrderFulfillmentRequest}
	 * */
	private OrderFulfillmentRequest createOFRequestForAcceptance(Integer vendorId, Integer resourceId, Integer termAndCond, SecurityContext securityContext,boolean acceptByFirmInd) throws BusinessServiceException {
		OrderFulfillmentRequest acceptOfrequest = new OrderFulfillmentRequest();
		com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = new com.servicelive.orderfulfillment.domain.ServiceOrder();
		Contact vendorContact = null;
		serviceOrder.setAcceptedProviderId(vendorId.longValue());
		serviceOrder.setSoTermsCondId(termAndCond);	
		serviceOrder.setProviderSOTermsCondInd(1);
		serviceOrder.setProviderTermsCondDate(Calendar.getInstance().getTime());
		if(acceptByFirmInd){
			//When assignment type is Firm, set Assignment type misc params
			serviceOrder.setAssignmentType(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM);
			String name = null;
    		try{
        		name = serviceOrderBO.getVendorBusinessName(securityContext.getCompanyId());
        	}catch (BusinessServiceException bse) {
        		LOGGER.error("error fetching business name of firm", bse);
			}            		
        	acceptOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_NAME,name);
			acceptOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_SEND_PROVIDER_EMAIL, "false");
		}else{
			//When Assignment type is Resource
			serviceOrder.setAssignmentType(OrderConstants.SO_ASSIGNMENT_TYPE_PROVIDER);
			serviceOrder.setAcceptedProviderResourceId(resourceId.longValue());
			/*Get the Contact info for Vendor Resource!!!*/
			// get the state (location e.g. MI,IL,etc.) for the provider that accepted
			try {
				vendorContact = resourceBO.getVendorResourceContact(resourceId);
			} catch (BusinessServiceException bse) {
				LOGGER.error(bse);
				throw bse;
			}
			//Set contact and address of Vendor Resource
			SOContact contact = acceptMapper.mapContact(vendorContact);
			serviceOrder.addContact(contact);
			SOLocation soLocation = acceptMapper.mapLocation(vendorContact);
			serviceOrder.addLocation(soLocation);
			acceptOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE, vendorContact.getStateCd());
		}
		acceptOfrequest.setElement(serviceOrder);
		acceptOfrequest.setIdentification(OFMapper.createProviderIdentFromSecCtx(securityContext));
		return acceptOfrequest;
	}

//	/**
//	 * Checks whether SO is in Edit Mode.
//	 * @param soId.
//	 * @return true: When SO is in edit mode. else false
//	 * */
//	private boolean isSOInEditMode(String soId){
//		boolean flag = Boolean.FALSE;
//		try {
//			flag = getServiceOrderBO().isSOInEditMode(soId);
//		} catch (BusinessServiceException e) {
//			//this should not happen just logging it
//			LOGGER.error("At the time of acceptance getting service order ", e);
//		}
//		return flag;
//	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public SOAcceptMapper getAcceptMapper() {
		return acceptMapper;
	}

	public void setAcceptMapper(SOAcceptMapper acceptMapper) {
		this.acceptMapper = acceptMapper;
	}

	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public IResourceBO getResourceBO() {
		return resourceBO;
	}

	public void setResourceBO(IResourceBO resourceBO) {
		this.resourceBO = resourceBO;
	}

	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}
}
