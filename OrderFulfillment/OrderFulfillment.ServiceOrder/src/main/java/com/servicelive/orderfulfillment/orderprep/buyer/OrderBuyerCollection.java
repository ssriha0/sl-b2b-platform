package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBuyerCollection {
    Map<Long, IOrderBuyer> orderBuyerMap;

    public OrderBuyerCollection(List<IOrderBuyer> orderBuyerList) {
        orderBuyerMap = new HashMap<Long, IOrderBuyer>();
        for (IOrderBuyer orderBuyer : orderBuyerList) {
            orderBuyerMap.put(orderBuyer.getBuyerId(), orderBuyer);
        }
    }

    public IOrderBuyer getOrderBuyer(Long buyerId) {
        return orderBuyerMap.get(buyerId);
    }

	public void initializeAllOrderBuyers() {
		for (IOrderBuyer orderBuyer : orderBuyerMap.values()) {
			orderBuyer.initialize();
		}
	}
}
