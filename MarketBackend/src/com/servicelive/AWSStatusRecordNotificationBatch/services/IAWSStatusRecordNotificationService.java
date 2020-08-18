package com.servicelive.AWSStatusRecordNotificationBatch.services;

import java.util.List;
import org.apache.commons.collections.MultiMap;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;

public interface IAWSStatusRecordNotificationService {

	public List<BuyerCallbackNotificationVO> getStatusDetails(List<String> param) throws DataServiceException;

	public void setUpdatedAlertId(List<String> param) throws DataServiceException;

	public void setAlertStatus(List<BuyerCallbackNotificationVO> buyerCallbackNotification) throws DataServiceException;

	public String createKeyValueStringFromMap(MultiMap alertMap);

}
