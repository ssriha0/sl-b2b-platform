package com.newco.marketplace.web.delegates;

import java.util.List;

import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO;
import com.newco.marketplace.exception.BusinessServiceException;

/**
 * 
 * @author Chris Carlevato
 *
 * $Revision: 1.5 $ $Author: groma $ $Date: 2008/05/23 00:56:04 $
 */
public interface IHomepageDelegate {

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public SiteStatisticsVO getSiteStatistics() throws BusinessServiceException;
	/**
	 * @param zip
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean zipExists(String zip) throws BusinessServiceException;	
	
	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PopularServicesVO> getPopularProServices() throws BusinessServiceException;
	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PopularServicesVO> getPopularSimpleServices() throws BusinessServiceException;
	
	/**
	 * @return
	 * @throws BusinessServiceException
	 */	
	public List<String> getBlackoutStates() throws BusinessServiceException;
}
