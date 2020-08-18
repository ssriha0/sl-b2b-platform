/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.alert;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;


public class VendorResourceImpl extends ABaseImplDao implements VendorResourceDao 
{
    private static final Logger logger = Logger.getLogger(VendorResourceImpl.class.getName());
    
    

    /**
     * (non-Javadoc)
     * @see com.newco.provider.registration.dao.VendorResourceDao#update(com.newco.provider.registration.dao.VendorResource)
     */
    public int update(VendorResource vendorResource) throws DataServiceException
    {
        try
        {
            return update("vendorResource.update", vendorResource);

        }//end try
        catch (DataAccessException dae)
        {
            logger.info("[Exception thrown updating a vendor resource] " + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("dataaccess.failedinsert", dae);
            
        }//end catch
        catch (Exception e)
        {
            logger.info("[Exception thrown updating a vendor resource] " + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("dataaccess.failedinsert", e);
            
        }//end catch (Exception
        
    }//end method
    
    
    public int updateResourceLocationType(VendorResource vendorResource) throws DataAccessException
    {
	return update("vendorResource.updateResourceLocationType", vendorResource);
    }
    
    
    public VendorResource query(VendorResource vendorResource) throws DataAccessException
    {
	return (VendorResource) queryForObject("vendorResource.query", vendorResource);
    }
    
    /*
     * (non-Javadoc)
     * @see com.newco.provider.registration.dao.VendorResourceDao#insert(com.newco.provider.registration.dao.VendorResource)
     */
    public VendorResource insert(VendorResource vendorResource) throws DataServiceException
    {
        Integer id = null;
        
        try
        {
            id =  (Integer) insert("vendorResource.insert", vendorResource);
            
        }//end try
        catch (Exception e)
        {
            logger.info("[Exception thrown inserting a vendor resource] " + StackTraceHelper.getStackTrace(e));
            throw new DataServiceException("dataaccess.failedinsert", e);
            
        }//end catch
        
        vendorResource.setResourceId(id);
        return vendorResource;
        
    }//end method insert()
    
    public List queryList(VendorResource vendorResource) throws DataAccessException
    {
	return queryForList("vendorResource.query", vendorResource);
    } // queryList
    
    public int updateBackgroundCheckStatus (TMBackgroundCheckVO tmbcVO) throws DataServiceException {
        return (update("vendorResource.updateBackgroundCheckStatus", tmbcVO));
    }//updateBackgroundCheckStatus
    
    public String getBackgroundCheckStatus (TMBackgroundCheckVO tmbcVO) throws DataServiceException    {
        String rvString = (String) queryForObject ("vendorResource.getBackgroundCheckStatus", tmbcVO.getResourceId());
        if (null==rvString) {
            rvString = "";
        }
        return (rvString);
    }//getBackgroundCheckStatus
    
    public String getTeamMemberEmail (TMBackgroundCheckVO tmbcVO) throws DataServiceException    {
        String rvString = (String) queryForObject ("vendorResource.getTeamMemberEmail", tmbcVO.getResourceId());
        if (null==rvString) {
            rvString = "";
        }
        return (rvString);
        
    }//getTeamMemberEmail
    
    public String getVendorEmail (TMBackgroundCheckVO tmbcVO) throws DataServiceException    {
        String rvString = (String) queryForObject ("vendorResource.getVendorEmail", tmbcVO.getResourceId());
        if (null==rvString) {
            rvString = "";
        }
        return (rvString);
    }//getVendorEmail
    
    public int updateWFState(VendorResource vendorResource) throws DataServiceException
    {
        try
        {
            return update("vendorResource.updateWfStateId", vendorResource);

        }//end try
        catch (Exception  dae)
        {
            logger.info("[Exception thrown updating a vendor resource] " + StackTraceHelper.getStackTrace(dae));
            throw new DataServiceException("dataaccess.failedinsert", dae);
            
        }//end catch
        
    }//end method

    

}
