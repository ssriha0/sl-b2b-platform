package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This is a generic bean class for storing service order details.
 * @author Infosys
 *
 */
@XStreamAlias("soDetails")
@XmlRootElement(name = "soDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceOrderDetails {
	
	@XStreamAlias("orderDetails")
	private GeneralSection orderDetails;	
	
	@XStreamAlias("appointment")
	private Appointment appointment;	
		
	@XStreamAlias("buyer")
	private Buyer buyer;	
	
	@XStreamAlias("provider")
	private Provider provider;	
	
	@XStreamAlias("serviceLocation")
	private ServiceLocation serviceLocation;
	
	@XStreamAlias("alternateServiceLocation")
	private AlternateServiceLocation alternateServiceLocation;
	
	@XStreamAlias("scope")
	private Scope scope;	
	
	@XStreamAlias("addonList")
	private AddOnList addonList;
	
	@XStreamAlias("parts")
	private Parts parts;	

	@XStreamAlias("documents")
	private Documents documents;

	@XStreamAlias("buyerRefPresentInd")
	private String buyerRefPresentInd;	

	@XStreamAlias("buyerReferences")
	private BuyerReferences buyerReferences;	

	@XStreamAlias("notes")
	private Notes notes;	

	@XStreamAlias("supportNotes")
	private SupportNotes supportNotes;
	

	public GeneralSection getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(GeneralSection orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public ServiceLocation getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(ServiceLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public AlternateServiceLocation getAlternateServiceLocation() {
		return alternateServiceLocation;
	}

	public void setAlternateServiceLocation(
			AlternateServiceLocation alternateServiceLocation) {
		this.alternateServiceLocation = alternateServiceLocation;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Parts getParts() {
		return parts;
	}

	public void setParts(Parts parts) {
		this.parts = parts;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

	public String getBuyerRefPresentInd() {
		return buyerRefPresentInd;
	}

	public void setBuyerRefPresentInd(String buyerRefPresentInd) {
		this.buyerRefPresentInd = buyerRefPresentInd;
	}

	public BuyerReferences getBuyerReferences() {
		return buyerReferences;
	}

	public void setBuyerReferences(BuyerReferences buyerReferences) {
		this.buyerReferences = buyerReferences;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}

	public SupportNotes getSupportNotes() {
		return supportNotes;
	}

	public void setSupportNotes(SupportNotes supportNotes) {
		this.supportNotes = supportNotes;
	}

	public AddOnList getAddonList() {
		return addonList;
	}

	public void setAddonList(AddOnList addonList) {
		this.addonList = addonList;
	}
}
