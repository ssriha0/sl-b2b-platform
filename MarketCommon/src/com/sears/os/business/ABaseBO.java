package com.sears.os.business;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public abstract class ABaseBO {

	private String clientId;
	private DataSourceTransactionManager transactionManager;
	protected final Log logger = LogFactory.getLog(getClass());
	private TransactionStatus status;
	protected final StopWatch stopWatch = new StopWatch();

	protected void beforeMethod() {
		if (logger.isDebugEnabled()) {
			stopWatch.reset();
			stopWatch.start();
		}
	}

	protected void afterMethod(String method) {
		if (logger.isDebugEnabled()) {
			stopWatch.stop();
			StringBuffer sb = new StringBuffer();
			sb.append("BUS ID[");
			sb.append(Thread.currentThread().hashCode());
			sb.append("] METHOD[");
			sb.append(method);
			sb.append("] completed RUN TIME[");
			sb.append(stopWatch.toString());
			sb.append("]");
			logger.debug(sb.toString());
		}
	}

	/**
	 * Returns the transactionMgr.
	 * @return DataSourceTransactionManager
	 */
	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * Sets the transactionMgr.
	 * @param transactionMgr The transactionMgr to set
	 */
	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void beginWork() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		status = getTransactionManager().getTransaction(def);
	}

	public void commitWork() {
		getTransactionManager().commit(status);
	}

	public void rollbackWork() {
		getTransactionManager().rollback(status);
	}
	

	/**
	 * Returns the logger.
	 * @return Logger
	 */
	public Log getLogger() {
		return logger;
	}


	/**
	 * @return
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param string
	 */
	public void setClientId(String string) {
		clientId = string;
	}

}
