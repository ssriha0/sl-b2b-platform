package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.vendor.VendorResourceLocationProcessor;

public class VendorResourceLocationScheduler extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(VendorResourceLocationScheduler.class);

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info(this.getClass().getSimpleName() + "-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);

			VendorResourceLocationProcessor processor = (VendorResourceLocationProcessor)applicationContext.getBean("vendorResourceLocationProcessor");
			processor.execute();
		}
		catch(Exception e){
			logger.error("Execution of " + this.getClass().getSimpleName() + " failed",e);
			throw new JobExecutionException("Execution of " + this.getClass().getSimpleName() + " failed",e);
		}
	}
}
