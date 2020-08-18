package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.client.SimpleRestClient;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.SPNetHdrVO;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOChild;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.TierRouteProviders;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class PostSignal extends Signal {
	
	private boolean isPostedFromFE = false;
    
    @Override
	protected void update(SOElement soe, ServiceOrder so) {
		logger.info("Inside PostSignal.process()");

		// we need to update routed providers
		List<Long> currentResourcesIds = getCurrentResourcesIds(so);
		// soe should be a SOElementCollection and
		// should contain the information about routed providers

		if (null != so.getSOWorkflowControls() && null != so.getSOWorkflowControls().getTierRouteInd()
				&& so.getSOWorkflowControls().getTierRouteInd()) {
			logger.info("Tier Route Ind.." + so.getSOWorkflowControls().getTierRouteInd());
			SOElementCollection soec = (SOElementCollection) soe;
			if (null == soec.getElements() || soec.getElements().isEmpty()) {
				logger.info("SOELEMENT EMPTy");
			} else {
				logger.info("not empty");
			}
			/*
			 * logger.info("so.getRoutedResources().size() befor delete>>"+so.
			 * getRoutedResources().size());
			 * if(so.getRoutedResources().size()>0){
			 * logger.info("abt to delete SRP>>");
			 * serviceOrderDao.deleteSoRoutedProviders(so.getSoId());
			 * so.setRoutedResources(new ArrayList<RoutedProvider>()); }
			 */

			//

			logger.info("so.getRoutedResources().size() after delete>>" + so.getRoutedResources().size());
			for (SOElement e : soec.getElements()) {
				logger.info("elemet of type::" + e.getTypeName());

				if (e instanceof TierRouteProviders) {
					logger.info("TRP INSTANCE");
					TierRouteProviders source = (TierRouteProviders) e;
					// check if this routed provider already in the service
					// order
					// if so do not add
					if (!currentResourcesIds.contains(source.getProviderResourceId())) {
						TierRouteProviders target = new TierRouteProviders();
						target.setSpnId(so.getSpnId());
						target.setProviderResourceId(source.getProviderResourceId());
						target.setVendorId(source.getVendorId());
						target.setPerformanceScore(source.getPerformanceScore());
						so.addTierRoutedResources(target);
					}
				} else {
					logger.info("SRP INSTANCE");
					RoutedProvider source = (RoutedProvider) e;
					SPNetHdrVO hdr = serviceOrderDao.fetchSpnInfo(so.getSpnId());
					String criteriaLevel = "";
					if (null != hdr) {
						criteriaLevel = hdr.getPerfCriteriaLevel();
					}
					// check if this routed provider already in the service
					// order
					// if so do not add
					if (!currentResourcesIds.contains(source.getProviderResourceId())) {
						RoutedProvider target = new RoutedProvider();
						target.setRoutedDate(new Date());
						target.setSpnId(so.getSpnId());
						target.setTierId(source.getTierId());
						target.setProviderResourceId(source.getProviderResourceId());
						if (null != criteriaLevel && criteriaLevel.equals("PROVIDER")) {
							target.setPerfScore(source.getPerfScore());
						} else if (null != criteriaLevel && criteriaLevel.equals("FIRM")) {
							target.setFirmPerfScore(source.getFirmPerfScore());
						}
						target.setVendorId(source.getVendorId());
						target.setPriceModel(so.getPriceModel());
						so.addRoutedResources(target);
						SOWorkflowControls soWorkflowControls = so.getSOWorkflowControls();
						soWorkflowControls.setCurrentTier(source.getTierId());
						soWorkflowControls.setNextTier(source.getNextTier());
						soWorkflowControls.setCurrentTierRoutedDate(new Date());
						so.setSOWorkflowControls(soWorkflowControls);
					}
				}
			}
		} else {
			logger.info("Inside Else");

			// SL-21455
			if (null != so.getWfStateId() && (so.getWfStateId() == OrderfulfillmentConstants.POSTED_STATUS 
						|| so.getWfStateId() == OrderfulfillmentConstants.DRAFT_STATUS)
					&& so.getBuyerId() == OrderfulfillmentConstants.RELAY_SERVICES_BUYER_ID) {

				boolean relayServicesNotifyFlag = isRelayServicesNotificationNeeded(so);
				if (relayServicesNotifyFlag) {
					logger.info("Started the execution to send the relay notification..");
					// boolean releaseByFirm = serviceOrderDao.isReleaseByFirm(so.getSoId());
					// logger.info("releaseByFirm query " + releaseByFirm);
					/* if (releaseByFirm) { */
					List<Integer> oldVendorIdList = new ArrayList<Integer>();
					List<Integer> newVendorIdList = new ArrayList<Integer>();
					oldVendorIdList = serviceOrderDao.getFirmIdList(so.getSoId());
					if (null != oldVendorIdList) {
						logger.info("oldVendorIdList size" + oldVendorIdList.size());
					}

					SOElementCollection soElements = (SOElementCollection) soe;
					for (SOElement e : soElements.getElements()) {
						RoutedProvider source = (RoutedProvider) e;
						Integer sourceVendorId = source.getVendorId();
						if (!(newVendorIdList.contains(sourceVendorId))) {
							newVendorIdList.add(sourceVendorId);
						}
					}
					boolean newVendor = false;
					logger.info("newVendorIdList size" + newVendorIdList.size());
					List<Integer> uniqueNewVendorsList = new ArrayList<Integer>();
					if (null != oldVendorIdList) {
						if (this.isPostedFromFE && newVendorIdList.isEmpty()) { 
							uniqueNewVendorsList.addAll(oldVendorIdList);
							newVendor = true;
						} else {
							for (Integer vendor : newVendorIdList) {
								if (!(oldVendorIdList.contains(vendor))) {
									newVendor = true;
									uniqueNewVendorsList.add(vendor);
								}
							}
						}
					} else if (null == oldVendorIdList) {
						logger.info("inside else if when oldVendorIdList is null");
						uniqueNewVendorsList = newVendorIdList;
						newVendor = true;
					}

					logger.info("newVendor " + newVendor);
					logger.info("uniqueNewVendorsList size" + uniqueNewVendorsList.size());

					if (newVendor) {

						String vendorDetails = serviceOrderDao.getVendorBNameList(uniqueNewVendorsList);

						Map<String, String> param = new HashMap<String, String>();

						if (StringUtils.isNotEmpty(vendorDetails)) {
							param.put("firmsdetails", vendorDetails);
						}

						boolean releaseByFirm = serviceOrderDao.isReleaseByFirm(so.getSoId());
						logger.info("releaseByFirm query " + releaseByFirm);
						if (releaseByFirm) {
							sendNotification(so, OrderfulfillmentConstants.ORDER_REPOSTED_TO_NEW_FIRM, param);
						} else {
							sendNotification(so, OrderfulfillmentConstants.ORDER_POSTED_TO_FIRM, param);
						}
					}
					logger.info("ended the execution to send the relay notification..");
					/* } */

				}

			}

			boolean relay = false;
			if (null != so.getBuyerId() && so.getBuyerId() == OrderfulfillmentConstants.RELAY_SERVICES_BUYER_ID) {
				relay = true;
			}
			if (!relay) {
				SOElementCollection soec = (SOElementCollection) soe;
				for (SOElement e : soec.getElements()) {
					RoutedProvider source = (RoutedProvider) e;
					// check if this routed provider already in the service
					// order
					// if so do not add
					if (!currentResourcesIds.contains(source.getProviderResourceId())) {
						RoutedProvider target = new RoutedProvider();
						target.setRoutedDate(new Date());
						target.setSpnId(so.getSpnId());
						target.setTierId(source.getTierId());
						target.setProviderResourceId(source.getProviderResourceId());
						target.setVendorId(source.getVendorId());
						target.setPriceModel(so.getPriceModel());
						so.addRoutedResources(target);
					}
				}
			} else {
				logger.info("before relay resetting the routed providers");

				if (!this.isPostedFromFE) {
					logger.info("Deleting exising routed providers for so id : " + so.getSoId());
					deleteRoutedProviders(so);
				}

				SOElementCollection soec = (SOElementCollection) soe;
				for (SOElement e : soec.getElements()) {
					RoutedProvider source = (RoutedProvider) e;
					RoutedProvider target = new RoutedProvider();
					target.setRoutedDate(new Date());
					target.setSpnId(so.getSpnId());
					target.setTierId(source.getTierId());
					target.setProviderResourceId(source.getProviderResourceId());
					target.setVendorId(source.getVendorId());
					target.setPriceModel(so.getPriceModel());
					so.addRoutedResources(target);
				}
				logger.info("after relay resetting the routed providers");
			}
		}
		serviceOrderDao.update(so);
	}
    
    
    @Override
    public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
    	// call super method
    	super.accessMiscParams(miscParams, id, so);
    	
    	if (miscParams.containsKey(OrderfulfillmentConstants.FE_POST_ORDER)) {
    		isPostedFromFE = true;
    	}
    	
    	logger.info("posted from frontend : " + isPostedFromFE);
    }
    
    // SL-21455 send notification
	private void sendNotification(ServiceOrder serviceOrder,String eventType, Map<String, String> requestMap) {
		
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
									request.append(OrderConstants.AND).append(keyValue.getKey()).append(OrderConstants.EQUALS)
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

	public void deleteRoutedProviders(ServiceOrder so) {
    	if(so.getRoutedResources() != null) deleteChildren(so.getRoutedResources());
	}
	
	public void deleteChildren(List<? extends SOChild> children){
		for(SOChild element : children){
			element.setServiceOrder(null);
			serviceOrderDao.delete(element);
		}
		children.clear();
	}

    private List<Long> getCurrentResourcesIds(ServiceOrder so) {
        List<Long> currentResourcesIds = new ArrayList<Long>();
        if(null != so.getRoutedResources()){
            for(RoutedProvider rp : so.getRoutedResources()){
                currentResourcesIds.add(rp.getProviderResourceId());
            }
        }else{
        	for(TierRouteProviders rp : so.getTierRoutedResources()){
                currentResourcesIds.add(rp.getProviderResourceId()*1L);
            }
        }
        return currentResourcesIds;  
    }

    protected HashMap<String,Object> getLogMap(SOElement request, Identification id, ServiceOrder target) throws ServiceOrderException {
		HashMap<String,Object> map = new HashMap<String,Object>();
        if(!((SOElementCollection)request).getElements().isEmpty()){
            map.put("PROVIDER_COUNT", ((SOElementCollection)request).getElements().size());
        }else{
            map.put("PROVIDER_COUNT", target.getRoutedResources().size());
        }
		return map;
	}

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        //validate that routedProvider id is present
        SOElementCollection soec = (SOElementCollection) soe;
        List<String> errors = soec.validate();
        if(soec.getElements().isEmpty() && soTarget.getRoutedResources().isEmpty() && soTarget.getTierRoutedResources().isEmpty() && soTarget.getPriceModel() != PriceModelType.BULLETIN){
            errors.add("No providers have been previously selected, and none have been selected with the request. Please select at least one provider to route to.");
        }
        return errors;
    }
    
    
	// SL-21455 to check whether notification is needed
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
}
