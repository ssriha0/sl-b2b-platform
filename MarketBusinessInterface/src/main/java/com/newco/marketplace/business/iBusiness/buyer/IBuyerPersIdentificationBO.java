package com.newco.marketplace.business.iBusiness.buyer;

import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.buyer.PersonalIdentificationVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;

public interface IBuyerPersIdentificationBO {
	/**
	   * Saves the personal identification information - EIN in the PII history for the specified buyer ID
	   * 
	   * @param ein
	   * @param buyerId
	   * @param businessName
	   * @param userName
	   * @throws BusinessServiceException
	   */
	public void saveBuyerEIN(Integer buyerId, String ein, String businessName, String userName) throws BusinessServiceException;
	
	/**
	   * Saves the personal identification information - SSN in the PII history for the specified buyer ID
	   * 
	   * @param ssn
	   * @param buyerId
	   * @param businessName
	   * @param userName
	   * @throws BusinessServiceException
	   */
	public void saveBuyerSSN(Integer buyerId, String ssn, String dob, String businessName, String userName) throws BusinessServiceException;
	
	/**
	   * Saves the personal identification information - Alternate tax id in the PII history for the specified buyer ID
	   * 
	   * @param buyerVO 
	   * @throws BusinessServiceException
	   */
	public void saveBuyerAltId(Buyer buyerVO) throws BusinessServiceException;
	
	/**
	   * Fetches the personal identification information from buyer table for the view and edit buyer PII 
	   * 
	   * @param buyerId 
	   * @throws BusinessServiceException
	   */
	public Buyer getBuyerPIIData(Integer buyerId) throws BusinessServiceException;
	
	public List<PersonalIdentificationVO> getPiiHistory(Integer buyerId)throws DataServiceException;
	
}
