package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.performancemetric.PerformanceMetricProcessor;

// public class PerformanceMetricScheduler extends ABaseScheduler implements StatefulJob {
public class PerformanceMetricScheduler extends ABaseScheduler implements Job {
	
	private static final Logger logger = Logger.getLogger(PerformanceMetricScheduler.class.getName());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("---------------------------------------------------");
		logger.info("-- START: PerfromanceMetricScheduler: " + new Date());
		
		ApplicationContext applicationContext = getApplicationContext(context);
		try{
			PerformanceMetricProcessor processor = (PerformanceMetricProcessor) applicationContext.getBean("performanceMetricProcessor");
			processor.process();
		} catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		}
		logger.info("-------------------------------------------------");
		logger.info("-- END: PerfromanceMetricScheduler: " + new Date());
	}
}