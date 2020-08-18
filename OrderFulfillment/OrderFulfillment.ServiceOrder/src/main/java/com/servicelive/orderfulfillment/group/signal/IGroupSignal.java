package com.servicelive.orderfulfillment.group.signal;

import java.io.Serializable;
import java.util.Map;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.signal.ISignal;

public interface IGroupSignal extends ISignal {

	public void process(SOElement soe, SOGroup sog, Identification id, Map<String, Serializable> miscParams);
}
