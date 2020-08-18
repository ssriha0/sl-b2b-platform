package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.vendor.VendorKPIProcessor;

/**
 * @author jkahng
 */
public class VendorKPIScheduler extends ABaseScheduler implements StatefulJob {

	private static final Logger logger = Logger.getLogger(VendorKPIScheduler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("VendorKPIProcessor-->" + new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			VendorKPIProcessor vendorKPIProcessor = (VendorKPIProcessor) applicationContext.getBean("vendorKPIProcessor");
			vendorKPIProcessor.execute();
		} catch (Exception e) {
			logger.error("VendorKPIProcessor-->EXCEPTION-->", e);
		}
	}
}
