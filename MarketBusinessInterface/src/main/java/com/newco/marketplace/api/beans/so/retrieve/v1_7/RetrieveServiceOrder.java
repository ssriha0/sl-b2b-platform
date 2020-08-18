package com.newco.marketplace.api.beans.so.retrieve.v1_7;


import com.newco.marketplace.api.annotation.OptionalParam;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.AttachmentType;
import com.newco.marketplace.api.beans.so.Buyer;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.Estimates;
import com.newco.marketplace.api.beans.so.FirmContact;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.History;
import com.newco.marketplace.api.beans.so.InvoiceParts;
import com.newco.marketplace.api.beans.so.JobCodes;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.Notes;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.ReasonCodes;
import com.newco.marketplace.api.beans.so.RoutedProviders;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.ServiceOrderReview;

import com.newco.marketplace.api.beans.so.PaymentDetails;
import com.newco.marketplace.dto.vo.addons.AddOns;
import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.newco.marketplace.dto.vo.serviceorder.ProductDetailVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceOrder")
public class RetrieveServiceOrder {
	
	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("orderstatus")
	private OrderStatus orderstatus;
	
	@XStreamAlias("buyer")
	private Buyer buyer;

	@XStreamAlias("sectionGeneral")
	private GeneralSection sectionGeneral;
	
	@XStreamAlias("scopeOfWork")
	private ScopeOfWork scopeOfWork;

	@XStreamAlias("serviceLocation")
	private Location serviceLocation;

	@XStreamAlias("schedule")
	private Schedule schedule;
	
	@XStreamAlias("scheduleServiceSlot")
	private ScheduleServiceSlot scheduleServiceSlot;
	
	@XStreamAlias("pricing")
	private Pricing pricing;
	
	@XStreamAlias("contacts")
	private Contacts contacts;
	
	@XStreamAlias("attachments")
	private AttachmentType attachments;
	
	@XStreamAlias("parts")
	private Parts parts;
	
	@XStreamAlias("customReferences")
	private CustomReferences customReferences;
	
	@XStreamAlias("notes")
	private Notes notes;
	
	@XStreamAlias("history")
	private History history;
	
	@XStreamAlias("routedProviders")
	private RoutedProviders routedProviders;
	
	//SL-19206 Adding Accepted Provider Firm Contact
	@XStreamAlias("acceptedProviderFirmContact")
	private FirmContact acceptedProviderFirmContact;	
	

	@OptionalParam
	@XStreamAlias("reasonCodes")
	private ReasonCodes reasonCodes;
	
	@OptionalParam
	@XStreamAlias("customerResponseCodes")
	private ReasonCodes customerResponseCodes;
	
	@OptionalParam
	@XStreamAlias("preCallReasonCodes")
	private ReasonCodes preCallReasonCodes;
	
	@OptionalParam
	@XStreamAlias("product")
	private ProductDetailVO product;
	
	@XStreamAlias("estimateFlag")
	private Boolean estimateFlag;
	
	@XStreamAlias("estimates")
	private Estimates estimates;
	
	@XStreamAlias("review")
	private ServiceOrderReview review;
	
	@XStreamAlias("addons")
	private AddOns addOns;
	
	@XStreamAlias("jobCodes")
	private JobCodes jobCodes;
	
	@XStreamAlias("paymentDetails")
	private PaymentDetails PaymentDetails;
	
	@XStreamAlias("invoiceParts")
   	private InvoiceParts invoiceParts;

	public ProductDetailVO getProduct() {
		return product;
	}

	public void setProduct(ProductDetailVO product) {
		this.product = product;
	}

	public ReasonCodes getCustomerResponseCodes() {
		return customerResponseCodes;
	}

	public void setCustomerResponseCodes(ReasonCodes customerResponseCodes) {
		this.customerResponseCodes = customerResponseCodes;
	}

	public ReasonCodes getPreCallReasonCodes() {
		return preCallReasonCodes;
	}

	public void setPreCallReasonCodes(ReasonCodes preCallReasonCodes) {
		this.preCallReasonCodes = preCallReasonCodes;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public OrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}

	public GeneralSection getSectionGeneral() {
		return sectionGeneral;
	}

	public void setSectionGeneral(GeneralSection sectionGeneral) {
		this.sectionGeneral = sectionGeneral;
	}

	public ScopeOfWork getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(ScopeOfWork scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public Location getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(Location serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Pricing getPricing() {
		return pricing;
	}

	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	public AttachmentType getAttachments() {
		return attachments;
	}

	public void setAttachments(AttachmentType attachments) {
		this.attachments = attachments;
	}

	public Parts getParts() {
		return parts;
	}

	public void setParts(Parts parts) {
		this.parts = parts;
	}

	public CustomReferences getCustomReferences() {
		return customReferences;
	}

	public void setCustomReferences(CustomReferences customReferences) {
		this.customReferences = customReferences;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public RoutedProviders getRoutedProviders() {
		return routedProviders;
	}

	public void setRoutedProviders(RoutedProviders routedProviders) {
		this.routedProviders = routedProviders;
	}

	public ReasonCodes getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(ReasonCodes reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	public FirmContact getAcceptedProviderFirmContact() {
		return acceptedProviderFirmContact;
	}

	public void setAcceptedProviderFirmContact(
			FirmContact acceptedProviderFirmContact) {
		this.acceptedProviderFirmContact = acceptedProviderFirmContact;
	}	

public Boolean isEstimateFlag() {
		return estimateFlag;
	}

	public void setEstimateFlag(Boolean estimateFlag) {
		this.estimateFlag = estimateFlag;
	}

	public Estimates getEstimates() {
		return estimates;
	}

	public void setEstimates(Estimates estimates) {
		this.estimates = estimates;
	}

	public ServiceOrderReview getReview() {
		return review;
	}

	public void setReview(ServiceOrderReview review) {
		this.review = review;
	}

	public ScheduleServiceSlot getScheduleServiceSlot() {
		return scheduleServiceSlot;
	}

	public void setScheduleServiceSlot(ScheduleServiceSlot scheduleServiceSlot) {
		this.scheduleServiceSlot = scheduleServiceSlot;
	}

	public AddOns getAddOns() {
		return addOns;
	}

	public void setAddOns(AddOns addOns) {
		this.addOns = addOns;
	}

	public JobCodes getJobCodes() {
		return jobCodes;
	}

	public void setJobCodes(JobCodes jobCodes) {
		this.jobCodes = jobCodes;
	}

	public PaymentDetails getPaymentDetails() {
		return PaymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		PaymentDetails = paymentDetails;
	}

	public InvoiceParts getInvoiceParts() {
		return invoiceParts;
	}

	public void setInvoiceParts(InvoiceParts invoiceParts) {
		this.invoiceParts = invoiceParts;
	}

	
}
