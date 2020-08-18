package com.newco.marketplace.business.businessImpl.d2cproviderportal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.iBusiness.d2cproviderportal.ID2CProviderPortalBO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.Coverage;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.CoverageLocationVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.PrimaryIndustryOffersOnDetailsVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesData;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.services.d2cproviderportal.ID2CProviderPortalService;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.sears.os.business.ABaseBO;

/**
 * BO impl for D2C Provider portal flow
 * 
 * @author rranja1
 * 
 */
public class D2CProviderPortalBOImpl extends ABaseBO implements ID2CProviderPortalBO {

	private static final long serialVersionUID = 1L;
	private ID2CProviderPortalService d2cProviderPortalService;

	/**
	 * get multiple dispatch locations
	 */
	public List<DispatchLocationVO> getDispatchLocation(String vendorId) throws BusinessServiceException {
		logger.info("start getDispatchLocation of D2CProviderPortalBOImpl");

		List<DispatchLocationVO> dispatchLocs = null;
		try {
			dispatchLocs = d2cProviderPortalService.getDispatchLocation(vendorId);
		} catch (BusinessServiceException excep) {
			logger.error("Error while getDispatchLocation of D2CProviderPortalBOImpl", excep);
			throw new BusinessServiceException("Error while getDispatchLocation of D2CProviderPortalBOImpl", excep);
		}

		logger.info("end getDispatchLocation of D2CProviderPortalBOImpl");
		return dispatchLocs;
	}
	
	/**
	 * get complete company profile
	 * 
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public CompanyProfileVO getCompleteProfile(String vendorId) throws BusinessServiceException {
		logger.info("start getCompleteProfile of D2CProviderPortalBOImpl");
		
		CompanyProfileVO companyProfile = null;
		try {
			companyProfile = d2cProviderPortalService.getCompleteProfile(Integer.parseInt(vendorId));
		} catch (BusinessServiceException excep) {
			logger.error("Error while getCompleteProfile of D2CProviderPortalBOImpl", excep);
			throw new BusinessServiceException("Error while getCompleteProfile of D2CProviderPortalBOImpl", excep);
		}
		logger.info("end getCompleteProfile of D2CProviderPortalBOImpl");
		return companyProfile;
	}

	/**
	 * This method will fetch primary industries
	 */
	public List<LookupVO> getPrimaryIndustryBOImpl(Integer vendorId) throws BusinessServiceException {
		logger.info("start getPrimaryIndustryBOImpl in D2CProviderPortalBOImpl");

		List<LookupVO> primaryIndustries = null;
		try {
			primaryIndustries = d2cProviderPortalService.getPrimaryIndustryServiceImpl(vendorId);
		} catch (Exception e) {
			logger.error("Exception in fetching primary Industry : getPrimaryIndustryBOImpl in D2CProviderPortalBOImpl");
			throw new BusinessServiceException("Exception in fetching primary Industry", e);
		}

		logger.info("end getPrimaryIndustryBOImpl in D2CProviderPortalBOImpl");
		return primaryIndustries;
	}

	public List<BuyerSkuVO> getSkuDetailsForVendorAndPrimaryIndustries(Integer vendorIds, List<Integer> primaryIndustry)
			throws BusinessServiceException {
		logger.info("Inside getSkuDetailsForVendorAndPrimaryIndustries()");
		List<BuyerSkuVO> skuList = null;
		try {
			skuList = d2cProviderPortalService.getSkuDetailsForVendorAndPrimaryIndustries(vendorIds, primaryIndustry);
		} catch (Exception e) {
			logger.error("Error while retrieving sku details getSkuDetailsForVendorAndPrimaryIndustries()", e);
			throw new BusinessServiceException("Error while retrieving sku details getSkuDetailsForVendorAndPrimaryIndustries()", e);
		}
		return skuList;
	}
	
	/**
	 * get not opted sku list for a vendor
	 * @throws BusinessServiceException 
	 */
	public List<BuyerSkuVO> getNotOptedSKUList(String vendorId) throws BusinessServiceException {
		logger.info("Inside getNotOptedSKUList()");
		List<BuyerSkuVO> skuList = null;
		try {
			skuList = d2cProviderPortalService.getNotOptedSKUList(vendorId);
		} catch (Exception e) {
			logger.error("Error while retrieving sku details getNotOptedSKUList()", e);
			throw new BusinessServiceException("Error while retrieving sku details getNotOptedSKUList()", e);
		}
		return skuList;
	}

	public PrimaryIndustryOffersOnDetailsVO getServiceOfferedDetail(String vendorId, List<Integer> primaryIndustries)
			throws BusinessServiceException {
		logger.info("start getServiceOfferedDetail of D2CProviderPortalBOImpl");

		PrimaryIndustryOffersOnDetailsVO servicesOffered = null;
		try {
			servicesOffered = d2cProviderPortalService.getServiceOfferedDetail(vendorId, primaryIndustries);
		} catch (BusinessServiceException excep) {
			logger.error("Error while getServiceOfferedDetail of D2CProviderPortalBOImpl", excep);
			throw new BusinessServiceException("Error while getServiceOfferedDetail of D2CProviderPortalBOImpl", excep);
		}

		logger.info("end getServiceOfferedDetail of D2CProviderPortalBOImpl");
		return servicesOffered;
	}

	/**
	 * save a dispatch locations
	 * 
	 * @throws BusinessServiceException
	 */
	public boolean addUpdateDispatchLocation(DispatchLocationVO dispatchLocationVO) throws BusinessServiceException {
		logger.info("start addUpdateDispatchLocation of D2CProviderPortalBOImpl");

		boolean isSaved;
		try {
			isSaved = d2cProviderPortalService.addUpdateDispatchLocation(dispatchLocationVO);
		} catch (BusinessServiceException e) {
			logger.error("Error while addDispatchLocation of D2CProviderPortalBOImpl", e);
			throw new BusinessServiceException("Error while addUpdateDispatchLocation of D2CProviderPortalBOImpl", e);
		}

		logger.info("end addDispatchLocation of D2CProviderPortalBOImpl");
		return isSaved;
	}

	public void updateDailyLimitAndPrice(VendorServiceOfferingVO vendorServiceOfferVo, Integer vendorId, String vendorName) {
		logger.info("inside updateDailyLimitAndPrice D2CProviderPortalBOImpl");
		try {
			d2cProviderPortalService.updateDailyLimitAndPrice(vendorServiceOfferVo, vendorId, vendorName);
		} catch (BusinessServiceException e) {
			logger.error("Exception in D2CProviderPortalBOImpl.updateDailyLimitAndPrice", e);
		}
		logger.info("exiting updateDailyLimitAndPrice D2CProviderPortalBOImpl");
	}

	public ID2CProviderPortalService getD2cProviderPortalService() {
		return d2cProviderPortalService;
	}

	public void setD2cProviderPortalService(ID2CProviderPortalService d2cProviderPortalService) {
		this.d2cProviderPortalService = d2cProviderPortalService;
	}
	

	public void updateCoverageZipCodes(List<ZipCodesData> zipCodesData,Coverage coverage) {
		logger.info("entering updateCoverageZipCodes of D2CProviderPortalBOImpl");

		d2cProviderPortalService.updateCoverageZipCodes(zipCodesData, coverage);
		logger.info("exiting updateCoverageZipCodes of D2CProviderPortalBOImpl");
	}

	public void saveZipCoverageRadius(Coverage coverage) {
		logger.info("entering saveZipCoverageRadius of D2CProviderPortalBOImpl");
		d2cProviderPortalService.saveZipCoverageRadius(coverage);
		logger.info("exiting saveZipCoverageRadius of D2CProviderPortalBOImpl");

	}
	public List<CoverageLocationVO> getCoverageAreas(String vendorIdStr) {
		return d2cProviderPortalService.getCoverageAreas(vendorIdStr);
	}
	
	public List<ZipCodesData> getDBSavedServiceAreaZipCodes(Coverage coverage){
		return d2cProviderPortalService.getDBSavedServiceAreaZipCodes(coverage);
	}

	public boolean updateVendorData(String data,Integer vendorId,String flag) throws BusinessServiceException{
		logger.info("Inside D2CProviderPortalBOImpl.updateVendorData");
		
		boolean isSuccess = false;
		try {
			isSuccess = d2cProviderPortalService.updateVendorData(data,vendorId,flag);
		} catch (BusinessServiceException e) {
			logger.info("Exception in D2CProviderPortalBOImpl.updateVendorData", e);
			throw new BusinessServiceException("Error while updateVendorData of D2CProviderPortalBOImpl", e);
		}
		logger.info("exiting D2CProviderPortalBOImpl.updateVendorData");
		return isSuccess;
	}

	/**
	 * update vendor service offering table
	 * @throws BusinessServiceException 
	 */
	public boolean updateVendorServiceOfferings(String vendorIdStr) throws BusinessServiceException {
		logger.info("start updateVendorServiceOfferings.updateVendorData");
		
		boolean success = false;
		try {
			success = d2cProviderPortalService.updateVendorServiceOfferings(vendorIdStr);
		} catch (BusinessServiceException e) {
			logger.error("Error while updateVendorServiceOfferings of D2CProviderPortalBOImpl", e);
			throw new BusinessServiceException("Error while updateVendorServiceOfferings of D2CProviderPortalBOImpl", e);
		}

		logger.info("end updateVendorServiceOfferings.updateVendorData");
		return success;
	}

	public String deleteCoverageArea(Coverage coverage) {
		return d2cProviderPortalService.deleteCoverageArea(coverage);
		
	}
	
	public List<D2CProviderAPIVO> getFirmDetailsList(D2CPortalAPIVORequest d2CPortalAPIVORequest) throws BusinessServiceException{
		List<D2CProviderAPIVO> firmDetailsListVo = null;
		try {
			firmDetailsListVo = d2cProviderPortalService.getFirmDetailsList(d2CPortalAPIVORequest);
		} catch (BusinessServiceException e) {
			throw new BusinessServiceException("Exception In getFirmDetailsList of D2CProviderPortalBOImpl", e);
		}
		return firmDetailsListVo;
	}	
	public RateCardVO getLookupServiceDays() throws BusinessServiceException{
		RateCardVO rateCardVO=new RateCardVO();
		logger.info("start getLookupServiceDays of D2CProviderPortalBOImpl");
		try {
			rateCardVO = d2cProviderPortalService.getLookupServiceDays();
		} catch (BusinessServiceException e) {
			throw new BusinessServiceException("Exception In getLookupServiceDays of D2CProviderPortalBOImpl", e);
		}
		logger.info("end getLookupServiceDays.D2CProviderPortalBOImpl");
		return rateCardVO;
	}
	
	public List<RateCardVO> getRateCardPriceDetails(Integer primaryIndustryId,Integer vendorId) throws BusinessServiceException{
		List<RateCardVO> rateCards = new ArrayList<RateCardVO>();
		logger.info("start getRateCardPriceDetails of D2CProviderPortalBOImpl");
		try {
			rateCards = d2cProviderPortalService.getRateCardPriceDetails(primaryIndustryId, vendorId);
		} catch (BusinessServiceException e) {
			throw new BusinessServiceException("Exception In getRateCardPriceDetails of D2CProviderPortalBOImpl", e);
		}
		logger.info("end getRateCardPriceDetails.D2CProviderPortalBOImpl");
		return rateCards;
	}
	
	public String updateOrDeleteOffersOn(VendorServiceOfferedOnVO vendorServiceOfferedOnVO) throws BusinessServiceException {
		logger.info("Inside D2CProviderPortalBOImpl.updateOrDeleteOffersOn");
		String status = d2cProviderPortalService.updateOrDeleteOffersOn(vendorServiceOfferedOnVO);
		logger.info("Exiting D2CProviderPortalBOImpl.updateOrDeleteOffersOn");
		return status;
	}
	
	public String saveRateCardPrice(RateCardRequestVO rateCardRequestVO,Integer vendorId) throws BusinessServiceException{
		logger.info("start saveRateCardPrice of D2CProviderPortalBOImpl");
		String rateCardAdded="";
		try {
			 rateCardAdded=d2cProviderPortalService.saveRateCardPrice(rateCardRequestVO, vendorId);
		} catch (BusinessServiceException e) {
			throw new BusinessServiceException("Exception In saveRateCardPrice of D2CProviderPortalBOImpl", e);
		}
		logger.info("end saveRateCardPrice.D2CProviderPortalBOImpl");
		return rateCardAdded;
	}

	
	public List<String> getStates() {
		
		return d2cProviderPortalService.getStates();
	}

	public String deleteFirmLogo(Integer vendorId){
		logger.info("Inside D2CProviderPortalBOImpl.deleteFirmLogo");
		String status = null;
		try{
		status = d2cProviderPortalService.deleteFirmLogo(vendorId);
		}catch(Exception e){
			logger.error("error in D2CProviderPortalBOImpl.deleteFirmLogo", e);
		}
		logger.info("Exiting D2CProviderPortalBOImpl.deleteFirmLogo");
		return status;
	}
	
	//Start : Below code added for new API to get provider list(Standard+Non-Standard)	
	public List<D2CProviderAPIVO> getFirmDetailsWithRetailPriceList(D2CPortalAPIVORequest d2CPortalAPIVORequest) throws BusinessServiceException{
		List<D2CProviderAPIVO> firmDetailsListVo = null;
		try {
			firmDetailsListVo = d2cProviderPortalService.getFirmDetailsWithRetailPriceList(d2CPortalAPIVORequest);
		} catch (BusinessServiceException e) {
			throw new BusinessServiceException("Exception In getFirmDetailsList of D2CProviderPortalBOImpl", e);
		}
		return firmDetailsListVo;
	}
	
	public Map<Integer, Long> getSoCompletedList(List<String> vendorIds){
		Map<Integer, Long> soCompletedMap = null;
		soCompletedMap = d2cProviderPortalService.getSoCompletedList(vendorIds);
		return soCompletedMap;
	}
	
	public Map<Integer, BigDecimal> getFirmRatings(List<String> vendorIds){
		Map<Integer, BigDecimal> firmRatings = null;
		firmRatings = d2cProviderPortalService.getFirmRatings(vendorIds);
		return firmRatings;
	}
	
	public Double getBuyerRetailPrice(String skuId, String buyerId){
		return d2cProviderPortalService.getBuyerRetailPrice(skuId, buyerId);
	}
	//End
}