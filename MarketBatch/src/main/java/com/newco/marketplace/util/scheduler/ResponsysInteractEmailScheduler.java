package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.service.EmailAlertService;

public class ResponsysInteractEmailScheduler extends ABaseScheduler implements StatefulJob {
		private static final Logger logger = Logger.getLogger(AlertScheduler.class.getName());
		
		public void execute(JobExecutionContext context) throws JobExecutionException {
			try{
				logger.info("ResponsysInteractEmailScheduler-->"+new Date());
				ApplicationContext applicationContext = getApplicationContext(context);
				EmailAlertService emailAlert = (EmailAlertService)applicationContext.getBean("responsysInteractService");
				emailAlert.processAlert();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}