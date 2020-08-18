package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing estimate information.
 * @author Infosys
 *
 */
@XStreamAlias("estimate")
public class Estimate {

	@XStreamAlias("estimationId")
	private Integer estimationId;
	
	@XStreamAlias("estimationRefNo")
	private String estimationRefNo;
	
	@XStreamAlias("estimateDate")
	private String estimateDate;
	
	@XStreamAlias("firmDetails")
	private EstimateFirmDetails firmDetails;
	
	@XStreamAlias("providerDetails")
	private EstimateProviderDetails providerDetails;
	
	
	/*@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("acceptSource")
	private String acceptSource;
	
	@XStreamAlias("tripCharge")
	private Double tripCharge;*/
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("responseDate")
	private String responseDate;
	
	/*@XStreamAlias("respondedCustomerName")
	private String respondedCustomerName;
	
	@XStreamAlias("responseDate")
	private String responseDate;*/
	
	@XStreamAlias("estimateDetails")
	private EstimateDetails estimateDetails;
	
	@XStreamAlias("pricing")
	private EstimatePricingDetails pricing;
	
	@XStreamAlias("comments")
	private String comments;
	
	@XStreamAlias("logoDocumentId")
	private Integer logoDocumentId;
	
	@XStreamAlias("estimationExpiryDate")
	private String estimationExpiryDate;
	
	@XStreamAlias("estimateHistoryDetails")
	private EstimateHistoryDetails estimateHistoryDetails;
	
	public Integer getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Integer estimationId) {
		this.estimationId = estimationId;
	}

	public String getEstimationRefNo() {
		return estimationRefNo;
	}

	public void setEstimationRefNo(String estimationRefNo) {
		this.estimationRefNo = estimationRefNo;
	}

	public String getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}

	

	/*public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAcceptSource() {
		return acceptSource;
	}

	public void setAcceptSource(String acceptSource) {
		this.acceptSource = acceptSource;
	}

	public Double getTripCharge() {
		return tripCharge;
	}

	public void setTripCharge(Double tripCharge) {
		this.tripCharge = tripCharge;
	}*/

	public EstimateFirmDetails getFirmDetails() {
		return firmDetails;
	}

	public void setFirmDetails(EstimateFirmDetails firmDetails) {
		this.firmDetails = firmDetails;
	}

	public EstimateProviderDetails getProviderDetails() {
		return providerDetails;
	}

	public void setProviderDetails(EstimateProviderDetails providerDetails) {
		this.providerDetails = providerDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	/*public String getRespondedCustomerName() {
		return respondedCustomerName;
	}

	public void setRespondedCustomerName(String respondedCustomerName) {
		this.respondedCustomerName = respondedCustomerName;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}*/

	public EstimateDetails getEstimateDetails() {
		return estimateDetails;
	}

	public void setEstimateDetails(EstimateDetails estimateDetails) {
		this.estimateDetails = estimateDetails;
	}

	public EstimatePricingDetails getPricing() {
		return pricing;
	}

	public void setPricing(EstimatePricingDetails pricing) {
		this.pricing = pricing;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getLogoDocumentId() {
		return logoDocumentId;
	}

	public void setLogoDocumentId(Integer logoDocumentId) {
		this.logoDocumentId = logoDocumentId;
	}
	
	


	public String getEstimationExpiryDate() {
		return estimationExpiryDate;
	}

	public void setEstimationExpiryDate(String estimationExpiryDate) {
		this.estimationExpiryDate = estimationExpiryDate;
	}

	public EstimateHistoryDetails getEstimateHistoryDetails() {
		return estimateHistoryDetails;
	}

	public void setEstimateHistoryDetails(
			EstimateHistoryDetails estimateHistoryDetails) {
		this.estimateHistoryDetails = estimateHistoryDetails;
	}


	
}
