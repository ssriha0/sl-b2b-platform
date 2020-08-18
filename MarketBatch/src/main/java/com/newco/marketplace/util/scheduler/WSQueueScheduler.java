/**
 * 
 */
package com.newco.marketplace.util.scheduler;


import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.webservices.IWSPayloadDispatcher;
import com.newco.marketplace.constants.Constants;

/**
 * @author Gordon Jackson - Sogeti USA, LLC
 *
 */
public class WSQueueScheduler extends ABaseScheduler implements Job {

	private static final Logger logger= Logger.getLogger(WSQueueScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Start" + WSQueueScheduler.class);
		try {
			ApplicationContext appCtx = getApplicationContext(context);
			IWSPayloadDispatcher dispatcher = (IWSPayloadDispatcher)appCtx.getBean(Constants.ApplicationContextBeans.WS_QUEUE_BEAN);
			dispatcher.sendPayloads();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
}
