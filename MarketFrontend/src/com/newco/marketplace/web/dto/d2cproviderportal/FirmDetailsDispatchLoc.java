package com.newco.marketplace.web.dto.d2cproviderportal;

import java.io.Serializable;
import java.util.List;

import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.PrimaryIndustryDetailsVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.CoverageLocationVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.PrimaryIndustryOffersOnDetailsVO;
import com.newco.marketplace.web.dto.provider.CompanyProfileDto;

public class FirmDetailsDispatchLoc implements Serializable {
	private static final long serialVersionUID = 1L;
	private CompanyProfileDto firmDetails;
	private List<DispatchLocationVO> dispatchLoc;
	private PrimaryIndustryOffersOnDetailsVO serviceOfferDetailVO;
	private List<CoverageLocationVO> coverageLoc;
	//Added To fetch primary Industry
	private List<LookupVO> primaryIndustryList;
	
	private String coverageHelpURL;
	private String serviceOfferHelpURL;
	
	
	private String imageBase64;

	public List<DispatchLocationVO> getDispatchLoc() {
		return dispatchLoc;
	}

	public void setDispatchLoc(List<DispatchLocationVO> dispatchLoc) {
		this.dispatchLoc = dispatchLoc;
	}

	public PrimaryIndustryOffersOnDetailsVO getServiceOfferDetailVO() {
		return serviceOfferDetailVO;
	}

	public void setServiceOfferDetailVO(
			PrimaryIndustryOffersOnDetailsVO serviceOfferDetailVO) {
		this.serviceOfferDetailVO = serviceOfferDetailVO;
	}

	public List<CoverageLocationVO> getCoverageLoc() {
		return coverageLoc;
	}

	public void setCoverageLoc(List<CoverageLocationVO> coverageLoc) {
		this.coverageLoc = coverageLoc;
	}

	public List<LookupVO> getPrimaryIndustryList() {
		return primaryIndustryList;
	}

	public void setPrimaryIndustryList(List<LookupVO> primaryIndustryList) {
		this.primaryIndustryList = primaryIndustryList;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	public String getCoverageHelpURL() {
		return coverageHelpURL;
	}

	public void setCoverageHelpURL(String coverageHelpURL) {
		this.coverageHelpURL = coverageHelpURL;
	}

	public String getServiceOfferHelpURL() {
		return serviceOfferHelpURL;
	}

	public void setServiceOfferHelpURL(String serviceOfferHelpURL) {
		this.serviceOfferHelpURL = serviceOfferHelpURL;
	}

	public CompanyProfileDto getFirmDetails() {
		return firmDetails;
	}

	public void setFirmDetails(CompanyProfileDto firmDetails) {
		this.firmDetails = firmDetails;
	}
}
