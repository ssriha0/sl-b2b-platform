package com.newco.marketplace.business.businessImpl.buyerCallbackNotification;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.BuyerCallbackNotification.IBuyerCallbackNotificationBO;
import com.newco.marketplace.buyercallbacknotification.client.BuyerCallBackNotificationClient;
import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerCallBackNotificationSchedulerVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.buyercallbacknotification.IBuyerCallbackNotificationDao;
import com.servicelive.common.exception.DataServiceException;

public class BuyerCallbackNotificationBOImpl implements IBuyerCallbackNotificationBO{
	
	private static final Logger logger = Logger
			.getLogger(BuyerCallbackNotificationBOImpl.class.getName());
	
	private IBuyerCallbackNotificationDao buyerCallbackNotificationDao;
	private BuyerCallBackNotificationClient buyerCallBackNotificationClient;
	
	public List<BuyerCallBackNotificationSchedulerVO> getNotificationServiceOrderDetails() throws BusinessServiceException{
		logger.info("Inisde getNotificationServiceOrderDetails()");
		try {
			return buyerCallbackNotificationDao.getNotificationServiceOrderDetails();
		} catch (DataServiceException e) {
			logger.error("Exception in fetching notification service order details"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	
	public void updateBuyerCallbackNotification(long notificationId, String requestData, String status) throws BusinessServiceException{
		logger.info("Inisde updateBuyerCallbackNotification()");
		try {
			buyerCallbackNotificationDao.updateBuyerCallbackNotification(notificationId, requestData, status);
		} catch (DataServiceException e) {
			logger.error("Exception in updating callback notification"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	
	public void updateBuyerCallbackNotificationFailure(long notificationId, long noOfRetries, String status) throws BusinessServiceException{
		logger.info("Inisde updateBuyerCallbackNotificationFailure()");
		try {
			buyerCallbackNotificationDao.updateBuyerCallbackNotificationFailure(notificationId, noOfRetries, status);
		} catch (DataServiceException e) {
			logger.error("Exception in updating callback notification"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	
	public void insertBuyerCallbackNotificationHistory()  throws BusinessServiceException {
		logger.info("Inisde insertBuyerCallbackNotificationHistory()");
		try {
			buyerCallbackNotificationDao.insertBuyerCallbackNotificationHistory();
		}catch (DataServiceException e) {
			logger.error("Exception in inserting callback notification history"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	
	public String getServiceOrderDetails(String buyerId, String soId, String responseFilter) throws BusinessServiceException{
		return buyerCallBackNotificationClient.getServiceOrderDetails(buyerId, soId, responseFilter);
	}
	
	public String getBuyerCallbackFilters(String buyerId, Integer actionId) throws BusinessServiceException{
		return buyerCallBackNotificationClient.getBuyerCallbackFilters(buyerId, actionId);
	}
	
	

	/**
	 * @return the buyerCallbackNotificationDao
	 */
	public IBuyerCallbackNotificationDao getBuyerCallbackNotificationDao() {
		return buyerCallbackNotificationDao;
	}

	/**
	 * @param buyerCallbackNotificationDao the buyerCallbackNotificationDao to set
	 */
	public void setBuyerCallbackNotificationDao(IBuyerCallbackNotificationDao buyerCallbackNotificationDao) {
		this.buyerCallbackNotificationDao = buyerCallbackNotificationDao;
	}

	/**
	 * @return the buyerCallBackNotificationClient
	 */
	public BuyerCallBackNotificationClient getBuyerCallBackNotificationClient() {
		return buyerCallBackNotificationClient;
	}

	/**
	 * @param buyerCallBackNotificationClient the buyerCallBackNotificationClient to set
	 */
	public void setBuyerCallBackNotificationClient(BuyerCallBackNotificationClient buyerCallBackNotificationClient) {
		this.buyerCallBackNotificationClient = buyerCallBackNotificationClient;
	}
	
	
	

}
