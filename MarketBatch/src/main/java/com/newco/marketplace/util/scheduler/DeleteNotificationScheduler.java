package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.documentExpiration.DeleteAuditNotificationService;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class DeleteNotificationScheduler extends ABaseScheduler implements Job{
	
	private static final Logger logger=Logger
			.getLogger(DeleteNotificationScheduler.class.getName());
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException  {
		
		logger.info("DeleteNotificationScheduler -->" + new Date());
		ApplicationContext applicationContext=getApplicationContext(context);
		
		DeleteAuditNotificationService deleteAuditNotificationService = (DeleteAuditNotificationService) applicationContext
				.getBean("deleteAuditNotificationService");
		
		try {
			deleteAuditNotificationService.processDeleteNotification();
		}
		catch(BusinessServiceException exception){
			logger.debug("Error in DeleteNotificationScheduler execute() due to "+ exception.getMessage());
			throw new JobExecutionException(exception.getMessage(),exception);
		}
	}
}
