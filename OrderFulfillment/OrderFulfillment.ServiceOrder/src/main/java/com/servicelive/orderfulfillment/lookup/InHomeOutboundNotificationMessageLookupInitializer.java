package com.servicelive.orderfulfillment.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.domain.common.InHomeOutBoundMessages;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformCommonLookupBO;

public class InHomeOutboundNotificationMessageLookupInitializer {
	protected final Logger logger = Logger.getLogger(getClass());

    IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO;

    public void initialize(InHomeOutboundNotificationMessageLookup homeOutboundNotificationMessageLookup) {
        List<InHomeOutBoundMessages> messages = marketPlatformCommonLookupBO.getOutBoundStatusMessages();
        homeOutboundNotificationMessageLookup.setPropertyMap(createMessageMap(messages));
        homeOutboundNotificationMessageLookup.setInitialized(true);
    }

    private Map<Integer, InHomeOutBoundMessages> createMessageMap(List<InHomeOutBoundMessages> outBoundMessages){
        Map<Integer, InHomeOutBoundMessages> propertyMap = new HashMap<Integer, InHomeOutBoundMessages>();
        for(InHomeOutBoundMessages ap : outBoundMessages){
            propertyMap.put(ap.getWfStatus(), ap);
        }

        return propertyMap;
    }

    public void setMarketPlatformCommonLookupBO(IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO) {
        this.marketPlatformCommonLookupBO = marketPlatformCommonLookupBO;
    }
}
