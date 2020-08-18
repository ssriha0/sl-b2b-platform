package com.newco.marketplace.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;

public class IncidentAOPAdvice extends BaseAOPAdvice {

	private static final Logger logger = Logger.getLogger(IncidentAOPAdvice.class.getName());
	private ServiceOrderDao serviceOrderDao;

	public void before(Method arg0, Object[] arg1, Object arg2) {
		// No implementation as of now
	}
	
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
		try {
			String methodName = method.getName();
			logger.debug("IncidentAOPAdvice-->afterReturning()-->MethodName="+methodName);
			
			//Using Reflection, Call AOPMapper class's method to initialize the HashMap with method arguments
			IncidentAOPMapper incidentAOPMapper = new IncidentAOPMapper(args);
			Method mapperMethod = null;
			SecurityContext securityContext = null;
			try {
				mapperMethod = incidentAOPMapper.getClass().getMethod(methodName);
				securityContext = (SecurityContext)args[args.length - 1];
			} catch(NoSuchMethodException nsmEx) {
				logger.error("IncidentAOPAdvice --> Respective Method not defined in AOP Mapper!", nsmEx);
				return;
			} catch(ClassCastException ccEx) {
				logger.error("IncidentAOPAdvice --> The method "+methodName+" last argument is not SecurityContext ", ccEx);
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> aopParams = (Map<String, Object>)mapperMethod.invoke(incidentAOPMapper);
			aopParams.put(AOPConstants.AOP_METHOD_NAME, methodName);
			aopParams.put(AOPConstants.AOP_SECURITY_CONTEXT, securityContext);
			
			//Fetch the Service Order details from database and 
			ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder((String)aopParams.get(AOPConstants.AOP_SO_ID));
			if(serviceOrder!=null){
				aopParams = incidentAOPMapper.mapServiceOrder(aopParams, serviceOrder,securityContext);
				aopParams.put(AOPConstants.AOP_SERVICE_ORDER, serviceOrder);
			}
			executePostSOInjectionMethod(incidentAOPMapper, methodName, aopParams);
			
			//Fetch the AOP Types - Alert, Logging, Caching from database using MethodName/Action
			List<AOPAdviceVO> alAOPAdviceVOs = getAOPAdvices(methodName);
			for (AOPAdviceVO aopAdviceVO : alAOPAdviceVOs) {

				if (AOPConstants.AOP_ADVICE_ALERT.equals(aopAdviceVO.getAopName())) {
					try {
						handleAOPAlerts(aopParams, aopAdviceVO);
					} catch(Exception e){
						logger.error("IncidentAOPAdvice-->afterReturning()-->EXCEPTION-->", e);
						continue;
					}
				} else if(AOPConstants.AOP_ADVICE_LOGGING.equals(aopAdviceVO.getAopName())) {
					// No implementation as of now
				} else if(AOPConstants.AOP_ADVICE_CACHING.equals(aopAdviceVO.getAopName())) {
					// No implementation as of now
				}
			}
		} catch(Exception ex) {
			// Any exception in AOP code should be logged and eaten up
			logger.error("IncidentAOPAdvice-->afterReturning()-->EXCEPTION-->", ex);
		}
	}
	
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
}
