package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class OrderRoutingRuleAssocVO implements Serializable {
    private static final long serialVersionUID = 1L;

    String serviceOrderId;
    Integer routingRuleId;

    public OrderRoutingRuleAssocVO() {

    }

    public OrderRoutingRuleAssocVO(String serviceOrderId, Integer routingRuleId) {
        this.serviceOrderId = serviceOrderId;
        this.routingRuleId = routingRuleId;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

    @XmlElement
    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public Integer getRoutingRuleId() {
        return routingRuleId;
    }

    @XmlElement
    public void setRoutingRuleId(Integer routingRuleId) {
        this.routingRuleId = routingRuleId;
    }
}
