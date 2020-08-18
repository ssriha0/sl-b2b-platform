package com.newco.marketplace.business.businessImpl;


import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.beans.EmailDataSource;
import com.newco.marketplace.business.DataSourceBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.EmailAlertDao;

public class DataSourceBOImpl implements DataSourceBO{
	 private EmailAlertDao emailAlertDao;
	 
	 
	public void setEmailAlertDao(EmailAlertDao emailAlertDao) {
		this.emailAlertDao = emailAlertDao;
	}
	
	public List<EmailDataSource> getDataSources(String schedulerName) throws DataServiceException{
		return(emailAlertDao.getDataSources(schedulerName));
	}
	public List<HashMap<String, String>> fetchData(String preQuery) throws DataServiceException{
		return(emailAlertDao.fetchData(preQuery));
	}
	public void executePostProcessor(String postQuery) throws DataServiceException{
		emailAlertDao.executePostProcessor(postQuery);
	}
}
