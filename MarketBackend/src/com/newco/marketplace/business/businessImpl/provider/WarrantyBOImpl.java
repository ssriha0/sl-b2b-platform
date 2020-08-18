package com.newco.marketplace.business.businessImpl.provider;

import java.util.HashMap;

import com.newco.marketplace.business.iBusiness.provider.IWarrantyBO;
import com.newco.marketplace.persistence.iDao.provider.IWarrantyDao;
import com.newco.marketplace.vo.provider.WarrantyVO;

/**
 * @author MTedder
 *
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 00:40:26 $
 */

/*
 * Maintenance History
 * $Log: WarrantyBOImpl.java,v $
 * Revision 1.5  2008/04/26 00:40:26  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.12.1  2008/04/23 11:42:14  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.4  2008/04/23 05:01:46  hravi
 * Reverting to build 247.
 *
 * Revision 1.3  2008/02/05 22:23:15  mhaye05
 * removed commented out code
 *
 */
public class WarrantyBOImpl implements IWarrantyBO {

		private IWarrantyDao iWarrantyDao;
	/**
		 * @param warrantyDao
		 */
		public WarrantyBOImpl(IWarrantyDao warrantyDao) {			
			iWarrantyDao = warrantyDao;
		}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.warranty.IWarrantyBO#getWarrantyData()
	 */
	public WarrantyVO getWarrantyData(WarrantyVO objWarrantyVO) {
		//query database for warranty data
		System.out.println("getWarrantyData");
		objWarrantyVO = iWarrantyDao.query(objWarrantyVO);
		return objWarrantyVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.warranty.IWarrantyBO#setWarrantyData(com.newco.marketplace.vo.WarrantyVO)
	 */
	public int saveWarrantyData(WarrantyVO objWarrantyVO) {
		WarrantyVO warrantyVO = getWarrantyData(objWarrantyVO);
		if (warrantyVO!=null) {
			iWarrantyDao.update(objWarrantyVO);
		} else {
			iWarrantyDao.insert(objWarrantyVO);
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.warranty.IWarrantyBO#deleteWarrantyData(com.newco.marketplace.vo.WarrantyVO)
	 */
	public void deleteWarrantyData(WarrantyVO objWarrantyVO) {
		// TODO Auto-generated method stub
		System.out.println("deletedWarrantyData");
		iWarrantyDao.delete(objWarrantyVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.warranty.IWarrantyBO#updateWarrantyData(com.newco.marketplace.vo.WarrantyVO)
	 */
	public void updateWarrantyData(WarrantyVO objWarrantyVO){
		// TODO Auto-generated method stub
		System.out.println("updateWarrantyData");
		iWarrantyDao.update(objWarrantyVO);		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.warranty.IWarrantyBO#loadPage(com.newco.marketplace.vo.WarrantyVO)
	 */
	public WarrantyVO loadPage(WarrantyVO objWarrantyVO) {
		// TODO Auto-generated method stub
		System.out.println("getLuWarrantyPeriods");
		objWarrantyVO = iWarrantyDao.loadPage(objWarrantyVO);
		return objWarrantyVO;
	}

	/*
	 * Get drop down box data from DB.
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IWarrantyBO#getMapLuWarrantyPeriods()
	 */
	public HashMap getMapLuWarrantyPeriods() {
		return iWarrantyDao.getMapLuWarrantyPeriods();
	}

}
