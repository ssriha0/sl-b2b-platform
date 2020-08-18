package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.BuyerCallbackNotification.IBuyerCallbackNotificationBO;

public class BuyerCallbackNotificationHistoryScheduler extends ABaseScheduler implements StatefulJob{

	private static final Logger logger= Logger.getLogger(BuyerCallbackNotificationHistoryScheduler.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime =System.currentTimeMillis();
		logger.info("BuyerCallbackNotificationHistoryScheduler start-->"+ new Date());
		try {
			ApplicationContext applicationContext = getApplicationContext(context); 
			IBuyerCallbackNotificationBO buyerCallbackNotificationBO = 
					(IBuyerCallbackNotificationBO) applicationContext.getBean("buyerCallbackNotificationBOImpl");
			buyerCallbackNotificationBO.insertBuyerCallbackNotificationHistory();
			logger.info("BuyerCallbackNotificationHistoryScheduler executed successfully");
		}catch(Exception e){
			logger.error("Error occured while running the BuyerCallbackNotificationHistoryScheduler process",e);
			throw new JobExecutionException("Error occured while running the BuyerCallbackNotificationHistoryScheduler process",e);
		}
		logger.info("BuyerCallbackNotificationHistoryScheduler end-->"+ new Date());
		long endTime =(System.currentTimeMillis()-startTime)/1000;
		logger.info("BuyerCallbackNotificationHistoryScheduler execution time : "+endTime+" secs");
		
	}

}
