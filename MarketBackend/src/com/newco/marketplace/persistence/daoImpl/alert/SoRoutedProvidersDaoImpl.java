/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.alert;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;


public class SoRoutedProvidersDaoImpl extends ABaseImplDao implements SoRoutedProvidersDao
{
    private static final Logger logger = Logger.getLogger(SoRoutedProvidersDaoImpl.class.getName());
    private static final Logger localLogger = Logger.getLogger(SoRoutedProvidersDaoImpl.class.getName());
    

    public int update(SoRoutedProviders soRoutedProviders) throws DataServiceException
    {
        int result = 0;
        
        try {
            result = update("soRoutedProviders.update", soRoutedProviders);
        } 
        catch (Exception ex) {
            localLogger.info("[soRoutedProviders.update - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = "dataaccess.update";
            throw new DataServiceException(error, ex);
        }                
        
	return result;
    }
    
    public SoRoutedProviders query(SoRoutedProviders soRoutedProviders) throws DataServiceException
    {
        try {
        	soRoutedProviders = (SoRoutedProviders) queryForObject("soRoutedProviders.query", soRoutedProviders);
        }
        catch (Exception ex) {
            localLogger.info("[soRoutedProviders.update - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = "dataaccess.select";
            throw new DataServiceException(error, ex);
        }                
            
        return soRoutedProviders;
    }
    
    public SoRoutedProviders insert(SoRoutedProviders soRoutedProviders) throws DataServiceException
    {
        logger.debug("[insert] entering");
        Integer id = null;

        try {
            soRoutedProviders = (SoRoutedProviders)insert("soRoutedProviders.insert", soRoutedProviders);
            
        }
        catch (Exception ex) {
            localLogger.info("[soRoutedProviders.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = "This Service Order has already been routed to the given service provider";
            throw new DataServiceException(error, ex);
        }                
        
        return soRoutedProviders;
    }
    
    public List queryList(SoRoutedProviders soRoutedProviders) throws DataServiceException
    {
        List result = null;
        
        try {
            result = queryForList("soRoutedProviders.query", soRoutedProviders);
        }
        catch (Exception ex) {
            localLogger.info("[soRoutedProviders.insert - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            final String error = "dataaccess.select";
            throw new DataServiceException(error, ex);
        }    
            
	return result;
    }
}
