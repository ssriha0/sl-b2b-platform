package com.newco.marketplace.business;


import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.beans.EmailDataSource;
import com.newco.marketplace.exception.core.DataServiceException;

public interface DataSourceBO{
	
	public List<EmailDataSource> getDataSources(String schedulerName) throws DataServiceException;
		
	
	public List<HashMap<String, String>> fetchData(String sql) throws DataServiceException;
	
	public void executePostProcessor(String postQuery) throws DataServiceException;
}
