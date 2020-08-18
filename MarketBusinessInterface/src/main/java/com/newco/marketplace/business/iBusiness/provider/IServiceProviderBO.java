/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.ServiceProviderRegistrationVO;

/**
 * @author KSudhanshu
 *
 */
public interface IServiceProviderBO {

	public ServiceProviderRegistrationVO queryEmailForServiceProvider(ServiceProviderRegistrationVO serviceProviderRegistrationVO) throws BusinessServiceException;
	public void saveServiceProviderRegistration(ServiceProviderRegistrationVO serviceProviderRegistrationVO) throws BusinessServiceException;
	public void updateVendorInfoCache() throws BusinessServiceException;
	
	/**
     * Update vendor KPI
     */
	public void updateVendorKPI() throws BusinessServiceException;
}
