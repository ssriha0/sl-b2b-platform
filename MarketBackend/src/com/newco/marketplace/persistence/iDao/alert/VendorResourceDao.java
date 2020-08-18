/**
 * 
 */
package com.newco.marketplace.persistence.iDao.alert;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.exception.core.DataServiceException;

public interface VendorResourceDao
{
    /**
     * 
     * @param vendorResource
     * @return
     * @throws DataServiceException
     */
    public int update(VendorResource vendorResource) throws DataServiceException;
    
    public int updateResourceLocationType(VendorResource vendorResource) throws DataServiceException;
    
    public VendorResource query(VendorResource vendorResource)
	    throws DataServiceException;
    
    /**
     * Inserts a vendor resource into the datastore
     * 
     * @param vendorResource
     * @return
     * @throws DataServiceException
     */
    public VendorResource insert(VendorResource vendorResource) throws DataServiceException;
    
    public List queryList(VendorResource vo) throws DataAccessException;
    
    public int updateBackgroundCheckStatus (TMBackgroundCheckVO tmbcVO) throws DataServiceException;
    
    public String getBackgroundCheckStatus (TMBackgroundCheckVO tmbcVO) throws DataServiceException;

    public String getTeamMemberEmail (TMBackgroundCheckVO tmbcVO) throws DataServiceException;
    
    public String getVendorEmail (TMBackgroundCheckVO tmbcVO) throws DataServiceException;
    
    public int updateWFState (VendorResource vendorResource) throws DataServiceException;

}
