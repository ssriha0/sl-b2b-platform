package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class PriceUpdateForPndngCnclCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** jBPM PriceUpdateForPndngCnclCmd Command ***");
		
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		Double cancelAmount = 0.0;
      
		if(null != processVariables.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT)){
			cancelAmount = Double.valueOf((String)processVariables.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT));
		}         
		serviceOrder.setSpendLimitLabor(new BigDecimal(cancelAmount));
		serviceOrder.setSpendLimitParts(new BigDecimal(0.0));
		serviceOrder.setFinalPriceParts(new BigDecimal(0.0));
		
		//SL-18007 - to set correct action as Service Order Pending Cancellation
		List<SOPriceChangeHistory>  soPriceChangeHistoryList =  serviceOrder.getSoPriceChangeHistory();
		int listSize = 0;
		if (null != soPriceChangeHistoryList){
			listSize = soPriceChangeHistoryList.size();
			if (listSize > 0){
            	SOPriceChangeHistory latestSOPriceChangeHistory = soPriceChangeHistoryList.get(listSize-1);
            	logger.info(latestSOPriceChangeHistory);
            	if (latestSOPriceChangeHistory.getAction().equals(OFConstants.ORDER_CANCELLED)){
            		latestSOPriceChangeHistory.setAction(OFConstants.ORDER_PENDING_CANCELLATION);
            	}
			}
		}
		serviceOrderDao.update(serviceOrder);
		
		logger.debug("finished with PriceUpdateForPndngCnclCmd command");
	}

}
