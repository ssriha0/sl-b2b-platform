package com.newco.marketplace.business.businessImpl.alert;

import java.util.Date;

public class AlertTask {

private long	alertTaskId;
private Date	alertedTimestamp;
private String	targetKey;
private String	payload;
private String  payloadKey;
private long	transactionId;
private String	completionIndicator;
private Date	createdDate;
private Date 	modifiedDate;
private String  modifiedBy;
private int		alertTypeId;
private int		templateId;
private int		stateTransitionId;
private String  alertTo;
private String  alertFrom;
private String  alertCc;
private String  alertBcc;
private String  priority;
private String  templateInputValue;
private String templateSource;
private String templateSubject;
private String eid;
private String aopActionId;
private String parameters;
private String providerType;

/** Responsys folder name change done as part of v6 migration, change done on 06/08/2016 **/
private String responsysFolderName;


public String getResponsysFolderName() {
	return responsysFolderName;
}
public void setResponsysFolderName(String responsysFolderName) {
	this.responsysFolderName = responsysFolderName;
}

/** Responsys folder name change done as part of v6 migration, change done on 06/08/2016 **/

public String getTemplateSubject() {
	return templateSubject;
}
public void setTemplateSubject(String templateSubject) {
	this.templateSubject = templateSubject;
}
public String getTemplateSource() {
	return templateSource;
}
public void setTemplateSource(String templateSource) {
	this.templateSource = templateSource;
}
public String getAlertTo() {
	return alertTo;
}
public void setAlertTo(String alertTo) {
	this.alertTo = alertTo;
}
public String getAlertFrom() {
	return alertFrom;
}
public void setAlertFrom(String alertFrom) {
	this.alertFrom = alertFrom;
}
public String getAlertCc() {
	return alertCc;
}
public void setAlertCc(String alertCc) {
	this.alertCc = alertCc;
}
public String getAlertBcc() {
	return alertBcc;
}
public void setAlertBcc(String alertBcc) {
	this.alertBcc = alertBcc;
}
public long getAlertTaskId() {
	return alertTaskId;
}
public void setAlertTaskId(long alertTaskId) {
	this.alertTaskId = alertTaskId;
}
public Date getAlertedTimestamp() {
	return alertedTimestamp;
}
public void setAlertedTimestamp(Date alertedTimestamp) {
	this.alertedTimestamp = alertedTimestamp;
}
public String getTargetKey() {
	return targetKey;
}
public void setTargetKey(String targetKey) {
	this.targetKey = targetKey;
}
public String getPayload() {
	return payload;
}
public void setPayload(String payload) {
	this.payload = payload;
}
public String getPayloadKey() {
	return payloadKey;
}
public void setPayloadKey(String payloadKey) {
	this.payloadKey = payloadKey;
}
public long getTransactionId() {
	return transactionId;
}
public void setTransactionId(long transactionId) {
	this.transactionId = transactionId;
}
public String getCompletionIndicator() {
	return completionIndicator;
}
public void setCompletionIndicator(String completionIndicator) {
	this.completionIndicator = completionIndicator;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
public Date getModifiedDate() {
	return modifiedDate;
}
public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
public int getAlertTypeId() {
	return alertTypeId;
}
public void setAlertTypeId(int alertTypeId) {
	this.alertTypeId = alertTypeId;
}
public int getTemplateId() {
	return templateId;
}
public void setTemplateId(int templateId) {
	this.templateId = templateId;
}
public int getStateTransitionId() {
	return stateTransitionId;
}
public void setStateTransitionId(int stateTransitionId) {
	this.stateTransitionId = stateTransitionId;
}
public String getPriority() {
	return priority;
}
public void setPriority(String priority) {
	this.priority = priority;
}
public String getTemplateInputValue() {
	return templateInputValue;
}
public void setTemplateInputValue(String templateInputValue) {
	this.templateInputValue = templateInputValue;
}
/**
 * @return the eid
 */
public String getEid() {
	return eid;
}
/**
 * @param eid the eid to set
 */
public void setEid(String eid) {
	this.eid = eid;
}
public String getParameters() {
	return parameters;
}
public void setParameters(String parameters) {
	this.parameters = parameters;
}
public String getProviderType() {
	return providerType;
}
public void setProviderType(String providerType) {
	this.providerType = providerType;
}
	
}
