package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;


import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("LeadStatusUpdateRequest")
public class UpdateLeadStatusRequest {
	
	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation = PublicAPIConstant.LEAD_UPDATE_FIRM_STATUS_REQUEST_SCHEMALOCATION;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace = PublicAPIConstant.NEW_SERVICES_NAMESPACE;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("LeadId")
	private String leadId;

	@XStreamAlias("Status")
	private String status;
	
	@XStreamAlias("LeadFirmStatus")
	private LeadFirmStatus leadfirmStatus;
	
	@XStreamAlias("Reason")
	private String reason;
	
	@XStreamAlias("UserName")
	private String userName;
	
	@XStreamAlias("FullName")
	private String fullName;
	
	@XStreamAlias("EntityId")
	private Integer entityId;
	
	
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LeadFirmStatus getLeadfirmStatus() {
		return leadfirmStatus;
	}
	public void setLeadfirmStatus(LeadFirmStatus leadfirmStatus) {
		this.leadfirmStatus = leadfirmStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
}
