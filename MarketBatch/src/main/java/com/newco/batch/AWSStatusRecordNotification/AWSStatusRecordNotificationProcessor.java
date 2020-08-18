package com.newco.batch.AWSStatusRecordNotification;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.util.scheduler.AWSStatusRecordNotificationScheduler;
import com.servicelive.AWSStatusRecordNotificationBatch.services.IAWSStatusRecordNotificationService;

public class AWSStatusRecordNotificationProcessor {
	private static final Logger logger = Logger.getLogger(AWSStatusRecordNotificationScheduler.class.getName());
	private IAWSStatusRecordNotificationService awsStatusRecordNotificationService;

	public void process() {
		logger.info("Entered in process() method of AWSStatusRecordNotificationProcessor");

		List<BuyerCallbackNotificationVO> buyerCallbackNotification = new ArrayList<BuyerCallbackNotificationVO>();
		List<String> param = new ArrayList<String>();
		param.add(0, AlertConstants.UPDATE_NOTIFICATION_STATUS_WAITING_FOR_REQUEST_DATA);
		param.add(1, AlertConstants.UPDATE_NOTIFICATION_STATUS_SUCCESS);
		param.add(2, AlertConstants.CALLBACK_NOTIFICATION_STATUS_SUCCESS);

		try {
			buyerCallbackNotification = awsStatusRecordNotificationService.getStatusDetails(param);
			logger.info("Data retrieved from buyer_callback_notification table suceessfully");
			if (!buyerCallbackNotification.isEmpty()) {
				awsStatusRecordNotificationService.setUpdatedAlertId(param);
				logger.info("Alert_id updated successfully in buyer_callback_notification");
				awsStatusRecordNotificationService.setAlertStatus(buyerCallbackNotification);
			}
		} catch (DataServiceException e) {
			logger.error("Exception in Process() method of AWSStatusRecordNotificationProcessor :" + e);
			e.printStackTrace();
		}

		logger.info("Leaving process() method of AWSStatusRecordNotificationProcessor");

	}

	public IAWSStatusRecordNotificationService getAwsStatusRecordNotificationService() {
		return awsStatusRecordNotificationService;
	}

	public void setAwsStatusRecordNotificationService(
			IAWSStatusRecordNotificationService awsStatusRecordNotificationService) {
		this.awsStatusRecordNotificationService = awsStatusRecordNotificationService;
	}

}
