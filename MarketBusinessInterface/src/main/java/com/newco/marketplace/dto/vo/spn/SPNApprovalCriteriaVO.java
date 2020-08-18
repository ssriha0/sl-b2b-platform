package com.newco.marketplace.dto.vo.spn;

import com.sears.os.vo.SerializableBaseVO;

public class SPNApprovalCriteriaVO extends SerializableBaseVO{
	private static final long serialVersionUID = -1314230224821828179L;
	private int id;
	private int criteriaId;
	private String criteriaDesc;
	private int criteriaLevel;
	private String criteriaValue;
	private int spnId;
	private String value; 
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(int criteriaId) {
		this.criteriaId = criteriaId;
	}
	public String getCriteriaDesc() {
		return criteriaDesc;
	}
	public void setCriteriaDesc(String criteriaDesc) {
		this.criteriaDesc = criteriaDesc;
	}
	public int getCriteriaLevel() {
		return criteriaLevel;
	}
	public void setCriteriaLevel(int criteriaLevel) {
		this.criteriaLevel = criteriaLevel;
	}
	public String getCriteriaValue() {
		return criteriaValue;
	}
	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	public int getSpnId() {
		return spnId;
	}
	public void setSpnId(int spnId) {
		this.spnId = spnId;
	}
	
}