package com.newco.marketplace.business;

import java.util.ArrayList;

import java.util.List;


import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;

public interface EmailAlertBO {
	
	public boolean insertToAlertTask(AlertTask alertTask) throws DataServiceException;
	public ArrayList<AlertTask> retrieveAlertTasks(String priority,String providerType) throws DataServiceException;
	public void updateAlertStatus(List<Long> alertTaskIds) throws DataServiceException;
	public ArrayList<AlertTask> retrieveAdobeAlertTasks(String priority,String providerType) throws DataServiceException;
	
}
