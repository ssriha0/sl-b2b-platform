package com.newco.marketplace.utils.utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicelive.SimpleRestClient;
import com.servicelive.response.SimpleRestResponse;

public class RestClientWorkerThread implements Runnable
{
	private String restUrl;
	private HashMap<String, String> header;
	private Long alertTaskId;
	private Collection<String> uniqueEmails;
	private Map<String, String> baseParamMap;
	private boolean isAllEmailsSuccess;


	private static final Logger logger = Logger.getLogger(RestClientWorkerThread.class.getName());

	public RestClientWorkerThread(String restUrl, HashMap<String, String> header, Map<String, String> baseParamMap,Collection<String> uniqueEmails){
		super();
		this.restUrl = restUrl;
		this.header = header;
		this.uniqueEmails=uniqueEmails;
		this.baseParamMap=baseParamMap;
	}


	@Override
	public void run() {

		logger.info("RestClientWorkerThread-->run-->started --- thread name: " + Thread.currentThread().getName());
		try{

			SimpleRestClient simpleRestClient;

			simpleRestClient = new SimpleRestClient(restUrl, null, null, false);
			logger.info("RestClientWorkerThread-->run-->about to send mail for alert task id: " + alertTaskId);
			for (String destinationEmail : uniqueEmails) {
				logger.info("Alert Task ID: " + alertTaskId + "RestClientWorkerThread-->run-->sending mail to : " + destinationEmail);
				String jsonPayload=toParameterMapJson(baseParamMap,destinationEmail);
				logger.info("Alert Task ID: " + alertTaskId + "RestClientWorkerThread-->run-->Json request for template is: \n " + jsonPayload);
				logger.info("Alert Task ID: " + alertTaskId + "RestClientWorkerThread-->run-->restUrl is: \n " + restUrl);
				SimpleRestResponse response = simpleRestClient.post(jsonPayload, header);
				logger.info("Alert Task ID: " + alertTaskId + "RestClientWorkerThread-->run-->response : " + response.getResponseCode());
				if (response != null && response.getResponseCode() == 201) {
					isAllEmailsSuccess=true;
				}
				else
				{
					isAllEmailsSuccess=false;
					break;
				}
				
			}
			
			if(isAllEmailsSuccess)
			{
				logger.info("RestClientWorkerThread-->run-->Updating status of : " + alertTaskId);
				ServiceEmailUtilitySingleton.getInstance().add(alertTaskId);
			}
		
		}catch(Exception ex)
		{
			logger.info("Alert Task ID: " + alertTaskId + "RestClientWorkerThread-->run-->Sending email failed due to : " + ex.getMessage());
		}

		logger.info("RestClientWorkerThread-->run-->ended --- thread name: " + Thread.currentThread().getName());
	}


	public String toParameterMapJson(Map<String, String> PMap,String toEmails) {
		String jsonResp = null;

		try {
			ObjectMapper mapperObj = new ObjectMapper();
			Map<String, Object> basePMap = new HashMap<String, Object>();
			basePMap.put("ctx", PMap);
			basePMap.put("email", toEmails);
			jsonResp = mapperObj.writeValueAsString(basePMap);
		} catch (Exception ex) {
			logger.error("Error in converting payload : " + ex.getMessage(), ex);
		}

		return jsonResp;
	}
	
	
	public String getRestUrl() {
		return restUrl;
	}


	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	public HashMap<String, String> getHeader() {
		return header;
	}

	public void setHeader(HashMap<String, String> header) {
		this.header = header;
	}

	public Long getAlertTaskId() {
		return alertTaskId;
	}

	public void setAlertTaskId(Long alertTaskId) {
		this.alertTaskId = alertTaskId;
	}

}