package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.csatmapping.CSATIntegrationProcessor;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class CSATMapping extends ABaseScheduler implements Job{

		private static final Logger logger = Logger.getLogger(CSATMapping.class.getName());

		public void execute(JobExecutionContext context) throws JobExecutionException {
			logger.info("CSATMapping started-->"+ new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			try {
				CSATIntegrationProcessor CSATIntegrationProcessor = (CSATIntegrationProcessor)applicationContext.getBean("CSATIntegrationProcessor");
				long startTime = System.currentTimeMillis();
				CSATIntegrationProcessor.execute();
				logger.info("CSATMapping completed-->"+ new Date());
				long endTime = System.currentTimeMillis();
				final int MILISECONDS_PER_SECOND = 1000;
				logger.info("Time taken for CSATResponseScheduler: " + (endTime - startTime) / MILISECONDS_PER_SECOND + " seconds");
			} catch (BusinessServiceException e) {
				logger.error("Job failed at-->"+new Date()+" due to--> "+e);
				throw new JobExecutionException(e.getCause());
			}
		}
}
