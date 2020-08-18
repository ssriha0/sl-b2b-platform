package com.servicelive.spn.scheduler.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.servicelive.spn.services.campaign.CampaignInvitationServices;

/**
 * 
 * @author svanloo
 *
 */
public class CampaignInvitationScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(CampaignInvitationScheduler.class);

	/**
	 * 
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("*****************Starting " + this.getClass().getSimpleName());
		try {
			CampaignInvitationServices services = getCampaignInventationServices(context);
			services.inviteProviders();
		} catch (SchedulerException e) {
			logger.error("Caught SchedulerException", e);
			throw new JobExecutionException(e);
		} catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		} finally {
			logger.info("*****************Stopping " + this.getClass().getSimpleName());
		}
	}

	private CampaignInvitationServices getCampaignInventationServices(JobExecutionContext context) throws SchedulerException {
		ApplicationContext applicationContext = getApplicationContext(context);
		return (CampaignInvitationServices) applicationContext.getBean("campaignInvitationServices");
	}
	

}
