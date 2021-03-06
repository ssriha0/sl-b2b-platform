/**
 * 
 */
package com.newco.marketplace.webservices.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;


/**
 * @author HRAVI
 *
 */
public class WSAOPAdvice implements  AfterReturningAdvice{
	private static final Logger logger = Logger.getLogger(WSAOPAdvice.class.getName());
	private WSLoggingAdvice wsLoggingAdvice;

	
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if (logger.isInfoEnabled()) {
			String methodName = method.getName();
			logger.info("WSAOPAdvice-->afterReturning()-->MethodName="+method);
			System.out.print("\n\n ============= MSG GENERATED BY AOP FOR METHOD ==> "+methodName+" \n\n");
		}
	}


	public WSLoggingAdvice getWsLoggingAdvice() {
		return wsLoggingAdvice;
	}


	public void setWsLoggingAdvice(WSLoggingAdvice wsLoggingAdvice) {
		this.wsLoggingAdvice = wsLoggingAdvice;
	}
	
}
