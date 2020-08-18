package com.servicelive.domain.spn.network;

import java.io.Serializable;
import java.util.Date;



/**
 * Class to hold exceptions types,gracePeriod values (for SL_18018)
 *
 */
public class SPNComplianceVO  implements Serializable
{

	private static final long serialVersionUID = 5254692630787236707L;
	private Integer spnId;
	private String credType;
    private String credCategory;
	private String fullName;
	private Integer id;
	private Integer vendorId;

	private String marketName;
	private String stateCode;
	private String wfState;
	private String providerFirstName;
	private String providerLastName;
	private String requirement;
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
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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
