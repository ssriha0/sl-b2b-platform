package com.newco.marketplace.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.service.EmailAlertService;


public class RTMScheduler extends EmailBaseScheduler implements StatefulJob {
	private static final Logger logger = Logger.getLogger(RTMScheduler.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("RTMScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			EmailAlertService emailAlertService = (EmailAlertService)applicationContext.getBean("rtmAlertService");
			emailAlertService.processAlert();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

