package com.newco.marketplace.business.businessImpl.alert;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSendException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.aop.dispatcher.Dispatcher;
import com.newco.marketplace.email.EmailService;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;

public class AlertProcessor implements AlertConstants {

	private AlertDao alertDao;
	private static final Logger logger = Logger.getLogger(AlertProcessor.class.getName());
	private EmailService emailService;
	
	
	/**
	 * @return the emailService
	 */
	public EmailService getEmailService() {
		return emailService;
	}

	/**
	 * @param emailService the emailService to set
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	public void processAlerts(String priority) {

		ArrayList<AlertTask> alertTypeList = null;
		try {
			alertTypeList = (ArrayList<AlertTask>) getAlertDao().retrieveAlertTypes();
		} 
		catch (DataServiceException dse) {
			logger.error("AlertProcessor-->processAlerts-->DataServiceException-->", dse);
		}
		
		for (int i = 0; i < alertTypeList.size(); i++) {
			AlertTask alertTypeTask = (AlertTask) alertTypeList.get(i);
			AlertQueueProcessor alertQueueProcessor = null;
			if (alertTypeTask.getAlertTypeId() == TEMPLATE_TYPE_EMAIL) {
				// email
				alertQueueProcessor = (EmailAlertQueueProcessor) AlertFactory.getAlertQueueProcessor("1");
				try {
					ArrayList<AlertTask> alertTaskList = (ArrayList<AlertTask>) getAlertDao()
							.retrieveAlertTasks(priority, new Integer(alertTypeTask.getAlertTypeId()).toString());
					sendAlerts(alertTaskList, getAlertDao(), alertQueueProcessor);
				} 
				catch (DataServiceException dse) {
					logger.error("AlertProcessor-->processAlerts-->DataServiceException-->", dse);
				}
			} 
			/*else if (alertTypeTask.getAlertTypeId() == TEMPLATE_TYPE_SMS) {
				alertQueueProcessor = (SmsAlertQueueProcessor) AlertFactory.getAlertQueueProcessor("2");
				try {
					ArrayList<AlertTask> alertTaskList = (ArrayList<AlertTask>) getAlertDao()
							.retrieveAlertTasks(priority, new Integer(alertTypeTask.getAlertTypeId()).toString());
					sendAlerts(alertTaskList, getAlertDao(), alertQueueProcessor);
				} 
				catch (DataServiceException dse) {
					logger.error("AlertProcessor-->processAlerts-->DataServiceException-->", dse);
				}
			}*/
			else if (alertTypeTask.getAlertTypeId() == TEMPLATE_TYPE_FTP) {
				try {
					ArrayList<AlertTask> alertTaskList = (ArrayList<AlertTask>) getAlertDao().retrieveAlertTasks(priority, new Integer(alertTypeTask.getAlertTypeId()).toString());
					sendFTP(alertTaskList, alertDao, alertQueueProcessor);
				} 
				catch (DataServiceException dse) {
					logger.error("AlertProcessor-->processAlerts-->DataServiceException-->", dse);
				}
			}
			else if (alertTypeTask.getAlertTypeId() == TEMPLATE_TYPE_WS) {
				try {
					ArrayList<AlertTask> alertTaskList = (ArrayList<AlertTask>) getAlertDao().retrieveAlertTasks(priority, new Integer(alertTypeTask.getAlertTypeId()).toString());
					sendWS(alertTaskList, alertDao, alertQueueProcessor);
				} 
				catch (DataServiceException dse) {
					logger.error("AlertProcessor-->processAlerts-->DataServiceException-->", dse);
				}
			}else if (alertTypeTask.getAlertTypeId() == TEMPLATE_TYPE_SMS_SUBSCRIBE) {
				alertQueueProcessor = (SmsAlertQueueProcessor) AlertFactory.getAlertQueueProcessor(AlertConstants.ALERT_TYPE_SMS_SUBSCRIBE);
				try {
					ArrayList<AlertTask> alertTaskList = (ArrayList<AlertTask>) getAlertDao().retrieveAlertTasks(priority, new Integer(alertTypeTask.getAlertTypeId()).toString());
					subscribe(alertTaskList, alertDao, alertQueueProcessor);
				} 
				catch (DataServiceException dse) {
					logger.error("AlertProcessor-->processAlerts-->DataServiceException-->", dse);
				}
			}
		}
	}
	
	private void sendFTP(ArrayList<AlertTask> alertTaskList, AlertDao alertDao, AlertQueueProcessor alertQueueProcessor) {
		List<Long> updateAlertTaskIDs = new ArrayList<Long>();
		int alertIndex = 0;
		for (AlertTask task : alertTaskList) {
			try {
				VelocityContext velocityContext = toVelocityContext(task.getTemplateInputValue());
				VelocityEngine velocityEngine = new VelocityEngine();
				StringWriter payloadWriter = new StringWriter();
				StringWriter fileNameWriter = new StringWriter();
				
				velocityEngine.evaluate(velocityContext, payloadWriter, "Velocity Template", task.getTemplateSource());
				velocityEngine.evaluate(velocityContext, fileNameWriter, "Velocity Template", task.getTemplateSubject());
				
				//use reflection to get dispatcher from alert_to class name
				Dispatcher dispatcher = getDispatcher(task.getAlertTo());
				if(null != dispatcher){
					boolean success = dispatcher.sendAlert(task, payloadWriter.toString(), fileNameWriter.toString());
					if (success) {
						updateAlertTaskIDs.add(task.getAlertTaskId());
					}
				}
				else{
					logger.info("Could not find a dispatcher for FTP alert Task payload->" + task.getPayload());
				}
				alertIndex++;
			}
			catch (Exception e) {
				logger.error("Error dispatching ftp request", e);
			}
		}
		
		int successAlertsCount = updateAlertTaskIDs.size();
		if (successAlertsCount > 0) {
			try {
				alertDao.updateAlertStatus(updateAlertTaskIDs);
			} 
			catch (DataServiceException dse) {
				logger.error("AlertProcessor-->sendAlerts-->DataServiceException-->", dse);
			}
		}
	}
	
	private void sendWS(ArrayList<AlertTask> alertTaskList, AlertDao alertDao, AlertQueueProcessor alertQueueProcessor) {
		
	}

	private void sendAlerts(ArrayList<AlertTask> alertTaskList, AlertDao alertDao, AlertQueueProcessor alertQueueProcessor) {
		List<Long> updateAlertTaskIds = new ArrayList<Long>();
		for (int j = 0; j < alertTaskList.size(); j++) {
			AlertTask alertTask = (AlertTask) alertTaskList.get(j);
			long alertTaskId = alertTask.getAlertTaskId();
			try {
				
					VelocityContext velocityContext = toVelocityContext(alertTask.getTemplateInputValue());
					VelocityEngine velocityEngine = new VelocityEngine();
					StringWriter bodyStringWriter = new StringWriter();
					StringWriter subjectStringWriter = new StringWriter();
				
					velocityEngine.evaluate(velocityContext, bodyStringWriter, "Velocity Template", alertTask.getTemplateSource());
					velocityEngine.evaluate(velocityContext, subjectStringWriter, "Velocity Template", alertTask.getTemplateSubject());
					alertQueueProcessor.sendMessage(alertTask.getAlertFrom(),
												alertTask.getAlertTo(), bodyStringWriter.toString(),
												subjectStringWriter.toString(), alertTask.getAlertCc(),
												alertTask.getAlertBcc());
					updateAlertTaskIds.add(alertTaskId);
								
			} catch (MailSendException msEx) {
				logger.error("MAIL_SEND_EXCEPTION in AlertProcessor.sendAlerts()");
			} catch (Exception e) {
				logger.error("AlertProcessor-->sendAlerts-->EXCEPTION--> [" + alertTaskId + "]", e);
			}
		}
		
		int successAlertsCount = updateAlertTaskIds.size();
		if (successAlertsCount > 0) {
			try {
				alertDao.updateAlertStatus(updateAlertTaskIds);
			} 
			catch (DataServiceException dse) {
				logger.error("AlertProcessor-->sendAlerts-->DataServiceException-->", dse);
			}
		}
	}

	/** Method to subscribe a provider for SMS service
	 * @param AlertDao alertDao
	 * @param alertTaskList
	 * @param alertQueueProcessor
	 * @return void
	 */
	private void subscribe(ArrayList<AlertTask> alertTaskList, AlertDao alertDao, AlertQueueProcessor alertQueueProcessor) {
		List<Long> updateAlertTaskIds = new ArrayList<Long>();
		boolean result=false;
		for (int alertCount = 0; alertCount < alertTaskList.size(); alertCount++) {
			AlertTask alertTask = (AlertTask) alertTaskList.get(alertCount);
			long alertTaskId = alertTask.getAlertTaskId();
			try {
					VelocityContext velocityContext = toVelocityContext(alertTask.getTemplateInputValue());
					VelocityEngine velocityEngine = new VelocityEngine();
					StringWriter bodyStringWriter = new StringWriter();
					StringWriter subjectStringWriter = new StringWriter();
				
					velocityEngine.evaluate(velocityContext, bodyStringWriter, AlertConstants.VELOCITY_TEMPLATE, alertTask.getTemplateSource());
					velocityEngine.evaluate(velocityContext, subjectStringWriter, AlertConstants.VELOCITY_TEMPLATE, alertTask.getTemplateSubject());
					result=alertQueueProcessor.subscribe(alertTask.getAlertTo(), bodyStringWriter.toString());
					if(result==true){
						updateAlertTaskIds.add(alertTaskId);
					}
								
			} catch (MailSendException msEx) {
				logger.error("MAIL_SEND_EXCEPTION in AlertProcessor.subscribe()");
			} catch (Exception e) {
				logger.error("AlertProcessor-->subscribe-->EXCEPTION-->", e);
			}
		}
		
		int successAlertsCount = updateAlertTaskIds.size();
		if (successAlertsCount > 0) {
			try {
				alertDao.updateAlertStatus(updateAlertTaskIds);
			} 
			catch (DataServiceException dse) {
				logger.error("AlertProcessor-->subscribe-->DataServiceException-->", dse);
			}
		}
	}

	/**
	 * This method will remove the duplicate e-mail addresses from the emails list.
	 * @param emails
	 * @return
	 */
	private Collection<String> removeDuplicateEntries(List<String> emails) {
		
		Set<String> tokens = new HashSet<String>();
		tokens.addAll(emails);
		return tokens;
	}

	private void addemailsToList(List<String> emails, String listOfEmails) {
		
		if(listOfEmails == null){
			return;
		}
		
		StringTokenizer  toTokens = new StringTokenizer(listOfEmails,";");
		while (toTokens.hasMoreElements()) {
			String toEmail = toTokens.nextToken();
			if(!((toEmail == null)|| (org.apache.commons.lang.StringUtils.isEmpty(toEmail)) || (!toEmail.contains("@")))){
				emails.add(toEmail);
			}	
		}
	}

	
	private Map<String, String> toParameterMap(String keyValue) throws Exception{
		Map<String, String> parameterMap = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(keyValue, "|");
		while (st.hasMoreTokens()) {
			String myKeyValue = st.nextToken();
			if(myKeyValue.startsWith(AOPConstants.AOP_REROUTE_SO_PROVIDER_PROMO_CONTENT) || myKeyValue.startsWith(AOPConstants.AOP_ROUTE_SO_PROVIDER_PROMO_CONTENT)){
				int firstIndex = myKeyValue.indexOf("=");
				String vContextKey = myKeyValue.substring(0, firstIndex) ;
				String vContextKeyValue = myKeyValue.substring(firstIndex+1);
				parameterMap.put(vContextKey, vContextKeyValue);
			}else{
				
				StringTokenizer myKeyValueToken = new StringTokenizer(myKeyValue,"=");
				parameterMap.put(myKeyValueToken.nextToken(), myKeyValueToken.hasMoreTokens()?myKeyValueToken.nextToken():"");
			}
			
		}

		return parameterMap;
	}
	
	
	public VelocityContext toVelocityContext(String keyValue) throws Exception {
		VelocityContext vContext = new VelocityContext();
		// UnusedList<ChildServiceOrderVO> childList = new ArrayList<ChildServiceOrderVO>();
		
		StringTokenizer st = new StringTokenizer(keyValue, "|");
		while (st.hasMoreTokens()) {
			String myKeyValue = st.nextToken();
			
				int firstIndex = myKeyValue.indexOf("=");
			if(firstIndex != -1){
				String vContextKey = myKeyValue.substring(0, firstIndex) ;
				String vContextKeyValue = myKeyValue.substring(firstIndex+1);
				vContext.put(vContextKey, vContextKeyValue);
			}
		}
		
/*		// if grouped Order we need List of child Orders -- String won't work in this case
		if( (vContext.get(AOPConstants.AOP_SO_GROUP_ID)!= null) && (!"".equals(vContext.get(AOPConstants.AOP_SO_GROUP_ID).toString())
				&& (!"null".equals(vContext.get(AOPConstants.AOP_SO_GROUP_ID).toString()) ) ) ){
			String groupId = (String)vContext.get(AOPConstants.AOP_SO_GROUP_ID);
			Object[] args = null;
			AOPMapper aopMapper = new AOPMapper(args);
			childList = aopMapper.getListOfChildSO(groupId);
			vContext.put(AOPConstants.AOP_SO_GROUP_CHILDREN, childList);
		}*/

		return vContext;
	}
	
	private Dispatcher getDispatcher(String beanName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(StringUtils.isNotBlank(beanName)){
			Dispatcher dispactcher = (Dispatcher) MPSpringLoaderPlugIn.getCtx().getBean(beanName);
			return dispactcher;
		}
		else
		{
			return null;
		}
	}

	public static void main(String h[]) {

		AlertDao alertDao = (AlertDao)MPSpringLoaderPlugIn.getCtx().getBean("alertDao");
		AlertProcessor ap = new AlertProcessor();
		ap.setAlertDao(alertDao);
		ap.processAlerts("1");
	}

	public AlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDao alertDao) {
		this.alertDao = alertDao;
	}

}
