package com.newco.marketplace.persistence.daoImpl.provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.ITermsAndCondDao;
import com.newco.marketplace.vo.provider.TermsAndCondVO;
import com.newco.marketplace.utils.ActivityRegistryConstants;

public class TermsAndCondDaoImpl extends SqlMapClientDaoSupport implements ITermsAndCondDao{
	
	private final Log logger = LogFactory.getLog(getClass());
	private IActivityRegistryDao activityRegistryDao;
	private ILookupDAO lookupDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.IBusinessinfoDao#query(com.newco.marketplace.web.dto.BusinessinfoDto)
	 */
	public TermsAndCondVO save(TermsAndCondVO objTermsAndCondVO) {

		try {
			getSqlMapClient().update("termsAndCond.update", objTermsAndCondVO);
			getActivityRegistryDao().updateActivityStatus(String
					.valueOf(objTermsAndCondVO.getVendorId()),
					ActivityRegistryConstants.TERMSANDCOND);
		} catch (Exception ex) {
			logger.info("[TermsAndCondDaoImpl.save - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}

		return objTermsAndCondVO;
	}

	
	public TermsAndCondVO getData(TermsAndCondVO objTermsAndCondVO) {

		try {

			if (objTermsAndCondVO.getVendorId() > 0) {
				objTermsAndCondVO = (TermsAndCondVO) getSqlMapClient()
						.queryForObject("termsAndCond.get", objTermsAndCondVO);
			}

		} catch (Exception ex) {
			logger.info("[TermsAndCondDaoImpl.getData - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return objTermsAndCondVO;
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


	public ILookupDAO getLookupDAO() {
		return lookupDAO;
	}


	public void setLookupDAO(ILookupDAO lookupDAO) {
		this.lookupDAO = lookupDAO;
	}


	public Integer getData(Integer id) throws Exception {
		Integer ind =  (Integer) getSqlMapClient().queryForObject("termsAndCond.getInd",id);
		return ind;
	}


	public void save(Integer id) throws Exception {
		getSqlMapClient().update("termsAndCond.insertInd", id);
		
	}
}
