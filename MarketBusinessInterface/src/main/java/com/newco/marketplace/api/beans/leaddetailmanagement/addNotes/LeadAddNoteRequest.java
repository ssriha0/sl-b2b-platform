package com.newco.marketplace.api.beans.leaddetailmanagement.addNotes;


import org.codehaus.xfire.aegis.type.java5.XmlType;

import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the AddNotesForLeadService
 * @author Infosys
 *
 */

@XStreamAlias("leadAddNoteRequest")
public class LeadAddNoteRequest{
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.LEAD_ADDNOTE_REQUEST_SCHEMALOCATION;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.LEAD_ADDNOTE_REQUEST_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("vendorBuyerId")
	private Integer vendorBuyerId;
	
	@XStreamAlias("vendorBuyerResourceId")
	private Integer vendorBuyerResourceId;
	
	@XStreamAlias("role")
	private String role;
	
	@XStreamAlias("leadId")
	private String leadId;
	
	@XStreamAlias("leadNoteId")
	private Integer leadNoteId;


	@XStreamAlias("leadNote")
	private LeadNoteType leadNote;

	@XStreamAlias("emailAlert")
	private EmailAlertType emailAlert;
	
	@XStreamAlias("leadEmailTitle")
	private String leadEmailTitle;
	
	@XStreamAlias("leadEmailAuthor")
	private String leadEmailAuthor;
	
	@XStreamAlias("leadName")
	private String leadName;
	
	@XStreamAlias("leadPhone")
	private String leadPhone;
	
	//for mail
	private String lmsLeadId; 
	public String getLeadEmailAuthor() {
		return leadEmailAuthor;
	}

	public void setLeadEmailAuthor(String leadEmailAuthor) {
		this.leadEmailAuthor = leadEmailAuthor;
	}

	public String getLeadPhone() {
		return leadPhone;
	}

	public void setLeadPhone(String leadPhone) {
		this.leadPhone = leadPhone;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public String getLeadEmailTitle() {
		return leadEmailTitle;
	}

	public void setLeadEmailTitle(String leadEmailTitle) {
		this.leadEmailTitle = leadEmailTitle;
	}

	public LeadNoteType getLeadNote() {
		return leadNote;
	}

	public void setLeadNote(LeadNoteType leadNote) {
		this.leadNote = leadNote;
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


	public Integer getVendorBuyerId() {
		return vendorBuyerId;
	}

	public void setVendorBuyerId(Integer vendorBuyerId) {
		this.vendorBuyerId = vendorBuyerId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public EmailAlertType getEmailAlert() {
		return emailAlert;
	}

	public void setEmailAlert(EmailAlertType emailAlert) {
		this.emailAlert = emailAlert;
	}

	public Integer getLeadNoteId() {
		return leadNoteId;
	}

	public void setLeadNoteId(Integer leadNoteId) {
		this.leadNoteId = leadNoteId;
	}

	public Integer getVendorBuyerResourceId() {
		return vendorBuyerResourceId;
	}

	public String getLmsLeadId() {
		return lmsLeadId;
	}

	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}

	public void setVendorBuyerResourceId(Integer vendorBuyerResourceId) {
		this.vendorBuyerResourceId = vendorBuyerResourceId;
	}
	
	
}
