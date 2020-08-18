package com.newco.marketplace.persistence.daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;


import com.newco.marketplace.beans.AlertTaskUpdate;
import com.newco.marketplace.beans.EmailDataSource;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.EmailAlertDao;

public class EmailAlertDaoImpl  extends MPBaseDaoImpl implements EmailAlertDao{

	
	public boolean insertToAlertTask(AlertTask alertTask) throws DataServiceException{
		 try {
			 if(StringUtils.isBlank(alertTask.getAlertTo()) &&
					 StringUtils.isBlank(alertTask.getAlertCc())&&
					 StringUtils.isBlank(alertTask.getAlertBcc())){
				 alertTask.setCompletionIndicator("2");
			 }
	            insert("emailalert.insert", alertTask);
	        } catch (Exception e) {
	            throw new DataServiceException("Exception occured at AlertDaoImpl-addAlertToQueue", e);
	        }
	        return true;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.EmailAlertDao#retrieveAlertTasks(java.lang.String, java.lang.String)
	 * it fetches the record from the alert task table for the given priority and provider type
	 */
	public ArrayList<AlertTask> retrieveAlertTasks(String priority,String providerType) throws DataServiceException {
        try {
            AlertTask alertTask = new AlertTask();
            String ppp = priority;
            alertTask.setPriority(ppp);
            alertTask.setProviderType(providerType);
            return (ArrayList) queryForList("emailalert.selectalert", alertTask);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAlertTasks - Exception", ex);
        }
    }

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.EmailAlertDao#updateAlertStatus(java.util.List)
	 * it updates the completion indicator in the alert task table
	 */
	public void updateAlertStatus(List<Long> alertTaskIds) throws DataServiceException {
        try {
            if (alertTaskIds != null && !alertTaskIds.isEmpty()) {
                AlertTaskUpdate atp = new AlertTaskUpdate();
                atp.setAlertTaskIds(alertTaskIds);
                int result=update("emailalert.setCompletion", atp);
            }
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.updateAlertStatus - Exception", ex);
        }
    }
	public List<EmailDataSource> getDataSources(String schedulerName) throws DataServiceException{
		
		List<EmailDataSource> queryList=new ArrayList<EmailDataSource>();
		queryList= (ArrayList) queryForList("emailalert.getQueries", schedulerName);
		return queryList;
		
	}
	public List<HashMap<String, String>> fetchData(String preQuery) throws DataServiceException{
		 List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		 HashMap<String, String> processLogMap = new HashMap<String, String>();
		 try {
			 dataList = (List<HashMap<String, String>>) queryForList("emailalert.executePreQuery", preQuery);
			} catch (Exception ex) {
				throw new DataServiceException("Exception occurred in NachaDao.getSumAchProcessByLogId()", ex);
			}
			
		return dataList;
	}
	public void executePostProcessor(String postQuery) throws DataServiceException{
		//Execute Update sql
		int result=update("emailalert.executePostQuery", postQuery);
	}
	
	public Integer getEmailTemplateSpecificToBuyer(Integer buyerId,Integer templateId) throws DataServiceException {
		Integer tempId = 0;
		Map parametermap=new HashMap<String,String>();
		parametermap.put("buyerId", buyerId);
		parametermap.put("templateId",templateId);
		 try {
			 tempId=(Integer)queryForObject("emailalert.originalTemplateNonFunded",parametermap);
			} catch (Exception ex) {
				throw new DataServiceException("Exception occurred in NachaDao.getEmailTemplateSpecificToBuyer()", ex);
			}
			
		return tempId;
		
	}
	
		/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.EmailAlertDao#retrieveAdobeAlertTasks(java.lang.String, java.lang.String)
	 * it fetches the record from the alert task table for the given priority and provider type
	 */
	public ArrayList<AlertTask> retrieveAdobeAlertTasks(String priority,String providerType) throws DataServiceException {
        try {
            AlertTask alertTask = new AlertTask();
            String ppp = priority;
            alertTask.setPriority(ppp);
            alertTask.setProviderType(providerType);
            return (ArrayList) queryForList("emailalert.selectAdobeAlert", alertTask);
        } catch (Exception ex) {
            throw new DataServiceException("AlertDaoImpl.retrieveAdobeAlertTasks - Exception", ex);
        }
    }
	
}
