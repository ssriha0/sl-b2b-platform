package com.servicelive.marketplatform.notification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.marketplatform.common.domain.AbstractDomainEntity;

@Entity
@Table(name = "alert_task")
public class NotificationTask extends AbstractDomainEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_task_id")
    private Long id;

    @Column(name = "template_id")
    private Long templateId;
    
    @Column(name = "alert_type_id")
    private Long alertTypeId;

    @Column(name = "priority")
    private Double priority;

    @Column(name = "completion_indicator")
    private Short completionIndicator;    // 1=incomplete,  2=complete

    @Column(name = "alert_from")
    private String taskSender;

    @Column(name = "alert_to")
    private String taskRecipient;

    @Column(name = "alert_cc")
    private String taskCCRecipient;

    @Column(name = "alert_bcc")
    private String taskBCCRecipient;

    @Column(name = "template_input_value")
    private String mailMergeValues;

    @Column(name = "state_transition_id")
    private Integer stateTransitionId = 0;

    @Column(name = "transaction_id")
    private Integer transactionId = 0;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getAlertTypeId() {
        return alertTypeId;
    }

    public void setAlertTypeId(Long alertTypeId) {
        this.alertTypeId = alertTypeId;
    }

    public Short getCompletionIndicator() {
        return completionIndicator;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public void setCompletionIndicator(Short completionIndicator) {
        this.completionIndicator = completionIndicator;
    }

    public String getTaskSender() {
        return taskSender;
    }

    public void setTaskSender(String taskSender) {
        this.taskSender = taskSender;
    }

    public String getTaskRecipient() {
        return taskRecipient;
    }

    public void setTaskRecipient(String taskRecipient) {
        this.taskRecipient = taskRecipient;
    }

    public String getTaskCCRecipient() {
        return taskCCRecipient;
    }

    public void setTaskCCRecipient(String taskCCRecipient) {
        this.taskCCRecipient = taskCCRecipient;
    }

    public String getTaskBCCRecipient() {
        return taskBCCRecipient;
    }

    public void setTaskBCCRecipient(String taskBCCRecipient) {
        this.taskBCCRecipient = taskBCCRecipient;
    }

    public String getMailMergeValues() {
        return mailMergeValues;
    }

    public void setMailMergeValues(String mailMergeValues) {
        this.mailMergeValues = mailMergeValues;
    }

    public Integer getStateTransitionId() {
        return stateTransitionId;
    }

    public void setStateTransitionId(Integer stateTransitionId) {
        this.stateTransitionId = stateTransitionId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    ////////////////////////////////////////////////////////////////////////////
    // HELPER METHODS
    ////////////////////////////////////////////////////////////////////////////
    public void setTaskNotYetCompleted() {
        completionIndicator = 1;
    }
}
