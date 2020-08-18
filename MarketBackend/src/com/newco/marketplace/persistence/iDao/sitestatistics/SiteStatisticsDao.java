package com.newco.marketplace.persistence.iDao.sitestatistics;

import java.util.List;

import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 22:54:28 $
 */
public interface SiteStatisticsDao {

	/**
	 * Returns the data from the site_stats table
	 * @return
	 * @throws DataServiceException
	 */
	public SiteStatisticsVO getSiteStatistics () throws DataServiceException;
	
	/**
	 * Deletes all of the data from the site_stats table
	 * @throws DataServiceException
	 */
	public void deleteSiteStats () throws DataServiceException;
	
	/**
	 * Loads data into the site_stats table
	 * @param data
	 * @throws DataServiceException
	 */
	public void insertSiteStats (SiteStatisticsVO data) throws DataServiceException;
	
	/**
	 * If no results are found, an empty list will be returned
	 * @param buyerTypeId
	 * @return
	 * @throws DataServiceException
	 */
	public List<PopularServicesVO> getPopularServicesByBuyerType(int buyerTypeId)  throws DataServiceException;
	
	
	/**
	 * @param data
	 * @throws DataServiceException
	 */
	public void insertPopularServices (List<PopularServicesVO> data) throws DataServiceException;
	
	/**
	 * @param buyerTypeId
	 * @throws DataServiceException
	 */
	public void deletePopularServicesByBuyerType(int buyerTypeId)  throws DataServiceException;
}
