package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.mobile.beans.sodetails.AddOnList;
import com.newco.marketplace.api.mobile.beans.sodetails.AlternateServiceLocation;
import com.newco.marketplace.api.mobile.beans.sodetails.Appointment;
import com.newco.marketplace.api.mobile.beans.sodetails.Buyer;
import com.newco.marketplace.api.mobile.beans.sodetails.BuyerReferences;
import com.newco.marketplace.api.mobile.beans.sodetails.Documents;
import com.newco.marketplace.api.mobile.beans.sodetails.GeneralSection;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.Merchandises;
import com.newco.marketplace.api.mobile.beans.sodetails.Notes;
import com.newco.marketplace.api.mobile.beans.sodetails.Parts;
import com.newco.marketplace.api.mobile.beans.sodetails.Provider;
import com.newco.marketplace.api.mobile.beans.sodetails.Scope;
import com.newco.marketplace.api.mobile.beans.sodetails.ServiceLocation;
import com.newco.marketplace.api.mobile.beans.sodetails.SupportNotes;
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
	
	@XStreamAlias("currentTripNo")
	private Integer currentTripNo;
	
	//SL-20673: Edit Completion Details
	@XStreamAlias("latestTrip")
	private LatestTripDetails latestTrip;

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
	
	@XStreamAlias("merchandises")
	private Merchandises merchandises;	
	
	@XStreamAlias("invoicePartsList")
	private InvoicePartsList invoicePartsList;

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
	
	@XStreamAlias("tripDetails")
	private SOTrips tripDetails;
	
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
	
	public Merchandises getMerchandises() {
		return merchandises;
	}

	public void setMerchandises(Merchandises merchandises) {
		this.merchandises = merchandises;
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

	/**
	 * @return the currentTripNo
	 */
	public Integer getCurrentTripNo() {
		return currentTripNo;
	}

	/**
	 * @param currentTripNo the currentTripNo to set
	 */
	public void setCurrentTripNo(Integer currentTripNo) {
		this.currentTripNo = currentTripNo;
	}

	/**
	 * @return the latestTrip
	 */
	public LatestTripDetails getLatestTrip() {
		return latestTrip;
	}

	/**
	 * @param latestTrip the latestTrip to set
	 */
	public void setLatestTrip(LatestTripDetails latestTrip) {
		this.latestTrip = latestTrip;
	}
	
	/**
	 * @return the tripDetails
	 */
	public SOTrips getTripDetails() {
		return tripDetails;
	}

	/**
	 * @param tripDetails the tripDetails to set
	 */
	public void setTripDetails(SOTrips tripDetails) {
		this.tripDetails = tripDetails;
	}

	/**
	 * @return the invoicePartsList
	 */
	public InvoicePartsList getInvoicePartsList() {
		return invoicePartsList;
	}

	/**
	 * @param invoicePartsList the invoicePartsList to set
	 */
	public void setInvoicePartsList(InvoicePartsList invoicePartsList) {
		this.invoicePartsList = invoicePartsList;
	}
}
