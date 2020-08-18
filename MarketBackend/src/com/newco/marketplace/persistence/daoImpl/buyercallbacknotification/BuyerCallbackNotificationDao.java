package com.newco.marketplace.persistence.daoImpl.buyercallbacknotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerCallBackNotificationSchedulerVO;
import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.buyercallbacknotification.IBuyerCallbackNotificationDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.servicelive.common.exception.DataServiceException;


public class BuyerCallbackNotificationDao extends MPBaseDaoImpl implements IBuyerCallbackNotificationDao{
	
	private static final Logger logger = Logger
			.getLogger(BuyerCallbackNotificationDao.class.getName());
	
	public List<BuyerCallBackNotificationSchedulerVO> getNotificationServiceOrderDetails() throws DataServiceException{
		logger.info("Inisde getNotificationServiceOrderDetails() DAO");
		try {
			
			return (ArrayList<BuyerCallBackNotificationSchedulerVO>) this.queryForList("buyerCallbackNotification.getNotificationServiceOrderDetails");
		} catch (Exception e) {
			logger.error("BuyerCallbackNotificationDao.getNotificationServiceOrderDetails NoResultException" + StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		}
	
	}
	
	public void updateBuyerCallbackNotification(long notificationId, String requestData, String status) throws DataServiceException {
		logger.info("Inisde updateBuyerCallbackNotification() DAO");
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("notificationId", notificationId);
			map.put("status", status);
			map.put("requestData", requestData);
			map.put("noOfRetries", -1);
			getSqlMapClientTemplate().update("buyerCallbackNotification.updateNotification", map);
		} catch(Exception e) {
			logger.error("[BuyerCallbackNotificationDao exception while updating buyer callback notification]" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}
	
	public void insertBuyerCallbackNotificationHistory()  throws DataServiceException{
		logger.info("Inisde insertBuyerCallbackNotificationHistory() DAO");
		try {
			queryForList("buyerCallbackNotification.processCallbackNotificationHistory");
			logger.info("Buyer callback notification record moved to history table successfully");
		} catch(Exception e) {
			logger.error("[BuyerCallbackNotificationDao exception while inserting buyer callback notification history]" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	public void updateBuyerCallbackNotificationFailure(long notificationId ,long noOfRetries, String status) throws DataServiceException{
		logger.info("Inisde updateBuyerCallbackNotificationFailure() DAO");
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("notificationId", notificationId);
			map.put("status", status);
			map.put("noOfRetries", noOfRetries);
			getSqlMapClientTemplate().update("buyerCallbackNotification.updateNotificationFailure", map);
		} catch(Exception e) {
			logger.error("[BuyerCallbackNotificationDao exception while updating buyer callback notification]" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

}
