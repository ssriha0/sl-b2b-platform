package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleRequest;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleResponse;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification.NotificationServiceImpl;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/05/04
 * for validating phase 2 mobile APIs
 *
 */
@APIRequestClass(MobileSOSubmitRescheduleRequest.class)
@APIResponseClass(MobileSOSubmitRescheduleResponse.class)
public class MobileSOSubmitRescheduleService extends BaseService {
	private static final Logger LOGGER = Logger.getLogger(MobileSOSubmitRescheduleService.class);
	private MobileGenericValidator mobileGenericValidator;
	private MobileGenericMapper mobileGenericMapper;
	//changes
	private IServiceOrderBO serviceOrderBo;
	private OFHelper ofHelper;
	private NotificationServiceImpl notificationService;

	public MobileSOSubmitRescheduleService() {
		// calling the BaseService constructor to initialize
		super();
	}
	/**
	 * Method to execute submitReschedule
	 */
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LOGGER.info("Entering MobileSOSubmitRescheduleService.execute()");
		MobileSOSubmitRescheduleResponse submitRescheduleResponse = new MobileSOSubmitRescheduleResponse();
		MobileSOSubmitRescheduleRequest submitRescheduleRequest =(MobileSOSubmitRescheduleRequest)apiVO.getRequestFromPostPut();	
		
		com.newco.marketplace.vo.ordermanagement.so.RescheduleVO rescheduleVOFromDB = null;
		SecurityContext securityContext = null;	
		SOSchedule soSchedule = null;
		Results results = null;
		
		String soId = apiVO.getSOId();
		Integer  providerResourceId = apiVO.getProviderResourceId();
		securityContext = getSecurityContextForVendor(providerResourceId);
		Identification ident = mobileGenericMapper.createOFIdentityFromSecurityContext(securityContext);
		
		String timeZone = "";
		try{
			rescheduleVOFromDB = serviceOrderBo.getRescheduleInfo(soId);//Existing reschedule data obtained from so_hdr for that particular SO
			timeZone = serviceOrderBo.getServiceLocTimeZone(soId);
			soSchedule = mobileGenericMapper.mapRescheduleRequest(submitRescheduleRequest,timeZone);//Reschedule data obtained from request
			
			LOGGER.info("soSchedule.getServiceDate1 Info"+soSchedule.getServiceDate1());
			LOGGER.info("soSchedule.getServiceTimeStart Info"+soSchedule.getServiceTimeStart());
			//soSchedule.setCreatedViaAPI(true);
			//Removing the unwanted code, CC-1067
			/*List<String> validateErrors =  new ArrayList<String>();
			validateErrors = soSchedule.validateSheduleForAPI(timeZone);*/
			
			submitRescheduleResponse = mobileGenericValidator.validateSubmitReschedule(timeZone, soSchedule,submitRescheduleResponse,soId);
			results = submitRescheduleResponse.getResults();
			if(null!= results && null != results.getError() && results.getError().size()>0){
				submitRescheduleResponse.setResults(results);
			}
			else{
			String reason = "";
			InHomeRescheduleVO notificationServiceVO = null;
			reason = serviceOrderBo.getRescheduleReason(submitRescheduleRequest.getSoRescheduleInfo().getReasonCode());
			//CC-1067 issue fix
			Integer buyerId = serviceOrderBo.getBuyerId(soId);
			String rescheduleMessage = "";
			if(null != buyerId && InHomeNPSConstants.HSRBUYER.equals(buyerId)){
				notificationServiceVO = mobileGenericMapper.mapInHomeRescheduleVO(rescheduleVOFromDB,soSchedule);
				rescheduleMessage = notificationService.createRescheduleMessageForProviderRescheduleAPI(notificationServiceVO); 
			}
			
			OrderFulfillmentResponse response = null;
			
				SignalType signalType = SignalType.PROVIDER_REQUEST_RESCHEDULE;
				List<Parameter> parameters = new ArrayList<Parameter>();
				StringBuffer reschedulePeriod =  null;
				
				// Adding old and new reschedule date to so_logging data when a provider try to edit reschedule.
				
				reschedulePeriod = mobileGenericMapper.mapReschedulePeriod(rescheduleVOFromDB, soSchedule);
		    	//changing logging flow in case of edit reschedule request
		    	if(null!= rescheduleVOFromDB && rescheduleVOFromDB.getRescheduleServiceStartDate()!=null)
	        	{
	        		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST, " edited reschedule request"));
	        	}
				else{
					parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST, " requested reschedule"));
				}
		    	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_COMMENT, submitRescheduleRequest.getSoRescheduleInfo().getComments()));
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RESCHEDULE_REQUEST_REASON_CODE,  " Reason: "+reason));
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_DATE_INFO, reschedulePeriod.toString()));
				LOGGER.info("requestRescheduleSO.rescheduleMessage : "+rescheduleMessage);
	            parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_AUTOACCEPT_MESSAGE, rescheduleMessage));
	            
	            //Setting call code for creating request xml of Service Operation API
	            parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
				response = ofHelper.runOrderFulfillmentProcess(soId, signalType, soSchedule, ident, parameters);
				
				submitRescheduleResponse = mobileGenericMapper.mapOFResponse(response,soId,soSchedule,reason,submitRescheduleRequest.getSoRescheduleInfo().getComments());
			}
		}catch (Exception e) {
			LOGGER.error("Exception Occurred inside MobileSOSubmitRescheduleService.execute"+ e);
			e.printStackTrace();
		}
		return submitRescheduleResponse;
	}	
	
	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}
	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}
	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
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
	
	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}
	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}
	
	
}