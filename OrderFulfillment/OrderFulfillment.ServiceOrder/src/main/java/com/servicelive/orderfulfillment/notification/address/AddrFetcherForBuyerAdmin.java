package com.servicelive.orderfulfillment.notification.address;

import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public class AddrFetcherForBuyerAdmin {
    IMarketPlatformBuyerBO mktPlatformBuyerBO;

    public String fetchDestAddr(NotificationType notificationType, ServiceOrder serviceOrder) {
        Contact contactInformation = mktPlatformBuyerBO.retrieveBuyerContactInfo(serviceOrder.getBuyerId());
        return AddrFetcherHelper.resolveDestAddrForContactInformation(contactInformation, notificationType);
    }

    public void setMktPlatformBuyerBO(IMarketPlatformBuyerBO mktPlatformBuyerBO) {
        this.mktPlatformBuyerBO = mktPlatformBuyerBO;
    }
}
