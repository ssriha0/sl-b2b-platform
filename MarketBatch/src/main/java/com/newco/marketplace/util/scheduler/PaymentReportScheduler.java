
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.reporting.PaymentReportExportProcessor;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;


public class PaymentReportScheduler extends ABaseScheduler implements Job {
	
	private static final Logger logger = Logger.getLogger(PaymentReportScheduler.class.getName());
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		double batchIdentifier = Math.random();
		logger.info("PaymentReportScheduler "+ batchIdentifier +"--> Started at "+new Date());
		logger.debug("*****************Starting " + this.getClass().getSimpleName());
		try{			
			ApplicationContext applicationContext = getApplicationContext(context);
			PaymentReportExportProcessor exportProcessor = (PaymentReportExportProcessor)applicationContext.getBean("paymentReportExportProcessor");
			exportProcessor.execute();
	       // ofacFileImpl.writeOFACRecords();
			logger.info("PaymentReportScheduler "+batchIdentifier+"--> Completed at "+new Date());    
		}
		catch(Exception e){
			logger.info("Caught Exception and ignoring",e);
		}finally{
			logger.debug("*****************Stopping " + this.getClass().getSimpleName());
		}
	}
	
	public static void main(String args[]){
		   ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();		   
		   
	 }

}
