package com.newco.marketplace.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.beans.EmailDataSource;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;

public interface EmailAlertDao {
	public boolean insertToAlertTask(AlertTask alertTask) throws DataServiceException;
	public ArrayList<AlertTask> retrieveAlertTasks(String priority, String providerType) throws DataServiceException;
	public void updateAlertStatus(List<Long> alertTaskIds) throws DataServiceException;
	public List<EmailDataSource> getDataSources(String schedulerName) throws DataServiceException;
	public List<HashMap<String, String>> fetchData(String preQuery) throws DataServiceException;
	public void executePostProcessor(String postQuery) throws DataServiceException;
	public Integer getEmailTemplateSpecificToBuyer(Integer buyerId,Integer templateId) throws DataServiceException;
	public ArrayList<AlertTask> retrieveAdobeAlertTasks(String priority, String providerType) throws DataServiceException;
}
