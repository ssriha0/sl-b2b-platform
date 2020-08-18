package com.servicelive.orderfulfillment.orderprep.processor.hsr;

import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderHdrEnhancer;

public class HsrOrderHdrEnhancer extends AbstractOrderHdrEnhancer {
	@Override
	protected void setMiscOrderHdrInfo(ServiceOrder serviceOrder,
			BuyerOrderTemplate buyerOrderTemplate) {
		
		SOTask primaryTask = serviceOrder.getPrimaryTask();
		serviceOrder.setSowTitle(
				String.format("%s - %s", 
						fixStringIfNull(primaryTask.getExternalSku(), ""), 
						fixStringIfNull(primaryTask.getTaskName(), "")));
		
		serviceOrder.setSowDs(
				String.format("%s%s",
						fixStringIfNull(primaryTask.getTaskComments(), ""),
						fixStringIfNull(serviceOrder.getSowDs(), "")));
	}

	private String fixStringIfNull(String s, String defaultValue) {
		return s == null ? defaultValue : s;
	}
}
