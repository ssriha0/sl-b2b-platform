/**
 * 
 */
package com.newco.marketplace.persistence.iDao.buyer;

import com.newco.marketplace.dto.vo.buyer.BuyerUserProfile;
import com.newco.marketplace.exception.DBException;
 
/**
 * @author paugus2
 */
public interface IBuyerUserProfileDao {

	public BuyerUserProfile insert(BuyerUserProfile userProfile) throws DBException;
	
    public BuyerUserProfile query(BuyerUserProfile userProfile)throws DBException;

}
