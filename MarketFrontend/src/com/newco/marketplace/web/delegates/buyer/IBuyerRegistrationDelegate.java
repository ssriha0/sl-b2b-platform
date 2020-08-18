package com.newco.marketplace.web.delegates.buyer;

import java.util.List;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;

/**
 * @author paugus2
 *
 */
public interface IBuyerRegistrationDelegate {
	public BuyerRegistrationDTO loadZipSet(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException; 
	public BuyerRegistrationDTO loadRegistration(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException;
	public BuyerRegistrationDTO saveBuyerRegistration(BuyerRegistrationDTO buyerRegistrationDTO)throws DelegateException,DuplicateUserException;
	public boolean updateBuyerCompanyProfile(BuyerRegistrationDTO buyerRegistrationDTO)throws DelegateException;
	public BuyerRegistrationDTO loadListData(BuyerRegistrationDTO buyerRegistrationDTO,Integer buyerId) throws DelegateException;
	public BuyerRegistrationDTO loadListData(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException;
	public BuyerRegistrationDTO loadData(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException;
	public boolean isBlackoutState(String stateCode) throws DelegateException;
	public void saveBuyerLead(BuyerRegistrationDTO buyerRegistrationDTO) throws DelegateException;
	public List<String> getBlackoutStates() throws DelegateException;
}