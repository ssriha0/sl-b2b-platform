package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.List;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

public class InsuranceParameterBean extends RatingParameterBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136563273406573329L;
	private Integer vendorID;
	private Integer insuranceStatus;
	private List<Integer> insuranceTypes;
	private Boolean verifiedByServiceLive;
	
	public Integer getInsuranceStatus() {
		return insuranceStatus;
	}
	public void setInsuranceStatus(Integer insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}
	public List<Integer> getInsuranceTypes() {
		return insuranceTypes;
	}
	public void setInsuranceTypes(List<Integer> insuranceTypes) {
		this.insuranceTypes = insuranceTypes;
	}
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	public Boolean getVerifiedByServiceLive() {
		return verifiedByServiceLive;
	}
	public void setVerifiedByServiceLive(Boolean verifiedByServiceLive) {
		this.verifiedByServiceLive = verifiedByServiceLive;
	}
	
}
