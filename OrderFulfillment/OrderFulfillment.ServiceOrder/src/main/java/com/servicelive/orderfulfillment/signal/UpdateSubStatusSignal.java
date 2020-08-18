package com.servicelive.orderfulfillment.signal;

import java.util.Collections;
import java.util.List;

import com.servicelive.bus.EventAdapter;
import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * Created by IntelliJ IDEA.
 * User: mmotiwala
 * Date: Mar 18, 2010
 * Time: 5:17:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateSubStatusSignal extends Signal {

    private EventAdapter eventAdapter;
    private List<Integer> subStatusList = Collections.emptyList();
    /**
     * Only support updating the SubStatus on the ServiceOrder.
     * @param element - Input ServiceOrder with attributes which need to be updated.
     * @param dst - Target/Destination. This is JPA managed Entity
     */
    @Override
    protected void update(SOElement element, ServiceOrder dst) {
        ServiceOrder source = (ServiceOrder) element;
        //For legacy code compat. we need to null out the value if none has been specified.
        if(source.getWfSubStatusId()==52) dst.setWfSubStatusId(null);//lu_so_substatus defines 52 as no substatus specified
        else dst.setWfSubStatusId(source.getWfSubStatusId());
        fireEvent(dst);
    }

    private void fireEvent(ServiceOrder so) {
        if(subStatusList.contains(so.getWfSubStatusId())){
            ServiceOrderEvent event = new ServiceOrderEvent(so.getSoId(),so, OrderEventType.SUBSTATUS_CHANGE);
            event.addHeader(EventHeader.BUYER_COMPANY_ID, String.valueOf(so.getBuyerId()));
            event.addHeader(EventHeader.BUYER_RESOURCE_ID, String.valueOf(so.getBuyerResourceId()));
            eventAdapter.raiseEvent(event);
        }
    }

    /**
     * No specific validation rules as the intent is for a free
     * form update request to update various parameters on the ServiceOrder.
     *
     * @param soe - The input to the Signal received from the requestor. For this signal
     * the type is expected to be ServiceOrder
     * @param soTarget
     * @return Empty immutable List.
     */
    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        return Collections.emptyList();
    }

    public void setEventAdapter(EventAdapter eventAdapter) {
        this.eventAdapter = eventAdapter;
    }

    public void setSubStatusList(List<Integer> subStatusList) {
        this.subStatusList = subStatusList;
    }
}
