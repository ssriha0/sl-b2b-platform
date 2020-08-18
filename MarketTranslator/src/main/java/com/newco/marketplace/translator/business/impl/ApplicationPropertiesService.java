package com.newco.marketplace.translator.business.impl;

import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.dao.IApplicationPropertiesDAO;
import com.newco.marketplace.translator.util.Constants;

public class ApplicationPropertiesService implements IApplicationPropertiesService {
	
	private IApplicationPropertiesDAO applicationPropertiesDAO;
	private static final Logger logger = Logger.getLogger(ApplicationPropertiesService.class);
	
	public String getServiceLiveEndpoint() {
		String slEndpoint = getApplicationPropertiesDAO().findByAppKey(Constants.ApplicationPropertiesConstants.SL_ENDPOINT_KEY).getAppValue();
		return slEndpoint;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IApplicationPropertiesService#getStagingWebServiceEndPoint()
	 */
	public String getStagingWebServiceEndPoint() {
		String stagingWebServiceEndpoint = getApplicationPropertiesDAO().findByAppKey(Constants.ApplicationPropertiesConstants.STAGING_ENDPOINT_KEY).getAppValue();
		return stagingWebServiceEndpoint;
	}
	
	public Integer getBuyerId(String buyerIdKey) {
		Integer buyerId = Integer.parseInt(getApplicationPropertiesDAO().findByAppKey(Constants.ApplicationPropertiesConstants.HSR_BUYER_ID).getAppValue());
		return buyerId;
	}	
	
	public int getNPSOrdersToProcess(String npsNumOrdersToCloseKey) {
		int npsNumOrdersToClose= Integer.parseInt(getApplicationPropertiesDAO().findByAppKey(Constants.ApplicationPropertiesConstants.NPS_NUM_ORDERS_TO_CLOSE).getAppValue());
		return npsNumOrdersToClose;
	}
	
	public String getRolloutUnits() {
		try {
			String rolloutStores = getApplicationPropertiesDAO().findByAppKey(Constants.ApplicationPropertiesConstants.OF_ROLLOUT_UNITS).getAppValue();
			return rolloutStores;
		} catch (RuntimeException re) {
			logger.info("Returning default value 'all' for application property 'orderfulfillment_rollout_stores'");
			return "all";
		}
		
	}
	
	public Boolean getUseNewOFProcess() {
		return returnAsBoolean(Constants.ApplicationPropertiesConstants.USE_NEW_OF_PROCESS);
	}
	
	public Boolean getOFTestMode() {
		try {
			return returnAsBoolean(Constants.ApplicationPropertiesConstants.OF_TEST_MODE);
		} catch (RuntimeException re) {
			logger.info("Returning default value FALSE for application property 'orderfulfillment_test_mode'");
			return false;
		}
	}
	
	public Boolean getMaintainLegacyStagingData() {
		try {
			return returnAsBoolean(Constants.ApplicationPropertiesConstants.MAINTAIN_LEGACY_STAGING_DATA);
		} catch (RuntimeException re) {
			logger.info("Returning default value FALSE for application property 'maintain_legacy_staging_data'");
			return false;
		}
	}
	
	private Boolean returnAsBoolean(String applicationPropertyName) {
		String stringValue = getApplicationPropertiesDAO().findByAppKey(applicationPropertyName).getAppValue();
		return stringValue != null && (stringValue.equals("1") || stringValue.equalsIgnoreCase("yes") || stringValue.equalsIgnoreCase("true"));
	}
	
	public IApplicationPropertiesDAO getApplicationPropertiesDAO() {
		return applicationPropertiesDAO;
	}

	public void setApplicationPropertiesDAO(IApplicationPropertiesDAO applicationPropertiesDAO) {
		this.applicationPropertiesDAO = applicationPropertiesDAO;
	}	

}
