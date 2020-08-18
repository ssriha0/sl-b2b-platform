package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.buyer.BuyerCompletedOrdersCountsProcessor;


public class BuyerCompletedCountsScheduler extends ABaseScheduler implements
		StatefulJob {
	private static final Logger logger= Logger.getLogger(BuyerCompletedCountsScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("Start "+ BuyerCompletedCountsScheduler.class.getName() + "-->" +new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			BuyerCompletedOrdersCountsProcessor buyerCompletedOrdersCountsProcessor = (BuyerCompletedOrdersCountsProcessor)applicationContext.getBean("buyerCompletedOrdersCountsProcessor");
			buyerCompletedOrdersCountsProcessor.execute();
		} catch (Exception e) {
			logger.error("BuyerCompletedCountsScheduler-->EXCEPTION-->", e);
		}
		logger.info("End "+ BuyerCompletedCountsScheduler.class.getName() + "-->" +new Date());

	}

}
