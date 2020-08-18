package com.newco.marketplace.translator.business.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.IZipService;
import com.newco.marketplace.translator.business.ScheduleService;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.exception.InvalidZipCodeException;
import com.newco.marketplace.translator.util.DateUtil;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.translator.util.TranslatorUtils;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.LocationRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

public class ScheduleServiceOMSImpl implements ScheduleService {

	private static final Logger logger = Logger.getLogger(ScheduleServiceOMSImpl.class);
	private static final int HOUR_PAD = 2;
	private static final int DEFAULT_START_TIME = 8;
	private static final int DEFAULT_END_TIME = 17;
	private static final int SAME_DAY_START_TIME_CUTOFF=15;

	/**
	 * Schedule a service order according to the following rules
	 * @param request
	 * @param skus
	 * @return
	 * @throws ParseException
	 */
	public boolean scheduleOrder(CreateDraftRequest request, List<SkuPrice> skus, List<NoteRequest> notes,String  client) 
		throws Exception
	{
		BuyerSkuService skuService = (BuyerSkuService) SpringUtil.factory.getBean("BuyerSkuService");
		IZipService zipService = (ZipService) SpringUtil.factory.getBean("ZipService");

		LocationRequest serviceLocation = request.getServiceLocation();
		String zip = serviceLocation.getZip();
		NumberFormat nfmt = NumberFormat.getInstance();
		nfmt.setMinimumIntegerDigits(2);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z"); //XML Gregorian Cal
		SimpleDateFormat fmtTime = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat fmtTimeMilitary = new SimpleDateFormat("HH:mm:ss");

		TimeZone localTimezone = null;
		int startHour = 0;
		int startMinute=0;

		try {
			localTimezone = zipService.getTimeZoneByZip(zip);
		} catch (InvalidZipCodeException e1) {
			//Generate a note for the ServiceOrder
			//Set AutoRoute action to false
			// Generate warning mail to ProdSupport for adding the missing zipcode data in ServiceLive DB
			localTimezone = TranslatorUtils.zipCodeNotFoundActions(request, notes,client);
		}
		int offset = Math.round(localTimezone.getOffset((new Date()).getTime()) / (1000*60*60));

		Date startServiceDate = null;
		Date endServiceDate = null;
		//Promise dates are determined by a char at the beginning of the start date passed in. They have
		//different rules than delivery dates.
		boolean promiseDate = false;
		try {
			if (request.getAppointmentStartDate() != null && request.getAppointmentStartDate().length() > 0) {
				//start date has a key on it that tells if it originated from the promise date or delivery date
				if (request.getAppointmentStartDate().indexOf(TranslationService.KEY_PROMISE_DATE) == 0) {
					promiseDate = true;
				}
				//strip the keys out of the date so it can be parsed
				request.setAppointmentStartDate(request.getAppointmentStartDate().substring(1));
				startServiceDate = fmt.parse(request.getAppointmentStartDate());
			}
			else {
				//the default is today per business
				startServiceDate = new Date();
			}
		} catch (ParseException e) {
			logger.error("Error parsing start of service date", e);
			startServiceDate = new Date();
		}

		//compare to today's date if the date is today then the time goes forward 2 hours
		Date today = new Date();
		String startTime = "";
		int endHour = Math.abs(offset - DEFAULT_END_TIME);
		String endTime = nfmt.format(endHour) + ":00:00";

		// if not promiseDate( if deliveryDate) and if delivery date is not valid, set start day to today
		if(!promiseDate){
			if(!DateUtil.isExpiredDate(startServiceDate)){
				startServiceDate = new Date();
			}
		}



		if (DateUtil.isSameDay(startServiceDate, today)) {
			if (!promiseDate) {
				if (DateUtil.isHoliday(startServiceDate)) {
					startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
				}
				if (DateUtil.isSunday(startServiceDate)) {
					startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
				}
			}
			//use current time and increment the day if the hour pad dictates
			startServiceDate = DateUtil.addHoursToDate(today, Integer.valueOf(HOUR_PAD));
			startHour = Math.abs(offset - DateUtil.getHourFromDate(startServiceDate).intValue());
			startMinute = DateUtil.getMinutesFromDate(startServiceDate).intValue();
			if (startHour > 23) {
				startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
				startHour = startHour - 24;
			}
			startMinute = roundToNearestQuarterHour(startMinute);
			startTime = nfmt.format(startHour) + ":" + nfmt.format(startMinute) + ":00";
		}
		else if (promiseDate) {
			startHour = Math.abs(offset - DateUtil.getHourFromDate(startServiceDate).intValue());
			if (startHour > 23) {
				startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
				startHour = startHour - 24;
			}
			startMinute = roundToNearestQuarterHour(DateUtil.getMinutesFromDate(startServiceDate).intValue());
			startTime = nfmt.format(startHour) + ":" + nfmt.format(startMinute) + ":00";
		}
		else {
			//a default start time should be used
			startHour = Math.abs(offset - DEFAULT_START_TIME);
			startTime = nfmt.format(startHour) + ":00:00";

		}

		//determine end date based on lead time from skus and Sundays, Holidays
		endServiceDate = skuService.getServiceEndDate(skus, startServiceDate);
		Map<String, Date> dates = DateUtil.getDatesAfterExcludedDays(startServiceDate, endServiceDate, promiseDate);
		startServiceDate = dates.get("start");
		if( ! promiseDate )
		{
			endServiceDate = dates.get("end");
		}
		else
		{
			endServiceDate = startServiceDate;
		}

		//convert military to AM/PM and set in request
		
		Date tendTime = null;
		if (promiseDate) {
			int tendHour = Math.abs(offset - DateUtil.getHourFromDate(fmt.parse(request.getAppointmentEndDate())).intValue());
			if (tendHour > 23) {
				endServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
				tendHour = tendHour - 24;
			}
			int endMinute = roundToNearestQuarterHour(DateUtil.getMinutesFromDate(fmt.parse(request.getAppointmentEndDate())).intValue());
			endTime = nfmt.format(tendHour) + ":" + nfmt.format(endMinute) + ":00";
		}
		
		//For Promised date if AppointmentStart/End date is today and if start time is =>3:15PM ,Add another day and set default start and end time.
		int startHourWithoutOffset=  Math.abs(offset+startHour);
		if (promiseDate && DateUtil.isSameDay(fmt.parse(request.getAppointmentStartDate()), today) 
				&& DateUtil.isSameDay(fmt.parse(request.getAppointmentStartDate()), 
						fmt.parse(request.getAppointmentEndDate()))) {
			if(startHourWithoutOffset>SAME_DAY_START_TIME_CUTOFF ||(startHourWithoutOffset==SAME_DAY_START_TIME_CUTOFF&&startMinute>=SAME_DAY_START_TIME_CUTOFF)){
			startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
			endServiceDate=startServiceDate;
			startHour = Math.abs(offset - DEFAULT_START_TIME);
			startTime = nfmt.format(startHour) + ":00:00";
			endHour = Math.abs(offset - DEFAULT_END_TIME);
			endTime = nfmt.format(endHour) + ":00:00";
			}
		}
		Date tstartTime = fmtTimeMilitary.parse(startTime);
		request.setAppointmentStartTime(fmtTime.format(tstartTime));

		tendTime = fmtTimeMilitary.parse(endTime);

		request.setAppointmentEndTime(fmtTime.format(tendTime));

		request.setAppointmentStartDate(fmt.format(DateUtil.getDateMidnight(startServiceDate)));
		request.setAppointmentEndDate(fmt.format(DateUtil.getDateMidnight(endServiceDate)));

		return true;
	}

	public int roundToNearestQuarterHour(int in) {
		int out = 0;
		if (in < 15) {
			out = 0;
		}
		else if (in < 30) {
			out = 15;
		}
		else if (in < 45) {
			out = 30;
		}
		else {
			out = 45;
		}
		return out;
	}
}
