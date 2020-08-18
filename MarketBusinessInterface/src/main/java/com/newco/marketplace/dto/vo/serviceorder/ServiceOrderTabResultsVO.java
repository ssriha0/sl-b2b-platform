package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderTabResultsVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5633839460161936117L;
	private String soId = "";
	private String soStatus = "";
	private String soSubStatus = "";
	private String soTitle = "";
	private String soTitleDesc = "";
	private Timestamp appointStartDate;
	private Timestamp appointEndDate;
	private Timestamp createdDate;
	private Integer buyerID = 0;
	private Integer acceptedResourceId = 0;
	private Integer acceptedVendorId = 0;
	private String street1 = "";
	private String street2 = "";
	private String city = "";
	private String stateCd = "";
	private String zip = "";
	private String zip4 = "";
	private String country = "";
	private Double spendLimit = 0.0;
	private String providerFirstName = "";
	private String providerLastName = "";
	private String buyerFirstName = "";
	private String buyerLastName = "";
	private String endCustomerFirstName = "";
	private String endCustomerLastName = "";
	private Timestamp routedDate;
	private Integer providerCounts = 0;
	private Integer condCounts = 0;
	private Integer rejectedCounts = 0;
	private String searchByType = "";	
	private String searchByValue = "";
	private String roleType = "";
	private String routedResourceId = "";
	
	
	
	public String getRoutedResourceId() {
		return routedResourceId;
	}
	public void setRoutedResourceId(String routedResourceId) {
		this.routedResourceId = routedResourceId;
	}
	public Integer getAcceptedVendorId() {
		return acceptedVendorId;
	}
	public void setAcceptedVendorId(Integer acceptedVendorId) {
		this.acceptedVendorId = acceptedVendorId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getSearchByType() {
		return searchByType;
	}
	public void setSearchByType(String searchByType) {
		this.searchByType = searchByType;
	}
	public String getSearchByValue() {
		return searchByValue;
	}
	public void setSearchByValue(String searchByValue) {
		this.searchByValue = searchByValue;
	}
	public String getEndCustomerFirstName() {
		return endCustomerFirstName;
	}
	public void setEndCustomerFirstName(String endCustomerFirstName) {
		this.endCustomerFirstName = endCustomerFirstName;
	}
	public String getEndCustomerLastName() {
		return endCustomerLastName;
	}
	public void setEndCustomerLastName(String endCustomerLastName) {
		this.endCustomerLastName = endCustomerLastName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
    public String getBuyerFirstName() {
        return buyerFirstName;
    }
    public void setBuyerFirstName(String buyerFirstName) {
        this.buyerFirstName = buyerFirstName;
    }
    public String getBuyerLastName() {
        return buyerLastName;
    }
    public void setBuyerLastName(String buyerLastName) {
        this.buyerLastName = buyerLastName;
    }
    
	public Timestamp getAppointEndDate() {
		return appointEndDate;
	}
	public void setAppointEndDate(Timestamp appointEndDate) {
		this.appointEndDate = appointEndDate;
	}
	public Timestamp getAppointStartDate() {
		return appointStartDate;
	}
	public void setAppointStartDate(Timestamp appointStartDate) {
		this.appointStartDate = appointStartDate;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}
	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	public Double getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(Double spendLimit) {
		this.spendLimit = spendLimit;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public String getSoTitleDesc() {
		return soTitleDesc;
	}
	public void setSoTitleDesc(String soTitleDesc) {
		this.soTitleDesc = soTitleDesc;
	}
	public Timestamp getRoutedDate() {
		return routedDate;
	}
	public void setRoutedDate(Timestamp routedDate) {
		this.routedDate = routedDate;
	}

	public Integer getCondCounts() {
		return condCounts;
	}
	public void setCondCounts(Integer condCounts) {
		this.condCounts = condCounts;
	}
	public Integer getRejectedCounts() {
		return rejectedCounts;
	}
	public void setRejectedCounts(Integer rejectedCounts) {
		this.rejectedCounts = rejectedCounts;
	}
	public Integer getProviderCounts() {
		return providerCounts;
	}
	public void setProviderCounts(Integer providerCounts) {
		this.providerCounts = providerCounts;
	}
	
	
}
