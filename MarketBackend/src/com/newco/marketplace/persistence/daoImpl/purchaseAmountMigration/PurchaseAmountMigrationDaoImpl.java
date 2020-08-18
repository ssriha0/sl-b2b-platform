package com.newco.marketplace.persistence.daoImpl.purchaseAmountMigration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.purchaseAmountMigration.IPurchaseAmountMigrationDao;
import com.newco.marketplace.purchaseAmountMigration.vo.SoTaskVo;
import com.sears.os.dao.impl.ABaseImplDao;

public class PurchaseAmountMigrationDaoImpl extends ABaseImplDao implements IPurchaseAmountMigrationDao{

	/**
	 * fetching the SOs within the interval in chunks
	 * @param dateMap
	 */
	public List<String> getChunkSoInDateRange(Map<Object, Object> dateMap) throws DataServiceException {
		
		List<String> soIds=new ArrayList<String>();
		try {
			soIds = (List<String>)queryForList("getChunkSoInDateRange.query", dateMap);
			
		} catch (Exception e) {
			logger.error("Exception in PurchaseAmountMigrationDaoImpl.getChunkSoInDateRange() :"
							+ e);
			throw new DataServiceException(e.getMessage());
		}
		return soIds;
	}
	
	
	/**
	 * fetching count of RI orders which were created within a date interval
	 * @param dateMap
	 * @return Integer
	 */
	public Integer getSoCountInDateRange(Map<Object, Object> dateMap) throws DataServiceException {
		
		Integer count = null;
		try {
			count = (Integer) queryForObject("getSoCountInDateRange.query", dateMap);
			
		} catch (Exception e) {
			logger.error("Exception in PurchaseAmountMigrationDaoImpl.getSoCountInDateRange() :" 
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		return count;
	}
	
	/**
	 * get the latest injection xml for each SO
	 * @param soId
	 * 
	 */
	public String getSalesCheckItemsXml(String soId) throws DataServiceException {
		
		String xml = null;
		try {
			xml= (String)queryForObject("getSalesCheckItems.query", soId);
		} catch (Exception e) {
			logger.error("Exception in PurchaseAmountMigrationDaoImpl.getSalesCheckItems() :"
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		return xml;
	}
	
	/**
	 * fetch the tasks for the SO	 
	 * @param soId
	 * @return List<SoTaskVo>
	 */
	public List<SoTaskVo> fetchTasks(String soId) throws DataServiceException {
		
		List<SoTaskVo> soTasks = new ArrayList<SoTaskVo>();
		try {
			soTasks = (List<SoTaskVo>)queryForList("getSoTaskData.query", soId);
		} catch (Exception e) {
			logger.error( "Exception in PurchaseAmountMigrationDaoImpl.fetchTasks() :"
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		return soTasks;
	}
	 
	
	/**
	 * updating the purchaseAmt for each task
	 * @param soTaskVo
	 */
	public void updatePurchaseAmountSoTasks(SoTaskVo soTaskVo) throws DataServiceException {
		try {
			update("updatePurchaseAmountSoTasks.query", soTaskVo);

		} catch (Exception e) {
			logger.error("Exception in PurchaseAmountMigrationDaoImpl.updatePurchaseAmt() " + e);
			throw new DataServiceException(e.getMessage());
		}

	}

	/**
	 * get the date from application Properties
	 * @param key
	 * 
	 */
	public String getDateInterval(String key) throws DataServiceException {
		   String keyValue=null;
			try {
				
				keyValue= (String)queryForObject(
						"getAapplicationPropertyDate.query", key);
			} catch (Exception e) {
				logger.error( "Exception in PurchaseAmountMigrationDaoImpl.getDateInterval() :"
						+ e);
				throw new DataServiceException(e.getMessage());
			}
			return keyValue;
	}
	
}
