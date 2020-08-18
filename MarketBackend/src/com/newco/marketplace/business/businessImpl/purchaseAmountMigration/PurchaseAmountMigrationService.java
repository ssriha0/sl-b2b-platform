package com.newco.marketplace.business.businessImpl.purchaseAmountMigration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.purchaseAmountMigration.IPurchaseAmountMigrationDao;
import com.newco.marketplace.purchaseAmountMigration.beans.SalesCheckItem;
import com.newco.marketplace.purchaseAmountMigration.beans.SalesCheckItems;
import com.newco.marketplace.purchaseAmountMigration.constatns.PurchaseAmountMigrationConstants;
import com.newco.marketplace.purchaseAmountMigration.service.IPurchaseAmountMigrationService;
import com.newco.marketplace.purchaseAmountMigration.vo.SoTaskVo;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.thoughtworks.xstream.XStream;

public class PurchaseAmountMigrationService implements IPurchaseAmountMigrationService{
	
	private IPurchaseAmountMigrationDao purchaseAmountMigrationDao;
	private IApplicationProperties applicationProperties;
	
	private static final Logger logger = Logger
			.getLogger(PurchaseAmountMigrationService.class);

	
	/**
	 * fetching count of RI orders 
	 * which were created within a date interval
	 */
	public Integer getSOCount(Map<Object, Object> dateMap) throws BusinessServiceException {
		Integer count = null;
		try {				
			if(null != dateMap){
				count = purchaseAmountMigrationDao.getSoCountInDateRange(dateMap);
			}
		} catch (DataServiceException e) {
			logger.error("Exception in PurchaseAmountMigrationService.getSOCount() " + e);
			throw new BusinessServiceException(e);
		}
		return count;
	}

	/**
	 * fetching the SOs within the interval in chunks
	 */
	public List<String> fetchSO(Map<Object, Object> dateMap) throws BusinessServiceException {
		List<String> soIdList = null;
		try {		
			if(null != dateMap){
				dateMap.put("limit", PurchaseAmountMigrationConstants.LIMIT);
				dateMap.put("offset", PurchaseAmountMigrationConstants.OFFSET);
				soIdList = purchaseAmountMigrationDao.getChunkSoInDateRange(dateMap);			
				PurchaseAmountMigrationConstants.OFFSET = PurchaseAmountMigrationConstants.OFFSET + PurchaseAmountMigrationConstants.LIMIT;
			}
		} catch (DataServiceException e) {
			logger.error("Exception in PurchaseAmountMigrationService.fetchSO() " + e);
			throw new BusinessServiceException(e);
		}
		return soIdList;
	}

	/**
	 * update the purchase amount
	 * @param xstream
	 * @param soId
	 */
	public void migratePurchaseAmount(String soId, XStream xstream)	throws BusinessServiceException {
		logger.info("inside migratePurchaseAmount");
		SalesCheckItems salesItems = null;
		List<SoTaskVo> taskList = new ArrayList<SoTaskVo>();
		//get the latest injection xml for each SO
		
		try {
		    salesItems = getSalesCheckItems(soId,xstream);
		    if(null != salesItems && null != salesItems.getSalesCheckItemList() && !salesItems.getSalesCheckItemList().isEmpty()){
			
		    	//fetch the tasks for the SO
		    	taskList = purchaseAmountMigrationDao.fetchTasks(soId);
			
		    	if(null != taskList){
		    		for(SoTaskVo task : taskList){	
		    			if(null != task){
			    			BigDecimal purchaseAmt = null;
			    			for(SalesCheckItem item : salesItems.getSalesCheckItemList()){									
			    				if(null != item && StringUtils.isNotBlank(item.getItemNumber()) 
			    						&& StringUtils.isNotBlank(task.getSku()) && item.getItemNumber().equals(task.getSku())){
			    					//updating the purchaseAmt for each task
			    					purchaseAmt = new BigDecimal("0.00");
			    					if(null != item.getPurchaseAmt()){
			    						purchaseAmt = new BigDecimal(item.getPurchaseAmt());
			    						task.setPurchaseAmount(purchaseAmt);
			    						purchaseAmountMigrationDao.updatePurchaseAmountSoTasks(task);
			    					}
			    				}
			    			}
			    			if(null == purchaseAmt){
			    				logger.info("No matching item found for the task with sku " 
			    						+ task.getSku() + "for the SO " + soId);
			    			}
		    			}
		    		}
		    	}
			}
		}catch (DataServiceException e) {
			logger.error("Exception in PurchaseAmountMigrationService.migratePurchaseAmount() " + e);
			throw new BusinessServiceException(e);
		}	
	}
	
	/**
	 * get the date interval for which 
	 * data needs to be migrated
	 */
	public Map<Object, Object> getinterval(Map<Object, Object> param) {
		logger.info("inside getinterval() in PurchaseAmountMigrationService ");    	
		String startDate = null;
		String endDate = null;
		try {
			 startDate = applicationProperties.getPropertyFromDB(PurchaseAmountMigrationConstants.MIGRATION_START_DATE);
			 endDate = applicationProperties.getPropertyFromDB(PurchaseAmountMigrationConstants.MIGRATION_END_DATE);     
			 
		} catch (DataNotFoundException e) {			
			  return param;
		 }
	   	if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {   	   
		    	param = new HashMap<Object, Object>();
				param.put("startdate", startDate);
				param.put("enddate", endDate);				
	   	}
		return param;
	}

	/**
	 * get the latest injection xml for each SO
	 * @param xstream
	 * @param soId
	 */
	private SalesCheckItems getSalesCheckItems(String soId,XStream xstream) {
		
		String xml = null;
		SalesCheckItems items = null;
		
		try{
			xml = purchaseAmountMigrationDao.getSalesCheckItemsXml(soId);			
			if(null != xml){
				//to get the entire content of <SalesCheckItems> tag, 18 is added to the index of </SalesCheckItems>
				xml = xml.substring(xml.indexOf("<SalesCheckItems>"), xml.indexOf("</SalesCheckItems>")+PurchaseAmountMigrationConstants.LENGTH);
				logger.info("Service Order Injection xml For Purchase Amount Migration ::" + xml);				
				//getting the sales check items
				items = (SalesCheckItems) xstream.fromXML(xml);
				logger.info("After mapping Xml to SalesCheckItems ::");		
			}else{
				logger.info("No xml found for soId : " + soId);
			}
			
		}catch(Exception e){
			logger.error("Exception in PurchaseAmountMigrationService.getSalesCheckItems() " + e);
		}
		return items;
	}
	
	public IPurchaseAmountMigrationDao getPurchaseAmountMigrationDao() {
		return purchaseAmountMigrationDao;
	}

	public void setPurchaseAmountMigrationDao(
			IPurchaseAmountMigrationDao purchaseAmountMigrationDao) {
		this.purchaseAmountMigrationDao = purchaseAmountMigrationDao;
	}

	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	
}
