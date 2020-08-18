package com.servicelive.orderfulfillment.lookup;

import java.util.Map;

import com.servicelive.domain.common.InHomeOutBoundMessages;

public class InHomeOutboundNotificationMessageLookup implements IQuickLookup, IRemoteServiceDependentLookup{
	InHomeOutboundNotificationMessageLookupInitializer initializer;
    Map<Integer, InHomeOutBoundMessages> propertyMap;
    boolean initialized;
    
    public InHomeOutBoundMessages getMessageObject(Integer wfStatus){
        if(isInitialized())
            return propertyMap.get(wfStatus);
        else
            return null;
    }

    public String getMessage(Integer wfStatus){
    	InHomeOutBoundMessages outBoundMessage = getMessageObject(wfStatus);
        if (null == outBoundMessage) 
        	return "";
        else 
        	return outBoundMessage.getMessage();
    }
    
    public void initialize() {
        initializer.initialize(this);
    }

    public void setInitializer(InHomeOutboundNotificationMessageLookupInitializer initializer) {
        this.initializer = initializer;
    }

    public void setPropertyMap(Map<Integer, InHomeOutBoundMessages> propertyMap) {
        this.propertyMap = propertyMap;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
