package com.servicelive.integrationtest.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.servicelive.integrationtest.domain.Transaction;

public interface IIntegrationBO {
	public Connection getSqlConnection() throws SQLException;
	public List<Transaction> retrieveTransactions(List<String> externalOrderIds);
	public String getSoIdByExternalOrderNum(String externalOrderNum);
}
