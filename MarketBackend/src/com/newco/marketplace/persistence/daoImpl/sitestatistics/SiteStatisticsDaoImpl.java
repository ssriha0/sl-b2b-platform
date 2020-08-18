package com.newco.marketplace.persistence.daoImpl.sitestatistics;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 22:54:07 $
 */
public class SiteStatisticsDaoImpl extends ABaseImplDao implements
		SiteStatisticsDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao#getSiteStatistics()
	 */
	public SiteStatisticsVO getSiteStatistics() throws DataServiceException {
		return (SiteStatisticsVO)queryForObject("siteStats.query", null);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao#deleteSiteStats()
	 */
	public void deleteSiteStats() throws DataServiceException {
		delete("siteStats_delete_all.delete", null);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao#insertSiteStats(com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO)
	 */
	public void insertSiteStats(SiteStatisticsVO data)
			throws DataServiceException {
		insert("siteStats.insert", data);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao#getPopularServicesByBuyerType(int)
	 */
	public List<PopularServicesVO> getPopularServicesByBuyerType(int buyerTypeId)
			throws DataServiceException {
		List<PopularServicesVO> results = queryForList("popularServicesByBuyerType.query", new Integer(buyerTypeId));
		if (null == results) {
			results = new ArrayList<PopularServicesVO>();
		}
		return results;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao#insertPopularServices(java.util.List)
	 */
	public void insertPopularServices(List<PopularServicesVO> data)
			throws DataServiceException {
		for(PopularServicesVO popularServicesVO : data) {
			insert("popularServices.insert",popularServicesVO);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.sitestatistics.SiteStatisticsDao#deletePopularServicesByBuyerType(int)
	 */
	public void deletePopularServicesByBuyerType(int buyerTypeId)
			throws DataServiceException {
		delete("popularServicesByBuyerType.delete",new Integer(buyerTypeId));
	}

	
	
}
