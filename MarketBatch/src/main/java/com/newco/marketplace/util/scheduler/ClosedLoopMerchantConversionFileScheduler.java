package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;

public class ClosedLoopMerchantConversionFileScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(ClosedLoopMerchantConversionFileScheduler.class.getName());
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
		        logger.info("FullfillmentBajScheduler--> Started: "+new Date());
		        ApplicationContext applicationContext = getApplicationContext(context);
		        // IClosedLoopFileWriterImpl closedLoopFileWriter  = (IClosedLoopFileWriterImpl)applicationContext.getBean("closedLoopFileWriterImpl");
		        // closedLoopFileWriter.writeFullfillmentRecordsToClosedLoopConversionFile();
				logger.info("FullfillmentBajScheduler--> Completed: "+new Date());
		}
		catch(Exception e){
			logger.info("Caught Exception and ignoring",e);
		}
	}

	public static void main(String args[]){
		   ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
		   // ClosedLoopFileWriterImpl closedLoopFileWriter  = (ClosedLoopFileWriterImpl)applicationContext.getBean("closedLoopFileWriterImpl");
		   /* stry {
			   closedLoopFileWriter.writeFullfillmentRecordsToClosedLoopConversionFile();
			} catch (Exception e) {
				logger.info("Caught Exception and ignoring",e);
			} */

	 }

}

