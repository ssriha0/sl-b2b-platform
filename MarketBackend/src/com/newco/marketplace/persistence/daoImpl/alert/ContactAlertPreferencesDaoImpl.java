/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.alert;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.newco.marketplace.dto.vo.alert.ContactAlertPreferencesVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;


public class ContactAlertPreferencesDaoImpl extends ABaseImplDao implements ContactAlertPreferencesDao
{
    private static final Logger logger = Logger.getLogger(ContactAlertPreferencesDaoImpl.class.getName());
    private static final Logger localLogger = Logger.getLogger(ContactAlertPreferencesDaoImpl.class.getName());
    private static final MessageResources messages = MessageResources.getMessageResources("DataAccessLocalStrings");

    public int update(ContactAlertPreferencesVO contactAlertPreferencesVO) throws DataServiceException
    {
        int result = 0;
        
        try {
            result = update("contactAlertPreferences.update", contactAlertPreferencesVO);
        } 
        catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.update - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
        
	return result;
    }
    
    public ContactAlertPreferencesVO query(ContactAlertPreferencesVO contactAlertPreferencesVO) throws DataServiceException
    {
        try {
        	contactAlertPreferencesVO = (ContactAlertPreferencesVO) queryForObject("contactAlertPreferences.query", contactAlertPreferencesVO);
        }
        catch (Exception ex) {
            localLogger.info("[contactAlertPreferences.query - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
            
        return contactAlertPreferencesVO;
    }
    
    public ContactAlertPreferencesVO insert(ContactAlertPreferencesVO contactAlertPreferencesVO) throws DataServiceException
    {
        logger.debug("[insert] entering");
        Integer id = null;

        try {
            id = (Integer)insert("contactAlertPreferences.insert", contactAlertPreferencesVO);
            contactAlertPreferencesVO.setContactId(id.intValue());
        }
        catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
        
        return contactAlertPreferencesVO;
    }
    
    public ContactAlertPreferencesVO registrationInsert(ContactAlertPreferencesVO contactAlertPreferencesVO) throws DataServiceException
    {
        Integer id = null;

        try
        {        
            id  = (Integer)insert("contactAlertPreferences.insert", contactAlertPreferencesVO);
            localLogger.info("GENERATE ID OF: " + id);
        }
        catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }       
        
        localLogger.info("here is the contact id number (" + id + ")");

        contactAlertPreferencesVO.setContactId(id.intValue());
        return contactAlertPreferencesVO;
    }
    
    public List queryList(ContactAlertPreferencesVO contactAlertPreferencesVO) throws DataServiceException
    {
        List result = null;
        
        try {
            result = queryForList("contactAlertPreferences.query", contactAlertPreferencesVO);
        }
        catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }    
            
	return result;
    }
}
