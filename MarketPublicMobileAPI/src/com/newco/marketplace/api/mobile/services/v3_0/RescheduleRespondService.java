package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleRespondRequest;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification.NotificationServiceImpl;
import com.newco.marketplace.business.iBusiness.buyerOutBoundNotification.MobileOutBoundNotificationVo;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;


@APIRequestClass(RescheduleRespondRequest.class)
@APIResponseClass(RescheduleResponse.class)
public class RescheduleRespondService extends BaseService{
	private static final Logger LOGGER = Logger.getLogger(RescheduleRespondService.class);
	private MobileGenericValidator mobileValidator;
	private IMobileGenericBO mobileBO;
	private MobileGenericMapper mobileMapper;
	private OFHelper ofHelper;
	private NotificationServiceImpl notificationService;
	//private OFMapper ofMapper;
	
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		RescheduleResponse rescheduleResponse = new RescheduleResponse();
		RescheduleRespondRequest respondRequest = (RescheduleRespondRequest) apiVO.getRequestFromPostPut();
		SecurityContext securityContext =null;
		RescheduleVO rescheduleVo = null;
		MobileOutBoundNotificationVo buyerOutboundNotificationVO=null;
		String strSuccessCode = null;
		String ofResponse = null;
		String message = null;
		try{
			rescheduleVo = mobileMapper.mapRescheduleRespondRequest(respondRequest,apiVO);
			rescheduleVo = mobileValidator.validateRescheduleRespond(rescheduleVo);
			if(ResultsCode.SUCCESS.getCode().equals(rescheduleVo.getValidationCode().getCode())){
				securityContext = getSecurityContextForVendor(apiVO.getProviderResourceId());
				//Fetching reschedule info  for RI buyer Reschedule Accept.
			
				ofResponse = respondToReschedule(rescheduleVo,securityContext);
				
				if(null!=ofResponse){
				strSuccessCode = ofResponse.substring(0,2);
				// checking of response to continue with  NPS notification for RI.
				if (null!= strSuccessCode && strSuccessCode.equalsIgnoreCase(ResultsCode.SUCCES_OF_CALL.getCode())){
					buyerOutboundNotificationVO = mobileBO.getRescheduleInformations(rescheduleVo.getSoId());
					if(null!=buyerOutboundNotificationVO && MPConstants.RESCHEDULE_ACCEPT.equalsIgnoreCase(rescheduleVo.getRescheduleRespondType())
							&& rescheduleVo.getBuyerId().intValue()== MPConstants.SEARS_BUYER_INT){
						//CHOICE WEB SERVICE invocation.
					try{
						mobileBO.invokeChoiceWebService(rescheduleVo,buyerOutboundNotificationVO);
						}catch (Exception e) {
							LOGGER.error("Exception in notifying choice web service for RI Reschedule Accept"+ e.getMessage());
							// Continuing the execution.
						}
					}
					//setting success Response
					rescheduleResponse = (RescheduleResponse) mobileMapper.createSuccessResponse(rescheduleVo.getRescheduleRespondType());
				// Error in signal execution for accepting/reject/cancel reschedule request.
				}else{
					message = ofResponse.substring(2);
					rescheduleResponse = mobileMapper.createErrorResponseForOFError(strSuccessCode, message);
				}
			}
			}else{
				//Error Response for validating reschedule accept/reject/cancel eligibility.
				rescheduleResponse = (RescheduleResponse) mobileMapper.createErrorResponse(rescheduleVo.getRescheduleRespondType());
			}
		}catch (Exception e) {
			LOGGER.error("Exception in reschedule Request"+ respondRequest.getResponseType()+"by the provider");
			rescheduleVo.setValidationCode(ResultsCode.INTERNAL_SERVER_ERROR);
			rescheduleResponse = (RescheduleResponse) mobileMapper.createErrorResponse(rescheduleVo);
		}
		return rescheduleResponse;
	}
	
	public String respondToReschedule(RescheduleVO rescheduleVo,SecurityContext securityContext)throws BusinessServiceException {
	String returnVal= "";
	OrderFulfillmentResponse response =null;
	List<Parameter> parameters = null;
	ProcessResponse processResponse = null;
	Identification identification = mobileMapper.createOFIdentityFromSecurityContext(securityContext);
	try{
		//Accept Reschedule Request
		if(null!= rescheduleVo && MPConstants.RESCHEDULE_ACCEPT.equals(rescheduleVo.getRescheduleRespondType())){
			// Method to set Of parameters required for notifying NPS for inhome orders when reschedule accepts.
			  parameters = setParametersForNPSNotification(rescheduleVo);
			  response = ofHelper.runOrderFulfillmentProcess(rescheduleVo.getSoId(), SignalType.PROVIDER_ACCEPT_RESCHEDULE, new SOSchedule(),identification,parameters);
			  returnVal = mobileMapper.setOFResults(response, processResponse);
		   }//Reject Reschedule Request	
		   else if(null!= rescheduleVo && MPConstants.RESCHEDULE_REJECT.equals(rescheduleVo.getRescheduleRespondType())){
			  response = ofHelper.runOrderFulfillmentProcess(rescheduleVo.getSoId(), SignalType.PROVIDER_REJECT_RESCHEDULE, new SOSchedule(),identification);
			  returnVal = mobileMapper.setOFResults(response, processResponse);
		  }//Cancel Reschedule Request.
		else{
			  response = ofHelper.runOrderFulfillmentProcess(rescheduleVo.getSoId(), SignalType.PROVIDER_CANCEL_RESCHEDULE, new SOSchedule(),identification);
			  returnVal = mobileMapper.setOFResults(response, processResponse);
		 }
	}catch (Exception e) {
		LOGGER.error("Exception in processing OF signal for accept/reject/cancel reschedule request"+ e.getMessage());
		throw new BusinessServiceException("Exception in processing OF signal"+ e.getMessage());
	}
	LOGGER.info("Results Message after OF SIGNAL"+ returnVal);
	return returnVal;
}

	private List<Parameter> setParametersForNPSNotification(RescheduleVO rescheduleVo) throws BusinessServiceException {
		List<Parameter> parameters = new ArrayList<Parameter>();
		String rescheduleMessage =null;
		InHomeRescheduleVO input=new InHomeRescheduleVO();
    	InHomeRescheduleVO result=new InHomeRescheduleVO();
    	input.setSoId(rescheduleVo.getSoId());
    	input.setIsNPSMessageRequired(true);
    	try {
			result=notificationService.getSoDetailsForReschedule(input);
			rescheduleMessage = result.getRescheduleMessage();
			//Setting message  for creating request xml of Send Message API
			parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE, rescheduleMessage));
        	//Setting call code for creating request xml of Service Operation API
        	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
		} catch (Exception e) {
			LOGGER.error("Exception in setting messages for NPS for reschedule accept");
			throw new BusinessServiceException(e.getMessage());
		}
		return parameters;
	}
	
	
	public MobileGenericValidator getMobileValidator() {
		return mobileValidator;
	}

	public void setMobileValidator(MobileGenericValidator mobileValidator) {
		this.mobileValidator = mobileValidator;
	}

	public IMobileGenericBO getMobileBO() {
		return mobileBO;
	}

	public void setMobileBO(IMobileGenericBO mobileBO) {
		this.mobileBO = mobileBO;
	}

	public MobileGenericMapper getMobileMapper() {
		return mobileMapper;
	}

	public void setMobileMapper(MobileGenericMapper mobileMapper) {
		this.mobileMapper = mobileMapper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public NotificationServiceImpl getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationServiceImpl notificationService) {
		this.notificationService = notificationService;
	}

}
