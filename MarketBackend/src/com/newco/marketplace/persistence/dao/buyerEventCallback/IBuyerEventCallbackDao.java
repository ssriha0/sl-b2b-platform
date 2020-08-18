package com.newco.marketplace.persistence.dao.buyerEventCallback;

import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;

public interface IBuyerEventCallbackDao {
	
	public void updateBuyerCallbackNotification(BuyerCallbackNotificationVO notificationVo) throws DataServiceException;
	
}
