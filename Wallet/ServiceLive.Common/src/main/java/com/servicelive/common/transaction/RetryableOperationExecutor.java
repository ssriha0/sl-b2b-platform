package com.servicelive.common.transaction;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.dao.DeadlockLoserDataAccessException;

//@Aspect
public class RetryableOperationExecutor implements Ordered, MethodInterceptor {
	private static final Logger logger = Logger.getLogger(RetryableOperationExecutor.class);
	private static final int DEFAULT_MAX_RETRY_ATTEMPTS = 5;
	
	private int maximumRetryAttempts = DEFAULT_MAX_RETRY_ATTEMPTS;
	private int order;
	
	// @Around("com.servicelive.common.transaction.TransactionalNature.retryableTransactionalOperation()")
	// This method is currently not being used
	// See configuration in retryableOperationContext.xml
	public Object executeRetryableTransactionalOperation(ProceedingJoinPoint proceedingJoinPoint) 
	throws Throwable {
		final String methodName = "executeRetryableTransactionalOperation";
		logger.info(String.format("Entered %s", methodName));
		
		int numberOfRetries = 0;
		boolean continueRetrying = false;
		Throwable error;
		do {
			try {
				logger.debug(String.format("invoking target method. previously attempted %d times", numberOfRetries));
				Object result = proceedingJoinPoint.proceed();
				logger.debug(String.format("returned normally from target method."));
				
				logger.info(String.format("Exiting %s", methodName));
				return result;
			} catch (Throwable t) {
				logger.debug("returned from target method with an exception: ", t);
				error = t;
				numberOfRetries++;
				continueRetrying = 
					shouldOperationBeRetried_(proceedingJoinPoint, numberOfRetries, t);
				logger.debug(String.format("Retry transaction after %d attempts: %s", numberOfRetries, continueRetrying));
			}
		} while (continueRetrying);

		throw error;
	}

	// @Around("com.servicelive.common.transaction.TransactionalNature.retryableOperation()")
	// This method is currently not being used
	// See configuration in retryableOperationContext.xml and class TransactionalNature
	public Object executeRetryableOperation(ProceedingJoinPoint pjp)
			throws Throwable {
		
		final String methodName = "executeRetryableOperation";
		logger.info(String.format("Entered %s", methodName));

		Object result = pjp.proceed();
		
		logger.info(String.format("Exiting %s", methodName));
		return result;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		final String methodName = "executeRetryableTransactionalOperation";
		logger.info(String.format("Entered %s", methodName));
		
		int numberOfRetries = 0;
		boolean continueRetrying = false;
		Throwable error;
		do {
			try {
				logger.debug(String.format("invoking target method. previously attempted %d times", numberOfRetries));
				Object result = invocation.proceed();
				logger.debug(String.format("returned normally from target method."));
				
				logger.info(String.format("Exiting %s", methodName));
				return result;
			} catch (Throwable t) {
				logger.debug("returned from target method with an exception: ", t);
				error = t;
				numberOfRetries++;
				continueRetrying = 
					shouldOperationBeRetried(invocation.getMethod(), numberOfRetries, t);
				logger.debug(String.format("Retry transaction after %d attempts: %s", numberOfRetries, continueRetrying));
			}
		} while (continueRetrying);

		throw error;
	}

	@SuppressWarnings("unused")
	private boolean shouldOperationBeRetried(
			ProceedingJoinPoint proceedingJoinPoint, int numberOfRetries, Throwable error) {
		if (error instanceof DeadlockLoserDataAccessException) {
			
			if (numberOfRetries < this.maximumRetryAttempts) {
				return true;
			}
			else {
				throw new RetryableOperationException(String.format("Unable to complete operation in maximum allowed retries (%d).", this.maximumRetryAttempts), error);
			}
		}
		return false;
	}
	
		private boolean shouldOperationBeRetried_(
			ProceedingJoinPoint proceedingJoinPoint, int numberOfRetries, Throwable error)
			throws NoSuchMethodException {

		boolean continueRetrying = false;
		Signature proceedingJoinPointSignature = proceedingJoinPoint
				.getSignature();
		if (proceedingJoinPointSignature instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) proceedingJoinPointSignature;
			Method method = methodSignature.getMethod();
			Method targetMethod = proceedingJoinPoint.getTarget().getClass()
					.getMethod(method.getName(), method.getParameterTypes());
			continueRetrying = shouldOperationBeRetried(targetMethod,
					numberOfRetries, error);
		}
		return continueRetrying;
	}

		private boolean shouldOperationBeRetried(Method targetMethod,
				int numberOfRetries, Throwable error) {
			boolean continueRetrying = false;
			Retryable retryableSettings = targetMethod
					.getAnnotation(Retryable.class);
			if (retryableSettings != null) {
				boolean errorIsRetryable = this.isErrorRetryable(error,
						retryableSettings);

				if (errorIsRetryable) {
					if (retryableSettings.unlimitedRetriesAllowed() || numberOfRetries < retryableSettings
							.maximumRetryAttempts()) {
						continueRetrying = true;
					}
					else {
						throw new RetryableOperationException(String.format("Unable to complete operation in maximum allowed retries (%d).", this.maximumRetryAttempts), error);
					}
				}
			}
			return continueRetrying;
		}

	private boolean isErrorRetryable(Throwable error,
			Retryable retryableSettings) {
		boolean errorIsRetryable = false;
		for (Class<? extends Throwable> clazz : retryableSettings.retryExceptions()) {
			if (clazz.isAssignableFrom(error.getClass())) {
				errorIsRetryable = true;
				break;
			}
		}
		return errorIsRetryable;
	}

	public void setMaximumRetryAttempts(int maximumRetryAttempts) {
		this.maximumRetryAttempts = maximumRetryAttempts;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
