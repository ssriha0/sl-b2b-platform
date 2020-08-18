package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.AWSStatusRecordNotification.FailedCommunicationToAwsNotificationProcessor;

public class FailedCommunicationToAwsNotificationScheduler extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(FailedCommunicationToAwsNotificationScheduler.class.getName());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("FailedCommunicationToAwsNotificationScheduler-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			FailedCommunicationToAwsNotificationProcessor failedCommunicationToAwsNotificationProcessor = (FailedCommunicationToAwsNotificationProcessor) applicationContext
					.getBean("failedCommunicationToAwsNotificationProcessor");
			failedCommunicationToAwsNotificationProcessor.process();
		} catch (Exception e) {
			logger.error("FailedCommunicationToAwsNotificationScheduler---->EXCEPTION-->", e);
			e.printStackTrace();
		}
	}
}
