package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("FirmDetail")
public class PostFirmDetails {
	
	@XStreamOmitField
	NumberFormat numberFormat = new DecimalFormat("#.##");

	@XStreamAlias("FirmId")
	private Integer firmId;

	@XStreamAlias("FirmName")
	private String firmName;

	@XStreamAlias("FirmOwner")
	private String firmOwner;

	@XStreamAlias("FirmRating")
	private Double firmRating;

	@XStreamAlias("Contact")
	private FirmContact Contact;

	@XStreamAlias("PointsAwarded")
	private Integer pointsAwarded;

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public FirmContact getContact() {
		return Contact;
	}

	public void setContact(FirmContact contact) {
		Contact = contact;
	}

	public Integer isPointsAwarded() {
		return pointsAwarded;
	}

	public void setPointsAwarded(Integer pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
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

}
