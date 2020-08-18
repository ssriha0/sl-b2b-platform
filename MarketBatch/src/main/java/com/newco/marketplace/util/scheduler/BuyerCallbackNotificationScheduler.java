package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.businessImpl.buyerCallbackNotification.BuyerCallbackNotificationProcessor;

public class BuyerCallbackNotificationScheduler extends ABaseScheduler implements StatefulJob {
		private static final Logger logger = Logger.getLogger(BuyerCallbackNotificationScheduler.class.getName());
		
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try{
				logger.info("BuyerCallbackNotificationScheduler-->"+new Date());
				ApplicationContext applicationContext = getApplicationContext(context);
				BuyerCallbackNotificationProcessor callbackProcessor = (BuyerCallbackNotificationProcessor)applicationContext.getBean("buyerCallbackNotificationProcessor");
				callbackProcessor.processAlerts();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}


