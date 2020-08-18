package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.service.DataSourceService;

public class TriggeredEmailScheduler extends ABaseScheduler implements StatefulJob {
	private static final Logger logger = Logger.getLogger(TriggeredEmailScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("TriggeredEmailScheduler-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			DataSourceService triggerEmail = (DataSourceService) applicationContext
					.getBean("datasourceService");
			String jobName = context.getJobDetail().getName();
			triggerEmail.processDataSources(jobName);
			}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
