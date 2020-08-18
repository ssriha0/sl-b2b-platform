package com.servicelive.orderfulfillment.signal.forwarder;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;
import com.servicelive.orderfulfillment.signal.ISignal;

/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Aug 19, 2010
 * Time: 3:53:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadyForAutoPostForwarder implements ISignalForwarder{
    private ISignal forwardedSignal;

    public ISignal getForwardedSignal(ISignal signal, SOElement element) {
        ServiceOrder source = (ServiceOrder) element;
        if (source.getWfSubStatusId() == LegacySOSubStatus.READY_FOR_POSTING.getId()){
            return forwardedSignal;
        }
        return signal;
    }

    public void setForwardedSignal(ISignal forwardedSignal) {
        this.forwardedSignal = forwardedSignal;
    }
}
