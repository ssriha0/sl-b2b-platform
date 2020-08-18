package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
  
   	
   	@XStreamOmitField
   	private String buyerId;

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


}
