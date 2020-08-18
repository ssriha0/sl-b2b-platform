package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.service.TokenService;

public class ServiceLiveEmailTokenScheduler extends ABaseScheduler implements StatefulJob {
	
private static final Logger logger = Logger.getLogger(ServiceLiveEmailTokenScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			logger.info("AdobeTokenRefreshScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			TokenService tokenService = (TokenService)applicationContext.getBean("adobeTokenService");
			tokenService.genrateToken();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
