package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.batch.inhomeOutboundNotification.InHomeNotificationFailureMailProcess;

/**
 *
 * @author svanloo
 *
 */
public class InHomeNotificationFailureMailScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(InHomeNotificationFailureMailScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("InHomeNotificationFailureMailScheduler-->"+ new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		try{
			InHomeNotificationFailureMailProcess processor = (InHomeNotificationFailureMailProcess)applicationContext.getBean("inHomeFailureMailProcessor");
			processor.process();
		}
		catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		}
	}

	//for testing
	public static void main (String args[]) {
		 ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/inhomeOutBoundNotificationContext.xml");
	        if (appCtx == null) {
	        	System.out.println("No application context available in scheduler context for key \"" + "spnBackEndAppContext" + "\"");
	            return;
	        }

		System.out.println("*****************Starting MM Schedukler Main App*************************");
		try {
			InHomeNotificationFailureMailProcess service = (InHomeNotificationFailureMailProcess) appCtx.getBean("inHomeFailureMailProcessor");
			service.process();
		} catch (Throwable e) {
			System.out.println("Caught Throwable");

		} finally {
			System.out.println("*****************Stopping Schedukler Main App  " );
		}
	}
}
