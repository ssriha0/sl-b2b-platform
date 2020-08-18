package com.newco.marketplace.dto.vo.leadsmanagement;

import java.sql.Timestamp;

public class LeadMatchingProVO {
	private String slLeadId;
	private Integer lmsFirmId;
	private Integer serviceLiveFirmId;
	private Integer leadFirmStatus;
	private boolean postedInd;
	private double leadPrice;
	private Timestamp createdDate;
	private Timestamp modifiedDate;

	public String getSlLeadId() {
		return slLeadId;
	}

	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public Integer getLmsFirmId() {
		return lmsFirmId;
	}

	public void setLmsFirmId(Integer lmsFirmId) {
		this.lmsFirmId = lmsFirmId;
	}

	public Integer getLeadFirmStatus() {
		return leadFirmStatus;
	}

	public void setLeadFirmStatus(Integer leadFirmStatus) {
		this.leadFirmStatus = leadFirmStatus;
	}

	public boolean isPostedInd() {
		return postedInd;
	}

	public void setPostedInd(boolean postedInd) {
		this.postedInd = postedInd;
	}

	public double getLeadPrice() {
		return leadPrice;
	}

	public void setLeadPrice(double leadPrice) {
		this.leadPrice = leadPrice;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getServiceLiveFirmId() {
		return serviceLiveFirmId;
	}

	public void setServiceLiveFirmId(Integer serviceLiveFirmId) {
		this.serviceLiveFirmId = serviceLiveFirmId;
	}

}
