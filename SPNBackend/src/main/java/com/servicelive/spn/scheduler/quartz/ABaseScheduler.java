package com.servicelive.spn.scheduler.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * 
 * 
 *
 */
public abstract class ABaseScheduler {
	protected ApplicationContext getApplicationContext(JobExecutionContext context ) throws SchedulerException {
        ApplicationContext appCtx = (ApplicationContext)context.getScheduler().getContext().get("spnBackEndAppContext");
        if (appCtx == null) {
            throw new JobExecutionException("No application context available in scheduler context for key \"" + "spnBackEndAppContext" + "\"");
        }
        return appCtx;
    }

	/**
	 * 
	 * @param context
	 * @throws JobExecutionException
	 */
	public abstract void execute(JobExecutionContext context) throws JobExecutionException;
}