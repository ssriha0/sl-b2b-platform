package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.background.BackgroundCheckRecertificationProcess;


public class ProviderBackgroundCheckScheduler extends ABaseScheduler 
			implements Job {
	
	private static final Logger logger = Logger.getLogger(ProviderBackgroundCheckScheduler.class.getName());
	
	

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("ProviderBackgroundCheckScheduler-->"+ new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		try
		{
		BackgroundCheckRecertificationProcess processor = (BackgroundCheckRecertificationProcess)applicationContext.getBean("backgroundCheckRecertificationProcess");
		processor.process();
		}
		catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		}
		
	}

	
	
	
}
