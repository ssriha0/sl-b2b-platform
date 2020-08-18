package com.newco.marketplace.web.dto;

public class SOWSelBuyerRefDTO extends SerializedBaseDTO{

	private static final long serialVersionUID = -2635592118510223188L;
	private String refType;
	private String refValue;
	private boolean privateInd;
	private Integer editable;
	//Priority 5B
	private String requiredInd;
	
	public String getRequiredInd() {
		return requiredInd;
	}
	public void setRequiredInd(String requiredInd) {
		this.requiredInd = requiredInd;
	}
	
	public Integer getEditable() {
		return editable;
	}
	public void setEditable(Integer editable) {
		this.editable = editable;
	}
	public boolean isPrivateInd() {
		return privateInd;
	}
	public void setPrivateInd(boolean privateInd) {
		this.privateInd = privateInd;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getRefValue() {
		return refValue;
	}
	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

}
