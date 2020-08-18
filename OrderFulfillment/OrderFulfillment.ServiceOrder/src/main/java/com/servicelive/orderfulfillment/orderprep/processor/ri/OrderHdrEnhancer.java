package com.servicelive.orderfulfillment.orderprep.processor.ri;

import java.util.List;

import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderHdrEnhancer;

public class OrderHdrEnhancer extends AbstractOrderHdrEnhancer {

    @Override
    protected void setMiscOrderHdrInfo(ServiceOrder serviceOrder, BuyerOrderTemplate buyerOrderTemplate) {
    	if(serviceOrder.isCreatedViaAPI()){
    		serviceOrder.setSowTitle(serviceOrder.getSowTitle());
    		serviceOrder.setSowDs(serviceOrder.getSowDs());
    	}else{
    		String title =serviceOrder.getSowTitle();
    		if(null != serviceOrder.getPrimaryTask()){
    			title = title + "-" + serviceOrder.getPrimaryTask().getTaskName();
    		}
        serviceOrder.setSowTitle(title);
        serviceOrder.setSowDs(serviceOrder.getSowDs() + getAllTaskComments(serviceOrder.getTasks()));
    	}
    }

    private String getAllTaskComments(List<SOTask> taskList) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (SOTask task : taskList) {
            stringBuilder.append(task.getTaskComments()).append("\n");
        }
        return stringBuilder.toString();
    }
    private String fixStringIfNull(String str) {
        return (str == null) ?  "" : str;
    }
}