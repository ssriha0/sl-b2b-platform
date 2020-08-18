package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.client.SimpleRestClient;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.ICARAssociationDao;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class RelayOutboundNotificationCmd extends SOCommand {
	QuickLookupCollection quickLookupCollection;
	ICARAssociationDao carAssociationDao;

	@Override
	public void execute(Map<String, Object> processVariables) {
		logger.info("Entering RelayOutboundNotificationCmd.");
		
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		boolean relayServicesNotifyFlag = isRelayServicesNotificationNeeded(serviceOrder);
		
		if(relayServicesNotifyFlag)
		{
			String URL=null;
			SimpleRestClient client = null;
			int responseCode = 0;
			String eventType = SOCommandArgHelper.extractStringArg(processVariables, 1);
			
			String newLaborPrice = null == serviceOrder.getSpendLimitLabor() ? "0.0" :serviceOrder.getSpendLimitLabor().toString();	
			String newPartsPrice = null == serviceOrder.getSpendLimitParts() ? "0.0" :serviceOrder.getSpendLimitParts().toString();	
			String oldLaborPrice =  (String)processVariables.get(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_LABOR_PRICE);
			String OldPartsPrice =  (String)processVariables.get(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_PARTS_PRICE);
			
			try {
					if(StringUtils.isNotBlank(serviceOrder.getCustomRefValue(OrderfulfillmentConstants.CALL_BACK_URL)))
						{
							URL=serviceOrder.getCustomRefValue(OrderfulfillmentConstants.CALL_BACK_URL);
							client = new SimpleRestClient(URL,"","",false);
							
							logger.info("URL for Webhooks:"+URL);
							StringBuffer request = new StringBuffer();
							request.append(OrderfulfillmentConstants.SERVICEPROVIDER);
							request.append(OrderfulfillmentConstants.EQUALS);
							request.append(OrderfulfillmentConstants.SERVICELIVE);
							request.append(OrderfulfillmentConstants.AND);
							request.append(OrderfulfillmentConstants.EVENT);
							request.append(OrderfulfillmentConstants.EQUALS);
							request.append(eventType.toLowerCase());
							request.append(OrderfulfillmentConstants.AND);
							request.append(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_LABOR_PRICE);
							request.append(OrderfulfillmentConstants.EQUALS);
							request.append(oldLaborPrice);
							request.append(OrderfulfillmentConstants.AND);
							request.append(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_PARTS_PRICE);
							request.append(OrderfulfillmentConstants.EQUALS);
							request.append(OldPartsPrice);
							request.append(OrderfulfillmentConstants.AND);
							request.append(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_NEW_LABOR_PRICE);
							request.append(OrderfulfillmentConstants.EQUALS);
							request.append(newLaborPrice);
							request.append(OrderfulfillmentConstants.AND);
							request.append(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_NEW_PARTS_PRICE);
							request.append(OrderfulfillmentConstants.EQUALS);
							request.append(newPartsPrice);
							logger.info("Request for Webhooks with service order id:"+serviceOrder.getSoId());
							logger.info("Request for Webhooks:"+request);
							responseCode = client.post(request.toString());
							//logging the request, response and soId in db
							serviceOrderDao.loggingRelayServicesNotification(serviceOrder.getSoId(),request.toString(),responseCode);
							
							logger.info("Response for Webhooks with service order id:"+serviceOrder.getSoId());
							logger.info("Response Code from Webhooks:"+responseCode);
						}
					
				
			} catch (Exception e) {
				logger.error("Exception occurred in RelayOutboundNotificationCmd.execute() due to "+e);
			}		
		}
		
		logger.info("Leaving RelayOutboundNotificationCmd.");
	}
	
	private boolean isRelayServicesNotificationNeeded(
			ServiceOrder serviceOrder) {
		logger.info("Entering RelayOutboundNotificationCmd.isRelayServicesNotificationNeeded()...");
		ApplicationFlagLookup applicationFlagLookup = quickLookupCollection.getApplicationFlagLookup();
		if (!applicationFlagLookup.isInitialized()) {
			throw new ServiceOrderException(
					"Unable to lookup ApplicationFlags. ApplicationFlagLookup not initialized.");
		}
		String relayServicesNotifyFlag = applicationFlagLookup
				.getPropertyValue("relay_services_notify_flag");
		Long buyerId = serviceOrder.getBuyerId();
		if ((OrderfulfillmentConstants.RELAY_SERVICES_BUYER_ID == buyerId || 
				OrderfulfillmentConstants.TECHTALK_SERVICES_BUYER_ID == buyerId) && ("ON").equals(relayServicesNotifyFlag)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// //////////////////////////////////////////////////////////////////////////
	// SETTERS FOR SPRING INJECTION
	// //////////////////////////////////////////////////////////////////////////
	public void setQuickLookupCollection(
			QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

	public void setCarAssociationDao(ICARAssociationDao carAssociationDao) {
		this.carAssociationDao = carAssociationDao;
	}
}
