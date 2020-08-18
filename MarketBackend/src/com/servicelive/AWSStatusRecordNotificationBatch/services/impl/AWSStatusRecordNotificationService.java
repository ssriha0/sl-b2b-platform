package com.servicelive.AWSStatusRecordNotificationBatch.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.buyercallbacknotification.IAwsStatusNotificationActionDao;
import com.servicelive.AWSStatusRecordNotificationBatch.services.IAWSStatusRecordNotificationService;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

public class AWSStatusRecordNotificationService implements IAWSStatusRecordNotificationService {

	private static final Logger logger = Logger.getLogger(AWSStatusRecordNotificationService.class.getName());

	private IAwsStatusNotificationActionDao awsStatusNotificationActionDao;

	Date date = new Date();

	private String alertTo = System.getProperty("responsys.callback.alert.mailTo");
	private String alertCc = System.getProperty("responsys.callback.alert.mailCC");

	@Override
	public List<BuyerCallbackNotificationVO> getStatusDetails(List<String> param) throws DataServiceException {
		List<BuyerCallbackNotificationVO> buyerCallbackStatusDetails = new ArrayList<BuyerCallbackNotificationVO>();
		try {
			buyerCallbackStatusDetails = awsStatusNotificationActionDao.getStatusDetails(param);
		} catch (Exception e) {
			logger.error("Exception in getStatusDetails() method of AWSStatusRecordNotificationService :" + e);
			e.printStackTrace();
		}
		return buyerCallbackStatusDetails;
	}

	@Override
	public void setUpdatedAlertId(List<String> param) throws DataServiceException {
		try {
			awsStatusNotificationActionDao.setUpdatedAlert_id(param);
		} catch (Exception e) {
			logger.error("Exception in setUpdatedAlertId() method of AWSStatusRecordNotificationService :" + e);
			e.printStackTrace();
		}

	}

	@Override
	public void setAlertStatus(List<BuyerCallbackNotificationVO> list) throws DataServiceException {

		try {
			MultiMap alertTaskMultiMap = new MultiValueMap();
			AlertTask task = new AlertTask();
			task.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
			task.setAlertTypeId(AlertConstants.ALERT_TYPE_EMAIL);
			task.setPriority(AlertConstants.PRIORITY);
			task.setCreatedDate(date);
			task.setModifiedBy(AlertConstants.NOTIFICATION_SERVICE);
			task.setTemplateId(AlertConstants.TEMPLATE_TYPE_ALERT_RESPONSYS);
			task.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
			task.setAlertTo(alertTo);
			task.setAlertCc(alertCc);
			String input = null;
			Iterator<BuyerCallbackNotificationVO> iterator = list.iterator();
			while (iterator.hasNext()) {
				BuyerCallbackNotificationVO vo = iterator.next();
				alertTaskMultiMap.put(vo.getStatus(), vo.getSoId());
				if (!iterator.hasNext()) {
					alertTaskMultiMap.putIfAbsent(AlertConstants.UPDATE_NOTIFICATION_STATUS_WAITING_FOR_REQUEST_DATA,
							"Records are not available in this segment");
					alertTaskMultiMap.putIfAbsent(AlertConstants.UPDATE_NOTIFICATION_STATUS_SUCCESS,
							"Records are not available in this segment");
					alertTaskMultiMap.putIfAbsent(AlertConstants.CALLBACK_NOTIFICATION_STATUS_SUCCESS,
							"Records are not available in this segment");
					input = createKeyValueStringFromMap(alertTaskMultiMap);
				}
			}

			if (null != input) {
				logger.info("final multimap : ");
				logger.info(input);
				task.setTemplateInputValue(input);
			}

			awsStatusNotificationActionDao.setAlertStatus(task);
		} catch (Exception e) {

			logger.error("Exception in setAlertStatus() method of AWSStatusRecordNotificationService :" + e);
			e.printStackTrace();
		}
	}

	@Override
	public String createKeyValueStringFromMap(MultiMap alertTaskMap) {
		// TODO Auto-generated method stub
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

	public IAwsStatusNotificationActionDao getAwsStatusNotificationActionDao() {
		return awsStatusNotificationActionDao;
	}

	public void setAwsStatusNotificationActionDao(IAwsStatusNotificationActionDao awsStatusNotificationActionDao) {
		this.awsStatusNotificationActionDao = awsStatusNotificationActionDao;
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

}
