/**
 * 
 */
package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.web.dto.provider.VendorHdrDto;

/**
 * @author LENOVO USER
 * 
 */
public class VendorHdrMapper extends ObjectMapper {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public VendorHdr convertDTOtoVO(Object objectDto, Object objectVO) {
		VendorHdrDto vendorHdrDto = (VendorHdrDto) objectDto;
		VendorHdr vendorHdr = (VendorHdr) objectVO;
		if (objectDto != null && objectVO!=null) {
			
			
			vendorHdr.setAuditClaimedBy(vendorHdrDto.getAuditClaimedBy());
			vendorHdr.setBusinessAffiliateName(vendorHdrDto.getBusinessAffiliateName());
			vendorHdr.setBusinessAffiliateRelationship(vendorHdrDto.getBusinessAffiliateRelationship());
			vendorHdr.setBusinessFax(vendorHdrDto.getBusinessFax());
			vendorHdr.setBusinessName(vendorHdrDto.getBusinessName());
			vendorHdr.setBusinessPhone(vendorHdrDto.getBusinessPhone());
			vendorHdr.setBusinessStartDate(vendorHdrDto.getBusinessStartDate());
			vendorHdr.setBusinessTypeId(vendorHdrDto.getBusinessTypeId());
			vendorHdr.setBusPhoneExtn(vendorHdrDto.getBusPhoneExtn());
			vendorHdr.setCBGLI(vendorHdrDto.getCBGLI());
			vendorHdr.setCBGLIAmount(vendorHdrDto.getCBGLIAmount());
			vendorHdr.setCompanySizeId(vendorHdrDto.getCompanySizeId());
			vendorHdr.setContactGroup(vendorHdrDto.getContactGroup());
			vendorHdr.setDbaName(vendorHdrDto.getDbaName());
			vendorHdr.setDunsNo(vendorHdrDto.getDunsNo());
			vendorHdr.setEinNo(vendorHdrDto.getEinNo());
			vendorHdr.setForeignOwnedInd(vendorHdrDto.getForeignOwnedInd());
			vendorHdr.setForeignOwnedPct(vendorHdrDto.getForeignOwnedPct());
			vendorHdr.setNoCredInd(vendorHdrDto.getNoCredInd());
			vendorHdr.setProfileState(vendorHdrDto.getProfileState());
			vendorHdr.setPromotionCode(vendorHdrDto.getPromotionCode());
			vendorHdr.setTaxStatus(vendorHdrDto.getTaxStatus());
			vendorHdr.setTermsCondDate(vendorHdrDto.getTermsCondDate());
			vendorHdr.setTermsCondInd(vendorHdrDto.getTermsCondInd());
			vendorHdr.setTermsCondVersion(vendorHdrDto.getTermsCondVersion());
			vendorHdr.setVendorId(vendorHdrDto.getVendorId());
			vendorHdr.setVendorStatusId(vendorHdrDto.getVendorStatusId());
			vendorHdr.setVLI(vendorHdrDto.getVLI());
			vendorHdr.setVLIAmount(vendorHdrDto.getVLIAmount());
			vendorHdr.setWCI(vendorHdrDto.getWCI());
			vendorHdr.setWCIAmount(vendorHdrDto.getWCIAmount());
			vendorHdr.setWebAddress(vendorHdrDto.getWebAddress());
			vendorHdr.setYrsInBusiness(vendorHdrDto.getYrsInBusiness());
			vendorHdr.setYrsInBusinessUnderName(vendorHdrDto.getYrsInBusinessUnderName());
			vendorHdr.setPrimaryIndustryId(vendorHdrDto.getPrimaryIndustryId());
		}
		return vendorHdr;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public VendorHdrDto convertVOtoDTO(Object objectVO, Object objectDto) {
		VendorHdr vendorHdr = (VendorHdr) objectVO;
		VendorHdrDto vendorHdrDto = (VendorHdrDto) objectDto;
		if (objectVO != null && objectDto!= null) {
			vendorHdrDto.setAuditClaimedBy(vendorHdr.getAuditClaimedBy());
			vendorHdrDto.setBusinessAffiliateName(vendorHdr.getBusinessAffiliateName());
			vendorHdrDto.setBusinessAffiliateRelationship(vendorHdr.getBusinessAffiliateRelationship());
			vendorHdrDto.setBusinessFax(vendorHdr.getBusinessFax());
			vendorHdrDto.setBusinessName(vendorHdr.getBusinessName());
			vendorHdrDto.setBusinessPhone(vendorHdr.getBusinessPhone());
			vendorHdrDto.setBusinessStartDate(vendorHdr.getBusinessStartDate());
			vendorHdrDto.setBusinessTypeId(vendorHdr.getBusinessTypeId());
			vendorHdrDto.setBusPhoneExtn(vendorHdr.getBusPhoneExtn());
			vendorHdrDto.setCBGLI(vendorHdr.getCBGLI());
			vendorHdrDto.setCBGLIAmount(vendorHdr.getCBGLIAmount());
			vendorHdrDto.setCompanySizeId(vendorHdr.getCompanySizeId());
			vendorHdrDto.setContactGroup(vendorHdr.getContactGroup());
			vendorHdrDto.setDbaName(vendorHdr.getDbaName());
			vendorHdrDto.setDunsNo(vendorHdr.getDunsNo());
			vendorHdrDto.setEinNo(vendorHdr.getEinNo());
			vendorHdrDto.setForeignOwnedInd(vendorHdr.getForeignOwnedInd());
			vendorHdrDto.setForeignOwnedPct(vendorHdr.getForeignOwnedPct());
			vendorHdrDto.setNoCredInd(vendorHdr.getNoCredInd());
			vendorHdrDto.setProfileState(vendorHdr.getProfileState());
			vendorHdrDto.setPromotionCode(vendorHdr.getPromotionCode());
			vendorHdrDto.setTaxStatus(vendorHdr.getTaxStatus());
			vendorHdrDto.setTermsCondDate(vendorHdr.getTermsCondDate());
			vendorHdrDto.setTermsCondInd(vendorHdr.getTermsCondInd());
			vendorHdrDto.setTermsCondVersion(vendorHdr.getTermsCondVersion());
			vendorHdrDto.setVendorId(vendorHdr.getVendorId());
			vendorHdrDto.setVendorStatusId(vendorHdr.getVendorStatusId());
			vendorHdrDto.setVLI(vendorHdr.getVLI());
			vendorHdrDto.setVLIAmount(vendorHdr.getVLIAmount());
			vendorHdrDto.setWCI(vendorHdr.getWCI());
			vendorHdrDto.setWCIAmount(vendorHdr.getWCIAmount());
			vendorHdrDto.setWebAddress(vendorHdr.getWebAddress());
			vendorHdrDto.setYrsInBusiness(vendorHdr.getYrsInBusiness());
			vendorHdrDto.setYrsInBusinessUnderName(vendorHdr.getYrsInBusinessUnderName());
			vendorHdrDto.setPrimaryIndustryId(vendorHdr.getPrimaryIndustryId());
		}
		return vendorHdrDto;
	}
	 

}
