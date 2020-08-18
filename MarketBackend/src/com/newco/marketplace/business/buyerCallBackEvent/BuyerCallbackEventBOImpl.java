package com.newco.marketplace.business.buyerCallBackEvent;


import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyerCallbackEvent.IBuyerCallbackEventBO;
import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerKeysVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.dao.buyerCallbackEvent.IBuyerCallbackEventDao;
import com.servicelive.common.exception.DataServiceException;

public class BuyerCallbackEventBOImpl implements IBuyerCallbackEventBO{
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
			.getLogger(BuyerCallbackEventBOImpl.class.getName());
	private IBuyerCallbackEventDao buyerCallbackEventDao;
	
	public List<BuyerCallbackEvent> getBuyerCallbackEventList() throws BusinessServiceException {
		
		logger.info("For fetching the all registered Callback Events");
		List<BuyerCallbackEvent> buyerCallbackEvents=null;
	
		try {
			buyerCallbackEvents= buyerCallbackEventDao.getCallbackEventList();
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buyerCallbackEvents;
	}
	
public List<BuyerDetailsEventCallbackVO> getBuyerCallbackAPIDetailsList() throws BusinessServiceException {
		
		logger.info("To fetch buyers callback API Details");
		List<BuyerDetailsEventCallbackVO> buyerDetailsEventCallbackVOList=null;
	
		try {
			buyerDetailsEventCallbackVOList= buyerCallbackEventDao.getBuyerCallbackAPIDetailsList();
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return buyerDetailsEventCallbackVOList;
	}
	
	public List<BuyerKeysVO> getBuyerKeyDetailsList() throws BusinessServiceException {
		logger.info("To fetch buyers key Details");
		
		List<BuyerKeysVO> buyerKeys=null;
		try {
			buyerKeys=buyerCallbackEventDao.getBuyerKeyList();
		} catch (DataServiceException e) {
			logger.info("exception while fetching the all buyers key details");
		}
		
		return buyerKeys;
	}



	public IBuyerCallbackEventDao getBuyerCallbackEventDao() {
		return buyerCallbackEventDao;
	}
	public void setBuyerCallbackEventDao(
			IBuyerCallbackEventDao buyerCallbackEventDao) {
		this.buyerCallbackEventDao = buyerCallbackEventDao;
	}
	
	
	
}
