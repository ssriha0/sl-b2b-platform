package com.servicelive.orderfulfillment.serviceinterface;

import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.PendingServiceOrdersResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public interface IOrderFulfillmentBO {

	public OrderFulfillmentResponse createServiceOrder(CreateOrderRequest request);
	public OrderFulfillmentResponse processOrderSignal(String soId, SignalType signalType, OrderFulfillmentRequest request);
	public OrderFulfillmentResponse processGroupSignal(String groupId, SignalType signalType, OrderFulfillmentRequest request);
	public PendingServiceOrdersResponse getPendingServiceOrders();
}
