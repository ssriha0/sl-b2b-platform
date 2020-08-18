
package com.newco.batch.ivr;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.serviceorder.SOEventProcessor;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.util.scheduler.ABaseScheduler;

/**
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 00:40:36 $
 */
public class IVROnsiteVisitBatchProcessor extends ABaseScheduler implements Job{

	private static final Logger logger= Logger.getLogger(IVROnsiteVisitBatchProcessor.class);


	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//ApplicationContext applicationContext = MPSpringLoaderPlugin.ctx;//applicationContext.xml");
		try {
			ApplicationContext applicationContext = getApplicationContext(context);
		 
			SOEventProcessor soEventProcessor = (SOEventProcessor)applicationContext.getBean("soEventProcessor");
		
			soEventProcessor.execute();
		} catch (BusinessServiceException e) {
			logger.error("IVROnsiteVisitBatchProcessor failed",e);
			throw new JobExecutionException("IVROnsiteVisitBatchProcessor failed",e);
		}catch (Exception e1) {
			logger.error("IVROnsiteVisitBatchProcessor failed loading application context",e1);
			throw new JobExecutionException("IVROnsiteVisitBatchProcessor failed loading application context",e1);
		}
		
	}
	
	public static void main(String args[]){
		IVROnsiteVisitBatchProcessor a= new IVROnsiteVisitBatchProcessor();
		try {
			a.execute(null);
		} catch (JobExecutionException e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}
}
