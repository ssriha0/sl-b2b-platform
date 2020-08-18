package com.newco.batch.vendor;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

import com.newco.batch.smsdatamigration.SMSDataMigrationProcess;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.smsdatamigration.service.ISMSDataMigrationService;

public class SMSDataMigrationProcessor {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Inside Main");
		SMSDataMigrationProcess smsDataMigration = new SMSDataMigrationProcess();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("testmainApplicationContext.xml");
		ISMSDataMigrationService smsDataMigrationService = (ISMSDataMigrationService) ctx.getBean("smsDataMigrationService");
		smsDataMigration.setSmsDataMigrationService(smsDataMigrationService);
		try {
			smsDataMigration.processSmsRecords();
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}

	}

}
