package com.newco.marketplace.vo.leadprofile;

public class LeadServicePriceVO {
	
	private Integer leadProjectPriceId;
	private Integer lmsProjectTypeId;
	private Integer leadCategoryId;
	private String competitivePrice;
	private String exclusivePrice;
	private Integer launcMarketId;
	private String providerFirmId;
	private Integer projectTypeMappingId;
	private boolean compSelected = false;
	private boolean exclSelected = false;
	private String lmsProjectTypeDesc;
	private String lmsServiceCategoryDesc;
	
	public Integer getLeadProjectPriceId() {
		return leadProjectPriceId;
	}
	public void setLeadProjectPriceId(Integer leadProjectPriceId) {
		this.leadProjectPriceId = leadProjectPriceId;
	}
	public Integer getLmsProjectTypeId() {
		return lmsProjectTypeId;
	}
	public void setLmsProjectTypeId(Integer lmsProjectTypeId) {
		this.lmsProjectTypeId = lmsProjectTypeId;
	}
	public Integer getLeadCategoryId() {
		return leadCategoryId;
	}
	public void setLeadCategoryId(Integer leadCategoryId) {
		this.leadCategoryId = leadCategoryId;
	}
	public String getCompetitivePrice() {
		return competitivePrice;
	}
	public void setCompetitivePrice(String competitivePrice) {
		this.competitivePrice = competitivePrice;
	}
	public String getExclusivePrice() {
		return exclusivePrice;
	}
	public void setExclusivePrice(String exclusivePrice) {
		this.exclusivePrice = exclusivePrice;
	}
	public Integer getLauncMarketId() {
		return launcMarketId;
	}
	public void setLauncMarketId(Integer launcMarketId) {
		this.launcMarketId = launcMarketId;
	}
	public String getProviderFirmId() {
		return providerFirmId;
	}
	public void setProviderFirmId(String providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	public Integer getProjectTypeMappingId() {
		return projectTypeMappingId;
	}
	public void setProjectTypeMappingId(Integer projectTypeMappingId) {
		this.projectTypeMappingId = projectTypeMappingId;
	}
	public boolean isCompSelected() {
		return compSelected;
	}
	public void setCompSelected(boolean compSelected) {
		this.compSelected = compSelected;
	}
	public boolean isExclSelected() {
		return exclSelected;
	}
	public void setExclSelected(boolean exclSelected) {
		this.exclSelected = exclSelected;
	}
	public String getLmsProjectTypeDesc() {
		return lmsProjectTypeDesc;
	}
	public void setLmsProjectTypeDesc(String lmsProjectTypeDesc) {
		this.lmsProjectTypeDesc = lmsProjectTypeDesc;
	}
	public String getLmsServiceCategoryDesc() {
		return lmsServiceCategoryDesc;
	}
	public void setLmsServiceCategoryDesc(String lmsServiceCategoryDesc) {
		this.lmsServiceCategoryDesc = lmsServiceCategoryDesc;
	}
}
