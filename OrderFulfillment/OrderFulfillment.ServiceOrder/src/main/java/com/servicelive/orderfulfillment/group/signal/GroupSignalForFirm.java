package com.servicelive.orderfulfillment.group.signal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.servicelive.orderfulfillment.ServiceOrderBO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.signal.Signal;

public class GroupSignalForFirm extends Signal implements IGroupSignal {
	
	protected boolean sendSignalToOrders;
	protected ServiceOrderBO serviceOrderBO;
	protected SignalType orderSignal = SignalType.UNKNOWN;
	protected List<String> groupProcessKeys = new ArrayList<String>();

	public GroupSignalForFirm(){
		groupProcessKeys.add(ProcessVariableUtil.PVKEY_GROUP_ID);
		groupProcessKeys.add(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS);
	}

	public void process(SOElement soe, SOGroup sog, Identification id, Map<String, Serializable> miscParams){
        List<String> errors = validate(soe, sog);
        if (errors.isEmpty()) {
            update(soe, sog);
            sendSignalToOrders(soe, sog, id, miscParams);
        } else {
            throw new ServiceOrderException(errors);
        }
	}
	
	protected void sendSignalToOrders(SOElement soe, SOGroup sog, Identification id, Map<String, Serializable> miscParams) {
        if (sendSignalToOrders && orderSignal != SignalType.UNKNOWN){
        	Map<String, Serializable> params = new HashMap<String, Serializable>();
        	for (Entry<String, Serializable> entry : miscParams.entrySet()){
        		if (!groupProcessKeys.contains(entry.getKey()))
        			params.put(entry.getKey(), entry.getValue());
        	}
        	for (ServiceOrder so : sog.getServiceOrders()){
            	serviceOrderBO.processOrderSignal(so.getSoId(), orderSignal, id, soe, params);
        	}
        }		
	}

	protected void update(SOElement soe, SOGroup sog) {
        //Tempalate method - can not declare abstract as Signal is a generic handler.
	}

	protected List<String> validate(SOElement soe, SOGroup sog) {
		return Collections.emptyList();
	}

	public void setSendSignalToOrders(boolean sendSignalToOrders) {
		this.sendSignalToOrders = sendSignalToOrders;
	}

	public void setServiceOrderBO(ServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setOrderSignal(SignalType orderSignal) {
		this.orderSignal = orderSignal;
	}

}
