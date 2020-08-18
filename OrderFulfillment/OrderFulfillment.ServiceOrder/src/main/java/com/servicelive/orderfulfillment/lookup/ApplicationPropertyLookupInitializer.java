package com.servicelive.orderfulfillment.lookup;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformCommonLookupBO;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.common.ApplicationProperties;

/**
 * User: yburhani
 * Date: Jun 21, 2010
 * Time: 10:15:17 AM
 */
public class ApplicationPropertyLookupInitializer {
    protected final Logger logger = Logger.getLogger(getClass());

    IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO;

    public void initialize(ApplicationPropertyLookup applicationPropertyLookup) {
        List<ApplicationProperties> applicationProperties = marketPlatformCommonLookupBO.getApplicationProperties();
        applicationPropertyLookup.setPropertyMap(createApplicationPropertiesMap(applicationProperties));
        applicationPropertyLookup.setInitialized(true);
    }

    private Map<String, ApplicationProperties> createApplicationPropertiesMap(List<ApplicationProperties> applicationProperties){
        Map<String, ApplicationProperties> propertyMap = new HashMap<String, ApplicationProperties>();
        for(ApplicationProperties ap : applicationProperties){
            propertyMap.put(ap.getKey().toUpperCase(), ap);
        }

        return propertyMap;
    }

    public void setMarketPlatformCommonLookupBO(IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO) {
        this.marketPlatformCommonLookupBO = marketPlatformCommonLookupBO;
    }
}
