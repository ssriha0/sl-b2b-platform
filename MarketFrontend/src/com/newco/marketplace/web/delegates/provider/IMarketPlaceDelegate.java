package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.web.dto.provider.MarketPlaceDTO;


/**
 * @author Covansys - Offshore
 *
 */
public interface IMarketPlaceDelegate {
	public MarketPlaceDTO loadMarketPlace(MarketPlaceDTO marketPlaceDTO, String provUserName) throws DelegateException;
	public MarketPlaceDTO updateMarketPlace(MarketPlaceDTO marketPlaceDTO)throws DelegateException, DuplicateUserException;
	//R16_1: SL-18979: Commenting out code since validating sms no is done through vibes call
	//public boolean validateSmsNo(MarketPlaceDTO marketPlaceDTO);

}
