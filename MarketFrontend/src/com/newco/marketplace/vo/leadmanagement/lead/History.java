package com.newco.marketplace.vo.leadmanagement.lead;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History {

	private String description;
	private String historyDate;
	private String historyBy;
	private String historyAction;
	private String firmStatus;
	private String reasonComment;
	private  String formattedHistoryDate;
	
	public String getHistoryAction() {
		return historyAction;
	}
	public void setHistoryAction(String historyAction) {
		this.historyAction = historyAction;
	}
	public String getHistoryBy() {
		return historyBy;
	}
	public void setHistoryBy(String historyBy) {
		this.historyBy = historyBy;
	}
	public String getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFirmStatus() {
		return firmStatus;
	}
	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}
	public String getReasonComment() {
		return reasonComment;
	}
	public void setReasonComment(String reasonComment) {
		this.reasonComment = reasonComment;
	}
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
	private static SimpleDateFormat popupDate = new SimpleDateFormat("MMM d, yyyy hh:mm a");
	
	public String getFormattedHistoryDate() {
		return formattedHistoryDate;
	}
	public void setFormattedHistoryDate() {
		this.formattedHistoryDate = getFormattedDate(this.historyDate);
	}
	public String getFormattedDate(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpDate(date);
		return outDate;

	}
	public String getFormattedTime(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpTime(date);
		return outDate;

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
		return timeFormat.format(pDate);
	}
}
