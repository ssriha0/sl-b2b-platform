package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class LuBuyerRefVO extends SerializableBaseVO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -135417318026406274L;
	private String refType;
	private Integer refTypeId;
	private Integer buyerInput;
	private Integer required;
	private String referenceValue;
	private Integer activeInd;
	private Integer editable;
	private Integer displayNoValue;
	
	public Integer getDisplayNoValue() {
		return displayNoValue;
	}

	public void setDisplayNoValue(Integer displayNoValue) {
		this.displayNoValue = displayNoValue;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public Integer getRefTypeId() {
		return refTypeId;
	}

	public void setRefTypeId(Integer refTypeId) {
		this.refTypeId = refTypeId;
	}

	public Integer getBuyerInput() {
		return buyerInput;
	}

	public void setBuyerInput(Integer buyerInput) {
		this.buyerInput = buyerInput;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public String getReferenceValue() {
		return referenceValue;
	}

	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}

	public Integer getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(Integer activeInd) {
		this.activeInd = activeInd;
	}

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}
	
	
}
