package com.newco.marketplace.persistence.dao.buyerCallbackEvent;

import java.util.List;

import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerKeysVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.servicelive.common.exception.DataServiceException;

public interface IBuyerCallbackEventDao {

	public Integer getQueryByEventId(int actionId,Integer buyerId) throws DataServiceException;
	
	public BuyerCallbackEvent getBuyerCallbackEventDetail(int actionId,Integer buyerId) throws DataServiceException;
	
	public List<BuyerCallbackEvent> getBuyerCallbackEventList(Integer buyerId) throws DataServiceException;
	
	public List<BuyerCallbackEvent> getCallbackEventList() throws DataServiceException;
	
	public List<BuyerDetailsEventCallbackVO> getBuyerCallbackAPIDetailsList() throws DataServiceException;
	
	public List<BuyerKeysVO> getBuyerKeyList() throws DataServiceException;
	
}
