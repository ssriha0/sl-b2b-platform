package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Credentials;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Insurances;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Services;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("FirmDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmDetails {
	
	@XStreamAlias("FirmId")
	@XmlElement(name="FirmId")
	private Integer firmId;

	@XStreamAlias("FirmRank")
	@XmlElement(name="FirmRank")
	private Integer firmRank;

	@XStreamAlias("FirmName")
	@XmlElement(name="FirmName")
	private String firmName;

	@XStreamAlias("FirmOwner")
	@XmlElement(name="FirmOwner")
	private String firmOwner;
	
	@XStreamAlias("FirmDistance")
	@XmlElement(name="FirmDistance")
	private Double firmdistance;
	
	@XStreamAlias("FirmCity")
	@XmlElement(name="FirmCity")
	private String firmCity;
	
	@XStreamAlias("FirmState")
	@XmlElement(name="FirmState")
	private String firmState;

	@XStreamAlias("YearsOfService")
	@XmlElement(name="YearsOfService")
	private Double yearsOfService;
	
	@XStreamAlias("FirmRating")
	@XmlElement(name="FirmRating")
	private Double firmRating;

	@XStreamAlias("Contact")
	@XmlElement(name="Contact")
	private Contact contact;

	@XStreamAlias("FirmReviews")
	@XmlElement(name="FirmReviews")
	private FirmReviews firmReviews;

/*	@XStreamAlias("Insurances")
	private Insurances insurances;
	*/
	@XStreamAlias("Credentials")
	@XmlElement(name="Credentials")
	private Credentials credentials;
	
	@XStreamAlias("Services")
	@XmlElement(name="Services")
	private Services services;
	
	@XStreamAlias("PointsAwarded")
	@XmlElement(name="PointsAwarded")
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
		NumberFormat numberFormat = new DecimalFormat("#.##");
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
		NumberFormat numberFormat = new DecimalFormat("#.##");
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

	public String getFirmCity() {
		return firmCity;
	}

	public void setFirmCity(String firmCity) {
		this.firmCity = firmCity;
	}

	public String getFirmState() {
		return firmState;
	}

	public void setFirmState(String firmState) {
		this.firmState = firmState;
	}

	/*public Insurances getInsurances() {
		return insurances;
	}

	public void setInsurances(Insurances insurances) {
		this.insurances = insurances;
	}*/

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
	
	
	
}
