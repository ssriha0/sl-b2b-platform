package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.inhomeOutboundNotification.InhomeOutBoundNotificationProcess;

/**
 * @author Infosys
 * This batch will process the data in the buyer_outbound_notification table and invoke
 * NPS web service to notify the the status changes, sub status changes, add note or reschedule for hsr orders
 */

public class InhomeOutBoundMessageScheduler extends ABaseScheduler implements Job {
	
	private static final Logger logger = Logger.getLogger(InhomeOutBoundMessageScheduler.class.getName());
	
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
		logger.info("InhomeOutBoundMessageScheduler-->" + new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		InhomeOutBoundNotificationProcess inhomeOutboundProcessor = (InhomeOutBoundNotificationProcess) applicationContext.getBean("inhomeOutboundProcessor");
		try {
			inhomeOutboundProcessor.processMessages();
		} 
		catch (Exception e) {
			logger.error("InhomeOutBoundMessageScheduler-->EXCEPTION-->", e);
			throw new JobExecutionException(e);
		}
	}
}
