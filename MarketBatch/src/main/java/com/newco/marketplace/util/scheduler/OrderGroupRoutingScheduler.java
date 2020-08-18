/*
 * @(#)OrderGroupRoutingScheduler.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.serviceorder.OrderGroupRoutingProcessor;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;

/**
 * @author Mahmud Khair
 *
 */
public class OrderGroupRoutingScheduler extends ABaseScheduler implements StatefulJob {
	
	private static final Logger logger= Logger.getLogger(OrderGroupRoutingScheduler.class);
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		try {
			logger.info("Start "+ OrderGroupRoutingScheduler.class.getName() + "-->" +new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			OrderGroupRoutingProcessor orderGroupRoutingProcessor = (OrderGroupRoutingProcessor)applicationContext.getBean("orderGroupRoutingProcessor");
			orderGroupRoutingProcessor.execute();
		} catch (Exception e) {
			logger.error("OrderGroupRoutingScheduler-->EXCEPTION-->", e);
		}
		logger.info("End "+ OrderGroupRoutingScheduler.class.getName() + "-->" +new Date());
	}

	public static void main(String args[]){
		try{
			logger.info("Start "+ OrderGroupRoutingScheduler.class.getName() + "-->" +new Date());
			ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
			OrderGroupRoutingProcessor orderGroupRoutingProcessor = (OrderGroupRoutingProcessor)applicationContext.getBean("orderGroupRoutingProcessor");
			orderGroupRoutingProcessor.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
}
