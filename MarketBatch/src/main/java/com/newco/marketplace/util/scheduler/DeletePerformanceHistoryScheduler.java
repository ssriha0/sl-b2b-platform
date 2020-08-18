package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.tier.performance.DeletePerformanceHistoryProcessor;

public class DeletePerformanceHistoryScheduler extends ABaseScheduler implements StatefulJob{
	private static final Logger logger = Logger.getLogger(DeletePerformanceHistoryScheduler.class.getName());
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
			logger.info("DeletePerformanceHistoryScheduler-->"+new Date());
			long startTime =System.currentTimeMillis();
			logger.info("DeletePerformanceHistoryScheduler Start time:"+startTime);
			ApplicationContext applicationContext = getApplicationContext(context);
			DeletePerformanceHistoryProcessor deleteProcessor = (DeletePerformanceHistoryProcessor)applicationContext.getBean("deletePerfHistoryProcess");
			deleteProcessor.execute();
			long endTime =System.currentTimeMillis()-startTime;
			logger.info("DeletePerformanceHistoryScheduler time taken:"+endTime);
						
		}
		catch(Exception e){
			logger.error("DeletePerformanceHistoryScheduler-->EXCEPTION-->", e);
			
		}
	}
}
