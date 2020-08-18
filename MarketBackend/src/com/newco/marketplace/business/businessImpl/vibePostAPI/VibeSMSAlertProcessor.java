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
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.newco.marketplace.utils.TimeUtils;

import com.newco.marketplace.vo.vibes.VibesResponseVO;
import com.servicelive.SimpleRestClient;

/** * @author Karthik_Hariharan01
 * SL-18979 Send SMS SO Post Batch processor
 * Logic of the batch will be as below.
 * Fetch records from alert_task with alert_type_id=2 and completion_indicator=1
 * Iterate the alert task list, for each alert task,
 *	 Get the phone numbers associated with the alert_task_id from bcc column, split the same with semi colon
 *   For each phone number for each alert_task_id, call the SMS API of AWS (Earlier we used to call Create Event APi of the Vibe)
 *   After API call update the sms_alert_status table along with so id, phone number, API call status code, event id in case of API success call
 * Update the completion_indicator in alert_task, if API call is success for any one phone number [No Retry logic]  
 * 
 * API Response Codes will be as below
 *  Amazon SNS returns 200 as success code
 * 	202-Accepted: The request has been accepted for processing, but processing has not been completed.
 *  413: Request Entity too large will be returned if the JSON block exceeds 16k characters.
 *
 */
public class VibeSMSAlertProcessor {

	private AlertDao alertDao;
	private static final Logger logger = Logger.getLogger(VibeSMSAlertProcessor.class.getName());
	private ILookupBO lookupBO;

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	public void processAlerts() throws Exception {

		ArrayList<AlertTask> alertTasksList = null;
		Map <String, String> vibeConstantsMap = null;
		List<Long> alertTaskIdsList = new ArrayList<Long>();
		List<VibeAlertStatusVO> smsAlertStatusVOList = null;
		String querylimitString = "";
		int noOfRecords = 400; // default the limit to 400
		
		// fetch records from alert_tasks with alert_type_id = 2 and completion_ind=1
		try {
			vibeConstantsMap = getAlertDao().getVibeConstants();
			
			if(null != vibeConstantsMap){
				querylimitString = vibeConstantsMap.get(AlertConstants.VIBES_QUERY_LIMIT);
				if(StringUtils.isNotBlank(querylimitString) && StringUtils.isNumeric(querylimitString)){
					noOfRecords = Integer.parseInt(querylimitString);
				}
				alertTasksList = getAlertDao().retrieveAlertTasksForVibe(noOfRecords);
				logger.info("*********************VibeSMSAlertPRoessor*************"+alertTasksList.size());
			}
		} 
		catch (Exception dse) {
			logger.error("Exception occured while DB fetch in VibeSMSAlertProcessor.processAlerts():", dse);
			throw new Exception("Exception occured while DB fetch in VibeSMSAlertProcessor.processAlerts():",dse);
		}
		
		
		String vibesHeader = vibeConstantsMap.get(AlertConstants.VIBES_HEADER); // Get the vibes header			
		//String vibesEventType = vibeConstantsMap.get(AlertConstants.VIBES_EVENT_TYPE); // Get vibes event type
		String deepLinkURL = vibeConstantsMap.get(AlertConstants.VIBES_DEEP_LINK_URL); // Get deep link URL
		
		if(null != alertTasksList && alertTasksList.size()>0){
//				&& null != vibeConstantsMap 
//				&& StringUtils.isNotBlank(vibeConstantsMap.get(AlertConstants.VIBES_COMPANYID))
//				&& StringUtils.isNotBlank(vibeConstantsMap.get(AlertConstants.VIBES_CREATE_EVENT_API_URL))
//				&& StringUtils.isNotBlank(vibesEventType)
//				&& StringUtils.isNotBlank(deepLinkURL)
//				&& StringUtils.isNotBlank(vibesHeader)){

			logger.info("alertTasksListSize:"+alertTasksList.size());
			
			// Create the URL for the service call.
			 //String finalURL = getUrlString(vibeConstantsMap);
			String finalURL = AlertConstants.SMS_NOTIFICATION_SERVICE_URL;  //REST API URL for SMS service hosted on Amazon

			//iterate the alertTasksList for getting each row
			
			for(AlertTask alertTask: alertTasksList){
				smsAlertStatusVOList = new ArrayList<VibeAlertStatusVO>();
				try{
					//split the alert_bcc with delimiter ';' to get all the phone numbers to which SMS needs to be sent
					
					Set<String> phoneNumberList;
					phoneNumberList = StringUtils.isNotBlank(alertTask.getAlertBcc()) ? 
							new HashSet<String>(Arrays.asList(alertTask.getAlertBcc().split(Pattern.quote(";"))))
							: null;					
					
					//split the template values and create a hashmap to hold the same
							
					Map<String, String> templateInputMap = getTemplateInputMap(alertTask.getTemplateInputValue());
					if(null != phoneNumberList && phoneNumberList.size()>0
							&& null != templateInputMap){
						
						logger.info("SMS trigger for alert_task_id:"+alertTask.getAlertTaskId()+
								" for SOID:"+templateInputMap.get(AlertConstants.VIBES_SO_ID));

						SMSAPIRequest apiRequest = new SMSAPIRequest();
						String SOID = templateInputMap.get(AlertConstants.VIBES_SO_ID);
						String SOSERVICECITY = templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_CITY);
						String SOSPENDLIMIT = templateInputMap.get(AlertConstants.VIBES_SO_SPEND_LIMIT);
						String SOSERVICEZIP = templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_ZIP);
						String SOSERVICESTATE = templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_STATE);
						String SOROUTEDDATE = templateInputMap.get(AlertConstants.VIBES_SO_ROUTED_DATE);

						String SODATETIME = getServiceDateTime(templateInputMap);
						
						
						
						//VibePostAPIRequest apiRequest = new VibePostAPIRequest();
						// apiRequest.setEvent_type(vibesEventType);
						//String currentDate = getCurrentDate();
						//apiRequest.setEvent_date(currentDate);
						//apiRequest.setCreated_at(currentDate);
						//apiRequest.setUpdated_at(currentDate);						
						
						//EventData eventData = new EventData();
						//String SOID = templateInputMap.get(AlertConstants.VIBES_SO_ID);
						
						//eventData.setSo_id(SOID);
						//eventData.setService_city(templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_CITY)+AlertConstants.VIBES_COMMA+
						//		templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_STATE) + AlertConstants.VIBES_SPACE+ templateInputMap.get(AlertConstants.VIBES_SO_SERVICE_ZIP));
						//eventData.setService_spend_limit("$"+templateInputMap.get(AlertConstants.VIBES_SO_SPEND_LIMIT));
						//eventData.setService_datetime(getServiceDateTime(templateInputMap));
						
						//Deep link URL fetched from applications_constants is https://provider.servicelive.com
						//need to append the soId (without dashes) also with the deep link url
						//Final deep link url will be https://provider.servicelive.com?soid=Value&SID=SIDValue where Value is the so id without dashes
						//String SID = vibeConstantsMap.get(AlertConstants.VIBES_SID);
						//if (StringUtils.isNotBlank(SID)){
						//	eventData.setDeepLinkURL(deepLinkURL+"?soid="+(StringUtils.isNotBlank(SOID) ? StringUtils.remove(SOID, AlertConstants.HYPHEN) : StringUtils.EMPTY)+"&SID="+SID);
						//}
						//else{
						//	eventData.setDeepLinkURL(deepLinkURL+"?soid="+(StringUtils.isNotBlank(SOID) ? StringUtils.remove(SOID, AlertConstants.HYPHEN) : StringUtils.EMPTY));
						//}
						
						String message = "ServiceLive: SO "+SOID+" in "+SOSERVICECITY +", "+SOSERVICESTATE+" "+SOSERVICEZIP+" posted at "+SOROUTEDDATE+ " CST spend limit: "+
								SOSPENDLIMIT+". Text STOP to stop msgs";
						boolean atLeastOneSuccess = false; 
						
						//iterate phone numbers
						for(String phoneNumber: phoneNumberList){
							try{
								
								apiRequest.setPhoneNumber(AlertConstants.INDIA_CODE + phoneNumber);  //+1 = USA
								apiRequest.setMetadata("{}");
								apiRequest.setMessage(message);

								//apiRequest.setEvent_data(eventData);
								
								//call API
								VibesResponseVO response = null;
								try{
									response = createResponseFromVibes(apiRequest, finalURL, vibesHeader);
								}catch(Exception ex){
									logger.error("skipping the record with alert_task_id:"+alertTask.getAlertTaskId()
											+" for phone number:"+phoneNumber+
											" due to an exception orrcured in API call:"+ex);
								}
								
								// insert into alert_status new table
								VibeAlertStatusVO alertStatusVO = new VibeAlertStatusVO();
								alertStatusVO.setSoId(SOID);
								alertStatusVO.setPhoneNo(phoneNumber);
								//alertStatusVO.setEventType("SMS EVENT");
								logger.info("*********************response ::::statusCode::"+response+"::::::"+response.getStatusCode());
								if(null != response && AlertConstants.SMS_API_SUCCESS_CODE == response.getStatusCode()){
									logger.info("SMS SENT SUCCESSFULLY");
									alertStatusVO.setStatus(AlertConstants.VIBES_ALERT_STATUS_SUCCESS);
									//alertStatusVO.setEventId(getEventId(response.getResponse()));
									atLeastOneSuccess = true;
								}else{
									alertStatusVO.setStatus(AlertConstants.VIBES_ALERT_STATUS_FAILURE);
								}
								
								//add the alertStatusVO to the list smsAlertStatusVOList for triggering batch insert to sms_alert_status
								smsAlertStatusVOList.add(alertStatusVO);
							}catch(Exception ex){
								logger.error("skipping the record with alert_task_id:"+alertTask.getAlertTaskId()+" for phone number:"+phoneNumber+
										" due to an exception:"+ex);
								ex.printStackTrace();
							}
							
						}
						//if at least one api call is success, add the alert_task_id for updating the completion indicator as 2
						if(null != smsAlertStatusVOList && !smsAlertStatusVOList.isEmpty()){
							logger.info("UPDATING THE STATUS AFTER CALLING SMS API");
							// batch insert to sms_alert_status
							// update completion_ind for alertTasksList
							updateAlertStatusAndAlertTask(alertTask,smsAlertStatusVOList,atLeastOneSuccess);
						}
					}else{
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
	 * @param vibeConstantsMap
	 * @return String finalUrl
	 * The Create Event API URl and Company ID is stored in application_constants table as seperate entries. 
	 * Method combines both and makes the final create event API URL.
	 */
	private String getUrlString(Map<String, String> vibeConstantsMap){
		String finalUrl = "";
		String url = vibeConstantsMap.get(AlertConstants.VIBES_CREATE_EVENT_API_URL);
		String companyId=vibeConstantsMap.get(AlertConstants.VIBES_COMPANYID);
		finalUrl = url.replace(AlertConstants.VIBES_COMPANY_ID_TO_REPLACE, companyId);
		return finalUrl;

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
	public VibesResponseVO createResponseFromVibes(SMSAPIRequest vibePostAPIRequest, String urlString, String authorizationHeader) throws Exception{
		String requestString = null;
		Map<String,String> authorizationHeaderParameterMap = null;
		VibesResponseVO response = null;
		//convert request object to String request
		requestString = formJson(vibePostAPIRequest);
		logger.info("API Request:::::"+requestString);
		if(StringUtils.isBlank(requestString)){
			logger.error("Request is blank, cannot proceed with API call");
			response = null;
			throw new Exception("Request is blank, cannot proceed with API call");
		}
		//construct parameter map from header.
		authorizationHeaderParameterMap  = getHttpHeaders(authorizationHeader);
		//if(null == authorizationHeaderParameterMap){
		//	logger.error("Authorization header is incorrect, cannot proceed with API call");
		//	response = null;
		//	throw new Exception("Authorization header is incorrect, cannot proceed with API call");
		//
		// Calling postMethod 
		try{
			SimpleRestClient simpleRestClient = new SimpleRestClient(urlString,null,null,false);
			// Calling postMethod in SimpleRestClient to call the web service.
			response = simpleRestClient.postMethodVibes(requestString, authorizationHeaderParameterMap);
		}
		catch (Exception e) {
			logger.error("General Exception @VibesAPIClient.createResponseFromVibes() in" + requestString + "due to" + e.getMessage());
			response = null;
			throw new Exception("General Exception @VibesAPIClient.createResponseFromVibes() due to "+ e.getMessage());
		}
		if(null == response){
			logger.error("API response is null");
		}
		//logger.info("API Response:::::"+response.getResponse());
		logger.info("API Response:::::"+response.getStatusText());
		logger.info("VibePostApiSimpleRestClient.createResponseFromVibes:End");
		return response;
	}
	
	
	/**
	 * @param String parameters
	 * @return Map<String,String>
	 * @throws Exception
	 * Method returns a map after processing the data VIBESHeader to get the Authorization details
	 */
	private Map<String,String> getHttpHeaders(String parameters) throws Exception{
		HashMap<String,String> map = null;
		String[] splittedValue = null;
		if(StringUtils.isNotBlank(parameters)){
			map = new HashMap<String,String>();
			splittedValue= parameters.split(AlertConstants.VIBES_COMMA);
			for(int count = 0;count< splittedValue.length;count++){
				String value = splittedValue[count];
				String[] againSplitted = value.split(AlertConstants.VIBES_COLON);
				map.put(againSplitted[0], againSplitted[1]);
			}
		}
		return map;
	}

	/**
	 * @param VibePostAPIRequest request
	 * @return String reqJson
	 * @throws Exception
	 * Method converts the request object VibePostAPIRequest to a request String
	 */
	private String formJson(SMSAPIRequest request) throws Exception{
		String reqJson = null;
		if(null != request){
			JSON j = JSONSerializer.toJSON(request);
			reqJson  = j.toString();
		}
		return reqJson;
	}
	
	
	public void updateAlertStatusAndAlertTask(AlertTask alertTask,List<VibeAlertStatusVO> smsAlertStatusVOList,
			boolean atLeastOneSuccess){
		
	logger.info("VibeSMSAlertProcessor:::updateAlertStatusAndAlertTask");
		
		// batch insert to sms_alert_status
		List<Long> alertTaskIdsList = new ArrayList<Long>();
		try {
			getAlertDao().insertSMSAlertStatus(smsAlertStatusVOList);
		} catch (DataServiceException e) {
			logger.error("batch insert to sms_alert_sms_status failed due to DataServiceException in updateAlertStatus:"+e);
			e.printStackTrace();
		}
		
		// Update the alert task if at least one SMS was sent successfully
		// Else next iteration of batch will retry if all were failure
		
		if(atLeastOneSuccess){
			
			//for(int i=0;i< smsAlertStatusVOList.size();i++){
			//	VibeAlertStatusVO obj = (VibeAlertStatusVO)smsAlertStatusVOList.get(i);
			//	if((obj.getStatus()).equalsIgnoreCase(AlertConstants.VIBES_ALERT_STATUS_SUCCESS)){
				//update completion_ind for alertTasksList
			//    alertTaskIdsList.add(alertTask.getAlertTaskId());
			//	}
			//}
			try {
				getAlertDao().updateAlertStatus(alertTask.getAlertTaskId());
			} catch (DataServiceException e) {
				logger.error("alert_task update completion_ind call failed due to DataServiceException in updateAlertStatus:"+e);
				e.printStackTrace();
			}
		}
		
	}
	
    /**
     * @return eventId
     * get eventId from response and set it to response object
     */

    /*public String getEventId(String response) {
        String eventId = null;
    	if(StringUtils.isNotBlank(response)){
            try {
            	JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(response);
                eventId = jsonObject.getString("event_id");
            } catch (JSONException e) {
                eventId = null;
            }
        }
        return eventId;
    }*/
    
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
	

}
