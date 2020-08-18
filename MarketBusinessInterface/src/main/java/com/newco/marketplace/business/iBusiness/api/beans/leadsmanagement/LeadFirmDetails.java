package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("firmDetail")
public class LeadFirmDetails {
	
	@XStreamOmitField
	NumberFormat numberFormat = new DecimalFormat("#.##");
	
	@XStreamAlias("firmId")
	private Integer firmId;
	
	@XStreamAlias("resourceId")
	private Integer resourceId;

	@XStreamAlias("firmRank")
	private Integer firmRank;

	@XStreamAlias("firmName")
	private String firmName;

	@XStreamAlias("firmOwner")
	private String firmOwner;
	
	@XStreamAlias("firmdistance")
	private Double firmdistance;

	@XStreamAlias("yearsOfService")
	private Double yearsOfService;
	
	@XStreamAlias("firmRating")
	private Double firmRating;
	
	@XStreamAlias("phoneNo")
	private String phoneNo;
	
	@XStreamAlias("phoneExtNo")
	private String phoneExtNo;
	
	@XStreamAlias("mobileNo")
	private String mobileNo;
	
	@XStreamAlias("email")
	private String email;

	@XStreamAlias("altEmail")
	private String altEmail;
	
	/*
	@XStreamAlias("firmReviews")
	private LeadFirmReviews firmReviews;
	 */
	@XStreamAlias("locations")
	private Locations locations;
	
	
	

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public Integer getFirmRank() {
		return firmRank;
	}

	public void setFirmRank(Integer firmRank) {
		this.firmRank = firmRank;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmOwner() {
		return firmOwner;
	}

	public void setFirmOwner(String firmOwner) {
		this.firmOwner = firmOwner;
	}

	public Double getFirmRating() {
		return firmRating;
	}

	public void setFirmRating(Double firmRating) {
		if(null != firmRating){
			firmRating = Double.parseDouble(numberFormat.format(firmRating));
		}
		this.firmRating = firmRating;
	}

	public Double getFirmdistance() {
		return firmdistance;
	}

	public void setFirmdistance(Double firmdistance) {
		this.firmdistance = firmdistance;
	}

	public Double getYearsOfService() {
		return yearsOfService;
	}

	public void setYearsOfService(Double yearsOfService) {
		if(null != yearsOfService){
			yearsOfService = Double.parseDouble(numberFormat.format(yearsOfService));
		}
		this.yearsOfService = yearsOfService;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPhoneExtNo() {
		return phoneExtNo;
	}

	public void setPhoneExtNo(String phoneExtNo) {
		this.phoneExtNo = phoneExtNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public Locations getLocations() {
		return locations;
	}

	public void setLocations(Locations locations) {
		this.locations = locations;
	}
	/*
	public void setFirmReviews(LeadFirmReviews firmReviews) {
		this.firmReviews = firmReviews;
	}
	*/
}
