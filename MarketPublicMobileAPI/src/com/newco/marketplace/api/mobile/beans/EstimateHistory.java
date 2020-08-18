package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing estimate information.
 * @author Infosys
 *
 */
@XStreamAlias("estimateHistory")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimateHistory {
	
	@XStreamAlias("estimationHistoryId")
	private Integer estimationHistoryId;

	@XStreamAlias("estimationId")
	private Integer estimationId;
	
	@XStreamAlias("estimationRefNo")
	private String estimationRefNo;
	
	@XStreamAlias("estimateDate")
	private String estimateDate;
	
	@XStreamAlias("resourceId")
	private Integer resourceId;
	
	@XStreamAlias("vendorId")
	private Integer vendorId;
	
	
	/*@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("acceptSource")
	private String acceptSource;
	
	@XStreamAlias("tripCharge")
	private Double tripCharge;*/
	
	@XStreamAlias("status")
	private String status;
	
	/*@XStreamAlias("respondedCustomerName")
	private String respondedCustomerName;*/
	
	@XStreamAlias("responseDate")
	private String responseDate;
	
	@XStreamAlias("pricing")
	private PricingDetails pricing;
	
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


	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}


	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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



	public PricingDetails getPricing() {
		return pricing;
	}

	public void setPricing(PricingDetails pricing) {
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
	

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public EstimateItemsHistory getEstimateItemsHistory() {
		return estimateItemsHistory;
	}

	public void setEstimateItemsHistory(EstimateItemsHistory estimateItemsHistory) {
		this.estimateItemsHistory = estimateItemsHistory;
	}
   
	
	

	
}
