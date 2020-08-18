package com.servicelive.FailedCommunicationToAwsNotificationBatch.services;

import java.util.List;
import java.util.Map;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;

public interface IFailedCommunicationToAwsNotificationService {

	public List<BuyerCallbackNotificationVO> getFailedCallbacksDetails(String error) throws DataServiceException;

	public void setUpdatedAlertId(String error) throws DataServiceException;

	public void setAlertStatus(List<BuyerCallbackNotificationVO> buyerCallbackNotification) throws DataServiceException;

	public String createKeyValueStringFromMap(Map alertMap);

}
