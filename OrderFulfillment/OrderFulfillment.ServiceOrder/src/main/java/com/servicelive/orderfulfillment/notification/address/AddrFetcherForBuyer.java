package com.servicelive.orderfulfillment.notification.address;

import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public class AddrFetcherForBuyer {
    IMarketPlatformBuyerBO mktPlatformBuyerBO;

    public String fetchDestAddr(NotificationType notificationType, ServiceOrder serviceOrder) {
        Contact contactInformation = mktPlatformBuyerBO.retrieveBuyerResourceContactInfo(serviceOrder.getBuyerResourceId());
        return AddrFetcherHelper.resolveDestAddrForContactInformation(contactInformation, notificationType);
    }

    public void setMktPlatformBuyerBO(IMarketPlatformBuyerBO mktPlatformBuyerBO) {
        this.mktPlatformBuyerBO = mktPlatformBuyerBO;
    }
}
