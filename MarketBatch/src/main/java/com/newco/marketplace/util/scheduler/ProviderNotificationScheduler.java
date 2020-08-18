package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.businessImpl.vibePostAPI.NotificationAlertProcessor;

public class ProviderNotificationScheduler extends ABaseScheduler implements StatefulJob {
		private static final Logger logger = Logger.getLogger(ProviderNotificationScheduler.class.getName());
		
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try{
				logger.info("ProviderNotificationScheduler-->"+new Date());
				ApplicationContext applicationContext = getApplicationContext(context);
				NotificationAlertProcessor pushAlert = (NotificationAlertProcessor)applicationContext.getBean("notificationAlertProcessor");
				pushAlert.processAlerts();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}


