package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;

public class SPNComplianceVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -645331214344123531L;
	
	private Integer spnId;
	private String credType;
    private String credCategory;
	private Integer id;

	private String wfState;
	private String providerFirstName;
	private String providerLastName;
	private String requirement;
	private String spnName;
	private String buyerName;
	private String criteriaName;
	private Double liabilityAmount;
	private String criteriaValueName;
	private String providerState;

	
	
	public String getCriteriaValueName() {
		return criteriaValueName;
	}
	public void setCriteriaValueName(String criteriaValueName) {
		this.criteriaValueName = criteriaValueName;
	}
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public String getCredType() {
		return credType;
	}
	public void setCredType(String credType) {
		this.credType = credType;
	}
	public String getCredCategory() {
		return credCategory;
	}
	public void setCredCategory(String credCategory) {
		this.credCategory = credCategory;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getWfState() {
		return wfState;
	}
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
	public String getProviderFirstName() {
		return providerFirstName;
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderLastName() {
		return providerLastName;
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getCriteriaName() {
		return criteriaName;
	}
	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}
	public Double getLiabilityAmount() {
		return liabilityAmount;
	}
	public void setLiabilityAmount(Double liabilityAmount) {
		this.liabilityAmount = liabilityAmount;
	}
	public String getProviderState() {
		return providerState;
	}
	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}
	
	
	
	
}
