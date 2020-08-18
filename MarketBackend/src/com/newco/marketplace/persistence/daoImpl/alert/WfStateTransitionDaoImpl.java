/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.alert;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.newco.marketplace.dto.vo.alert.WfStateTransitionVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;


public class WfStateTransitionDaoImpl extends ABaseImplDao implements WfStateTransitionDao
{
    private static final Logger logger = Logger.getLogger(WfStateTransitionDaoImpl.class.getName());
    private static final Logger localLogger = Logger.getLogger(WfStateTransitionDaoImpl.class.getName());
    private static final MessageResources messages = MessageResources.getMessageResources("DataAccessLocalStrings");

    public int update(WfStateTransitionVO wfStateTransitionVO) throws DataServiceException
    {
        int result = 0;
        
        try {
            result = update("wfStateTransition.update", wfStateTransitionVO);
        } 
        catch (Exception ex) {
            localLogger.info("[WfStateTransitionDaoImpl.update - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
        
	return result;
    }
    
    public WfStateTransitionVO query(WfStateTransitionVO wfStateTransitionVO) throws DataServiceException
    {
        try {
        	wfStateTransitionVO = (WfStateTransitionVO) queryForObject("wfStateTransition.query", wfStateTransitionVO);
        }
        catch (Exception ex) {
            localLogger.info("[WfStateTransitionDaoImpl.update - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
            
        return wfStateTransitionVO;
    }
    
    public WfStateTransitionVO insert(WfStateTransitionVO wfStateTransitionVO) throws DataServiceException
    {
        logger.debug("[insert] entering");
        Integer id = null;

        try {
            id = (Integer)insert("wfStateTransition.insert", wfStateTransitionVO);
            wfStateTransitionVO.setStateTransitionId(id.intValue());
        }
        catch (Exception ex) {
            localLogger.info("[WfStateTransitionDaoImpl.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }                
        
        return wfStateTransitionVO;
    }
    
    public List queryList(WfStateTransitionVO wfStateTransitionVO) throws DataServiceException
    {
        List result = null;
        
        try {
            result = queryForList("wfStateTransition.query", wfStateTransitionVO);
        }
        catch (Exception ex) {
            localLogger.info("[WfStateTransitionDaoImpl.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = messages.getMessage("dataaccess.select");
            throw new DataServiceException(error, ex);
        }    
            
	return result;
    }
}
