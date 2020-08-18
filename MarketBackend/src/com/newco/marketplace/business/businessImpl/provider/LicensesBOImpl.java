/**
 * 
 */
package com.newco.marketplace.business.businessImpl.provider;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.ILicensesBO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.ILicensesDao;
import com.newco.marketplace.vo.provider.LicensesVO;

/**
 * @author MTedder
 *
 */
public class LicensesBOImpl implements ILicensesBO {

	private ILicensesDao iLicensesDao;
private IAuditBO auditBusinessBean;
	
	private static final Logger logger = Logger.getLogger(LicensesAndCertBOImpl.class);
	
	private IAuditBO getAuditBusinessBean() {

		if (auditBusinessBean == null) {
		auditBusinessBean = (IAuditBO) MPSpringLoaderPlugIn.getCtx().getBean(MPConstants.BUSINESSBEAN_AUDIT);
		}
		return (auditBusinessBean);
		}

	
	/**
	 * @param licensesDao
	 */
	public LicensesBOImpl(ILicensesDao licensesDao) {		
		iLicensesDao = licensesDao;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesBO#delete(com.newco.marketplace.vo.LicensesVO)
	 */
	public void delete(LicensesVO licensesVO) {
		//call DAO layer
		iLicensesDao.delete(licensesVO);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesBO#get(com.newco.marketplace.vo.LicensesVO)
	 */
	public LicensesVO get(LicensesVO licensesVO) {
		//call DAO layer
		licensesVO = iLicensesDao.get(licensesVO);
		return licensesVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesBO#update(com.newco.marketplace.vo.LicensesVO)
	 */
	public void update(LicensesVO licensesVO) {
		iLicensesDao.update(licensesVO);	
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILicensesBO#save(com.newco.marketplace.vo.LicensesVO)
	 */
	public void insert(LicensesVO licensesVO) {
		//call DAO layer
		iLicensesDao.insert(licensesVO);

	}
	}
