package com.newco.marketplace.api.beans.so;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing schedule information.
 * @author Infosys
 *
 */
@XStreamAlias("schedule")
public class Schedule {
	
	@XStreamAlias("scheduleType")
	private String scheduleType;
	
	@XStreamAlias("serviceDateTime1")
	private String serviceDateTime1;
	
	@XStreamAlias("serviceDateTime2")
	private String serviceDateTime2;
	
	@XStreamAlias("confirmWithCustomer")
	private String confirmWithCustomer;
	
	@XStreamAlias("reschedule")
	private Reschedule reschedule;
	
	@OptionalParam
	@XStreamAlias("serviceLocationTimezone") 
	private String serviceLocationTimezone;
	
	private String formattedServiceDate1;
	private String formattedServiceDate2;
	private String serviceTime1;
	private static SimpleDateFormat popupDate = new SimpleDateFormat("EEE MMM d, yyyy");
	private static SimpleDateFormat popupTime = new SimpleDateFormat("hh:mm aa");
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	

	public String getFormattedServiceDate1() {
		return formattedServiceDate1;
	}

	public String getFormattedServiceDate2() {
		return formattedServiceDate2;
	}

	public String getServiceTime1() {
		return serviceTime1;
	}

	public String getServiceTime2() {
		return serviceTime2;
	}
	private String serviceTime2;

	
	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getServiceDateTime1() {
		return serviceDateTime1;
	}

	public void setServiceDateTime1(String serviceDateTime1) {
		this.serviceDateTime1 = serviceDateTime1;
	}

	public String getServiceDateTime2() {
		return serviceDateTime2;
	}

	public void setServiceDateTime2(String serviceDateTime2) {
		this.serviceDateTime2 = serviceDateTime2;
	}

	public String getConfirmWithCustomer() {
		return confirmWithCustomer;
	}

	public void setConfirmWithCustomer(String confirmWithCustomer) {
		this.confirmWithCustomer = confirmWithCustomer;
	}

	public Reschedule getReschedule() {
		return reschedule;
	}

	public void setReschedule(Reschedule reschedule) {
		this.reschedule = reschedule;
	}

	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}

	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}
	public String getFormattedServiceDate(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpDate(date);
		return outDate;

	}
	public void setFormattedServiceDate1() {
		String serviceDate = serviceDateTime1.replaceAll("T", " ");
		this.formattedServiceDate1 = getFormattedServiceDate(serviceDate);
	}

	public void setFormattedServiceDate2() {
		String serviceDate = serviceDateTime2.replaceAll("T", " ");
		this.formattedServiceDate2 = getFormattedServiceDate(serviceDate);
	}

	public void setServiceTime1() {
		String serviceTime = serviceDateTime1.replaceAll("T", " ");
		this.serviceTime1 = getFormattedServiceTime(serviceTime);
	}

	public void setServiceTime2() {
		String serviceTime = serviceDateTime2.replaceAll("T", " ");
		this.serviceTime2 = getFormattedServiceTime(serviceTime);
	}

	public String getFormattedServiceTime(String inTime){
		Date date = formatStringToDate(inTime);
		String outTime = getPopUpTime(date);
		
		return outTime;

	}
	public static Date formatStringToDate(String source)
	{
			try {
				return defaultFormat.parse(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}	
	public static String getPopUpDate(Date pDate) {
		return popupDate.format(pDate);
	}
	
	public static String getPopUpTime(Date pDate) {
			return popupTime.format(pDate);
		
	}

}
