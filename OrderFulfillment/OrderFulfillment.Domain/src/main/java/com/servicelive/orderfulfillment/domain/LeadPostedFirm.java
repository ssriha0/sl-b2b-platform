package com.servicelive.orderfulfillment.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "lead_matched_firm")
@XmlRootElement()
public class LeadPostedFirm extends LeadChild{
	
	private static final long serialVersionUID = 9180446939179439349L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lead_prov_match_id")
	private Integer providerMatchId;
	
	@Column(name="lms_firm_id")
	private Integer lmsFirmId;
	
	@Column(name="vendor_id")
	private Integer vendorId;
	
	@Column(name="resource_id")
	private Integer resourceId;
	
	@Column(name="lead_firm_status")
	private Integer leadFirmStatus;
	
	@Column(name="lead_posted_ind")
	private boolean postedInd;
	
	@Column(name="lead_price")
	private double leadPrice;
	
	@Column(name="service_start_time")
	private String serviceStartTime;
	
	@Column(name="service_end_time")
	private String serviceEndTime;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name="service_date")
	private Date serviceDate;
	
	@Column(name="number_of_visits")
	private Integer noOfVisits;
	
	
	public Integer getProviderMatchId() {
		return providerMatchId;
	}
	public void setProviderMatchId(Integer providerMatchId) {
		this.providerMatchId = providerMatchId;
	}
	public Integer getLmsFirmId() {
		return lmsFirmId;
	}
	public void setLmsFirmId(Integer lmsFirmId) {
		this.lmsFirmId = lmsFirmId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
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
	public String getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	public String getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public Integer getNoOfVisits() {
		return noOfVisits;
	}
	public void setNoOfVisits(Integer noOfVisits) {
		this.noOfVisits = noOfVisits;
	}

}
