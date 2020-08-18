/*
** IVendorContactLocationDao.java    v1.0    Jun 14, 2007
*/
package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.ResourceLocation;

/**
 * Dao Interface for accessing Resource Location
 *
 * @version 
 * @author blars04
 *
 */
public interface IResourceLocationDao 
{
    
    
    /**
     * Inserts a Resource Location record into the datastore
     * 
     * @param resourceLocation
     * @return
     * @throws DataServiceException
     */
    public ResourceLocation insert(ResourceLocation resourceLocation) throws DBException;
    
    
}//end interface