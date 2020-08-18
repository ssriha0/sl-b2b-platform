/**
 * 
 */
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.serviceorder.UpdateServiceOrderProcessor;

/**
 * @author schavda
 *
 */
public class SOActiveExpireScheduler extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(SOActiveExpireScheduler.class.getName());
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
			logger.info("SOActiveExpireScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			UpdateServiceOrderProcessor updateSOProcessor = (UpdateServiceOrderProcessor)applicationContext.getBean("updateSOBatchProcess");
			updateSOProcessor.execute();
						
		}
		catch(Exception e){
			logger.error("SOActiveExpireScheduler-->EXCEPTION-->", e);
			
		}
	}
}
