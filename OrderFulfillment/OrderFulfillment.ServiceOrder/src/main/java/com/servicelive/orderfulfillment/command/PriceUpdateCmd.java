package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class PriceUpdateCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM PriceUpdateCmd Command ***");
		
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		Double cancelAmount = 0.0;
      
		if(null != processVariables.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT)){
			cancelAmount = Double.valueOf((String)processVariables.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT));
		}         
		serviceOrder.setSpendLimitLabor(new BigDecimal(cancelAmount));
		serviceOrder.setSpendLimitParts(new BigDecimal(0.0));
		serviceOrder.setFinalPriceLabor(new BigDecimal(cancelAmount));
		serviceOrder.setFinalPriceParts(new BigDecimal(0.0));
		serviceOrderDao.update(serviceOrder);
		
		logger.debug("finished with PriceUpdateCmd command");
	}

}
