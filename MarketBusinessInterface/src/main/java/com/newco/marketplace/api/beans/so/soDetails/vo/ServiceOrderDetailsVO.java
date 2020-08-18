package com.newco.marketplace.api.beans.so.soDetails.vo;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;

public class ServiceOrderDetailsVO {

	private Integer currentTripNo;
	private LatestTripVO latestTrip;
	private OrderDetailsVO orderDetails;	
	private AppointmentVO appointment;	
	private ScheduleServiceSlot scheduleServiceSlot;
	private BuyerVO buyer;	
	private ProviderVO provider;		
	private ServiceLocationVO serviceLocation;
	private AlternateServiceLocationVO alternateServiceLocation;
	private ScopeVO scope;	
	private AddOnListVO addonList;
	private PartDetailVO parts;
	private MerchandisesVO merchandises;		
	private InvoicePartsListVO invoicePartsList;
	private DocumentsVO documents;
	private String buyerRefPresentInd;	
	private BuyerReferencesVO buyerReferences;	
	private NotesVO notes;	
	private SupportNotesVO supportNotes;
	private SOTripsVO tripDetails;
	private boolean followupFlag;
	
	private List<ProviderResultVO> counteredResourceDetailsList = new ArrayList<ProviderResultVO>();
	
	public boolean isFollowupFlag() {
		return followupFlag;
	}
	public void setFollowupFlag(boolean followupFlag) {
		this.followupFlag = followupFlag;
	}
	public Integer getCurrentTripNo() {
		return currentTripNo;
	}
	public void setCurrentTripNo(Integer currentTripNo) {
		this.currentTripNo = currentTripNo;
	}
	public OrderDetailsVO getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderDetailsVO orderDetails) {
		this.orderDetails = orderDetails;
	}
	public AppointmentVO getAppointment() {
		return appointment;
	}
	public void setAppointment(AppointmentVO appointment) {
		this.appointment = appointment;
	}
	public BuyerVO getBuyer() {
		return buyer;
	}
	public void setBuyer(BuyerVO buyer) {
		this.buyer = buyer;
	}
	public ProviderVO getProvider() {
		return provider;
	}
	public void setProvider(ProviderVO provider) {
		this.provider = provider;
	}
	public ServiceLocationVO getServiceLocation() {
		return serviceLocation;
	}
	public void setServiceLocation(ServiceLocationVO serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	public AlternateServiceLocationVO getAlternateServiceLocation() {
		return alternateServiceLocation;
	}
	public void setAlternateServiceLocation(
			AlternateServiceLocationVO alternateServiceLocation) {
		this.alternateServiceLocation = alternateServiceLocation;
	}
	public ScopeVO getScope() {
		return scope;
	}
	public void setScope(ScopeVO scope) {
		this.scope = scope;
	}
	public AddOnListVO getAddonList() {
		return addonList;
	}
	public void setAddonList(AddOnListVO addonList) {
		this.addonList = addonList;
	}
	public PartDetailVO getParts() {
		return parts;
	}
	public void setParts(PartDetailVO parts) {
		this.parts = parts;
	}
	public MerchandisesVO getMerchandises() {
		return merchandises;
	}
	public void setMerchandises(MerchandisesVO merchandises) {
		this.merchandises = merchandises;
	}
	public InvoicePartsListVO getInvoicePartsList() {
		return invoicePartsList;
	}
	public void setInvoicePartsList(InvoicePartsListVO invoicePartsList) {
		this.invoicePartsList = invoicePartsList;
	}
	public DocumentsVO getDocuments() {
		return documents;
	}
	public void setDocuments(DocumentsVO documents) {
		this.documents = documents;
	}
	public String getBuyerRefPresentInd() {
		return buyerRefPresentInd;
	}
	public void setBuyerRefPresentInd(String buyerRefPresentInd) {
		this.buyerRefPresentInd = buyerRefPresentInd;
	}
	public BuyerReferencesVO getBuyerReferences() {
		return buyerReferences;
	}
	public void setBuyerReferences(BuyerReferencesVO buyerReferences) {
		this.buyerReferences = buyerReferences;
	}
	public NotesVO getNotes() {
		return notes;
	}
	public void setNotes(NotesVO notes) {
		this.notes = notes;
	}
	public SupportNotesVO getSupportNotes() {
		return supportNotes;
	}
	public void setSupportNotes(SupportNotesVO supportNotes) {
		this.supportNotes = supportNotes;
	}
	public SOTripsVO getTripDetails() {
		return tripDetails;
	}
	public void setTripDetails(SOTripsVO tripDetails) {
		this.tripDetails = tripDetails;
	}
	public LatestTripVO getLatestTrip() {
		return latestTrip;
	}
	public void setLatestTrip(LatestTripVO latestTrip) {
		this.latestTrip = latestTrip;
	}
	public List<ProviderResultVO> getCounteredResourceDetailsList() {
		return counteredResourceDetailsList;
	}
	public void setCounteredResourceDetailsList(
			List<ProviderResultVO> counteredResourceDetailsList) {
		this.counteredResourceDetailsList = counteredResourceDetailsList;
	}
	public ScheduleServiceSlot getScheduleServiceSlot() {
		return scheduleServiceSlot;
	}
	public void setScheduleServiceSlot(ScheduleServiceSlot scheduleServiceSlot) {
		this.scheduleServiceSlot = scheduleServiceSlot;
	}
	
	

}
