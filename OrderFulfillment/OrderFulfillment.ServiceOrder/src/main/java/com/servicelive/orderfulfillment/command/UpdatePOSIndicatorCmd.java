package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.ProcessingBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class UpdatePOSIndicatorCmd extends SOCommand {

	private ProcessingBO processingBO;
	
	@Override
	public void execute(Map<String, Object> processVariables) {
        logger.debug("Starting UpdatePOSIndicatorCmd.execute");
        
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        String soId = serviceOrder.getSoId();
        processingBO.updatePOSCancellationIndicator(soId, serviceOrder);
        
        logger.debug("Finished with UpdatePOSIndicatorCmd.execute method.");
	}

	public void setProcessingBO(ProcessingBO processingBO) {
        this.processingBO = processingBO;
    }
  
}