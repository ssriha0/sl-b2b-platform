
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.leadManagement.LeadReminderProcessor;

/*
 * Batch which can be run to send reminder mails to customers amount tomorrows appointment
 */
public class LeadReminderScheduler extends ABaseScheduler implements StatefulJob{
	private static final Logger logger = Logger.getLogger(LeadReminderScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
			logger.info("LeadReminderScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			LeadReminderProcessor reminderProcessor = (LeadReminderProcessor)applicationContext.getBean("leadReminderProcessor");
			reminderProcessor.execute();
						
		}
		catch(Exception e){
			logger.error("LeadReminderScheduler---->EXCEPTION-->", e);
			
		}
	}

}
