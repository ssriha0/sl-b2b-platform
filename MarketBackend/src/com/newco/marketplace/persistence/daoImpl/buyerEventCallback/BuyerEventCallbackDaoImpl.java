package com.newco.marketplace.persistence.daoImpl.buyerEventCallback;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.dao.buyerEventCallback.IBuyerEventCallbackDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerEventCallbackDaoImpl extends ABaseImplDao implements IBuyerEventCallbackDao {

	public final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param notificationVo
	 * @throws DataServiceException
	 */
	@Override
	 public void updateBuyerCallbackNotification(BuyerCallbackNotificationVO notificationVo) throws DataServiceException {
		 try {
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 map.put("status", notificationVo.getStatus());
			 map.put("response", notificationVo.getResponse());
			 map.put("exception", notificationVo.getException());
			 map.put("notificationId", notificationVo.getNotificationId());
			 getSqlMapClientTemplate().update("buyerEventCallback.updateNotification", map);
		 } catch(Exception e) {
			 logger.error("[BuyerEventCallbackDaoImpl exception while updating buyer callback notification]" + e.getMessage());
			 throw new DataServiceException(e.getMessage());
		 }
	
	 }
	
}
