package com.newco.marketplace.util.scheduler;

import java.util.Date;


import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import com.newco.batch.inhomeAutoClose.InHomeAutoCloseProcess;



/**
 * @author Infosys
 * This batch will process the data in the buyer_outbound_notification table and invoke
 * the SO closure for HSR Buyer
 */

public class InHomeAutoCloseScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(InHomeAutoCloseScheduler.class.getName());
	
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
		logger.info("InHomeAutoCloseScheduler-->" + new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		InHomeAutoCloseProcess inhomeAutoCloseProcessor = (InHomeAutoCloseProcess) applicationContext.getBean("inhomeAutoCloseProcessor");
		try {
			inhomeAutoCloseProcessor.process();
		} 
		catch (Exception e) {
			logger.error("InHomeAutoCloseScheduler-->EXCEPTION-->", e);
			throw new JobExecutionException(e);
		}
	}
	
}
