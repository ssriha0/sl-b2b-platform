package com.newco.marketplace.web.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.vo.simple.CreateServiceOrderCreateAccountVO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderAddFundsDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderCreateAccountDTO;
import com.newco.marketplace.web.dto.simple.CreditCardDTO;

public class CreateServiceOrderCreateAccountMapper extends ObjectMapper{
	
	private static final Logger logger = Logger.getLogger(CreateServiceOrderCreateAccountMapper.class.getName());
	
	public void convertVOtoDTO(CreateServiceOrderCreateAccountDTO accountDTO, CreateServiceOrderCreateAccountVO accountVO) {
		accountDTO.setAptNo(accountVO.getAptNo());
		accountDTO.setBuyerTermsAndConditionAgreeInd(accountVO.getBuyerTermsAndConditionAgreeInd());
		accountDTO.setBuyerTermsAndConditionText(accountVO.getBuyerTermsAndConditionText());
		accountDTO.setCity(accountVO.getCity());
		accountDTO.setEmail(accountVO.getEmail());
		accountDTO.setEmailConfirm(accountVO.getEmailConfirm());
		accountDTO.setFirstName(accountVO.getFirstName());
		accountDTO.setHomeAddressInd(accountVO.getHomeAddressInd());
		accountDTO.setLastName(accountVO.getLastName());
		accountDTO.setLocName(accountVO.getLocName());
		accountDTO.setPrimaryPhone(accountVO.getPrimaryPhone());
		accountDTO.setSecondaryPhone(accountVO.getSecondaryPhone());
		accountDTO.setSlBucksAgreeInd(accountVO.getSlBucksAgreeInd());
		accountDTO.setSlBucksText(accountVO.getSlBucksText());
		accountDTO.setState(accountVO.getState());
		accountDTO.setStatesList(accountVO.getStatesList());
		accountDTO.setStreet1(accountVO.getStreet1());
		accountDTO.setStreet2(accountVO.getStreet2());
		accountDTO.setUsername(accountVO.getUsername());
		accountDTO.setUsernameConfirm(accountVO.getUsernameConfirm());
		accountDTO.setZip(accountVO.getZip());
		accountDTO.setSlBucksAgreeId(accountVO.getSlBucksAgreeId());
	}
	
	public void convertDTOtoVO(CreateServiceOrderCreateAccountVO accountVO, CreateServiceOrderCreateAccountDTO accountDTO) {
		accountVO.setAptNo(accountDTO.getAptNo());
		accountVO.setBuyerTermsAndConditionAgreeInd(accountDTO.getBuyerTermsAndConditionAgreeInd());
		accountVO.setBuyerTermsAndConditionText(accountDTO.getBuyerTermsAndConditionText());
		accountVO.setCity(accountDTO.getCity());
		accountVO.setEmail(accountDTO.getEmail());
		accountVO.setEmailConfirm(accountDTO.getEmailConfirm());
		accountVO.setFirstName(accountDTO.getFirstName());
		accountVO.setHomeAddressInd(accountDTO.getHomeAddressInd());
		accountVO.setLastName(accountDTO.getLastName());
		accountVO.setLocName(accountDTO.getLocName());
		accountVO.setPrimaryPhone(accountDTO.getPrimaryPhone());
		accountVO.setSecondaryPhone(accountDTO.getSecondaryPhone());
		accountVO.setSlBucksAgreeInd(accountDTO.getSlBucksAgreeInd());
		accountVO.setSlBucksText(accountDTO.getSlBucksText());
		accountVO.setState(accountDTO.getState());
		accountVO.setStatesList(accountDTO.getStatesList());
		accountVO.setStreet1(accountDTO.getStreet1());
		accountVO.setStreet2(accountDTO.getStreet2());
		accountVO.setUsername(accountDTO.getUsername());
		accountVO.setUsernameConfirm(accountDTO.getUsernameConfirm());
		accountVO.setZip(accountDTO.getZip());
	}
	
	public static SOWContactLocationDTO mapLocationVOTODto(LocationVO locationVO){
		SOWContactLocationDTO contactLocationDTO = null;
		if(locationVO != null){
			contactLocationDTO = new SOWContactLocationDTO();
		
			contactLocationDTO.setLocationId(locationVO.getLocnId());
			contactLocationDTO.setAptNo(locationVO.getAptNo());
			contactLocationDTO.setCity(locationVO.getCity());
			contactLocationDTO.setState(locationVO.getState());
			contactLocationDTO.setStreetName1(locationVO.getStreet1());
			contactLocationDTO.setStreetName2(locationVO.getStreet2());
			contactLocationDTO.setZip(locationVO.getZip());
			contactLocationDTO.setLocationName(locationVO.getLocName());
		}
		return contactLocationDTO;
	}
	
	public static CreditCardVO buildCreditCardVO(CreateServiceOrderAddFundsDTO accountDTO, Integer buyerId){
		logger.debug("Entering CreateServiceOrderCreateAccountMapper --> buildCreditCardVO");
		CreditCardVO creditCardVO = null;
		
		if(accountDTO != null) {
			creditCardVO = new CreditCardVO();
			creditCardVO.setAccountTypeId(LedgerConstants.CC_ACCOUNT_TYPE);
			//creditCardVO.setAddress(accountDTO.getBillingStreet1()+" "+accountDTO.getBillingStreet2());
			
			//if(accountDTO.getCheckboxSaveThisCard()){
			//	creditCardVO.setActive_ind(Boolean.TRUE);
			//}else{
			//	creditCardVO.setActive_ind(Boolean.FALSE);
			//}
			
			if(accountDTO.getUseExistingCard() 
					&& accountDTO.getExistingCreditCard() != null) {
				creditCardVO.setCardId(accountDTO.getExistingCreditCard().getCardId());
				creditCardVO.setBillingAddress1(accountDTO.getExistingCreditCard().getBillingAddress1());
				creditCardVO.setBillingAddress2(accountDTO.getExistingCreditCard().getBillingAddress2());
				creditCardVO.setBillingAptNo(accountDTO.getExistingCreditCard().getBillingApartmentNumber());
				creditCardVO.setBillingCity(accountDTO.getExistingCreditCard().getBillingCity());
				creditCardVO.setBillingState(accountDTO.getExistingCreditCard().getBillingState());
				creditCardVO.setZipcode(accountDTO.getExistingCreditCard().getBillingZipCode());
				creditCardVO.setCardVerificationNo(accountDTO.getExistingCardSecurityCode());
				creditCardVO.setActive_ind(Boolean.TRUE);
			} else if(accountDTO.getNewCreditCard() != null) {
				
				creditCardVO.setCardHolderName(accountDTO.getNewCreditCard().getCreditCardHolderName());
				creditCardVO.setCardNo(accountDTO.getNewCreditCard().getCreditCardNumber());
				creditCardVO.setCardVerificationNo(accountDTO.getNewCreditCard().getSecurityCode());
				
				if(StringUtils.isNotBlank(accountDTO.getNewCreditCard().getExpirationMonth())
						&& StringUtils.isNotBlank(accountDTO.getNewCreditCard().getExpirationYear())) {
					creditCardVO.setExpireDate(accountDTO.getNewCreditCard().getExpirationYear()+accountDTO.getNewCreditCard().getExpirationMonth());
				}
				
				if(StringUtils.isNotBlank(accountDTO.getNewCreditCard().getCreditCardType())) {
					creditCardVO.setCardTypeId(Long.parseLong(accountDTO.getNewCreditCard().getCreditCardType()));
				}
				
				if(accountDTO.getCheckboxSaveThisCard()) {
					creditCardVO.setActive_ind(Boolean.TRUE);
				} else {
					creditCardVO.setActive_ind(Boolean.FALSE);
				}
				
				creditCardVO.setBillingAddress1(accountDTO.getNewCreditCard().getBillingAddress1());
				creditCardVO.setBillingAddress2(accountDTO.getNewCreditCard().getBillingAddress2());
				creditCardVO.setBillingAptNo(accountDTO.getNewCreditCard().getBillingApartmentNumber());
				creditCardVO.setBillingCity(accountDTO.getNewCreditCard().getBillingCity());
				creditCardVO.setBillingState(accountDTO.getNewCreditCard().getBillingState());
				creditCardVO.setZipcode(accountDTO.getNewCreditCard().getBillingZipCode());
					
			}
			
			creditCardVO.setEnabled_ind(Boolean.TRUE);
			creditCardVO.setAccountStatusId(CreditCardConstants.ACTIVE_ACCOUNT_STATUS);
			
			creditCardVO.setCountryId(Constants.LocationConstants.COUNTRY_US);
			creditCardVO.setEntityId(buyerId);
			creditCardVO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
			
			creditCardVO.setLocationTypeId(Constants.LocationConstants.LOCATION_TYPE_BILLING);
			creditCardVO.setTransactionAmount(accountDTO.getTransactionAmount());
			
		}
		logger.debug("Leaving CreateServiceOrderCreateAccountMapper --> buildCreditCardVO");
		return creditCardVO;
	}
	
	public static CreditCardVO buildExistingCreditCardVO(CreateServiceOrderAddFundsDTO accountDTO,  Integer buyerId) {
		
		logger.debug("Entering CreateServiceOrderCreateAccountMapper --> buildExistingCreditCardVO");
		CreditCardVO creditCardVO = null; 
		
		if(accountDTO != null && accountDTO.getExistingCreditCard() != null) {
			creditCardVO = new CreditCardVO();
			creditCardVO.setAccountTypeId(LedgerConstants.CC_ACCOUNT_TYPE);

			creditCardVO.setCardId(accountDTO.getExistingCreditCard().getCardId());
			creditCardVO.setBillingAddress1(accountDTO.getExistingCreditCard().getBillingAddress1());
			creditCardVO.setBillingAddress2(accountDTO.getExistingCreditCard().getBillingAddress2());
			creditCardVO.setBillingAptNo(accountDTO.getExistingCreditCard().getBillingApartmentNumber());
			creditCardVO.setBillingCity(accountDTO.getExistingCreditCard().getBillingCity());
			creditCardVO.setBillingState(accountDTO.getExistingCreditCard().getBillingState());
			creditCardVO.setZipcode(accountDTO.getExistingCreditCard().getBillingZipCode());
			creditCardVO.setCardVerificationNo(accountDTO.getExistingCardSecurityCode());
			
			creditCardVO.setActive_ind(Boolean.TRUE);
			creditCardVO.setEnabled_ind(Boolean.TRUE);
			creditCardVO.setAccountStatusId(CreditCardConstants.ACTIVE_ACCOUNT_STATUS);
			
			creditCardVO.setCountryId(Constants.LocationConstants.COUNTRY_US);
			creditCardVO.setEntityId(buyerId);
			creditCardVO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
			
			creditCardVO.setLocationTypeId(Constants.LocationConstants.LOCATION_TYPE_BILLING);
			creditCardVO.setTransactionAmount(accountDTO.getTransactionAmount());
			
		}
		logger.debug("Leaving CreateServiceOrderCreateAccountMapper --> buildExistingCreditCardVO");
		return creditCardVO;
	}
	
	public static CreditCardDTO mapCreditCardVoToDTO(CreditCardVO creditCardVO){
		CreditCardDTO creditCardDTO = null;
		
		if(creditCardVO != null){
			creditCardDTO = new CreditCardDTO();
			
			creditCardDTO.setCardId(creditCardVO.getCardId());
			creditCardDTO.setCreditCardHolderName(creditCardVO.getCardHolderName());
			//creditCardDTO.setCreditCardName(creditCardVO);
			if(creditCardVO.getCardNo() != null ){
				if(creditCardVO.getCardNo().length() > 5){
					creditCardDTO.setCreditCardNumber(ServiceLiveStringUtils.maskString(creditCardVO.getCardNo() , 4, "*"));
				}
				else{
					creditCardDTO.setCreditCardNumber(ServiceLiveStringUtils.maskString(creditCardVO.getCardNo(), (creditCardVO.getCardNo().length()/2), "*"));
				}
			}
			if(creditCardVO.getCardTypeId() != null){
				creditCardDTO.setCreditCardType(creditCardVO.getCardTypeId().toString());
			}
			String expDate = creditCardVO.getExpireDate();
			if(StringUtils.isNotBlank(expDate)
					&& expDate.length() == 4){
				creditCardDTO.setExpirationYear(expDate.substring(0, 2));
				creditCardDTO.setExpirationMonth(expDate.substring(2, 4));				
			}			
			creditCardDTO.setSecurityCode(creditCardVO.getCardVerificationNo());
			
			creditCardDTO.setBillingAddress1(creditCardVO.getBillingAddress1());
			creditCardDTO.setBillingAddress2(creditCardVO.getBillingAddress2());
			creditCardDTO.setBillingApartmentNumber(creditCardVO.getBillingAptNo());
			creditCardDTO.setBillingCity(creditCardVO.getBillingCity());
			creditCardDTO.setBillingState(creditCardVO.getBillingState());
			creditCardDTO.setBillingZipCode(creditCardVO.getZipcode());
			
		}
		return creditCardDTO;
	}
}
