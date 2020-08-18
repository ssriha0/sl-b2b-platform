package com.newco.marketplace.util.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * This starts a batch job to synchronize WorkFlow monitor queues
 * @author priti
 *
 */
public class WFMQueuesScheduler extends ABaseScheduler implements Job {
		public static final Logger logger = Logger.getLogger(WFMQueuesScheduler.class);

		public void execute(JobExecutionContext context) throws JobExecutionException {
			try{
				logger.info("---->WFMQueuesScheduler Started <-----");
				ApplicationContext appCtx = getApplicationContext(context);
				IPowerBuyerBO powerBuyerBO = (IPowerBuyerBO) appCtx.getBean(Constants.ApplicationContextBeans.POWER_BUYER_BO_BEAN);
				powerBuyerBO.updateWFMQueues();
				
				logger.info("----->WFMQueuesScheduler Ended <------");
			}catch(Exception e){
				logger.error("Error occured while running the WFMQueuesScheduler process", e);
				throw new JobExecutionException("Error occured while running the WFMQueuesScheduler process", e);
			}

		}
		
		public static void main(String[] args) {
			ApplicationContext ctx = MPSpringLoaderPlugIn.getCtx(); 
			IPowerBuyerBO powerBuyerBO = (IPowerBuyerBO) ctx.getBean(Constants.ApplicationContextBeans.POWER_BUYER_BO_BEAN);
			try {
				powerBuyerBO.updateWFMQueues();
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}
			
		}

	}

