package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.servicelive.routingrulesengine.services.RoutingRuleAlertService;


public class RoutingRuleAlertValidationScheduler extends ABaseScheduler implements Job {
	
	private static final Logger logger = Logger.getLogger(RoutingRuleAlertValidationScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException  {
		
		Date startDate = new Date();
		logger.info("starting");
		try {
			RoutingRuleAlertService service = (RoutingRuleAlertService) getApplicationContext(context).getBean("routingRuleAlertService");
			service.processAlertsForActiveRules();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		} finally {
			Date endDate = new Date();
			logger.info("ending - job took " +  (endDate.getTime() - startDate.getTime()) + "ms");
		}
		
	}

}
