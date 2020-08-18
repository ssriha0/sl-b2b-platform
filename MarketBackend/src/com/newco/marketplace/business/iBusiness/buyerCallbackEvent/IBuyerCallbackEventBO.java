package com.newco.marketplace.business.iBusiness.buyerCallbackEvent;

import java.util.List;

import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerKeysVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.newco.marketplace.exception.core.BusinessServiceException;


public interface IBuyerCallbackEventBO {

	public List<BuyerCallbackEvent> getBuyerCallbackEventList()throws BusinessServiceException ;
	
	public List<BuyerDetailsEventCallbackVO> getBuyerCallbackAPIDetailsList() throws BusinessServiceException;
	
	public List<BuyerKeysVO> getBuyerKeyDetailsList() throws BusinessServiceException;
	
}