package com.servicelive.spn.common.util;

import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.spn.dao.common.ApplicationPropertiesDao;


/**
 * 
 * @author svanloon
 *
 */
public class PropertyManagerUtil {

	private static final Logger logger = Logger.getLogger(PropertyManagerUtil.class);
	private static final String KEY_SPN_AUDITOR_MONITOR_STICKY_QUEUE_TIMEOUT_MIN = "spn.auditorMonitor.stickyQueue.timeOut.min";
	private static final String SPN_TIERED_ROUTING_MIN_DELAY_MINUTES = "spn.auditor.tieredRouting.minDelayMinutes";
	private static final String DEFAULT_SPN_TIERED_ROUTING_MIN_DELAY_MINUTES = "1";
	private static final String DEFAULT_SPN_AUDITOR_MONITOR_STICKY_QUEUE_TIMEOUT_MIN = "30";
	private static final String KEY_CHEETAH_TEST_MODE = "cheetah.test.mode";
	private static final String DEFAULT_IS_TEST_MAIL = Boolean.FALSE.toString();
	private static final String KEY_SERVICELIVE_URL = "servicelive_url";
	private static final String DEFAULT_SERVICELIVE_URL = "www.servicelive.com/MarketFrontend";
	private static final Integer DEFAULT_SPN_MM_SEARCH_RESULT_MAX = Integer.valueOf(30);
	private ResourceBundleMessageSource spnProperties;
	private ApplicationPropertiesDao spnApplicationPropertiesDao;

	/**
	 * 
	 * @return int in minutes
	 */
	public int getSpnAuditorMonitorStickyQueueTimeoutMinutes() {
		String valueStr = spnProperties.getMessage(KEY_SPN_AUDITOR_MONITOR_STICKY_QUEUE_TIMEOUT_MIN, null, DEFAULT_SPN_AUDITOR_MONITOR_STICKY_QUEUE_TIMEOUT_MIN, Locale.getDefault());
		valueStr = valueStr.trim();
		return Integer.parseInt(valueStr);
	}

	/**
	 * 
	 * @return String
	 */
	public String getServiceLiveUrl() {
		try {
			ApplicationProperties applicationProperty = spnApplicationPropertiesDao.findById(KEY_SERVICELIVE_URL);
			if(applicationProperty == null) {
				return DEFAULT_SERVICELIVE_URL;
			}

			return applicationProperty.getValue();
		} catch (Exception e) {
			logger.info("Couldn't find servicelive_url in the application_properties table");
			return DEFAULT_SERVICELIVE_URL;
		}
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isTestMail() {
		String valueStr = spnProperties.getMessage(KEY_CHEETAH_TEST_MODE, null, DEFAULT_IS_TEST_MAIL, Locale.getDefault());
		valueStr = valueStr.trim();
		return Boolean.parseBoolean(valueStr);
	}

	public int getSpnTieredRoutingMinDelayMinutes() {
		String valueStr = spnProperties.getMessage(SPN_TIERED_ROUTING_MIN_DELAY_MINUTES, null, DEFAULT_SPN_TIERED_ROUTING_MIN_DELAY_MINUTES, Locale.getDefault());
		valueStr = valueStr.trim();
		return Integer.parseInt(valueStr);
	}
	
	public Integer getMemberManageSearchMaxResultCount() {
		try {
			ApplicationProperties applicationProperty  = spnApplicationPropertiesDao.findById("spn.max.membermanage.search.result");
			if(applicationProperty == null) {
				return DEFAULT_SPN_MM_SEARCH_RESULT_MAX;
			}

			return Integer.valueOf(applicationProperty.getValue());
		} catch (NumberFormatException e) {
			logger.info("Couldn't find *spn.max.membermanage.search.result* in the application_properties table");
			return DEFAULT_SPN_MM_SEARCH_RESULT_MAX;
		} catch (Exception e) {
			logger.info("Couldn't find *spn.max.membermanage.search.result*  in the application_properties table");
			return DEFAULT_SPN_MM_SEARCH_RESULT_MAX;
		}
	}
	
	
	/**
	 * @param spnProperties the spnProperties to set
	 */
	public void setSpnProperties(ResourceBundleMessageSource spnProperties) {
		this.spnProperties = spnProperties;
	}

	/**
	 * @param applicationPropertiesDao the applicationPropertiesDao to set
	 */
	public void setSpnApplicationPropertiesDao(ApplicationPropertiesDao spnApplicationPropertiesDao) {
		this.spnApplicationPropertiesDao = spnApplicationPropertiesDao;
	}
}
