package com.newco.marketplace.mobile.api.utils.validators.v3_1;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.reportAProblem.ReportProblemVO;
import com.newco.marketplace.api.beans.so.updateTimeWindow.UpdateTimeWindowVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.getTeamMembers.ViewTeamMembersResponse;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSOResponse;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemResponse;
import com.newco.marketplace.api.mobile.beans.resolveProblem.ResolveProblemResponse;
import com.newco.marketplace.api.mobile.beans.updateSchedule.UpdateScheduleDetailsResponse;
import com.newco.marketplace.api.mobile.beans.updateTimeWindow.UpdateTimeWindowResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateScheduleDetails.UpdateScheduleVO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.sears.os.utils.DateValidationUtils;
/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/12/16
 * Request/Response Validator class for Mobile phase v3.1 API's
 */
public class NewMobileGenericValidator extends MobileGenericValidator{

	private static final Logger LOGGER = Logger.getLogger(NewMobileGenericValidator.class);

	private INewMobileGenericBO newMobileGenericBO;

	/**
	 * @Description Method to validate the request parameters and role level validation in the Report problem API.
	 * @param reportProblemVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ReportProblemResponse validateReportProblemRequest(ReportProblemVO reportProblemVO) throws BusinessServiceException {
		boolean errorOccured = false;
		Results results = null;
		ReportProblemResponse validationResponse = new ReportProblemResponse();

		//check if reason code is blank
		if(null == reportProblemVO.getProblemReasonCode() || reportProblemVO.getProblemReasonCode().equals(0)){
			results=Results.getError(ResultsCode.v3_1_INVALID_REASON_CODE.getMessage(), ResultsCode.v3_1_INVALID_REASON_CODE.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		if(!errorOccured && !validateServiceOrderStatus(reportProblemVO.getSoId(), MPConstants.REPORT_PROBLEM_v3_1)){
			results = Results.getError(
					ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS
					.getMessage(),
					ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		//Check whether role 2 resorces are able to report a problem for self assigned orders
		if(!errorOccured && MPConstants.ROLE_LEVEL_TWO.equals(reportProblemVO.getRoleId()) &&  !canRoleTwoAccess(reportProblemVO.getSoId(), reportProblemVO.getResourceId())){
			results = Results.getError(
					ResultsCode.PERMISSION_ERROR
					.getMessage(),
					ResultsCode.PERMISSION_ERROR
					.getCode());
			validationResponse.setResults(results);
		}
		return validationResponse;
	}
	/**
	 * @Description Method to validate the request parameters and role level validation in the resolve problem API.
	 * @param reportProblemVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ResolveProblemResponse validateResolveProblemRequest(ReportProblemVO reportProblemVO) throws BusinessServiceException {
		boolean errorOccured = false;
		Results results = null;
		ResolveProblemResponse validationResponse = new ResolveProblemResponse();
		if(StringUtils.isBlank(reportProblemVO.getResolutionComments())){
			results = Results.getError(
					ResultsCode.V3_1_RESOLVE_PBM_COMMENT_MANDATORY
					.getMessage(),
					ResultsCode.V3_1_RESOLVE_PBM_COMMENT_MANDATORY
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		if(!errorOccured && !validateServiceOrderStatus(reportProblemVO.getSoId(), MPConstants.RESOLVE_PROBLEM_v3_1)){
			results = Results.getError(
					ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS
					.getMessage(),
					ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		if(!errorOccured && MPConstants.ROLE_LEVEL_ONE.equals(reportProblemVO.getRoleId())){
			results = Results.getError(
					ResultsCode.PERMISSION_ERROR
					.getMessage(),
					ResultsCode.PERMISSION_ERROR
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		// check role 2 resources are able to resolve a problem for self assigned orders only
		if(!errorOccured && MPConstants.ROLE_LEVEL_TWO.equals(reportProblemVO.getRoleId()) &&  !canRoleTwoAccess(reportProblemVO.getSoId(), reportProblemVO.getResourceId())){
			results = Results.getError(
					ResultsCode.PERMISSION_ERROR
					.getMessage(),
					ResultsCode.PERMISSION_ERROR
					.getCode());
			validationResponse.setResults(results);
		}

		return validationResponse;
	}

	private boolean validateServiceOrderStatus(String soId, String action) throws BusinessServiceException{
		boolean isValidStatus = false;
		try{
			isValidStatus = newMobileGenericBO.validateServiceOrderStatus(soId, action);

		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return isValidStatus;
	}

	private boolean validateScheduleStatus(String soId, String action) throws BusinessServiceException{
		boolean isValidStatus = false;
		try{
			isValidStatus = newMobileGenericBO.validateScheduleStatus(soId, action);

		}catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return isValidStatus;
	}

	/**
	 * @Description Method to validate the request parameters in the release request and the response is set to the response object
	 * @param releaseVO
	 * @return
	 * @throws BusinessServiceException 
	 */
	public MobileReleaseSOResponse validateReleaseRequest(MobileSOReleaseVO releaseVO,MobileReleaseSOResponse releaseSOResponse) throws BusinessServiceException {
		boolean errorOccured = false;
		boolean isValidStatus = false;
		boolean isValidReleaseReason = false;
		Results results = null;
		try{
			if(null !=releaseVO){
				//check if reason code is blank
				if(StringUtils.isBlank(releaseVO.getReason()) || !isInteger(releaseVO.getReason())){
					results=Results.getError(ResultsCode.v3_1_INVALID_REASON_CODE.getMessage(), ResultsCode.v3_1_INVALID_REASON_CODE.getCode());
					releaseSOResponse.setResults(results);
					errorOccured = true;
				}
				//check if the reason is 13 or 14,comment is given in the request or not
				if(!errorOccured && (releaseVO.getReason().equals(MPConstants.RELEASE_REASON_CODE_PROVIDER)||releaseVO.getReason().equals(MPConstants.RELEASE_REASON_CODE_FIRM))
						&& StringUtils.isBlank(releaseVO.getComments())){
					results=Results.getError(ResultsCode.RELEASE_COMMENT_MANDATORY.getMessage(), ResultsCode.RELEASE_COMMENT_MANDATORY.getCode());
					releaseSOResponse.setResults(results);
					errorOccured = true;
				}
				//check the status of the service order whether it is Accepted,Active or Problem
				if(!errorOccured && StringUtils.isNotBlank(releaseVO.getSoId())){
					isValidStatus=newMobileGenericBO.validateServiceOrderStatus(releaseVO.getSoId(),MPConstants.RELEASE_SO_ACTION);
					if(!isValidStatus){
						results=Results.getError(ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS.getMessage(), ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS.getCode());
						releaseSOResponse.setResults(results);
						errorOccured = true;
					}
				}
				//R16_1 : Check whether the user has entered proper reason according to the assignment type
				if(!errorOccured && StringUtils.isNotBlank(releaseVO.getSoId())){	
					isValidReleaseReason = newMobileGenericBO.validateReleaseReasonCode(releaseVO.getSoId(),releaseVO.getReason());
					if(!isValidReleaseReason){
						results=Results.getError(ResultsCode.V3_1_INVALID_RELEASE_REASON.getMessage(), ResultsCode.V3_1_INVALID_RELEASE_REASON.getCode());
						releaseSOResponse.setResults(results);
						errorOccured = true;
					}
				}
				if(!errorOccured && validateReleaseSOPermission(releaseVO)){
					results=Results.getError(ResultsCode.PERMISSION_ERROR.getMessage(), ResultsCode.PERMISSION_ERROR.getCode());
					releaseSOResponse.setResults(results);
					errorOccured = true;
				}

			}	
		}catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return releaseSOResponse;
	}

	/**
	 * @Description Method to validate the role level permission for release service order API.Role TWO is able to release only self assigned orders
	 * @param releaseVO 
	 * @return
	 */
	private boolean validateReleaseSOPermission(MobileSOReleaseVO releaseVO) {
		boolean errorOccured = false;
		if(MPConstants.ROLE_LEVEL_ONE.equals(releaseVO.getRoleId())){
			errorOccured = true;
		}
		else if(releaseVO.getReleaseByFirmInd() && MPConstants.ROLE_LEVEL_TWO.equals(releaseVO.getRoleId())){
			errorOccured = true;
		}else if(MPConstants.ROLE_LEVEL_TWO.equals(releaseVO.getRoleId()) && !canRoleTwoAccess(releaseVO.getSoId(),releaseVO.getResourceId())){
			errorOccured = true;
		}
		return errorOccured;
	}

	/**
	 * @Description method to check the Role TWO resource is assigned with the given so 
	 * @param soId
	 * @param integer 
	 * @return
	 */
	private boolean canRoleTwoAccess(String soId, Integer resId) {
		return newMobileGenericBO.canRoleTwoAccess(soId,resId);
	}

	/**
	 * @Description Method to validate the vendor has the manage User Profile permission and  resource activity status
	 * @param apiVO 
	 * @return ViewTeamMembersResponse
	 * @throws BusinessServiceException
	 */
	public ViewTeamMembersResponse validateTeamMember(APIRequestVO apiVO, ViewTeamMembersResponse viewTeamMembersResponse) throws BusinessServiceException{
		Results results = null;
		boolean errorOccured = false;

		try{
			if(apiVO!=null){
				//Validation 1:Checking for the Manage User Profile permission
				if(!newMobileGenericBO.isManageTeamPermission(apiVO.getProviderResourceId())){
					results=Results.getError(ResultsCode.NO_MANAGE_TEAM_PERMISSION.getMessage(), ResultsCode.NO_MANAGE_TEAM_PERMISSION.getCode());
					viewTeamMembersResponse.setResults(results);
					errorOccured=true;
				}
				//Validation 2:fetching the resource activity status to check whether any resource is under this vendor
				if(!errorOccured && ! newMobileGenericBO.isResourceActive(apiVO.getProviderId())){
					results=Results.getError(ResultsCode.NO_REGISTERED_PROVIDERS.getMessage(), ResultsCode.NO_REGISTERED_PROVIDERS.getCode());
					viewTeamMembersResponse.setResults(results);
				}
			}
		}
		catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return viewTeamMembersResponse;
	}
	public UpdateTimeWindowResponse validateUpdateTimeWindowRequest(UpdateTimeWindowVO timeWindowVO, boolean isBusinessValidation) throws BusinessServiceException{
		boolean errorOccured = false;
		Results results = null;
		UpdateTimeWindowResponse validationResponse = new UpdateTimeWindowResponse();
		//Checking the role 2 providers are updating the time window of self assigned orders only
		if(!errorOccured && MPConstants.ROLE_LEVEL_TWO.equals(timeWindowVO.getRoleLevel()) && !canRoleTwoAccess(timeWindowVO.getSoId(),timeWindowVO.getResourceId())){
			results = Results.getError(
					ResultsCode.PERMISSION_ERROR
					.getMessage(),
					ResultsCode.PERMISSION_ERROR
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		if(!isBusinessValidation){
			//validate start time is not empty
			if(!errorOccured && StringUtils.isBlank(timeWindowVO.getStartTime())){
				results = Results.getError(
						ResultsCode.v3_1_INVALID_START_TIME
						.getMessage(),
						ResultsCode.v3_1_INVALID_START_TIME
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}
			//validate start time
			if(!errorOccured && StringUtils.isNotBlank(timeWindowVO.getStartTime()) && !validateTime(timeWindowVO.getStartTime())){
				results = Results.getError(
						ResultsCode.v3_1_INVALID_START_TIME
						.getMessage(),
						ResultsCode.v3_1_INVALID_START_TIME
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			//validate end time
			if(!errorOccured && StringUtils.isNotBlank(timeWindowVO.getEndTime()) && !validateTime(timeWindowVO.getEndTime())){
				results = Results.getError(
						ResultsCode.v3_1_INVALID_END_TIME
						.getMessage(),
						ResultsCode.v3_1_INVALID_END_TIME
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			//validate ETA
			if(!errorOccured && StringUtils.isNotBlank(timeWindowVO.getEta()) && !validateTime(timeWindowVO.getEta())){
				results = Results.getError(
						ResultsCode.v3_1_INVALID_ETA
						.getMessage(),
						ResultsCode.v3_1_INVALID_ETA
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			//so status should be Accepted/Active/Problem	
			if(!errorOccured && !validateServiceOrderStatus(timeWindowVO.getSoId(), MPConstants.UPDATE_TIME_WINDOW_v3_1)){
				results = Results.getError(
						ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS
						.getMessage(),
						ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

		}
		if(isBusinessValidation){
			//end time is mandatory, if date type is range
			if(!errorOccured && timeWindowVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE)&& StringUtils.isBlank(timeWindowVO.getEndTime())){
				results = Results.getError(
						ResultsCode.v3_1_END_TIME_MANDATORY
						.getMessage(),
						ResultsCode.v3_1_END_TIME_MANDATORY
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			//End time less than start time
			if (!errorOccured && timeWindowVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE)
					&& !DateValidationUtils.timeValidationFor24HrFormat(timeWindowVO.getStartTime(), timeWindowVO.getEndTime())) {
				results = Results.getError(
						ResultsCode.v3_1_START_TIME_GREATER_THAN_END_TIME
						.getMessage(),
						ResultsCode.v3_1_START_TIME_GREATER_THAN_END_TIME
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}
			//validate for min and max time window
			if(!errorOccured && timeWindowVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE) 
					&& null!= timeWindowVO.getMinTimeWindow() && null!= timeWindowVO.getMaxTimeWindow()){
				boolean isValidTimeWndw = validateTimeWndw(timeWindowVO.getStartTime(),timeWindowVO.getEndTime(),
						timeWindowVO.getMinTimeWindow(), timeWindowVO.getMaxTimeWindow());
				if(!isValidTimeWndw){
					results = Results.getError(
							ResultsCode.v3_1_TIME_WINDOW_MISMATCH
							.getMessage(),
							ResultsCode.v3_1_TIME_WINDOW_MISMATCH
							.getCode());
					validationResponse.setResults(results);
					errorOccured = true;
				}
			}
		}
		return validationResponse;
	}

	public UpdateScheduleDetailsResponse validateUpdateScheduleRequest(UpdateScheduleVO scheduleVO) throws BusinessServiceException{
		boolean errorOccured = false;
		Results results = null;
		UpdateScheduleDetailsResponse validationResponse = new UpdateScheduleDetailsResponse();
		//check role two providers are allowed to update schedule details for self assiged orders
		if(!errorOccured && MPConstants.ROLE_LEVEL_TWO.equals(scheduleVO.getRoleId()) &&  !canRoleTwoAccess(scheduleVO.getSoId(), scheduleVO.getResourceId())){
			results = Results.getError(
					ResultsCode.PERMISSION_ERROR
					.getMessage(),
					ResultsCode.PERMISSION_ERROR
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		//Common validations for PRE_CALL and CONFIRM_APPOINTMENT
		//validate ETA
		if(!errorOccured && StringUtils.isNotBlank(scheduleVO.getEtaOriginalValue()) && !validateTime(scheduleVO.getEtaOriginalValue())){
			results = Results.getError(
					ResultsCode.v3_1_INVALID_ETA
					.getMessage(),
					ResultsCode.v3_1_INVALID_ETA
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}

		//Customer Not Available Reason Code is mandatory if customer is not available
		if(!errorOccured && !scheduleVO.getCustomerAvailableFlag() 
				&& (null == scheduleVO.getCustNotAvailableReasonCode() || MPConstants.ZERO_COUNT.equals(scheduleVO.getCustNotAvailableReasonCode()))){
			results = Results.getError(
					ResultsCode.V3_1_CUSTOMER_NOT_AVAILABLE_REASON_MANDATORY
					.getMessage(),
					ResultsCode.V3_1_CUSTOMER_NOT_AVAILABLE_REASON_MANDATORY
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}

		//PRE_CALL only validations
		if(scheduleVO.getSource().equals(MPConstants.PRE_CALL)){
			//validate so status, for PRE_CALL, so status should be Accepted
			if(!errorOccured && !validateServiceOrderStatus(scheduleVO.getSoId(), MPConstants.PRE_CALL_ACTION)){
				results = Results.getError(
						ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS_PRE_CALL
						.getMessage(),
						ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS_PRE_CALL
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			// For Pre Call, schedule status should be Schedule Needed/Pre-Call Attempted
			if(!errorOccured && !validateScheduleStatus(scheduleVO.getSoId(), MPConstants.PRE_CALL_ACTION)){
				results = Results.getError(
						ResultsCode.V3_1_INVALID_SCHEDULE_STATUS
						.getMessage(),
						ResultsCode.V3_1_INVALID_SCHEDULE_STATUS
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}
		}

		//CONFIRM_APPOINTMENT only validations
		if(scheduleVO.getSource().equals(MPConstants.CONFIRM_APPOINTMENT)){
			//validate so status, for Confirm Appointment, so status should be Accepted/Active/Problem 
			if(!errorOccured && !validateServiceOrderStatus(scheduleVO.getSoId(), MPConstants.CONFIRM_APPOINTMENT_ACTION)){
				results = Results.getError(
						ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS_CONFIRM_APPT
						.getMessage(),
						ResultsCode.V3_1_INVALID_SERVICE_ORDER_STATUS_CONFIRM_APPT
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			//For Confirm Appointment, appointment date should fall in the next 3 days.
			if(!errorOccured && !validateScheduleStatus(scheduleVO.getSoId(), MPConstants.CONFIRM_APPOINTMENT_ACTION)){
				results = Results.getError(
						ResultsCode.V3_1_INVALID_SCHEDULE_STATUS
						.getMessage(),
						ResultsCode.V3_1_INVALID_SCHEDULE_STATUS
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			//For Confirm Appointment, appointment date should fall in the next 3 days.
			if(!errorOccured && !newMobileGenericBO.isAppoinmentIn3Day(scheduleVO.getSoId())){
				results = Results.getError(
						ResultsCode.V3_1_APPOINTMENT_DATE_NOT_3_DAY
						.getMessage(),
						ResultsCode.V3_1_APPOINTMENT_DATE_NOT_3_DAY
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}
		}

		//Common validations for PRE_CALL and CONFIRM_APPOINTMENT
		if(!errorOccured){
			ServiceOrder serviceOrder = newMobileGenericBO.getServiceOrderAssignmentAndScheduleDetails(scheduleVO.getSoId());
			boolean isAppointmentToday = checkIfApptIsToday(serviceOrder);
			if(isAppointmentToday){
				//If appointment date is today, a provider must be assigned.
				if(!errorOccured && null == serviceOrder.getAcceptedResourceId()){
					results = Results.getError(
							ResultsCode.V3_1_APPOINTMENT_DATE_TODAY_UNASSIGN
							.getMessage(),
							ResultsCode.V3_1_APPOINTMENT_DATE_TODAY_UNASSIGN
							.getCode());
					validationResponse.setResults(results);
					errorOccured = true;
				}
				//If appointment date is today and customer is available, then the customer response reason code is mandatory
				if(!errorOccured && scheduleVO.getCustomerAvailableFlag() && (null == scheduleVO.getCustAvailableRespCode() || MPConstants.ZERO_COUNT.equals(scheduleVO.getCustAvailableRespCode()))){
					results = Results.getError(
							ResultsCode.V3_1_CUSTOMER_RESPONSE_MANDATORY
							.getMessage(),
							ResultsCode.V3_1_CUSTOMER_RESPONSE_MANDATORY
							.getCode());
					validationResponse.setResults(results);
					errorOccured = true;
				}
			}
		}

		if(!errorOccured && scheduleVO.getCustomerAvailableFlag() 
				&& MPConstants.UPDATE_SERVICE_WINDOW.equals(scheduleVO.getCustAvailableRespCode())){
			//validate start time is not empty
			if(!errorOccured && StringUtils.isBlank(scheduleVO.getStartTimeFromRequest())){
				results = Results.getError(
						ResultsCode.v3_1_INVALID_START_TIME
						.getMessage(),
						ResultsCode.v3_1_INVALID_START_TIME
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}
			//validate start time
			if(!errorOccured && StringUtils.isNotBlank(scheduleVO.getStartTimeFromRequest()) && !validateTime(scheduleVO.getStartTimeFromRequest())){
				results = Results.getError(
						ResultsCode.v3_1_INVALID_START_TIME
						.getMessage(),
						ResultsCode.v3_1_INVALID_START_TIME
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}

			//validate end time
			if(!errorOccured && StringUtils.isNotBlank(scheduleVO.getEndTimeFromRequest()) && !validateTime(scheduleVO.getEndTimeFromRequest())){
				results = Results.getError(
						ResultsCode.v3_1_INVALID_END_TIME
						.getMessage(),
						ResultsCode.v3_1_INVALID_END_TIME
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}
		}
		return validationResponse;
	}

	public UpdateScheduleDetailsResponse validateUpdateScheduleRequestForTime(UpdateScheduleVO scheduleVO) throws BusinessServiceException{
		boolean errorOccured = false;
		Results results = null;
		UpdateScheduleDetailsResponse validationResponse = new UpdateScheduleDetailsResponse();
		//end time is mandatory, if date type is range
		if(!errorOccured && scheduleVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE)&& StringUtils.isBlank(scheduleVO.getEndTimeFromRequest())){
			results = Results.getError(
					ResultsCode.v3_1_END_TIME_MANDATORY
					.getMessage(),
					ResultsCode.v3_1_END_TIME_MANDATORY
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}

		//End time less than start time
		if (!errorOccured && scheduleVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE)
				&& !DateValidationUtils.timeValidationFor24HrFormat(scheduleVO.getStartTimeFromRequest(), scheduleVO.getEndTimeFromRequest())) {
			results = Results.getError(
					ResultsCode.v3_1_START_TIME_GREATER_THAN_END_TIME
					.getMessage(),
					ResultsCode.v3_1_START_TIME_GREATER_THAN_END_TIME
					.getCode());
			validationResponse.setResults(results);
			errorOccured = true;
		}
		//validate for min and max time window
		if(!errorOccured && scheduleVO.getServiceDateType().equals(MPConstants.SERVICE_TYPE_DATE_RANGE) 
				&& null!= scheduleVO.getMinTimeWindow() && null!= scheduleVO.getMaxTimeWindow()){
			boolean isValidTimeWndw = validateTimeWndw(scheduleVO.getStartTimeFromRequest(),scheduleVO.getEndTimeFromRequest(),
					scheduleVO.getMinTimeWindow(), scheduleVO.getMaxTimeWindow());
			if(!isValidTimeWndw){
				results = Results.getError(
						ResultsCode.v3_1_TIME_WINDOW_MISMATCH
						.getMessage(),
						ResultsCode.v3_1_TIME_WINDOW_MISMATCH
						.getCode());
				validationResponse.setResults(results);
				errorOccured = true;
			}
		}
		return validationResponse;

	}

	/**
	 * Method checks if a string value is a valid integer
	 * @param val
	 * @return boolean
	 */
	private boolean isInteger(String val){
		boolean isInteger = true;
		try{
			Integer.parseInt(val);
		}catch(NumberFormatException numEx){
			isInteger = false;
		}
		return isInteger;
	}

	private boolean validateTime(String time){
		Pattern pattern;
		Matcher matcher;
		boolean valid = true;
		String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
		matcher = pattern.matcher(time);
		valid = matcher.matches();
		return valid;		
	}


	private boolean validateTimeWndw(String startTime,String endTime,Integer minTimeWindw, Integer maxTimeWndw){
		
		@SuppressWarnings("deprecation")
		Long timeStart = new Date("01/01/2007 " + startTime).getTime();
		@SuppressWarnings("deprecation")
		Long timeEnd = new Date("01/01/2007 " + endTime).getTime();
		Long minuteDiff = (timeEnd - timeStart)/(60 * 1000);
		Double totalTimeDiff = minuteDiff.intValue()/60 + minuteDiff.doubleValue()%60/100;

		if(minTimeWindw<=totalTimeDiff && totalTimeDiff<=maxTimeWndw){
			return true;
		}else{
			return false;
		}
	}

	private boolean checkIfApptIsToday(ServiceOrder serviceOrder) throws BusinessServiceException{
		String timeZone =null;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try{
			if (serviceOrder != null) {
				GMTToGivenTimeZone(serviceOrder);
			}
			if (serviceOrder.getServiceDate1() != null) {

				timeZone = serviceOrder.getServiceLocationTimeZone();
				Calendar calendarNow = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
				Timestamp serviceDate1 = serviceOrder.getServiceDate1();
				String dateNowString = dateFormat.format(calendarNow.getTime());
				Date dateNow = dateFormat.parse(dateNowString);
				Date serviceDate = new Date(serviceDate1.getTime());
				String serviceDateString = dateFormat.format(serviceDate);
				serviceDate = dateFormat.parse(serviceDateString);
				Calendar serviceCalendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
				serviceCalendar.setTime(serviceDate);
				calendarNow.setTime(dateNow);
				if(0 == serviceCalendar.compareTo(calendarNow)){
					return true;
				}
			}
		}
		catch(Exception e){
			LOGGER.error("Error Occured Inside validateServiceOrderAssignment() for So: "+serviceOrder+" : "+e.getMessage());
		}
		return false;
	}

	/**
	 * @param serviceOrder
	 * to convert to given time zone
	 */
	private static void GMTToGivenTimeZone(ServiceOrder serviceOrder) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		String date1 = "";
		String date2 = "";
		String time1 = "";
		String time2 = "";
		DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yy");
		Date sDate1 = null;
		Date sDate2 =null;

		HashMap<String, String> formattedDates = new HashMap<String, String>();
		serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate1(), serviceOrder.getServiceTimeStart(), serviceOrder.getServiceLocationTimeZone());
		if (serviceDate1 != null && !serviceDate1.isEmpty()) {
			date1 = sdfReturn.format((serviceDate1.get(OrderConstants.GMT_DATE)));
			time1 = (String) serviceDate1.get(OrderConstants.GMT_TIME);
			formattedDates.put("date1", date1);
			formattedDates.put("time1", time1);
			try {
				sDate1 = sdfReturn.parse(date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			Timestamp serviceDateStamp1 = new Timestamp(sDate1.getTime());
			serviceOrder.setServiceDate1(serviceDateStamp1);
			serviceOrder.setServiceTimeStart(time1);
		}
		if (serviceOrder.getServiceDate2() != null && serviceOrder.getServiceTimeEnd() != null) {
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate2(), serviceOrder.getServiceTimeEnd(), serviceOrder.getServiceLocationTimeZone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				date2 = sdfReturn.format(serviceDate2.get(OrderConstants.GMT_DATE));
				time2 = (String) serviceDate2.get(OrderConstants.GMT_TIME);
				formattedDates.put("date2", date2);
				formattedDates.put("time2", time2);
				try {
					sDate2 = sdfReturn.parse(date2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Timestamp serviceDateStamp2 = new Timestamp(sDate2.getTime());
				serviceOrder.setServiceDate2(serviceDateStamp2);
				serviceOrder.setServiceTimeEnd(time2);
			}	
		}
	}

	public INewMobileGenericBO getNewMobileGenericBO() {
		return newMobileGenericBO;
	}

	public void setNewMobileGenericBO(INewMobileGenericBO newMobileGenericBO) {
		this.newMobileGenericBO = newMobileGenericBO;
	}



}
