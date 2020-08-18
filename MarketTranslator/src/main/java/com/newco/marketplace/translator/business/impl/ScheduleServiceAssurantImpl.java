package com.newco.marketplace.translator.business.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.newco.marketplace.translator.business.ScheduleService;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.exception.InvalidZipCodeException;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.DateUtil;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.translator.util.TranslatorUtils;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;

public class ScheduleServiceAssurantImpl implements ScheduleService {

	private static final int DEFAULT_START_TIME = 8;
	private static final int DEFAULT_END_TIME = 17;
	private static final int DEFAULT_DAY_PAD = 3;

	public boolean scheduleOrder(CreateDraftRequest request, List<SkuPrice> skus, List<NoteRequest> notes,String client) 
		throws ParseException, InvalidZipCodeException 
	{
		ZipService zipService = (ZipService) SpringUtil.factory.getBean("ZipService");
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		SimpleDateFormat fmtTime = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat fmtTimeMilitary = new SimpleDateFormat("HH:mm:ss");
		
		NumberFormat nfmt = NumberFormat.getInstance();
		nfmt.setMinimumIntegerDigits(2);
		
		String zip = request.getServiceLocation().getZip();
		TimeZone localTimezone = null;
		try
		{
			localTimezone = zipService.getTimeZoneByZip(zip);
		}
		catch(InvalidZipCodeException e)
		{
			localTimezone = TranslatorUtils.zipCodeNotFoundActions(request,notes,client);
		}
		int offset = Math.round(localTimezone.getOffset((new Date()).getTime()) / (1000*60*60));
		
		Date startServiceDate = DateUtil.getDateMidnight();
		startServiceDate = DateUtil.addDaysToDate(startServiceDate, new Integer(DEFAULT_DAY_PAD));
		String startTime = "";
		int endHour = Math.abs(offset - DEFAULT_END_TIME);
		String endTime = nfmt.format(endHour) + ":00:00"; 
		
		if (DateUtil.isHoliday(startServiceDate)) {
			startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
		}
		if (DateUtil.isSunday(startServiceDate)) {
			startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
		}

		//use current time and increment the day if the hour pad dictates 
		Date serviceTimeStart = DateUtil.addHoursToDate(startServiceDate, Integer.valueOf(DEFAULT_START_TIME));
		int startHour = Math.abs(offset - DateUtil.getHourFromDate(serviceTimeStart).intValue());
		int startMinute = DateUtil.getMinutesFromDate(serviceTimeStart).intValue();
		if (startHour > 23) {
			startServiceDate = DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(1));
			startHour = startHour - 24;
		}
		startTime = nfmt.format(startHour) + ":" + nfmt.format(startMinute) + ":00";
		request.setAppointmentStartDate(fmt.format(startServiceDate));
		request.setAppointmentEndDate(fmt.format(DateUtil.addDaysToDate(startServiceDate, Integer.valueOf(DEFAULT_DAY_PAD))));
		Date tstartTime = fmtTimeMilitary.parse(startTime);
		request.setAppointmentStartTime(fmtTime.format(tstartTime));
		Date tendTime = fmtTimeMilitary.parse(endTime);
		request.setAppointmentEndTime(fmtTime.format(tendTime));
		request.setServiceDateTypeId(Integer.valueOf(Constants.FIXED_DATE));
		return true;
	}

}
