package com.servicelive.integrationtest.dao;

import java.util.List;

import com.servicelive.integrationtest.domain.QueryResults;

public interface IServiceOrderDao {

	public List<QueryResults> runAllQueriesForServiceOrder(String soId);
	
}
