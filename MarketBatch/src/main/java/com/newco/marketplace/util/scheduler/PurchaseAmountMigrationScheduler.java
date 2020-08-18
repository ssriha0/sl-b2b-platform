
package com.newco.marketplace.util.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.batch.purchaseAmountMigrationBatch.PurchaseAmountMigrationProcessor;

/*
 * Batch to populate purchase_amount in so_tasks table for the existing production data. 
 *	1.	A date interval for which the data migration is to be performed is configured in application_properties table as purchase_amount_migration_start_date and purchase_amount_migration_end_date.
 *	2.	The soId of the first 1000 orders which were created in the given interval is fetched from so_hdr table.
 *	3.	For each SO, the latest injection XML is fetched from xml_fragment column of shc_order_transaction table.
 *	4.	Xml fragment is parsed to get the list of sales check items for each SO.
 *	5.	If the list of sales check items is not empty, the tasks for the particular SO is fetched from so_tasks table.
 *	6.	The task list is iterated and for each task, the item whose itemNumber matches with the sku of the task is identified.
 *	7.	purchase_amount column for the corresponding task in so_tasks table is updated with the purchaseAmount of the matching item. 
 *	8.	The next 1000 records is fetched from so_hdr and the same steps are repeated till the purchase_amount is updated for all the orders created within the given interval.
*/


public class PurchaseAmountMigrationScheduler extends ABaseScheduler implements StatefulJob{
	private static final Logger logger = Logger.getLogger(PurchaseAmountMigrationScheduler.class.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			
			logger.info("PurchaseAmountMigrationScheduler-->"+new Date());
			ApplicationContext applicationContext = getApplicationContext(context);
			PurchaseAmountMigrationProcessor purchaseAmountMigrationProcessor = (PurchaseAmountMigrationProcessor)applicationContext.getBean("purchaseAmountMigrationProcessor");
			purchaseAmountMigrationProcessor.execute();
						
		}
		catch(Exception e){
			logger.error("PurchaseAmountMigrationScheduler---->EXCEPTION-->", e);
			
		}
	}

}
