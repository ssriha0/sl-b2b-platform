package com.servicelive.orderfulfillment.orderprep;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderEnhancementServiceResolver {
    final Map<Long, OrderEnhancementService> orderEnhancementServiceMap = new HashMap<Long, OrderEnhancementService>();

    public OrderEnhancementServiceResolver(List<OrderEnhancementService> enhancementServiceList) {
        for (OrderEnhancementService orderEnhancementService : enhancementServiceList) {
            orderEnhancementServiceMap.put(orderEnhancementService.orderBuyer.getBuyerId(), orderEnhancementService);
        }
    }

    public OrderEnhancementService resolveOrderEnhancementService(Long buyerId) {
        return orderEnhancementServiceMap.get(buyerId);
    }
}
