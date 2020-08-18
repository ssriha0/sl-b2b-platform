package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.documentExpiration.DocumentExpirationService;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * This is the starting point for TRG_DOC_EXPIRATION_NOTIFICATION batch.
 * This batch processes firm/provider credentials and sends 7/30 days expiry notification mails to the firm admin.
 */
public class DocumentExpirationScheduler extends ABaseScheduler implements Job {

	private static final Logger logger = Logger
			.getLogger(DocumentExpirationScheduler.class.getName());

	/**
	 * This method processes firm/provider credentials and sends 7/30 days expiry notification mail to the firm admin
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		logger.info("DocumentExpirationScheduler-->" + new Date());
		ApplicationContext applicationContext = getApplicationContext(context);

		DocumentExpirationService documentExpirationService = (DocumentExpirationService) applicationContext
				.getBean("documentExpirationService");

		try {
			documentExpirationService.processDocumentExpiration();
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}

	}


}
