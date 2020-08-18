package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.client.SimpleRestClient;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Mustafa Motiwala
 *
 */
public class RejectSignal extends Signal{

	private static  final Logger logger = Logger.getLogger(RejectSignal.class);
	QuickLookupCollection quickLookupCollection;
    /*
     * Over ride the base method to find the correct RoutedProvider and then
     * update the response status. It deviates from PostSignal as Post needs to
     * Create the routed providers, whereas in reject we just need to update the
     * response for the already existing RoutedProvider instance. 
     */
    @Override
    protected void update(SOElement soe, ServiceOrder so) {
    	//Can not proceed. Bad request.
  
        boolean isRelay=false;
        Long buyerId = so.getBuyerId();
        if (OrderfulfillmentConstants.RELAY_SERVICES_BUYER_ID == buyerId || OrderfulfillmentConstants.TECHTALK_SERVICES_BUYER_ID == buyerId){
        	isRelay=true;
        }
        SOElementCollection col= (SOElementCollection)soe;
        List<SOElement> inputRoutedProviders = (List<SOElement>) col.getElements();
        List<RoutedProvider> routedProviders = so.getRoutedResources();
         
         
         if(isRelay){  
        	if( isRelayServicesNotificationNeeded(so)){
        		
        	Integer vendorId=null;
        	 boolean rejectByProvider=false;
        	 List<Long> resourceIdList=new ArrayList<Long>();	 
        	 
             for(SOElement rejectedProviderElement :inputRoutedProviders){
              	RoutedProvider rejectedProvider=(RoutedProvider)rejectedProviderElement;
        		  logger.info("rejectedProvider.getProviderResourceId():"+rejectedProvider.getProviderResourceId());

              	for(RoutedProvider routedProvider :routedProviders){
              		if(routedProvider.getProviderResourceId().equals(rejectedProvider.getProviderResourceId()))   				
              				{
              		  logger.info("routedProvider.getProviderResourceId():"+routedProvider.getProviderResourceId());

              			vendorId=routedProvider.getVendorId();
              		  logger.info("vendorId 1:"+vendorId);
              			break;
              		}           	
              	}
              }
             
             logger.info("vendorId 2:"+vendorId); 
             
             
             for(SOElement rejectedProviderElement :inputRoutedProviders){
               	RoutedProvider rejectedProvider=(RoutedProvider)rejectedProviderElement;
               	resourceIdList.add(rejectedProvider.getProviderResourceId()); 
             }
             
             logger.info("resourceIdList"+resourceIdList.size());
               	for(RoutedProvider routedProvider :routedProviders){
               		
               		String providerResponse=null;
               		if(null!=routedProvider.getProviderResponse())
               			providerResponse=routedProvider.getProviderResponse().toString();
               		
          		  logger.info("00routedProvider.getVendorId()"+routedProvider.getVendorId());
          		  logger.info("00routedProvider.getProviderResourceId()"+routedProvider.getProviderResourceId());
          		  logger.info("00routedProvider.getProviderResponse()"+providerResponse);

          		logger.info("01"+(null!=providerResponse  && providerResponse.equals("RELEASED")));
          		logger.info("02"+(null!=providerResponse  && providerResponse.equals("RELEASED_BY_FIRM")));
          		logger.info("03"+(null!=providerResponse  && providerResponse.equals("REJECTED")));
               		if(vendorId.intValue()==routedProvider.getVendorId().intValue() && (null== providerResponse || !( providerResponse.equals("RELEASED")|| 
               				providerResponse.equals("RELEASED_BY_FIRM") ||  providerResponse.equals("REJECTED")))
      						
      						&& !resourceIdList.contains(routedProvider.getProviderResourceId())
              				 ){
                       	logger.info("rejectByProvider=true"+routedProvider.getProviderResourceId());
               			rejectByProvider=true;
               			break;
               			
               		}           	
               	}
               	logger.info("rejectByProvider value::"+rejectByProvider);
             if(rejectByProvider){
		    		sendNotification(so,"ORDER_REJECTED_BY_PROVIDER");

             }else{
		    		sendNotification(so,"ORDER_REJECTED_BY_FIRM");
             }
             
        	}
         }

         for(SOElement rejectedProviderElement :inputRoutedProviders){
         	RoutedProvider rejectedProvider=(RoutedProvider)rejectedProviderElement;
         	for(RoutedProvider routedProvider :routedProviders){
         		if(routedProvider.getProviderResourceId().equals(rejectedProvider.getProviderResourceId())){
         			 routedProvider.setProviderRespReasonId(rejectedProvider.getProviderRespReasonId());
         	         routedProvider.setProviderResponse(rejectedProvider.getProviderResponse());
         	         routedProvider.setProviderRespComment(rejectedProvider.getProviderRespComment());
         	         routedProvider.setModifiedBy(rejectedProvider.getModifiedBy());
         	         routedProvider.setProviderRespDate(rejectedProvider.getProviderRespDate());
         	         routedProvider.setModifiedDate(rejectedProvider.getModifiedDate());
         		}
         	
         	}
         }
         so.setRoutedResources(routedProviders);
         serviceOrderDao.update(so);
    }
    
    
	public boolean isRelayServicesNotificationNeeded(
			ServiceOrder serviceOrder) {
		logger.info("Entering RelayOutboundNotificationCmd.isRelayServicesNotificationNeeded()...");
		ApplicationFlagLookup applicationFlagLookup = quickLookupCollection.getApplicationFlagLookup();
		if (!applicationFlagLookup.isInitialized()) {
			throw new ServiceOrderException(
					"Unable to lookup ApplicationFlags. ApplicationFlagLookup not initialized.");
		}
		String relayServicesNotifyFlag = applicationFlagLookup
				.getPropertyValue("relay_services_notify_flag");
		if (("ON").equals(relayServicesNotifyFlag)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public void sendNotification(ServiceOrder serviceOrder,String eventType){
		
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
    
	
	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}


	public void setQuickLookupCollection(
			QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}
    
    
}
