package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("CancelLeadRequest")
public class CancelLeadRequest {

	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.CANCEL_LEAD_REQUEST_SCHEMALOCATION;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.NEW_SERVICES_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	
	@XStreamAlias("LeadId")
	private String leadId;

	@XStreamAlias("ReasonCode")
	private int reasonCode;

	@XStreamAlias("Comments")
	private String comments;

	@XStreamAlias("Providers")
	private String providers;

	@XStreamAlias("RevokePointsInd")
	private boolean revokePointsIndicator;
	
	@XStreamAlias("ChkAllProviderInd")
	private boolean chkAllProviderInd;
	
	@XStreamAlias("vendorBuyerName")
	private String vendorBuyerName;
	
	@XStreamAlias("RoleId")
	private int roleId;

	@XStreamAlias("cancelInitiatedBy")
	private String cancelInitiatedBy;
	
	@XStreamAlias("createdBy")
	private String createdBy;
	
	@XStreamAlias("modifiedBy")
	private String modifiedBy;
	
	@XStreamAlias("entityId")
	private Integer entityId;
	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	// Result of validation
	private ResultsCode validationCode;

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getProviders() {
		return providers;
	}

	public void setProviders(String providers) {
		this.providers = providers;
	}

	public boolean isRevokePointsIndicator() {
		return revokePointsIndicator;
	}

	public void setRevokePointsIndicator(boolean revokePointsIndicator) {
		this.revokePointsIndicator = revokePointsIndicator;
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

	public boolean isChkAllProviderInd() {
		return chkAllProviderInd;
	}

	public void setChkAllProviderInd(boolean chkAllProviderInd) {
		this.chkAllProviderInd = chkAllProviderInd;
	}

	

	public String getVendorBuyerName() {
		return vendorBuyerName;
	}

	public void setVendorBuyerName(String vendorBuyerName) {
		this.vendorBuyerName = vendorBuyerName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getCancelInitiatedBy() {
		return cancelInitiatedBy;
	}

	public void setCancelInitiatedBy(String cancelInitiatedBy) {
		this.cancelInitiatedBy = cancelInitiatedBy;
	}
	
	
	
	

}
