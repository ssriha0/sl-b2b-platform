package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.feedback.FeedbackMailProcessor;

/**
 * @author lgeorg6
 * This class is used to send Feedback for the new Order Management Tool to a mailing 
 * list according to a schedule. Currently this is scheduled to run ever week.
 */

public class FeedbackMailScheduler extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(FeedbackMailScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("FeedbackMailScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			FeedbackMailProcessor feedbackMailer = (FeedbackMailProcessor)applicationContext.getBean("feedMailProcessor");
			feedbackMailer.process();
		}
		catch(Exception e){
			logger.error("FeedbackMailScheduler-->EXCEPTION-->", e);
		}
	}

}