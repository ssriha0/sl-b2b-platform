package com.newco.marketplace.mobile.api.utils.validators.v2_0;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.v2_0.SORevisitNeededRequest;
import com.newco.marketplace.api.mobile.beans.v2_0.SORevisitNeededResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;

/**
 * The class acts as the entry point for the validation done for 
 * SO Revisit Needed
 * 
 */
public class SORevisitNeededValidator{
	
	private Logger logger = Logger.getLogger(SORevisitNeededValidator.class);
	private IMobileSOActionsBO mobileSOActionsBO;
	private String timeZone;

	public SORevisitNeededResponse validateRequest(
			SORevisitNeededRequest revisitNeededRequest, String soId) {
		SORevisitNeededResponse revisitNeededResponse = null;
		// Validate trip number
		try {
			// Make sure that the trip number is associated with the given so id.
			if(revisitNeededRequest.getTripNumber().equals(0) || 
					null == mobileSOActionsBO.validateLatestOpenTrip(revisitNeededRequest.getTripNumber(), soId)){
				revisitNeededResponse = SORevisitNeededResponse
						.getInstanceForError(
								ResultsCode.REVISIT_NEEDED_TRIP, null);
			}
		} catch (BusinessServiceException e) {
			logger.error("Exception while validating trip No :"+e);
			// set the error code as internal server error
			revisitNeededResponse = SORevisitNeededResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR,
							soId);
		}
		// to check for time window mismatch
		try {
			if(!MPConstants.CUSTOMER_NOT_HOME.equals(revisitNeededRequest.getRevisitReason()))
			{
				UpdateApptTimeVO timeWindowVO = mobileSOActionsBO.getBuyerMinAndMaxTimeWindow(soId);
				if(null!=timeWindowVO && StringUtils.isNotEmpty(revisitNeededRequest.getNextApptStartTime()) && StringUtils.isNotEmpty(revisitNeededRequest.getNextApptEndTime()) && !validateTimeWindow(revisitNeededRequest.getNextApptStartTime(), revisitNeededRequest.getNextApptEndTime(), timeWindowVO)){
					revisitNeededResponse = SORevisitNeededResponse
							.getInstanceForError(
									ResultsCode.TIME_WINDOW_MISMATCH, soId);
				}
			}
		}
		catch (BusinessServiceException e) {
			logger.error("Exception while getBuyerMinAndMaxTimeWindow :"+e);
			// set the error code as internal server error
			revisitNeededResponse = SORevisitNeededResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR,
							soId);
		}
		
		if(!MPConstants.CUSTOMER_NOT_HOME.equals(revisitNeededRequest.getRevisitReason())){
			// Date format validation for next appointment date and time
			Date currentDate = fetchDateInGivenTimeZone(timeZone);
			Date nextApptStartDate = null;
			Date nextApptEndDate = null;
			DateFormat formatter = new SimpleDateFormat(MPConstants.DATE_FORMAT_VALIDATION);
			try {
				nextApptStartDate = formatter.parse(revisitNeededRequest.getNextApptDate() + " " + revisitNeededRequest.getNextApptStartTime());
				nextApptEndDate = formatter.parse(revisitNeededRequest.getNextApptDate() + " " + revisitNeededRequest.getNextApptEndTime());
			} catch (ParseException e) {
				revisitNeededResponse = SORevisitNeededResponse
						.getInstanceForError(
								ResultsCode.REVISIT_NEEDED_APPT_DATE_TIME, null);
				return revisitNeededResponse;
			}
			// Validate whether the next appointment date and start time is not past date time. 
			if(nextApptStartDate.compareTo(currentDate) < 0){
				revisitNeededResponse = SORevisitNeededResponse
						.getInstanceForError(
								ResultsCode.REVISIT_NEEDED_APPT_DATE_TIME_PAST, null);
			}
			// Validate whether the next appointment date and end time is before start date time.
			if(nextApptEndDate.compareTo(nextApptStartDate) <= 0){
				revisitNeededResponse = SORevisitNeededResponse
						.getInstanceForError(
								ResultsCode.REVISIT_NEEDED_APPT_DATE_TIME_PAST, null);
			}
		}
		// Validation for reason code. Not required
		logger.info("Revisit Needed Validator : No errors");
		return revisitNeededResponse;
	}
	
	public Date fetchDateInGivenTimeZone(String timeZone){
		Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone(timeZone);

        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }
        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        return calendar.getTime();
	}
	
	/**
	 * @param startTime
	 * @param endTime
	 * @param scheduleVO
	 * @return to validate the service window business rules
	 */
	@SuppressWarnings("deprecation")
	private boolean validateTimeWindow(String startTime,String endTime,UpdateApptTimeVO scheduleVO){
		
		
		Integer minTimeWindw = scheduleVO.getMinTimeWindow();
		Integer maxTimeWndw = scheduleVO.getMaxTimeWindow();
		if(null!=minTimeWindw && null!=maxTimeWndw){
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
		return true;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
}
