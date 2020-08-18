package com.servicelive.spn.scheduler.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.spn.services.network.MemberMaintenanceService;
import com.servicelive.spn.services.network.NightlyMemberMaintenanceService;

/**
 * 
 * @author svanloo
 *
 */
public class MemberMaintenanceScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger.getLogger(MemberMaintenanceScheduler.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("*****************Starting " + this.getClass().getSimpleName());
		try {
			NightlyMemberMaintenanceService service = getMemberMaintenanceService(context);
			service.maintainCompliance();
		} catch (Throwable e) {
			logger.error("Caught Throwable", e);
			throw new JobExecutionException(e);
		} finally {
			logger.info("*****************Stopping " + this.getClass().getSimpleName());
		}
	}

	private NightlyMemberMaintenanceService getMemberMaintenanceService(JobExecutionContext context) throws SchedulerException {
		ApplicationContext applicationContext = getApplicationContext(context);
		return (NightlyMemberMaintenanceService) applicationContext.getBean("nightlyMemberMaintenanceService");
	}

	public static void main (String args[]) {
		MemberMaintenanceScheduler scheduler = new MemberMaintenanceScheduler();
		 ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spnBackendTestApplicationContext.xml");
	        if (appCtx == null) {
	        	System.out.println("No application context available in scheduler context for key \"" + "spnBackEndAppContext" + "\"");
	            return;
	        }
	     
		System.out.println("*****************Starting MM Schedukler Main App*************************");
		try {
			NightlyMemberMaintenanceService service = (NightlyMemberMaintenanceService) appCtx.getBean("nightlyMemberMaintenanceService");
			service.maintainCompliance();
		} catch (Throwable e) {
			System.out.println("Caught Throwable");
			
		} finally {
			System.out.println("*****************Stopping Schedukler Main App  " );
		}
	}
}
