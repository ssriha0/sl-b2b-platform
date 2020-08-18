package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ServiceOrderBuyerDocumentsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String serviceOrderId;
    private Long buyerId;
    private List<String> documentTitles = new ArrayList<String>();

    public ServiceOrderBuyerDocumentsVO() {}

    public ServiceOrderBuyerDocumentsVO(String serviceOrderId, Long buyerId, List<String> documentTitles) {
        this.serviceOrderId = serviceOrderId;
        this.buyerId = buyerId;
        this.documentTitles = documentTitles;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

    @XmlElement
    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    @XmlElement
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public List<String> getDocumentTitles() {
        return documentTitles;
    }

    @XmlElement
    public void setDocumentTitles(List<String> documentTitles) {
        this.documentTitles = documentTitles;
    }
}
