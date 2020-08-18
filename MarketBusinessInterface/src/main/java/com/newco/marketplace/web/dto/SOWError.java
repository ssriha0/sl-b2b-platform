package com.newco.marketplace.web.dto;

public class SOWError implements IError{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2916746129001868152L;
	private String fieldId;
	private String errorMsg;
	private String errorType;
	
	
	
	public SOWError(String fieldId, String errorMsg, String errorType) {
		super();
		this.fieldId = fieldId;
		this.errorMsg = errorMsg;
		this.errorType = errorType;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getMsg() {
		return errorMsg;
	}
	public void setMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMsgType() {
		return errorType;
	}
	public void setMsgType(String errorType) {
		this.errorType = errorType;
	}
	
	
}
