package com.servicelive.orderfulfillment.lookup;

import java.util.Map;

import com.servicelive.domain.lookup.LookupServiceType;

public class ServiceTypeLookup implements IQuickLookup {
    Map<Integer, Map<String, LookupServiceType>> nodeAndDescriptionToServiceTypeMap;
    Map<Integer, LookupServiceType> serviceTypeMap;
    ServiceTypeLookupInitializer initializer;

    boolean initialized;

    public void initialize() {
        initializer.initialize(this);
    }

    public void setNodeAndDescriptionToServiceTypeMap(Map<Integer, Map<String, LookupServiceType>> nodeAndDescriptionToServiceTypeMap) {
        this.nodeAndDescriptionToServiceTypeMap = nodeAndDescriptionToServiceTypeMap;
    }

    public void setServiceTypeMap(Map<Integer, LookupServiceType> serviceTypeMap) {
        this.serviceTypeMap = serviceTypeMap;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public LookupServiceType getLookupServiceTypeById(Integer svcTypId) {
        return serviceTypeMap.get(svcTypId);
    }

    public LookupServiceType getLookupServiceTypeByNodeAndDescription(Integer nodeId, String description) {
        Map<String, LookupServiceType> descriptionToServiceTypeMap = nodeAndDescriptionToServiceTypeMap.get(nodeId);
        if (descriptionToServiceTypeMap != null) {
            return descriptionToServiceTypeMap.get(description);
        }
        return null;
    }

    public void setInitializer(ServiceTypeLookupInitializer initializer) {
        this.initializer = initializer;
    }
}
