package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.buyer.BuyerSOMCountsProcessor;

public class BuyerSOMCountsScheduler extends ABaseScheduler implements
		StatefulJob {
	private static final Logger logger= Logger.getLogger(BuyerSOMCountsScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("Start "+ BuyerSOMCountsScheduler.class.getName() + "-->" +new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			BuyerSOMCountsProcessor buyerSOMCountsProcessor = (BuyerSOMCountsProcessor)applicationContext.getBean("buyerSOMCountsProcessor");
			buyerSOMCountsProcessor.execute();
		} catch (Exception e) {
			logger.error("BuyerSOMCountsScheduler-->EXCEPTION-->", e);
		}
		logger.info("End "+ BuyerSOMCountsScheduler.class.getName() + "-->" +new Date());

	}

}
