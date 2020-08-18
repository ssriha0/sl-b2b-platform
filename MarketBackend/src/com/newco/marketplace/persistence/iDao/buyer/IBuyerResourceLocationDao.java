/*
** IVendorContactLocationDao.java    v1.0    Jun 14, 2007
*/
package com.newco.marketplace.persistence.iDao.buyer;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.ResourceLocation;

/**
 * Dao Interface for accessing Resource Location
 * and contact information
 *
 * @version 
 * @author blars04
 *
 */
public interface IBuyerResourceLocationDao 
{
    /**
     * Inserts a Resource Location record into the datastore
     * 
     * @param resourceLocation
     * @return
     * @throws DataServiceException
     */
    public ResourceLocation insert(ResourceLocation resourceLocation) throws DBException;
    
	/**
	 * Description: Returns Contact information when given a buyer resource Id. 
	 * @param buyerResId
	 * @return <code>Contact</code>
	 * @throws DBException
	 */
	public Contact getBuyerResourceInfo(Integer buyerResId) throws DBException;
	
	/**
	 * Description: Returns int of record updated or zero if no update was made 
	 * to the contact table.
	 * @param contact
	 * @return <code>int</code>
	 * @throws DBException
	 */
	public int updateBuyerResourceInfo(Contact contact)throws DBException;

	/**
	 * Description: Inserts a simple buyer resource's address into the locations table. 
	 * @param location
	 * @return <code>LocationVO</code>
	 * @throws DataAccessException 
	 */
	public LocationVO insertBuyerResLoc(LocationVO location)	throws DataAccessException;
	
	/**
	 * Description: Retrieves a list of locations associated with buyer resource when given their id.
	 * @param buyerResId
	 * @return List<LocationVO>
	 * @throws DataAccessException
	 */
	public List<LocationVO> getBuyerResourceLocationList(int buyerResId) throws DataAccessException;
	
    /**
     * Inserts a Buyer Resource Location record into the datastore
     * 
     * @param resourceLocation
     * @return
     * @throws DataServiceException
     */
	public ResourceLocation insertBuyerResourceLocation(ResourceLocation resourceLocation) throws DBException;
}