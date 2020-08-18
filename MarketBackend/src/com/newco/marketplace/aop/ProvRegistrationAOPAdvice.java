/**
 * 
 */
package com.newco.marketplace.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.aop.AOPAdviceVO;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * @author karuppannan_p
 *
 */
public class ProvRegistrationAOPAdvice extends BaseAOPAdvice {

	private static final Logger logger = Logger.getLogger(MPAOPAdvice.class);

	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		// not implemented as of now		
	}

	@SuppressWarnings("unchecked")
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		try{
			String methodName = method.getName();
			logger.debug("ProvRegistrationAOPAdvice-->afterReturning()-->MethodName="+methodName);
			
			if(returnValue != null){
				
				if (returnValue instanceof com.newco.marketplace.webservices.base.response.ProcessResponse){ 
					if(((ProcessResponse)returnValue).isError()){
						return;
					}					
				} 
			}
			
			//Fetch the AOP Types - Alert, Logging, Caching from database using MethodName/Action
			List<AOPAdviceVO> alAOPAdviceVOs = getAOPAdvices(methodName);
			int iSize = alAOPAdviceVOs.size();
			
			Map<String, Object> hmParams = null;

			//Using Reflection, Call AOPMapper class's method to initialize the HashMap with method arguments
			AOPMapper aopParamMap = new AOPMapper(args);
			Method methodNameParam = null;
			try{
				methodNameParam = aopParamMap.getClass().getMethod(methodName);
			}
			catch(NoSuchMethodException me){
				if (iSize != 0) {
					// if AOP has been configured in the database, we should not
					// be here
					logger.error("ProvRegistrationAOPAdvice-->afterReturning()-->NoSuchMethodException-->", me);
				}
				return;
			}
			catch(ClassCastException ce){
				
				logger.error("ProvRegistrationAOPAdvice-->afterReturning()-->ClassCastException-->" +
						"The method "+methodName+" last argument is not SecurityContext ",ce);
			}
			
			hmParams = (Map<String, Object>)methodNameParam.invoke(aopParamMap);
			hmParams.put(AOPConstants.AOP_METHOD_NAME, methodName);
			//hmParams.put(AOPConstants.AOP_SECURITY_CONTEXT, securityContext);
			
			//Some methods need things mapped to the hash after the so in set. Methods needing this should
			//create a [methodName]PostSOInjection method. This prevents extra hits to the DB.
			//hmParams = executePostSOInjectionMethod(aopParamMap, methodName, hmParams);

			for(AOPAdviceVO aopAdviceVO:alAOPAdviceVOs){
				try {
					
					//Check for AOP Advice name
					//For Alert
					if(AOPConstants.AOP_ADVICE_ALERT.equals(aopAdviceVO.getAopName())){
						handleAOPAlertsForRegistration(hmParams, aopAdviceVO);
						
					}
					//Logging
					else if(AOPConstants.AOP_ADVICE_LOGGING.equals(aopAdviceVO.getAopName())){
						handleAOPLogging(aopAdviceVO, hmParams);
					}
					//Caching
					else if(AOPConstants.AOP_ADVICE_CACHING.equals(aopAdviceVO.getAopName())){
						handleAOPCaching(aopAdviceVO, hmParams);
					}
				} catch (Exception e) {
					logger.error("ProvRegistrationAOPAdvice-->afterReturning()-->EXCEPTION-->"+e.getMessage());
					continue;
				}				
			}
		} catch(Exception ex) {
			logger.error("ProvRegistrationAOPAdvice-->afterReturning()-->EXCEPTION-->"+ex.getMessage());
		}
	}
}