package com.newco.marketplace.util.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.smsdatasynch.SMSDataSynchProcess;

/**
 * @author Infosys
 * SL-18979
 * This batch will process the process the files from Vibes
 * to synch the opt-in opt-out data of the providers in ServiceLive
 */

public class SMSDataSynchScheduler extends ABaseScheduler implements Job {


    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
		ApplicationContext applicationContext = getApplicationContext(context);
		SMSDataSynchProcess smsDataSynch = (SMSDataSynchProcess) applicationContext.getBean("smsDataSynchProcess");
		
		try {
			smsDataSynch.processFile();
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException("SMSDataSynchScheduler failed! Error Message: " + e.getMessage(), e);
		}
	}
 
       
	
}
