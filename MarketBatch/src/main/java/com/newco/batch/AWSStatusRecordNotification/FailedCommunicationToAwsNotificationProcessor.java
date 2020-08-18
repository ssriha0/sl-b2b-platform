package com.newco.batch.AWSStatusRecordNotification;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.servicelive.FailedCommunicationToAwsNotificationBatch.services.IFailedCommunicationToAwsNotificationService;

public class FailedCommunicationToAwsNotificationProcessor {

	private static final Logger logger = Logger
			.getLogger(FailedCommunicationToAwsNotificationProcessor.class.getName());
	private IFailedCommunicationToAwsNotificationService failedCommunicationToAwsNotificationService;

	public void process() {
		logger.info("Entered in process() method of FailedCommunicationToAwsNotificationProcessor");

		List<BuyerCallbackNotificationVO> buyerCallbackFailureDetails = new ArrayList<BuyerCallbackNotificationVO>();
		String error = AlertConstants.CALLBACK_NOTIFICATION_STATUS_FAILURE;
		try {
			buyerCallbackFailureDetails = failedCommunicationToAwsNotificationService.getFailedCallbacksDetails(error);
			logger.info("Failure Data retrieved from buyer_callback_notification suceessfully");
			if (!buyerCallbackFailureDetails.isEmpty()) {
				failedCommunicationToAwsNotificationService.setUpdatedAlertId(error);
				logger.info("Alert_id updated successfully in buyer_callback_notification");
				failedCommunicationToAwsNotificationService.setAlertStatus(buyerCallbackFailureDetails);
			}
		} catch (DataServiceException e) {
			logger.error("Exception in Process() method of FailedCommunicationToAwsNotificationProcessor :" + e);
			e.printStackTrace();
		}

		logger.info("Leaving process() method of AWSStatusRecordNotificationProcessor");
	}

	public IFailedCommunicationToAwsNotificationService getFailedCommunicationToAwsNotificationService() {
		return failedCommunicationToAwsNotificationService;
	}

	public void setFailedCommunicationToAwsNotificationService(
			IFailedCommunicationToAwsNotificationService failedCommunicationToAwsNotificationService) {
		this.failedCommunicationToAwsNotificationService = failedCommunicationToAwsNotificationService;
	}

}
