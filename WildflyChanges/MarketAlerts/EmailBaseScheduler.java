package com.newco.marketplace.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

public abstract class EmailBaseScheduler {
	
	public static final Properties bootstrapConfig = new Properties();;
	private static final Logger logger = Logger.getLogger(EmailBaseScheduler.class.getName());


	static {
		/*String bootstrapFileClassPath = new StringBuilder("resources").append('/')
			.append("bootstrap.").append(System.getenv("sl_app_lifecycle")).append(".properties").toString();*/
		String bootstrapFileClassPath = new StringBuilder("resources").append('/')
				.append("bootstrap.").append(System.getProperty("sl_app_lifecycle")).append(".properties").toString();
		InputStream is = EmailBaseScheduler.class.getClassLoader().getResourceAsStream(bootstrapFileClassPath);
		try {
			bootstrapConfig.load(is);
			logger.info("Bootstrap Config loaded");
		} catch (IOException ioEx) {
			logger.error("Could not load bootstrap resource bundle!!!");
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
