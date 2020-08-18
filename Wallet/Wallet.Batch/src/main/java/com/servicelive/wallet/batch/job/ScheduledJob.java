package com.servicelive.wallet.batch.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.servicelive.wallet.batch.IProcessor;

public class ScheduledJob implements Job {
	
	public static final Properties bootstrapConfig = new Properties();;
	private static final Logger logger = Logger.getLogger(ScheduledJob.class.getName());

	static {
		String bootstrapFileClassPath = new StringBuilder("com/servicelive/wallet/batch").append('/')
			.append("bootstrap.").append(System.getenv("sl_app_lifecycle")).append(".properties").toString();		
		
		logger.info("loading " + bootstrapFileClassPath);
		
		InputStream is = ScheduledJob.class.getClassLoader().getResourceAsStream(bootstrapFileClassPath);
		try {
			bootstrapConfig.load(is);
			System.out.println("Bootstrap Config loaded");
		} catch (IOException ioEx) {
			System.err.println("Could not load bootstrap resource bundle!!!");
		}
	}
	
	public static final String APPLICATION_CONTEXT_KEY = "applicationContext";
	
	protected ApplicationContext getApplicationContext(JobExecutionContext context ) throws JobExecutionException {
        ApplicationContext appCtx = null;
        try {
			appCtx = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
		} catch (SchedulerException e) {
            throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
		}
        if (appCtx == null) {
            throw new JobExecutionException(
                    "No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
        }
        return appCtx;
    }
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (logger.isInfoEnabled()){
			logger.info(context.getJobDetail().getFullName() + " started " + Calendar.getInstance().getTime() + " actual starting time " + context.getFireTime());
		}
		
		//Make sure that spring context have the same bean name as the job name in the quartz database
		try{
			IProcessor processor = (IProcessor)getApplicationContext(context).getBean(context.getJobDetail().getFullName());
			
			processor.process();
			
		}catch(Exception e){
			logger.error(" error happened in the job " + context.getJobDetail().getFullName(), e);
		}		
		
		if (logger.isInfoEnabled()){
			logger.info(context.getJobDetail().getFullName() + " ending " + Calendar.getInstance().getTime());
		}
	}
	
}
