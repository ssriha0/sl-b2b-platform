package com.servicelive.orderfulfillment.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.notification.INotificationTaskProcessor;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class AlertCmd extends SOCommand {
	protected INotificationTaskProcessor notificationTaskProcessor;

	QuickLookupCollection quickLookupCollection;

	public void execute(Map<String, Object> processVariables) {
		logger.debug("*** AlertCmd Command ***");

	
		

		BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection
				.getBuyerFeatureLookup();
		if (!buyerFeatureLookup.isInitialized()) {
			throw new ServiceOrderException(
					"Unable to lookup buyer feature for grouping. BuyerFeatureLookup not initialized.");
		}
		Long buyerId = getBuyerId(processVariables);
		
		if(null!=processVariables){
		String emailType = SOCommandArgHelper.extractStringArg(processVariables, 1);
		logger.info("emailType"+emailType);
		if(null!=emailType && emailType.equals("emailAutoAcceptBuyerRescheduleRequest")){
			// send email only if the value in application constant is true
			String value=serviceOrderDao.getApplicationFlagValue("emailAutoAcceptBuyerRescheduleRequest");
			if(!(null!=value && value.equalsIgnoreCase("true"))){
				logger.info("Email not sent for emailAutoAcceptBuyerRescheduleRequest");
				return;
				
			}
		}
		}

		// SL-19291 As per the new requirements there is no need to send email
		// while editing a reschedule request
		// by either buyer or provider.
		String isBuyerEditReschedule = (String) processVariables
				.get(OrderfulfillmentConstants.PVKEY_EDIT_RESCHEDULE_REQUEST);
		logger.info("Buyer Edit Reschedule:" + isBuyerEditReschedule);
		String value = " edited reschedule request";
		// When buyer or provider edit reschedule, AlertCmd will not work.
		if (!(null != isBuyerEditReschedule && value
				.equalsIgnoreCase(isBuyerEditReschedule))) {
			logger.info("Inside Alert Cmd");

			// Code Added for Jira SL-19728 
			// For Sending emails specific to NON FUNDED buyer
			// As per new requirements

			if (null!=buyerId && buyerFeatureLookup.isInitialized()
					&& buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(
							BuyerFeatureSetEnum.NON_FUNDED, buyerId)) {
				List<String> emailTasks = SOCommandArgHelper
						.extractAllCommandArgs(processVariables);
				Integer buyerIds = buyerId.intValue();
				for (String taskBldrCtxNm : emailTasks) {
					notificationTaskProcessor
							.processNotificationTaskForNonFunded(
									taskBldrCtxNm,
									createProcessVariablesCopy(processVariables),
									buyerIds);
				}

			} else {
				List<String> emailTasks = SOCommandArgHelper
						.extractAllCommandArgs(processVariables);

				for (String taskBldrCtxNm : emailTasks) {
					notificationTaskProcessor.processNotificationTask(
							taskBldrCtxNm,
							createProcessVariablesCopy(processVariables));
				}
			}
		}

	}
    public Long getBuyerId(Map<String, Object> processVariables) {
		String soId = getSoId(processVariables);
		if (soId == null) {
			logger.error("getServiceOrder - no order available for So ID: " + soId);
			return null;
		}
		ServiceOrder so=serviceOrderDao.getServiceOrder(soId);
		if(null!=so){
			return so.getBuyerId();
		}
		else{
			return null;
		}
	}
    

	protected Map<String, Object> createProcessVariablesCopy(
			Map<String, Object> processVariables) {
		HashMap<String, Object> copy = new HashMap<String, Object>();
		copy.putAll(processVariables);
		return copy;
	}

	public void setNotificationTaskProcessor(
			INotificationTaskProcessor notificationTaskProcessor) {
		this.notificationTaskProcessor = notificationTaskProcessor;
	}

	public void setQuickLookupCollection(
			QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

}
