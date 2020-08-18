package com.servicelive.orderfulfillment.lookup;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;

public class ServiceTypeLookupInitializer {
    IServiceOrderDao serviceOrderDao;

    public void initialize(ServiceTypeLookup serviceTypeLookup) {
        List<LookupServiceType> serviceTypes = serviceOrderDao.getServiceTypeTemplates();

        Map<Integer, Map<String, LookupServiceType>> nodeAndDescriptionToServiceTypeMap = new TreeMap<Integer, Map<String, LookupServiceType>>();
        Map<Integer, LookupServiceType> serviceTypeMap = new TreeMap<Integer, LookupServiceType>();

        for (LookupServiceType lookupServiceType : serviceTypes) {
            serviceTypeMap.put(lookupServiceType.getId(), lookupServiceType);
            Integer nodeId = lookupServiceType.getSkillNodeId().getId();

            Map<String, LookupServiceType> descToSvcTypeMap;
            if (nodeAndDescriptionToServiceTypeMap.containsKey(nodeId)) {
                descToSvcTypeMap = nodeAndDescriptionToServiceTypeMap.get(nodeId);
            } else {
                descToSvcTypeMap = new TreeMap<String, LookupServiceType>();
                nodeAndDescriptionToServiceTypeMap.put(nodeId, descToSvcTypeMap);
            }
            descToSvcTypeMap.put(lookupServiceType.getDescription(), lookupServiceType);
        }

        serviceTypeLookup.setServiceTypeMap(serviceTypeMap);
        serviceTypeLookup.setNodeAndDescriptionToServiceTypeMap(nodeAndDescriptionToServiceTypeMap);
    }

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }
}
