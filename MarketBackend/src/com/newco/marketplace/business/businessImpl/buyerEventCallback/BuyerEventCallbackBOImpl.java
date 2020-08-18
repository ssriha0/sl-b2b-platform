package com.newco.marketplace.business.businessImpl.buyerEventCallback;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyerEventCallback.IBuyerEventCallbackBO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.dao.buyerEventCallback.IBuyerEventCallbackDao;

public class BuyerEventCallbackBOImpl implements IBuyerEventCallbackBO {
	private Logger logger = Logger.getLogger(BuyerEventCallbackBOImpl.class);
	protected IBuyerEventCallbackDao buyerEventCallbackDao;


	public void updateBuyerNotification(BuyerCallbackNotificationVO notificationVo) throws BusinessServiceException{
		logger.info("To update Buyer Acknowledgement Notification");
		try {
			buyerEventCallbackDao.updateBuyerCallbackNotification(notificationVo);;
		} catch (DataServiceException e) {
			e.printStackTrace();
		}		
	}
	
	public IBuyerEventCallbackDao getBuyerEventCallbackDao() {
		return buyerEventCallbackDao;
	}

	public void setBuyerEventCallbackDao(IBuyerEventCallbackDao buyerEventCallbackDao) {
		this.buyerEventCallbackDao = buyerEventCallbackDao;
	}

}
