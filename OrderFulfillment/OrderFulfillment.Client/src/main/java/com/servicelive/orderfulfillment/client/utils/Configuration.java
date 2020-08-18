package com.servicelive.orderfulfillment.client.utils;

import org.apache.commons.lang.BooleanUtils;

/**
 * Helper class to read configuration information from system properties.
 * @author Mustafa Motiwala
 *
 */
public class Configuration {
    
    private static final String OF_ENABLED_PROPERTY="orderfulfillment.enabled";
    private static final String OF_SIMPLE_BUYER_ONLY="orderfulfillment.allowed.buyerroles";
    private static final String OF_SERVICE_URL="orderfulfillment.service.url";
    
    /**
     * Private constructor, as this class is not meant to be instantiated.
     */
    public Configuration(){}
    
    public static String getServiceBaseUrl(){
        return System.getProperty(OF_SERVICE_URL);
    }
    
    /**
     * Returns true, if and ONLY if the {@link Configuration.OF_ENABLED_PROPERTY} has been defined
     * as has been set to either {@code TRUE, YES, ON or 1}
     * @return
     */
    public static Boolean isOrderFulfillmentEnabled(){
        return BooleanUtils.toBoolean(System.getProperty(OF_ENABLED_PROPERTY));
    }
    
    public static String getAllowedBuyerRoles(){
        return System.getProperty(OF_SIMPLE_BUYER_ONLY);
    }
}
