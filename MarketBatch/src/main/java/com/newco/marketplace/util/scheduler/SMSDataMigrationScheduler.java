package com.newco.marketplace.util.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.smsdatamigration.SMSDataMigrationProcess;

/**
 * @author Infosys
 * SL-18979: This will migrate the resources whose alt contact 
 * method is SMS and not having an entry for the same in 
 * vendor_sms_subscription table
 * 
 */

public class SMSDataMigrationScheduler extends ABaseScheduler implements Job {


    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
		ApplicationContext applicationContext = getApplicationContext(context);
		SMSDataMigrationProcess smsDataMigration = (SMSDataMigrationProcess) applicationContext.getBean("smsDataMigrationProcess");
		
		try {
			smsDataMigration.processSmsRecords();
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException("SMSDataMigrationScheduler failed! Error Message: " + e.getMessage(), e);
		}
	}
 
       
	
}
