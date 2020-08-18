package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

@XmlRootElement()
@XmlSeeAlso(value = {CondRoutingRuleVO.class, ProviderListCriteriaForAutoRoutingVO.class, ServiceOrderBuyerDocumentsVO.class, ItemsForCondAutoRouteRepriceVO.class,ProviderFirmInfoVO.class})
public class MarketPlatformServiceRequest {
    @XmlElement()
    private Object requestObj;

    @SuppressWarnings("unchecked")
    @XmlElementWrapper()
    private Collection requestCollection;

    private static final long serialVersionUID = 1L;

    public Object getRequestObj() {
        if (requestObj != null) return requestObj;
        else return requestCollection;
    }

    @SuppressWarnings("unchecked")
    @XmlTransient
    public void setRequestObj(Object requestObj) {
        if (requestObj instanceof Collection){
        	this.requestCollection = (Collection) requestObj;
        }else{
        	this.requestObj = requestObj;
        }
    }
}
