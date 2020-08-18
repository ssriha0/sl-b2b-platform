package com.servicelive.orderfulfillment.orderprep.processor.common;

import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;

public class CommonPartsEnhancer extends AbstractOrderEnhancer {
    public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        if (serviceOrder.getParts() != null) {
            for (SOPart part : serviceOrder.getParts()) {
                part.setProviderBringPartInd(partHasPickupLocation(part));
            }
        }
    }

    private Boolean partHasPickupLocation(SOPart part) {
        return new Boolean(part.getPickupLocation() != null);
    }
}
