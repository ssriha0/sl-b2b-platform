package com.newco.marketplace.persistence.iDao.buyercallbacknotification;

import java.util.List;

import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerCallBackNotificationSchedulerVO;
import com.servicelive.common.exception.DataServiceException;

public interface IBuyerCallbackNotificationDao {
	
	public List<BuyerCallBackNotificationSchedulerVO> getNotificationServiceOrderDetails() throws DataServiceException;
	public void updateBuyerCallbackNotification(long notificationId, String requestData, String status) throws DataServiceException;
	public void updateBuyerCallbackNotificationFailure(long notificationId ,long noOfRetries, String status) throws DataServiceException;
	public void insertBuyerCallbackNotificationHistory()  throws DataServiceException;	
}
