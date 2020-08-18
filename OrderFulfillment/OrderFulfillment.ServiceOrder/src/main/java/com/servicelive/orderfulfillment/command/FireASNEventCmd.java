package com.servicelive.orderfulfillment.command;

import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Aug 24, 2010
 * Time: 11:43:27 AM
 */
public class FireASNEventCmd extends FireEventCmd{

    protected ServiceOrderEvent createEvent(String stateName, ServiceOrder so) {
        LegacySOStatus status = LegacySOStatus.valueOf(stateName);
        if(status.equals(LegacySOStatus.DRAFT)){
            return createDraftEvent(status, so);
        } else {
            return super.createEvent(stateName, so);
        }
    }

    private ServiceOrderEvent createDraftEvent(LegacySOStatus status, ServiceOrder so) {
        //refresh ServiceOrder to get the formula fields populated
        refreshCustomReference(so);
        SOCustomReference customReference = ServiceOrder.extractCustomReferenceFromList(SOCustomReference.CREF_INCIDENTID, so.getCustomReferences());
        if(null != customReference){
            //Step 1 - find the service order with the same incident Id
            ServiceOrder oldSO = findServiceOrder(so.getBuyerId(), customReference.getBuyerRefTypeId(), customReference.getBuyerRefValue(), so.getSoId());
            if(null != oldSO){
                //Step 2 - send the close event for the old service order
                eventAdapter.raiseEvent(getCompletedEvent(oldSO));
                //Step 3 - send the reopen event for the new service order
                return getCreateEvent(ServiceOrderEvent.ORDER_CREATION_TYPE_REOPEN, so);
            }
        }

        //Step 4 - if no old service order is found send the normal Draft status
        return getCreateEvent(ServiceOrderEvent.ORDER_CREATION_TYPE_NEW, so);
    }

    private void refreshCustomReference(ServiceOrder so) {
        for(SOCustomReference cr : so.getCustomReferences()){
            serviceOrderDao.refresh(cr);
        }
    }

    private ServiceOrderEvent getCreateEvent(String newOrReopen, ServiceOrder so){
        ServiceOrderEvent returnVal = createOrderEvent(so);
        returnVal.addHeader(EventHeader.ORDER_CREATION_TYPE, newOrReopen);
        returnVal.setEventType(OrderEventType.CREATED);
        return returnVal;
    }

    private ServiceOrderEvent getCompletedEvent(ServiceOrder so){
        ServiceOrderEvent returnVal = createOrderEvent(so);
        returnVal.setEventType(OrderEventType.COMPLETED);
        return returnVal;
    }

    private ServiceOrder findServiceOrder(Long buyerId, Integer customRefTypeId, String customRefValue, String soId){
        return serviceOrderDao.findServiceOrderByCustomReference(buyerId, customRefTypeId, customRefValue, soId);
    }
}
