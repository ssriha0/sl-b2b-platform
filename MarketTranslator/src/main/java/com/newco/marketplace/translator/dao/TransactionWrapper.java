package com.newco.marketplace.translator.dao;

import org.springframework.orm.jpa.JpaTransactionManager;

public class TransactionWrapper {
	
	private JpaTransactionManager txManager;

	public void startTrans() {
		// intentionally blank
	}
	
	public void endTrans() {
		// intentionally blank
	}
	
	public JpaTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(JpaTransactionManager txManager) {
		this.txManager = txManager;
	}
	
	
}
