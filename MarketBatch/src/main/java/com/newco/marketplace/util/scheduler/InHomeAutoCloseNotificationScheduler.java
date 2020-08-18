package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.inhomeAutoCloseNotification.InhomeAutoCloseNotificationProcess;


/**
 * @author Infosys
 * This batch will process the data in the so_inhome_auto_close table and 
 * send notification to Prod Support Team for failures.
 */

public class InHomeAutoCloseNotificationScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(InHomeAutoCloseNotificationScheduler.class.getName());
	
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
		logger.info("InHomeAutoCloseNotificationScheduler-->" + new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		InhomeAutoCloseNotificationProcess inhomeAutoCloseProcessor = (InhomeAutoCloseNotificationProcess) applicationContext.getBean("inhomeAutoCloseNotificationProcessor");
		try {
			inhomeAutoCloseProcessor.process();
		} 
		catch (Exception e) {
			logger.error("InHomeAutoCloseNotificationScheduler-->EXCEPTION-->", e);
			throw new JobExecutionException(e);
		}
	}
	
}
