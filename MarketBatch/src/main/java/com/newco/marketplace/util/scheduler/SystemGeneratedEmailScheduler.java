package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.systemgeneratedemail.ISystemGeneratedBO;

public class SystemGeneratedEmailScheduler extends ABaseScheduler implements StatefulJob {
	private static final Logger logger = Logger.getLogger(SystemGeneratedEmailScheduler.class.getName());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime =System.currentTimeMillis();
		logger.info("SystemGeneratedEmailScheduler start-->"+ new Date());
		try {
			ApplicationContext applicationContext = getApplicationContext(context); 
			ISystemGeneratedBO systemGeneratedBO = 
					(ISystemGeneratedBO) applicationContext.getBean("systemGeneratedEmailBO");
			systemGeneratedBO.processSystemGeneratedEmail();
		} catch (Exception e) {
			logger.error("Exception on executing processSystemGeneratedEmail : " + e);
			throw new JobExecutionException();
		}
		logger.info("SystemGeneratedEmailScheduler end-->"+ new Date());
		long endTime =System.currentTimeMillis()-startTime;
		logger.info("SystemGeneratedEmailScheduler execution time : "+endTime+" milli secs");
	}
}
