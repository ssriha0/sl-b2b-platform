/**
 * 
 */
package com.newco.marketplace.dto.vo.alert;

import java.sql.Timestamp;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author sahmad7
 * 
 */
public class AlertTaskVO extends MPBaseVO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 919331573630377493L;
	private Integer alertTaskId;
    private Timestamp alertedTimestamp;
    private String targetKey;
    private String payload;
    private String payloadKey;
    private Integer transactionId;
    private Integer alertTypeId;
    private Boolean completionIndicator;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String modifiedBy;
    private Integer wfTemplateId;
    private Integer stateTransitionId;

    public Timestamp getAlertedTimestamp() {
        return alertedTimestamp;
    }

    public void setAlertedTimestamp(Timestamp alertedTimestamp) {
        this.alertedTimestamp = alertedTimestamp;
    }

    public Integer getAlertTaskId() {
        return alertTaskId;
    }

    public void setAlertTaskId(Integer alertTaskId) {
        this.alertTaskId = alertTaskId;
    }

    public Boolean getCompletionIndicator() {
        return completionIndicator;
    }

    public void setCompletionIndicator(Boolean completionIndicator) {
        this.completionIndicator = completionIndicator;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getWfTemplateId() {
        return wfTemplateId;
    }

    public void setWfTemplateId(Integer wfTemplateId) {
        this.wfTemplateId = wfTemplateId;
    }

    public Integer getAlertTypeId() {
        return alertTypeId;
    }

    public void setAlertTypeId(Integer alertTypeId) {
        this.alertTypeId = alertTypeId;
    }

    @Override
	public String toString() {
        return ("<AlertTaskVO>" + "alertTaskId | " + alertTaskId
                + "alertedTimestamp | " + alertedTimestamp + "targetKey | "
                + targetKey + "payload | " + payload + "payloadKey | "
                + payloadKey + "transactionId | " + transactionId
                + "alertTypeId | " + alertTypeId + "completionIndicator | "
                + completionIndicator + "createdDate | " + createdDate
                + "modifiedDate | " + modifiedDate + "modifiedBy | " + modifiedBy
                + "wfTemplateId | " + wfTemplateId + "</AlertTaskVO>");
    }

    public Integer getStateTransitionId() {
        return stateTransitionId;
    }

    public void setStateTransitionId(Integer stateTransitionId) {
        this.stateTransitionId = stateTransitionId;
    }
}
