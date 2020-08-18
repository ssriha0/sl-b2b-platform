package com.servicelive.bus.event.order;

import java.util.Collections;
import java.util.List;

import com.servicelive.bus.MessageSelector;
import com.servicelive.bus.event.EventHeader;

/**
 * User: Mustafa Motiwala
 * Date: Mar 31, 2010
 * Time: 11:41:16 PM
 */
public class ServiceOrderMessageSelector implements MessageSelector {
    private List<String> buyers = Collections.emptyList();
    private List<OrderEventType> orderEventType = Collections.emptyList();

    public String buildSelector() {
        StringBuilder returnVal = new StringBuilder();
        returnVal.append(EventHeader.EVENT_TYPE).append("= '").append(ServiceOrderEvent.class.getName()).append("'");

        //Setup interested Buyer selector
        if(!buyers.isEmpty()){
            returnVal.append(" AND ").append(EventHeader.BUYER_COMPANY_ID).append(" IN (");
            for(String buyer : buyers){
                returnVal.append("'").append(buyer).append("',");
            }
            returnVal.deleteCharAt(returnVal.length() -1); //Remove trailing ,
            returnVal.append(") ");
        }

        if(!orderEventType.isEmpty()){
            returnVal.append(" AND ").append(ServiceOrderEvent.ORDER_EVENT).append(" IN (");
            for(OrderEventType orderEvent:orderEventType){
                returnVal.append("'").append(orderEvent).append("',");
            }
            returnVal.deleteCharAt(returnVal.length() -1); //Remove trailing ,
            returnVal.append(")");
        }
        
        return returnVal.toString();
    }

    public void setBuyers(List<String> buyers) {
        this.buyers = buyers;
    }

    public void setOrderEventType(List<OrderEventType> orderEventType) {
        this.orderEventType = orderEventType;
    }
}
