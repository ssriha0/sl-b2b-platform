package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.AWSStatusRecordNotification.AWSStatusRecordNotificationProcessor;

public class AWSStatusRecordNotificationScheduler extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(AWSStatusRecordNotificationScheduler.class.getName());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			logger.info("AWSStatusRecordNotificationScheduler-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			AWSStatusRecordNotificationProcessor awsStatusRecordProcessor = (AWSStatusRecordNotificationProcessor) applicationContext
					.getBean("awsStatusRecordNotificationProcessor");
			awsStatusRecordProcessor.process();

		} catch (Exception e) {
			logger.error("AWSStatusRecordNotificationScheduler---->EXCEPTION-->", e);
			throw new JobExecutionException(e);
		}

	}

}
