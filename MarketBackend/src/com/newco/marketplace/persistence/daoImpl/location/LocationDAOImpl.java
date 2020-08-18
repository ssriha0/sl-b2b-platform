package com.newco.marketplace.persistence.daoImpl.location;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.provider.ProviderLocationVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.location.LocationDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class LocationDAOImpl extends ABaseImplDao implements LocationDao {
	
	 private static final Logger logger = Logger.getLogger(LocationDAOImpl.class.getName());
	 private static final MessageResources messages = MessageResources.getMessageResources("DataAccessLocalStrings");
	 public int update(ProviderLocationVO location) throws DataAccessException
	 {
      return update("location.update", location);
	 }
	 
	 public ProviderLocationVO query(ProviderLocationVO location) throws DataAccessException
	 {
      return (ProviderLocationVO) queryForObject("location.query", location);
	 } 
	 
	 public ProviderLocationVO insert(ProviderLocationVO location) throws DataServiceException
	{
	    Integer id = null;
	    
	    try
	    {
	        id =  (Integer) insert("location.insert", location);
	    
	}//end try
	catch (Exception e)
	{
	    logger.info("[Exception thrown inserting a location] " + StackTraceHelper.getStackTrace(e));
	throw new DataServiceException(messages.getMessage("dataaccess.failedinsert"), e);
	    
	}//end catch
	    
	    location.setLocationId(id);
	    return location;
	    
	}
	 
    public List queryList(ProviderLocationVO location) throws DataAccessException
	{
	  return queryForList("location.query", location);
	}
	 
    public ProviderLocationVO locationIdInsert(ProviderLocationVO location) throws DataAccessException
    {
        Integer id = null;

        try
        {
        
            id  = (Integer)insert("locationId.insert", location);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        System.out.println("here is the contact id number (" + id + ")");

        location.setLocationId(id.intValue());
        return location;

    }
	 
	 public int updateLocation(ProviderLocationVO locationVO)
			throws DataServiceException {
		try
        {
            return update("location.updateLatLong", locationVO);

        }//end try
        catch (DataAccessException dae)
        {
            logger.info("[Exception thrown updating a location resource] " + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("dataaccess.failedinsert", dae);
            
        }//end catch
        catch (Exception e)
        {
            logger.info("[Exception thrown updating a location resource] " + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("dataaccess.failedinsert", e);
            
        }
		
	}
	public ProviderLocationVO retrieveLocation(VendorResource vendorResource) throws DataServiceException {
		
		try
		{
			return (ProviderLocationVO)queryForObject("location.selectAddress", vendorResource);
		}
		catch (DataAccessException dae)
        {
            logger.info("[Exception thrown updating a location resource] " + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("dataaccess.failedinsert", dae);
            
        }//end catch
        catch (Exception e)
        {
            logger.info("[Exception thrown updating a location resource] " + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("dataaccess.failedinsert", e);
            
        }
	}
}
