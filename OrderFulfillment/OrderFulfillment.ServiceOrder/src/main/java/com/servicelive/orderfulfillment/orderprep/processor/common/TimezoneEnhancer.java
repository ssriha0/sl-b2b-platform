package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;


public class TimezoneEnhancer extends AbstractOrderEnhancer{
	private QuickLookupCollection quickLookupCollection;

	public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
		String tzID = getServiceTimeZoneId(serviceOrder);
		serviceOrder.setServiceLocationTimeZone(tzID);
	}
	
	private String getServiceTimeZoneId(ServiceOrder serviceOrder) {
        String tzID = null;

        if (serviceOrder.getServiceLocation() != null
                && StringUtils.isNotBlank(serviceOrder.getServiceLocation().getZip())) {
            tzID = quickLookupCollection.getTimeZoneLookup().getTimeZone(serviceOrder.getServiceLocation().getZip());
        }else{
            validationUtil.addErrors(serviceOrder.getValidationHolder(), ProblemType.CannotFindTimeZone);
        }

        TimeZone serviceTimeZone = (tzID==null) ? TimeZone.getTimeZone("GMT") : TimeZone.getTimeZone(tzID);
        if (serviceTimeZone.equals(TimeZone.getTimeZone("GMT"))) {
            tzID = "GMT";
        }

        return tzID;
    }
	
	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
	}
	
	public QuickLookupCollection getQuickLookupCollection(){
		return this.quickLookupCollection;
	}
}
