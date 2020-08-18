package com.newco.marketplace.api.services.leadsmanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AddRatingsAndReviewRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AddRatingsAndReviewResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentResponse;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ScheduleAppointmentMailVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ScheduleRequestVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;


public class ScheduleAppointmentService extends BaseService {
	private static Logger logger = Logger
			.getLogger(ScheduleAppointmentService.class);
	private LeadProcessingBO leadProcessingBO;
	private LeadManagementValidator leadManagementValidator;
	
	public static String POINTS_AWARDED = "300";
	public static String formateDate="MM/dd/yyyy";
	public static String formatTime="HH:mm";
	public static String parseDate="yyyy-MM-dd";
	public static String parseTime="hh:mm a";
	public ScheduleAppointmentService() {
		super(
				PublicAPIConstant.SCHEDULE_INFO_REQUEST_XSD,
				PublicAPIConstant.SCHEDULE_INFO_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.SCHEDULE_INFO__RESPONSE_SCHEMALOCATION,
				ScheduleAppointmentRequest.class,
				ScheduleAppointmentResponse.class);
	}
 
	@Override
	public IAPIResponse execute(APIRequestVO apiVO){
		ScheduleAppointmentRequest scheduleAppointmentRequest = (ScheduleAppointmentRequest) apiVO
				.getRequestFromPostPut();
		ScheduleAppointmentResponse response = new ScheduleAppointmentResponse();
		ScheduleAppointmentMailVO scheduleVO=new ScheduleAppointmentMailVO();
		ScheduleRequestVO scheduleReqVO=new  ScheduleRequestVO();
       
		//setTestResponse(updateMembershipInfoRequest, response);
		// Invoke Validation Service to validate the request
		try {
			scheduleAppointmentRequest = leadManagementValidator
					.validate(scheduleAppointmentRequest);
			
			if ((ResultsCode.SCHEDULE_SUCCESS != scheduleAppointmentRequest
					.getValidationCode())&&(ResultsCode.RESCHEDULE_SUCCESS != scheduleAppointmentRequest
							.getValidationCode())){
				return createErrorResponse(scheduleAppointmentRequest
						.getValidationCode().getMessage(),
						scheduleAppointmentRequest.getValidationCode().getCode());
			   }
		   } 
		catch (Exception e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		try {
	           //getting security context 
				String reqType=null;
				SecurityContext securityContext=null;
				if(null!= scheduleAppointmentRequest.getIdentification()){
					reqType= scheduleAppointmentRequest.getIdentification().getType();
				}
				if(null != reqType && reqType.equals(NewServiceConstants.RESOURCE_ID)){
					Integer providerResourceId =null;					
					providerResourceId = scheduleAppointmentRequest.getIdentification().getId();
					securityContext = getSecurityContextForVendor(providerResourceId);
	                securityContext.getUsername();
				}
				else{
				securityContext = getSecurityContextForVendorAdmin(apiVO.getProviderResourceId());
				}
			leadProcessingBO.scheduleAppointment(scheduleAppointmentRequest);
			scheduleReqVO.setLeadId(scheduleAppointmentRequest.getLeadId());
			scheduleReqVO.setVendorId(scheduleAppointmentRequest.getVendorId());
			scheduleVO=leadProcessingBO.getScheduleMailDeatils(scheduleReqVO);
			leadProcessingBO.sendRescheduleMailToCustomer(scheduleAppointmentRequest, scheduleVO);
			 //Logging details into lead history
			
			 historyLogging(scheduleAppointmentRequest,securityContext.getUsername());
			
		} catch (BusinessServiceException e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		response.setResults(Results.getSuccess());		
		response.setLeadId(scheduleAppointmentRequest
				.getLeadId());
		
		return response;
	}

	public void historyLogging(ScheduleAppointmentRequest scheduleAppointmentRequest,String userName)
			throws BusinessServiceException {

		int actionId = NewServiceConstants.LEAD_SCHEDULED;
		String comments = "";
		Date appointmentDate=null;
		String appointmentDateString = "";
		String startTimeString = scheduleAppointmentRequest.getServiceStartTime();
		String endTimeString = scheduleAppointmentRequest.getServiceEndTime();
		
		if(StringUtils.isNotBlank(scheduleAppointmentRequest.getScheduledDate())){
			try {
				appointmentDate=new SimpleDateFormat(parseDate).parse(scheduleAppointmentRequest.getServiceDate());
			} catch (ParseException e) {
				logger.info("Exception in parsing Date"+ e.getMessage());
				}
			  appointmentDateString=new SimpleDateFormat(formateDate).format(appointmentDate);
			}
		//setting variables as for rescheduling
		if (StringUtils.isNotBlank(scheduleAppointmentRequest.getScheduledDate())) {
			comments = NewServiceConstants.RESCHEDULED_WITH_REASON + " "+
					   appointmentDateString +" "+ startTimeString+" "+
					   "to" + " " + endTimeString+" "+
					   "for"+" "+scheduleAppointmentRequest.getResheduleReason();
			actionId = NewServiceConstants.LEAD_RESCHEDULED;
			
		}
		//setting variables as for scheduling
		else {
			comments = NewServiceConstants.STATUS_CHANGE_SCHEDULED +" "+ NewServiceConstants.REASON + scheduleAppointmentRequest.getResheduleReason();;
		}
		try {
			/* commenting getting user name of firm and taking it from securitycontext to log into history		 
			String userName = leadProcessingBO.getUserName(scheduleAppointmentRequest.getVendorId().toString());*/
			String createdBy=scheduleAppointmentRequest.getIdentification().getFullName();
			String modifiedBy=scheduleAppointmentRequest.getIdentification().getUserName();
			Integer entityId=scheduleAppointmentRequest.getIdentification().getEntityId();
			
			//logging into lead history
			LeadLoggingVO leadLoggingVO = new LeadLoggingVO(
					scheduleAppointmentRequest.getLeadId(), actionId,
					NewServiceConstants.WORKING_LEAD_STATUS,
					NewServiceConstants.SCHEDULE_LEAD_STATUS, comments, createdBy,
					modifiedBy, NewServiceConstants.ROLE_ID_PROVIDER ,entityId);
			leadProcessingBO.insertLeadLogging(leadLoggingVO);
		} catch (BusinessServiceException e) {
			logger.error("Exception in historyLogging " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}
		public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	@SuppressWarnings("unchecked")
	private void setTestResponse(
			AddRatingsAndReviewRequest addRatingsAndReviewRequest,
			AddRatingsAndReviewResponse addRatingsAndReviewResponse) {
		addRatingsAndReviewResponse.setLeadId(addRatingsAndReviewRequest
				.getLeadId());
		addRatingsAndReviewResponse.setResults(Results.getSuccess());
	}
	
	private ScheduleAppointmentResponse createErrorResponse(String message, String code){
		ScheduleAppointmentResponse createResponse = new ScheduleAppointmentResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}

	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}	

}
