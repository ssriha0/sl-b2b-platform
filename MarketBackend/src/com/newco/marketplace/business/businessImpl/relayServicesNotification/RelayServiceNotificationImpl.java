package com.newco.marketplace.business.businessImpl.relayServicesNotification;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.NotificationDao;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.servicelive.SimpleRestClient;


public class RelayServiceNotificationImpl implements IRelayServiceNotification {

	private IServiceOrderBO serviceOrderBO;
	private NotificationDao notificationDao;
	private final Logger logger = Logger.getLogger(RelayServiceNotificationImpl.class);
	private static final String CALL_BACK_URL = "CallBackURL";
	
	public void sentNotificationRelayServices(String event,String soId) throws BusinessServiceException
	{
		logger.info("Entering RelayServiceNotification.");
		SimpleRestClient client = null;
		int responseCode = 0;
		String URL =null;
		try {
				ServiceOrderCustomRefVO customRefVO=serviceOrderBO.getCustomReferenceValue(CALL_BACK_URL,soId);
			
				if(null!=customRefVO && StringUtils.isNotBlank(customRefVO.getRefValue()))
				{
					URL=customRefVO.getRefValue();
					client = new SimpleRestClient(URL,"","",false);
					logger.info("URL for Webhooks:"+URL);
					StringBuffer request = new StringBuffer();
					request.append(OrderConstants.SERVICEPROVIDER);
					request.append(OrderConstants.EQUALS);
					request.append(OrderConstants.SERVICELIVE);
					request.append(OrderConstants.AND);
					request.append(OrderConstants.EVENT);
					request.append(OrderConstants.EQUALS);
					request.append(event);
					
					logger.info("Request for Webhooks with service order id:"+soId);
					logger.info("Request for Webhooks:"+request);
					
					responseCode = client.postWebHooks(request.toString());
					//logging the request, response and soId in db
					if (null == soId){
						soId = "";
					}
					notificationDao.loggingRelayServicesNotification(soId,request.toString(),responseCode);
					
					logger.info("Response for Webhooks with service order id"+soId);
					logger.info("Response Code from Webhooks:"+responseCode);
				}
				
			} 
			catch (Exception e) {
			logger.error("Exception occurred in RelayServiceNotificationImpl.sentNotificationRelayServices() due to"+e, e);
			}

		
		logger.info("Leaving RelayServiceNotification.");
	}

	
	public void sentNotificationRelayServices(String event,String soId, Map<String, String> requestMap) throws BusinessServiceException
	{
		logger.info("Entering RelayServiceNotification.");
		SimpleRestClient client = null;
		int responseCode = 0;
		String URL =null;
		try {
				ServiceOrderCustomRefVO customRefVO=serviceOrderBO.getCustomReferenceValue(CALL_BACK_URL,soId);
			
				if(null!=customRefVO && StringUtils.isNotBlank(customRefVO.getRefValue()))
				{
					URL=customRefVO.getRefValue();
					client = new SimpleRestClient(URL,"","",false);
					logger.info("URL for Webhooks:"+URL);
					StringBuffer request = new StringBuffer();
					request.append(OrderConstants.SERVICEPROVIDER);
					request.append(OrderConstants.EQUALS);
					request.append(OrderConstants.SERVICELIVE);
					request.append(OrderConstants.AND);
					request.append(OrderConstants.EVENT);
					request.append(OrderConstants.EQUALS);
					request.append(event);
					
					if (null != requestMap && requestMap.size() > 0) {
						for (Map.Entry<String, String> keyValue : requestMap.entrySet()) {
							request.append(OrderConstants.AND).append(keyValue.getKey()).append(OrderConstants.EQUALS).append(keyValue.getValue());
						}
					}
					
					logger.info("Request for Webhooks with service order id:"+soId);
					logger.info("Request for Webhooks:"+request);
					
					responseCode = client.postWebHooks(request.toString());
					//logging the request, response and soId in db
					if (null == soId){
						soId = "";
					}
					notificationDao.loggingRelayServicesNotification(soId,request.toString(),responseCode);
					
					logger.info("Response for Webhooks with service order id"+soId);
					logger.info("Response Code from Webhooks:"+responseCode);
				}
				
			} 
			catch (Exception e) {
			logger.error("Exception occurred in RelayServiceNotificationImpl.sentNotificationRelayServices() due to"+e, e);
			}

		
		logger.info("Leaving RelayServiceNotification.");
	}
	
	
	public boolean isRelayServicesNotificationNeeded(Integer buyerId,String currentServiceOrderId) throws BusinessServiceException {
		boolean isEligible=false;
		String relayServicesNotifyFlag="";
		if(MPConstants.RELAY_SERVICES_BUYER_ID.equals(buyerId) || MPConstants.TECHTALK_SERVICES_BUYER_ID.equals(buyerId)){
		    try {
		    	relayServicesNotifyFlag = notificationDao.getOutBoundFlag(MPConstants.RELAY_SERVICES_NOTIFY_FLAG);
		     }catch (DataServiceException de) {
		    	 logger.error("Exception in getting logger value "+ de.getMessage(), de);
			    throw new BusinessServiceException("Exception in getting logger value "+ de.getMessage(), de);
		      }
		      if(MPConstants.RELAY_SERVICES_BUYER_ID.equals(buyerId) && InHomeNPSConstants.OUTBOUND_FLAG_ON.equals(relayServicesNotifyFlag)){
		    	  isEligible=true;
		      }
		}
		return isEligible;
	}
	
	// SL-21469
	public Integer getBuyerId(String soId) throws BusinessServiceException{
	
		Integer buyerId=null;

		    try {
		    	buyerId = notificationDao.getBuyerId(soId);
		     }catch (DataServiceException de) {
		    	 logger.error("Exception in getting buyerId value "+ de.getMessage(), de);
			    throw new BusinessServiceException("Exception in getting buyerId value "+ de.getMessage(), de);
		      }
		
		return buyerId;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}


	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}
	
}