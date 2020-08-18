package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;

public class SOCleanupScheduler extends ABaseScheduler implements Job {

	//this class is a scheduler for cleaning up any SO's that are old and have no status in
	//so_hdr.wf_state_id column
	private static final Logger logger = Logger.getLogger(SOActiveExpireScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("SOCleanupScheduler-->"+new Date());
			ServiceOrderBO soBO = (ServiceOrderBO)getApplicationContext(context).getBean("serviceOrderBOTarget");
			soBO.deleteOldServiceOrders(Integer.valueOf(10));		
		}
		catch(Exception e){
			logger.error("SOCleanupScheduler-->EXCEPTION-->", e);
		}
	}
}
