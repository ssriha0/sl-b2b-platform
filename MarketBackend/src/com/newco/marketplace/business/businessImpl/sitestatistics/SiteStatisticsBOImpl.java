package com.newco.marketplace.business.businessImpl.sitestatistics;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.sitestatistics.ISiteStatisticsBO;
import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 22:54:29 $
 */
public class SiteStatisticsBOImpl implements ISiteStatisticsBO{

	private static final Logger logger = Logger.getLogger(SiteStatisticsBOImpl.class.getName());
	private SiteStatisticsDao siteStatisticsDao;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.sitestatistics.ISiteStatisticsBO#deleteAllSiteStatistics()
	 */
	public void deleteAllSiteStatistics() throws BusinessServiceException {
		try {
			siteStatisticsDao.deleteSiteStats();
		} catch (DataServiceException e) {
			logger.error("Unable to delete site statistics",e);
			throw new BusinessServiceException("Unable to delete site statistics",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.sitestatistics.ISiteStatisticsBO#getSiteStatistics()
	 */
	public SiteStatisticsVO getSiteStatistics() throws BusinessServiceException {
		SiteStatisticsVO data = null;
		
		try {
			data = siteStatisticsDao.getSiteStatistics();
		} catch (DataServiceException e) {
			logger.error("Unable to get site statistics",e);
			throw new BusinessServiceException("Unable to get site statistics",e);
		}
		
		return data;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.sitestatistics.ISiteStatisticsBO#loadSiteStats(com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO)
	 */
	public void loadSiteStats(SiteStatisticsVO data)
			throws BusinessServiceException {
		
		try {
			deleteAllSiteStatistics();
			siteStatisticsDao.insertSiteStats(data);
		} catch (DataServiceException e) {
			logger.error("Unable to load site statistics",e);
			throw new BusinessServiceException("Unable to load site statistics",e);
		}
		
		return;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.sitestatistics.ISiteStatisticsBO#getPopularServicesByBuyerType(int)
	 */
	public List<PopularServicesVO> getPopularServicesByBuyerType(int buyerTypeId)
			throws BusinessServiceException {
		List<PopularServicesVO> results = null;
		try {
			results = siteStatisticsDao.getPopularServicesByBuyerType(buyerTypeId);
		} catch (DataServiceException e) {
			logger.error("Unable to get list of popular services for buyer type:" + buyerTypeId,e);
			throw new BusinessServiceException("Unable to get list of popular services for buyer type:" + buyerTypeId,e);
		}
		return results;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.sitestatistics.ISiteStatisticsBO#loadPopularServices(int, java.util.List)
	 */
	public void loadPopularServices(int buyerTypeId,
			List<PopularServicesVO> data) throws BusinessServiceException {
		
		try {
			siteStatisticsDao.deletePopularServicesByBuyerType(buyerTypeId);
			siteStatisticsDao.insertPopularServices(data);
		} catch (DataServiceException e) {
			logger.error("Unable to get load popular services for buyer type:" + buyerTypeId,e);
			throw new BusinessServiceException("Unable to load popular services for buyer type:" + buyerTypeId,e);
		}
		
	}

	public SiteStatisticsDao getSiteStatisticsDao() {
		return siteStatisticsDao;
	}

	public void setSiteStatisticsDao(SiteStatisticsDao siteStatisticsDao) {
		this.siteStatisticsDao = siteStatisticsDao;
	}

}
