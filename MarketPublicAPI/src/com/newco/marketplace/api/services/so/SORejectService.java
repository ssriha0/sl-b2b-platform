/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 03-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.RejectSORequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.RejectSOResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO.Resource;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.ordermanagement.services.OrderManagementService;

@Namespace("http://www.servicelive.com/namespaces/so")
@APIRequestClass(RejectSORequest.class)
@APIResponseClass(RejectSOResponse.class)
/**
 * This class would act as a service class for Reject SO
 * 
 * @author Infosys
 * @version 1.0
 */
public class SORejectService extends BaseService{
	private IServiceOrderBO serviceOrderBO;
	private IOrderGroupBO orderGroupBO;
	private ILookupBO lookupBO;
	private OrderManagementService managementService;
	private static final Logger LOGGER = Logger.getLogger(SORejectService.class);
	private OFHelper ofHelper;
	private static final Integer PROVIDER_RESP_REJECTED = 3;
	/**
	 * Constructor
	 */

	public SORejectService() {
		super();
		addRequiredURLParam(APIRequestVO.PROVIDER_RESOURCE_ID, 
				DataTypes.STRING);
		addRequiredURLParam(APIRequestVO.SOID, DataTypes.STRING);
	}

	/**
	 * This method is for rejecting a SO.
	 * 
	 * @param apiVO  APIRequestVO
	 * @return RejectSOResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		LOGGER.info("Entering SORejectService.execute()");
		RejectSORequest request;
		RejectSOResponse rejectResponse = null;
		ProcessResponse processResponse = null;
		OrderFulfillmentRequest ofRequest = null;
		OrderFulfillmentResponse ofResponse = null;
		String soId = "";
		SecurityContext securityContext=null;
		try{
			request  = (RejectSORequest) apiVO.getRequestFromPostPut();
			soId = (String)apiVO.getProperty(APIRequestVO.SOID);
			Integer providerResId = Integer.valueOf((String)apiVO.getProperty(APIRequestVO.PROVIDER_RESOURCE_ID));
			securityContext = getSecurityContextForVendor(providerResId);
			if (null == securityContext) {
				rejectResponse = new RejectSOResponse();
				rejectResponse.setResults(Results.getError(
						ResultsCode.VENDOR_RESOURCE_ID_FAILURE.getMessage(), 
						ResultsCode.VENDOR_RESOURCE_ID_FAILURE.getCode()));
			}else{
				Map<String, String> requestMap = (Map<String, String>) apiVO.getProperty(PublicAPIConstant.REQUEST_MAP);
				String groupIndParam = StringUtils.EMPTY;
				if(null != requestMap && requestMap.containsKey(PublicAPIConstant.GROUP_IND_PARAM)){
					groupIndParam = requestMap.get(PublicAPIConstant.GROUP_IND_PARAM);
				}
				String groupId = StringUtils.EMPTY;
				if (StringUtils.isNotBlank(groupIndParam) && PublicAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupIndParam)) {
					groupId = soId;
				}
				processResponse = validateRequest(request,securityContext,soId, groupId);
				if ((PublicAPIConstant.ONE).equalsIgnoreCase(processResponse.getCode())) {
					List<Integer> checkedResourceID = request.getResourceList().getResourceId();
					Integer reasonId = Integer.valueOf(request.getReasonId()); 
					  
					//If request parameters are validated then create OF Request
					ofRequest = createOrderFulfillmentRejectSORequest(securityContext, request);
					//groupId = so.getGroupId();
					//If Group order
					if (StringUtils.isNotBlank(groupId)) {
						if (ofHelper.isNewGroup(groupId)){
							ofResponse = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.REJECT_GROUP,ofRequest);
							processResponse = mapProcessResponse(ofResponse);
						}else{
							processResponse = getOrderGroupBO().rejectGroupedOrder(checkedResourceID.get(0), groupId, reasonId,
									PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId().toString(), request.getReasonDesc(),securityContext);
						}
					}else if(ofHelper.isNewSo(soId)){
						boolean isSOInEditMode = isSOInEditMode(soId);						
						ofResponse = ofHelper.runOrderFulfillmentProcess(soId, SignalType.REJECT_ORDER, ofRequest);
						processResponse = mapProcessResponse(ofResponse, isSOInEditMode );
					}else{
						processResponse = getServiceOrderBO().rejectServiceOrder(checkedResourceID.get(0), soId, reasonId,
								PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId().toString(), request.getReasonDesc(),securityContext);
					}
				} else {
					//Create error response when request parameter validation failed.
					return createErrorResponse(processResponse);
				}
				rejectResponse = createResultResponse(processResponse);
				//Send notification
				sendRejectEmailToBuyer(soId, securityContext);
			}		
		}catch (NumberFormatException ex) {
			LOGGER.error("SORejectService-->execute()-->" +
					"NumberFormatException-->"
					+ ex.getMessage(), ex);
			Results results = Results.getError(
					ResultsCode.
					INVALID_OR_MISSING_REQUIRED_PARAMS.getMessage(),
					ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
			rejectResponse = new RejectSOResponse();
			rejectResponse.setResults(results);
		}catch (BusinessServiceException bsEx) {
			LOGGER.error("Unexpected error in rejecting Service Order: "+bsEx);
			Results results = Results.getError(
					ResultsCode.GENERIC_ERROR.getMessage(),
					ResultsCode.GENERIC_ERROR.getCode());
			rejectResponse = new RejectSOResponse();
			rejectResponse.setResults(results);
			processResponse.setMessage("Unexpected error in rejecting Service Order:");
		}catch (Exception ex) {
			LOGGER.error("SORejectService-->execute()--> Exception-->", ex);
			Results results = Results.getError(
					ResultsCode.GENERIC_ERROR.getMessage(),
					ResultsCode.GENERIC_ERROR.getCode());
			rejectResponse = new RejectSOResponse();
			rejectResponse.setResults(results);
		}	
		LOGGER.info("Leaving SORejectService.execute()");
		return rejectResponse;
	}

	/**
	 * Creates {@link RejectSOResponse}<p>
	 * Sets related Error/Success messge and codes.
	 * @param processResponse 
	 * */
	private RejectSOResponse createResultResponse(
			ProcessResponse processResponse) {
		RejectSOResponse response = new RejectSOResponse();
		if(processResponse == null) {
			response.setResults(Results.getError(
					ResultsCode.REJECT_FAILURE.getMessage(), 
					ResultsCode.REJECT_FAILURE.getCode()));
		} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
			response.setResults(Results.getSuccess(
					ResultsCode.REJECT_SUCCESSFUL.getMessage()));					
		} else {
			List<String> eMsgs = processResponse.getMessages();
			StringBuilder eMsg = new StringBuilder();
			for(String msg: eMsgs) {
				eMsg = eMsg.append("; " + msg);
			}
			LOGGER.error("Error occured in rejecting SO, error code: " 
					+ processResponse.getCode() 
					+ ", messages: " + eMsg);
			response.setResults(Results.getError(eMsgs.get(0), 
					ResultsCode.FAILURE.getCode()));			
		}
		return response;
	}

	/**
	 * Checks whether SO is in Edit Mode.
	 * @param soId.
	 * @return true: When SO is in edit mode. else false
	 * */
	private boolean isSOInEditMode(String soId){
		boolean flag = Boolean.FALSE;
		try {
			flag = getServiceOrderBO().isSOInEditMode(soId);
		} catch (BusinessServiceException e) {
			//this should not happen just logging it
			LOGGER.error("At the time of acceptance getting service order ", e);
		}
		return flag;
	}
	
	/**
	 * Method which validates the request for Reject Service Order API. <p>
	 * @param request : The request XML object. {@link RejectSORequest}
	 * @param soId which is to be rejected for some selected Resources.
	 * @param securityContext 
	 * @param groupId 
	 * @return {@link ProcessResponse} <p>
	 * ProcessResponse.code will be "1" when all the request parameters are valid.
	 * */
	private ProcessResponse validateRequest(RejectSORequest request, SecurityContext securityContext, String soId, String groupId) {
		ProcessResponse response = new ProcessResponse();
		// Set response code as 1 for success (default)
		response.setCode(PublicAPIConstant.ONE);
		List<String> message = new ArrayList<String>();
		if(null != request){
			//Validates Reason code for SO Rejection
			if(!validateReasonCode(request.getReasonId(), request.getReasonDesc())){
				message.add(CommonUtility
						.getMessage(PublicAPIConstant.REJECT_INVALID_REASON_CODE));
			}
			Integer firmId = securityContext.getCompanyId();
			//Validate resource Ids
			String warnMessage =  validateResourceIds(request.getResourceList(), firmId, soId, groupId );
			if(!StringUtils.isBlank(warnMessage)){
				message.add(warnMessage);
			}				
		}
		if(0 < message.size()){
			response.setMessages(message);
			response.setCode(ServiceConstants.USER_ERROR_RC);
		}		
		return response;
	}

	/**
	 * This method validates resources submitted to reject an SO
	 * @param resourceList : List of resources {@link Resource} which is to be validated.
	 * @param firmId : Firm id of the provider
	 * @param soId : SO Id/Group Id which is requested to be rejected for the selected resource ids.
	 * @param groupId : Whether so is grouped.
	 * @return Error/warning message. Empty string is returned when all resources are valid.
	 * */
	private String validateResourceIds(Resource resourceList, Integer firmId, String soId, String groupId) {
		if(null != resourceList){
			List<Integer> resourceIds =  resourceList.getResourceId();
			if(null == resourceIds || resourceIds.size() ==0){
				//When no resources are submitted
				return CommonUtility.getMessage(PublicAPIConstant.REJECT_RESOURCE_ID_REQUIRED);
			}else{
				//Validate resource Ids.
				List<Integer> invalidIds = checkResources(resourceIds, firmId, soId, groupId);
				if(invalidIds.size()>0){
					//Format warning message. Message contains invalid resource ids.
					String warnMessage = CommonUtility.getMessage(PublicAPIConstant.REJECT_RESOURCE_ID_INVALID);
					warnMessage += invalidIds;
					return warnMessage;
				}
			}
		}
		//When all resource ids are valid.
		return "";
	}

	/**
	 * Validates the resource Ids against the service order id and its firm id.<p>
	 * @param resourceIds : List of resource id whcih is to be validated.
	 * @param firmId : Firm id of the provider
	 * @param soId : SO Id whcih is requested to be rejected for the selected resource ids.
	 * @param groupId 
	 * @return List of Invalid resource Ids.
	 * */
	private List<Integer> checkResources(List<Integer> resourceIds, Integer firmId, String soId, String groupId) {
		List<Integer> invalidIds = new ArrayList<Integer>();
		if (null != firmId && null != soId) {
			List<ProviderResultVO> providers;
			if(StringUtils.isNotBlank(groupId)){
				providers = managementService.getEligibleProvidersForGroup(String.valueOf(firmId),groupId);
			}else{
				providers = managementService.getEligibleProviders(String.valueOf(firmId),soId);
			}
			if(null == providers || 0 == providers.size()){
				//Assume that all given resource Ids are invalid.
				return resourceIds;
			}else{
				List<Integer> validIds = new ArrayList<Integer>();
				for(ProviderResultVO resultVO : providers){
					validIds.add(resultVO.getResourceId());
				}
				/*Return empty list as a list of invalid Ids when all resource ids are valid.
				 * Find all invalid Ids and return them as list of integer
				 */
				if(!validIds.containsAll(resourceIds)){
					for(Integer id : resourceIds){
						if(!validIds.contains(id)){
							invalidIds.add(id);
						}
					}
				}
			}
		}
		return invalidIds;
	}
    
    /**
     * Creates error response when request parameter validation failed.
     * @param rejectResponse {@link ProcessResponse}
     * @return {@link RejectSOResponse}
     * */
	private RejectSOResponse createErrorResponse(ProcessResponse rejectResponse) {
		RejectSOResponse rejectSOResponse = new RejectSOResponse();
		Results results = Results.getError(rejectResponse.getMessage(),
				ServiceConstants.USER_ERROR_RC);
		if (results == null) {
			results = Results.getError(rejectResponse.getMessage(),
					ServiceConstants.SYSTEM_ERROR_RC);
		}
		rejectSOResponse.setResults(results);
		return rejectSOResponse;
	}

	/**
	 * This method validates the reason code submitted to reject a Service Order.
	 * <p>
	 * Method validates both reason code Id and description. (lu_so_provider_resp_reason = 3)
	 * @param reasonId: Reason code id
	 * @param reasonDesc : Reason code description.
	 * @return true: when both reason code id and description are valid.<p>
	 * false : When either of the args is invalid.
	 * **/
	private boolean validateReasonCode(String reasonId, String reasonDesc) {
		LuProviderRespReasonVO luReasonVO = new LuProviderRespReasonVO();
		luReasonVO.setSearchByResponse(PROVIDER_RESP_REJECTED);
		ArrayList<LuProviderRespReasonVO> listReasonCode;
		try {
			listReasonCode = lookupBO.getProviderRespReason(luReasonVO);
			if(null == listReasonCode || 0 == listReasonCode.size()){
				return false;
			}else{
				int reasonCode = Integer.valueOf(reasonId);
				for(LuProviderRespReasonVO reasonVO : listReasonCode){
					if(reasonCode == reasonVO.getRespReasonId()&& reasonDesc.equalsIgnoreCase(reasonVO.getDescr())){
						return true;
					}
				}
			}

		} catch (DataServiceException e) {
			LOGGER.error(e);
		}
		return false;
	}

	/**
	 * Creates OF Request from API Request. Sets only the list of resource for whom
	 * the service order is requested to be rejected.
	 * @return {@link OrderFulfillmentRequest}
	 * **/
	private OrderFulfillmentRequest createOrderFulfillmentRejectSORequest(SecurityContext securityContext, RejectSORequest request){
		OrderFulfillmentRequest rejectOfrequest = new OrderFulfillmentRequest();
		Identification idfn = OFMapper.createProviderIdentFromSecCtx(securityContext);
		rejectOfrequest.setIdentification(idfn);
		List<Integer> checkedResourceID=null;
		if(null != request.getResourceList()){
			checkedResourceID = request.getResourceList().getResourceId();
		}else{
			return null;
		}
		List<RoutedProvider> rejectProviders = getRejectedProviders(checkedResourceID, request.getReasonId(), request.getReasonCommentDesc(), PROVIDER_RESP_REJECTED, securityContext.getVendBuyerResId());
		SOElementCollection collection=new SOElementCollection();
		collection.addAllElements(rejectProviders);				
		rejectOfrequest.setElement(collection);
		return rejectOfrequest;
	}

	/**
	 * Creates a List of {@link RoutedProvider} from the list for resource Ids for whom the SO to be
	 * rejected.
	 * @param checkedResourceID : The list of Resource Ids
	 * @param reasonId : Reason code Id
	 * @param responseId : {lu_so_provider_resp_reason}
	 * @param modifiedBy : Resource iD of Provider Firm
	 * @return List<RoutedProvider>
	 * */
	private List<RoutedProvider> getRejectedProviders(List<Integer> checkedResourceID ,String reasonId, String venderResonCommentDesc, int responseId, Integer modifiedBy) {
		List<RoutedProvider> rejectedResources=new ArrayList<RoutedProvider>();
		for(int resId: checkedResourceID){
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setProviderResourceId((long)resId);
			routedProvider.setProviderRespReasonId(Integer.valueOf(reasonId));
			routedProvider.setProviderRespComment(venderResonCommentDesc);
			routedProvider.setProviderResponse(ProviderResponseType.fromId(responseId));
			routedProvider.setModifiedBy(String.valueOf(modifiedBy));
			Date now = Calendar.getInstance().getTime();
			routedProvider.setProviderRespDate(now);
			routedProvider.setModifiedDate(now);
			rejectedResources.add(routedProvider);
		}
		return rejectedResources;
	}

	/**
	 * Sends mail after SO is rejeected.
	 * */
	private void sendRejectEmailToBuyer(String soId, SecurityContext securityContext){
		boolean isAlertNeeded = getServiceOrderBO().isRejectAlertNeeded(soId);
		if (isAlertNeeded){
			getServiceOrderBO().sendAllProviderRejectAlert(soId, securityContext);
		}
	}

	/**
	 * Creates {@link ProcessResponse} from {@link OrderFulfillmentResponse}
	 * @return {@link ProcessResponse}
	 * */
	private ProcessResponse mapProcessResponse(OrderFulfillmentResponse response){
		return mapProcessResponse(response, Boolean.FALSE);
	}
	
	/**
	 * Creates {@link ProcessResponse} from {@link OrderFulfillmentResponse}
	 * <p>When SO is being edited, it sets a meaning full warning message.
	 * @return {@link ProcessResponse}
	 * */
	private ProcessResponse mapProcessResponse(OrderFulfillmentResponse response, boolean isSOInEditMode){
		ProcessResponse returnVal = new ProcessResponse();
		if(!response.isSignalAvailable() && isSOInEditMode){
			returnVal.setCode(ServiceConstants.USER_ERROR_RC);
			returnVal.setMessage(OrderConstants.ORDER_BEING_EDITED);
		}
		else if(!response.getErrors().isEmpty()){
			returnVal.setCode(ServiceConstants.USER_ERROR_RC);
			returnVal.setMessage(response.getErrorMessage());
		}else{
			returnVal.setCode(ServiceConstants.VALID_RC);
			returnVal.setMessage("Request Successfully executed.");
		}
		return returnVal;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}
}
