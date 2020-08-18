package com.newco.marketplace.persistence.iDao.buyercallbacknotification;

import java.util.List;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;

public interface IAwsStatusNotificationActionDao {

	public List<BuyerCallbackNotificationVO> getStatusDetails(List<String> param) throws DataServiceException;

	public void setUpdatedAlert_id(List<String> param) throws DataServiceException;

	public boolean setAlertStatus(AlertTask alertTask) throws DataServiceException;

}
