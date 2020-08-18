/**
 * 
 */
package com.newco.marketplace.web.utils;


import org.apache.log4j.Logger;

import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
import com.newco.marketplace.web.dto.provider.ProviderRegistrationDto;

/**
 * @author LENOVO USER
 * 
 */
public class ProviderRegistrationMapper extends ObjectMapper {
	private static final Logger logger = Logger
	.getLogger(ProviderRegistrationMapper.class.getName());
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public ProviderRegistrationVO convertDTOtoVO(Object objectDto, Object objectVO) {
		ProviderRegistrationDto providerRegistrationDto = (ProviderRegistrationDto) objectDto;
		ProviderRegistrationVO providerRegistrationVO = (ProviderRegistrationVO) objectVO;
		if (objectDto != null && objectVO!=null) {
			
			
			providerRegistrationVO.setLegalBusinessName(providerRegistrationDto.getLegalBusinessName());
			providerRegistrationVO.setDBAName(providerRegistrationDto.getDBAName());
			providerRegistrationVO.setWebsiteAddress(providerRegistrationDto.getWebsiteAddress());
			providerRegistrationVO.setMainBusiPhoneNo1(providerRegistrationDto.getMainBusiPhoneNo1());
			providerRegistrationVO.setMainBusiPhoneNo2(providerRegistrationDto.getMainBusiPhoneNo2());
			providerRegistrationVO.setMainBusiPhoneNo3(providerRegistrationDto.getMainBusiPhoneNo3());
			providerRegistrationVO.setMainBusinessExtn(providerRegistrationDto.getMainBusinessExtn());
			providerRegistrationVO.setBusinessFax1(providerRegistrationDto.getBusinessFax1());
			providerRegistrationVO.setBusinessFax2(providerRegistrationDto.getBusinessFax2());
			providerRegistrationVO.setBusinessFax3(providerRegistrationDto.getBusinessFax3());
			providerRegistrationVO.setPrimaryIndustry(providerRegistrationDto.getPrimaryIndustry());
			providerRegistrationVO.setOtherPrimaryService(providerRegistrationDto.getOtherPrimaryService());
			providerRegistrationVO.setBusinessStreet1(providerRegistrationDto.getBusinessStreet1());
			providerRegistrationVO.setBusinessStreet2(providerRegistrationDto.getBusinessStreet2());
			providerRegistrationVO.setBusinessCity(providerRegistrationDto.getBusinessCity());
			providerRegistrationVO.setBusinessState(providerRegistrationDto.getBusinessState());
			providerRegistrationVO.setBusinessZip(providerRegistrationDto.getBusinessZip());
			providerRegistrationVO.setBusinessAprt(providerRegistrationDto.getBusinessAprt());
			providerRegistrationVO.setMailAddressChk(providerRegistrationDto.getMailAddressChk());
			providerRegistrationVO.setMailingStreet1(providerRegistrationDto.getMailingStreet1());
			providerRegistrationVO.setMailingStreet2(providerRegistrationDto.getMailingStreet2());
			providerRegistrationVO.setMailingCity(providerRegistrationDto.getMailingCity());
			providerRegistrationVO.setMailingState(providerRegistrationDto.getMailingState());
			providerRegistrationVO.setMailingZip(providerRegistrationDto.getMailingZip());
			
			providerRegistrationVO.setMailingAprt(providerRegistrationDto.getMailingAprt());
			providerRegistrationVO.setHowDidYouHear(providerRegistrationDto.getHowDidYouHear());
			providerRegistrationVO.setPromotionCode(providerRegistrationDto.getPromotionCode());
			providerRegistrationVO.setRoleWithinCom(providerRegistrationDto.getRoleWithinCom());
			providerRegistrationVO.setJobTitle(providerRegistrationDto.getJobTitle());
			providerRegistrationVO.setServiceCall(providerRegistrationDto.getServiceCall());
			providerRegistrationVO.setFirstName(providerRegistrationDto.getFirstName());
			providerRegistrationVO.setMiddleName(providerRegistrationDto.getMiddleName());
			providerRegistrationVO.setLastName(providerRegistrationDto.getLastName());
			
			providerRegistrationVO.setNameSuffix(providerRegistrationDto.getNameSuffix());
			providerRegistrationVO.setEmail(providerRegistrationDto.getEmail());
			providerRegistrationVO.setConfirmEmail(providerRegistrationDto.getConfirmEmail());
			providerRegistrationVO.setAltEmail(providerRegistrationDto.getAltEmail());
			providerRegistrationVO.setConfAltEmail(providerRegistrationDto.getConfAltEmail());
			providerRegistrationVO.setUserName(providerRegistrationDto.getUserName());
			providerRegistrationVO.setPrimaryIndList(providerRegistrationDto.getPrimaryIndList());
			providerRegistrationVO.setRoleWithinCompany(providerRegistrationDto.getRoleWithinCompany());
			providerRegistrationVO.setHowDidYouHearList(providerRegistrationDto.getHowDidYouHearList());
			providerRegistrationVO.setVendorId(providerRegistrationDto.getVendorId());
			providerRegistrationVO.setVendorContactResourceId(providerRegistrationDto.getVendorContactResourceId());
			providerRegistrationVO.setPassword(providerRegistrationDto.getPassword());
			providerRegistrationVO.setStateType(providerRegistrationDto.getStateType());


			
		}
		return providerRegistrationVO;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public ProviderRegistrationDto convertVOtoDTO(Object objectVO, Object objectDto) {
		ProviderRegistrationVO providerRegistrationVO = (ProviderRegistrationVO) objectVO;
		ProviderRegistrationDto providerRegistrationDto = (ProviderRegistrationDto) objectDto;
		if (objectVO != null && objectDto!= null) {
			providerRegistrationDto.setLegalBusinessName(providerRegistrationVO.getLegalBusinessName());
			providerRegistrationDto.setDBAName(providerRegistrationVO.getDBAName());
			providerRegistrationDto.setWebsiteAddress(providerRegistrationVO.getWebsiteAddress());
			providerRegistrationDto.setMainBusiPhoneNo1(providerRegistrationVO.getMainBusiPhoneNo1());
			providerRegistrationDto.setMainBusiPhoneNo2(providerRegistrationVO.getMainBusiPhoneNo2());
			providerRegistrationDto.setMainBusiPhoneNo3(providerRegistrationVO.getMainBusiPhoneNo3());
			providerRegistrationDto.setMainBusinessExtn(providerRegistrationVO.getMainBusinessExtn());
			providerRegistrationDto.setBusinessFax1(providerRegistrationVO.getBusinessFax1());
			providerRegistrationDto.setBusinessFax2(providerRegistrationVO.getBusinessFax2());
			providerRegistrationDto.setBusinessFax3(providerRegistrationVO.getBusinessFax3());
			providerRegistrationDto.setPrimaryIndustry(providerRegistrationVO.getPrimaryIndustry());
			providerRegistrationDto.setOtherPrimaryService(providerRegistrationVO.getOtherPrimaryService());
			providerRegistrationDto.setBusinessStreet1(providerRegistrationVO.getBusinessStreet1());
			providerRegistrationDto.setBusinessStreet2(providerRegistrationVO.getBusinessStreet2());
			providerRegistrationDto.setBusinessCity(providerRegistrationVO.getBusinessCity());
			providerRegistrationDto.setBusinessState(providerRegistrationVO.getBusinessState());
			providerRegistrationDto.setBusinessZip(providerRegistrationVO.getBusinessZip());
			providerRegistrationDto.setBusinessAprt(providerRegistrationVO.getBusinessAprt());
			providerRegistrationDto.setMailAddressChk(providerRegistrationVO.getMailAddressChk());
			providerRegistrationDto.setMailingStreet1(providerRegistrationVO.getMailingStreet1());
			providerRegistrationDto.setMailingStreet2(providerRegistrationVO.getMailingStreet2());
			providerRegistrationDto.setMailingCity(providerRegistrationVO.getMailingCity());
			providerRegistrationDto.setMailingState(providerRegistrationVO.getMailingState());
			providerRegistrationDto.setMailingZip(providerRegistrationVO.getMailingZip());
			
			providerRegistrationDto.setMailingAprt(providerRegistrationVO.getMailingAprt());
			providerRegistrationDto.setHowDidYouHear(providerRegistrationVO.getHowDidYouHear());
			providerRegistrationDto.setPromotionCode(providerRegistrationVO.getPromotionCode());
			providerRegistrationDto.setRoleWithinCom(providerRegistrationVO.getRoleWithinCom());
			providerRegistrationDto.setJobTitle(providerRegistrationVO.getJobTitle());
			providerRegistrationDto.setServiceCall(providerRegistrationVO.getServiceCall());
			providerRegistrationDto.setFirstName(providerRegistrationVO.getFirstName());
			providerRegistrationDto.setMiddleName(providerRegistrationVO.getMiddleName());
			providerRegistrationDto.setLastName(providerRegistrationVO.getLastName());
			
			providerRegistrationDto.setNameSuffix(providerRegistrationVO.getNameSuffix());
			providerRegistrationDto.setEmail(providerRegistrationVO.getEmail());
			providerRegistrationDto.setConfirmEmail(providerRegistrationVO.getConfirmEmail());
			providerRegistrationDto.setAltEmail(providerRegistrationVO.getAltEmail());
			providerRegistrationDto.setConfAltEmail(providerRegistrationVO.getConfAltEmail());
			providerRegistrationDto.setUserName(providerRegistrationVO.getUserName());
			providerRegistrationDto.setPrimaryIndList(providerRegistrationVO.getPrimaryIndList());
			providerRegistrationDto.setRoleWithinCompany(providerRegistrationVO.getRoleWithinCompany());
			providerRegistrationDto.setHowDidYouHearList(providerRegistrationVO.getHowDidYouHearList());
			providerRegistrationDto.setVendorId(providerRegistrationVO.getVendorId());
			providerRegistrationDto.setVendorContactResourceId(providerRegistrationVO.getVendorContactResourceId());
			providerRegistrationDto.setPassword(providerRegistrationVO.getPassword());
			providerRegistrationDto.setStateTypeList(providerRegistrationVO.getStateTypeList());
			providerRegistrationDto.setValidateState(providerRegistrationVO.isValidateState());
		}
		return providerRegistrationDto;
	}
	 

}
