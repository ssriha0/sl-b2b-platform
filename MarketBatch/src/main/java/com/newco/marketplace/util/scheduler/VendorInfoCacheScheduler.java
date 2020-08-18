package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.vendor.VendorInfoCacheProcessor;

/**
 * @author jkahng
 */
public class VendorInfoCacheScheduler extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(VendorInfoCacheScheduler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("VendorInfoCacheScheduler-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			VendorInfoCacheProcessor vendorInfoCacheProcessor = (VendorInfoCacheProcessor) applicationContext.getBean("vendorInfoCacheProcessor");
			vendorInfoCacheProcessor.execute();
		} catch (Exception e) {
			logger.error("VendorInfoCacheScheduler-->EXCEPTION-->", e);
		}
	}
}
