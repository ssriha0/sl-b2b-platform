package com.newco.marketplace.business.iBusiness.BuyerCallbackNotification;

import java.util.List;

import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerCallBackNotificationSchedulerVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IBuyerCallbackNotificationBO {
	
	public List<BuyerCallBackNotificationSchedulerVO> getNotificationServiceOrderDetails() throws BusinessServiceException;
	public String getServiceOrderDetails(String buyerId, String soId, String responseFilter) throws BusinessServiceException;
	public void updateBuyerCallbackNotification(long notificationId, String requestData, String status) throws BusinessServiceException;
	public void updateBuyerCallbackNotificationFailure(long notificationId, long noOfRetries, String status) throws BusinessServiceException;
	public String getBuyerCallbackFilters(String buyerId, Integer actionId) throws BusinessServiceException;
	public void insertBuyerCallbackNotificationHistory()  throws BusinessServiceException;

}
