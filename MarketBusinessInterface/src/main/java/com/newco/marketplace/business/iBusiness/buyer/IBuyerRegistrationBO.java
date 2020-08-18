package com.newco.marketplace.business.iBusiness.buyer;

import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.simple.CreateServiceOrderCreateAccountVO;

/**
 * @author paugus2
 * 
 */
public interface IBuyerRegistrationBO{

	public BuyerRegistrationVO loadRegistration(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException;

	public BuyerRegistrationVO saveSimpleBuyerRegistration(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException,DuplicateUserException;

	public BuyerRegistrationVO saveProfBuyerRegistration(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException,DuplicateUserException;

	public BuyerRegistrationVO saveBuyerRegistration(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException,DuplicateUserException;
	
	public BuyerRegistrationVO loadZipSet(
			BuyerRegistrationVO buyerRegistrationVO) 
			throws BusinessServiceException;
	public BuyerRegistrationVO loadData(BuyerRegistrationVO buyerRegistrationVO)	throws BusinessServiceException;
	public BuyerRegistrationVO loadListData(BuyerRegistrationVO buyerRegistrationVO,Integer buyerId)	throws BusinessServiceException;
	public BuyerRegistrationVO loadListData(BuyerRegistrationVO buyerRegistrationVO)	throws BusinessServiceException;
	public boolean updateBuyerCompanyProfile(BuyerRegistrationVO buyerRegistrationVO) throws BusinessServiceException;
	public void loadAccount(CreateServiceOrderCreateAccountVO accountVO) throws BusinessServiceException;
	public List<LocationVO> loadLocations(String stateCode) throws BusinessServiceException;
	public boolean checkBlackoutState(String stateCd) throws BusinessServiceException;
	public void SaveBlackoutBuyerLead(BuyerRegistrationVO buyerRegistrationVO) throws BusinessServiceException;
	public List<String> getBlackoutStates() throws BusinessServiceException;
	public BuyerRegistrationVO updateSimpleBuyerInformation (
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException;
	
}
