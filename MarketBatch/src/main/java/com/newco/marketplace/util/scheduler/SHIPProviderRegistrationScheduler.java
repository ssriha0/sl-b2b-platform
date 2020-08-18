
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.shipregistration.SHIPProviderRegistrationProcessor;

/*
 * Batch which can be run to register SHIP providers in ServiceLive
 */
public class SHIPProviderRegistrationScheduler extends ABaseScheduler implements StatefulJob{
	private static final Logger logger = Logger.getLogger(SHIPProviderRegistrationScheduler.class.getName());
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
			logger.info("SHIPProviderRegistrationScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			SHIPProviderRegistrationProcessor providerRegProcessor = (SHIPProviderRegistrationProcessor)applicationContext.getBean("shipProviderRegProcess");
			providerRegProcessor.execute();
						
		}
		catch(Exception e){
			logger.error("SHIPProviderRegistrationScheduler-->EXCEPTION-->", e);
			
		}
	}

}
