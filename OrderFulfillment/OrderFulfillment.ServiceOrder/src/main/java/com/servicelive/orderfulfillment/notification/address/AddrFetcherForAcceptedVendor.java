package com.servicelive.orderfulfillment.notification.address;

import org.apache.log4j.Logger;

import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public class AddrFetcherForAcceptedVendor {
    IMarketPlatformProviderBO mktPlatformProviderBO;
    private Logger logger = Logger.getLogger(getClass());

    public String fetchDestAddr(NotificationType notificationType, ServiceOrder serviceOrder) {
        Long providerRsrcId = serviceOrder.getAcceptedProviderResourceId();
        Long providerId = serviceOrder.getAcceptedProviderId();
        Contact contactInformation = null;
        
        logger.info(" Notification Type : " + notificationType);
        // Added code for push notification
        if(notificationType == notificationType.PUSH && null != providerRsrcId){
        	logger.info(" Provider Resource Id : " + providerRsrcId);
        	return providerRsrcId.toString();
        }
                
        if(serviceOrder.getAssignmentType() != null && serviceOrder.getAssignmentType().equals("FIRM")){
        	contactInformation = mktPlatformProviderBO.retrieveProviderPrimaryResourceContactInfo(providerId);
        }else{
        	contactInformation = mktPlatformProviderBO.retrieveProviderResourceContactInfo(providerRsrcId);
        }
        return AddrFetcherHelper.resolveDestAddrForContactInformation(contactInformation, notificationType);
    }

    public void setMktPlatformProviderBO(IMarketPlatformProviderBO mktPlatformProviderBO) {
        this.mktPlatformProviderBO = mktPlatformProviderBO;
    }
}
