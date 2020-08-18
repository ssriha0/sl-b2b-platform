package com.newco.marketplace.util.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.providersearch.IETMProviderSearch;
import com.newco.marketplace.constants.Constants;

public class ETMCleanupScheduler extends ABaseScheduler implements Job {

	private static final Logger logger= Logger.getLogger(ETMCleanupScheduler.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("Start of ETMCleanupScheduler --> execute() to clean the temp table");
		Integer numRowsDeleted = Integer.valueOf(0);
		try {
			ApplicationContext appCtx = getApplicationContext(context);
			
			IETMProviderSearch etmProviderSearchBo = (IETMProviderSearch) appCtx.getBean(Constants.ApplicationContextBeans.ETM_PROVIDER_SEARCH_BO_BEAN);
			
			numRowsDeleted = etmProviderSearchBo.cleanETMTempTableOldRecords();
			
			if(numRowsDeleted != null){
				logger.info("ETMCleanupScheduler --> execute() --> Deleted "+numRowsDeleted+
						" old records from temp table");
			}
		}catch (Exception e) {			
			logger.error("Error in ETMCleanupScheduler --> execute() "+ e.getMessage());
		}
		
	}

}
