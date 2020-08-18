package com.servicelive.orderfulfillment.group.signal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class AcceptGroupSignal extends GroupSignal {
	
	protected void sendSignalToOrders(SOElement soe, SOGroup sog, Identification id, Map<String, Serializable> miscParams) {
        if (sendSignalToOrders && orderSignal != SignalType.UNKNOWN){
        	Map<String, Serializable> params = new HashMap<String, Serializable>();
        	for (Entry<String, Serializable> entry : miscParams.entrySet()){
        		if (!groupProcessKeys.contains(entry.getKey()))
        			params.put(entry.getKey(), entry.getValue());
        	}
        	for (ServiceOrder so : sog.getServiceOrders()){
        		params.put(OrderfulfillmentConstants.PVKEY_SEND_EMAIL, Boolean.FALSE);
            	serviceOrderBO.processOrderSignal(so.getSoId(), orderSignal, id, soe, params);
        	}
        }		
	}
}
