package com.servicelive.orderfulfillment.lookup;

import com.servicelive.domain.common.ApplicationProperties;

import java.util.Map;

/**
 * User: yburhani
 * Date: Jun 21, 2010
 * Time: 10:13:13 AM
 */
public class ApplicationPropertyLookup implements IQuickLookup, IRemoteServiceDependentLookup {
    ApplicationPropertyLookupInitializer initializer;
    Map<String, ApplicationProperties> propertyMap;

    boolean initialized;
    
    public ApplicationProperties getProperty(String appKey){
        if(isInitialized())
            return propertyMap.get(appKey.toUpperCase());
        else
            return null;
    }

    public String getPropertyValue(String appKey){
        ApplicationProperties appProperty = getProperty(appKey);
        if (null == appProperty) return "";
        else return appProperty.getValue();
    }
    
    public void initialize() {
        initializer.initialize(this);
    }

    public void setInitializer(ApplicationPropertyLookupInitializer initializer) {
        this.initializer = initializer;
    }

    public void setPropertyMap(Map<String, ApplicationProperties> propertyMap) {
        this.propertyMap = propertyMap;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
