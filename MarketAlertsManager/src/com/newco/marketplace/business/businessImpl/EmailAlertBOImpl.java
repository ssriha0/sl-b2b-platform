package com.newco.marketplace.business.businessImpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.EmailAlertDao;

public class EmailAlertBOImpl extends MPBaseDaoImpl implements EmailAlertBO{
	 private EmailAlertDao emailAlertDao;
	 
	 public boolean insertToAlertTask(AlertTask alertTask) throws DataServiceException{
			boolean isInserted=emailAlertDao.insertToAlertTask(alertTask); 
			return isInserted;
		}
	
	 /* 
	 * @see com.newco.marketplace.business.EmailAlertBO#retrieveAlertTasks(java.lang.String, java.lang.String)
	 * it triggers to fetch the record from the alert task table
	 */
	public ArrayList<AlertTask> retrieveAlertTasks(String priority, String providerType)
			throws DataServiceException {
		ArrayList<AlertTask>  taskList=null;
        try {
        	taskList=emailAlertDao.retrieveAlertTasks(priority, providerType);
          
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAlertTasks - Exception", ex);
        }
        return taskList;
	}

	public void setEmailAlertDao(EmailAlertDao emailAlertDao) {
		this.emailAlertDao = emailAlertDao;
	}


	/* 
	 * @see com.newco.marketplace.business.EmailAlertBO#updateAlertStatus(java.util.List)
	 * it triggers the method to update the completion indicator in alert task table
	 * 
	 */
	public void updateAlertStatus(List<Long> alertTaskIds)
			throws DataServiceException {
		emailAlertDao.updateAlertStatus(alertTaskIds);
		
	}
	
		 /* 
	 * @see com.newco.marketplace.business.EmailAlertBO#retrieveAdobeAlertTasks(java.lang.String, java.lang.String)
	 * it triggers to fetch the record from the alert task table
	 */
	public ArrayList<AlertTask> retrieveAdobeAlertTasks(String priority, String providerType)
			throws DataServiceException {
		ArrayList<AlertTask>  taskList=null;
        try {
        	taskList=emailAlertDao.retrieveAdobeAlertTasks(priority, providerType);
          
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAdobeAlertTasks - Exception", ex);
        }
        return taskList;
	}
	
	
}
