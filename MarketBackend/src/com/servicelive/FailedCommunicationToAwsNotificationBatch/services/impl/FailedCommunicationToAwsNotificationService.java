package com.servicelive.FailedCommunicationToAwsNotificationBatch.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.buyercallbacknotification.IFailedCommunicationToAwsActionDao;
import com.servicelive.FailedCommunicationToAwsNotificationBatch.services.IFailedCommunicationToAwsNotificationService;

public class FailedCommunicationToAwsNotificationService implements IFailedCommunicationToAwsNotificationService {

	private static final Logger logger = Logger.getLogger(FailedCommunicationToAwsNotificationService.class.getName());

	private IFailedCommunicationToAwsActionDao failedCommunicationToAwsActionDao;

	Date date = new Date();
	Map<String, Object> alertTaskMap = new HashMap<String, Object>();
	private String alertTo = System.getProperty("responsys.callback.alert.mailTo");
	private String alertCc = System.getProperty("responsys.callback.alert.mailCC");

	@Override
	public List<BuyerCallbackNotificationVO> getFailedCallbacksDetails(String error) throws DataServiceException {
		List<BuyerCallbackNotificationVO> buyerCallbackFailureDetails = new ArrayList<BuyerCallbackNotificationVO>();
		try {
			buyerCallbackFailureDetails = failedCommunicationToAwsActionDao.getFailureDetails(error);
		} catch (Exception e) {
			logger.error("Exception in getFailedCallbacksDetails() method of FailedCommunicationToAwsNotificationService :"+ e);
			e.printStackTrace();
		}
		return buyerCallbackFailureDetails;
	}

	@Override
	public void setUpdatedAlertId(String error) throws DataServiceException {
		try {
			failedCommunicationToAwsActionDao.setUpdatedAlert_id(error);
		} catch (Exception e) {
			logger.error("Exception in setUpdatedAlertId() method of FailedCommunicationToAwsNotificationService :" + e);
			e.printStackTrace();
		}
	}

	@Override
	public void setAlertStatus(List<BuyerCallbackNotificationVO> list) throws DataServiceException {
		String input;
		AlertTask task = new AlertTask();
		Iterator<BuyerCallbackNotificationVO> iterator = list.iterator();
		while (iterator.hasNext()) {
			BuyerCallbackNotificationVO vo = iterator.next();
			task.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
			task.setAlertTypeId(AlertConstants.ALERT_TYPE_EMAIL);
			task.setPriority(AlertConstants.PRIORITY);
			task.setCreatedDate(date);
			task.setModifiedBy(AlertConstants.NOTIFICATION_SERVICE);
			task.setTemplateId(AlertConstants.TEMPLATE_TYPE_FAILURE_ALERT_RESPONSYS);
			task.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
			task.setAlertTo(alertTo);
			task.setAlertCc(alertCc);
			alertTaskMap.put(AlertConstants.SO_ID, vo.getSoId());
			alertTaskMap.put(AlertConstants.EXCEPTION, vo.getException());
			input = createKeyValueStringFromMap(alertTaskMap);
			task.setTemplateInputValue(input);
			failedCommunicationToAwsActionDao.setAlertStatus(task);
		}

	}

	@Override
	public String createKeyValueStringFromMap(Map alertMap) {
		StringBuffer stringBuffer = new StringBuffer("");
		boolean isFirstKey = true;
		if (null != alertTaskMap) {
			Set<String> keySet = alertTaskMap.keySet();
			for (String key : keySet) {
				if (isFirstKey) {
					isFirstKey = !isFirstKey;
				} else {
					stringBuffer.append("|");
				}
				stringBuffer.append(key).append("=").append(alertTaskMap.get(key));
			}
		}
		return stringBuffer.toString();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAlertTo() {
		return alertTo;
	}

	public void setAlertTo(String alertTo) {
		this.alertTo = alertTo;
	}

	public String getAlertCc() {
		return alertCc;
	}

	public void setAlertCc(String alertCc) {
		this.alertCc = alertCc;
	}

	public IFailedCommunicationToAwsActionDao getFailedCommunicationToAwsActionDao() {
		return failedCommunicationToAwsActionDao;
	}

	public void setFailedCommunicationToAwsActionDao(
			IFailedCommunicationToAwsActionDao failedCommunicationToAwsActionDao) {
		this.failedCommunicationToAwsActionDao = failedCommunicationToAwsActionDao;
	}

	public Map<String, Object> getAlertTaskMap() {
		return alertTaskMap;
	}

	public void setAlertTaskMap(Map<String, Object> alertTaskMap) {
		this.alertTaskMap = alertTaskMap;
	}

}
