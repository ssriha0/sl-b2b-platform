package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

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
public class SPNNetworkSummaryLoadScheduler extends ABaseScheduler implements
		Job {

	private static final Logger logger = Logger.getLogger(SPNNetworkSummaryLoadScheduler.class.getName());
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("PBFilterSummaryLoadScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			ISelectProviderNetworkBO spnBO = (ISelectProviderNetworkBO)applicationContext.getBean(Constants.ApplicationContextBeans.SELECT_PROVIDER_NETWORK_BO_BEAN);
			spnBO.loadSPNSummaryTable();
		}
		catch(Exception e){
			logger.error("Execution of SPNNetworkSummaryLoadScheduler failed",e);
			throw new JobExecutionException("Execution of SPNNetworkSummaryLoadScheduler failed",e);
		}
	}

}

/*
 * Maintenance History:
 * $Log: SPNNetworkSummaryLoadScheduler.java,v $
 * Revision 1.2  2008/05/02 21:23:42  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.1  2008/04/10 16:18:01  mhaye05
 * updates to all for a scheduled job to load the summary table
 *
 */