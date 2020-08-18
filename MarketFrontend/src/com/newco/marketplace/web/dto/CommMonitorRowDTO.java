package com.newco.marketplace.web.dto;

import java.util.Date;

import org.apache.struts.util.LabelValueBean;

public class CommMonitorRowDTO extends SerializedBaseDTO{
	
	private LabelValueBean from;
	private LabelValueBean subject;
	private Date dateTime;
	private String generalPurposeParameter;

	public CommMonitorRowDTO()
	{		
	}

	
	public LabelValueBean getFrom() {
		return from;
	}
	public void setFrom(LabelValueBean from) {
		this.from = from;
	}
	public LabelValueBean getSubject() {
		return subject;
	}
	public void setSubject(LabelValueBean subject) {
		this.subject = subject;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	public String getGeneralPurposeParameter() {
		return generalPurposeParameter;
	}


	public void setGeneralPurposeParameter(String generalPurposeParameter) {
		this.generalPurposeParameter = generalPurposeParameter;
	}


	
	

}
