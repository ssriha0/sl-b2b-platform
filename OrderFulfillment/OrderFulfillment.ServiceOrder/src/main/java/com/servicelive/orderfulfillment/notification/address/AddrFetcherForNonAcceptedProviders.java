package com.servicelive.orderfulfillment.notification.address;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public class AddrFetcherForNonAcceptedProviders extends AddrFetcherForRoutedProviders{

    public List<String> fetchDestAddrList(NotificationType notificationType, ServiceOrder serviceOrder) {
        List<Long> providerIds = new ArrayList<Long>();
        List<Long> providerFirmIds = new ArrayList<Long>();
        
        for (RoutedProvider routedProvider : serviceOrder.getRoutedResources()) {
            @SuppressWarnings("unused")
            Long rsrcId = routedProvider.getProviderResourceId();
            if (routedProvider.getProviderResponse() != ProviderResponseType.ACCEPTED &&
            		routedProvider.getProviderResponse() != ProviderResponseType.REJECTED){
                providerIds.add(routedProvider.getProviderResourceId());
                if(!providerFirmIds.contains(routedProvider.getVendorId().longValue())) {
                	providerFirmIds.add(routedProvider.getVendorId().longValue());
                }
	        }
        }

        return getAddressList(providerIds, notificationType, providerFirmIds);
    }

}
