package com.servicelive.orderfulfillment.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.common.ApplicationFlags;
import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformCommonLookupBO;

public class ApplicationFlagLookupInitializer {
	protected final Logger logger = Logger.getLogger(getClass());

    IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO;
    public void initialize(ApplicationFlagLookup applicationFlagLookup) {
        List<ApplicationFlags> applicationFlags = marketPlatformCommonLookupBO.getApplicationFlags();
        logger.info("applicationFlags: "+applicationFlags);
        applicationFlagLookup.setPropertyMap(createApplicationPropertiesMap(applicationFlags));
        applicationFlagLookup.setInitialized(true);
    }

    private Map<String, ApplicationFlags> createApplicationPropertiesMap(List<ApplicationFlags> applicationFlags){
        Map<String, ApplicationFlags> propertyMap = new HashMap<String, ApplicationFlags>();
        for(ApplicationFlags ap : applicationFlags){
        	logger.info("applicationFlags key value: "+ap.getKey()+":"+ap.getValue());
            propertyMap.put(ap.getKey().toUpperCase(), ap);
        }

        return propertyMap;
    }

    public void setMarketPlatformCommonLookupBO(IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO) {
        this.marketPlatformCommonLookupBO = marketPlatformCommonLookupBO;
    }
}
