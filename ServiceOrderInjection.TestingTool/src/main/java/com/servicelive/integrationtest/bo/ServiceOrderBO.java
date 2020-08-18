package com.servicelive.integrationtest.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.servicelive.integrationtest.dao.IServiceOrderDao;
import com.servicelive.integrationtest.domain.QueryResults;

public class ServiceOrderBO implements IServiceOrderBO {
	
	private IServiceOrderDao serviceOrderDao;
	private DataSource dataSource;
	
	public List<QueryResults> runAllQueriesForServiceOrder(String soId) {
		return serviceOrderDao.runAllQueriesForServiceOrder(soId);
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

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public IServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

}
