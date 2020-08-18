package com.servicelive.orderfulfillment.lookup;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformCommonLookupBO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Aug 6, 2010
 * Time: 12:25:20 PM
 */
public class TimeZoneLookup implements IQuickLookup, IRemoteServiceDependentLookup {

    private static Map<String, String> zipToTimeZone = new HashMap<String, String>();
    private IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO;
    boolean initialized;

    public void initialize() {
        zipToTimeZone.clear();
        initialized = true;
    }

    public String getTimeZone(String zipCode){
        String tzID = null;
        if(zipToTimeZone.containsKey(zipCode)) {
            tzID = zipToTimeZone.get(zipCode);
        } else {
            tzID = marketPlatformCommonLookupBO.lookupTimeZoneForZip(zipCode);
            zipToTimeZone.put(zipCode, tzID);
        }
        return tzID;
    }

    public void setMarketPlatformCommonLookupBO(IMarketPlatformCommonLookupBO marketPlatformCommonLookupBO) {
        this.marketPlatformCommonLookupBO = marketPlatformCommonLookupBO;
    }
}
