package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO;
import com.newco.marketplace.business.iBusiness.sitestatistics.ISiteStatisticsBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.sitestatistics.SiteStatisticsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.web.delegates.IHomepageDelegate;

/**
 * @author nsanzer
 *
 */
public class HomepageDelegateImpl implements IHomepageDelegate {

	private static final Logger logger = Logger.getLogger(HomepageDelegateImpl.class
			.getName());
	IGeneralInfoBO generalInfoBO;
	ISiteStatisticsBO siteStatisticsBO;
	IBuyerRegistrationBO buyerRegistrationBO;

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IHomepageDelegate#getPopularProServices()
	 */
	public List<PopularServicesVO> getPopularProServices()
	{
		
		List<PopularServicesVO> popularProServicesVOs = new ArrayList<PopularServicesVO>();
		try {
			popularProServicesVOs = siteStatisticsBO.getPopularServicesByBuyerType(Constants.BUYER_TYPE.PRO);
		} catch (BusinessServiceException e) {
			logger.debug("Exception thrown retrieving Popular Pro Services", e);
			e.printStackTrace();
		}
		return popularProServicesVOs;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IHomepageDelegate#getPopularSimpleServices()
	 */
	public List<PopularServicesVO> getPopularSimpleServices()
	{
		List<PopularServicesVO> popularSimpleServicesVOs = new ArrayList<PopularServicesVO>();
		try {
			popularSimpleServicesVOs = siteStatisticsBO.getPopularServicesByBuyerType(Constants.BUYER_TYPE.SIMPLE);
		} catch (BusinessServiceException e) {
			logger.debug("Exception thrown retrieving Popular Pro Services", e);
			e.printStackTrace();
		}
	
		return popularSimpleServicesVOs;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IHomepageDelegate#getBlackoutStates()
	 */
	public List<String> getBlackoutStates() {
		
		List<String> blackoutStates = new ArrayList<String>();
		try {
			blackoutStates = buyerRegistrationBO.getBlackoutStates();
		} catch(BusinessServiceException e) {
			logger.debug("Exception thrown retrieving Blackout States", e);
			e.printStackTrace();
		}
		
		return blackoutStates;
	}

	
	public SiteStatisticsVO getSiteStatistics() throws BusinessServiceException{
		return siteStatisticsBO.getSiteStatistics();
	}


	public boolean zipExists(String zip) throws BusinessServiceException {
		return generalInfoBO.zipExists(zip);
	}
	public IGeneralInfoBO getGeneralInfoBO() {
		return generalInfoBO;
	}
	public void setGeneralInfoBO(IGeneralInfoBO generalInfoBO) {
		this.generalInfoBO = generalInfoBO;
	}
	public ISiteStatisticsBO getSiteStatisticsBO() {
		return siteStatisticsBO;
	}
	public void setSiteStatisticsBO(ISiteStatisticsBO siteStatisticsBO) {
		this.siteStatisticsBO = siteStatisticsBO;
	}


	public IBuyerRegistrationBO getBuyerRegistrationBO() {
		return buyerRegistrationBO;
	}


	public void setBuyerRegistrationBO(IBuyerRegistrationBO buyerRegistrationBO) {
		this.buyerRegistrationBO = buyerRegistrationBO;
	}

}
