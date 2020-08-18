package com.newco.marketplace.persistence.iDao.purchaseAmountMigration;

import java.util.List;
import java.util.Map;
import com.newco.marketplace.purchaseAmountMigration.vo.SoTaskVo;
import com.newco.marketplace.exception.core.DataServiceException;
public interface IPurchaseAmountMigrationDao {
	
	/**
	 * fetching the SOs within the interval in chunks
	 * @param dateMap
	 */
	public List<String> getChunkSoInDateRange(Map<Object, Object> dateMap)
			throws DataServiceException ;
	/**
	 * fetching count of RI orders which were created within a date interval
	 * @param dateMap
	 * @return Integer
	 */
	public Integer getSoCountInDateRange(Map<Object, Object> dateMap)
	throws DataServiceException;
	/**
	 * get the latest injection xml for each SO
	 * @param soId
	 * 
	 */
	public String getSalesCheckItemsXml(String soId)
			throws DataServiceException;
	/**
	 * fetch the tasks for the SO	 
	 * @param soId
	 * @return
	 */
	public List<SoTaskVo> fetchTasks(String soId)
			throws DataServiceException;
	/**
	 * updating the purchaseAmt for each task
	 * @param 
	 */
	public void updatePurchaseAmountSoTasks(SoTaskVo soTaskVo)
	throws DataServiceException;
		
	/**
	 * get the date from application Properties
	 * @param key
	 * 
	 */
	public String getDateInterval(String key)
			throws DataServiceException;

}
