package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.businessImpl.alert.AlertProcessor;

public class AlertScheduler extends ABaseScheduler implements StatefulJob {
	private static final Logger logger = Logger.getLogger(AlertScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("AlertScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			AlertProcessor alertProcessor = (AlertProcessor)applicationContext.getBean("alertProcessor");
			alertProcessor.processAlerts("1");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

