
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.MobileSoPdfGenerationBatch.MobileSoPdfGenerationProcessor;

/*
 * Batch which can be run to generate Signed Service Order pdf and send to customers.'
 */
public class MobileSoPdfGenerationScheduler extends ABaseScheduler implements StatefulJob{
	private static final Logger logger = Logger.getLogger(MobileSoPdfGenerationScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
			logger.info("MobileSoPdfGenerationScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			MobileSoPdfGenerationProcessor pdfGenerationProcessor = (MobileSoPdfGenerationProcessor)applicationContext.getBean("mobileSoPdfGenerationProcessor");
			pdfGenerationProcessor.execute();
						
		}
		catch(Exception e){
			logger.error("MobileSoPdfGenerationScheduler---->EXCEPTION-->", e);
			
		}
	}

}
