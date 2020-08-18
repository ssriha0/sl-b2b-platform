package com.newco.marketplace.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.ClientConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.aop.IAOPAdviceDao;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.SimpleCache;

public abstract class BaseAOPAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

	Logger logger = Logger.getLogger(BaseAOPAdvice.class);
	
	private AlertAdvice alertAdvice;
	private LoggingAdvice loggingAdvice;
	private CachingAdvice cachingAdvice;
	private IAOPAdviceDao aopAdviceDao;
	
	/**
	 * AOPAdvice does not change in DB at run type. Its worth caching it
	 * unless we implement memcache.
	 */
	//private static Map<String, List<AOPAdviceVO> > aOPAdviceVOCache;
	private static SimpleCache aOPAdviceVOCache;
	
	public BaseAOPAdvice() {
		aOPAdviceVOCache = SimpleCache.getInstance();
	}
	
	/**
	 * Returns list of advices to be invoked for given method
	 * @param methodName
	 * @return List<AOPAdviceVO>
	 */
	protected List<AOPAdviceVO> getAOPAdvices(String method) {
		final String key = "AOPAdvices_" + method;		
		@SuppressWarnings("unchecked")
		List<AOPAdviceVO> advices = (List<AOPAdviceVO>)aOPAdviceVOCache.get(key);
		
		if (advices == null) {
			try {
				advices = aopAdviceDao.getAOPAdvices(method);
				aOPAdviceVOCache.put(key, advices);
			} catch (DataServiceException dsEx) {
				// Do nothing; no business rule; eat error; exception should have been logged at root
			}
		} else {
			if (logger.isDebugEnabled())
			  logger.debug("getAOPAdvices Cached:" + method);
		}
		//advices = (List<AOPAdviceVO>)aOPAdviceVOCache.get(key);
		return advices;
	}
	
	/**
	 * Some methods need things mapped to the hash after the so in set. Methods needing this should
	 * create a [methodName]PostSOInjection method. This prevents extra hits to the DB.
	 * 
	 * @param aopMapper
	 * @param nameOfMethodAOPed
	 * @param aopParams
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> executePostSOInjectionMethod(BaseAOPMapper aopMapper, String nameOfMethodAOPed, Map<String,Object> aopParams)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		for (int methodIndex = 0; methodIndex < aopMapper.getClass().getMethods().length; methodIndex++) {
			String methodNameTest = aopMapper.getClass().getMethods()[methodIndex].getName();
			if (methodNameTest.equals(nameOfMethodAOPed + "PostSOInjection")) {
				Method methodPostSO = aopMapper.getClass().getMethods()[methodIndex];
				aopParams = (Map<String, Object>)methodPostSO.invoke(aopMapper, new Object[]{aopParams});
				break;
			}
		}
		return aopParams;
	}
	
	/**
	 * Returns Caching advice
	 * @param cachingEventId
	 * @return AOPAdviceVO
	 */
	protected AOPAdviceVO getCachingEvent(Integer cachingEventId) {
		AOPAdviceVO cachingAdvice = null;
		try {
			cachingAdvice = aopAdviceDao.getCachingEvent(cachingEventId);
		} catch (DataServiceException dsEx) {
			// Do nothing; no business rule; eat error; exception should have been logged at root
		}
		return cachingAdvice;
	}

	/**
	 * Handles AOP Alert and add alerts to Queue
	 * @param hmParams
	 * @param aopAdviceVO
	 * @param templateId
	 */
	protected void handleAOPAlerts(Map<String, Object> hmParams, AOPAdviceVO aopAdviceVO) {
		Integer templateId = aopAdviceVO.getTemplateId();
		if (logger.isDebugEnabled())
		  logger.debug("BaseAOPAdvice-->ALERT-->TemplateId="+templateId);
		hmParams.put(AOPConstants.AOP_TEMPLATE_ID, templateId);
		hmParams.put(AOPConstants.AOP_AOP_ACTION_ID, aopAdviceVO.getAopActionId()+"");
		String aopActionId = aopAdviceVO.getAopActionId()!= null? aopAdviceVO.getAopActionId().toString() :"";
		hmParams.put(AOPConstants.AOP_AOP_ACTION_ID, aopActionId);
		IBuyerFeatureSetBO buyerFeatureSetBO = (IBuyerFeatureSetBO) MPSpringLoaderPlugIn.getCtx().getBean("buyerFeatureSetBO");
		Integer buyerID = (Integer) hmParams.get(AOPConstants.AOP_BUYER_ID);
		if (aopAdviceVO.getTemplateId() != null 
				&& (aopAdviceVO.getTemplateId().intValue() == AOPConstants.AOP_ASSURANT_FTP_TEMPLATE_ID 
						|| aopAdviceVO.getTemplateId().intValue() == AOPConstants.AOP_ASSURANT_INCIDENT_RESPONSE_FTP_TEMPLATE_ID)){
			if (buyerID != null && buyerFeatureSetBO.validateFeature(buyerID, BuyerFeatureConstants.ASSURANT_ALERTS)
					&& hmParams.get(AOPConstants.SKIP_FTP_ALERT) != null && 
					OrderConstants.FLAG_NO.equals(hmParams.get(AOPConstants.SKIP_FTP_ALERT))) {
				handleMultipleRecordsOfBuyerSubStatus(hmParams);
			}
		}
		else if (aopAdviceVO.getTemplateId() != null && aopAdviceVO.getTemplateId().intValue() == AOPConstants.AOP_HSR_FTP_TEMPLATE_ID){
			if (buyerID != null && buyerFeatureSetBO.validateFeature(buyerID, BuyerFeatureConstants.HSR_ALERTS)
					&& hmParams.get(AOPConstants.SKIP_FTP_ALERT) != null && 
					OrderConstants.FLAG_NO.equals(hmParams.get(AOPConstants.SKIP_FTP_ALERT))) {
				alertAdvice.addAlertToQueue(hmParams);
			}
		}
		else if(hmParams.get(AOPConstants.SKIP_ALERT) != null && OrderConstants.FLAG_NO.equals(hmParams.get(AOPConstants.SKIP_ALERT))){
			if (logger.isDebugEnabled())
			  logger.debug("AOPAdvice-handleAOPAlerts-TemplateId-"+templateId);
			alertAdvice.addAlertToQueue(hmParams);
		}
	}
	
	/**
	 * Handles AOP logging and inserts the log.
	 * 
	 * @param aopAdviceVO
	 * @param hmParams
	 * @throws BusinessServiceException
	 */
	protected void handleAOPLogging(AOPAdviceVO aopAdviceVO,
			Map<String, Object> hmParams) throws BusinessServiceException {
		Integer templateId = aopAdviceVO.getTemplateId();
		if (logger.isDebugEnabled())
		  logger.debug("BaseAOPAdvice-->LOGGING-->TemplateId="+templateId);
		hmParams.put(AOPConstants.AOP_TEMPLATE_ID, templateId);
		hmParams.put(AOPConstants.AOP_ACTION_ID, aopAdviceVO.getActionId());
		if(hmParams.get(AOPConstants.SKIP_LOGGING) != null && OrderConstants.FLAG_NO.equals(hmParams.get(AOPConstants.SKIP_LOGGING))){
				getLoggingAdvice().insertLog(hmParams);
		}
	}
	
	/**
	 * Handles AOP Caching
	 * 
	 * @param aopAdviceVO
	 * @param hmParams
	 * @throws BusinessServiceException
	 */
	protected void handleAOPCaching(AOPAdviceVO aopAdviceVO,
			Map<String, Object> hmParams) throws BusinessServiceException {
		Integer cachingEventId = aopAdviceVO.getCachingEventId();
		AOPAdviceVO cachingAdviceVO = getCachingEvent(cachingEventId);
		if (logger.isDebugEnabled()) {
		  logger.debug("BaseAOPAdvice-->CACHING-->CachingEventId="+cachingEventId);		
		  logger.debug("BaseAOPAdvice-->CACHING-->CachingEventClass="+cachingAdviceVO.getCachingEventClass());
		}
		
		hmParams.put(AOPConstants.AOP_CACHING_EVENT_ID, cachingEventId);
		hmParams.put(AOPConstants.AOP_CACHING_EVENT_CLASS, cachingAdviceVO.getCachingEventClass());
		getCachingAdvice().callCacheEvent(hmParams);
	}

	
	
	/**
	 * go thru list of buyerAssocInfoList and put comments , susStauts and buyerAssocSubstatusId for each buyerSubstatusInfo
	 * and send alert for each buyerSubstatusInfo
	 * @param hmParams
	 */
	private void handleMultipleRecordsOfBuyerSubStatus(Map<String, Object> hmParams) {
		String soId = (String) hmParams.get(AOPConstants.AOP_SO_ID);
		@SuppressWarnings("unchecked")
		List<BuyerSubstatusAssocVO>  buyerAssocInfoList = (List<BuyerSubstatusAssocVO>)hmParams.get(AOPConstants.AOP_BUYER_SUBSTATUS_ASSOC);
		if (buyerAssocInfoList == null || buyerAssocInfoList.isEmpty()) {
			return;
		}
		hmParams.remove(AOPConstants.AOP_BUYER_SUBSTATUS_ASSOC);
		boolean secondAlertOnwards = false;
		for(BuyerSubstatusAssocVO buyerSubstatusInfo : buyerAssocInfoList){
			if (secondAlertOnwards) {
				try {
					// Multiple Assurant's FTP alerts need to be created with 1 second gap to ensure Assurant receives these alerts in right order
					Thread.sleep(OrderConstants.ONE_SECOND);
				} catch (InterruptedException e) {
					// Do nothing for this unexpected condition; just proceed
				}
			}
			String buyerComments = buyerSubstatusInfo.getComments();
			String buyerSubstatus = buyerSubstatusInfo.getBuyerStatus();
			String buyerAssocInfoId = buyerSubstatusInfo.getBuyerSubStatusAssocId()!= null ? buyerSubstatusInfo.getBuyerSubStatusAssocId().toString() : "";
			hmParams.put(AOPConstants.AOP_ASSURANT_COMMENTS, buyerComments);
			hmParams.put(AOPConstants.AOP_BUYER_SUBSTATUS_DESC, buyerSubstatus);
			hmParams.put(OrderConstants.BUYER_SUBSTATUS_ASSOC_ID , buyerAssocInfoId);
			if (buyerSubstatus.equalsIgnoreCase(ClientConstants.CLOSED_STATUS)) {
				resetSoIdWithClosedSoId(hmParams);
			}
			
			// set file date for each alert  
			SimpleDateFormat sdfFileDate = new SimpleDateFormat("ddMMyy'_'HHmmss");//DDMMYY_HHMMSS
			hmParams.put("FILE_DATE", sdfFileDate.format(new Date()));

			RandomGUID random = new RandomGUID();
			try {
				hmParams.put("RANDOM", Long.toString(random.generateGUID()));
			} catch (Exception e) {
				logger.warn("could not generate random number for file name - using 1111");
				hmParams.put("RANDOM", "1111");
			}
			
			// multiple alerts will be added for a case like On draft Dispatch Received & Parts Pending Output file
			alertAdvice.addAlertToQueue(hmParams);
			//Reset the SO ID with the actual SO ID because it might have been set with Close SO ID in resetSoIdWithClosedSoId()
			hmParams.put(AOPConstants.AOP_SO_ID, soId);
			secondAlertOnwards = true;
		}
	}
	
	/**
	 * Handles AOP Alert and add alerts to Queue specially for buyer and provider registration
	 *
	 * @param hmParams
	 * @param aopAdviceVO
	 * @param templateId
	 */
	protected void handleAOPAlertsForRegistration(Map<String, Object> hmParams, AOPAdviceVO aopAdviceVO) {
		Integer templateId = aopAdviceVO.getTemplateId();
        if (logger.isDebugEnabled())
		  logger.debug("BaseAOPAdvice-->ALERT-->TemplateId="+templateId);
		hmParams.put(AOPConstants.AOP_TEMPLATE_ID, templateId);
		hmParams.put(AOPConstants.AOP_AOP_ACTION_ID, aopAdviceVO.getAopActionId()+ "");
		String aopActionId = aopAdviceVO.getAopActionId()!= null? aopAdviceVO.getAopActionId().toString() :"";
		hmParams.put(AOPConstants.AOP_AOP_ACTION_ID, aopActionId);
		if(hmParams.get(AOPConstants.SKIP_ALERT) != null && OrderConstants.FLAG_NO.equals(hmParams.get(AOPConstants.SKIP_ALERT))){
			alertAdvice.addAlertToQueue(hmParams);			
		}
	}
	
	/**
	 * Handles AOP Alert and add alerts to Queue specially for buyer and provider registration
	 *
	 * @param hmParams
	 * @param aopAdviceVO
	 * @param templateId
	 */
	protected void handleAOPAlertsForUSerMgmt(Map<String, Object> hmParams, AOPAdviceVO aopAdviceVO) {
		if(hmParams.get(AOPConstants.SKIP_ALERT) != null && OrderConstants.FLAG_NO.equals(hmParams.get(AOPConstants.SKIP_ALERT))){
			alertAdvice.addAlertToQueue(hmParams);			
		}
	}
	
	/**
	 * Handles AOP Alert and add alerts to Queue specially for buyer and provider registration
	 *
	 * @param hmParams
	 * @param aopAdviceVO
	 * @param templateId
	 */
	protected void handleAOPAlertsForRequest(Map<String, Object> hmParams, AOPAdviceVO aopAdviceVO) {
		handleAOPAlertsForRegistration(hmParams, aopAdviceVO);
	}

	
	/**
	 * Reset the SO ID with the closed SO ID so that in the Closed out file during SP ReOpen, it displays the SO ID
	 * for the previous SO for the same incident.
	 * 
	 * @param hmParams
	 */
	private void resetSoIdWithClosedSoId(Map<String, Object> hmParams) {
		String closedSoId = (String) hmParams.get(AOPConstants.AOP_CLOSED_SO_ID);
		if (StringUtils.isNotBlank(closedSoId)) {
			hmParams.put(AOPConstants.AOP_SO_ID, closedSoId);
		}
	}

	public CachingAdvice getCachingAdvice() {
		return cachingAdvice;
	}
	public void setCachingAdvice(CachingAdvice cachingAdvice) {
		this.cachingAdvice = cachingAdvice;
	}
	public LoggingAdvice getLoggingAdvice() {
		return loggingAdvice;
	}
	public void setLoggingAdvice(LoggingAdvice loggingAdvice) {
		this.loggingAdvice = loggingAdvice;
	}
	public IAOPAdviceDao getAopAdviceDao() {
		return aopAdviceDao;
	}
	public void setAopAdviceDao(IAOPAdviceDao aopAdviceDao) {
		this.aopAdviceDao = aopAdviceDao;
	}
	public AlertAdvice getAlertAdvice() {
		return alertAdvice;
	}
	public void setAlertAdvice(AlertAdvice alertAdvice) {
		this.alertAdvice = alertAdvice;
	}
}
