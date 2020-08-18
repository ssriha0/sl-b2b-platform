package com.servicelive.orderfulfillment.notification.address;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Yunus Burhani
 * Date: Jul 15, 2010
 * Time: 4:41:27 PM
 */
public class AddrFetcherForNewRoutedProviders extends AddrFetcherForRoutedProviders {
    IMarketPlatformProviderBO mktPlatformProviderBO;

    public List<String> fetchDestAddrList(NotificationType notificationType, ServiceOrder serviceOrder) {
        List<Long> providerIds = new ArrayList<Long>();
        List<Long> providerFirmIds = new ArrayList<Long>();
        
        for (RoutedProvider routedProvider : serviceOrder.getRoutedResources()){
            if(!routedProvider.getEmailSent()) { //this is not being set currently TODO set the value to true once email is sent
                providerIds.add(routedProvider.getProviderResourceId());
                if(!providerFirmIds.contains(routedProvider.getVendorId().longValue())) {
                	providerFirmIds.add(routedProvider.getVendorId().longValue());
            	}
            }
        }

        return getAddressList(providerIds, notificationType, providerFirmIds);
    }

    public void setMktPlatformProviderBO(IMarketPlatformProviderBO mktPlatformProviderBO) {
        this.mktPlatformProviderBO = mktPlatformProviderBO;
    }
}
