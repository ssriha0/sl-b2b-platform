package com.newco.marketplace.util.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

public abstract class ABaseScheduler {
	
	public static final Properties bootstrapConfig = new Properties();
	
	static {
		/*String bootstrapFileClassPath = new StringBuilder("resources").append('/')
			.append("bootstrap.").append(System.getenv("sl_app_lifecycle")).append(".properties").toString();*/
		String bootstrapFileClassPath = new StringBuilder("resources").append('/')
				.append("bootstrap.").append(System.getProperty("sl_app_lifecycle")).append(".properties").toString();
		InputStream is = ABaseScheduler.class.getClassLoader().getResourceAsStream(bootstrapFileClassPath);
		try {
			bootstrapConfig.load(is);
			System.out.println("Bootstrap Config ["+bootstrapFileClassPath+"] loaded");
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
}
