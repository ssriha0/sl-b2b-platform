package com.newco.marketplace.dao;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.TransactionStatus;
/**
 * <p> Title: RFSBaseDAO </p>
 * <p> Description: Base classfor all the DAO classes.Transaction methods are defined here. 
 * @author RHarini
 * @date Aug 12, 2006
 * 
 */
public abstract class MPBaseDAO extends SqlMapClientTemplate{

	
	private DataSourceTransactionManager transactionManager;
	
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
	 * Starts the Transaction
	 * @return  TransactionStatus
	 * New if no transaction is already available. 
	 * 	Else returns the current transaction 
 	 */
		public TransactionStatus startTransaction(){
			TransactionStatus status = getTransactionManager().getTransaction(null);
			return status;
		}

	/**
		 * Commits and ends the transaction
		 * @param  status Status of the Transaction
		 */
			public void endTransaction(TransactionStatus status){
				getTransactionManager().commit(status);
			}

	/**
	 * Commits and ends the transaction
	 * @param  status Status of the Transaction
	 */
		public void commitTransaction(TransactionStatus status){
			getTransactionManager().commit(status);
		}
	
	/**
		 * Rolbacks the transaction
		 * @param  status Status of the Transaction
		 */
			public void rollbackTransaction(TransactionStatus status){
				getTransactionManager().rollback(status);
			}	
}
