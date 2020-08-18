package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.mobile.beans.sodetails.AddOns;
import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.api.mobile.beans.sodetails.Appointment;
import com.newco.marketplace.api.mobile.beans.sodetails.CancellationReasonCodes;
import com.newco.marketplace.api.mobile.beans.sodetails.CompletionDocuments;
import com.newco.marketplace.api.mobile.beans.sodetails.CompletionStatus;
import com.newco.marketplace.api.mobile.beans.sodetails.DocumentTypes;
import com.newco.marketplace.api.mobile.beans.sodetails.PartsTracking;
import com.newco.marketplace.api.mobile.beans.sodetails.Permits;
import com.newco.marketplace.api.mobile.beans.sodetails.Price;
import com.newco.marketplace.api.mobile.beans.sodetails.ProviderReferences;
import com.newco.marketplace.api.mobile.beans.sodetails.ServiceLocation;
import com.newco.marketplace.api.mobile.beans.sodetails.SignatureDocuments;
import com.newco.marketplace.api.mobile.beans.sodetails.Tasks;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing service order completion details.
 * @author Infosys
 *
 */

@XStreamAlias("completionDetails")
@XmlRootElement(name = "completionDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompletionDetails {
	
	@XStreamOmitField
	private Appointment appointment;
	
	@XStreamAlias("completionStatus")
	private CompletionStatus completionStatus;
	
	@XStreamAlias("currentTripNo")
	private Integer currentTripNo;
	
	//SL-20673: Edit Completion Details
	@XStreamAlias("latestTrip")
	private LatestTripDetails latestTrip;
	
	@XStreamAlias("resolutionComments")
	private String resolutionComments;
	
	@XStreamAlias("cancellationReasonCodes")
	private CancellationReasonCodes cancellationReasonCodes;
	
	@XStreamAlias("tasks")
	private Tasks tasks;
    
   	@XStreamAlias("price")
   	private Price price; 
    
   	@XStreamAlias("providerRefPresentInd")
   	private String providerRefPresentInd;
   	
   	@XStreamAlias("providerReferences")
   	private ProviderReferences providerReferences;
    
   	@XStreamAlias("addons")
   	private AddOns addons;	
	
   	@XStreamAlias("permits")   	
   	private Permits permits;
   	
   	@XStreamAlias("partsTracking")
   	private PartsTracking partsTracking;
    
	@XStreamAlias("documentTypes")
	private DocumentTypes documentTypes;
	   
   	@XStreamAlias("invoiceParts")
   	private InvoiceParts invoiceParts;
   	
   	@XStreamAlias("documents")
	private CompletionDocuments documents;
   	
   	//R12_0 Sprint 5: SLM 90 
   	@XStreamOmitField
   	private Boolean buyerEmailRequiredInd;
   	
   	@XStreamAlias("customerEmailRequired")
   	private String customerEmailRequired;
   	
   	@XStreamAlias("signatures")
	private SignatureDocuments signatures;
   	
   	@XStreamOmitField
   	private String buyerId;
   	
	@XStreamAlias("addonPayment")
	private AddonPayment addonPayment;
	
	//SL-20673: Edit Completion Details
	@XStreamAlias("tripDetails")
	private SOTrips tripDetails;
   	
	//SL-20673: Edit Completion Details
	@XStreamOmitField
	private ServiceLocation serviceLocation;
	
	
	public DocumentTypes getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(DocumentTypes documentTypes) {
		this.documentTypes = documentTypes;
	}

	/**
	 * @return the providerRefPresentInd
	 */
	public String getProviderRefPresentInd() {
		return providerRefPresentInd;
	}

	/**
	 * @param providerRefPresentInd the providerRefPresentInd to set
	 */
	public void setProviderRefPresentInd(String providerRefPresentInd) {
		this.providerRefPresentInd = providerRefPresentInd;
	}

	public AddOns getAddons() {
		return addons;
	}

	public void setAddons(AddOns addons) {
		this.addons = addons;
	}

	/**
	 * @return the price
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return the permits
	 */
	public Permits getPermits() {
		return permits;
	}

	/**
	 * @param permits the permits to set
	 */
	public void setPermits(Permits permits) {
		this.permits = permits;
	}

	/**
	 * @return the partsTracking
	 */
	public PartsTracking getPartsTracking() {
		return partsTracking;
	}

	/**
	 * @param partsTracking the partsTracking to set
	 */
	public void setPartsTracking(PartsTracking partsTracking) {
		this.partsTracking = partsTracking;
	}

	/**
	 * @return the invoiceParts
	 */
	public InvoiceParts getInvoiceParts() {
		return invoiceParts;
	}

	/**
	 * @param invoiceParts the invoiceParts to set
	 */
	public void setInvoiceParts(InvoiceParts invoiceParts) {
		this.invoiceParts = invoiceParts;
	}

	/**
	 * @return the tasks
	 */
	public Tasks getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the providerReferences
	 */
	public ProviderReferences getProviderReferences() {
		return providerReferences;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public CancellationReasonCodes getCancellationReasonCodes() {
		return cancellationReasonCodes;
	}

	public void setCancellationReasonCodes(
			CancellationReasonCodes cancellationReasonCodes) {
		this.cancellationReasonCodes = cancellationReasonCodes;
	}

	/**
	 * @param providerReferences the providerReferences to set
	 */
	public void setProviderReferences(ProviderReferences providerReferences) {
		this.providerReferences = providerReferences;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public CompletionStatus getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(CompletionStatus completionStatus) {
		this.completionStatus = completionStatus;
	}

	public CompletionDocuments getDocuments() {
		return documents;
	}

	public void setDocuments(CompletionDocuments documents) {
		this.documents = documents;
	}

	public SignatureDocuments getSignatures() {
		return signatures;
	}

	public void setSignatures(SignatureDocuments signatures) {
		this.signatures = signatures;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public AddonPayment getAddonPayment() {
		return addonPayment;
	}

	public void setAddonPayment(AddonPayment addonPayment) {
		this.addonPayment = addonPayment;
	}

	public Boolean getBuyerEmailRequiredInd() {
		return buyerEmailRequiredInd;
	}

	public void setBuyerEmailRequiredInd(Boolean buyerEmailRequiredInd) {
		this.buyerEmailRequiredInd = buyerEmailRequiredInd;
	}

	public String getCustomerEmailRequired() {
		return customerEmailRequired;
	}

	public void setCustomerEmailRequired(String customerEmailRequired) {
		this.customerEmailRequired = customerEmailRequired;
	}

	public SOTrips getTripDetails() {
		return tripDetails;
	}

	public void setTripDetails(SOTrips tripDetails) {
		this.tripDetails = tripDetails;
	}

	public LatestTripDetails getLatestTrip() {
		return latestTrip;
	}

	public void setLatestTrip(LatestTripDetails latestTrip) {
		this.latestTrip = latestTrip;
	}

	public ServiceLocation getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(ServiceLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public Integer getCurrentTripNo() {
		return currentTripNo;
	}

	public void setCurrentTripNo(Integer currentTripNo) {
		this.currentTripNo = currentTripNo;
	}

}
