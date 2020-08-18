package com.newco.marketplace.util.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.buyerFileUploadTool.IBuyerFileUploadToolBO;

public class BuyerFileUploadScheduler extends ABaseScheduler implements Job {
	
	public static final Logger logger = Logger.getLogger(BuyerFileUploadScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime = System.currentTimeMillis();
		try {
			ApplicationContext applicationContext = getApplicationContext(context); 
			IBuyerFileUploadToolBO BFUTProcessor = (IBuyerFileUploadToolBO) applicationContext.getBean("BFUTBOImpl");
			BFUTProcessor.StartFileToSOProcessing();
			Thread.sleep(30000);  // Sleep to give the ESB time to process the files before attempting to copy the results back to the supplier db
			BFUTProcessor.moveFileUploadDataFromIntegrationDbToSupplierDb();
			
		} catch (Exception e) {
			logger.error("Error occured while running the BuyerFileUploadScheduler process",e);
			throw new JobExecutionException("Error occured while running the BuyerFileUploadScheduler process",e);
		} finally { 
			long endTime = System.currentTimeMillis();
			logger.error("Time taken by Buyer File Upload batch: " + (endTime-startTime)/1000 + " seconds");
		}
	}

	public static void main(String[] args) {
		ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx();
		long startTime = System.currentTimeMillis();
		logger.info("---->BuyerFileUploadScheduler begins <-----");
		IBuyerFileUploadToolBO BFUTProcessor = (IBuyerFileUploadToolBO) ctx.getBean("BFUTBOImpl");
		BFUTProcessor.StartFileToSOProcessing();
		logger.info("---->BuyerFileUploadScheduler ends <-----");
		long endTime = System.currentTimeMillis();
		logger.error("Time taken by batch : " + (endTime-startTime)/1000 + " seconds");
		System.err.println("Time taken by batch : " + (endTime-startTime)/1000 + " seconds");
	}
}
