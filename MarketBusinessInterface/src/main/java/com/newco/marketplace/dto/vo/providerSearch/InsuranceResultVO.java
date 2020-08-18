package com.newco.marketplace.dto.vo.providerSearch;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;


public class InsuranceResultVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4558147858565421074L;
	private Integer vendorInsuranceTypes;
	private Boolean verifiedByServiceLive = Boolean.FALSE;
	private Double amount;
	private Date insVerifiedDate;
	private Boolean insurancePresent =Boolean.FALSE;
	
	public Boolean getInsurancePresent() {
		return insurancePresent;
	}
	public void setInsurancePresent(Boolean insurancePresent) {
		this.insurancePresent = insurancePresent;
	}
	public Integer getVendorInsuranceTypes() {
		return vendorInsuranceTypes;
	}
	public void setVendorInsuranceTypes(Integer vendorInsuranceTypes) {
		this.vendorInsuranceTypes = vendorInsuranceTypes;
	}
	public Boolean getVerifiedByServiceLive() {
		return verifiedByServiceLive;
	}
	public void setVerifiedByServiceLive(Boolean verifiedByServiceLive) {
		this.verifiedByServiceLive = verifiedByServiceLive;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getInsVerifiedDate() {
		return insVerifiedDate;
	}
	public void setInsVerifiedDate(Date insVerifiedDate) {
		this.insVerifiedDate = insVerifiedDate;
	}

	
}
