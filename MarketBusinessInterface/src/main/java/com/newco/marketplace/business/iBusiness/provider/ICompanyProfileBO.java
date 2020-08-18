/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.CompanyProfileVO;


/**
 * @author LENOVO USER
 * 
 */
public interface ICompanyProfileBO {

	
	public CompanyProfileVO getCompleteProfile(int vendorId) throws BusinessServiceException;
	
}
