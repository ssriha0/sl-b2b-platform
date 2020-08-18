package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.mobile.beans.so.accept.MobileSOAcceptRequest;
import com.newco.marketplace.api.mobile.beans.so.accept.MobileSOAcceptResponse;
import com.newco.marketplace.api.mobile.beans.vo.AcceptVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.provider.IResourceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class would act as a Service for SO Accept API
 * 
 * @author Infosys $ $Date: 2015/04/28
 * @version 1.0
 */

@APIRequestClass(MobileSOAcceptRequest.class)
@APIResponseClass(MobileSOAcceptResponse.class)
public class MobileSOAcceptService extends BaseService {
	private static final Logger LOGGER = Logger.getLogger(MobileSOAcceptService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericValidator validator;
	private MobileGenericMapper mobileGenericMapper;
	
	private OFHelper ofHelper;
	private IServiceOrderBO serviceOrderBo;
	private IResourceBO resourceBO;
    /**
	 * This method handle the main logic for accepting service order.
	 * @param apiVO
	 */
	public IAPIResponse execute(final APIRequestVO apiVO) {
		ProcessResponse processResponse = null;
		String soId = null;
		SecurityContext securityContext = null;
		boolean acceptByFirmInd = false;
		Integer resourceIdUrl = null;
		Integer resourceIdRequest = null;
		String groupIndParam = "";
		String groupId = "";
		Integer preferenceValue;
		AcceptVO acceptVo = null;
		final MobileSOAcceptRequest request  = (MobileSOAcceptRequest)apiVO.getRequestFromPostPut();
		MobileSOAcceptResponse mobileSOAcceptResponse = new MobileSOAcceptResponse();
		soId = (String) apiVO.getSOId();
		resourceIdUrl = apiVO.getProviderResourceId();
		//Denotes SO to be accepted for a particular resource or Firm.
		acceptByFirmInd = request.getAcceptByFirmInd();
		resourceIdRequest = request.getAcceptedResource();
		Map<String, Object> reqMap = (Map<String, Object>)apiVO.getProperties().get(PublicAPIConstant.REQUEST_MAP);
		if(null != reqMap && null != reqMap.get(PublicMobileAPIConstant.GROUP_IND_PARAM)){
			groupIndParam = (String) reqMap.get(PublicMobileAPIConstant.GROUP_IND_PARAM);
		}
		
		preferenceValue = request.getPreferenceInd();
		try {
			//Security context of Vendor
			securityContext = getSecurityContextForVendor(resourceIdUrl);
			
				if(PublicMobileAPIConstant.GROUPED_SO_IND.equals(groupIndParam)){
					groupId=soId;
				}
				acceptVo = mobileGenericMapper.mapAcceptSoRequest(apiVO,groupId,resourceIdRequest,acceptByFirmInd);
				mobileSOAcceptResponse = validator.validateSOAcceptMobile(acceptVo,securityContext);
				
				if(null != mobileSOAcceptResponse){
					return mobileSOAcceptResponse;
				}
				processResponse = executeSOAccept(acceptVo, securityContext);
				mobileSOAcceptResponse = mobileGenericMapper.createAcceptResponse(processResponse);
				 if (processResponse.getCode() == ServiceConstants.VALID_RC){
					 ServiceDatetimeSlot serviceDatetimeSlot= serviceOrderBo.getSODateTimeSlot(soId,  preferenceValue);
					 if (serviceDatetimeSlot!=null)
					 serviceOrderBo.updateAcceptedServiceDatetimeSlot(serviceDatetimeSlot);
				 }
		} catch (Exception ex) {
			LOGGER.error("Exception Occurred inside SOAcceptService.execute"+ ex);
			mobileSOAcceptResponse = MobileSOAcceptResponse.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		}
		return mobileSOAcceptResponse;
	}

	
	/**
	 * Method to accept service order or grouped order.
	 * @param soId
	 * @param groupId
	 * @param resourceId
	 * @param acceptByFirmInd
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProcessResponse executeSOAccept(AcceptVO acceptVo,SecurityContext securityContext ) throws BusinessServiceException{
		ProcessResponse response =null;
		try{
			if(null!=acceptVo){
				/*if (StringUtils.isNotBlank(acceptVo.getGroupId())) {
					//Accept Grouped SO
					response= acceptGroupedOrder(acceptVo.getGroupId(), acceptVo.getAcceptedResourceId(), acceptVo.isAcceptByFirmInd(), securityContext);
				}else{*/
					//Accept Individual SO
					response= acceptServiceOrder(acceptVo.getSoId(),  acceptVo.getAcceptedResourceId(), acceptVo.isAcceptByFirmInd(), securityContext);
/*				}
*/			}
		
		}catch (BusinessServiceException e) {
			LOGGER.error("Exception in processing request for accepting service order"+ acceptVo.getSoId()+ e.getMessage());
		    throw new BusinessServiceException(e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * Method to accept grouped order.
	 * @throws BusinessServiceException 
	 */
	private ProcessResponse acceptGroupedOrder(String groupId, Integer resourceId, boolean  acceptByFirmInd, SecurityContext securityContext) throws BusinessServiceException {
		Integer vendorId = securityContext.getCompanyId();
		SignalType signalType;
		OrderFulfillmentResponse ofResponse;
		ProcessResponse processResponse = null;
		OrderFulfillmentRequest acceptRequest = null;
		boolean isSOInEditMode = false;
        try{
        	/*Currently none of the child gets on locked in edit mode once a grouped order is edited.
        	  Need to remove this code later getting confirmation */
        	isSOInEditMode = mobileGenericBO.isGroupedOrderInEditMode(groupId);
        	if(isSOInEditMode){
        		processResponse = new ProcessResponse();
        		//Set warning message when SO in Edit Mode.
				processResponse.setCode(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getCode());
				processResponse.setMessage(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getMessage());
        	}else{
				acceptRequest = createOFRequestForAcceptance(vendorId, resourceId,securityContext,acceptByFirmInd);
				//Use proper Signals based on whether SO to be accepted for resource / Firm.
				signalType = acceptByFirmInd?SignalType.ACCEPT_GROUP_FOR_FIRM : SignalType.ACCEPT_GROUP;
				ofResponse = ofHelper.runOrderFulfillmentGroupProcess(groupId,signalType, acceptRequest);
				//Create Process response from OFResponse
				processResponse = OFMapper.mapProcessResponse(ofResponse);
        	}
		}catch (Exception e) {
			//Create Error Response when Order Processing fails.
			LOGGER.info("error occured while accepting grouped Order grouped Order-->" + groupId);
			throw new BusinessServiceException(e.getMessage());
			
		}
		return processResponse;
	}
	
	
	/**
	 * Method to accept service order.
	 * @throws BusinessServiceException 
	 */
	private ProcessResponse acceptServiceOrder(String soId, Integer resourceId, boolean acceptByFirmInd, SecurityContext securityContext) throws BusinessServiceException {
		ProcessResponse processResponse = null;
		Integer vendorId = securityContext.getCompanyId();
		boolean isSOInEditMode = false;
		SignalType signalType = null;
		OrderFulfillmentRequest acceptRequest = null;
		OrderFulfillmentResponse responseOF = null;
		try{
			//Check whether SO in Edit Mode
			isSOInEditMode = serviceOrderBo.isSOInEditMode(soId);
			if (isSOInEditMode) {
				    processResponse = new ProcessResponse();
					//Set warning message when SO in Edit Mode.
					processResponse.setCode(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getCode());
					processResponse.setMessage(ResultsCode.SO_ACCEPT_IN_EDIT_MODE.getMessage());
			} else {	
					//Create OF request for SO Accept
					acceptRequest = createOFRequestForAcceptance(vendorId, resourceId,securityContext,acceptByFirmInd);
					//Set signals according to acceptedByfirmIndicator value.
					signalType = acceptByFirmInd?SignalType.ACCEPT_FOR_FIRM : SignalType.ACCEPT_ORDER;
					responseOF = ofHelper.runOrderFulfillmentProcess(soId, signalType, acceptRequest);
					//Create Process response from Of response 
					processResponse = OFMapper.mapProcessResponse(responseOF);
				}
			} catch (Exception bse) {
			LOGGER.error("Exception thrown accepting SO", bse);
			throw new BusinessServiceException(bse.getMessage());
		}
		return processResponse;
	}
	
	/**
	 * Method to create OF Request For Acceptance.
	 */
	private OrderFulfillmentRequest createOFRequestForAcceptance(Integer vendorId, Integer resourceId, SecurityContext securityContext,boolean acceptByFirmInd) throws BusinessServiceException {
		OrderFulfillmentRequest acceptOfrequest = new OrderFulfillmentRequest();
		ServiceOrder serviceOrder = new ServiceOrder();
		Contact vendorContact = null;
		serviceOrder = setBasicFirmDetails(vendorId,serviceOrder);
		if(acceptByFirmInd){
			//When assignment type is Firm, set Assignment type misc params
			serviceOrder.setAssignmentType(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM);
			String name = null;
			try{
				name = serviceOrderBo.getVendorBusinessName(securityContext.getCompanyId());
			}catch (Exception bse) {
				LOGGER.error("error fetching business name of firm", bse);
				throw new BusinessServiceException(bse.getMessage());
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
			} catch (Exception bse) {
				LOGGER.error(bse);
				throw new BusinessServiceException(bse.getMessage());
			}
			//Set contact and address of Vendor Resource
			serviceOrder = setContactLocation(vendorContact,serviceOrder);
			acceptOfrequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE, vendorContact.getStateCd());
		}
		acceptOfrequest.setElement(serviceOrder);
		acceptOfrequest.setIdentification(OFMapper.createProviderIdentFromSecCtx(securityContext));
		return acceptOfrequest;
	}
	
	private ServiceOrder setBasicFirmDetails(Integer vendorId,ServiceOrder serviceOrder) {
		serviceOrder.setAcceptedProviderId(vendorId.longValue());
		serviceOrder.setSoTermsCondId( PublicAPIConstant.acceptSO.TERMS_AND_COND_IND_PROVIDER_ACCEPT);	
		serviceOrder.setProviderSOTermsCondInd(1);
		serviceOrder.setProviderTermsCondDate(Calendar.getInstance().getTime());
		return serviceOrder;
	}
	
	private ServiceOrder setContactLocation(Contact vendorContact,
			ServiceOrder serviceOrder) {
		SOContact contact = OFMapper.mapContact(vendorContact);
		serviceOrder.addContact(contact);
		SOLocation soLocation = OFMapper.mapLocation(vendorContact);
		serviceOrder.addLocation(soLocation);
		return serviceOrder;
	}
	
	
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(final IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericValidator getValidator() {
		return validator;
	}

	public void setValidator(final MobileGenericValidator validator) {
		this.validator = validator;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}


	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}


	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}


	public IResourceBO getResourceBO() {
		return resourceBO;
	}


	public void setResourceBO(IResourceBO resourceBO) {
		this.resourceBO = resourceBO;
	}
	
	
}