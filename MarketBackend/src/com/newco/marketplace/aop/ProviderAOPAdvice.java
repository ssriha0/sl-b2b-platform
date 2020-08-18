package com.newco.marketplace.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.interfaces.AOPConstants;

public class ProviderAOPAdvice extends BaseAOPAdvice  {
	
	private static final Logger logger = Logger.getLogger(ProviderAOPAdvice.class.getName());
	
	
	/* (non-Javadoc)
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void before(Method arg0, Object[] arg1, Object arg2) {
		// No implementation as of now
	}

	/* (non-Javadoc)
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
		try {
			String methodName = method.getName();
			if (logger.isDebugEnabled())
			  logger.debug("ProviderInsuranceAdvice-->afterReturning()-->MethodName="+methodName);
			
			//Using Reflection, Call AOPMapper class's method to initialize the HashMap with method arguments
			ProviderAOPMapper insuranceMapper = new ProviderAOPMapper(args);
			Method mapperMethod = null;
			SecurityContext securityContext = null;
			try {
				mapperMethod = insuranceMapper.getClass().getMethod(methodName);
				securityContext = (SecurityContext)args[args.length - 1];
			} catch(NoSuchMethodException nsmEx) {
				logger.error("ProviderInsuranceAdvice --> Respective Method not defined in AOP Mapper!", nsmEx);
				return;
			} catch(ClassCastException ccEx) {
				logger.error("ProviderInsuranceAdvice --> The method "+methodName+" last argument is not SecurityContext ", ccEx);
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> aopParams = (Map<String, Object>)mapperMethod.invoke(insuranceMapper);
			aopParams.put(AOPConstants.AOP_METHOD_NAME, methodName);
			aopParams = insuranceMapper.mapParams(aopParams,securityContext);
			
			
			//Fetch the AOP Types - Alert, Logging, Caching from database using MethodName/Action
			List<AOPAdviceVO> alAOPAdviceVOs = getAOPAdvices(methodName);
			for (AOPAdviceVO aopAdviceVO : alAOPAdviceVOs) {

				if (AOPConstants.AOP_ADVICE_ALERT.equals(aopAdviceVO.getAopName())) {
					try {
						handleAOPAlerts(aopParams, aopAdviceVO);
					} catch(Exception e){
						logger.error("ProviderInsuranceAdvice-->afterReturning()-->EXCEPTION-->", e);
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
			logger.error("ProviderInsuranceAdvice-->afterReturning()-->EXCEPTION-->", ex);
		}
	}
}
