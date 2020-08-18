package com.newco.marketplace.api.utils.mappers.account.Provider;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountRequest;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountResponse;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;

public class ProviderAccountMapper {
	
	private static final Logger logger = Logger
			.getLogger(ProviderAccountMapper.class);	
	/**
	 * This method is for creating Provider account.
	 * Code created by 643272 date 03-09-2013
	 */
	public static ProviderRegistrationVO getVOForCreateProviderAccount(
			CreateProviderAccountRequest request) throws DataException {
		
		ProviderRegistrationVO vo = new ProviderRegistrationVO();
		
		vo.setLegalBusinessName(request.getLegalBusinessName());
		vo.setDBAName(request.getDBAName());
		vo.setPrimaryIndustry(request.getPrimaryIndustry());
		vo.setWebsiteAddress(request.getWebsiteAddress());
		
		vo.setMainBusinessExtn(request.getMainBusinessExtn());
		vo.setBusinessFax1(request.getBusinessFax1());
		vo.setBusinessStreet1(request.getBusinessStreet1());
		vo.setBusinessStreet2(request.getBusinessStreet2());
		vo.setBusinessCity(request.getBusinessCity());
		vo.setBusinessState(request.getBusinessState());
		vo.setBusinessZip(request.getBusinessZip());
		vo.setBusinessAprt(request.getBusinessAprt());
		vo.setMailingStreet1(request.getMailingStreet1());
		vo.setMailingStreet2(request.getMailingStreet2());
		vo.setMailingCity(request.getMailingCity());
		vo.setMailingState(request.getMailingState());
		vo.setMailingZip(request.getMailingZip());
		vo.setMailingAprt(request.getMailingAprt());
		vo.setHowDidYouHear(request.getHowDidYouHear());
		vo.setPromotionCode(request.getPromotionCode());
		vo.setRoleWithinCom(request.getRoleWithinCom());
		vo.setJobTitle(request.getJobTitle());
		vo.setServiceCall(request.getServiceCallInd());
		
		vo.setEmail(request.getEmail());
		vo.setConfirmEmail(request.getConfirmEmail());
		vo.setAltEmail(request.getAltEmail());
		vo.setAltEmail(request.getAltEmail());
		vo.setConfAltEmail(request.getConfAltEmail());
		vo.setUserName(request.getUserName());
		//setting the variables for contact table
		vo.setFirstName(request.getFirstName());
		vo.setLastName(request.getLastName());
		vo.setMiddleName(request.getMiddleName());
		vo.setNameSuffix(request.getNameSuffix());
		vo.setMainBusiPhoneNo1(request.getMainBusiPhoneNo1());
		//setting an indicator saying registration is happening via API.
		vo.setRegisterProviderUsingAPI(true);
		//SL 16934 Setting the referral code obtained from request  using IPR into provider VO
		vo.setReferralCode(request.getReferralCode());
		
		return vo;
	}

	public static CreateProviderAccountResponse convertVOToPOJOForCreateProviderAccount(
			ProviderRegistrationVO vo) throws DataException {

		CreateProviderAccountResponse response = new CreateProviderAccountResponse();
		response.setVendorId(vo.getVendorId());
		response.setVendorResourceId(vo.getVendorContactResourceId());
		response.setResults(Results.getSuccess());
		return response;
	}
}
