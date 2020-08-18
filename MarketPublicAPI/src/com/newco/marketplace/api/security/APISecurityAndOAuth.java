package com.newco.marketplace.api.security;


import java.util.ArrayList;
import java.util.List;
import net.oauth.consumer.IOAuthConsumer;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.api.APISecurityImpl;
import com.newco.marketplace.business.iBusiness.api.IAPISecurity;
import com.newco.marketplace.dto.api.APIApplicationDTO;
import com.newco.marketplace.dto.api.APIGroup;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.daoImpl.admin.APISecurityDAOImpl;
import com.newco.marketplace.persistence.iDao.admin.APISecurityDAO;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.vo.api.APISecurityVO;
import com.sears.os.business.ABaseBO;

/**
 * $Revision: 1.0 $ $Author: pgangrade
 *
 */
public class APISecurityAndOAuth extends APISecurityImpl implements IOAuthConsumer {
	private static final Logger logger = Logger
			.getLogger(APISecurityAndOAuth.class.getName());

	private String oAuthFilter="on";
	private String basicAuthFilter="on";


	public APISecurityAndOAuth(APISecurityDAO apiSecurityDAO) {
		super(apiSecurityDAO);
	}


	/**
	 * Checks whether OAuth is enabled.
	 * By default it is enabled. To disable it put following flag in jboss/conf local.properties
	 * oauth.authentication=off
	 */
	public boolean isOAuthEnabled() {
		if (oAuthFilter.equalsIgnoreCase("off") || oAuthFilter.equalsIgnoreCase("false"))
			return false;
		return true;
	}

	public boolean isSecurityEnabled() {
		return isOAuthEnabled();
	}

	
	
	public boolean isBasicAuthEnabled() {
		if (basicAuthFilter.equalsIgnoreCase("off") || basicAuthFilter.equalsIgnoreCase("false"))
			return false;
		return true;
	}

	

	/**
	 * This method read consumer secret from DB again consumer key received in HTTP request.
	 * It reads it from supplier.oauth_consumer table.
	 * It also uses read only cache in 10 minutes interval.
	 *
	 */
	public String getConsumerSecret(String consumerKey) {
		APIApplicationDTO dto = getApplication(consumerKey);
		if (dto != null)
			return dto.getConsumerPassword();
		return null;
	}

	public String getoAuthFilter() {
		return oAuthFilter;
	}

	public void setoAuthFilter(String oAuthFilter) {
		this.oAuthFilter = oAuthFilter;
	}


	public String getBasicAuthFilter() {
		return basicAuthFilter;
	}


	public void setBasicAuthFilter(String basicAuthFilter) {
		this.basicAuthFilter = basicAuthFilter;
	}

	
	
}
