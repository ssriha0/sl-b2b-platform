package com.newco.marketplace.vo.leadprofile;

import com.newco.marketplace.api.beans.Errors;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class LeadPartnerStatusResponseVO {
	
	@XStreamAlias("result")
	private String result;
	
	@XStreamAlias("partner_id")
	private Integer partnerId;
	
	@XStreamAlias("partner_name")
	private String partnerName;
	
	@XStreamAlias("partner_status")
	private String partnerStatus;
	
	@XStreamAlias("partner_status_reason")
	private String partnerStatusReason;
	
	@XStreamAlias("errors")
	private Errors errors;
	
	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerStatus() {
		return partnerStatus;
	}

	public void setPartnerStatus(String partnerStatus) {
		this.partnerStatus = partnerStatus;
	}

	public String getPartnerStatusReason() {
		return partnerStatusReason;
	}

	public void setPartnerStatusReason(String partnerStatusReason) {
		this.partnerStatusReason = partnerStatusReason;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}


}
