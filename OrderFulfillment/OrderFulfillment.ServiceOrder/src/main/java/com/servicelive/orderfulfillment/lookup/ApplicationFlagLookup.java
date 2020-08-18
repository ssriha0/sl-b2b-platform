package com.servicelive.orderfulfillment.lookup;

import java.util.Map;

import com.servicelive.domain.common.ApplicationFlags;

/**
 * User: yburhani
 * Date: Jun 21, 2010
 * Time: 10:13:13 AM
 */
public class ApplicationFlagLookup implements IQuickLookup, IRemoteServiceDependentLookup {
	ApplicationFlagLookupInitializer initializer;
    Map<String, ApplicationFlags> propertyMap;

    boolean initialized;
    
    public ApplicationFlags getProperty(String appKey){
        if(isInitialized())
            return propertyMap.get(appKey.toUpperCase());
        else
            return null;
    }

    public String getPropertyValue(String appKey){
    	ApplicationFlags appProperty = getProperty(appKey);
        if (null == appProperty) return "";
        else return appProperty.getValue();
    }
    
    public void initialize() {
        initializer.initialize(this);
    }

    public void setInitializer(ApplicationFlagLookupInitializer initializer) {
        this.initializer = initializer;
    }

    public void setPropertyMap(Map<String, ApplicationFlags> propertyMap) {
        this.propertyMap = propertyMap;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
