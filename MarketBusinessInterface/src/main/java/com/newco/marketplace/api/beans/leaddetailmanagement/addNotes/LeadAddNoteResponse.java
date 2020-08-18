package com.newco.marketplace.api.beans.leaddetailmanagement.addNotes;


import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the AddNotesForLeadService
 * @author Infosys
 *
 */

@XStreamAlias("leadAddNoteResponse")
public class LeadAddNoteResponse implements IAPIResponse{
	
	
	 @XStreamAlias("version")   
	 @XStreamAsAttribute()   
	private String version=PublicAPIConstant.SORESPONSE_VERSION;
	 
	 @XStreamAlias("xsi:schemaLocation")   
	 @XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.LEAD_ADDNOTE_RESPONSE_SCHEMALOCATION;
	 
	 @XStreamAlias("xmlns")   
	 @XStreamAsAttribute()   
	private String namespace=PublicAPIConstant.LEAD_ADDNOTE_RESPONSE_NAMESPACE;
	 
	 @XStreamAlias("xmlns:xsi")   
	 @XStreamAsAttribute()   
	private String schemaInstance=PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("leadId")
	private String leadId;
	
	@XStreamAlias("leadNoteId")
	private Integer leadNoteId;
	
   public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
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

	public Integer getLeadNoteId() {
		return leadNoteId;
	}

	public void setLeadNoteId(Integer leadNoteId) {
		this.leadNoteId = leadNoteId;
	}

	



}
