package com.newco.marketplace.util.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.constants.Constants;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:42 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class LoadSPNCampaignScheduler extends ABaseScheduler implements Job {

	private static final Logger logger= Logger.getLogger(LoadSPNCampaignScheduler.class);

	public LoadSPNCampaignScheduler() {
		//do nothing
	}
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("Start"+LoadSPNCampaignScheduler.class.getName());
		try {
			ISelectProviderNetworkBO spnBO = (ISelectProviderNetworkBO) getApplicationContext(context).getBean(Constants.ApplicationContextBeans.SELECT_PROVIDER_NETWORK_BO_BEAN);
			spnBO.runSPNCampaign();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}

/*
 * Maintenance History:
 * $Log: LoadSPNCampaignScheduler.java,v $
 * Revision 1.2  2008/05/02 21:23:42  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.1  2008/04/18 22:46:03  mhaye05
 * updated to allow for spn save and the running of spn campaigns
 *
 */