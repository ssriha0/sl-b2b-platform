/*
** IVendorContactLocationDao.java    v1.0    Jun 14, 2007
*/
package com.newco.marketplace.persistence.iDao.provider;
import com.newco.marketplace.vo.provider.VendorContactLocation;

/**
 * Dao Interface for accessing Vendor Contact Location
 *
 * @version 
 * @author blars04
 *
 */
public interface IVendorContactLocationDao 
{
    /**
     * Inserts a Vendor Contact Location record into the datastore
     * 
     * @param vendorContactLocation
     * @return
     * @throws DataServiceException
     */
    public VendorContactLocation insert(VendorContactLocation vendorContactLocation);
    
    
}//end interface