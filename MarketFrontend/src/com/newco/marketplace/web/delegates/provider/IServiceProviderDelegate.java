/**
 * 
 */
package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.ServiceProviderRegistrationDto;

/**
 * @author MTedder
 *
 */
public interface IServiceProviderDelegate {

		public void saveServiceProviderRegistration(ServiceProviderRegistrationDto serviceProviderRegistrationDto) throws DelegateException;
		//MTedder@covansys.com.com
		public ServiceProviderRegistrationDto queryEmailForServiceProvider(ServiceProviderRegistrationDto serviceProviderRegistrationDto) throws DelegateException;
}
