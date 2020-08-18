package com.servicelive.marketplatform.notification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.marketplatform.common.domain.AbstractDomainEntity;

@Entity
@Table(name = "template")
public class NotificationTemplate extends AbstractDomainEntity {

    public enum TemplateProviderType {
        RESPONSYS, VELOCITY, CHEETAH, ADOBE;
    }

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "template_id")
    private Long id;

    @Column(name = "template_type_id")
    private Long typeId;

    @Column(name = "template_name")
    private String name;

    @Column(name = "template_subject")
    private String subject;

    @Column(name = "template_source")
    private String source;

    @Column(name = "sort_order")
    private Long cardinality;

    @Column(name = "template_from")
    private String notificationSender;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "eid")
    private String eid;
    
    @Column(name = "email_provider_type")
    private TemplateProviderType emailProviderType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getCardinality() {
        return cardinality;
    }

    public void setCardinality(Long cardinality) {
        this.cardinality = cardinality;
    }

    public String getNotificationSender() {
        return notificationSender;
    }

    public void setNotificationSender(String notificationSender) {
        this.notificationSender = notificationSender;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public TemplateProviderType getEmailProviderType() {
        return emailProviderType;
    }

    public void setEmailProviderType(TemplateProviderType emailProviderType) {
        this.emailProviderType = emailProviderType;
    }
}
