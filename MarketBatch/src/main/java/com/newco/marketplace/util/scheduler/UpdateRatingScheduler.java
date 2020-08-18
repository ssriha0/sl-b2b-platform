package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;


import com.newco.batch.updaterating.UpdateRatingProcessor;

public class UpdateRatingScheduler  extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(UpdateRatingScheduler.class.getName());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			logger.info("UpdateRatingScheduler-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			UpdateRatingProcessor updateRatingProcessor = (UpdateRatingProcessor) applicationContext
					.getBean("updateRatingProcessor");
			updateRatingProcessor.process();

		} catch (Exception e) {
			logger.error("UpdateRatingScheduler---->EXCEPTION-->", e);
			throw new JobExecutionException(e);
		}

	}

}
