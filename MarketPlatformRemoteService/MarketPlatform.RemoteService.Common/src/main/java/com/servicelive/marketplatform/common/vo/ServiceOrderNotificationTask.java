package com.servicelive.marketplatform.common.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceOrderNotificationTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private String serviceOrderId;
    private Long templateId;
    private ServiceOrderNotificationTaskAddresses addresses;
    Map<String, String> templateMergeValueMap;

    @XmlElement
    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

    @XmlElement
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    @XmlElement
    public void setAddresses(ServiceOrderNotificationTaskAddresses addresses) {
        this.addresses = addresses;
    }

    public ServiceOrderNotificationTaskAddresses getAddresses() {
        return addresses;
    }

    @XmlElement
    public void setTemplateMergeValueMap(Map<String, String> templateMergeValueMap) {
        this.templateMergeValueMap = templateMergeValueMap;
    }

    public Map<String, String> getTemplateMergeValueMap() {
        return templateMergeValueMap;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
