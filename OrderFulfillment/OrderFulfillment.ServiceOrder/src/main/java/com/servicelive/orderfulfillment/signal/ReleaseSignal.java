package com.servicelive.orderfulfillment.signal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.*; 

import org.apache.commons.lang.StringUtils;

import com.servicelive.client.SimpleRestClient;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class ReleaseSignal extends RoutedProviderSignal {

	private static  final Logger logger = Logger.getLogger(ReleaseSignal.class);

    @Override
    protected void update(SOElement soe, ServiceOrder so) {
    	boolean isBuyerReschedule=false;
    	// release so from a single provider
    	if (soe instanceof RoutedProvider){
	        RoutedProvider source = (RoutedProvider)soe;
	        //since anyone authorized in the company can release the order
	        //therefore the resource id may not match the accepted resource id
	        //we need to make sure that we substitute the id with the accepted resource id
	        source.setProviderResourceId(so.getAcceptedProviderResourceId());
			boolean relayServicesNotifyFlag = isRelayServicesNotificationNeeded(so);
			if (relayServicesNotifyFlag) {
				boolean releaseByFirm = true;
				for (RoutedProvider provider : so.getRoutedResources()) {
					if (so.getAcceptedProviderId().intValue() == provider.getVendorId().intValue()
							&& !(so.getAcceptedProviderResourceId().intValue() == provider.getProviderResourceId().intValue())
							&& !(null != provider.getProviderResponse() && (provider.getProviderResponse().equals("RELEASED") || provider
									.getProviderResponse().equals("RELEASED_BY_FIRM")))) {
						releaseByFirm = false;
						break;
					}
				}
				
				logger.info("releaseByFirm:" + releaseByFirm);
				
				if (releaseByFirm) {
					
					Map<String, String> param = new HashMap<String, String>();
					
					if (null != so.getAcceptedProviderId()) {
						List<Integer> vendorIds = new ArrayList<Integer>();
						vendorIds.add(so.getAcceptedProviderId().intValue());
						String vendorDetails = serviceOrderDao.getVendorBNameList(vendorIds);
						if (StringUtils.isNotEmpty(vendorDetails)) {
							param.put("firmsdetails", vendorDetails);
						}
					}
					
					sendNotification(so, "ORDER_RELEASED_BY_FIRM", param);
				} else {
					sendNotification(so, "ORDER_RELEASED_BY_PROVIDER", null);
				}
			}

	        
	
	        //also delete the accepted provider contact and location information
	        SOContact contact = so.getVendorResourceContact();
	        contact.setServiceOrder(null);
	        so.getContacts().remove(contact);
	        serviceOrderDao.delete(contact);
	        
	       
	        
	        SOLocation location = so.getVendorResourceLocation();
	        location.setServiceOrder(null);
	        so.getLocations().remove(location);
	        serviceOrderDao.delete(location);
	        
	        //set method_of_acceptance and assignment_type as null
	        so.setAssignmentType(null);
	        //updating so_hdr values
	        
	        so.setAcceptedProviderId(null);
	        Integer resourceId = null;
	        if(null != so.getAcceptedProviderResourceId()){
	        	resourceId = so.getAcceptedProviderResourceId().intValue();
	        }
	        so.setAcceptedProviderResourceId(null);
	        SOSchedule reschedule = so.getReschedule();
	        if(reschedule!=null){
	        	so.getReschedule().setServiceDate1(null);
	        	so.getReschedule().setServiceDateGMT1(null);
	        	so.getReschedule().setServiceDateGMT2(null);
		        so.getReschedule().setServiceTimeStartGMT(null);
		        so.getReschedule().setServiceTimeEndGMT(null);
		        Integer dateTypeId=null;
		        so.getReschedule().setServiceDateTypeId(dateTypeId);
		        /*SOScheduleType scheduleType =null;
		        so.getReschedule().setServiceDateTypeId(scheduleType);*/
		       
	        }
	        // Remove any existing reschedule request
	        so.setReschedule(null);
	        so.setAcceptedDate(null);
	        so.setActArrivalStartDate(null);
	        so.setActArrivalEndDate(null);
	        so.setProviderTermsCondResp(null);
	        so.setProviderTermsCondDate(null);
	        so.setRoutedDate(new Date());
	        
	        if(null != so.getSOWorkflowControls()){
	        	so.getSOWorkflowControls().setMethodOfAcceptance(null);
	        	
	        	//SL-19291 Updating service date(in so_hdr) details with buyer preferred date details (in so_workflow_controls)
	        	SOSchedule buyerSchedule = so.getSOWorkflowControls().getBuyerSchedule();
		        if(null != buyerSchedule){
		        	isBuyerReschedule=true;
		        	so.getSOWorkflowControls().setBuyerSchedule(null);
			        so.setOldSchedule(so.getSchedule());
			        so.setSchedule(buyerSchedule);
			        // R12.0 Sprint 2 Updating current appointment date and time in so_trip when buyer request reschedule followed by provider release.
			        serviceOrderDao.updateSOTripForReschedule(buyerSchedule, so.getSoId());
		        }
	        }
	        //SL-19291: Here we iterate all the routed providers for the SO 
	        //if a provider's responseId is Rejected then set the responseId, responseReasonId, response date and comment as null
	        logger.info("About to enter For Loop: accepted resource id:"+resourceId);
			for (RoutedProvider provider : so.getRoutedResources()) {
				logger.info("Inside For Loop: Provider ID:"
						+ provider.getProviderResourceId() + " Response:"
						+ provider.getProviderResponse());
				if (isBuyerReschedule && provider.getProviderResponse() != null) {
					Integer routedResourceId = null;
					if(null != provider.getProviderResourceId()){
						routedResourceId = provider.getProviderResourceId().intValue();
					}
					if (!(routedResourceId.equals(resourceId))) {
						logger.info("Condition True");
						provider.setProviderRespId(null);
						provider.setProviderRespReasonId(null);
						provider.setProviderRespComment(null);
						provider.setProviderRespDate(null);
					}
				}
			}
	        super.update(source, so);
	        
    	}
    }

	@Override
	protected void processRoutedProvider(RoutedProvider provider, RoutedProvider target) {
        
        target.setProviderResponse(provider.getProviderResponse());
        target.setIncreaseSpendLimit(null);
        target.setProviderRespReasonId(provider.getProviderRespReasonId());
        target.setSchedule(null);
        target.setOfferExpirationDate(null);
        target.setTotalHours(null);
        target.setTotalLabor(null);
        target.setPartsMaterials(null);
        target.setProviderRespComment(provider.getProviderRespComment());
        target.setProviderRespDate(provider.getProviderRespDate());
	}
	
	public boolean isRelayServicesNotificationNeeded(
			ServiceOrder serviceOrder) {
		logger.info("Entering RelayOutboundNotificationCmd.isRelayServicesNotificationNeeded()...");
		ApplicationFlagLookup applicationFlagLookup = quickLookup.getApplicationFlagLookup();
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
	
	public void sendNotification(ServiceOrder serviceOrder,String eventType, Map<String, String> requestMap){
		
		logger.info("Entering RelayOutboundNotification sendNotification.");
			String URL=null;
			SimpleRestClient client = null;
			int responseCode = 0;
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
							
							if (null != requestMap && requestMap.size() > 0) {
								for (Map.Entry<String, String> keyValue : requestMap.entrySet()) {
									request.append(OrderfulfillmentConstants.AND).append(keyValue.getKey()).append(OrderfulfillmentConstants.EQUALS)
											.append(keyValue.getValue());
								}
							}
							
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
		
		
		logger.info("Leaving RelayOutboundNotification sendNotification.");
	
	}	
}
