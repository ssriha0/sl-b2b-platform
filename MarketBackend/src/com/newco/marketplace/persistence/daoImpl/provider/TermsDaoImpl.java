package com.newco.marketplace.persistence.daoImpl.provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.persistence.iDao.provider.ITermsDao;
import com.newco.marketplace.vo.provider.TermsVO;


public class TermsDaoImpl extends SqlMapClientDaoSupport implements ITermsDao{
	
	private final Log logger = LogFactory.getLog(getClass());
	//private IActivityRegistryDelegate activityRegistryDelegate;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.IBusinessinfoDao#query(com.newco.marketplace.web.dto.BusinessinfoDto)
	 */
	public TermsVO save(TermsVO objTermsVO) {

		try {
			getSqlMapClient().update("updateTerms.update", objTermsVO);
			//activityRegistryDelegate.updateActivityStatus(String.valueOf(objTermsVO.getVendorId()),	ActivityRegistryConstants.TERMSANDCOND);
		} catch (Exception ex) {
			logger.info("[TermsDaoImpl.insert - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return objTermsVO;
	}

	/**
	 * @param activityRegistryDelegate the activityRegistryDelegate to set
	 */
	
	
	public TermsVO getData(TermsVO objTermsVO) {

		try {

			if (objTermsVO.getVendorId() > 0) {
				objTermsVO = (TermsVO) getSqlMapClient()
						.queryForObject("selectTerms.query", objTermsVO);
			}

		} catch (Exception ex) {
			logger.info("[LicensesAndCertBOImpl.select - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return objTermsVO;
	}
}
