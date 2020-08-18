package com.newco.marketplace.business.buyerCallBackEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.newco.marketplace.business.iBusiness.buyerCallbackEvent.IBuyerCallbackEventBO;
import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerKeysVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.newco.marketplace.exception.core.BusinessServiceException;



public class BuyerCallbackEventsCache implements InitializingBean	{
	private static final Logger logger = Logger
			.getLogger(BuyerCallbackEventsCache.class.getName());
	private IBuyerCallbackEventBO buyerCallbackEventBO;
	
	
	Map<String,BuyerCallbackEvent> EventBuyerCallBackEventMap = new HashMap<String,  BuyerCallbackEvent>();
	Map<String, BuyerDetailsEventCallbackVO> EventBuyerCallbackAPIDetailsMap = new HashMap<String,  BuyerDetailsEventCallbackVO>();
	Map<String, BuyerKeysVO> BuyerKeysMap = new HashMap<String,  BuyerKeysVO>();
	
	public void afterPropertiesSet() throws Exception {
		logger.info("Enter the  BuyerCallbackEventsCache.afterPropertiesSet(");
		loadBuyerCallbackEvents();
		loadBuyerCallbackAPIDetails();
		loadBuyerKeyDetails();
	}

	
	
	private void  loadBuyerCallbackAPIDetails() throws BusinessServiceException
	{
		logger.info("Initializing buyer details for callback service map ");
		try {
		 List<BuyerDetailsEventCallbackVO> buyerDetailsEventCallbackVOList = buyerCallbackEventBO.getBuyerCallbackAPIDetailsList();
        
        if (buyerDetailsEventCallbackVOList != null) {
            for (BuyerDetailsEventCallbackVO buyerDetailsEventCallbackVO : buyerDetailsEventCallbackVOList) {
            	EventBuyerCallbackAPIDetailsMap.put(buyerDetailsEventCallbackVO.getBuyerId().toString(), buyerDetailsEventCallbackVO);
            }
        }
		
		} catch (BusinessServiceException e) {
			logger.info(" Error occureed in BuyerCallbackEventsCache.loadBuyerCallbackEvents() ");
			e.printStackTrace();
		}
		logger.info("BuyerCallbackEventsCache = "
				+ EventBuyerCallBackEventMap.toString());
	}
	
	private void  loadBuyerCallbackEvents() throws BusinessServiceException
	{
		logger.info("Initializing buyer call back events map ");
		try {
		 List<BuyerCallbackEvent> loadBuyerCallbackEvents = buyerCallbackEventBO.getBuyerCallbackEventList();
        
        if (loadBuyerCallbackEvents != null) {
            for (BuyerCallbackEvent buyerCallbackEvent : loadBuyerCallbackEvents) {
            String key= buyerCallbackEvent.getBuyerId().toString()+"_"+buyerCallbackEvent.getActionId();
            	
                EventBuyerCallBackEventMap.put(key, buyerCallbackEvent);
            }
        }
		
		} catch (BusinessServiceException e) {
			logger.info(" Error occureed in BuyerCallbackEventsCache.loadBuyerCallbackEvents() ");
			e.printStackTrace();
		}
		logger.info("BuyerCallbackEventsCache = "
				+ EventBuyerCallBackEventMap.toString());
	}
	
	private void loadBuyerKeyDetails() throws BusinessServiceException {
		logger.info("Initializing buyer call back events map ");
		try {
			List<BuyerKeysVO> buyerKeyList=buyerCallbackEventBO.getBuyerKeyDetailsList();
			if(buyerKeyList!=null){
				for (BuyerKeysVO buyerKeysVO : buyerKeyList) {
					BuyerKeysMap.put(buyerKeysVO.getBuyerId(), buyerKeysVO);
	            }
		       
			}
		} catch (BusinessServiceException e) {
			logger.info(" Error occureed in BuyerCallbackEventsCache.loadBuyerKeydetails() ");
			e.printStackTrace();
		}
	}
	
	public BuyerCallbackEvent fetchBuyerCallbackEvent(String BuyerId, String actionId) {
		logger.info("Entering  the fetchBuyerCallbackEvent");
		BuyerCallbackEvent buyerCallbackEvent= new BuyerCallbackEvent();
			if (null != EventBuyerCallBackEventMap
					&& (!StringUtils.isBlank(BuyerId+"_"+actionId)
			                && EventBuyerCallBackEventMap.containsKey(BuyerId+"_"+actionId))) {
				
				buyerCallbackEvent= EventBuyerCallBackEventMap.get(BuyerId+"_"+actionId);
		}
			logger.info("Fetching the event Id" + buyerCallbackEvent.getEventId());
		
		return buyerCallbackEvent;
	}

	
	public BuyerDetailsEventCallbackVO fetchBuyerCallbackAPIDetails(Integer buyerId) {
		logger.info("Entering  the fetchBuyerBuyerCallbackAPIDetails");
		BuyerDetailsEventCallbackVO buyerDetailsEventCallbackVO= new BuyerDetailsEventCallbackVO();
			if (null != EventBuyerCallBackEventMap ) {
				buyerDetailsEventCallbackVO= EventBuyerCallbackAPIDetailsMap.get(buyerId.toString());
		}
			logger.info("Fetched Buyer Callback API details for buyer : " + buyerDetailsEventCallbackVO.getBuyerId());
		
		return buyerDetailsEventCallbackVO;
	}
	
	public BuyerKeysVO fetchBuyerKeyDetails(String buyerId) {
		logger.info("Entering  the fetchBuyerKeyDetails");
		BuyerKeysVO buyerKeysVO=new BuyerKeysVO();
		if(BuyerKeysMap!=null){
			buyerKeysVO=BuyerKeysMap.get(buyerId);
		}
		logger.info("Fetched Buyer key details for buyer : " + buyerKeysVO.getBuyerId());
		
		return buyerKeysVO;
	}


	public IBuyerCallbackEventBO getBuyerCallbackEventBO() {
		return buyerCallbackEventBO;
	}



	public void setBuyerCallbackEventBO(IBuyerCallbackEventBO buyerCallbackEventBO) {
		this.buyerCallbackEventBO = buyerCallbackEventBO;
	}

}
