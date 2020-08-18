package com.servicelive.orderfulfillment.orderprep.processor.assurant;

import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderHdrEnhancer;

public class AssurantOrderHdrEnhancer extends AbstractOrderHdrEnhancer {
    @Override
    protected void setMiscOrderHdrInfo(ServiceOrder serviceOrder, BuyerOrderTemplate buyerOrderTemplate) {
    	String title = buyerOrderTemplate.getTitle();
    	if(null != serviceOrder.getPrimaryTask()){
    		title = title + "-" + serviceOrder.getPrimaryTask().getTaskName();
    	}
        serviceOrder.setSowTitle(title);
    }
}
