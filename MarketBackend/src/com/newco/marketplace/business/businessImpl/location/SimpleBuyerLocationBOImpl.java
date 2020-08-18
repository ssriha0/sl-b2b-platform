package com.newco.marketplace.business.businessImpl.location;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.businessImpl.provider.ABaseBO;
import com.newco.marketplace.business.iBusiness.location.ISimpleBuyerLocationBO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceLocationDao;
import com.newco.marketplace.vo.provider.ResourceLocation;

/**
* CRUD operations for buyer resource's contact and location information
* @author Nick Sanzeri
*
* $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 22:54:20 $
*/
public class SimpleBuyerLocationBOImpl extends ABaseBO implements ISimpleBuyerLocationBO {
	private IBuyerResourceLocationDao buyerResourceLocationDao;

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.location.ISimpleBuyerLocationBO#getBuyerResourceInfo(java.lang.Integer)
	 */
	public Contact getBuyerResourceInfo(
			Integer buyerResId) throws DBException {
		Contact contactInfo = buyerResourceLocationDao.getBuyerResourceInfo(buyerResId);
		return contactInfo;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.location.ISimpleBuyerLocationBO#updateBuyerResourceInfo(com.newco.marketplace.dto.vo.serviceorder.Contact)
	 */
	public int updateBuyerResourceInfo(Contact contact) throws DBException {
		int ret = 0;
		ret = buyerResourceLocationDao.updateBuyerResourceInfo(contact);
		return ret;
	}



	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.location.ISimpleBuyerLocationBO#getBuyerResourceLocationList(int)
	 */
	public List<LocationVO> getBuyerResourceLocationList(int buyerResId)
			throws DataAccessException {
		List<LocationVO> locVOs = new ArrayList<LocationVO>();
		locVOs = buyerResourceLocationDao.getBuyerResourceLocationList(buyerResId);
		return locVOs;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.location.ISimpleBuyerLocationBO#insertBuyerResLoc(com.newco.marketplace.dto.vo.LocationVO, java.lang.Integer)
	 */
	public LocationVO insertBuyerResLoc(LocationVO location, Integer buyerResId)
			throws DataAccessException {
		LocationVO locVO = buyerResourceLocationDao.insertBuyerResLoc(location);
		ResourceLocation resourceLocation = new ResourceLocation();
		resourceLocation.setLocationId(locVO.getLocnId());
		resourceLocation.setResourceId(buyerResId);
		resourceLocation.setCreatedDate(new Date(System.currentTimeMillis()));
		resourceLocation.setModifiedDate(new Date(System.currentTimeMillis()));
		resourceLocation.setModifiedBy("system");
		try {
			buyerResourceLocationDao.insertBuyerResourceLocation(resourceLocation);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return locVO;
	}
	
	public IBuyerResourceLocationDao getBuyerResourceLocationDao() {
		return buyerResourceLocationDao;
	}

	public void setBuyerResourceLocationDao(
			IBuyerResourceLocationDao buyerResourceLocationDao) {
		this.buyerResourceLocationDao = buyerResourceLocationDao;
	}
}
