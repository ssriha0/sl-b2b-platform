package com.newco.marketplace.dto.vo;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;
@SuppressWarnings("serial")
public class ExpiryDetailsVO extends SerializableBaseVO
{
private Integer firmId;
private String firmName;
private String providerFirstName;
private String providerLastName;
private Integer credentialTypeId;
private String credentialType;
private Integer credentialCategoryId;
private String credentialCategory;
private Date credentialExpirationDate;
private Integer credentialStatus;
private Integer credentialId;
private String notificationType;
private Integer providerId;
private Integer credentialInd;
private Integer spnId;
private String reasoncode;
private String reviewcomments;
private String auditor;
private String credentialName;
private Integer documentId;
private Integer auditTaskId;
private String auditNotes;
private String firmEmail;
private String providerEmail;
private Integer entityType;
private Integer auditInd;
private Integer notificationId;
private String credName;
private String credNumber;

public String getCredNumber() {
	return credNumber;
}
public void setCredNumber(String credNumber) {
	this.credNumber = credNumber;
}
public String getCredName() {
	return credName;
}
public void setCredName(String credName) {
	this.credName = credName;
}

public Integer getNotificationId() {
	return notificationId;
}
public void setNotificationId(Integer notificationId) {
	this.notificationId = notificationId;
}
public Integer getAuditInd() {
	return auditInd;
}
public void setAuditInd(Integer auditInd) {
	this.auditInd = auditInd;
}
public Integer getDocumentId() {
	return documentId;
}
public void setDocumentId(Integer documentId) {
	this.documentId = documentId;
}
public Integer getAuditTaskId() {
	return auditTaskId;
}
public void setAuditTaskId(Integer auditTaskId) {
	this.auditTaskId = auditTaskId;
}
public Integer getEntityType() {
	return entityType;
}
public void setEntityType(Integer entityType) {
	this.entityType = entityType;
}
public Integer getFirmId() {
	return firmId;
}
public void setFirmId(Integer firmId) {
	this.firmId = firmId;
}
public Integer getCredentialTypeId() {
	return credentialTypeId;
}
public void setCredentialTypeId(Integer credentialTypeId) {
	this.credentialTypeId = credentialTypeId;
}
public Integer getCredentialCategoryId() {
	return credentialCategoryId;
}
public void setCredentialCategoryId(Integer credentialCategoryId) {
	this.credentialCategoryId = credentialCategoryId;
}
public Integer getCredentialStatus() {
	return credentialStatus;
}
public void setCredentialStatus(Integer credentialStatus) {
	this.credentialStatus = credentialStatus;
}
public Integer getCredentialId() {
	return credentialId;
}
public void setCredentialId(Integer credentialId) {
	this.credentialId = credentialId;
}
public Integer getProviderId() {
	return providerId;
}
public void setProviderId(Integer providerId) {
	this.providerId = providerId;
}
public Integer getCredentialInd() {
	return credentialInd;
}
public void setCredentialInd(Integer credentialInd) {
	this.credentialInd = credentialInd;
}

public String getFirmEmail() {
	return firmEmail;
}
public void setFirmEmail(String firmEmail) {
	this.firmEmail = firmEmail;
}
public String getProviderEmail() {
	return providerEmail;
}
public void setProviderEmail(String providerEmail) {
	this.providerEmail = providerEmail;
}

public Integer getSpnId() {
	return spnId;
}
public void setSpnId(Integer spnId) {
	this.spnId = spnId;
}

public String getAuditNotes() {
	return auditNotes;
}
public void setAuditNotes(String auditNotes) {
	this.auditNotes = auditNotes;
}

public String getCredentialName() {
	return credentialName;
}
public void setCredentialName(String credentialName) {
	this.credentialName = credentialName;
}

public String getReasoncode() {
	return reasoncode;
}
public void setReasoncode(String reasoncode) {
	this.reasoncode = reasoncode;
}
public String getReviewcomments() {
	return reviewcomments;
}
public void setReviewcomments(String reviewcomments) {
	this.reviewcomments = reviewcomments;
}
public String getAuditor() {
	return auditor;
}
public void setAuditor(String auditor) {
	this.auditor = auditor;
}

public String getFirmName() {
	return firmName;
}
public void setFirmName(String firmName) {
	this.firmName = firmName;
}
public String getProviderFirstName() {
	return providerFirstName;
}
public void setProviderFirstName(String providerFirstName) {
	this.providerFirstName = providerFirstName;
}
public String getProviderLastName() {
	return providerLastName;
}
public void setProviderLastName(String providerLastName) {
	this.providerLastName = providerLastName;
}

public String getCredentialType() {
	return credentialType;
}
public void setCredentialType(String credentialType) {
	this.credentialType = credentialType;
}

public String getCredentialCategory() {
	return credentialCategory;
}
public void setCredentialCategory(String credentialCategory) {
	this.credentialCategory = credentialCategory;
}
public Date getCredentialExpirationDate() {
	return credentialExpirationDate;
}
public void setCredentialExpirationDate(Date credentialExpirationDate) {
	this.credentialExpirationDate = credentialExpirationDate;
}

public String getNotificationType() {
	return notificationType;
}
public void setNotificationType(String notificationType) {
	this.notificationType = notificationType;
}

}
