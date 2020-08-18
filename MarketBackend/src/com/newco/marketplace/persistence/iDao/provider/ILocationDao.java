
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.ResourceLocationType;

public interface ILocationDao
{
    public int update(Location location) throws DBException;
    public Location query(Location location)throws DBException;
    public ResourceLocationType queryResourceLocationType(ResourceLocationType resourceLocationType)throws DBException;
    public ResourceLocationType queryResourceWorkLocation(ResourceLocationType resourceLocationType)throws DBException;
    
    
    
    /**
     * Inserts a location object into the datastore.  Returns the same location
     * object, with the locationId populated.
     * 
     * @param location
     * @return
     * @throws DataServiceException
     */
    public Location insert(Location location)throws DBException;
    
    
    /**
     * @deprecated As of release 1.3, replaced by {@link #insert()}
     */

    public Location locationIdInsert(Location location) throws DBException;
    
    public List queryList(Location vo) throws DBException;
    
    /**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public int update(GeneralInfoVO generalInfoVO)throws DBException;
    
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO get(GeneralInfoVO generalInfoVO) throws DBException;
    
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException;
	
	public Location queryVendorLocation(int vendorId) throws DBException;
}
