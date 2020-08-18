package com.servicelive.orderfulfillment.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.decision.ProcessingPathCheck;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;

public class UpdateSubStatusCmd extends SOCommand {
	private static final Logger logger = Logger.getLogger(UpdateSubStatusCmd.class);

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.info("Inside UpdateSubStatusCmd ");
		ServiceOrder so = getServiceOrder(processVariables);
		String subStatus = SOCommandArgHelper.extractStringArg(processVariables, 1);
		if (subStatus == null) {
			logger.info("Inside UpdateSubStatusCmd subStatus null1");

            // remove the sub status
            so.setWfSubStatusId(null);
		}
		else if ("NULL".equals(subStatus.toUpperCase())) {
			logger.info("Inside UpdateSubStatusCmd subStatus null2");

            so.setWfSubStatusId(null);
        }else{
        	try {
	            LegacySOSubStatus soSubStatus = LegacySOSubStatus.valueOf(subStatus.toUpperCase());
				logger.info("Inside UpdateSubStatusCmd subStatus :"+ soSubStatus);
	            so.setWfSubStatusId(soSubStatus.getId());
	            /**This code will delete the routed resources from service order if 
	             * the service order is created on draft with Recall Provider Not available sub status*/
	            if(soSubStatus.getId() == 139){
	            	logger.info("Removing Routed Resources from the order :"+ so.getSoId());
	            	serviceOrderDao.deleteSoRoutedProviders(so.getSoId());
	            }
            }
        	catch (IllegalArgumentException e) {
                so.setWfSubStatusId(null);
        	}
        }
		serviceOrderDao.update(so);
	}

}
