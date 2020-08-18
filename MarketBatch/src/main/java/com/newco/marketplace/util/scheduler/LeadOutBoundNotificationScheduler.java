package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.leadOutBoundNotification.LeadOutBoundNotificationProcess;

public class LeadOutBoundNotificationScheduler extends ABaseScheduler implements Job {
	
	private static final Logger logger = Logger.getLogger(LeadOutBoundNotificationScheduler.class.getName());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("LeadOutBoundNotificationScheduler-->"+ new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		try{
			LeadOutBoundNotificationProcess processor = (LeadOutBoundNotificationProcess)applicationContext.getBean("leadOutBoundNotificationProcess");
			processor.process();
		}
		catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		}
	}
}