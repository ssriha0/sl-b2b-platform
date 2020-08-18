package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Provider Firm VO.
 */
public class RoutingRulesSearchVO implements Serializable
{
	private static final long serialVersionUID = 4209518599795591722L;

	
	private BigInteger providerFirmId;
	private String providerFirmName;
	private String ruleName; 
	private Integer searchColumn;
	private Integer buyerId;
	private String uploadedFileName;
	private BigInteger ruleId;
	private String processId;
	private Boolean exactSearch = false;
	private String nullSearchCriteria;
    private String autoAcceptSearchlabel;
	public String getNullSearchCriteria() {
		return nullSearchCriteria;
	}

	public void setNullSearchCriteria(String nullSearchCriteria) {
		this.nullSearchCriteria = nullSearchCriteria;
	} 
 
	public Boolean getExactSearch() {
		return exactSearch;
	}

	public void setExactSearch(Boolean exactSearch) {
		this.exactSearch = exactSearch;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(Integer searchColumn) {
		this.searchColumn = searchColumn;
	}
 
	/**
	 * 
	 */
	public RoutingRulesSearchVO() {
		super();
	}

	public RoutingRulesSearchVO(BigInteger providerFirmId,
			String providerFirmName, String ruleName) {
		super();
		this.providerFirmId = providerFirmId;
		this.providerFirmName = providerFirmName;
		this.ruleName = ruleName;
	}

	

	public BigInteger getProviderFirmId() {
		return providerFirmId;
	}

	public void setProviderFirmId(BigInteger providerFirmId) {
		this.providerFirmId = providerFirmId;
	}

	public String getProviderFirmName() {
		return providerFirmName;
	}

	public void setProviderFirmName(String providerFirmName) {
		this.providerFirmName = providerFirmName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public BigInteger getRuleId() {
		return ruleId;
	}

	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	/**
	 * @return the autoAcceptSearchlabel
	 */
	public String getAutoAcceptSearchlabel() {
		return autoAcceptSearchlabel;
	}

	/**
	 * @param autoAcceptSearchlabel the autoAcceptSearchlabel to set
	 */
	public void setAutoAcceptSearchlabel(String autoAcceptSearchlabel) {
		this.autoAcceptSearchlabel = autoAcceptSearchlabel;
	}

	
}