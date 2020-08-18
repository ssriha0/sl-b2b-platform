package com.servicelive.common.transaction;


//@Aspect
public class TransactionalNature {
	//@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
	// This method is currently not being used
	// See configuration in retryableOperationContext.xml and class RetryableOperationExecutor
	public void transactionalOperation() {}
	
	//@Pointcut("@annotation(com.servicelive.common.transaction.Retryable)")
	// This method is currently not being used
	// See configuration in retryableOperationContext.xml and class RetryableOperationExecutor
	public void retryableOperation() {}
	
	//@Pointcut("transactionalOperation() && retryableOperation()")
	// This method is currently not being used
	// See configuration in retryableOperationContext.xml and class RetryableOperationExecutor
	public void retryableTransactionalOperation() {}
}
