package com.newco.marketplace.api.beans.so.providerRetrieve;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.Attachments;
import com.newco.marketplace.api.beans.so.Buyer;
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
@XStreamAlias("soProviderResponse")
public class SOProviderRetrieveResponse implements IAPIResponse {
	
	
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

	@OptionalParam
	@XStreamAlias("orderstatus")
	private OrderStatus orderstatus;

	@OptionalParam
	@XStreamAlias("sectionGeneral")
	private GeneralSection sectionGeneral;
	
	@OptionalParam
	@XStreamAlias("scopeOfWork")
	private ScopeOfWork scopeOfWork;

	@OptionalParam
	@XStreamAlias("serviceLocation")
	private Location serviceLocation;

	@OptionalParam
	@XStreamAlias("schedule")
	private Schedule schedule;
	
	@OptionalParam
	@XStreamAlias("pricing")
	private Pricing pricing;
	
	@OptionalParam
	@XStreamAlias("contacts")
	private Contacts contacts;
	
	@OptionalParam
	@XStreamAlias("attachments")
	private Attachments attachments;
	
	@OptionalParam
	@XStreamAlias("parts")
	private Parts parts;
	
	@OptionalParam
	@XStreamAlias("customReferences")
	private CustomReferences customReferences;
	
	@OptionalParam
	@XStreamAlias("notes")
	private Notes notes;
	
	@OptionalParam
	@XStreamAlias("history")
	private History history;
	
	@OptionalParam
	@XStreamAlias("buyer")
	private Buyer buyer;
	
	public SOProviderRetrieveResponse () {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicAPIConstant.providerRetrieveSO.SORESPONSE_NAMESPACE;
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

	public Attachments getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachments attachments) {
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

	public ScopeOfWork getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(ScopeOfWork scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public Buyer getBuyer() {
		return buyer;
}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
	

}
