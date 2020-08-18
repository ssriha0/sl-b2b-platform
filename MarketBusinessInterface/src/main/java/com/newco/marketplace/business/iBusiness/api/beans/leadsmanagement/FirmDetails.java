package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("FirmDetail")
public class FirmDetails {
	
	@XStreamOmitField
	NumberFormat numberFormat = new DecimalFormat("#.##");
	
	@XStreamAlias("FirmId")
	private Integer firmId;

	@XStreamAlias("FirmRank")
	private Integer firmRank;

	@XStreamAlias("FirmName")
	private String firmName;

	@XStreamAlias("FirmOwner")
	private String firmOwner;
	
	@XStreamAlias("FirmDistance")
	private Double firmdistance;

	@XStreamAlias("YearsOfService")
	private Double yearsOfService;
	
	@XStreamAlias("FirmRating")
	private Double firmRating;

	@XStreamAlias("Contact")
	private Contact contact;

	@XStreamAlias("FirmReviews")
	private FirmReviews firmReviews;

	@XStreamAlias("PointsAwarded")
	private Integer pointsAwarded;
	
	//Added for ordering provider firm in mail to customer
	@XStreamOmitField
	private Integer providerFirmRank;
	
	public Integer getPointsAwarded() {
		return pointsAwarded;
	}

	public void setPointsAwarded(Integer pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public FirmReviews getFirmReviews() {
		return firmReviews;
	}

	public void setFirmReviews(FirmReviews firmReviews) {
		this.firmReviews = firmReviews;
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

	public Integer getProviderFirmRank() {
		return providerFirmRank;
	}

	public void setProviderFirmRank(Integer providerFirmRank) {
		this.providerFirmRank = providerFirmRank;
	}
}
