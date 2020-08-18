package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("businessInformation")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessInformation {

	@XStreamAlias("legalBusinessName")
	private String legalBusinessName;
	
	@XStreamAlias("doingBusinessAs")
	private String doingBusinessAs;
	
	@XStreamAlias("businessPhone")
	private String businessPhone;
	
	@XStreamAlias("businessFax")
	private String businessFax;
	
	@XStreamAlias("taxPayerId")
	private String taxPayerId;
	
	@XStreamAlias("dunsNo")
	private String dunsNo;
	
	@XStreamAlias("businessStructure")
	private String businessStructure;
	
	@XStreamAlias("businessStartedDate")
	private String businessStartedDate;
	
	@XStreamAlias("primaryIndustry")
	private String primaryIndustry;
	
	@XStreamAlias("websiteAddress")
	private String websiteAddress;
	
	@XStreamAlias("companySize")
	private String companySize;
	
	@XStreamAlias("annualSalesRevenue")
	private String annualSalesRevenue;
	
	@XStreamAlias("foreignOwnedInd")
	private Boolean foreignOwnedInd;
	
	@XStreamAlias("foreignOwnedPercent")
	private String foreignOwnedPercent;
	
	@XStreamAlias("companyId")
	private String companyId;
	
	@XStreamAlias("memberSinceDate")
	private String memberSinceDate;
	
	
	public String getLegalBusinessName() {
		return legalBusinessName;
	}
	public void setLegalBusinessName(String legalBusinessName) {
		this.legalBusinessName = legalBusinessName;
	}
	public String getDoingBusinessAs() {
		return doingBusinessAs;
	}
	public void setDoingBusinessAs(String doingBusinessAs) {
		this.doingBusinessAs = doingBusinessAs;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getBusinessFax() {
		return businessFax;
	}
	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}
	public String getTaxPayerId() {
		return taxPayerId;
	}
	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}
	public String getDunsNo() {
		return dunsNo;
	}
	public void setDunsNo(String dunsNo) {
		this.dunsNo = dunsNo;
	}
	public String getBusinessStructure() {
		return businessStructure;
	}
	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}
	public String getBusinessStartedDate() {
		return businessStartedDate;
	}
	public void setBusinessStartedDate(String businessStartedDate) {
		this.businessStartedDate = businessStartedDate;
	}
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	public String getWebsiteAddress() {
		return websiteAddress;
	}
	public void setWebsiteAddress(String websiteAddress) {
		this.websiteAddress = websiteAddress;
	}
	public String getCompanySize() {
		return companySize;
	}
	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}
	public String getAnnualSalesRevenue() {
		return annualSalesRevenue;
	}
	public void setAnnualSalesRevenue(String annualSalesRevenue) {
		this.annualSalesRevenue = annualSalesRevenue;
	}
	public Boolean getForeignOwnedInd() {
		return foreignOwnedInd;
	}
	public void setForeignOwnedInd(Boolean foreignOwnedInd) {
		this.foreignOwnedInd = foreignOwnedInd;
	}
	public String getForeignOwnedPercent() {
		return foreignOwnedPercent;
	}
	public void setForeignOwnedPercent(String foreignOwnedPercent) {
		this.foreignOwnedPercent = foreignOwnedPercent;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getMemberSinceDate() {
		return memberSinceDate;
	}
	public void setMemberSinceDate(String memberSinceDate) {
		this.memberSinceDate = memberSinceDate;
	}
	

}
