package com.newco.marketplace.util.scheduler;

import java.util.Date;

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
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/05/02 21:23:42 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class PBFilterSummaryLoadScheduler extends ABaseScheduler implements Job{

	private static final Logger logger = Logger.getLogger(PBFilterSummaryLoadScheduler.class.getName());
		
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("PBFilterSummaryLoadScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			IPowerBuyerBO powerBuyerBO = (IPowerBuyerBO)applicationContext.getBean(Constants.ApplicationContextBeans.POWER_BUYER_BO_BEAN);
			powerBuyerBO.updatePowerBuyerFilterSummaryCounts();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws BusinessServiceException {
		IPowerBuyerBO powerBuyerBO = (IPowerBuyerBO)MPSpringLoaderPlugIn.getCtx().getBean(Constants.ApplicationContextBeans.POWER_BUYER_BO_BEAN);
		powerBuyerBO.updatePowerBuyerFilterSummaryCounts();
	}
}
/*
 * Maintenance History
 * $Log: PBFilterSummaryLoadScheduler.java,v $
 * Revision 1.8  2008/05/02 21:23:42  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.7  2008/04/26 00:40:35  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.5.28.1  2008/04/10 16:18:01  mhaye05
 * updates to all for a scheduled job to load the summary table
 *
 * Revision 1.5.10.1  2008/04/23 11:42:18  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.6  2008/04/23 05:02:21  hravi
 * Reverting to build 247.
 *
 * Revision 1.5  2008/02/20 15:27:02  gjacks8
 * removed comments
 *
 * Revision 1.4  2008/02/06 15:24:21  gjacks8
 * quartz jobs now get app context from parent class
 *
 * Revision 1.3.4.1  2008/02/06 14:44:51  gjacks8
 * these classes now get their context from their parent
 *
 * Revision 1.3  2008/01/14 20:38:26  mhaye05
 * check in point where filter summary load complete
 *
 * Revision 1.2  2008/01/14 16:45:03  mhaye05
 * fixed imports so folks can run
 *
 * Revision 1.1  2008/01/14 16:43:13  mhaye05
 * Initial check in
 *
 */