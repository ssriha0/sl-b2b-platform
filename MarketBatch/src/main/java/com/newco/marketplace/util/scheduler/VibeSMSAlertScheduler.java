package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.businessImpl.vibePostAPI.VibeSMSAlertProcessor;

/**
 * @author karthik_hariharan01
 * R16_1: SL-18979 Send SMS SO Post Batch Scheduler
 *
 */
public class VibeSMSAlertScheduler extends ABaseScheduler implements StatefulJob {
	private static final Logger logger = Logger.getLogger(VibeSMSAlertScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("VibeSMSAlertScheduler Started at-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			VibeSMSAlertProcessor vibeSMSAlertProcessor = (VibeSMSAlertProcessor)applicationContext.getBean("vibeSMSAlertProcessor");
			vibeSMSAlertProcessor.processAlerts();
			logger.info("VibeSMSAlertScheduler Ended at-->"+new Date());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

