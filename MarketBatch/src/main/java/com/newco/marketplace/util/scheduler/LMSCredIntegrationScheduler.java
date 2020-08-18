package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.lmscredentials.integration.LMSIntegrationProcessor;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class LMSCredIntegrationScheduler extends ABaseScheduler implements Job{

	private static final Logger logger = Logger.getLogger(LMSCredIntegrationScheduler.class.getName());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("LMSCredentialUploadScheduler started-->"+ new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		try {
			LMSIntegrationProcessor lMSIntegrationProcessor = (LMSIntegrationProcessor)applicationContext.getBean("lMSIntegrationProcessor");
			lMSIntegrationProcessor.execute();
			logger.info("LMSCredentialUploadScheduler completed-->"+ new Date());
		} catch (BusinessServiceException e) {
			logger.error("Job failed at-->"+new Date()+" due to--> "+e);
			throw new JobExecutionException(e.getCause());
		}
	}
}
