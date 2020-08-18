/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.alert;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;


public class AlertTaskDaoImpl extends ABaseImplDao implements AlertTaskDao {
    private static final Logger logger = Logger.getLogger(AlertTaskDaoImpl.class.getName());
    private static final Logger localLogger = Logger.getLogger(AlertTaskDaoImpl.class.getName());
    private static final MessageResources messages = MessageResources.getMessageResources("DataAccessLocalStrings");

    public int update(AlertTaskVO alertTaskVO) throws DataServiceException
    {
        int result = 0;
        
        try {
            result = update("alertTask.update", alertTaskVO);
        } 
        catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.update - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
        
	return result;
    }
    
    public AlertTaskVO query(AlertTaskVO alertTaskVO) throws DataServiceException
    {
        try {
        	alertTaskVO = (AlertTaskVO) queryForObject("alertTask.query", alertTaskVO);
        }
        catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.update - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
            
        return alertTaskVO;
    }
    
    public AlertTaskVO insert(AlertTaskVO alertTaskVO) throws DataServiceException
    {
        logger.debug("[insert] entering");
        Integer id = null;

        try {
            id = (Integer)insert("alertTask.insert", alertTaskVO);
            alertTaskVO.setAlertTaskId(id.intValue());
        }
        catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
        
        return alertTaskVO;
    }
    
    public List<AlertTaskVO> queryList(AlertTaskVO alertTaskVO) throws DataServiceException {
        List<AlertTaskVO> result = null;
        try {
            result = queryForList ("alertTask.query", null);
        } catch (Exception ex) {
            localLogger.info("[AlertTaskDaoImpl.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }    
            
	return result;
    }
}
