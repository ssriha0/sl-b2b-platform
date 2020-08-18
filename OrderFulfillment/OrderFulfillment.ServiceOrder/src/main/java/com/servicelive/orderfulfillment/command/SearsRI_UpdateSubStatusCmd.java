package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;

public class SearsRI_UpdateSubStatusCmd extends UpdateSubStatusCmd {

	@Override
	public void execute(Map<String, Object> processVariables) {
				
		ServiceOrder so = getServiceOrder(processVariables);
		String rescheduleRequest = SOCommandArgHelper.extractStringArg(processVariables, 1);
		
		//logic to determine SubStatus based on Custom Ref field
		String dateCalculationMethod = so.getCustomRefValue(SOCustomReference.CREF_DATE_CALC_METHOD);
		LegacySOSubStatus subStatus = null;
		
		if (rescheduleRequest.equalsIgnoreCase("TRUE") || rescheduleRequest.equalsIgnoreCase("FALSE")) { // for Sears RI specific command invocation
			if (StringUtils.isNotBlank(dateCalculationMethod)) {
				if (rescheduleRequest.equalsIgnoreCase("TRUE")) { //isRescheduleReqeust 
					subStatus = LegacySOSubStatus.SCHEDULE_CONFIRMED;
				} else {
					if (dateCalculationMethod.equals("N") || dateCalculationMethod.equals("P")) {
						//subStatus = LegacySOSubStatus.SCHEDULE_CONFIRMED;
					} else {
						subStatus = LegacySOSubStatus.SCHEDULING_NEEDED;
					}
				}// End of If-Else
			}// End of If
			
			if(subStatus != null){
	            //remove the sub status and set it to null
				so.setWfSubStatusId(subStatus.getId());
				so.setWfSubStatus(subStatus.getDescription());
	        }else{
	        	so.setWfSubStatusId(null);
	        	so.setWfSubStatus("");
	        }
			serviceOrderDao.update(so);
		} else {	// for Generic Cmd  
			super.execute(processVariables);
		}
	}

}
