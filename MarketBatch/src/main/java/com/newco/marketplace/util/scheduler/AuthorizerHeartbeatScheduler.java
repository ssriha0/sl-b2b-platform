package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class AuthorizerHeartbeatScheduler extends ABaseScheduler implements Job{

		private static final Logger logger = Logger.getLogger(AuthorizerHeartbeatScheduler.class.getName());
		/* (non-Javadoc)
		 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
		 */

		public void execute(JobExecutionContext context) throws JobExecutionException {
			try{
				logger.info("AuthorizerHeartbeatScheduler--> Started: "+new Date());
				ApplicationContext applicationContext = getApplicationContext(context);
				applicationContext.getBean("fullfillmentTransactionBO");
				//ackProcessor.achachAcknowledgementReaderAndParser();
				logger.info("ACHAcknowledgementScheduler--> Completed: "+new Date());

			}
			catch(Exception e){
				logger.error("ACHAcknowledgementScheduler-->EXCEPTION-->", e);
			}
		}
		/*
		public static void main(String[] args) {
			try {
				ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
				IFullfillmentTransactionBO fullfillmentTransactionBO  = (IFullfillmentTransactionBO)applicationContext.getBean("fullfillmentTransactionBO");
				fullfillmentTransactionBO.handleNoResponseReceived();
				//fullfillmentTransactionBO
				//ackProcessor.achachAcknowledgementReaderAndParser();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		*/
}
