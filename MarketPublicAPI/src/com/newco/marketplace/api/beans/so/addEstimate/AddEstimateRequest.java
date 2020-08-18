package com.newco.marketplace.api.beans.so.addEstimate;

import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

//@XSD(name = "addEstimateRequest.xsd", path = "/resources/schemas/mobile/")
//@XmlRootElement(name = "soUpdateEstimate")
@XStreamAlias("soUpdateEstimate")
//@XmlAccessorType(XmlAccessType.FIELD)
public class AddEstimateRequest extends UserIdentificationRequest {
	
	@XStreamAlias("estimationId")
	private String estimationId;
	
	@XStreamAlias("estimationRefNo")
	private String estimationRefNo;
	 
	@XStreamAlias("estimationDate")
	private String estimationDate;
	
	@XStreamAlias("estimateTasks")
	private EstimateTasks estimateTasks;
	
	@XStreamAlias("estimateParts")
	private EstimateParts estimateParts;
	
	//Adding Other Services as part of R16_2_1
	@XStreamAlias("otherEstimateServices")
	private OtherEstimateServices otherEstimateServices;
	
	@XStreamAlias("pricing")
	private Pricing pricing;
	
	@XStreamAlias("comments")
	private String comments;
	
	@XStreamAlias("logoDocumentId")
	private String logoDocumentId;
	
	@XStreamAlias("isDraftEstimate")
	private String isDraftEstimate;
	
	@XStreamAlias("estimationExpiryDate")
	private String estimationExpiryDate;

	public String getEstimationRefNo() {
		return estimationRefNo;
	}

	public void setEstimationRefNo(String estimationRefNo) {
		this.estimationRefNo = estimationRefNo;
	}

	public String getEstimationDate() {
		return estimationDate;
	}

	public void setEstimationDate(String estimationDate) {
		this.estimationDate = estimationDate;
	}

	public EstimateTasks getEstimateTasks() {
		return estimateTasks;
	}

	public void setEstimateTasks(EstimateTasks estimateTasks) {
		this.estimateTasks = estimateTasks;
	}

	public EstimateParts getEstimateParts() {
		return estimateParts;
	}

	public void setEstimateParts(EstimateParts estimateParts) {
		this.estimateParts = estimateParts;
	}

	public Pricing getPricing() {
		return pricing;
	}

	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIsDraftEstimate() {
		return isDraftEstimate;
	}

	public void setIsDraftEstimate(String isDraftEstimate) {
		this.isDraftEstimate = isDraftEstimate;
	}

	public String getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(String estimationId) {
		this.estimationId = estimationId;
	}

	public String getLogoDocumentId() {
		return logoDocumentId;
	}

	public void setLogoDocumentId(String logoDocumentId) {
		this.logoDocumentId = logoDocumentId;
	}
	
	public OtherEstimateServices getOtherEstimateServices() {
		return otherEstimateServices;
	}

	public void setOtherEstimateServices(OtherEstimateServices otherEstimateServices) {
		this.otherEstimateServices = otherEstimateServices;
	}

	public String getEstimationExpiryDate() {
		return estimationExpiryDate;
	}

	public void setEstimationExpiryDate(String estimationExpiryDate) {
		this.estimationExpiryDate = estimationExpiryDate;
	}
	
	
	
}
