package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.background.BackgroundChkRecertificationProcess;

//R11_2
//SL-20437
public class BackgroundCheckScheduler extends ABaseScheduler 
			implements Job {
	
	private static final Logger logger = Logger.getLogger(BackgroundCheckScheduler.class.getName());
	
	

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("ProviderBackgroundCheckScheduler-->"+ new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		try{
			BackgroundChkRecertificationProcess processor = (BackgroundChkRecertificationProcess)applicationContext.getBean("backgroundChkRecertificationProcess");
			processor.process();
		}
		catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		}
		
	}
	
}
