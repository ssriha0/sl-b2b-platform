/**
 * 
 */
package com.servicelive.spn.scheduler.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.servicelive.spn.services.network.NetworkMaintenanceService;

/**
 * @author hoza
 *
 */
public class NetworkMaintenanceScheduler extends ABaseScheduler implements Job {

	
	private static final Logger logger = Logger.getLogger(NetworkMaintenanceScheduler.class);
	/* (non-Javadoc)
	 * @see com.servicelive.spn.scheduler.quartz.ABaseScheduler#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("*****************Starting " + this.getClass().getSimpleName());
		try {
			NetworkMaintenanceService service = getNetworkMaintenanceService(context);
			service.execute();
		} catch (Exception e) {
			logger.error("Caught Exception", e);
			throw new JobExecutionException(e);
		} finally {
			logger.info("*****************Stopping " + this.getClass().getSimpleName());
		}
	}

	private NetworkMaintenanceService getNetworkMaintenanceService(JobExecutionContext context) throws SchedulerException {
		ApplicationContext applicationContext = getApplicationContext(context);
		return (NetworkMaintenanceService) applicationContext.getBean("networkMaintenanceService");
	}

}
