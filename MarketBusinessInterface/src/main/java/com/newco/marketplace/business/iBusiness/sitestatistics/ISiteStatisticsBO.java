package com.newco.marketplace.business.iBusiness.sitestatistics;

import java.util.List;

import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO;
import com.newco.marketplace.exception.BusinessServiceException;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */
public interface ISiteStatisticsBO {

	/**
	 * First removes all existing site statistics data and then loads new data
	 * that was passed in
	 * @param data
	 * @throws BusinessServiceException
	 */
	public void loadSiteStats(SiteStatisticsVO data) throws BusinessServiceException;
	
	/**
	 * Returns current site statistics
	 * @return
	 * @throws BusinessServiceException
	 */
	public SiteStatisticsVO getSiteStatistics() throws BusinessServiceException;
	
	/**
	 * Deletes all site statistics data
	 * @throws BusinessServiceException
	 */
	public void deleteAllSiteStatistics() throws BusinessServiceException;
	
	/**
	 * @param buyerTypeId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PopularServicesVO> getPopularServicesByBuyerType(int buyerTypeId) throws BusinessServiceException;
	
	/**
	 * Removes any data that currently exists for buyer type and load data passed in
	 * @param buyerTypeId
	 * @param data
	 * @throws BusinessServiceException
	 */
	public void loadPopularServices(int buyerTypeId,List<PopularServicesVO> data) throws BusinessServiceException;
}
