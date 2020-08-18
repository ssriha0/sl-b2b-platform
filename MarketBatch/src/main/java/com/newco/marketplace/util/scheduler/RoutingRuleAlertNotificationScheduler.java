package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.newco.batch.routingrules.RoutingRulesEmailNotificationProcess;

public class RoutingRuleAlertNotificationScheduler extends ABaseScheduler
		implements Job {
	
	private static final Logger logger = Logger.getLogger(RoutingRuleAlertNotificationScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date startDate = new Date();
		logger.info("starting");
		try {			
			RoutingRulesEmailNotificationProcess process = (RoutingRulesEmailNotificationProcess) getApplicationContext(context).getBean("routingRulesEmailNotificationProcess");
			process.process();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		} finally {
			Date endDate = new Date();
			logger.info("ending - job took " +  (endDate.getTime() - startDate.getTime()) + "ms");
		}
		

	}

}
