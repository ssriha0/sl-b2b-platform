package com.newco.marketplace.business.businessImpl.provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.StopWatch;

/**
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 00:40:26 $
 */

/*
 * Maintenance History: See bottom of file
 */
public abstract class ABaseBO {

	private String clientId;
	private DataSourceTransactionManager transactionManager;
	protected final Log logger = LogFactory.getLog(getClass());
	protected final StopWatch stopWatch = new StopWatch();

	protected void beforeMethod() {
		if (logger.isDebugEnabled()) {
			//stopWatch.reset();
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
/*
 * Maintenance History
 * $Log: ABaseBO.java,v $
 * Revision 1.5  2008/04/26 00:40:26  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.12.1  2008/04/23 11:42:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.4  2008/04/23 05:01:43  hravi
 * Reverting to build 247.
 *
 * Revision 1.3  2008/02/06 19:38:53  mhaye05
 * merged with Feb4_release branch
 *
 * Revision 1.2.10.1  2008/02/06 17:38:22  mhaye05
 * all transactions are not handled by Spring AOP.  no more beginWork!!
 *
 */