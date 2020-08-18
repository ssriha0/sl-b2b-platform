package com.servicelive.orderfulfillment.command;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class AutoCloseHSRCmd extends SOCommand {
	
	
	public void execute(Map<String,Object> processVariables) {
		logger.info("Starting AutoCloseHSRCmd.execute");
       	ServiceOrder serviceOrder = getServiceOrder(processVariables);
       	String status = OrderfulfillmentConstants.STATUS_WAITING;
       	Integer emailInd = 0;
       	Integer noOfRetries  = 0;
       	String subStatus = null;
       	Integer activeInd = 0;
       	Date processAfterDate = null;
	
		if (null != serviceOrder && StringUtils.isNotBlank(serviceOrder.getSoId()) && null!= serviceOrder.getSOWorkflowControls() ) {
			logger.info("method of closure value:"+serviceOrder.getSOWorkflowControls().getMethodOfClosure());
			if((StringUtils.isNotBlank(serviceOrder.getSOWorkflowControls().getMethodOfClosure())) && (serviceOrder.getSOWorkflowControls().getMethodOfClosure().equalsIgnoreCase("PENDING AUTO CLOSE"))){
				//Fetching time interval from DB
				logger.info("Fetching autoclose time interval");
				Integer timeInterval = serviceOrderDao.getInhomeAutoCloseTimeInterval(OrderfulfillmentConstants.TIME_INTERVAL);
				logger.info("time interval value:"+timeInterval);
				
				//Calculating process_after_date in so_inhome_autoclose
				Calendar processDate = Calendar.getInstance();
				logger.info("Current date value:"+processDate); 
				processDate.add(Calendar.MINUTE, timeInterval);
				processAfterDate = processDate.getTime();
				logger.info("Process after date value:"+processAfterDate); 
				
				//Setting status as "PENDING_AUTO_CLOSE"
				subStatus = OrderfulfillmentConstants.PENDING_AUTO_CLOSE;
				activeInd = 1;
				
				//Insertion/Updationinto so_inhome_auto_close
				logger.info("Starting insertion/updation into so_inhome_auto_close");
				serviceOrderDao.insertSOInhomeAutoclose(serviceOrder.getSoId(),status,emailInd,noOfRetries,processAfterDate,subStatus,activeInd);
				
			}
			else{
					
				processAfterDate = null;
				logger.info("Process after date value:"+processAfterDate); 
				
				//Setting status as "PENDING_MANUAL_REVIEW"
				subStatus = OrderfulfillmentConstants.MANUAL_REVIEW;
				activeInd = 0;
				status = OrderfulfillmentConstants.STATUS_MANUAL;
				
				//Insertion/Updationinto so_inhome_auto_close
				logger.info("Starting insertion/updation into so_inhome_auto_close");
				serviceOrderDao.insertSOInhomeAutoclose(serviceOrder.getSoId(),status,emailInd,noOfRetries,processAfterDate,subStatus,activeInd);
			}

		}
	
    }



}

