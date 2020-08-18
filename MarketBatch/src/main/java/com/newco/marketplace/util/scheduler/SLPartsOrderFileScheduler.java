package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.serviceorder.SLPartsOrderFileProcessor;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;

public class SLPartsOrderFileScheduler extends ABaseScheduler implements Job{
private static final Logger logger = Logger.getLogger(SLPartsOrderFileScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("SLPartsOrderFileScheduler-->"+ new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			SLPartsOrderFileProcessor slPartsOrderFileProcessor = (SLPartsOrderFileProcessor)applicationContext.getBean("slPartsOrderFileProcessor");
			slPartsOrderFileProcessor.generateFile();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		logger.info("SLPartsOrderFileScheduler-->"+ new Date());
		ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx(); 
		SLPartsOrderFileProcessor slPartsOrderFileProcessor = (SLPartsOrderFileProcessor)applicationContext.getBean("slPartsOrderFileProcessor");
		slPartsOrderFileProcessor.generateFile();
	}
}
