package com.newco.marketplace.api.mobile.services;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.updateApptTime.SOUpdateAppointmentTimeRequest;
import com.newco.marketplace.api.mobile.beans.updateApptTime.SOUpdateAppointmentTimeResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.business.iBusiness.mobile.IManageScheduleBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.util.TimestampUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
/**
 * This class would act as a Service Edit Appointment Time 
 * 
 * @author Infosys
 * @version 1.0
 */
@APIRequestClass(SOUpdateAppointmentTimeRequest.class)
@APIResponseClass(SOUpdateAppointmentTimeResponse.class)
public class SOUpdateAppointmentTimeService extends BaseService {
	private Logger logger = Logger.getLogger(SOUpdateAppointmentTimeService.class);

	private IManageScheduleBO manageScheduleBO;
	private IMobileSOActionsBO mobileSOActionsBO;
	private IAuthenticateUserBO authenticateUserBO;
	private IMobileGenericBO mobileGenericBO;
	private IRelayServiceNotification relayNotificationService;


	ProcessResponse processResponse= new ProcessResponse();

	public SOUpdateAppointmentTimeService() {
		super();
	}



	@Override
	public IAPIResponse execute(APIRequestVO apiVO){ 
		logger.info("Inside SOEditAppointmentTimeService.execute()");
		SecurityContext securityContext = null;
		Integer providerId=0;
		int confirmedInd = 0;
		int scheduleStatusId = 0;
		int responseReasonId = 0;
		SOUpdateAppointmentTimeResponse editAppointmentTimeResponse = new SOUpdateAppointmentTimeResponse();
		SOUpdateAppointmentTimeRequest editAppointmentTimeRequest = (SOUpdateAppointmentTimeRequest) apiVO.getRequestFromPostPut();
		providerId = apiVO.getProviderResourceId();	
		String soId = (String) apiVO.getSOId();
		
		//SL-21580: Code change starts
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Code change ends
		
		Integer resultCode = 0;
		try {
			securityContext = getSecurityContextForVendor(providerId);			
			
			String userName="";
			Integer entityId= 0;
			Integer roleId=0;
			String createdBy="";
			String historyDesc = "";

			if(securityContext!=null){
				userName = securityContext.getUsername();
				entityId = securityContext.getVendBuyerResId();
				roleId = securityContext.getRoleId();
				LoginCredentialVO lvRoles = securityContext.getRoles();
				createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
			}
			
			
			// 1. Validate the provider
			Integer proId = validateProviderId(providerId.toString());
			
			logger.info("proId::"+proId);
			
			if(null == proId){
				Results results= Results.getError(ResultsCode.INVALID_RESOURCE_ID.getMessage(),ResultsCode.INVALID_RESOURCE_ID.getCode());
				editAppointmentTimeResponse.setResults(results);
				return editAppointmentTimeResponse;
			}
			
			// 2.Validate the service order id
			boolean serviceOrderId = isValidServiceOrder(soId);
			
			if(!serviceOrderId){
				Results results = Results.getError(ResultsCode.INVALID_SO_ID.getMessage(),ResultsCode.INVALID_SO_ID.getCode());
				editAppointmentTimeResponse.setResults(results);
				return editAppointmentTimeResponse;
			}
			
			// 3. validate the SO status
			UpdateApptTimeVO scheduleVO = new UpdateApptTimeVO();
			scheduleVO = manageScheduleBO.fetchServiceDatesAndTimeWndw(soId);
			Integer status = scheduleVO.getWfStateId();
			if(null != status && status.intValue() != 150 && 
					status.intValue() != 155 && status.intValue() != 170){
				Results results = Results.getError(
						ResultsCode.INVALID_STATE
								.getMessage(),
						ResultsCode.INVALID_STATE
								.getCode());
				editAppointmentTimeResponse.setResults(results);
				return editAppointmentTimeResponse;
			}
			
			// 4. Validate the so-provider relation
			Results result = validateProviderForSO(soId,providerId,proId);
			
			if(null != result){
				editAppointmentTimeResponse.setResults(result);	
				return editAppointmentTimeResponse;
			}
			
			Integer dateType = scheduleVO.getServiceDateType();
			if (null != providerId && null != soId) {
				if(editAppointmentTimeRequest!=null){
					if (StringUtils.isBlank(editAppointmentTimeRequest.getStartTime()) || (dateType.equals(2) && StringUtils.isBlank(editAppointmentTimeRequest.getEndTime()))){
										
						Results results = Results.getError(
								ResultsCode.INVALID_TIME
										.getMessage(),
								ResultsCode.INVALID_TIME
										.getCode());
						editAppointmentTimeResponse.setResults(results);
						return editAppointmentTimeResponse;
					}else if(StringUtils.isNotBlank(editAppointmentTimeRequest.getStartTime()) || StringUtils.isNotBlank(editAppointmentTimeRequest.getEndTime())){
						boolean validTime = validateTime(editAppointmentTimeRequest.getStartTime(),editAppointmentTimeRequest.getEndTime());
						if(!validTime){
							Results results = Results.getError(
									ResultsCode.INVALID_TIME
											.getMessage(),
									ResultsCode.INVALID_TIME
											.getCode());
							editAppointmentTimeResponse.setResults(results);
							return editAppointmentTimeResponse;
						}
					}
					String endTimeOrginal="";
					String startTimeOrginal="";
					
					String endTime="";
					String startTime = convertTimeFormat(editAppointmentTimeRequest.getStartTime());
					if(dateType.equals(2)){//if date type is range
					    endTime = convertTimeFormat(editAppointmentTimeRequest.getEndTime());
					}else{
						endTime = null;
					}
					String eta = null;
					
					if(StringUtils.isNotBlank(editAppointmentTimeRequest.getEta()) && dateType.equals(2)){
						boolean isValidETA = validateTime(editAppointmentTimeRequest.getEta(),null);//check if eta format is correct
						if(isValidETA){
							eta = convertTimeFormat(editAppointmentTimeRequest.getEta()); 
						}else{
							Results results = Results.getError(
									ResultsCode.INVALID_TIME
											.getMessage(),
									ResultsCode.INVALID_TIME
											.getCode());
							editAppointmentTimeResponse.setResults(results);
							return editAppointmentTimeResponse;
						}
					}
					String customerConfirmedInd = editAppointmentTimeRequest.getCustomerConfirmedInd();
					Integer custNotAvailReason = editAppointmentTimeRequest.getCustNotAvailReason();
					if(null!=customerConfirmedInd && (customerConfirmedInd.equals("true")||customerConfirmedInd.equals("false"))){
						if(customerConfirmedInd.equals("true")){
							confirmedInd = 1;
							scheduleStatusId = MPConstants.TIME_WINDOW_CALL_COMPLETED; // Time window-call completed
						}else if(customerConfirmedInd.equalsIgnoreCase("false")){						
							confirmedInd = 0;
							scheduleStatusId = MPConstants.TIME_WINDOW_CALL_ATTEMPTED; // Time window-call completed
							
							// COMMENTING SINCE MOBILE APP TEAM IS NOT MAKING THIS CHANGE
							/*// Check if the reason code is available
							if((null==custNotAvailReason) || (null!=custNotAvailReason && (custNotAvailReason.intValue() <= 0  
									|| custNotAvailReason.intValue() > 4))){
								Results results = Results.getError(
										ResultsCode.INVALID_CUST_NA_REASON_CODE
												.getMessage(),
										ResultsCode.INVALID_CUST_NA_REASON_CODE
												.getCode());
								editAppointmentTimeResponse.setResults(results);
								return editAppointmentTimeResponse;
							}else{
								 switch (custNotAvailReason) {
									 case 1:scheduleStatusId = MPConstants.TIME_WINDOW_CALL_ATTEMPTED; // Time window call attempted
									 		responseReasonId = MPConstants.CUST_PARTIAL_CONTACT; // partial contact
									 		break;
									 case 2 :scheduleStatusId = MPConstants.TIME_WINDOW_CALL_ATTEMPTED; // Time window call attempted
									 		responseReasonId = MPConstants.CUST_INVALID_NUMBER;// invalid number
									 		break;
									 case 3 :scheduleStatusId = MPConstants.TIME_WINDOW_CALL_ATTEMPTED; // Time window call attempted
									 		responseReasonId = MPConstants.CUST_LEFT_MESSAGE;//left message
										 	break;
									 case 4 :scheduleStatusId = 0; // No call made - No schedule update required
									 		 responseReasonId = 0; //
										 	 break;
									  default : logger.info("Invalid custNotAvailReason");
										 	break;
									 }
								}*/
						}
					}else if(null!=customerConfirmedInd && !customerConfirmedInd.trim().equals("")){
						Results results = Results.getError(
								ResultsCode.INVALID_CUST_CONFIRMED
										.getMessage(),
								ResultsCode.INVALID_CUST_CONFIRMED
										.getCode());
						editAppointmentTimeResponse.setResults(results);
						return editAppointmentTimeResponse;
					}
				
					//validate for min and max time window
					if(dateType.equals(2) && null!=scheduleVO && null!= scheduleVO.getMinTimeWindow() && null!= scheduleVO.getMaxTimeWindow()){
					boolean isValidTimeWndw = validateTimeWndw(editAppointmentTimeRequest.getStartTime(),editAppointmentTimeRequest.getEndTime(),scheduleVO);
					if(!isValidTimeWndw){
						Results results = Results.getError(
								ResultsCode.TIME_WINDOW_MISMATCH
										.getMessage(),
								ResultsCode.TIME_WINDOW_MISMATCH
										.getCode());
						editAppointmentTimeResponse.setResults(results);
						return editAppointmentTimeResponse;
					}
					}
					String startDate =scheduleVO.getStartDate().substring(0, scheduleVO.getStartDate().indexOf(" "));
					HashMap<String, Object> dateTimeMap;
					String timeZone = scheduleVO.getZone();
					// get the service time on service order Timezone.
					if (StringUtils.isNotBlank(startTime) && null != startDate) {
						//String startTimeForDate = startTime;
						dateTimeMap = new HashMap<String, Object>();
						Timestamp startAppDate = TimestampUtils.getTimestampFromString(startDate, MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
						startAppDate = (Timestamp) TimeUtils.convertGMTToGivenTimeZone(startAppDate, scheduleVO.getServiceTimeStart(), timeZone).get(MPConstants.DATE_PARAMETER);
						dateTimeMap = TimeUtils.convertToGMT(startAppDate, startTime, timeZone);
						startTimeOrginal=startTime;
						
						startTime = dateTimeMap.get(MPConstants.TIME_PARAMETER).toString();
						startDate = dateTimeMap.get(MPConstants.DATE_PARAMETER).toString();
						
						//SL 18896 R8.2
						//pass the startDate parameter also with the request, along with the startTime
						//need to check whether date goes to the next day, hence appending time+12 to date for PM
						//startAppDate = TimestampUtils.
						//		getTimestampFromString( getDateAndTimeAppended(startDate,startTimeForDate), MPConstants.DATE_FORMAT_APPENDED_WITH_TIME);
						
						//startDate = TimeUtils.convertToGMT(startAppDate, startTimeForDate, timeZone).get(MPConstants.DATE_PARAMETER).toString();
						logger.info("startTime:"+startTime+" startDate:"+startDate);
					}
					String endDate = "";
					if(dateType.equals(2)){
						endDate =scheduleVO.getEndDate().substring(0, scheduleVO.getEndDate().indexOf(" "));
						if (StringUtils.isNotBlank(endTime) && null != endDate) {
							//String endTimeForDate = endTime;
							dateTimeMap = new HashMap<String, Object>();
							Timestamp endAppDate = TimestampUtils.getTimestampFromString(endDate, MPConstants.DATE_FORMAT_DISPLAYED_FOR_ACCEPT);
							endAppDate = (Timestamp) TimeUtils.convertGMTToGivenTimeZone(endAppDate, scheduleVO.getServiceTimeEnd(), timeZone).get(MPConstants.DATE_PARAMETER);
							dateTimeMap = TimeUtils.convertToGMT(endAppDate, endTime, timeZone);
							
							endTimeOrginal=endTime;
							endTime = dateTimeMap.get(MPConstants.TIME_PARAMETER).toString();
							endDate = dateTimeMap.get(MPConstants.DATE_PARAMETER).toString();
							//SL 18896 R8.2
							//pass the endDate parameter also with the request, along with the endTime
							//endAppDate = TimestampUtils.
							//			getTimestampFromString(getDateAndTimeAppended(endDate,endTimeForDate), MPConstants.DATE_FORMAT_APPENDED_WITH_TIME);
							
							//endDate = TimeUtils.convertToGMT(endAppDate, endTimeForDate, timeZone).get(MPConstants.DATE_PARAMETER).toString();
							logger.info("endTime:"+endTime+" endDate:"+endDate);
						}
					}
					scheduleVO = new UpdateApptTimeVO();
					scheduleVO.setSource("CONFIRM_APPOINTMENT"); // SPM-1341 :Changed from UPDATE_TIME to CONFIRM_APPOINTMENT 
					scheduleVO.setServiceTimeStart(startTime);
					scheduleVO.setStartDate(startDate);
					if(StringUtils.isNotBlank(endTime)){
						scheduleVO.setServiceTimeEnd(endTime);
						scheduleVO.setEndDate(endDate);
					}
					else{
						scheduleVO.setServiceTimeEnd(null);
						scheduleVO.setEndDate(null);
					}
					scheduleVO.setEta(eta);
					scheduleVO.setSoId(soId);
					scheduleVO.setProviderId(securityContext.getCompanyId());
					scheduleVO.setCreatedDate(Timestamp.valueOf(format.format(new Date())));
					scheduleVO.setModifiedDate(Timestamp.valueOf(format.format(new Date())));
					scheduleVO.setModifiedByName(securityContext.getUsername());
					scheduleVO.setCustomerConfirmedInd(confirmedInd);
					if(0 != scheduleStatusId) {
						scheduleVO.setScheduleStatusId(scheduleStatusId); 
					}
					
					if(0 != responseReasonId) {
						scheduleVO.setReasonId(responseReasonId); 
					}
					resultCode = manageScheduleBO.editSOAppointmentTime(scheduleVO);
					
					try{
						Integer buyerId=relayNotificationService.getBuyerId(soId); 
						boolean relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(buyerId,soId); 
						if(relayServicesNotifyFlag){
					    relayNotificationService.sentNotificationRelayServices("update_time_by_provider", soId); 
						}
						}
						catch(Exception e){
						logger.error(" exception in invoking webhook event for update time window"+e);	
						}
					
						if(StringUtils.isNotBlank(startTimeOrginal)){
							historyDesc = MPConstants.UPDATE_TIME_ACTION_DESC +
										  MPConstants.WHITE_SPACE+startTimeOrginal;
							
						}
						if(StringUtils.isBlank(endTimeOrginal)){
							historyDesc  = historyDesc +MPConstants.DOT;
						}else if(StringUtils.isNotBlank(endTimeOrginal)){
							historyDesc = historyDesc + MPConstants.WHITE_SPACE+
										  MPConstants.HYPHEN+MPConstants.WHITE_SPACE+
										  endTimeOrginal+MPConstants.DOT;
						}
						if(StringUtils.isNotBlank(historyDesc)){
							if(4==scheduleStatusId){
								historyDesc =  MPConstants.WHITE_SPACE+ historyDesc+MPConstants.UPDATE_TIME_ACTION_DESC_STATUS+
											   MPConstants.TIME_WINDOW_CALL_ATTEMPTED_DESC;
							}else if (5==scheduleStatusId){
								historyDesc =  MPConstants.WHITE_SPACE+ historyDesc+MPConstants.UPDATE_TIME_ACTION_DESC_STATUS+ 
											   MPConstants.TIME_WINDOW_CALL_COMPLETED_DESC;
							}
						}
					}
				}
			if(resultCode.equals(1)){
				
				// Update the history - new action
				historyLogging(roleId, soId, userName,
						entityId,historyDesc,createdBy);
				
				Results results = Results.getSuccess(ResultsCode.UPDATE_APPT_TIME_SUCCESS.getMessage());
				editAppointmentTimeResponse.setResults(results);
				editAppointmentTimeResponse.setSoId(soId);
				return editAppointmentTimeResponse;
			}else{
				Results results = Results.getError(
						ResultsCode.UPDATE_APPT_TIME_FAILURE
								.getMessage(),
						ResultsCode.UPDATE_APPT_TIME_FAILURE
								.getCode());
				editAppointmentTimeResponse.setResults(results);
				return editAppointmentTimeResponse;
			}
			//editAppointmentTimeResponse = manageScheduleMapper.mapEditAppointmentTimeResponse(resultCode);
		}
		catch (Exception exception) {
			logger.error("SOEditAppointmentTimeService.execute(): exception due to "+exception);
		}
		logger.info("Leaving SOEditAppointmentTimeService.execute()");

		return editAppointmentTimeResponse;
	}
	
	/**
	 * @param roleId
	 * @param soId
	 * @param userName
	 * @param entityId
	 * @param desc
	 * @param createdBy
	 */
	private void historyLogging(Integer roleId, String soId, String userName,
			Integer entityId,String desc,String createdBy) {
		// TODO Auto-generated method stub
		try{
			Date d=new Date(System.currentTimeMillis());
			Timestamp date = new Timestamp(d.getTime());
			ProviderHistoryVO hisVO=new ProviderHistoryVO();
			hisVO.setSoId(soId);
			hisVO.setActionId(MPConstants.UPDATE_TIME_ACTION_ID);
			hisVO.setDescription(desc);
			hisVO.setCreatedDate(date);
			hisVO.setModifiedDate(date);
			hisVO.setCreatedBy(createdBy);
			hisVO.setRoleId(roleId);
			hisVO.setModifiedBy(userName);
			hisVO.setEnitityId(entityId);
			mobileSOActionsBO.historyLogging(hisVO);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileTimeOnSiteService-->historyLogging()");

		}
		
		
	}
	
	@SuppressWarnings("deprecation")
	private boolean validateTimeWndw(String startTime,String endTime,UpdateApptTimeVO scheduleVO){
		
		
		Integer minTimeWindw = scheduleVO.getMinTimeWindow();
		Integer maxTimeWndw = scheduleVO.getMaxTimeWindow();
		
		Long timeStart = new Date("01/01/2007 " + startTime).getTime();
		Long timeEnd = new Date("01/01/2007 " + endTime).getTime();
		Long minuteDiff = (timeEnd - timeStart)/(60 * 1000);
		Double totalTimeDiff = minuteDiff.intValue()/60 + minuteDiff.doubleValue()%60/100;

		if(minTimeWindw<=totalTimeDiff && totalTimeDiff<=maxTimeWndw){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean validateETA(String startTime,String endTime,String eta){
		SimpleDateFormat parser = new SimpleDateFormat("hh:mm a");
		try{
		Date ten = parser.parse(startTime);
		Date eighteen = parser.parse(endTime);
		Date userDate = parser.parse(eta);
		if (userDate.after(ten) && userDate.before(eighteen)) {
			return true;
	    }else{
	    	return false;
	    }
		}catch(ParseException e){
			return false;
		}
	}
	
	private String getDateAndTimeAppended(String date, String time){
		if(StringUtils.isNotBlank(date) && StringUtils.isNotBlank(time)){
			//convert 12 hr format to 24 hr format
			DateFormat f1 = new SimpleDateFormat("hh:mm a");
			Date d = null;
			try {
				d = f1.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			DateFormat f2 = new SimpleDateFormat("HH:mm");
			String convertedHour = f2.format(d).toLowerCase();
			
			return date+ " "+convertedHour+":00";
		}
		return null;
	}

	//convert 24 hr format to 12 hr format
	private String convertTimeFormat(String time){

		DateFormat f1 = new SimpleDateFormat("HH:mm:ss");
		Date d = null;
		try {
			d = f1.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat f2 = new SimpleDateFormat("hh:mm a");
		return f2.format(d).toUpperCase(); 
	}
	
	private boolean validateTime(String time1, String time2){
		Pattern pattern;
		Matcher matcher;
		boolean valid = true;
		String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		//validate start time
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
		matcher = pattern.matcher(time1);
		valid = matcher.matches();
		//validate end time
		if(valid && StringUtils.isNotBlank(time2)){
			matcher = pattern.matcher(time2);
			valid = matcher.matches();
		}
		  return valid;		
	}
	
	/**
	 * @param soId
	 * @param providerId
	 * @param firmId
	 * @return
	 * method to validate provider permission based on role
	 */
	private Results validateProviderForSO(String soId, Integer providerId, Integer firmId) {
		
		 
		Results result = null;
		try{
			Integer resourceRoleLevel = authenticateUserBO.getRoleOfResource(null, providerId);
			if(null!=firmId && null !=providerId){
				SoDetailsVO detailsVO = new SoDetailsVO();
			 detailsVO.setSoId(soId);
			 detailsVO.setFirmId(firmId.toString());
			 detailsVO.setProviderId(providerId);
			 detailsVO.setRoleId(resourceRoleLevel);
			 
			 logger.info("resourceRoleLevel::"+resourceRoleLevel);
			 boolean authSuccess = mobileGenericBO.isAuthorizedToViewBeyondPosted(detailsVO);
			 logger.info("authSuccess::"+authSuccess);
			 if(!authSuccess){
				 result = Results.getError(ResultsCode.PERMISSION_ERROR.getMessage(),
							ResultsCode.PERMISSION_ERROR.getCode());	
			 }
			}
			List<Integer> roleIdValues = Arrays.asList(
					PublicMobileAPIConstant.ROLE_LEVEL_ONE,
					PublicMobileAPIConstant.ROLE_LEVEL_TWO,
					PublicMobileAPIConstant.ROLE_LEVEL_THREE);
			if (null == resourceRoleLevel || !roleIdValues.contains(resourceRoleLevel)) {
				result = Results.getError(ResultsCode.INVALID_ROLE.getMessage(),ResultsCode.INVALID_ROLE.getCode());
			}
		}
		catch(Exception e){
			logger.error("Exception inside validateProviderForSO inside update time");
			e.printStackTrace();
		}
		
		
		return result;
	}

	public IManageScheduleBO getManageScheduleBO() {
		return manageScheduleBO;
	}


	public void setManageScheduleBO(IManageScheduleBO manageScheduleBO) {
		this.manageScheduleBO = manageScheduleBO;
	}

	
	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}



	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}



	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
	}
	

	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}


	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

	

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}


	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}



	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}



	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
	

}