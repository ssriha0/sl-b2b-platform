package com.newco.marketplace.business.businessImpl.vibePostAPI;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Pattern;

import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.vibes.VibesResponseVO;
import com.servicelive.SimpleRestClient;

/**
 * @author 
 * Logic of the Push Notification batch will be as below.
 * Fetch records from alert_task with alert_type_id=3 and completion_indicator=1
 * Iterate the alert task list, for each alert task,
 *	  For each vendor_id(user_id) for a unique provider i.e,for each alert_task_id, call the Push Notification REST APi 
 *   After API call update the sms_alert_status table along with so id, user id, API call status code, event id in case of API success call
 *   Update the completion_indicator in alert_task, if API call is success for any one vendor id [No Retry logic]  
 * 
 * API Response Codes will be as below
 * 	202-Accepted: The request has been accepted for processing, but processing has not been completed.
 *  413: Request Entity too large will be returned if the JSON block exceeds 16k characters.
 *
 */
public class NotificationAlertProcessor {

	private AlertDao alertDao;
	private static final Logger logger = Logger.getLogger(NotificationAlertProcessor.class.getName());
	private ILookupBO lookupBO;
	//SLT-2156 
	private IAuthenticateUserBO authenticateUserBO;
	
	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	public void processAlerts() throws Exception {

		logger.info("NotificationAlertProcessor::::::::::processAlerts");
		ArrayList<AlertTask> alertTasksList = null;
		Map <String, String> notificationConstantsMap = null;
		List<Long> alertTaskIdsList = new ArrayList<Long>();
		List<PushAlertStatusVo> pushAlertStatusVOList = null;
		String querylimitString = "";
		int noOfRecords = 400;
		Integer resourceRoleLevel=null;
		
		// fetch records from alert_tasks with alert_type_id = 7 and completion_ind=1
		try {
			notificationConstantsMap = getAlertDao().getNotificationConstants();
			if(null != notificationConstantsMap){
				querylimitString = notificationConstantsMap.get(AlertConstants.NOTIFICATION_QUERY_LIMIT);
				if(StringUtils.isNotBlank(querylimitString) && StringUtils.isNumeric(querylimitString)){
					noOfRecords = Integer.parseInt(querylimitString);
				}
				alertTasksList = getAlertDao().retrieveAlertTasksForPushNotification(noOfRecords);
				logger.info("*********************NotificationAlertProcessor*************"+alertTasksList.size());
			}

		}
		catch (Exception dse) {
			logger.error("Exception occured while DB fetch in NotificationAlertProcessor.processAlerts():", dse);
			throw new Exception("Exception occured while DB fetch in NotificationAlertProcessor.processAlerts():",dse);
		}
		
	
		if(null != alertTasksList && alertTasksList.size()>0
				&& null != notificationConstantsMap 
				&& StringUtils.isNotBlank(notificationConstantsMap.get(AlertConstants.PUSH_NOTIFICATION_SERVICE_URL))
			){

			logger.info("alertTasksListSize:" + alertTasksList.size());
			
			// Create the URL for the service call.
			String url = notificationConstantsMap.get(AlertConstants.PUSH_NOTIFICATION_SERVICE_URL);

			//iterate the alertTasksList for getting each row
			for(AlertTask alertTask : alertTasksList){
				try{
					pushAlertStatusVOList = new ArrayList<PushAlertStatusVo>();
					PushNotificationRequest apiRequest = new PushNotificationRequest();

					String userId = alertTask.getAlertFrom();	
					
				    //split the template values and create a hashmap to hold the same
					Map<String, String> templateInputMap = getTemplateInputMap(alertTask.getTemplateInputValue());
					
					String message = alertDao.getTemplateData(alertTask.getTemplateId());
					boolean atLeastOneSuccess = false;

					if(null!= userId && null!= message && !StringUtils.isEmpty(alertTask.getAlertTo())) {
					
						logger.info("Sending Push notification for userid::::::::"+userId);
					
						String SOID = templateInputMap.get(AlertConstants.VIBES_SO_ID);
						String eventType = templateInputMap.get(AlertConstants.EVENT_TYPE);
						String prividerId = templateInputMap.get(AlertConstants.VENDOR_RESOURCE_ID);
						String firmId = templateInputMap.get(AlertConstants.VENDOR_ID);
						String date = getServiceDateTime(templateInputMap);	
						
						try{
							// SLT-2156
							 resourceRoleLevel = authenticateUserBO.getRoleOfResource(null,Integer.valueOf(prividerId) );
						}catch(Exception e){
							logger.error("Exception occurred while fetching the Role level of Provider : " +prividerId+ "in Notification Alert Processor." + e.getMessage());
						}
						
											
						boolean success = false; 

							try{
								
								apiRequest.setUserID(alertTask.getAlertTo());
								Map<String,String> metaData = new HashMap<String, String>();
								metaData.put(AlertConstants.VIBES_SO_ID, SOID);
								metaData.put(AlertConstants.VENDOR_RESOURCE_ID, prividerId);
								metaData.put(AlertConstants.VENDOR_ID, firmId);
								metaData.put(AlertConstants.DATE, date);
								metaData.put(AlertConstants.EVENT_TYPE, eventType);

								JSONObject json = new JSONObject();
								json.putAll(metaData);
								//apiRequest.setMetaData("{}");
								apiRequest.setMetaData(metaData);
								
								//SLT-2156: Spend Limit Not Shown for Level 1 Providers.
								if (null != resourceRoleLevel) {
									if (MPConstants.ROLE_LEVEL_ONE.equals(resourceRoleLevel))
										message = message.replace("spend limit: $$TOTAL_SPEND_LIMIT.", ".");
								}
								
								// replace placeholder with data into message
								message = replacePlaceholders(message, templateInputMap);
								apiRequest.setMessage(message);
								apiRequest.setEvent(eventType);
								apiRequest.setClientName("SERVICE_LIVE_PLATFORM");
								//call API
								VibesResponseVO response = null;
								try{
									response = createResponse(apiRequest, url, "");
								}catch(Exception ex){
									logger.error("skipping the record with alert_task_id:"+alertTask.getAlertTaskId()
											+" for user ID:"+userId+
											" due to an exception orrcured in API call:"+ex);
								}
								
								// insert into alert_status new table
								PushAlertStatusVo alertStatusVO = new PushAlertStatusVo();
								alertStatusVO.setSoId(SOID);
								alertStatusVO.setAlertTaskId(String.valueOf(alertTask.getAlertTaskId()));
					
								if(null != response && AlertConstants.PUSH_API_SUCCESS_CODE == response.getStatusCode()){
									logger.info("SUCCESS");
									alertStatusVO.setStatus(AlertConstants.VIBES_ALERT_STATUS_SUCCESS);
									atLeastOneSuccess = true;
								} else {
									logger.info("FAILURE");
									alertStatusVO.setStatus(AlertConstants.VIBES_ALERT_STATUS_FAILURE);
								}	
								//add the alertStatusVO to the list smsAlertStatusVOList for triggering batch insert to sms_alert_status
								pushAlertStatusVOList.add(alertStatusVO);
							}catch(Exception ex){
								logger.error("skipping the record with alert_task_id:"+alertTask.getAlertTaskId()+" for user ID:"+userId+
										" due to an exception:"+ex);
								ex.printStackTrace();
							}
							
						}
						//if at least one api call is success, add the alert_task_id for updating the completion indicator as 2
						if(null != pushAlertStatusVOList && !pushAlertStatusVOList.isEmpty()){
							 //batch insert to push_alert_status
							//update completion_ind for alertTasksList
							updateAlertStatusAndAlertTask(alertTask, pushAlertStatusVOList ,atLeastOneSuccess);
						}
					else{
						logger.info("skipping the record with alert_task_id:"+alertTask.getAlertTaskId()+
								" due to no phone numbers in bcc column or exception occured while splitting the phone numbers/ template input value");
					}
				}catch (Exception e) {
					logger.info("skipping the record with alert_task_id:"+alertTask.getAlertTaskId()+
							" due to an exception:"+e);
					e.printStackTrace();
				}
				
			}
		}else{
			logger.info("Program terminates due to either alertTasksList is empty or one of vibe constants is empty");
		}
	}

	private String replacePlaceholders(String message, Map<String, String> valueMap){
		String newMessage = message;
		for (Map.Entry<String, String> entry : valueMap.entrySet()) {
			newMessage = newMessage.replace("$"+entry.getKey(), entry.getValue());
		}
		return newMessage;
	}
	
	/**
	 * @param templateInputMap
	 * @return String serviceDateTime
	 * Method combines the below template_input_value parameters and forms the final date time string to be send along with the request.
	 * SERVICE_DATE1, SERVICE_DATE2, SERVICE_START_TIME, SERVICE_END_TIME, SO_SERVICE_LOC_TIMEZONE
	 * 
	 */
	private String getServiceDateTime(Map<String, String> templateInputMap) {
		StringBuffer serviceDateTime = new StringBuffer();
		if(StringUtils.isNotBlank(templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_DATE1))){
			serviceDateTime.append(templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_DATE1));
		}
		serviceDateTime.append(AlertConstants.VIBES_COMMA);
		if(StringUtils.isNotBlank(templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_START_TIME))){
			serviceDateTime.append(templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_START_TIME));
		}
		serviceDateTime.append(AlertConstants.VIBES_SPACE);
		String timeZOne = mapTimeZone(templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_ZIP),templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_DATE1),templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_START_TIME),templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_LOCN_TIMEZONE));
		if(StringUtils.isNotBlank(timeZOne)){
			serviceDateTime.append(timeZOne);
		}else{
			serviceDateTime.append(templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_LOCN_TIMEZONE));
		}
		return serviceDateTime.toString();
	}

	/**
	 * @param string
	 * @param string2
	 * @param string3
	 * @return
	 */
	private String mapTimeZone(String zipCode, String serviceDate1, String startTime, String timeZone) {
		String dlsFlag = "N";
		String newTimeZone =null;

		try {
			dlsFlag = lookupBO.getDaylightSavingsFlg(zipCode);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Exception inside details mapper: "+e.getMessage());

		}

		if ("Y".equals(dlsFlag)) {
			TimeZone tz = TimeZone.getTimeZone(timeZone);
			Timestamp timeStampDate = null;
			String reformattedStr  =null;
			try {
				if (serviceDate1 != null 
						&& (StringUtils.isNotBlank(startTime))) {
					SimpleDateFormat fromUser = new SimpleDateFormat(AlertConstants.VIBES_DATE_FORMAT_REQUIRED);
					SimpleDateFormat myFormat = new SimpleDateFormat(AlertConstants.VIBES_DATE_FORMAT_REFORMAT);
					reformattedStr = myFormat.format(fromUser.parse(serviceDate1));
					java.util.Date dt = (java.util.Date) TimeUtils
							.combineDateTimeFromFormat(
									Timestamp.valueOf(reformattedStr),
									startTime);
					timeStampDate = new Timestamp(dt.getTime());
				}
			} catch (ParseException pe) {
				pe.printStackTrace();
			}

			if (null != timeStampDate) {
				boolean isDLSActive = tz.inDaylightTime(timeStampDate);

				if (isDLSActive) {
					newTimeZone = TimeUtils.getDSTTimezone(timeZone);
				} else {
					newTimeZone = TimeUtils.getNormalTimezone(timeZone);
				}
			}
		} 
		else {
			newTimeZone =TimeUtils.getNormalTimezone(timeZone);
		}
		return newTimeZone;

	}


	/**
	 * @param templateInputValue
	 * @return Map<String, String> templateInputMap
	 * Method splits the template_input_value of alert_task entry, and makes it into a Hashmap
	 */
	public Map<String, String> getTemplateInputMap(String templateInputValue){
		Map<String, String> templateInputMap = new HashMap<String, String>();
		try{
			if(StringUtils.isNotBlank(templateInputValue)){
				StringTokenizer tokenizer = new StringTokenizer(templateInputValue, "\\|");
				while (tokenizer.hasMoreTokens()) {
					String singleToken = tokenizer.nextToken();
					int firstIndex = singleToken.indexOf("=");
					if(firstIndex != -1){
						String key = singleToken.substring(0, firstIndex);
						String value = singleToken.substring(firstIndex + 1);
						templateInputMap.put(key, value);
					}
				}
			}else{
				templateInputMap = null;
			}
		}catch (Exception ex){
			logger.error("Exception occured while splitting the templateInputValue:"+templateInputValue);
			ex.printStackTrace();
		}
		return templateInputMap;
	}
	
	/**
	 * Method returns current Server Date in the desired format
	 * @return currentDateString
	 */
	private String getCurrentDate(){
		Date currentDate = new Date();
		String dateString = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(AlertConstants.VIBES_DISPLAY_DATE_FORMAT_WITH_TIMEZONE);
			sdf.setTimeZone(TimeZone.getTimeZone(getTimezone(currentDate)));
			dateString = sdf.format(currentDate);
		}catch(Exception ex){
			logger.error("Exception occured while formatting date");
			ex.printStackTrace();
		}
		return dateString;
	}
	
	public static String getTimezone(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("z");
		String timezone = null;
		if (date != null) {
			try {
				timezone = sdf.format(date);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return timezone;
	}
	
		
	/**
	 * @param vibePostAPIRequest
	 * @param urlString
	 * @param authorizationHeader
	 * @return VibePostAPIResponse
	 * @throws Exception
	 * Method call the client to invoke Create Event API
	 */
	public VibesResponseVO createResponse(PushNotificationRequest request, String urlString, String authorizationHeader) throws Exception{
		String requestString = null;
		Map<String,String> authorizationHeaderParameterMap = null;
		VibesResponseVO response = null;
		//convert request object to String request
		requestString = formJson(request);
		logger.info("******************************************PUSH NOTIFICATION API Request:::::"+requestString);
		if(StringUtils.isBlank(requestString)){
			logger.error("Request is blank, cannot proceed with API call");
			response = null;
			throw new Exception("Request is blank, cannot proceed with API call");
		}

		try{
			SimpleRestClient simpleRestClient = new SimpleRestClient(urlString,null,null,false);
			// Calling postMethod in SimpleRestClient to call the web service.
			response = simpleRestClient.postDataJSON(requestString);
		}
		catch (Exception e) {
			logger.error("General Exception .createResponse() in" + requestString + "due to" + e.getMessage());
			response = null;
			throw new Exception("General Exception .createResponse() due to "+ e.getMessage());
		}

		if(null == response){
			logger.error("NotificationAlertProcessor:::API response is null");
		}
		logger.info("API Response:::::"+response.getResponse());
		logger.info("VibePostApiSimpleRestClient.createResponseFromVibes:End");
		return response;
	}
	
	/**
	 * @param VibePostAPIRequest request
	 * @return String reqJson
	 * @throws Exception
	 * Method converts the request object PushNotificationRequest to a request String
	 */
	private String formJson(PushNotificationRequest request) throws Exception{
		String reqJson = null;
		if(null != request){
			JSON j = JSONSerializer.toJSON(request);
			reqJson  = j.toString();
		}
		return reqJson;
	}
	
	
	public void updateAlertStatusAndAlertTask(AlertTask alertTask,List<PushAlertStatusVo> pushAlertStatusVOList,
			boolean atLeastOneSuccess){
		
		// batch insert to sms_alert_status
		List<Long> alertTaskIdsList = new ArrayList<Long>();
		try {
			getAlertDao().insertPushAlertStatus(pushAlertStatusVOList);
		} catch (DataServiceException e) {
			logger.error("batch insert to push_alert_sms_status failed due to DataServiceException in updateAlertStatus:"+e);
			e.printStackTrace();
		}
		
		// Update the alert task if at least one SMS was sent successfully
		// Else next iteration of batch will retry if all were failure
		if(atLeastOneSuccess){
			
			//update completion_ind for alertTasksList
			alertTaskIdsList.add(alertTask.getAlertTaskId());
			try {
				getAlertDao().updateAlertStatus(alertTask.getAlertTaskId());
			} catch (DataServiceException e) {
				logger.error("alert_task update completion_ind call failed due to DataServiceException in updateAlertStatus:"+e);
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

	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}

	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}		
	

}
