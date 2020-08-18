package com.newco.marketplace.api.services.leadsmanagement;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AddRatingsAndReviewRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoResponse;
import com.newco.marketplace.dto.vo.leadsmanagement.ScheduleRequestVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadVO;

import com.newco.marketplace.utils.TimeUtils;


public class LeadManagementValidator {
	private static Logger logger = Logger
			.getLogger(LeadManagementValidator.class);
	private LeadProcessingBO leadProcessingBO;
	private static String ZERO = "0";

	public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public FetchProviderFirmRequest validate(
			FetchProviderFirmRequest fetchProviderFirmRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != fetchProviderFirmRequest
				&& StringUtils.isNotBlank(fetchProviderFirmRequest
						.getCustomerZipCode())) {
			try {
				if (ZERO.equals(leadProcessingBO
						.validateLaunchZip(fetchProviderFirmRequest
								.getCustomerZipCode()))) {
					validationResponse = ResultsCode.INVALID_ZIP_CODES;
					fetchProviderFirmRequest.setValidationCode(validationResponse);
					return fetchProviderFirmRequest;
				}
			} catch (BusinessServiceException e) {
				logger
						.error("Error validating customer zip code against launch markets.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				fetchProviderFirmRequest.setValidationCode(validationResponse);
				return fetchProviderFirmRequest;
			}
		}
		if (null != fetchProviderFirmRequest
				&& StringUtils.isNotBlank(fetchProviderFirmRequest
						.getPrimaryProject())) {
			try {
				fetchProviderFirmRequest = leadProcessingBO
						.validatePrimaryProject(fetchProviderFirmRequest
								.getPrimaryProject(), fetchProviderFirmRequest);
				if (StringUtils.isBlank(fetchProviderFirmRequest.getLeadCategory())) {
					validationResponse = ResultsCode.INVALID_PROJECT;
					fetchProviderFirmRequest.setValidationCode(validationResponse);
					return fetchProviderFirmRequest;
				}
			} catch (BusinessServiceException e) {
				logger.error("Error validating primary project.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				fetchProviderFirmRequest.setValidationCode(validationResponse);
				return fetchProviderFirmRequest;
			}
		}
		fetchProviderFirmRequest.setValidationCode(validationResponse);
		return fetchProviderFirmRequest;
	}

	public LeadRequest validate(SLLeadVO slLeadVO,LeadRequest leadRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != slLeadVO ) {
			try {
				String lmsLeadId = slLeadVO.getLmsLeadId();
				if (null == lmsLeadId) {
				    String slLeadId=slLeadVO.getSlLeadId();
				    if(null==slLeadId ){
					  validationResponse = ResultsCode.INVALID_LEAD_ID;
					  leadRequest.setValidationCode(validationResponse);
					  return leadRequest;
				    }
				}else{
					leadRequest.setLMSLeadId(lmsLeadId);
				}
				
			} catch (Exception e) {
				logger
						.error("Error validating Lead Id.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				leadRequest.setValidationCode(validationResponse);
				return leadRequest;
			}
		}else{
			validationResponse = ResultsCode.INVALID_LEAD_ID;
		}
		
		leadRequest.setValidationCode(validationResponse);
		return leadRequest;
		
	}

	public UpdateMembershipInfoRequest validate(
			UpdateMembershipInfoRequest updateMembershipInfoRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != updateMembershipInfoRequest
				&& StringUtils.isNotBlank(updateMembershipInfoRequest
						.getLeadId())) {
			try {
				//Changes for R14_0 B2C BLM/PLM 
                //all reference to the lms_lead_id is removed
                //Instead the sl_lead_id will be used for validation
				String slLeadId = leadProcessingBO.validateSlleadForPost(updateMembershipInfoRequest.getLeadId());
				if (null == slLeadId) {
					//Changes for BLM/PLM--END
					validationResponse = ResultsCode.INVALID_LEAD_ID;
					updateMembershipInfoRequest.setValidationCode(validationResponse);
					return updateMembershipInfoRequest;
				}else{
				 SLLeadVO leadVO=leadProcessingBO.validateRewardPoints(updateMembershipInfoRequest.getLeadId());
				   if(null != leadVO && (leadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_MATCHED) 
						   ||leadVO.getLeadWfStatus().equalsIgnoreCase(NewServiceConstants.COMPLETED_STATUS) )){
					   
					   if(null== leadVO.getRewardPoints()){
						   updateMembershipInfoRequest.setPointsRewarded(NewServiceConstants.SWYR_REWARD_POINTS_AWARDED);
						   updateMembershipInfoRequest.setEligibleForReward(true);
					   }else{
						   updateMembershipInfoRequest.setPointsRewarded(leadVO.getRewardPoints().toString());
						   updateMembershipInfoRequest.setEligibleForReward(false);
					   }
				   }else{
					    validationResponse = ResultsCode.INVALID_LEAD_MEMBERSHIP_STATUS;
						updateMembershipInfoRequest.setValidationCode(validationResponse);
						return updateMembershipInfoRequest;
				   }
				}				
			} catch (BusinessServiceException e) {
				logger
						.error("Error validating Lead Id.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				updateMembershipInfoRequest.setValidationCode(validationResponse);
				return updateMembershipInfoRequest;
			}
		}
		
		updateMembershipInfoRequest.setValidationCode(validationResponse);
		return updateMembershipInfoRequest;
	}
	
	
	public AddRatingsAndReviewRequest validate(
			AddRatingsAndReviewRequest addRatingsAndReviewRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != addRatingsAndReviewRequest
				&& StringUtils.isNotBlank(addRatingsAndReviewRequest
						.getLeadId())) {
			try {
				//Changes for R14_0 B2C BLM/PLM 
                //all reference to the lms_lead_id is removed
                //Instead the sl_lead_id will be used for validation
				String slLeadId = leadProcessingBO.validateSlleadForPost(addRatingsAndReviewRequest.getLeadId());
				if (null == slLeadId) {
					//Changes for BLM/PLM--END
					validationResponse = ResultsCode.INVALID_LEAD_ID;
					addRatingsAndReviewRequest.setValidationCode(validationResponse);
					return addRatingsAndReviewRequest;
				}
				String completedLead = leadProcessingBO.validateLeadCompletedStatus(addRatingsAndReviewRequest.getLeadId());
				if(null == completedLead) {
					validationResponse = ResultsCode.INVALID_LEAD_STATUS;
					addRatingsAndReviewRequest.setValidationCode(validationResponse);
					return addRatingsAndReviewRequest;
				}
			} catch (BusinessServiceException e) {
				logger
						.error("Error validating Lead Id.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				addRatingsAndReviewRequest.setValidationCode(validationResponse);
				return addRatingsAndReviewRequest;
			}
		}
		
		addRatingsAndReviewRequest.setValidationCode(validationResponse);
		return addRatingsAndReviewRequest;
	}

	
	   
	public ScheduleAppointmentRequest validate(
			ScheduleAppointmentRequest scheduleAppointmentRequest) {
		ResultsCode validationResponse = ResultsCode.SCHEDULE_SUCCESS;
		if (null != scheduleAppointmentRequest
				&& StringUtils.isNotBlank(scheduleAppointmentRequest
						.getLeadId())) {
			try {
				//Changes for R14_0 B2C BLM/PLM 
                //all reference to the lms_lead_id is removed
                //Instead the sl_lead_id will be used for validation
				String slLeadId = leadProcessingBO.validateSlleadForPost(scheduleAppointmentRequest.getLeadId());
				if (null == slLeadId) {
					//Changes for BLM/PLM--END
					validationResponse = ResultsCode.INVALID_LEAD_ID;
					scheduleAppointmentRequest.setValidationCode(validationResponse);
					return scheduleAppointmentRequest;
				}
				Date currentDate=new Date();
				String requestDateString = scheduleAppointmentRequest.getServiceDate();
				Date requestDate = null;
				Timestamp requestTimeStamp = null;
				try{
					requestDate=new SimpleDateFormat("yyyy-MM-dd").parse(requestDateString);
				}catch (Exception e) {
					// TODO: handle exception
					requestDate = new Date();
				}
				requestTimeStamp=new Timestamp(requestDate.getTime());
				ScheduleRequestVO sRequestVO=null;
				Date scheduledDate=null;
				sRequestVO=leadProcessingBO.validateLeadAssosiationWithFirm(scheduleAppointmentRequest);			   
				if(null!=sRequestVO){
					 scheduledDate=sRequestVO.getServiceDate();
					if(null!=sRequestVO.getServiceDate()){
					scheduleAppointmentRequest.setScheduledDate(scheduledDate.toString());
					scheduleAppointmentRequest.setRecheduleIndicator(true);
					validationResponse = ResultsCode.RESCHEDULE_SUCCESS;
					}
				}else{
					validationResponse = ResultsCode.LEAD_FIRM_NOT_MATCHING;
					scheduleAppointmentRequest.setValidationCode(validationResponse);
					return scheduleAppointmentRequest;
				}
			/*	if(null!= requestDate && DateUtils.isSameDay(requestDate, new Date())){
					   if((null != requestTimeStamp) &&(TimeUtils.isPastCurrentTime(requestTimeStamp, scheduleAppointmentRequest.getServiceStartTime()))){
					      validationResponse = ResultsCode.SCHEDULE_INVALID_START_TIME;
					      scheduleAppointmentRequest.setValidationCode(validationResponse);
					      return scheduleAppointmentRequest;
					   }
					   if(null!= requestDate && null != scheduleAppointmentRequest.getServiceStartTime() && null != scheduleAppointmentRequest.getServiceEndTime()){
						   int amOrPMStartTimeLenght = scheduleAppointmentRequest.getServiceStartTime().trim().length();
						   int amOrPMEndTimeLenght = scheduleAppointmentRequest.getServiceEndTime().trim().length();
						   String amOrPMStartTime =  scheduleAppointmentRequest.getServiceStartTime().trim().substring(amOrPMStartTimeLenght - 2,amOrPMStartTimeLenght); 
						   String amOrPMEndTime   =	 scheduleAppointmentRequest.getServiceEndTime().trim().substring(amOrPMEndTimeLenght - 2,amOrPMEndTimeLenght);  
						   String  hourEndClockStr   =  scheduleAppointmentRequest.getServiceEndTime().trim().substring(0,2);
						   int hourEndClockint = Integer.parseInt(hourEndClockStr);
						   if(amOrPMStartTime.equalsIgnoreCase(amOrPMEndTime)){
							   if(TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceStartTime()) < 
								   TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceEndTime())){
									if(hourEndClockint>=12){
									   	  //Todo : Please Change the error message
										  validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
									      scheduleAppointmentRequest.setValidationCode(validationResponse);
									      return scheduleAppointmentRequest;
									}
							   }
							   
						   }
						   if(TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceStartTime()) >= 
							   TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceEndTime())){
							      //Todo : Please Change the error message
								  validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
							      scheduleAppointmentRequest.setValidationCode(validationResponse);
							      return scheduleAppointmentRequest;
						   }
						   
					   }
					   //Need to chek if both are AM
					   
					   
				}else if(null!= requestDate && null != scheduleAppointmentRequest.getServiceStartTime() && null != scheduleAppointmentRequest.getServiceEndTime()){
					int amOrPMStartTimeLenght = scheduleAppointmentRequest.getServiceStartTime().trim().length();
					   int amOrPMEndTimeLenght = scheduleAppointmentRequest.getServiceEndTime().trim().length();
					   String amOrPMStartTime =  scheduleAppointmentRequest.getServiceStartTime().trim().substring(amOrPMStartTimeLenght - 2,amOrPMStartTimeLenght); 
					   String amOrPMEndTime   =	 scheduleAppointmentRequest.getServiceEndTime().trim().substring(amOrPMEndTimeLenght - 2,amOrPMEndTimeLenght);
					   String  hourStartClockStr =  scheduleAppointmentRequest.getServiceStartTime().trim().substring(0,2);
					   String  hourEndClockStr   =  scheduleAppointmentRequest.getServiceEndTime().trim().substring(0,2);
					   int hourStartClockint = Integer.parseInt(hourStartClockStr);
					   int hourEndClockint = Integer.parseInt(hourEndClockStr);
					   if(amOrPMStartTime.equalsIgnoreCase(amOrPMEndTime)){
						   	if(TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceStartTime()) < 
							   TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceEndTime())){
								if(hourEndClockint>=12 && hourStartClockint < 12){
									  //Todo : Please Change the error message
									  validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
								      scheduleAppointmentRequest.setValidationCode(validationResponse);
								      return scheduleAppointmentRequest;
								}
						   }
						   
					   }
					if(TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceStartTime()) >= 
					   TimeUtils.convertStringTimeToMilliSeconds(scheduleAppointmentRequest.getServiceEndTime())){
						  //Todo : Please Change the error message
						  validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
					      scheduleAppointmentRequest.setValidationCode(validationResponse);
					      return scheduleAppointmentRequest;
					}
					
				}*/
				String startTime = scheduleAppointmentRequest
						.getServiceStartTime();
				String endTime = scheduleAppointmentRequest.getServiceEndTime();
				DateFormat formatter = new SimpleDateFormat("hh:mm a");
				Date startDateTime = null;
				try {
					startDateTime = (Date) formatter.parse(startTime);
				} catch (ParseException e) {
					logger.info("Exception in parsing start time");
				}
				Date endDateTime = null;
				try {
					endDateTime = (Date) formatter.parse(endTime);
				} catch (ParseException e) {
					logger.info("Exception in parsing end time");
				}
			//checking for same day 
			if(null!= requestDate && DateUtils.isSameDay(requestDate, new Date())){
				if(!(TimeUtils.isDateTimeInFuture(requestDateString, startTime, "yyyy-MM-dd","hh:mm a"))){
					  validationResponse = ResultsCode.SCHEDULE_INVALID_START_TIME;
				      scheduleAppointmentRequest.setValidationCode(validationResponse);
				      return scheduleAppointmentRequest;
				 }else{
					  if(startDateTime.equals(endDateTime)){
				 	    validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
				        scheduleAppointmentRequest.setValidationCode(validationResponse);
					    return scheduleAppointmentRequest;
						 
					 }else if(startDateTime.after(endDateTime)){
						 validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
					     scheduleAppointmentRequest.setValidationCode(validationResponse);
						 return scheduleAppointmentRequest;
						 
					 }
				 }
					
				}//Future Date
			    else{
			    	 if(startDateTime.equals(endDateTime)){
					 	    validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
					        scheduleAppointmentRequest.setValidationCode(validationResponse);
						    return scheduleAppointmentRequest;
							 
					 }else if(startDateTime.after(endDateTime)){
							 validationResponse = ResultsCode.SCHEDULE_INVALID_START_ENDTIME;
						     scheduleAppointmentRequest.setValidationCode(validationResponse);
							 return scheduleAppointmentRequest;
							 
						 }
					
				}
			
			} catch (BusinessServiceException e) {
				logger.error("Error validating Lead Id.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				scheduleAppointmentRequest.setValidationCode(validationResponse);
				return scheduleAppointmentRequest;
			}
		}
		
		scheduleAppointmentRequest.setValidationCode(validationResponse);
		return scheduleAppointmentRequest;
	}
	
	
	public AssignOrReassignProviderRequest validate(
			AssignOrReassignProviderRequest assignOrReassignProviderRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		
		Map<String,String> validateMap=new HashMap<String, String>();
		validateMap.put("firmId",assignOrReassignProviderRequest.getFirmId());
		validateMap.put("resourceId",(assignOrReassignProviderRequest.getResourceId()).toString());
		
		Map<String,String> firmLeadMap=new HashMap<String, String>();
		firmLeadMap.put("leadId",assignOrReassignProviderRequest.getLeadId());
		firmLeadMap.put("firmId",assignOrReassignProviderRequest.getFirmId());
		
		if (null != assignOrReassignProviderRequest
				&& StringUtils.isNotBlank(assignOrReassignProviderRequest
						.getLeadId())) {
			try {
				//Changes for R14_0 B2C BLM/PLM 
                //all reference to the lms_lead_id is removed
                //Instead the sl_lead_id will be used for validation
				String leadId = leadProcessingBO.validateSlleadForPost(assignOrReassignProviderRequest.getLeadId());
				//Changes for BLM/PLM--END
				if (null == leadId) {
					validationResponse = ResultsCode.INVALID_LEAD_ID;
					assignOrReassignProviderRequest.setValidationCode(validationResponse);
					return assignOrReassignProviderRequest;
				}	
				String firmId = leadProcessingBO.toValidateFirmId(assignOrReassignProviderRequest.getFirmId());
				if(null == firmId){
					validationResponse = ResultsCode.INVALID_FIRM_ID;
					assignOrReassignProviderRequest.setValidationCode(validationResponse);
					return assignOrReassignProviderRequest;
				}
				String firmForLead = leadProcessingBO.toValidateFirmIdForLead(firmLeadMap);
				if(null == firmForLead){
					validationResponse = ResultsCode.INVALID_FIRM_FOR_LEAD;
					assignOrReassignProviderRequest.setValidationCode(validationResponse);
					return assignOrReassignProviderRequest;
				}
				String resourceId = leadProcessingBO.validateResourceId(validateMap);
				if(null == resourceId){
					validationResponse = ResultsCode.INVALID_RESOURCEID;
					assignOrReassignProviderRequest.setValidationCode(validationResponse);
					return assignOrReassignProviderRequest;
				}
				
				
			} catch (BusinessServiceException e) {
				logger.error("Error validating Lead Id");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				assignOrReassignProviderRequest.setValidationCode(validationResponse);
				return assignOrReassignProviderRequest;
			}
		}
		
		assignOrReassignProviderRequest.setValidationCode(validationResponse);
		return assignOrReassignProviderRequest;
	}
    //Method added to validate request parameters for getEligibleProviders API
   public GetEligibleProviderResponse validate(String firmId, String leadId) throws BusinessServiceException {
	       GetEligibleProviderResponse response=new GetEligibleProviderResponse();
	       //Setting leadId and FirmId in response.
		   response.setLeadId(leadId);
		   response.setFirmId(Integer.parseInt(firmId));
	       ResultsCode validationResponse = ResultsCode.SUCCESS;
	       response.setValidationCode(validationResponse);
	       Map<String,String> firmLeadMap=new HashMap<String, String>();
		   firmLeadMap.put("leadId",leadId);
		   firmLeadMap.put("firmId",firmId);
		   try{
			   //Changes for R14_0 B2C BLM/PLM
               //all reference to the lms_lead_id is removed
               //Instead the sl_lead_id will be used for validation
		       // String leadIdReturned = leadProcessingBO.validateLeadId(leadId);
		        String leadIdReturned = leadProcessingBO.validateSlleadForPost(leadId);
		        //Changes for BLM/PLM --END
		        if (null == leadIdReturned) {
					validationResponse = ResultsCode.INVALID_LEAD_ID;
					response.setValidationCode(validationResponse);
					return response;
				}
			    String firmIdReturned = leadProcessingBO.toValidateFirmId(firmId);
			    if(null == firmIdReturned){
					validationResponse = ResultsCode.INVALID_FIRM_ID;
					response.setValidationCode(validationResponse);
					return response;
				}
			    String firmForLead = leadProcessingBO.toValidateFirmIdForLead(firmLeadMap);
			    if(null == firmForLead){
					validationResponse = ResultsCode.INVALID_FIRM_FOR_LEAD;
					response.setValidationCode(validationResponse);
					return response;
				}
				validationResponse = ResultsCode.SUCCESS;
				response.setValidationCode(validationResponse);
				return response;
			    
			}catch(BusinessServiceException e){
			logger.error("Error validating Lead Id and FirmId");
			response.setValidationCode(ResultsCode.UNABLE_TO_PROCESS);
			return response;
		}
		
	}

   public CompleteLeadsRequest validate(CompleteLeadsRequest completeLeadsRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
				
		Map<String,String> firmLeadMap=new HashMap<String, String>();
		firmLeadMap.put("leadId",completeLeadsRequest.getLeadId());
		firmLeadMap.put("firmId",completeLeadsRequest.getFirmId());
		firmLeadMap.put("resourceId", completeLeadsRequest.getResourceId());
		Map<String,String> firmStatusMap=new HashMap<String, String>();
		firmStatusMap.put("slleadid",completeLeadsRequest.getLeadId());
		firmStatusMap.put("firmid",completeLeadsRequest.getFirmId());
		
		
		 if (null != completeLeadsRequest
				&& StringUtils.isNotBlank(completeLeadsRequest.getLeadId())) {
			try {
			    String requestDateString = completeLeadsRequest.getCompletedDate();
				Date requestDate = null;
				String requestTime=completeLeadsRequest.getCompletedTime();
				try{
					requestDate=new SimpleDateFormat("yyyy-MM-dd").parse(requestDateString);
				}catch (Exception e) {
					// TODO: handle exception
					requestDate = new Date();
				}
				completeLeadsRequest.setCompleteDateAsDate(requestDate);
				
				
				//Changes for R14_0 B2C BLM/PLM
	            //all reference to the lms_lead_id is removed
	            //Instead the sl_lead_id will be used for validation
				// String leadId =leadProcessingBO.validateLeadId(completeLeadsRequest.getLeadId());
				String leadId = leadProcessingBO
						.validateSlleadForPost(completeLeadsRequest.getLeadId());
				// Changes for BLM/PLM --END
				String firmId = leadProcessingBO.toValidateFirmId(completeLeadsRequest.getFirmId());
				String firmForLead = leadProcessingBO.toValidateFirmIdForLead(firmLeadMap);
				String leadStatus = leadProcessingBO.validateSlLeadIdAndStatus(completeLeadsRequest.getLeadId());
				String currentLeadFirmStatus = leadProcessingBO.validateLeadFirmStatus(firmStatusMap);
				String resId = leadProcessingBO.checkProvider(firmLeadMap);	
				boolean resourceIdAssociation=leadProcessingBO.getResourceIdForFirm(firmLeadMap);
				if (StringUtils.isBlank(leadId)) {
					validationResponse = ResultsCode.INVALID_LEAD_ID;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}	
				else if(StringUtils.isBlank(leadStatus)){
					validationResponse = ResultsCode.LEAD_NOT_FOUND;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}
				else if(!(leadStatus.equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_MATCHED))){
					validationResponse = ResultsCode.LEAD_STATUS_ALREADY_COMPLETED;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}
				else if(StringUtils.isBlank(firmId)){
					validationResponse = ResultsCode.INVALID_FIRM_ID;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}

				else if (StringUtils.isBlank(currentLeadFirmStatus)){
					validationResponse = ResultsCode.UNMATCHED1;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}
				else if(!(currentLeadFirmStatus.equalsIgnoreCase(NewServiceConstants.FIRM_STATUS_SCHEDULED))){
					validationResponse = ResultsCode.FIRM_STATUS_NOT_SCHEDULED;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;				  
				}
				else if(StringUtils.isBlank (firmForLead)){
					validationResponse = ResultsCode.INVALID_FIRM_FOR_LEAD;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}
				else if(null!= requestDate && DateUtils.isSameDay(requestDate, new Date())){
					if(null!=requestTime){
					   if(TimeUtils.isDateTimeInFuture(requestDateString, requestTime, "yyyy-MM-dd", "hh:mm a")){
					      validationResponse = ResultsCode.INVALID_COMPLETED_DATETIME;
					      completeLeadsRequest.setValidationCode(validationResponse);
					      return completeLeadsRequest;
					   }
					      }
				}
				else if(StringUtils.isBlank(resId)&& StringUtils.isBlank(completeLeadsRequest.getResourceId())){
					validationResponse = ResultsCode.RESOURCE_ID_MISSING;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}else if(!resourceIdAssociation){
					validationResponse = ResultsCode.INVALID_RESOURCE_ID_FOR_FIRM;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}
				else{
					validationResponse = ResultsCode.SUCCESS;
					completeLeadsRequest.setValidationCode(validationResponse);
					return completeLeadsRequest;
				}
			
			} catch (BusinessServiceException e) {
				logger.error("Error validating Lead Id");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				completeLeadsRequest.setValidationCode(validationResponse);
				return completeLeadsRequest;
			}
		}
		
		completeLeadsRequest.setValidationCode(validationResponse);
		return completeLeadsRequest;
	}
   
   public CancelLeadRequest validate(CancelLeadRequest cancelLeadRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != cancelLeadRequest
				&& StringUtils.isNotBlank(cancelLeadRequest
						.getLeadId())) {
			try {
				String leadId = leadProcessingBO.validateSlleadForPost(cancelLeadRequest.getLeadId());
				if (null == leadId) {
					validationResponse = ResultsCode.INVALID_LEAD_ID;
					cancelLeadRequest.setValidationCode(validationResponse);
					return cancelLeadRequest;
				}
								
			} catch (BusinessServiceException e) {
				logger
						.error("Error validating Lead Id.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				cancelLeadRequest.setValidationCode(validationResponse);
				return cancelLeadRequest;
			}
		}
		
		cancelLeadRequest.setValidationCode(validationResponse);
		return cancelLeadRequest;
		
	}

    public String validateleadId(String leadId) {
    	String leadIdReturn = "";
    	try{
    		leadIdReturn = leadProcessingBO.validateSlleadForPost(leadId);
    	}catch(BusinessServiceException e){
    		logger.error("Error validating Lead Id"+ e.getMessage());
    	}
    	return leadIdReturn;
    }
    public Integer validateLeadBuyerAssociation(String leadId,Integer buyerId){
    	Integer buyerIdReturn = null;
    	try{
    		buyerIdReturn = leadProcessingBO.validateLeadBuyerAssociation(leadId,buyerId);
    	}catch(Exception e){
    		logger.error("Error validating Lead Id and buyerId Association"+ e.getMessage());
    	}
    	return buyerIdReturn;
    }

	public Integer validateBuyerId(Integer buyerId) {
		Integer buyerIdReturn = null;
    	try{
    		buyerIdReturn = leadProcessingBO.validateLeadBuyerId(buyerId);
    	}catch(Exception e){
    		logger.error("Error validating Lead Id and buyerId Association"+ e.getMessage());
    	}
    	return buyerIdReturn;
	}
	
	/************************************************B2C*****************************************/
	public com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest validate(SLLeadVO slLeadVO,com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != slLeadVO ) {
			try {
				String lmsLeadId = slLeadVO.getLmsLeadId();
				if (null == lmsLeadId) {
				    String slLeadId=slLeadVO.getSlLeadId();
				    if(null==slLeadId ){
					  validationResponse = ResultsCode.INVALID_LEAD_ID;
					  leadRequest.setValidationCode(validationResponse);
					  return leadRequest;
				    }
				}else{
					leadRequest.setLMSLeadId(lmsLeadId);
				}
				
			} catch (Exception e) {
				logger
						.error("Error validating Lead Id.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				leadRequest.setValidationCode(validationResponse);
				return leadRequest;
			}
		}else{
			validationResponse = ResultsCode.INVALID_LEAD_ID;
		}
		
		leadRequest.setValidationCode(validationResponse);
		return leadRequest;
		
	}
	
	public com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest validate(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.
			FetchProviderFirmRequest fetchProviderFirmRequest) throws BusinessServiceException {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != fetchProviderFirmRequest
				&& StringUtils.isNotBlank(fetchProviderFirmRequest
						.getCustomerZipCode())) {
			try {
				//zip validation changed as below.
				//First level validation: validate the zip against the entries in zip_geo_code, if invalid throw error and no lead to be created with Out of Area
				//Second level validation: if switch is on, validate against lead_launch_zip, if invalid, throw error and create lead for Out of Area
				
				String isValidZip = "";
				String isValidZipLaunchMarket = "";
				boolean leadLaunchSwitch = false;
				
				if((null != fetchProviderFirmRequest.getLeadNonLaunchZipSwitch())
						&& (fetchProviderFirmRequest.getLeadNonLaunchZipSwitch().booleanValue() == true)){
					leadLaunchSwitch = true;
				}
				isValidZip = leadProcessingBO.validateAllZip(fetchProviderFirmRequest
						.getCustomerZipCode());
				
				if (ZERO.equals(isValidZip)) {
					validationResponse = ResultsCode.INVALID_ZIP_CODE_NON_US;
					fetchProviderFirmRequest.setValidationCode(validationResponse);
					return fetchProviderFirmRequest;
				}
				
				if(leadLaunchSwitch){
					isValidZipLaunchMarket = leadProcessingBO.validateLaunchZip(fetchProviderFirmRequest
							.getCustomerZipCode());
					
					if(ZERO.equals(isValidZipLaunchMarket)){
						validationResponse = ResultsCode.INVALID_ZIP_CODES;
						fetchProviderFirmRequest.setValidationCode(validationResponse);
						return fetchProviderFirmRequest;
					}
				}
			} catch (BusinessServiceException e) {
				logger
						.error("Error validating customer zip code against launch markets.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				fetchProviderFirmRequest.setValidationCode(validationResponse);
				return fetchProviderFirmRequest;
			}
		}
		if (null != fetchProviderFirmRequest
				&& StringUtils.isNotBlank(fetchProviderFirmRequest
						.getPrimaryProject())) {
			try {
				fetchProviderFirmRequest = leadProcessingBO
						.validatePrimaryProject(fetchProviderFirmRequest
								.getPrimaryProject(), fetchProviderFirmRequest);
				if (StringUtils.isBlank(fetchProviderFirmRequest.getLeadCategory())) {
					validationResponse = ResultsCode.INVALID_PROJECT;
					fetchProviderFirmRequest.setValidationCode(validationResponse);
					return fetchProviderFirmRequest;
				}
			} catch (BusinessServiceException e) {
				logger.error("Error validating primary project.");
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				fetchProviderFirmRequest.setValidationCode(validationResponse);
				return fetchProviderFirmRequest;
			}
		}
		
		
			
		fetchProviderFirmRequest.setValidationCode(validationResponse);
		return fetchProviderFirmRequest;
	}
	
	public com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest validatePostLead(SLLeadVO slLeadVO, 
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) {
		ResultsCode validationResponse = ResultsCode.SUCCESS;
		if (null != slLeadVO) {
			try {
			    String slLeadId = slLeadVO.getSlLeadId();
			    if(StringUtils.isBlank(slLeadId)){
			    	validationResponse = ResultsCode.INVALID_LEAD_ID;
			    	leadRequest.setValidationCode(validationResponse);
			    	return leadRequest;
				}
			    
			    
			}catch (Exception e) {
				logger.error("Error in PostLead validation"+e);
				validationResponse = ResultsCode.UNABLE_TO_PROCESS;
				leadRequest.setValidationCode(validationResponse);
				return leadRequest;
			}
		}else{
			validationResponse = ResultsCode.INVALID_LEAD_ID;
		}
		
		leadRequest.setValidationCode(validationResponse);
		return leadRequest;
		
	}
}
