package com.servicelive.orderfulfillment.command;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.marketplatform.common.vo.BuyerCallbackEventVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.vo.BuyerCallBackNotificationVO;

public class BuyerCallbackNotificationCmd extends SOCommand {
	QuickLookupCollection quickLookupCollection;
	IMarketPlatformBuyerBO marketPlatformBuyerBO;

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.info("Inside BuyerCallbackNotificationCmd");
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		Long buyerId = serviceOrder.getBuyerId();
		String soId =  serviceOrder.getSoId();

		BuyerCallbackEventVO buyerCallbackEventVO = new BuyerCallbackEventVO();
		BuyerCallBackNotificationVO buyerCallBackNotificationVO = new BuyerCallBackNotificationVO();
		String signalName = SOCommandArgHelper.extractStringArg(
				processVariables, 1);
		logger.info("Inside BuyerCallbackNotificationCmd and StateName is "
				+ signalName);
		if(buyerId!=null && StringUtils.isNotBlank(soId)){
			boolean isCallbackFlag = checkBuyerCallbackExist(buyerId);

			
			if (isCallbackFlag) {

				if (null != signalName
						&& (getCorrespondingActionId(signalName)!=null)) {


					try {

						buyerCallbackEventVO = marketPlatformBuyerBO
								.fetchBuyerCallbackEvent(buyerId.toString(),
										getCorrespondingActionId(signalName));
						buyerCallBackNotificationVO.setEventId(buyerCallbackEventVO
								.getEventId());
						buyerCallBackNotificationVO.setSoId(soId);
						buyerCallBackNotificationVO = populateCallbackNotification(buyerCallBackNotificationVO);
						serviceOrderDao
						.insertCallBackNotification(buyerCallBackNotificationVO);
					} catch (Exception e) {
						logger.error("Exception in inserting values into buyer_callback_notification table"
								+ e.getMessage());
					}
				}
			}
		}
	}

	private String getCorrespondingActionId(String stateName) {


		BuyerEventName status = BuyerEventName.valueOf(stateName);
		String returnValue =null;
		switch(status){
		case ACCEPT_ORDER:
			returnValue ="1";
			break;
		case RESCHEDULE_ACCEPT:
			returnValue="2";
			break;
		case CANCEL_ORDER:
			returnValue="3";
			break;
		default:
			returnValue = serviceOrderDao.getCorrespondingActionId(stateName);
		}
		return returnValue;
	}
	private BuyerCallBackNotificationVO populateCallbackNotification(BuyerCallBackNotificationVO buyerCallBackNotificationVO) {

		String seqNo = getSequenceId();
		buyerCallBackNotificationVO.setSeqNo(seqNo);
		buyerCallBackNotificationVO.setCreatedDate(new Date());
		buyerCallBackNotificationVO.setModifiedDate(new Date());
		buyerCallBackNotificationVO
		.setNoOfRetries(OrderfulfillmentConstants.NO_OF_RETRIES);
		buyerCallBackNotificationVO
		.setStatus(OrderfulfillmentConstants.STATUS_WAITING_FOR_REQUEST_DATA);

		return buyerCallBackNotificationVO;
	}

	private String getSequenceId() {
		String seqNo = "";
		try {
			seqNo = serviceOrderDao.getCorrelationId();
			int length = 8 - seqNo.length();
			for (int i = 0; i < length; i++) {
				seqNo = "0" + seqNo;
			}

		} catch (Exception e) {
			logger.error(
					"Exception in NotificationServiceCoordinator.getSequenceNo() "
							+ e.getMessage(), e);
		}
		return seqNo;
	}


	private boolean checkBuyerCallbackExist(Long buyerId) {

		return serviceOrderDao.checkBuyerCallbackExist(buyerId);

	}


	public void setQuickLookupCollection(
			QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}


	public void setMarketPlatformBuyerBO(
			IMarketPlatformBuyerBO marketPlatformBuyerBO) {
		this.marketPlatformBuyerBO = marketPlatformBuyerBO;
	}
	
	

}
