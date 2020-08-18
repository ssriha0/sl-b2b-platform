package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("precallHistory")
public class PreCallHistory {
	
	@XStreamAlias("reason")
	private String reason;
	
	@XStreamAlias("day")
	private String day;
	
	@XStreamAlias("date")
	private String date;
	
	@XStreamAlias("scheduleStatus")
	private String scheduleStatus;

	private String modifiedBy;
	
	@XStreamOmitField
	private int confirmInd;

	public int getConfirmInd() {
		return confirmInd;
	}
	public void setConfirmInd(int confirmInd) {
		this.confirmInd = confirmInd;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	private static SimpleDateFormat popupDate = new SimpleDateFormat("EEE MM/d/yyyy");
	private static SimpleDateFormat detailFormat = new SimpleDateFormat("MM/dd/yy hh:mm aa");
	private static SimpleDateFormat popupTime = new SimpleDateFormat("hh:mm aa");
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	private String formattedDate;
	private String formattedTime;
	private String detailFormattedDate;

	
	public String getDetailFormattedDate() {
		return detailFormattedDate;
	}
	public void setDetailFormattedDate() {
		this.detailFormattedDate = getDetailFormattedDate(this.date);
	}
	public String getFormattedTime() {
		return formattedTime;
	}
	public void setFormattedTime() {
		this.formattedTime = getFormattedServiceTime(this.date);
	}
	public String getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate() {
		this.formattedDate = getFormatDate(this.date);
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	
	public String getFormatDate(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpDate(date);
		return outDate;

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
	public String getDetailFormattedDate(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getDetailDate(date);
		return outDate;

	}
	public static String getDetailDate(Date pDate) {
		return detailFormat.format(pDate);
	}
}
