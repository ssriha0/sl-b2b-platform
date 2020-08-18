package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.orderfulfillment.ProcessingBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class SetPenaltyCmd extends SOCommand {

	private ProcessingBO processingBO;
	
	@Override
	public void execute(Map<String, Object> processVariables) {
        logger.info("Starting SetPenaltyCmd.execute");
        
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        logger.debug("Before fetching cancellation amount");
        Double cancelFee = processingBO.getCancelAmount(serviceOrder.getBuyerId().intValue());
        logger.debug("After fetching cancellation amount"+cancelFee);
        serviceOrder.setCancellationFee(new BigDecimal(cancelFee));        
      
        
        serviceOrder.setSpendLimitLabor(new BigDecimal(cancelFee));
		serviceOrder.setSpendLimitParts(new BigDecimal(0.0));
		serviceOrder.setFinalPriceLabor(new BigDecimal(cancelFee));
		serviceOrder.setFinalPriceParts(new BigDecimal(0.0));
		serviceOrderDao.update(serviceOrder);
        
        
        
        logger.info("Finished with SetPenaltyCmd.execute method.");
	}

	public void setProcessingBO(ProcessingBO processingBO) {
        this.processingBO = processingBO;
    }
  
}