/**
 * 
 */
package com.newco.marketplace.util.scheduler;


import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.constants.Constants;

/**
 * @author rambewa
 *
 */
public class PBExpiredClaimCleanUpScheduler extends ABaseScheduler implements Job {

	private static final Logger logger= Logger.getLogger(PBExpiredClaimCleanUpScheduler.class);
	/**
	 * 
	 */
	public PBExpiredClaimCleanUpScheduler() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("Start"+PBExpiredClaimCleanUpScheduler.class.getName());
		try {
			ApplicationContext appCtx = getApplicationContext(context);
			IPowerBuyerBO powerBuyerBO = (IPowerBuyerBO) appCtx.getBean(Constants.ApplicationContextBeans.POWER_BUYER_BO_BEAN);
			powerBuyerBO.processExpiredClaims();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
