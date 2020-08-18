package com.newco.marketplace.business.iBusiness.buyerEventCallback;

import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IBuyerEventCallbackBO {
	
	public void updateBuyerNotification(BuyerCallbackNotificationVO notificationVO) throws BusinessServiceException;
}
