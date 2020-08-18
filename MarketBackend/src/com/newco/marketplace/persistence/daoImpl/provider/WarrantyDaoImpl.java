package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IWarrantyDao;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.WarrantyVO;

/**
 * @author MTedder
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 00:40:31 $
 *	This class contains methods to perform database operations.
 */
public class WarrantyDaoImpl extends SqlMapClientDaoSupport implements IWarrantyDao {

	HashMap mapLuWarrantyPeriods;
	private IActivityRegistryDao activityRegistryDao;
	
	/* 
	 * Implementation of method to retrieve the VO data from the vendor_policy database table for a given vendor_id.
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistance.iDao.IWarrantyDao#query(com.newco.marketplace.vo.WarrantyVO)
	 */
	public WarrantyVO query(WarrantyVO objWarrantyVO) {
		// ADD IBATIS CODE HERE from Vendor Policy table		
		try {
			if (objWarrantyVO== null) {
				return null;
			}
			objWarrantyVO = (WarrantyVO)getSqlMapClient().queryForObject("getWarrantyData.query", objWarrantyVO);
			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}
		
		return objWarrantyVO;
	}
	
	/*
	 * Implementation of method to insert the VO data into the vendor_policy database table for a given vendor_id.
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.IWarrantyDao#insert(com.newco.marketplace.vo.WarrantyVO)
	 */
	public void insert(WarrantyVO objWarrantyVO) {
		try {
			getSqlMapClient().insert("saveWarrantyData.insert", objWarrantyVO);
			getActivityRegistryDao().updateActivityStatus(objWarrantyVO.getVendorID(), ActivityRegistryConstants.WARRANTY);
		} catch (Exception ex) {			
			ex.printStackTrace();
		}		
	}//
	
	/*
	 * Implementation of Delete warranty info in vendor_policy table for given vendor ID.
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.IWarrantyDao#delete(com.newco.marketplace.vo.WarrantyVO)
	 */
	public void delete(WarrantyVO objWarrantyVO) {		
		try {
			getSqlMapClient().delete("deleteWarrantyData.delete", objWarrantyVO);
			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * @param objWarrantyVO
	 */
	public void update(WarrantyVO objWarrantyVO){
		try {	
			getSqlMapClient().update("updateWarrantyData.update", objWarrantyVO);
			getActivityRegistryDao().updateActivityStatus(objWarrantyVO.getVendorID(), ActivityRegistryConstants.WARRANTY);
		} catch (Exception ex) {			
			ex.printStackTrace();
		}				
	}
	
	public void updateWarrantyPartialData(WarrantyVO objWarrantyVO){
		try {	
			getSqlMapClient().update("updateWarrantyPartialData.update", objWarrantyVO);
			getActivityRegistryDao().updateActivityStatus(objWarrantyVO.getVendorID(), ActivityRegistryConstants.WARRANTY);
		} catch (Exception ex) {			
			ex.printStackTrace();
		}				
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.IWarrantyDao#loadPage(com.newco.marketplace.vo.WarrantyVO)
	 */
	public WarrantyVO loadPage(WarrantyVO objWarrantyVO) {
		try {
			objWarrantyVO.setWarrantyPeriods(getMapLuWarrantyPeriods());
			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}	
	
		
		return objWarrantyVO;
	}
	
	public HashMap getMapLuWarrantyPeriods()
	{
		
		mapLuWarrantyPeriods = new HashMap();
		
		try {
			mapLuWarrantyPeriods = (HashMap) getSqlMapClient().queryForMap("getLuWarrantyPeriods.query", null, "id", "descr") ;
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
		return mapLuWarrantyPeriods;		
	}

	/**
	 * @return the activityRegistryDao
	 */
	public IActivityRegistryDao getActivityRegistryDao() {
		return activityRegistryDao;
	}

	/**
	 * @param activityRegistryDao the activityRegistryDao to set
	 */
	public void setActivityRegistryDao(IActivityRegistryDao activityRegistryDao) {
		this.activityRegistryDao = activityRegistryDao;
	}
}
