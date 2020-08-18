package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@XStreamAlias("FirmDetails")
public class FirmCompleteDetails {
		

	@XStreamOmitField
	NumberFormat numberFormat = new DecimalFormat("#.##");
	
	@XStreamAlias("FirmId")
	private String firmId;
	
	@XStreamAlias("FirmName")
	private String firmName;
	
	@XStreamAlias("FirmOverview")
	private String firmOverview;
	
	@XStreamAlias("FirmOwner")
	private String firmOwner;
	
	@XStreamAlias("FirmRating")
	private Double firmRating;
	
	@XStreamAlias("Reviews")
	private Reviewes reviews;
	
	@XStreamAlias("NumberEmployees")
	private Integer numberEmployees;
	
	@XStreamAlias("YearsOfService")
	private Double yearsOfService;
		
	@XStreamAlias("Policy")
	private Policy policy;
	
	@XStreamAlias("Insurances")
	private Insurances insurances;
	
	@XStreamAlias("Credentials")
	private Credentials credentials;
	
	@XStreamAlias("Services")
	private Services services;

	
	public Reviewes getReviews() {
		return reviews;
	}

	public void setReviews(Reviewes reviews) {
		this.reviews = reviews;
	}

	

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Insurances getInsurances() {
		return insurances;
	}

	public void setInsurances(Insurances insurances) {
		this.insurances = insurances;
	}

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

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmOverview() {
		return firmOverview;
	}

	public void setFirmOverview(String firmOverview) {
		this.firmOverview = firmOverview;
	}

	public String getFirmOwner() {
		return firmOwner;
	}

	public void setFirmOwner(String firmOwner) {
		this.firmOwner = firmOwner;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
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

	public Double getFirmRating() {
		return firmRating;
	}

	public void setFirmRating(Double firmRating) {
		if(null != firmRating){
			firmRating = Double.parseDouble(numberFormat.format(firmRating));
		}
		this.firmRating = firmRating;
	}

	public Integer getNumberEmployees() {
		return numberEmployees;
	}

	public void setNumberEmployees(Integer numberEmployees) {
		this.numberEmployees = numberEmployees;
	}

}
