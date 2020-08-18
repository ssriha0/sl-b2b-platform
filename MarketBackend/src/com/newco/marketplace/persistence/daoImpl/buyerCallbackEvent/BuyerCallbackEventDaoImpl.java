package com.newco.marketplace.persistence.daoImpl.buyerCallbackEvent;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerKeysVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.newco.marketplace.persistence.dao.buyerCallbackEvent.IBuyerCallbackEventDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.common.exception.DataServiceException;

public class BuyerCallbackEventDaoImpl extends ABaseImplDao implements IBuyerCallbackEventDao {
	
	private static final Logger logger = Logger
			.getLogger(BuyerCallbackEventDaoImpl.class.getName());
	
	public Integer getQueryByEventId(int actionId, Integer buyerId)
			throws DataServiceException {
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("actionId", actionId);
			params.put("buyerId", buyerId);
			
			
				return  (Integer) queryForObject("buyerCallbackEvent.getBuyerCallBackEventId", params);
	} catch (Exception e) {
		logger.error("BuyerCallbackEventDaoImpl.getBuyerCallBackEventId NoResultException" + StackTraceHelper.getStackTrace(e));
		throw new DataServiceException("Error", e);
	}
	}

	
	public BuyerCallbackEvent getBuyerCallbackEventDetail(int actionId,
			Integer buyerId) throws DataServiceException {
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("actionId", actionId);
			params.put("buyerId", buyerId);
			
				return (BuyerCallbackEvent) this.queryForObject("buyerCallbackEvent.getBuyerCallbackEventDetail", params);
	} catch (Exception e) {
		logger.error("BuyerCallbackEventDaoImpl.getBuyerCallbackEventDetail NoResultException" + StackTraceHelper.getStackTrace(e));
		throw new DataServiceException("Error", e);
	}
	}

	
	public List<BuyerCallbackEvent> getBuyerCallbackEventList(
			Integer buyerId) throws DataServiceException {
		
			List<BuyerCallbackEvent> buyerCallbackEvent = null;
			
			 logger.info("Inisde getBuyerCallbackEventList() of BuyerCallbackEventDaoImpl");
			 
				
				try {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("buyerId", buyerId);
					buyerCallbackEvent=(List<BuyerCallbackEvent>) queryForList("buyerCallbackEvent.getBuyerCallbackEventList", params);
				} catch (Exception e) {
					logger.error("BuyerCallbackEventDaoImpl.getBuyerCallbackEventList NoResultException" + StackTraceHelper.getStackTrace(e));
					throw new DataServiceException("Error", e);
				}
			 return buyerCallbackEvent;

	}
	
	
	public List<BuyerCallbackEvent> getCallbackEventList() throws DataServiceException {
		List<BuyerCallbackEvent> buyerCallbackEvent = null;
			 logger.info("Inisde getCallbackEventList() of BuyerCallbackEventDaoImpl");
				try {
					
					buyerCallbackEvent = (List<BuyerCallbackEvent>)queryForList("buyerCallbackEvent.getCallbackEventList");
				} catch (Exception e) {
					logger.error("BuyerCallbackEventDaoImpl.getCallbackEventList NoResultException" + StackTraceHelper.getStackTrace(e));
					throw new DataServiceException("Error", e);
				}
				return buyerCallbackEvent;
			 }
	
	public List<BuyerDetailsEventCallbackVO> getBuyerCallbackAPIDetailsList() throws DataServiceException {
		List<BuyerDetailsEventCallbackVO> buyerDetailsEventCallbackList = null;
			 logger.info("Inisde getCallbackEventList() of BuyerCallbackEventDaoImpl");
				try {
					
					buyerDetailsEventCallbackList = (List<BuyerDetailsEventCallbackVO>)queryForList("buyerCallbackEvent.getBuyerCallbackAPIDetailsList");
				} catch (Exception e) {
					logger.error("BuyerCallbackEventDaoImpl.getBuyerCallbackAPIDetailsList NoResultException" + StackTraceHelper.getStackTrace(e));
					throw new DataServiceException("Error", e);
				}
				return buyerDetailsEventCallbackList;
			 }
	
	public List<BuyerKeysVO> getBuyerKeyList() throws DataServiceException {
		 logger.info("Inisde getBuyerKeyList() of BuyerCallbackEventDaoImpl");
		 
		 List<BuyerKeysVO> buyerKeyList=null;
		 try {
			 buyerKeyList=(List<BuyerKeysVO>)queryForList("buyerEventCallback.getBuyerKeysList");
		 }catch (Exception e) {
			logger.error("BuyerCallbackEventDaoImpl.getBuyerKeyList NoResultException" + StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("Error", e);
		 }
		 
		 return buyerKeyList;
	}

	
}