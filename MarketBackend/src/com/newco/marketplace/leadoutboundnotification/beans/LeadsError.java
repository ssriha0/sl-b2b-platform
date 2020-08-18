package com.newco.marketplace.leadoutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("error")	
public class LeadsError {
	
	@XStreamAlias("errorcode")	
	private String errorCode;
	
	@XStreamAlias("errorfield")	
	private String errorField;
	
	@XStreamAlias("errorfieldname")	
	private String errorFieldName;
	
	@XStreamAlias("technicaldescription")	
	private String technicalDescription;
	
	@XStreamAlias("displaytext")	
	private String displayText;
	
	@XStreamAlias("datetime")	
	private String dateTime;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorField() {
		return errorField;
	}

	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}

	public String getErrorFieldName() {
		return errorFieldName;
	}

	public void setErrorFieldName(String errorFieldName) {
		this.errorFieldName = errorFieldName;
	}

	public String getTechnicalDescription() {
		return technicalDescription;
	}

	public void setTechnicalDescription(String technicalDescription) {
		this.technicalDescription = technicalDescription;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
}
