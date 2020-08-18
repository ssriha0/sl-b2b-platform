package com.newco.marketplace.web.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.web.dto.provider.CompanyProfileDto;

/**
 * This class would act as a mapper class for company details
 * 
 * @author rranja1
 * 
 */
public class FirmDetailsMapper {

	public CompanyProfileVO convertDTOtoVO(CompanyProfileDto objectDto, CompanyProfileVO objectVO) {
		CompanyProfileDto companyProfileDto = objectDto;
		CompanyProfileVO companyProfileVO = objectVO;
		if (companyProfileVO != null && companyProfileDto != null) {
			companyProfileVO.setVendorId(companyProfileDto.getVendorId());

		}
		return companyProfileVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang
	 * .Object, java.lang.Object)
	 */
	public CompanyProfileDto convertVOtoDTO(CompanyProfileVO objectVO, CompanyProfileDto objectDto) {
		CompanyProfileVO companyProfileVO = objectVO;
		CompanyProfileDto companyProfileDto = objectDto;
		if (companyProfileVO != null && companyProfileDto != null) {

			companyProfileDto.setVendorId(companyProfileVO.getVendorId());
			companyProfileDto.setAnnualSalesVolume(companyProfileVO.getAnnualSalesVolume());
			companyProfileDto.setBusinessDesc(companyProfileVO.getBusinessDesc());
			companyProfileDto.setBusinessFax(companyProfileVO.getBusinessFax());
			companyProfileDto.setBusinessName(companyProfileVO.getBusinessName());
			companyProfileDto.setBusinessPhone(companyProfileVO.getBusinessPhone());
			companyProfileDto.setPrimaryIndustry(companyProfileVO.getPrimaryIndustry());
			companyProfileDto.setWebAddress(companyProfileVO.getWebAddress());
			if (companyProfileVO.getBusinessStartDate() != null) {
				companyProfileDto.setBusinessStartDate(formatDate(companyProfileVO.getBusinessStartDate()));
			}
			if (companyProfileVO.getMemberSince() != null) {
				companyProfileDto.setMemberSince(formatDate(companyProfileVO.getMemberSince()));
			}
			companyProfileDto.setBusinessType(companyProfileVO.getBusinessType());
			companyProfileDto.setCompanySize(companyProfileVO.getCompanySize());
			companyProfileDto.setDbaName(companyProfileVO.getDbaName());
			companyProfileDto.setDunsNo(companyProfileVO.getDunsNo());
			companyProfileDto.setEinNo(companyProfileVO.getEinNo());
			companyProfileDto.setForeignOwnedInd(companyProfileVO.getForeignOwnedInd());
			companyProfileDto.setForeignOwnedPct(companyProfileVO.getForeignOwnedPct());

			companyProfileDto.setBusCity(companyProfileVO.getBusCity());
			companyProfileDto.setBusStateCd(companyProfileVO.getBusStateCd());
			companyProfileDto.setBusStreet1(companyProfileVO.getBusStreet1());
			companyProfileDto.setBusStreet2(companyProfileVO.getBusStreet2());
			companyProfileDto.setBusZip(companyProfileVO.getBusZip());

			companyProfileDto.setAltEmail(companyProfileVO.getAltEmail());
			companyProfileDto.setEmail(companyProfileVO.getEmail());
			companyProfileDto.setFirstName(companyProfileVO.getFirstName());
			companyProfileDto.setMi(companyProfileVO.getMi());
			companyProfileDto.setLastName(companyProfileVO.getLastName());
			companyProfileDto.setSuffix(companyProfileVO.getSuffix());
			companyProfileDto.setRole(companyProfileVO.getRole());
			companyProfileDto.setTitle(companyProfileVO.getTitle());
			companyProfileDto.setYearsInBusiness(companyProfileVO.getYearsInBusiness());
			if (companyProfileVO.getLicensesList() != null) {
				companyProfileDto.setLicensesList(companyProfileVO.getLicensesList());

			}
			if (companyProfileVO.getInsuranceList() != null) {
				companyProfileDto.setInsuranceList(companyProfileVO.getInsuranceList());

			}
			// warranty
			/*
			if (companyProfileVO.getWarrOfferedLabor() != null)
				companyProfileDto.setWarrOfferedLabor(Integer.parseInt(companyProfileVO.getWarrOfferedLabor().trim()));
			if (companyProfileVO.getWarrOfferedParts() != null)
				companyProfileDto.setWarrOfferedParts(Integer.parseInt(companyProfileVO.getWarrOfferedParts().trim()));
			if (companyProfileVO.getWarrPeriodLabor() != null)
				companyProfileDto.setWarrPeriodLabor(companyProfileVO.getWarrPeriodLabor().trim());

			if (companyProfileVO.getWarrPeriodParts() != null)
				companyProfileDto.setWarrPeriodParts(companyProfileVO.getWarrPeriodParts().trim());
			if (companyProfileVO.getConductDrugTest() != null)
				companyProfileDto.setConductDrugTest(Integer.parseInt(companyProfileVO.getConductDrugTest().trim()));
			if (companyProfileVO.getConsiderBadge() != null)
				companyProfileDto.setConsiderBadge(Integer.parseInt(companyProfileVO.getConsiderBadge().trim()));
			if (companyProfileVO.getConsiderDrugTest() != null)
				companyProfileDto.setConsiderDrugTest(Integer.parseInt(companyProfileVO.getConsiderDrugTest().trim()));
			if (companyProfileVO.getConsiderEthicPolicy() != null)
				companyProfileDto.setConsiderEthicPolicy(Integer.parseInt(companyProfileVO.getConsiderEthicPolicy().trim()));
			if (companyProfileVO.getConsiderImplPolicy() != null)
				companyProfileDto.setConsiderImplPolicy(Integer.parseInt(companyProfileVO.getConsiderImplPolicy().trim()));
			if (companyProfileVO.getFreeEstimate() != null)
				companyProfileDto.setFreeEstimate(Integer.parseInt(companyProfileVO.getFreeEstimate().trim()));
			if (companyProfileVO.getHasEthicsPolicy() != null)
				companyProfileDto.setHasEthicsPolicy(Integer.parseInt(companyProfileVO.getHasEthicsPolicy().trim()));
			if (companyProfileVO.getRequireBadge() != null)
				companyProfileDto.setRequireBadge(Integer.parseInt(companyProfileVO.getRequireBadge().trim()));
			if (companyProfileVO.getRequireUsDoc() != null)
				companyProfileDto.setRequireUsDoc(Integer.parseInt(companyProfileVO.getRequireUsDoc().trim()));
			if (companyProfileVO.getWarrantyMeasure() != null && companyProfileVO.getWarrantyMeasure().trim().length() > 0)
				companyProfileDto.setWarrantyMeasure(Integer.parseInt(companyProfileVO.getWarrantyMeasure()));
			 */
		}
		return companyProfileDto;
	}

	/**
	 * 
	 * @param dateString
	 * @return
	 */
	private String formatDate(Date dateString) {

		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate1 = null;
		if (dateString != null) {
			stringDate1 = formatter.format(dateString);
		}
		return stringDate1;
	}

}