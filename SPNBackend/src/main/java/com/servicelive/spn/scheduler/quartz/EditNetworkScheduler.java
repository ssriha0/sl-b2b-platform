package com.servicelive.spn.scheduler.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.servicelive.spn.services.network.EditNetworkService;

public class EditNetworkScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(EditNetworkScheduler.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("*****************Starting " + this.getClass().getSimpleName());
		try {
			EditNetworkService service = getEditNetworkService(context);
			service.cleanAliases();
		} catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		} finally {
			logger.info("*****************Stopping " + this.getClass().getSimpleName());
		}
		
	}

	private EditNetworkService getEditNetworkService(JobExecutionContext context) throws SchedulerException {
		ApplicationContext applicationContext = getApplicationContext(context);
		return (EditNetworkService) applicationContext.getBean("editNetworkService");
	}
}
