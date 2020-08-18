package com.servicelive.integrationtest.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.servicelive.integrationtest.domain.Transaction;

import com.servicelive.integrationtest.dao.IIntegrationDao;

public class IntegrationBO implements IIntegrationBO {
	
	private IIntegrationDao integrationDao;
	private DataSource dataSource;

	
	public List<Transaction> retrieveTransactions(List<String> externalOrderIds) {
		// Calling retrieveSingleOrderTransactions() searches one order at a time, which
		// is useful when test mode is on and the external order numbers have been appended.
		// retrieveTransactions() only works when the external order numbers are unmodified.
		List<Transaction> transactions = new ArrayList<Transaction>();
		for (String orderId : externalOrderIds) {
			transactions.addAll(integrationDao.retrieveSingleOrderTransactions(orderId));
		}
		return transactions;
		
		//return integrationDao.retrieveAllTransactions(externalOrderIds);
	}

	public String getSoIdByExternalOrderNum(String externalOrderNum) {
		return integrationDao.getSoIdByExternalOrderNum(externalOrderNum);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	public Connection getSqlConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public void setIntegrationDao(IIntegrationDao integrationDao) {
		this.integrationDao = integrationDao;
	}

	public IIntegrationDao getIntegrationDao() {
		return integrationDao;
	}

}
