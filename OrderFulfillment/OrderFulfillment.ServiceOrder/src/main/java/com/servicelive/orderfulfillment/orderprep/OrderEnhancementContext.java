package com.servicelive.orderfulfillment.orderprep;

import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;

public class OrderEnhancementContext {
    IOrderBuyer orderBuyer;

    public OrderEnhancementContext(IOrderBuyer orderBuyer) {
        this.orderBuyer = orderBuyer;
    }

    public IOrderBuyer getOrderBuyer() {
        return orderBuyer;
    }
}
