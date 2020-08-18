package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.marketplatform.common.vo.FeeType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;

public class ScheduleUpdateCommand extends SOCommand {

	
	
	@Override
	public void execute(Map<String, Object> processVariables) {
        logger.info("Starting ScheduleUpdateCommand.execute");
         long startTime = System.currentTimeMillis();
		 ServiceOrder so = getServiceOrder(processVariables);
		 SOSchedule reschedule = so.getReschedule();
		 SOWorkflowControls soWorkflowControls =so.getSOWorkflowControls();
		 Integer count=soWorkflowControls.getAutoAcceptRescheduleRequestCount();
		 if(null!=count){
		 count=count-1;
		 }
		 soWorkflowControls.setAutoAcceptRescheduleRequestCount(count);
		 so.setSOWorkflowControls(soWorkflowControls);
	     so.setReschedule(null);
	     
	     //SL-19291 Deleting buyer preferred reschedule details when auto accept reschedule request.
	     so.getSOWorkflowControls().setBuyerSchedule(null);
	     
	     so.setSchedule(reschedule);
	     serviceOrderDao.update(so);
	     
	     // R12.0 Sprint 2 Updating current appointment date and time in so_trip when provider auto accept a reschedule request.
	     serviceOrderDao.updateSOTripForReschedule(reschedule, so.getSoId());
	}

	
}