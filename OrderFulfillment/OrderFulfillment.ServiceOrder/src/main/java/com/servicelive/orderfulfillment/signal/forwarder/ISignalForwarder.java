package com.servicelive.orderfulfillment.signal.forwarder;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.signal.ISignal;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Aug 19, 2010
 * Time: 3:46:03 PM
 * This class determines if the signal needs to be forwarded to another signal
 * The determination is based on the data that is send with the signal
 */
public interface ISignalForwarder {
    public ISignal getForwardedSignal(ISignal signal, SOElement element);
}
