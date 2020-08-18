package com.newco.batch.purchaseAmountMigrationBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.purchaseAmountMigration.beans.SalesCheckItem;
import com.newco.marketplace.purchaseAmountMigration.beans.SalesCheckItems;
import com.newco.marketplace.purchaseAmountMigration.constatns.PurchaseAmountMigrationConstants;
import com.newco.marketplace.purchaseAmountMigration.service.IPurchaseAmountMigrationService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class PurchaseAmountMigrationProcessor extends ABatchProcess {

	private IPurchaseAmountMigrationService purchaseAmountMigrationService;
	private static final Logger logger = Logger
			.getLogger(PurchaseAmountMigrationProcessor.class);
	

	@Override
	public int execute() throws BusinessServiceException {
		try {
			logger.info("Entered execute() method of PurchaseAmountMigrationProcessor");
			process();
			logger.info("Leaving execute() method of PurchaseAmountMigrationProcessor");
		} catch (Exception ex) {
			logger.error("Unexpected exception occurred IN PurchaseAmountMigrationProcessor!", ex);
		}
		return 0;
	}

	
	public void process() {
		logger.info("Entered process() method of PurchaseAmountMigrationProcessor");
		Integer recordCount = null;
			
		XStream xstream = new XStream(new DomDriver());
		Class[] classes = new Class[] { SalesCheckItems.class, SalesCheckItem.class };
		xstream.processAnnotations(classes);
		try {
			Map<Object, Object> dateMap = null;
			//get the date interval for which migration is to be done
			dateMap = purchaseAmountMigrationService.getinterval(dateMap);
			//get the total count of RI SOs created within the date interval
			recordCount = purchaseAmountMigrationService.getSOCount(dateMap);
			PurchaseAmountMigrationConstants.OFFSET = 0;
			logger.info("Count Of So for Migration : " + recordCount);
			if(null != recordCount){
			
				while(recordCount > 0){
					List<String> soIds = new ArrayList<String>();		
					//fetching the RI SOs within the interval in chunks 					
					soIds = purchaseAmountMigrationService.fetchSO(dateMap);
					logger.info("First Chunks Of So's"+soIds.size());
					for(String soId : soIds){
						//update the purchase amount for the SO
						purchaseAmountMigrationService.migratePurchaseAmount(soId,xstream);
					}
					recordCount = recordCount - PurchaseAmountMigrationConstants.LIMIT;
			   }
			}
		}  catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Exception in Processs() method of PurchaseAmountMigrationProcessor :"	+ e);
		}
	}

	public IPurchaseAmountMigrationService getPurchaseAmountMigrationService() {
		return purchaseAmountMigrationService;
	}

	public void setPurchaseAmountMigrationService(
			IPurchaseAmountMigrationService purchaseAmountMigrationService) {
		this.purchaseAmountMigrationService = purchaseAmountMigrationService;
	} 	

	@Override
	public void setArgs(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
