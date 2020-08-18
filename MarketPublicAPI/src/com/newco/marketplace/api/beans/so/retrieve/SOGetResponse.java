package com.newco.marketplace.api.beans.so.retrieve;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.AttachmentType;
import com.newco.marketplace.api.beans.so.Attachments;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.History;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.Notes;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.RoutedProviders;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing response information for 
 * the SORetrieveService
 * @author Infosys
 *
 */
@XStreamAlias("soResponse")
public class SOGetResponse implements IAPIResponse {
	
	
	 @XStreamAlias("version")   
	 @XStreamAsAttribute()   
	private String version;
	 
	 @XStreamAlias("xsi:schemaLocation")   
	 @XStreamAsAttribute()   
	private String schemaLocation;
	 
	 @XStreamAlias("xmlns")   
	 @XStreamAsAttribute()   
	private String namespace;
	 
	 @XStreamAlias("xmlns:xsi")   
	 @XStreamAsAttribute()   
	private String schemaInstance;
	
	
	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("orderstatus")
	private OrderStatus orderstatus;

	@XStreamAlias("sectionGeneral")
	private GeneralSection sectionGeneral;
	
	@XStreamAlias("scopeOfWork")
	private ScopeOfWork scopeOfWork;

	@XStreamAlias("serviceLocation")
	private Location serviceLocation;

	@XStreamAlias("schedule")
	private Schedule schedule;
	
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
	

	public SOGetResponse () {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicAPIConstant.SORESPONSE_NAMESPACE;
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

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Location getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(Location serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
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

	public ScopeOfWork getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(ScopeOfWork scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public AttachmentType getAttachments() {
		return attachments;
	}

	public void setAttachments(AttachmentType attachments) {
		this.attachments = attachments;
	}

}
