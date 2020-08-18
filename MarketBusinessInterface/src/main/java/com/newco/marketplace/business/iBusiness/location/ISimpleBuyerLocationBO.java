package com.newco.marketplace.business.iBusiness.location;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;

/**
* CRUD operations for buyer resource's contact and location information
*  
* @author Nick Sanzeri
*
* $Revision$ $Author$ $Date$
*/
public interface ISimpleBuyerLocationBO {
	
	/**
	 * Description: retrieves buyer resource contact information
	 * @param buyerResId
	 * @return
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
	 * @param buyerResId 
	 * @return <code>LocationVO</code>
	 * @throws DataAccessException 
	 */
	public LocationVO insertBuyerResLoc(LocationVO location, Integer buyerResId)	throws DataAccessException;
	
	/**
	 * Description: Retrieves a list of locations associated with buyer resource when given their id.
	 * @param buyerResId
	 * @return List<LocationVO>
	 * @throws DataAccessException
	 */
	public List<LocationVO> getBuyerResourceLocationList(int buyerResId) throws DataAccessException;

}
