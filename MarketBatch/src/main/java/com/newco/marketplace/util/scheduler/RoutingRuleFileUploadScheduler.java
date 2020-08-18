package com.newco.marketplace.util.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.servicelive.routingrulesengine.services.RoutingRuleImportService;

public class RoutingRuleFileUploadScheduler extends ABaseScheduler implements Job {

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 * This starts a batch job to start processing the routing rule files
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ApplicationContext applicationContext = getApplicationContext(context);
		RoutingRuleImportService fileProcessor = (RoutingRuleImportService)applicationContext.getBean("routingRuleImportService");
		fileProcessor.startFileProcessing();
	}
}
