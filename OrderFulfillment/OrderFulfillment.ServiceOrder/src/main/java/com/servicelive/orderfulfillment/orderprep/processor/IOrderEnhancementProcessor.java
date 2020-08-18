package com.servicelive.orderfulfillment.orderprep.processor;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;

public interface IOrderEnhancementProcessor {
    public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext);
}
