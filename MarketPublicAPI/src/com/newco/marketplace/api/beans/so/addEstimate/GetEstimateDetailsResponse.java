package com.newco.marketplace.api.beans.so.addEstimate;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="getSOEstimateDetailsResponse.xsd", path="/resources/schemas/mobile/")
@XStreamAlias("getEstimateDetailsResponse")
@XmlRootElement(name = "getEstimateDetailsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetEstimateDetailsResponse implements IAPIResponse{
	

	@XStreamAlias("results")
	@XmlElement(name="results")
	private Results results;
	
	@XStreamAlias("estimationId")
	@XmlElement(name="estimationId")
	private Integer estimationId;
	
	@XStreamAlias("estimationRefNo")
	@XmlElement(name="estimationRefNo")
	private String estimationRefNo;
	
	@XStreamAlias("estimateDate")
	@XmlElement(name="estimateDate")
	private String estimateDate;
	
	@XStreamAlias("resourceId")
	@XmlElement(name="resourceId")
	private Integer resourceId;
	
	@XStreamAlias("vendorId")
	@XmlElement(name="vendorId")
	private Integer vendorId;
	
	/*@XStreamAlias("description")
	@XmlElement(name="description")
	private String description;
	
	@XStreamAlias("acceptSource")
	@XmlElement(name="acceptSource")
	private String acceptSource;
	
	@XStreamAlias("tripCharge")
	@XmlElement(name="tripCharge")
	private Double tripCharge;*/
	
	@XStreamAlias("status")
	@XmlElement(name="status")
	private String status;
	
	/*@XStreamAlias("respondedCustomerName")
	@XmlElement(name="respondedCustomerName")
	private String respondedCustomerName;*/
	
	@XStreamAlias("responseDate")
	@XmlElement(name="responseDate")
	private String responseDate;
	
	@XStreamAlias("estimateDetails")
	@XmlElement(name="estimateDetails")
	private EstimateDetails estimateDetails;
	
	@XStreamAlias("pricing")
	@XmlElement(name="pricing")
	private PricingDetails pricing;
	
	@XStreamAlias("comments")
	@XmlElement(name="comments")
	private String comments;
	
	@XStreamAlias("logoDocumentId")
	@XmlElement(name="logoDocumentId")
	private Integer logoDocumentId;
	
	@XStreamAlias("estimationExpiryDate")
	@XmlElement(name="estimationExpiryDate")
	private String estimationExpiryDate;
	
	@XStreamAlias("estimateHistoryDetails")
	@XmlElement(name="estimateHistoryDetails")
	private EstimateHistoryDetails estimateHistoryDetails;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
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

	public EstimateDetails getEstimateDetails() {
		return estimateDetails;
	}

	public void setEstimateDetails(EstimateDetails estimateDetails) {
		this.estimateDetails = estimateDetails;
	}

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

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public Integer getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Integer estimationId) {
		this.estimationId = estimationId;
	}

	public EstimateHistoryDetails getEstimateHistoryDetails() {
		return estimateHistoryDetails;
	}

	public void setEstimateHistoryDetails(
			EstimateHistoryDetails estimateHistoryDetails) {
		this.estimateHistoryDetails = estimateHistoryDetails;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}	
	
	
	
}
