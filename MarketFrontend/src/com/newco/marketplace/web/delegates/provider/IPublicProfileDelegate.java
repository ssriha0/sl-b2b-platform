package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.PublicProfileDto;


/**
 * @author LENOVO USER
 *
 */
public interface IPublicProfileDelegate { 
	
	public PublicProfileDto getPublicProfile(PublicProfileDto publicProfileDto) throws DelegateException;
	public boolean checkVendorResource(String resourceId, String vendorId) throws DelegateException;
}
