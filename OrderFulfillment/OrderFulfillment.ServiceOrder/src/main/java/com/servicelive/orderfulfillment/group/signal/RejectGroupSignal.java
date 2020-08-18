package com.servicelive.orderfulfillment.group.signal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class RejectGroupSignal extends GroupSignal {

	protected void update(SOElement soe, SOGroup sog){
		  SOElementCollection col= (SOElementCollection)soe;
	        List<SOElement> inputRoutedProviders = (List<SOElement>) col.getElements();
	         Set<GroupRoutedProvider> routedProviders = sog.getGroupRoutedProviders();
	         for(SOElement rejectedProviderElement :inputRoutedProviders){
	        	 RoutedProvider rejectedProvider=(RoutedProvider)rejectedProviderElement;
	         	for(GroupRoutedProvider routedProvider :routedProviders){
	         		if(routedProvider.getProviderResourceId().equals(rejectedProvider.getProviderResourceId())){
	         			 routedProvider.setProviderRespReasonId(rejectedProvider.getProviderRespReasonId());
	         	         routedProvider.setProviderResponse(rejectedProvider.getProviderResponse());
	         	         routedProvider.setProviderRespComment(rejectedProvider.getProviderRespComment());
	         	         routedProvider.setModifiedBy(rejectedProvider.getModifiedBy());
	         	         routedProvider.setProviderRespDate(rejectedProvider.getProviderRespDate());
	         	         routedProvider.setModifiedDate(rejectedProvider.getModifiedDate());
	         	         serviceOrderDao.update(routedProvider);
	         		}
	         	
	         	}
	         }
	         
	}
	
	
	protected void sendSignalToOrders(SOElement soe, SOGroup sog, Identification id, Map<String, Serializable> miscParams) {
        if (sendSignalToOrders && orderSignal != SignalType.UNKNOWN){
        	Map<String, Serializable> params = new HashMap<String, Serializable>();
        	for (Entry<String, Serializable> entry : miscParams.entrySet()){
        		if (!groupProcessKeys.contains(entry.getKey()))
        			params.put(entry.getKey(), entry.getValue());
        	}
        	int i = 0;
        	for (ServiceOrder so : sog.getServiceOrders()){
        		if (i == 0){
        			params.put(OrderfulfillmentConstants.PVKEY_SEND_EMAIL, Boolean.TRUE);
        		}else{
        			params.put(OrderfulfillmentConstants.PVKEY_SEND_EMAIL, Boolean.FALSE);
        		}
        		i++;
            	serviceOrderBO.processOrderSignal(so.getSoId(), orderSignal, id, soe, params);
        	}
        }		
	}

}
