package com.newco.marketplace.web.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;
/**
 * @author paugus2
 * 
 */

public class BuyerRegistrationMapper extends ObjectMapper{
	
	private static final Logger logger = Logger
	.getLogger(BuyerRegistrationMapper.class.getName());
		/*
		 * (non-Javadoc)
		 * 
		 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
		 *      java.lang.Object)
		 */
		public BuyerRegistrationDTO convertVOtoDTO(BuyerRegistrationDTO buyerRegistrationDTO, BuyerRegistrationVO buyerRegistrationVO) {
			if (buyerRegistrationDTO != null && buyerRegistrationVO!=null) {
				buyerRegistrationDTO.setBusinessName(buyerRegistrationVO.getBusinessName());
				buyerRegistrationDTO.setPhoneAreaCode(buyerRegistrationVO.getPhoneAreaCode());
				buyerRegistrationDTO.setPhonePart1(buyerRegistrationVO.getPhonePart1());
				buyerRegistrationDTO.setPhonePart2(buyerRegistrationVO.getPhonePart2());
				buyerRegistrationDTO.setPhoneExtn(buyerRegistrationVO.getPhoneExtn());
				buyerRegistrationDTO.setFaxAreaCode(buyerRegistrationVO.getFaxAreaCode());
				buyerRegistrationDTO.setFaxPart1(buyerRegistrationVO.getFaxPart1());
				buyerRegistrationDTO.setFaxPart2(buyerRegistrationVO.getFaxPart2());
				buyerRegistrationDTO.setBusinessStructure(buyerRegistrationVO.getBusinessStructure());
				buyerRegistrationDTO.setPrimaryIndustry(buyerRegistrationVO.getPrimaryIndustry());
				buyerRegistrationDTO.setSizeOfCompany(buyerRegistrationVO.getSizeOfCompany());
				buyerRegistrationDTO.setBusinessStarted(buyerRegistrationVO.getBusinessStarted());
				buyerRegistrationDTO.setBusinessStreet1(buyerRegistrationVO.getBusinessStreet1());
				buyerRegistrationDTO.setBusinessStreet2(buyerRegistrationVO.getBusinessStreet2());
				buyerRegistrationDTO.setBusinessCity(buyerRegistrationVO.getBusinessCity());
				buyerRegistrationDTO.setBusinessState(buyerRegistrationVO.getBusinessState());
				buyerRegistrationDTO.setBusinessZip(buyerRegistrationVO.getBusinessZip());
				buyerRegistrationDTO.setBusinessAprt(buyerRegistrationVO.getBusinessAprt());
				buyerRegistrationDTO.setMailingStreet1(buyerRegistrationVO.getMailingStreet1());
				buyerRegistrationDTO.setMailingStreet2(buyerRegistrationVO.getMailingStreet2());
				buyerRegistrationDTO.setMailingCity(buyerRegistrationVO.getMailingCity());
				buyerRegistrationDTO.setMailingState(buyerRegistrationVO.getMailingState());
				buyerRegistrationDTO.setMailingZip(buyerRegistrationVO.getMailingZip());
				buyerRegistrationDTO.setMailingAprt(buyerRegistrationVO.getMailingAprt());
				buyerRegistrationDTO.setLocName(buyerRegistrationVO.getLocName());
				buyerRegistrationDTO.setHomeAddressInd(buyerRegistrationVO.getHomeAddressInd());
				buyerRegistrationDTO.setRoleWithinCom(buyerRegistrationVO.getRoleWithinCom());
				buyerRegistrationDTO.setSizeOfCompany(buyerRegistrationVO.getSizeOfCompany());
				buyerRegistrationDTO.setAnnualSalesRevenue(buyerRegistrationVO.getAnnualSalesRevenue());
				buyerRegistrationDTO.setWebsiteAddress(buyerRegistrationVO.getWebsiteAddress());
				buyerRegistrationDTO.setMailAddressChk(buyerRegistrationVO.isMailAddressChk());
				buyerRegistrationDTO.setJobTitle(buyerRegistrationVO.getJobTitle());
				buyerRegistrationDTO.setFirstName(buyerRegistrationVO.getFirstName());
				buyerRegistrationDTO.setMiddleName(buyerRegistrationVO.getMiddleName());
				buyerRegistrationDTO.setLastName(buyerRegistrationVO.getLastName());
				buyerRegistrationDTO.setNameSuffix(buyerRegistrationVO.getNameSuffix());
				buyerRegistrationDTO.setBusPhoneNo1(buyerRegistrationVO.getBusPhoneNo1());
				buyerRegistrationDTO.setBusPhoneNo2(buyerRegistrationVO.getBusPhoneNo2());
				buyerRegistrationDTO.setBusPhoneNo3(buyerRegistrationVO.getBusPhoneNo3());
				buyerRegistrationDTO.setBusExtn(buyerRegistrationVO.getBusExtn());
				buyerRegistrationDTO.setMobPhoneNo1(buyerRegistrationVO.getMobPhoneNo1());
				buyerRegistrationDTO.setMobPhoneNo2(buyerRegistrationVO.getMobPhoneNo2());
				buyerRegistrationDTO.setMobPhoneNo3(buyerRegistrationVO.getMobPhoneNo3());
				buyerRegistrationDTO.setEmail(buyerRegistrationVO.getEmail());
				buyerRegistrationDTO.setConfirmEmail(buyerRegistrationVO.getConfirmEmail());
				buyerRegistrationDTO.setAltEmail(buyerRegistrationVO.getAltEmail());
				buyerRegistrationDTO.setConfAltEmail(buyerRegistrationVO.getConfAltEmail());
				buyerRegistrationDTO.setHowDidYouHear(buyerRegistrationVO.getHowDidYouHear());
				buyerRegistrationDTO.setPromotionCode(buyerRegistrationVO.getPromotionCode());
				buyerRegistrationDTO.setUserName(buyerRegistrationVO.getUserName());
				buyerRegistrationDTO.setSimpleBuyerInd(buyerRegistrationVO.isSimpleBuyerInd());
				buyerRegistrationDTO.setHowDidYouHearList(buyerRegistrationVO.getHowDidYouHearList());
				buyerRegistrationDTO.setPrimaryIndList(buyerRegistrationVO.getPrimaryIndList());
				buyerRegistrationDTO.setBusinessStructureList(buyerRegistrationVO.getBusinessStructureList());
				buyerRegistrationDTO.setSizeOfCompanyList(buyerRegistrationVO.getSizeOfCompanyList());
				buyerRegistrationDTO.setAnnualSalesRevenueList(buyerRegistrationVO.getAnnualSalesRevenueList());
				buyerRegistrationDTO.setRoleWithinCompanyList(buyerRegistrationVO.getRoleWithinCompanyList());
				buyerRegistrationDTO.setStateTypeList(buyerRegistrationVO.getStateTypeList());
				buyerRegistrationDTO.setBuyerContactResourceId(buyerRegistrationVO.getBuyerContactResourceId());
				buyerRegistrationDTO.setBuyerId(buyerRegistrationVO.getBuyerId());
				buyerRegistrationDTO.setPassword(buyerRegistrationVO.getPassword());
				buyerRegistrationDTO.setStateType(buyerRegistrationVO.getStateType());
				buyerRegistrationDTO.setValidateState(buyerRegistrationVO.isValidateState());
				buyerRegistrationDTO.setTermsAndConditions(buyerRegistrationVO.getTermsAndConditions());
				buyerRegistrationDTO.setTermsAndCondition(buyerRegistrationVO.getTermsAndCondition());
				buyerRegistrationDTO.setServiceLiveBucksInd(buyerRegistrationVO.getServiceLiveBucksInd());
				buyerRegistrationDTO.setServiceLiveBucksText(buyerRegistrationVO.getServiceLiveBucksText());
				
				
				// Changes Starts for 20461 Admin name change
				if(null!=buyerRegistrationVO.getBuyerList()){
					
					
					buyerRegistrationDTO.setBuyerList(buyerRegistrationVO.getBuyerList());
				
					buyerRegistrationDTO.setModifiedBy(buyerRegistrationVO.getModifiedBy());
					
				}
				
				
					buyerRegistrationDTO.setCurrentAdminResourceId(buyerRegistrationVO.getCurrentAdminResourceId());
					buyerRegistrationDTO.setCurrentAdminFirstname(buyerRegistrationVO.getCurrentAdminFirstname());
					buyerRegistrationDTO.setCurrentAdminLastName(buyerRegistrationVO.getCurrentAdminLastName());
					
					buyerRegistrationDTO.setNewAdminResourceId(buyerRegistrationVO.getNewAdminResourceId());
					
					buyerRegistrationDTO.setNewAdminFirstName(buyerRegistrationVO.getNewAdminFirstName());
					buyerRegistrationDTO.setNewAdminLastName(buyerRegistrationVO.getNewAdminLastName());
					buyerRegistrationDTO.setCurrentAdminUserName((buyerRegistrationVO.getCurrentAdminUserName()));
					buyerRegistrationDTO.setNewAdminUserName(buyerRegistrationVO.getNewAdminUserName());
					
					
				// Changes Ends for 20461 Admin name change
				
			}
			return buyerRegistrationDTO;
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
		 *      java.lang.Object)
		 */
		public BuyerRegistrationVO convertDTOtoVO(BuyerRegistrationVO buyerRegistrationVO, BuyerRegistrationDTO buyerRegistrationDTO) {
			if (buyerRegistrationVO != null && buyerRegistrationDTO!= null) {
				buyerRegistrationVO.setBusinessName(buyerRegistrationDTO.getBusinessName());
				buyerRegistrationVO.setPhoneAreaCode(buyerRegistrationDTO.getPhoneAreaCode());
				buyerRegistrationVO.setPhonePart1(buyerRegistrationDTO.getPhonePart1());
				buyerRegistrationVO.setPhonePart2(buyerRegistrationDTO.getPhonePart2());
				buyerRegistrationVO.setPhoneExtn(buyerRegistrationDTO.getPhoneExtn());
				buyerRegistrationVO.setFaxAreaCode(buyerRegistrationDTO.getFaxAreaCode());
				buyerRegistrationVO.setFaxPart1(buyerRegistrationDTO.getFaxPart1());
				buyerRegistrationVO.setFaxPart2(buyerRegistrationDTO.getFaxPart2());
				buyerRegistrationVO.setBusinessStructure(buyerRegistrationDTO.getBusinessStructure());
				buyerRegistrationVO.setPrimaryIndustry(buyerRegistrationDTO.getPrimaryIndustry());
				buyerRegistrationVO.setSizeOfCompany(buyerRegistrationDTO.getSizeOfCompany());
				buyerRegistrationVO.setBusinessStarted(buyerRegistrationDTO.getBusinessStarted());
				buyerRegistrationVO.setBusinessStreet1(buyerRegistrationDTO.getBusinessStreet1());
				buyerRegistrationVO.setBusinessStreet2(buyerRegistrationDTO.getBusinessStreet2());
				buyerRegistrationVO.setBusinessCity(buyerRegistrationDTO.getBusinessCity());
				buyerRegistrationVO.setBusinessState(buyerRegistrationDTO.getBusinessState());
				buyerRegistrationVO.setBusinessZip(buyerRegistrationDTO.getBusinessZip());
				buyerRegistrationVO.setBusinessAprt(buyerRegistrationDTO.getBusinessAprt());
				buyerRegistrationVO.setMailingStreet1(buyerRegistrationDTO.getMailingStreet1());
				buyerRegistrationVO.setMailingStreet2(buyerRegistrationDTO.getMailingStreet2());
				buyerRegistrationVO.setMailingCity(buyerRegistrationDTO.getMailingCity());
				buyerRegistrationVO.setMailingState(buyerRegistrationDTO.getMailingState());
				buyerRegistrationVO.setMailingZip(buyerRegistrationDTO.getMailingZip());
				buyerRegistrationVO.setMailingAprt(buyerRegistrationDTO.getMailingAprt());
				buyerRegistrationVO.setLocName(buyerRegistrationDTO.getLocName());
				buyerRegistrationVO.setHomeAddressInd(buyerRegistrationDTO.getHomeAddressInd());
				buyerRegistrationVO.setRoleWithinCom(buyerRegistrationDTO.getRoleWithinCom());
				buyerRegistrationVO.setSizeOfCompany(buyerRegistrationDTO.getSizeOfCompany());
				buyerRegistrationVO.setAnnualSalesRevenue(buyerRegistrationDTO.getAnnualSalesRevenue());
				buyerRegistrationVO.setWebsiteAddress(buyerRegistrationDTO.getWebsiteAddress());
				buyerRegistrationVO.setMailAddressChk(buyerRegistrationDTO.isMailAddressChk());
				buyerRegistrationVO.setJobTitle(buyerRegistrationDTO.getJobTitle());
				buyerRegistrationVO.setFirstName(buyerRegistrationDTO.getFirstName());
				buyerRegistrationVO.setMiddleName(buyerRegistrationDTO.getMiddleName());
				buyerRegistrationVO.setLastName(buyerRegistrationDTO.getLastName());
				//SL-19704
				if(StringUtils.isNotBlank(buyerRegistrationVO.getFirstName())){
					buyerRegistrationVO.setFirstName(buyerRegistrationVO.getFirstName().trim());
				}
				if(StringUtils.isNotBlank(buyerRegistrationVO.getMiddleName())){
					buyerRegistrationVO.setMiddleName(buyerRegistrationVO.getMiddleName().trim());
				}
				if(StringUtils.isNotBlank(buyerRegistrationVO.getLastName())){
					buyerRegistrationVO.setLastName(buyerRegistrationVO.getLastName().trim());
				}
				buyerRegistrationVO.setNameSuffix(buyerRegistrationDTO.getNameSuffix());
				buyerRegistrationVO.setBusPhoneNo1(buyerRegistrationDTO.getBusPhoneNo1());
				buyerRegistrationVO.setBusPhoneNo2(buyerRegistrationDTO.getBusPhoneNo2());
				buyerRegistrationVO.setBusPhoneNo3(buyerRegistrationDTO.getBusPhoneNo3());
				buyerRegistrationVO.setBusExtn(buyerRegistrationDTO.getBusExtn());
				buyerRegistrationVO.setMobPhoneNo1(buyerRegistrationDTO.getMobPhoneNo1());
				buyerRegistrationVO.setMobPhoneNo2(buyerRegistrationDTO.getMobPhoneNo2());
				buyerRegistrationVO.setMobPhoneNo3(buyerRegistrationDTO.getMobPhoneNo3());
				buyerRegistrationVO.setEmail(buyerRegistrationDTO.getEmail());
				buyerRegistrationVO.setConfirmEmail(buyerRegistrationDTO.getConfirmEmail());
				buyerRegistrationVO.setAltEmail(buyerRegistrationDTO.getAltEmail());
				buyerRegistrationVO.setConfAltEmail(buyerRegistrationDTO.getConfAltEmail());
				buyerRegistrationVO.setHowDidYouHear(buyerRegistrationDTO.getHowDidYouHear());
				buyerRegistrationVO.setPromotionCode(buyerRegistrationDTO.getPromotionCode());
				buyerRegistrationVO.setUserName(buyerRegistrationDTO.getUserName());
				//SL-19704
				if(StringUtils.isNotBlank(buyerRegistrationVO.getUserName())){
					buyerRegistrationVO.setUserName(buyerRegistrationVO.getUserName().trim());
				}
				buyerRegistrationVO.setSimpleBuyerInd(buyerRegistrationDTO.isSimpleBuyerInd());
				buyerRegistrationVO.setHowDidYouHearList(buyerRegistrationDTO.getHowDidYouHearList());
				buyerRegistrationVO.setPrimaryIndList(buyerRegistrationDTO.getPrimaryIndList());
				buyerRegistrationVO.setBusinessStructureList(buyerRegistrationDTO.getBusinessStructureList());
				buyerRegistrationVO.setSizeOfCompanyList(buyerRegistrationDTO.getSizeOfCompanyList());
				buyerRegistrationVO.setAnnualSalesRevenueList(buyerRegistrationDTO.getAnnualSalesRevenueList());
				buyerRegistrationVO.setRoleWithinCompanyList(buyerRegistrationDTO.getRoleWithinCompanyList());
				buyerRegistrationVO.setStateTypeList(buyerRegistrationDTO.getStateTypeList());
				buyerRegistrationVO.setBuyerContactResourceId(buyerRegistrationDTO.getBuyerContactResourceId());
				buyerRegistrationVO.setBuyerId(buyerRegistrationDTO.getBuyerId());
				buyerRegistrationVO.setPassword(buyerRegistrationDTO.getPassword());
				buyerRegistrationVO.setStateType(buyerRegistrationDTO.getStateType());
				buyerRegistrationVO.setValidateState(buyerRegistrationDTO.isValidateState());
				buyerRegistrationVO.setTermsAndConditions(buyerRegistrationDTO.getTermsAndConditions());
				buyerRegistrationVO.setTermsAndCondition(buyerRegistrationDTO.getTermsAndCondition());
				buyerRegistrationVO.setServiceLiveBucksInd(buyerRegistrationDTO.getServiceLiveBucksInd());
				buyerRegistrationVO.setServiceLiveBucksText(buyerRegistrationDTO.getServiceLiveBucksText());
				buyerRegistrationVO.setPromotionalMailInd(buyerRegistrationDTO.getPromotionalMailIndicator());
				
				//changes for Sl-20461 buyer admin change starts
				buyerRegistrationVO.setBuyerList(buyerRegistrationDTO.getBuyerList());
			
			
				buyerRegistrationVO.setCurrentAdminResourceId(buyerRegistrationDTO.getCurrentAdminResourceId());
			
				buyerRegistrationVO.setCurrentAdminFirstname(buyerRegistrationDTO.getCurrentAdminFirstname());
				buyerRegistrationVO.setCurrentAdminLastName(buyerRegistrationDTO.getCurrentAdminLastName());
		
				buyerRegistrationVO.setNewAdminResourceId(buyerRegistrationDTO.getNewAdminResourceId());
				
				buyerRegistrationVO.setNewAdminLastName(buyerRegistrationDTO.getNewAdminLastName());
				buyerRegistrationVO.setNewAdminFirstName(buyerRegistrationDTO.getNewAdminFirstName());
				buyerRegistrationVO.setCurrentAdminUserName(buyerRegistrationDTO.getCurrentAdminUserName());
				buyerRegistrationVO.setCurrentAdminUserName(buyerRegistrationDTO.getCurrentAdminUserName());
			
				buyerRegistrationVO.setModifiedBy(buyerRegistrationDTO.getModifiedBy());
				buyerRegistrationVO.setNewAdminUserName(buyerRegistrationDTO.getNewAdminUserName());
				
				//changes for SL-20461 buyer admin change ends
			}
			return buyerRegistrationVO;
		}
	}	


