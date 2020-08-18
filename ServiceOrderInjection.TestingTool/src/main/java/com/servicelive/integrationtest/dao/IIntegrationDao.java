package com.servicelive.integrationtest.dao;

import java.util.List;

import com.servicelive.integrationtest.domain.Transaction;

public interface IIntegrationDao {
	
	public List<Transaction> retrieveAllTransactions(List<String> externalOrderIds);
	public List<Transaction> retrieveSingleOrderTransactions(String externalOrderId);
	public String getSoIdByExternalOrderNum(String externalOrderNum);
	
}
