package com.servicelive.integrationtest.bo;

import java.util.List;

import com.servicelive.integrationtest.domain.QueryResults;

public interface IServiceOrderBO {
	
	public List<QueryResults> runAllQueriesForServiceOrder(String soId);

}
