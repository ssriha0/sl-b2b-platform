package com.newco.marketplace.service.serviceImpl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.service.EmailAlertService;
import com.newco.marketplace.utils.constants.EmailAlertConstants;
import com.newco.marketplace.utils.utility.EmailAlertUtility;
import com.newco.marketplace.utils.utility.JSONUtils;
import com.newco.marketplace.utils.utility.JWTUtil;
import com.newco.marketplace.utils.utility.RestClientWorkerThread;
import com.newco.marketplace.utils.utility.ServiceEmailUtilitySingleton;

public class ServiceLiveEmailServiceImpl implements EmailAlertService {
	private static final Logger logger = Logger.getLogger(ServiceLiveEmailServiceImpl.class.getName());

	private EmailAlertBO emailAlertBO;
	private EmailAlertUtility emailAlertUtility;
	private JWTUtil jwtUtil;
	private JSONUtils jsonUtils;
	private static String CONTENT_TYPE = "application/json;charset=utf-8";
	private static String CACHE_VALUE = "no-cache";
	private static String BEARER = "Bearer ";

	private Properties configProperties;
	private String serviceLiveWebUtilImageUrl;
	private String maxThreadPoolSize;
	
	//Multi-threading changes
	private static ExecutorService executor = Executors.newFixedThreadPool(5);//Replace with maxThreadPoolSize
	//Multi-threading changes

	@Override
	public void processAlert() throws DataServiceException {
		logger.info("Entering ServiceLiveEmailServiceImpl-->processAlert()");
		ArrayList<AlertTask> alertTaskList = null;
		try {
			logger.info("Fetching AlertTask list");
			alertTaskList = (ArrayList<AlertTask>) emailAlertBO.retrieveAdobeAlertTasks(EmailAlertConstants.RTM_PRIORITY,
					EmailAlertConstants.PROV_ADOBE);
			// TODO loggers to be removed
			if (null != alertTaskList && !alertTaskList.isEmpty()) {
				logger.info("alertTaskList Size:" + alertTaskList.size());
			} else {
				logger.info("alertTaskList is empty...");
			}
			// TODO loggers to be removed

		} catch (DataServiceException dse) {
			logger.error("ServiceLiveEmailServiceImpl-->processAlert-->DataServiceException-->", dse);
		}

		try {
			String bearedToken = jwtUtil.getAccessToken();
			logger.info("ServiceLiveEmailServiceImpl-->processAlert-->bearedToken: " + bearedToken);
			String baseEmailURL = configProperties.getProperty("emailEndpoint");
			logger.info("ServiceLiveEmailServiceImpl-->processAlert-->baseEmailURL: " + baseEmailURL);
	
			int templateid = 0;
			String campaignName = null;

			long startTime = new Date().getTime();
			logger.info("ServiceLiveEmailServiceImpl-->processAlert-->startTime: " + startTime);

			//Multi-threading changes
		    ExecutorService executor = Executors.newFixedThreadPool(5);//Replace with maxThreadPoolSize
			//Multi-threading changes

			for (int i = 0; i < alertTaskList.size(); i++) {

				AlertTask alertTask = (AlertTask) alertTaskList.get(i);
				templateid = alertTask.getTemplateId();
				campaignName = alertTask.getEid();
				// TODO loggers to be removed
				logger.info("ServiceLiveEmailServiceImpl-->processAlert-->printing alertTask details");
				logger.info("ServiceLiveEmailServiceImpl-->processAlert-->alertTaskId: " + alertTask.getAlertTaskId());
				logger.info("ServiceLiveEmailServiceImpl-->processAlert-->event name: " + campaignName);
				logger.info("ServiceLiveEmailServiceImpl-->processAlert-->templateId: " + templateid);
				logger.info("ServiceLiveEmailServiceImpl-->processAlert-->alertTo: " + alertTask.getAlertTo());
				// TODO loggers to be removed
	
				// Gets the templateInputValue in Map<String, String>
				// basePMap
				List<String> emails = new ArrayList<String>();
				String listOfEmails = null;
				if (null != alertTask.getAlertTo()) {
					listOfEmails = alertTask.getAlertTo();
					emailAlertUtility.addValidEmailsToList(emails, listOfEmails);
				}
				if (null != alertTask.getAlertCc()) {
					listOfEmails = alertTask.getAlertCc();
					emailAlertUtility.addValidEmailsToList(emails, listOfEmails);
				}
				if (null != alertTask.getAlertBcc()) {
					listOfEmails = alertTask.getAlertBcc();
					emailAlertUtility.addValidEmailsToList(emails, listOfEmails);
				}
				if (null != emails && emails.size() != 0) {
					// Remove the duplicate entries from the email list.
					Collection<String> uniqueEmails = emailAlertUtility.removeDuplicateEntries(emails);

					Map<String, String> basePMap = emailAlertUtility.toJsonParameterMap(alertTask.getTemplateInputValue());
					logger.info("ServiceLiveEmailServiceImpl-->processAlert-->imageURL: " + serviceLiveWebUtilImageUrl);
					basePMap.put(EmailAlertConstants.IMAGE_URL, serviceLiveWebUtilImageUrl);
	
					if (bearedToken != null && !bearedToken.isEmpty()) {
						logger.info("ServiceLiveEmailServiceImpl-->processAlert-->completeUrl: " + baseEmailURL+ campaignName);	
						HashMap<String, String> header = getRequestHeader(bearedToken);
						//Multi-threading changes
						RestClientWorkerThread restWorker = new RestClientWorkerThread(baseEmailURL+ campaignName,header,basePMap,uniqueEmails);
						restWorker.setAlertTaskId(alertTask.getAlertTaskId());
						executor.submit(restWorker);
						logger.info("ServiceLiveEmailServiceImpl-->processAlert-->executor submitting work");

						//Multi-threading changes
					}else{
						logger.info("ServiceLiveEmailServiceImpl-->processAlert-->Bearer token Null!");
					}
				}

			}
			long endTime = new Date().getTime();
			logger.info("ServiceLiveEmailServiceImpl-->processAlert-->endTime: " + endTime);
			logger.info("ServiceLiveEmailServiceImpl-->processAlert-->totalTime: " + (endTime - startTime) + " milliseconds");
			awaitTerminationAfterShutdown(executor);
			logger.info("ServiceLiveEmailServiceImpl-->processAlert-->executor shutting down.");
			logger.info("ServiceLiveEmailServiceImpl-->processAlert-->fetch task id for update" + ServiceEmailUtilitySingleton.getInstance().getAlertTaskIdList().toString());
			emailAlertBO.updateAlertStatus(ServiceEmailUtilitySingleton.getInstance().getAlertTaskIdList());
			ServiceEmailUtilitySingleton.getInstance().clearAllAlertTaskIds();
		} catch (MalformedURLException e) {
			logger.error("ServiceLiveEmailServiceImpl--> error occurred : ", e);
		} catch (Exception e) {
			logger.error("ServiceLiveEmailServiceImpl-->processAlert-->EXCEPTION-->", e);
		}

	}

	private HashMap<String, String> getRequestHeader(String bearedToken) {
		HashMap<String, String> header = new HashMap<>();
		header.put("x-api-key", configProperties.getProperty("apiKey"));
		header.put("Authorization", BEARER + bearedToken);
		header.put("Cache-Control", CACHE_VALUE);
		header.put("Content-Type", CONTENT_TYPE);
		return header;
	}

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}

	public EmailAlertUtility getEmailAlertUtility() {
		return emailAlertUtility;
	}

	public void setEmailAlertUtility(EmailAlertUtility emailAlertUtility) {
		this.emailAlertUtility = emailAlertUtility;
	}

	public JSONUtils getJsonUtils() {
		return jsonUtils;
	}

	public void setJsonUtils(JSONUtils jsonUtils) {
		this.jsonUtils = jsonUtils;
	}

	public JWTUtil getJwtUtil() {
		return jwtUtil;
	}

	public void setJwtUtil(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	public void setConfigProperties(Properties configProperties) {
		this.configProperties = configProperties;
	}

	public Properties getConfigProperties() {
		return configProperties;
	}

	public String getServiceLiveWebUtilImageUrl() {
		return serviceLiveWebUtilImageUrl;
	}

	public void setServiceLiveWebUtilImageUrl(String serviceLiveWebUtilImageUrl) {
		this.serviceLiveWebUtilImageUrl = serviceLiveWebUtilImageUrl;
	}
	
	public String getMaxThreadPoolSize() {
		return maxThreadPoolSize;
	}

	public void setMaxThreadPoolSize(String maxThreadPoolSize) {
		this.maxThreadPoolSize = maxThreadPoolSize;
	}


	public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
		threadPool.shutdown();
		try {
			if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
				threadPool.shutdownNow();
			}
		} catch (InterruptedException ex) {
			threadPool.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

}
