/**
 * 
 */
package com.servicelive.orderfulfillment.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOScheduleHistory;
import com.servicelive.orderfulfillment.domain.SOScheduleStatus;
import com.servicelive.orderfulfillment.domain.ServiceOrder;


/**
 * @author Infosys
 *
 */
public class UpdateSoHdrOnInhomeAutoAccept extends SOCommand {
	public static final String AUTO_ACCEPT_LOGGING_FIRM_NAME="AUTO_ACCEPT_LOGGING_FIRM_NAME";
	public static final String AUTO_ACCEPT_LOGGING_FIRM_ID="AUTO_ACCEPT_LOGGING_FIRM_ID";
	private Logger logger = Logger.getLogger(getClass());
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		if(null!=serviceOrder){
			    List<RoutedProvider>  providerVO = serviceOrder.getRoutedResources();
	    	    if(null!=providerVO && providerVO.size()>0){
	    			serviceOrder.setAcceptedProviderId(serviceOrder.getInhomeAcceptedFirm());
	    			processVariables.put(AUTO_ACCEPT_LOGGING_FIRM_NAME, serviceOrder.getInhomeAcceptedFirmName());
	    			processVariables.put(AUTO_ACCEPT_LOGGING_FIRM_ID, serviceOrder.getInhomeAcceptedFirm());
	    			serviceOrder.setAssignmentType("FIRM");
				    }
	    	    	//inserting values in so_schedule
	    	    	SOScheduleStatus soSchedule =  new SOScheduleStatus();
			        soSchedule.setSoId(serviceOrder.getSoId());
			        soSchedule.setScheduleStatusId(OFConstants.SCHEDULE_NEEDED);
			        soSchedule.setVendorId(serviceOrder.getAcceptedProviderId().intValue());
			        soSchedule.setCreatedDate(new Date());
			        soSchedule.setModifiedDate(new Date());
			        soSchedule.setModifiedBy(OFConstants.MODIFIED_BY);
			        serviceOrder.setSoScheduleStatus(soSchedule);
			        
			        //inserting values in so_schedule_history
			        SOScheduleHistory scheduleHistory = new SOScheduleHistory();
			        scheduleHistory.setServiceOrder(serviceOrder);
			        scheduleHistory.setVendorId(serviceOrder.getAcceptedProviderId().intValue());
			        scheduleHistory.setScheduleStatusId(OFConstants.SCHEDULE_NEEDED);
			        scheduleHistory.setModifiedBy(OFConstants.MODIFIED_BY);
			        serviceOrder.getScheduleHistory().add(scheduleHistory);
			        serviceOrderDao.update(serviceOrder);

	    	   }
	}
}
