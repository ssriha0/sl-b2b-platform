package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.isleads.InsideSalesDataLoadProcessor;

/**
 * @author jkahng
 */
public class InsideSalesDataLoadScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(InsideSalesDataLoadScheduler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("InsideSalesDataLoadScheduler-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			InsideSalesDataLoadProcessor processor = (InsideSalesDataLoadProcessor) applicationContext.getBean("isDataLoadProcessor");
			processor.process();
		} catch (Exception e) {
			logger.error("isDataLoadProcessor-->EXCEPTION-->", e);
		}
	}
}
