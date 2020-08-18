package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.tier.performance.PerformanceProcessor;


public class PerformanceScheduler extends ABaseScheduler implements StatefulJob{
	private static final Logger logger = Logger.getLogger(PerformanceScheduler.class.getName());
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
			logger.info("PerformanceScheduler-->"+new Date());
			long startTime =System.currentTimeMillis();
			logger.info("PerformanceScheduler Start time:"+startTime);
			ApplicationContext applicationContext = getApplicationContext(context);
			PerformanceProcessor providerRegProcessor = (PerformanceProcessor)applicationContext.getBean("tierPerformanceProcess");
			providerRegProcessor.execute();
			long endTime =(System.currentTimeMillis()-startTime)/1000;
			logger.info("PerformanceScheduler time taken:"+endTime+"secs");
						
		}
		catch(Exception e){
			logger.error("PerformanceScheduler-->EXCEPTION-->", e);
			
		}
	}
}
