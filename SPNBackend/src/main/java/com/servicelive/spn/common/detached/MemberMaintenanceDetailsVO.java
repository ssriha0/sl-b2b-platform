/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hoza
 *
 */
public class MemberMaintenanceDetailsVO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4317433894853677125L;
	
	private Integer spnId;
	private Boolean exceptionInd;
	private Integer credentialId;
	private Integer vendorId;
	private Integer resourceId;
	private Integer credTypeId;
	private Integer credCategoryId;
	private Integer spnApprovalCriteriaId;
	private Integer wfStateId;
	private String credentialType;
	private Integer  exceptionTypeId;
	private String exceptionValue;
	private Boolean activeInd;
	private String credState;
	private Date credExpiryDate;
	private String modifiedBy; 
	private String wfState;
	private String firmCredentialStatus="";

	private BigDecimal minimumRating;
	private Integer completedSo;
	private String criteriaValue;
	private String criteriaDescription;
	private String languageList;
	private Integer criteriaId;
	
	private Integer complianceId;
	private Integer luSpnApprovalCriteriaId;
	private String spnCriteriaValue;
	private String criteriaValueName;

	
	
	
	
	private String criteriaValueList;
	private String serviceTypeList;
	private String nodeIdList;
	
	
	private Boolean liabilityInd;
	private Double amount;
	private String exceptionTypeIdApplied;
	private Boolean workComplInd;  
	
	/* R11.0 SL-19387*/	
	private String backGroundState;
	private String overall;
	private Date recertificationDate;
	private Date backgroundRequestDate;
	private String backgroundRequestType;
	
	/* R11.0 CR SL-20289*/	
	private String slWfStateId;
	
	private String recertBeforeExpiry;
	

	public String getRecertBeforeExpiry() {
		return recertBeforeExpiry;
	}
	public void setRecertBeforeExpiry(String recertBeforeExpiry) {
		this.recertBeforeExpiry = recertBeforeExpiry;
	}
	public String getSlWfStateId() {
		return slWfStateId;
	}
	public void setSlWfStateId(String slWfStateId) {
		this.slWfStateId = slWfStateId;
	}
	public String getBackgroundRequestType() {
		return backgroundRequestType;
	}
	public void setBackgroundRequestType(String backgroundRequestType) {
		this.backgroundRequestType = backgroundRequestType;
	}
	public Date getBackgroundRequestDate() {
		return backgroundRequestDate;
	}
	public void setBackgroundRequestDate(Date backgroundRequestDate) {
		this.backgroundRequestDate = backgroundRequestDate;
	}
	
	public String getBackGroundState() {
		return backGroundState;
	}
	public void setBackGroundState(String backGroundState) {
		this.backGroundState = backGroundState;
	}
	public String getOverall() {
		return overall;
	}
	public void setOverall(String overall) {
		this.overall = overall;
	}
	public Date getRecertificationDate() {
		return recertificationDate;
	}
	public void setRecertificationDate(Date recertificationDate) {
		this.recertificationDate = recertificationDate;
	}
	public Boolean getWorkComplInd() {
		return workComplInd;
	}
	public void setWorkComplInd(Boolean workComplInd) {
		this.workComplInd = workComplInd;
	}
	public String getCriteriaValueName() {
		return criteriaValueName;
	}
	public void setCriteriaValueName(String criteriaValueName) {
		this.criteriaValueName = criteriaValueName;
	}
	public String getSpnCriteriaValue() {
		return spnCriteriaValue;
	}
	public void setSpnCriteriaValue(String spnCriteriaValue) {
		this.spnCriteriaValue = spnCriteriaValue;
	}
	public String getExceptionTypeIdApplied() {
		return exceptionTypeIdApplied;
	}
	public void setExceptionTypeIdApplied(String exceptionTypeIdApplied) {
		this.exceptionTypeIdApplied = exceptionTypeIdApplied;
	}
	public Boolean getLiabilityInd() {
		return liabilityInd;
	}
	public void setLiabilityInd(Boolean liabilityInd) {
		this.liabilityInd = liabilityInd;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCriteriaValueList() {
		return criteriaValueList;
	}
	public void setCriteriaValueList(String criteriaValueList) {
		this.criteriaValueList = criteriaValueList;
	}
	public String getServiceTypeList() {
		return serviceTypeList;
	}
	public void setServiceTypeList(String serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}
	public String getNodeIdList() {
		return nodeIdList;
	}
	public void setNodeIdList(String nodeIdList) {
		this.nodeIdList = nodeIdList;
	}
	public Integer getComplianceId() {
		return complianceId;
	}
	public void setComplianceId(Integer complianceId) {
		this.complianceId = complianceId;
	}
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Boolean getExceptionInd() {
		return exceptionInd;
	}
	public void setExceptionInd(Boolean exceptionInd) {
		this.exceptionInd = exceptionInd;
	}
	public Integer getCredentialId() {
		return credentialId;
	}
	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getCredTypeId() {
		return credTypeId;
	}
	public void setCredTypeId(Integer credTypeId) {
		this.credTypeId = credTypeId;
	}
	public Integer getCredCategoryId() {
		return credCategoryId;
	}
	public void setCredCategoryId(Integer credCategoryId) {
		this.credCategoryId = credCategoryId;
	}
	public Integer getSpnApprovalCriteriaId() {
		return spnApprovalCriteriaId;
	}
	public void setSpnApprovalCriteriaId(Integer spnApprovalCriteriaId) {
		this.spnApprovalCriteriaId = spnApprovalCriteriaId;
	}
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public String getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	public Integer getExceptionTypeId() {
		return exceptionTypeId;
	}
	public void setExceptionTypeId(Integer exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}
	public String getExceptionValue() {
		return exceptionValue;
	}
	public void setExceptionValue(String exceptionValue) {
		this.exceptionValue = exceptionValue;
	}
	public Boolean getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}
	public String getCredState() {
		return credState;
	}
	public void setCredState(String credState) {
		this.credState = credState;
	}
	public Date getCredExpiryDate() {
		return credExpiryDate;
	}
	public void setCredExpiryDate(Date credExpiryDate) {
		this.credExpiryDate = credExpiryDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getWfState() {
		return wfState;
	}
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
	public String getFirmCredentialStatus() {
		return firmCredentialStatus;
	}
	public void setFirmCredentialStatus(String firmCredentialStatus) {
		this.firmCredentialStatus = firmCredentialStatus;
	}
	public BigDecimal getMinimumRating() {
		return minimumRating;
	}
	public void setMinimumRating(BigDecimal minimumRating) {
		this.minimumRating = minimumRating;
	}
	public Integer getCompletedSo() {
		return completedSo;
	}
	public void setCompletedSo(Integer completedSo) {
		this.completedSo = completedSo;
	}
	public String getCriteriaValue() {
		return criteriaValue;
	}
	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	public String getCriteriaDescription() {
		return criteriaDescription;
	}
	public void setCriteriaDescription(String criteriaDescription) {
		this.criteriaDescription = criteriaDescription;
	}
	public String getLanguageList() {
		return languageList;
	}
	public void setLanguageList(String languageList) {
		this.languageList = languageList;
	}
	public Integer getLuSpnApprovalCriteriaId() {
		return luSpnApprovalCriteriaId;
	}
	public void setLuSpnApprovalCriteriaId(Integer luSpnApprovalCriteriaId) {
		this.luSpnApprovalCriteriaId = luSpnApprovalCriteriaId;
	}
	
	
	
	
	
	
}
	
