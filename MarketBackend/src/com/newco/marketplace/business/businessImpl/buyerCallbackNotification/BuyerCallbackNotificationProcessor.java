package com.newco.marketplace.business.businessImpl.buyerCallbackNotification;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.BuyerCallbackNotification;
import com.newco.marketplace.business.businessImpl.vibePostAPI.BuyerCallbackNotificationRequest;
import com.newco.marketplace.business.businessImpl.vibePostAPI.BuyerCallbackNotificationStatusVo;
import com.newco.marketplace.business.iBusiness.buyerCallbackEvent.IBuyerCallbackEventBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.newco.marketplace.vo.vibes.VibesResponseVO;
import com.servicelive.SimpleRestClient;

public class BuyerCallbackNotificationProcessor {

	private AlertDao alertDao;
	private static final Logger logger = Logger
			.getLogger(BuyerCallbackNotificationProcessor.class.getName());
	private ILookupBO lookupBO;
	//SLT-3836
	private IBuyerCallbackEventBO buyerCallbackEventBO;
	Date date = new Date();
	

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	public void processAlerts() throws Exception {

		logger.info("BuyerCallbackNotificationProcessor::::::::::processAlerts");
		ArrayList<BuyerCallbackNotification> notificationList = null;
		Map<String, String> notificationConstantsMap = null;
		List<BuyerCallbackNotificationStatusVo> notificationStatusVOList = null;
		String querylimitString = "";
		int noOfRecords = 400;
		Map<String,BuyerDetailsEventCallbackVO> buyerCallbackAPIDetailsMap = null;
		// fetch records from alert_tasks with alert_type_id = 7 and
		// completion_ind=1
		//SLT-3836
		String gateWayUrl = null;
		String gateWaytoken = null;
		
		try {
			notificationConstantsMap = getAlertDao()
					.getBuyerCallbackNotificationConstants();
			if (null != notificationConstantsMap) {
				querylimitString = notificationConstantsMap
						.get(AlertConstants.CALLBACK_NITIFICATION_QUERY_LIMIT);
				if (StringUtils.isNotBlank(querylimitString)
						&& StringUtils.isNumeric(querylimitString)) {
					noOfRecords = Integer.parseInt(querylimitString);
				}
				// Create the URL for the service call.
				gateWayUrl = notificationConstantsMap
						.get(AlertConstants.CALLACK_SERVICE_URL);
				gateWaytoken = notificationConstantsMap
						.get(AlertConstants.SL_WRAPPER_API_TOKEN);
			}
			//SLT-3836
			notificationList = getAlertDao()
					.retrieveBuyerCallbackNotification(noOfRecords);
			logger.info("*********************BuyerCallbackNotificationProcessor*************"
					+ notificationList.size());
			
			//SLT-3836 Fetching callback service details from buyer_callback_service
			logger.info("Fetching callback service details");
			 List<BuyerDetailsEventCallbackVO> buyerDetailsEventCallbackVOList = buyerCallbackEventBO.getBuyerCallbackAPIDetailsList();
			 if (buyerDetailsEventCallbackVOList != null && buyerDetailsEventCallbackVOList.size() > 0) {
				 buyerCallbackAPIDetailsMap = new HashMap<String,BuyerDetailsEventCallbackVO> ();
		            for (BuyerDetailsEventCallbackVO buyerDetailsEventCallbackVO : buyerDetailsEventCallbackVOList) {
		            	if(buyerDetailsEventCallbackVO.getBuyerId() != null){
		            		buyerCallbackAPIDetailsMap.put(buyerDetailsEventCallbackVO.getBuyerId().toString(), buyerDetailsEventCallbackVO);
		            	}
		            }
		      }
			
		} catch (Exception dse) {
			logger.error(
					"Exception occured while DB fetch in NotificationAlertProcessor.processAlerts():",
					dse);
			throw new Exception(
					"Exception occured while DB fetch in NotificationAlertProcessor.processAlerts():",
					dse);
		}
		
		//SLT-3836 If callback_base is PLATFORM, call the callback service in buyer_callback_service table directly
		//If callback_base is PLATFORM, call wrapper service specified in application_properties table
		if (null != notificationList && notificationList.size() > 0) {

			logger.info("notificationList:" + notificationList.size());
			notificationStatusVOList = new ArrayList<BuyerCallbackNotificationStatusVo>();

			for (BuyerCallbackNotification buyerCallbackNotification : notificationList) {

				String buyerId = buyerCallbackNotification.getBuyerId();
				BuyerCallbackNotificationStatusVo notificationStatusVO = null;

				if (buyerCallbackAPIDetailsMap != null && buyerCallbackAPIDetailsMap.get(buyerId) != null) {

					BuyerDetailsEventCallbackVO buyerDetailsEventCallbackVO = buyerCallbackAPIDetailsMap.get(buyerId);

					if (AlertConstants.CALLBACK_BASE_PLATFORM.equals(buyerDetailsEventCallbackVO.getCallbackBase())){
						
						logger.debug("Callback base is PLATFORM");
						notificationStatusVO = invokeBuyerCallbackService(buyerDetailsEventCallbackVO.getUrl(),
								buyerDetailsEventCallbackVO.getApiKey(), buyerCallbackNotification,
								AlertConstants.CALLBACK_NOTIFICATION_PLATFORM_SUCCESS,
								AlertConstants.CALLBACK_NOTIFICATION_PLATFORM_FAILURE);
					}
					else{
						logger.debug("Callback base is GATEWAY");
						notificationStatusVO = invokeBuyerCallbackService(gateWayUrl, gateWaytoken,
								buyerCallbackNotification, AlertConstants.CALLBACK_NOTIFICATION_STATUS_SUCCESS,
								AlertConstants.CALLBACK_NOTIFICATION_STATUS_FAILURE);
					}
					
					if(null != notificationStatusVO){
						notificationStatusVOList.add(notificationStatusVO);
					}
				}else{
					logger.error(
							"Exception occured while DB fetch in NotificationAlertProcessor.processAlerts():There is no entry in buyer_callback_service table");
				}
				
			}
			// if at least one api call is success, add the alert_task_id for
						// updating the completion indicator as 2
			if (null != notificationStatusVOList
						&& !notificationStatusVOList.isEmpty()) {
				// batch insert to push_alert_status
				// update completion_ind for alertTasksList
				updateNotificationTask(notificationStatusVOList);
			}
		}
				
	}
	
	private BuyerCallbackNotificationStatusVo invokeBuyerCallbackService(String url, String token, BuyerCallbackNotification buyerCallbackNotification,
			String successStatus, String errorStatus) {

		try {
			
			String buyerId = buyerCallbackNotification.getBuyerId();
			String event = buyerCallbackNotification.getEvent();
			long notificationId = buyerCallbackNotification
					.getNotificationId();

			String mappedUrl = url.replace("{buyerId}", buyerId)
					.replace("{event}", event)
					.replace("{notificationId}", String.valueOf(notificationId));
			try {
				VibesResponseVO response = null;
				try {
					response = createResponse(
							buyerCallbackNotification.getData() == null ? ""
									: buyerCallbackNotification
											.getData(),
							mappedUrl, token);
				} catch (Exception ex) {
					logger.error("retry the record with notification_id:"
							+ buyerCallbackNotification
									.getNotificationId()
							+ " for buyer ID:"
							+ buyerCallbackNotification.getBuyerId()
							+ " due to an exception orrcured in API call:"
							+ ex);
					response=new VibesResponseVO();
					response.setStatusCode(AlertConstants.CALLBACK_GATEWAY_TIMEOUT_CODE);
					response.setError(ex.getMessage());
				}

				// update notification status to table
				BuyerCallbackNotificationStatusVo notificationStatusVO = new BuyerCallbackNotificationStatusVo();
				notificationStatusVO
						.setNotificationId(buyerCallbackNotification
								.getNotificationId());
				notificationStatusVO.setNoOfRetries(buyerCallbackNotification.getRetries() + 1);

				
				if (null != response) {
					if (response.getStatusCode() == AlertConstants.CALLBACK_SUCCESS_CODE) {
						
						logger.debug("SUCCESS");
						notificationStatusVO.setStatus(successStatus);
						if (null !=response.getResponse() && !response.getResponse().isEmpty()){
							notificationStatusVO.setResponse(response.getResponse());	
						}
						notificationStatusVO.setException(null);
						
					} else if (response.getStatusCode() == AlertConstants.CALLBACK_GATEWAY_TIMEOUT_CODE) {
						
						logger.error("FAILED TO CONNECT to Callback API");
						
						if (AlertConstants.CALLBACK_NOTIFICATION_PLATFORM_FAILURE.equals(errorStatus)) {
							
							logger.debug("Callback base is PLATFORM");
							if(notificationStatusVO.getNoOfRetries()==4){
								notificationStatusVO.setStatus(AlertConstants.CALLBACK_FAILURE);
							}else{
								notificationStatusVO.setStatus(AlertConstants.CALLBACK_BASE_PLATFORM_RETRY);
							}
							
						} else {
							logger.debug("Callback base is GATEWAY");
							notificationStatusVO.setStatus(AlertConstants.CALLBACK_BASE_GATEWAY_RETRY);
						}
						notificationStatusVO.setException("ErrorCode:" + AlertConstants.CALLBACK_GATEWAY_TIMEOUT_CODE
								+ " ErrorMessage:" + response.getError());
					} else if(response.getStatusCode() == AlertConstants.CALLBACK_SERVICE_TEMP_UNAWAILABLE_CODE){	
							logger.error("503 Error - FAILED TO CONNECT to Callback or some Upstream API");
							
							if(notificationStatusVO.getNoOfRetries() == 12){
								notificationStatusVO.setStatus(AlertConstants.CALLBACK_FAILURE);
							}else{
								notificationStatusVO.setStatus(AlertConstants.CALLBACK_BASE_PLATFORM_RETRY);
							}
						notificationStatusVO.setException("ErrorCode:" + AlertConstants.CALLBACK_SERVICE_TEMP_UNAWAILABLE_CODE
									+ " ErrorMessage:" + response.getError());
					} else {
						
						logger.debug("FAILURE");
						notificationStatusVO.setStatus(AlertConstants.CALLBACK_FAILURE);
						if (null !=response.getError() && !response.getError().isEmpty()) {
							notificationStatusVO.setException(response.getError());
						}
						
					}
				} else {
					logger.error("FAILURE:Callback API response is null");
					notificationStatusVO.setStatus(errorStatus);
					notificationStatusVO.setException("Callback API response is null");
				}

				try {
					getAlertDao().updateCallbackNotificationStatus(
							notificationStatusVO);
					return notificationStatusVO;
				} catch (DataServiceException e) {
					logger.error("updateNotificationTask failed for"
							+ notificationStatusVO.getNotificationId()
							+ "due to DataServiceException in updateAlertStatus:"
							+ e);
					e.printStackTrace();
				}
				
			} catch (Exception ex) {
				logger.error("skipping the record with notification_id:"
						+ buyerCallbackNotification.getNotificationId()
						+ " for buyer ID:"
						+ buyerCallbackNotification.getBuyerId()
						+ " due to an exception:" + ex);
				ex.printStackTrace();
			}

		} catch (Exception e) {
			logger.info("skipping the record with alert_task_id:"
					+ buyerCallbackNotification.getNotificationId()
					+ " due to an exception:" + e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param BuyerCallbackNotificationRequest
	 * @param urlString
	 * @param authorizationHeader
	 * @return VibePostAPIResponse
	 * @throws Exception
	 *             Method call the client to invoke Create Event API
	 */
	public VibesResponseVO createResponse(
			String data, String urlString,
			String token) throws Exception {
		VibesResponseVO response = null;
		logger.info("******************************************Buyer Callback API Request data:::::"
				+ data);
		if (StringUtils.isBlank(data)) {
			logger.error("Request data is blank");
		}

		try {
			SimpleRestClient simpleRestClient = new SimpleRestClient(urlString,
					null, null, false);
			// Calling postMethod in SimpleRestClient to call the web service.
			response = simpleRestClient.postDataCallBack(data, token);
		} catch (Exception e) {
			logger.error("General Exception .createResponse() in"
					+ data + "due to" + e.getMessage());
			throw new Exception("General Exception due to "
					+ e.getMessage());
		}

		if (null == response) {
			logger.error("BuyerCallbackNotificationProcessor:::API response is null");
		}
		logger.info("API Response:::::" + response.getResponse());
		return response;
	}

	/**
	 * @param BuyerCallbackNotificationRequest
	 *            request
	 * @return String reqJson
	 * @throws Exception
	 *             Method converts the request object
	 *             BuyerCallbackNotificationRequest to a request String
	 */
	private String formJson(BuyerCallbackNotificationRequest request)
			throws Exception {
		String reqJson = null;
		if (null != request) {
			JSON j = JSONSerializer.toJSON(request);
			reqJson = j.toString();
		}
		return reqJson;
	}

	public void updateNotificationTask(
			List<BuyerCallbackNotificationStatusVo> pushAlertStatusVOList) {

		for (BuyerCallbackNotificationStatusVo notificationStatusVo : pushAlertStatusVOList) {
			try {
				getAlertDao().updateCallbackNotificationStatus(
						notificationStatusVo);
			} catch (DataServiceException e) {
				logger.error("updateNotificationTask failed for"
						+ notificationStatusVo.getNotificationId()
						+ "due to DataServiceException in updateAlertStatus:"
						+ e);
				e.printStackTrace();
			}
		}
	}
	

	public AlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDao alertDao) {
		this.alertDao = alertDao;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}
	//SLT-3836

	public IBuyerCallbackEventBO getBuyerCallbackEventBO() {
		return buyerCallbackEventBO;
	}

	public void setBuyerCallbackEventBO(IBuyerCallbackEventBO buyerCallbackEventBO) {
		this.buyerCallbackEventBO = buyerCallbackEventBO;
	}
}
