package com.newco.marketplace.persistence.daoImpl.buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceLocationDao;
import com.newco.marketplace.vo.provider.ResourceLocation;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author Nick Sanzeri
 *
 * $Revision: 1.5 $ $Author: akashya $ $Date: 2008/05/21 22:54:34 $
 */
public class BuyerResourceLocationDaoImpl extends ABaseImplDao implements
		IBuyerResourceLocationDao {
	private static final Logger logger = Logger.getLogger(BuyerResourceLocationDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.buyer.registration.dao.ResourceLocation#insert(com.newco.provider.registration.dao.resourceLocation)
	 */
	public ResourceLocation insert(ResourceLocation resourceLocation)
			throws DBException {
		try {
			resourceLocation = (ResourceLocation) getSqlMapClient().insert(
					"buyerLocation.insert", resourceLocation);
		}
		catch (SQLException ex) {
			errorMessage(ex, "insert");
		} catch (Exception ex) {
			errorMessage(ex, "insert");		
		}
		return resourceLocation;

	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceLocationDao#getBuyerResourceInfo(java.lang.Integer)
	 */
	public Contact getBuyerResourceInfo(Integer buyerResId) throws DBException {
		Contact contactInfo = null;
		try {
			contactInfo = (Contact) getSqlMapClient().queryForObject(
					"contactInfo.query", buyerResId);

		} catch (SQLException ex) {
			errorMessage(ex, "getBuyerResourceInfo");
		} catch (Exception ex) {
			errorMessage(ex, "getBuyerResourceInfo");
		}
		return contactInfo;

	}
	
	 /* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceLocationDao#updateBuyerResourceInfo(com.newco.marketplace.dto.vo.serviceorder.Contact)
	 */
	public int updateBuyerResourceInfo(Contact contact)throws DBException {
		int retCode = 0;
		try {
			retCode =  update("contactInfo.updateEmailPhone", contact);
		} catch (DataAccessException dae) {
			errorMessage(dae, "updateBuyerResourceInfo");
		} catch (Exception e) {
			errorMessage(e, "updateBuyerResourceInfo");
		}
		return retCode;
	}

    /* (non-Javadoc)
     * @see com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceLocationDao#locationIdInsert(com.newco.marketplace.dto.vo.LocationVO)
     */
    public LocationVO insertBuyerResLoc(LocationVO location)
			throws DataAccessException {
		Integer id = null;
		try {
			id = (Integer) insert("locationId.LocVoinsert", location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		location.setLocnId(id);
		return location;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceLocationDao#getBuyerResourceLocationList(int)
	 */
	public List<LocationVO> getBuyerResourceLocationList(int buyerResId)
			throws DataAccessException {
		List<LocationVO> locVOs = new ArrayList<LocationVO>();
		try {
			locVOs = (ArrayList) queryForList("contactInfo.buyerResourceLocationListQuery", new Integer(buyerResId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locVOs;
	}	
	
	/**
	 * Description: takes exception and method name and creates a log info message and 
	 * throws DBException.  Cuts down on repeating these lines in every DAO method. 
	 * @param ex
	 * @param methodName
	 * @throws DBException
	 */
	private void errorMessage(Exception ex, String methodName)
			throws DBException {
		logger.info("Exception @BuyerResourceLocationDaoImpl." + methodName
				+ "() due to" + ex.getMessage());
		throw new DBException("Exception @BuyerResourceLocationDaoImpl." + methodName
				+ "() due to " + ex.getMessage(), ex);
	}

	public ResourceLocation insertBuyerResourceLocation(ResourceLocation resourceLocation) throws DBException {
		try {
			resourceLocation = (ResourceLocation) getSqlMapClient().insert(
					"buyerResourceLocation.insert", resourceLocation);
		}
		catch (SQLException ex) {
			errorMessage(ex, "insertBuyerResourceLocation");
		} catch (Exception ex) {
			errorMessage(ex, "insertBuyerResourceLocation");		
		}
		return resourceLocation;
	}	

}
