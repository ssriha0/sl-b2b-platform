package com.newco.marketplace.web.dto;

public class SOWWarning implements IWarning{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3782686340473882370L;
	private String fieldId;
	private String warningMsg;
	private String warningType;
	
	
	
	public SOWWarning(String fieldId, String warningMsg, String warningType) {
		super();
		this.fieldId = fieldId;
		this.warningMsg = warningMsg;
		this.warningType = warningType;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getMsg() {
		return warningMsg;
	}
	public void setMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
	public String getMsgType() {
		return warningType;
	}
	public void setMsgType(String warningType) {
		this.warningType = warningType;
	}
	
	
}
