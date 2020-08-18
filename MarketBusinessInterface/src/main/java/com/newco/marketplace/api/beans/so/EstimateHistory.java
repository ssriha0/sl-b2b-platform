package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing estimate information.
 * @author Infosys
 *
 */
@XStreamAlias("estimateHistory")
public class EstimateHistory {
	
	@XStreamAlias("estimationHistoryId")
	private Integer estimationHistoryId;

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
	
	@XStreamAlias("pricing")
	private EstimatePricingDetails pricing;
	
	@XStreamAlias("comments")
	private String comments;
	
	@XStreamAlias("logoDocumentId")
	private Integer logoDocumentId;
	
	@XStreamAlias("estimationExpiryDate")
	private String estimationExpiryDate;
	
	@XStreamAlias("action")
	private String action;
	
	@XStreamAlias("estimateItemsHistory")
	private EstimateItemsHistory estimateItemsHistory;


	public Integer getEstimationHistoryId() {
		return estimationHistoryId;
	}

	public void setEstimationHistoryId(Integer estimationHistoryId) {
		this.estimationHistoryId = estimationHistoryId;
	}

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public EstimateItemsHistory getEstimateItemsHistory() {
		return estimateItemsHistory;
	}

	public void setEstimateItemsHistory(EstimateItemsHistory estimateItemsHistory) {
		this.estimateItemsHistory = estimateItemsHistory;
	}
    
	

	
}
