package com.newco.marketplace.aop;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertDisposition;
import com.newco.marketplace.business.businessImpl.alert.AlertDispositionProcessor;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.daoImpl.template.TemplateDaoImpl;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.webservices.base.Template;

public class AlertAdvice implements AlertConstants {

	
	private AlertDaoImpl alertDao;
	private TemplateDaoImpl templateDao;
	AlertDispositionProcessor alertDispositionProcessor;
	private IOrderGroupDao orderGroupDAO;
	private static final Logger logger = Logger.getLogger(AlertAdvice.class.getName());
	
	/**
	 * Implemented cache to reduce DB hit.
	 */
	private static Map<Integer,Template> templateCache;
	
	public AlertAdvice () {
		if (templateCache == null)
		  templateCache = new HashMap<Integer, Template>();
	}
	
	public void addAlertToQueue(Map<String, Object> alertMap){
		//alertMap has the details that AOP sends
		AlertTask alertTask = null;
		Object method= alertMap.get(AOPConstants.AOP_METHOD_NAME);
		
		if (OrderConstants.METHOD_SAVE_REGISTRATION.equals(method)
				|| OrderConstants.METHOD_SAVE_SIMPLE_BUYER_REG.equals(method)
				|| OrderConstants.METHOD_SAVE_PROF_BUYER_REG.equals(method)) {
			alertTask = getAlertTaskRegistration(alertMap);
		} else if (AOPConstants.AOP_METHOD_SEND_FORGOT_USERMAIL.equals(method)
				|| AOPConstants.AOP_METHOD_VALIDATE_ANS.equals(method)
				|| AOPConstants.AOP_METHOD_RESET_PASSWORD.equals(method)) {
			alertTask = getAlertTaskReguest(alertMap);
		}
		else if ((OrderConstants.METHOD_REROUTE_SO.equals(method) && 
					((Integer)alertMap.get(AOPConstants.AOP_TEMPLATE_ID)).intValue() == OrderConstants.BUYER_POSTING_TEMPLATE_ID)
				|| OrderConstants.METHOD_PROCESS_SUPPORT_ADD_NOTE.equals(method)
				|| (OrderConstants.METHOD_ROUTE_SO.equals(method) && 
						((Integer)alertMap.get(AOPConstants.AOP_TEMPLATE_ID)).intValue() == OrderConstants.BUYER_POSTING_TEMPLATE_ID)) {
			alertTask = getAlertTaskForSOPost(alertMap);
		} else {
			alertTask = getAlertTask(alertMap);
		}
		alertTask.setModifiedBy(ALERT_ADVICE);
		AlertDaoImpl alertDaoImpl = getAlertDao();
		// 
		if((alertTask.getAlertTypeId() == AlertConstants.TEMPLATE_TYPE_EMAIL ||
			 alertTask.getAlertTypeId() == AlertConstants.TEMPLATE_TYPE_SMS || 
			 alertTask.getAlertTypeId() == AlertConstants.TEMPLATE_TYPE_FTP)
			&&
		    (StringUtils.isNotBlank(alertTask.getAlertBcc())||
			 StringUtils.isNotBlank(alertTask.getAlertTo()) ||
			 StringUtils.isNotBlank(alertTask.getAlertCc())))
		{
			try {
				alertDaoImpl.addAlertToQueue(alertTask);
			} catch(DataServiceException dse) {
				logger.error("", dse);
			}
		} else {
			logger.error("AlertAdvice-Tried to insert an alert without a destination");
			logger.error(alertTask);
		}
	}
	
	
	private Template getTemplateDetail(Integer templateId){
		TemplateDaoImpl templateDaoImpl = getTemplateDao();
		Template template = templateCache.get(templateId);
		if (template == null) {
			try {
				template = new Template();
				template.setTemplateId(templateId);
				template = templateDaoImpl.query(template);
				templateCache.put(templateId, template);
			}
			catch(DataServiceException dse){
				logger.error("AlertAdvice-->getTemplateDetail-->DataServiceException-->", dse);
			}
		} else {
			if (logger.isDebugEnabled()) 
				logger.debug("AlertAdvice-->getTemplateDetail-->reading from cache" + templateId);			
		}
		
		return template;
	}
	
	private AlertTask getAlertTask(Map<String, Object> aopHahsMap){
		String methodName = (String)aopHahsMap.get(AOPConstants.AOP_METHOD_NAME);
		String soId = (String)aopHahsMap.get(AOPConstants.AOP_SO_ID);
		Integer templateId  = (Integer)aopHahsMap.get(AOPConstants.AOP_TEMPLATE_ID);
		Template template = getTemplateDetail(templateId);
		String groupId = (String)aopHahsMap.get(AOPConstants.AOP_SO_GROUP_ID); 
		
		if(groupId!= null && !"null".equals(groupId) && !"".equals(groupId)){
			try{
				List<ServiceOrderSearchResultsVO> serviceOrders= null;
				// after acceptance group no more exists , hence get child order using original grp Id
				if(OrderConstants.METHOD_SEND_ALL_PROVIDERS_EXCEPT_ACCEPTED_GRP.equals(methodName)){
					serviceOrders = orderGroupDAO.getServiceOrdersForOrigGroup(groupId);
				}else{
					serviceOrders = orderGroupDAO.getServiceOrdersForGroup(groupId);
				}
				
				if(serviceOrders!= null && serviceOrders.size() >0 ){
					soId = serviceOrders.get(0).getSoId();
				}
			}catch(DataServiceException e){
				logger.error("Error occurred while getting So  for GroupId -->" + groupId + "AlertAdvice.getAlertTask");
			}
		}
	
		AlertDispositionProcessor alertDispositionProcessor = getAlertDispositionProcessor();
		AlertDisposition alertDisposition = alertDispositionProcessor.getAlertDispositionDetail(methodName, soId,template, aopHahsMap);
		
		
		
		String alertFrom = alertDisposition.getAlertFrom();
		String alertTo = alertDisposition.getAlertTo();
		String alertCc = alertDisposition.getAlertCc();
		String alertBcc = alertDisposition.getAlertBcc();
		String role = alertDisposition.getRole();
		aopHahsMap.put(AOPConstants.AOP_ROLE_IND, role);
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		alertTask.setAlertBcc(alertBcc);
		alertTask.setAlertCc(alertCc);
		alertTask.setAlertFrom(alertFrom);
		alertTask.setAlertTo(alertTo);
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(aopHahsMap.toString());
		return alertTask; 	
	}
	
	private AlertTask getAlertTaskForSOPost(Map<String, Object> aopHahsMap){
		Integer templateId  = (Integer)aopHahsMap.get(AOPConstants.AOP_TEMPLATE_ID);
		Template template = getTemplateDetail(templateId);
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
		alertTask.setAlertTo((String)aopHahsMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(aopHahsMap.toString());
		return alertTask; 	
	}
	
	private AlertTask getAlertTaskReguest(Map<String, Object> aopHashMap) {
		logger.info("AlertAdvice-->getAlertTaskReguest-->saveRegistration");
		Integer templateId  = (Integer)aopHashMap.get(AOPConstants.AOP_TEMPLATE_ID);
		Template template = getTemplateDetail(templateId);
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
		alertTask.setAlertTo((String)aopHashMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(aopHashMap.toString());
		return alertTask; 	
	}
	
	private AlertTask getAlertTaskRegistration(Map<String, Object> aopHashMap) {
		logger.info("AlertAdvice-->getAlertTaskRegistration-->saveRegistration");
		Integer templateId  = (Integer)aopHashMap.get(AOPConstants.AOP_TEMPLATE_ID);
		Template template = getTemplateDetail(templateId);
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
		alertTask.setAlertTo((String)aopHashMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertCc((String)aopHashMap.get(AOPConstants.AOP_ALT_USER_EMAIL));
		if(StringUtils.isBlank(alertTask.getAlertCc())) {
			alertTask.setAlertCc("");
		}
		alertTask.setAlertBcc("");
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(aopHashMap.toString());
		return alertTask; 	
	}
	
	public AlertDaoImpl getAlertDao() {
		return alertDao;
	}
	
	public void setAlertDao(AlertDaoImpl alertDao) {
		this.alertDao = alertDao;
	}
	
	public TemplateDaoImpl getTemplateDao() {
		return templateDao;
	}
	
	public void setTemplateDao(TemplateDaoImpl templateDao) {
		this.templateDao = templateDao;
	}
	
	public AlertDispositionProcessor getAlertDispositionProcessor() {
		return alertDispositionProcessor;
	}
	
	public void setAlertDispositionProcessor(
			AlertDispositionProcessor alertDispositionProcessor) {
		this.alertDispositionProcessor = alertDispositionProcessor;
	}

	public IOrderGroupDao getOrderGroupDAO() {
		return orderGroupDAO;
	}

	public void setOrderGroupDAO(IOrderGroupDao orderGroupDAO) {
		this.orderGroupDAO = orderGroupDAO;
	}
	
}
