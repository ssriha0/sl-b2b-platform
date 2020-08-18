package com.newco.marketplace.api.beans.so.create;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.so.Attachments;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing service order information for 
 * the SOCreateService
 * @author Infosys
 *
 */
@XStreamAlias("serviceorder")
public class ServiceOrderBean {
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version;	 
	
	@XStreamAlias("template")   
	@XStreamAsAttribute()   
	private String template;	
	
	@OptionalParam
	@XStreamAlias("sectionGeneral")
	private GeneralSection generalSection;
	
	@XStreamAlias("subStatus")
	private String subStatus;
	
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
	
	public GeneralSection getGeneralSection() {
		return generalSection;
	}

	public void setGeneralSection(GeneralSection generalSection) {
		this.generalSection = generalSection;
	}

	public ScopeOfWork getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(ScopeOfWork scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

}
