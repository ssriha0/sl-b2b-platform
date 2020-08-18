package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.CompanyProfileDto;


/**
 * @author LENOVO USER
 *
 */
public interface ICompanyProfileDelegate { 
	
	public CompanyProfileDto getCompleteProfile(int vendorId) throws DelegateException;
}
