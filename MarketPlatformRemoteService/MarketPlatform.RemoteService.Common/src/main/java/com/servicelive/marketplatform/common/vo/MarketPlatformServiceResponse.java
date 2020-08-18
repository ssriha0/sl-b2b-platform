package com.servicelive.marketplatform.common.vo;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import com.servicelive.common.vo.SLBaseRestResponseVO;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerHoldTime;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.buyer.SimpleBuyerFeature;
import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.domain.common.ApplicationFlags;
import com.servicelive.domain.common.InHomeOutBoundMessages;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.marketplatform.notification.domain.NotificationTemplate;
import com.servicelive.marketplatform.provider.domain.SkillNode;

@XmlRootElement()
@XmlSeeAlso (value = {ApplicationFlags.class,InHomeOutBoundMessages.class,ApplicationProperties.class, Contact.class, Buyer.class, NotificationTemplate.class, SkillNode.class, ProviderIdVO.class, BuyerReferenceType.class, BuyerResource.class, SimpleBuyerFeature.class, ItemsForCondAutoRouteRepriceVO.class, BuyerHoldTime.class, TierReleaseInfoVO.class, CondRoutingRuleVO.class,ProviderFirmInfoVO.class,BuyerCallbackEventVO.class})
public class MarketPlatformServiceResponse extends SLBaseRestResponseVO {
    @XmlElement()
	private Object responseObj;
    
    @SuppressWarnings("unchecked")
	@XmlElementWrapper()
    private Collection responseCollection;
    
    private static final long serialVersionUID = 1L;

    public Object getResponseObj() {
        if (responseObj != null) return responseObj;
        else return responseCollection;
    }

    @SuppressWarnings("unchecked")
    @XmlTransient
	public void setResponseObj(Object responseObj) {
        if (responseObj instanceof Collection){
        	this.responseCollection = (Collection) responseObj;
        }else{
        	this.responseObj = responseObj;
        }
    }
}
