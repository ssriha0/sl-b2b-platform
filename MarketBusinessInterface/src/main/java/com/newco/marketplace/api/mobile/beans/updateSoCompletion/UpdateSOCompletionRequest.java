package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.newco.marketplace.api.mobile.beans.sodetails.Permits;

@XSD(name="updateSOCompletionRequest.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name="soCompletionRequest")
@XStreamAlias("soCompletionRequest")
public class UpdateSOCompletionRequest {

	@XStreamAlias("completionStatus")
	private String completionStatus;
	
	@XStreamAlias("updateAction")
	private String updateAction;
	
	@XStreamAlias("tasks")
	private Tasks tasks;
	
	@XStreamAlias("resolutionComments")
	private String resolutionComments;
	
	/*@XStreamAlias("cancellationOrRevisitReasonCode")
	private Integer cancellationOrRevisitReasonCode;*/
	
	@XStreamAlias("permits")
	private Permits permits;
	
	@XStreamAlias("references")
	private References references;

	@XStreamAlias("partsTracking")
	private PartsTracking partsTracking;

	@XStreamAlias("providerSignature")
	private Signature providerSignature;
	
	@XStreamAlias("customerSignature")
	private Signature customerSignature;
	
	@XStreamAlias("addOns")
	private AddOns addOns;
	
	@XStreamAlias("addOnPaymentDetails")
	private AddOnPaymentDetails addOnPaymentDetails;
	
	@XStreamAlias("parts")
	private Parts parts;
	
	@XStreamAlias("pricing")
	private Pricing pricing;

	
	public String getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
	}

	public String getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public Permits getPermits() {
		return permits;
	}

	public void setPermits(Permits permits) {
		this.permits = permits;
	}

	public References getReferences() {
		return references;
	}

	public void setReferences(References references) {
		this.references = references;
	}

	public PartsTracking getPartsTracking() {
		return partsTracking;
	}

	public void setPartsTracking(PartsTracking partsTracking) {
		this.partsTracking = partsTracking;
	}

	public AddOns getAddOns() {
		return addOns;
	}

	public void setAddOns(AddOns addOns) {
		this.addOns = addOns;
	}

	public AddOnPaymentDetails getAddOnPaymentDetails() {
		return addOnPaymentDetails;
	}

	public void setAddOnPaymentDetails(AddOnPaymentDetails addOnPaymentDetails) {
		this.addOnPaymentDetails = addOnPaymentDetails;
	}

	public Parts getParts() {
		return parts;
	}

	public void setParts(Parts parts) {
		this.parts = parts;
	}

	public Pricing getPricing() {
		return pricing;
	}

	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}

	public Signature getProviderSignature() {
		return providerSignature;
	}

	public void setProviderSignature(Signature providerSignature) {
		this.providerSignature = providerSignature;
	}

	public Signature getCustomerSignature() {
		return customerSignature;
	}

	public void setCustomerSignature(Signature customerSignature) {
		this.customerSignature = customerSignature;
	}

	

	/*public Integer getCancellationOrRevisitReasonCode() {
		return cancellationOrRevisitReasonCode;
	}

	public void setCancellationOrRevisitReasonCode(
			Integer cancellationOrRevisitReasonCode) {
		this.cancellationOrRevisitReasonCode = cancellationOrRevisitReasonCode;
	}
*/

	
}
