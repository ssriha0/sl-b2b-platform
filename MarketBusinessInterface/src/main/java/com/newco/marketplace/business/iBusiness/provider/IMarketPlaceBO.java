/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.vo.provider.MarketPlaceVO;

/**
 * @author Covansys - Offshore
 * 
 */
public interface IMarketPlaceBO {

	public MarketPlaceVO loadMarketPlace(
			MarketPlaceVO providerRegistrationVO,String provUserName)
			throws BusinessServiceException;
	
	public MarketPlaceVO updateMarketPlace(
			MarketPlaceVO providerRegistrationVO)
			throws BusinessServiceException, DuplicateUserException;
	
	public boolean updateMobileNo(String resourceId, String mobileNo)
			throws BusinessServiceException;
	
	//SL-18979: Commenting out code since validating sms no is done through vibes call
	//public boolean validateSmsNo(String number);

}
