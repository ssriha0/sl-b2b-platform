package com.servicelive.orderfulfillment.notification.address;

import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public class AddrFetcherForProviderAdmin {
    IMarketPlatformProviderBO mktPlatformProviderBO;

    public String fetchDestAddr(NotificationType notificationType, ServiceOrder serviceOrder) {
        Long providerId = serviceOrder.getAcceptedProviderId();
        Contact contactInformation = mktPlatformProviderBO.retrieveProviderPrimaryResourceContactInfo(providerId);
        return AddrFetcherHelper.resolveDestAddrForContactInformation(contactInformation, notificationType);
    }

    public void setMktPlatformProviderBO(IMarketPlatformProviderBO mktPlatformProviderBO) {
        this.mktPlatformProviderBO = mktPlatformProviderBO;
    }
}
