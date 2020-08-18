package com.servicelive.orderfulfillment.notification.address;

import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;

/**
 * User: acabansag
 * Date: Jan 26, 2010
 * Time: 2:28:43 PM
 */
public class AddrFetcherForAssurant {

    QuickLookupCollection quickLookupCollection;

    public String fetchDestAddr() {
        return quickLookupCollection.getApplicationPropertyLookup().getPropertyValue("email_sl_assurant_cancel_request");    
    }

    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }
}
