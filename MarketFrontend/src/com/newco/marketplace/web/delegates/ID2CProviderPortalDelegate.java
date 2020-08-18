package com.newco.marketplace.web.delegates;

import java.io.Serializable;
import java.util.List;

import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.Coverage;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.CoverageLocationVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesData;
import com.newco.marketplace.web.dto.d2cproviderportal.ProviderPortalResponseDto;
/**
 * Delegate to intercept d2c provider portal calls
 * 
 * @author rranja1
 *
 */
public interface ID2CProviderPortalDelegate extends Serializable {
	ProviderPortalResponseDto getPrimaryIndustryDeleImpl(Integer vendorId);
	ProviderPortalResponseDto getProviderProfileDetails(List<String> vendorIds, List<String> filters);
	ProviderPortalResponseDto getSkuDetailsForVendorAndPrimaryIndustries(Integer vendorIdStr, List<Integer> primaryIndustries);
	ProviderPortalResponseDto getDispatchLocation(String vendorId);
	ProviderPortalResponseDto addUpdateDispatchLocation(DispatchLocationVO disLocationVO);
	ProviderPortalResponseDto getServiceOfferedDetail(String vendorId, List<Integer> primaryIndustries);
	ProviderPortalResponseDto updateDailyLimitAndPrice(VendorServiceOfferingVO vendorServiceOfferVo,Integer vendorId,String vendorName);
	public void updateCoverageZipCodes(List<ZipCodesData> zipCodesData, Coverage coverage);
	public String findZipCodesInRadius(Coverage coverage);
	public void saveZipCoverageRadius(Coverage coverage);
	public List<ZipCodesData> getDBSavedServiceAreaZipCodes(Coverage coverage);
    ProviderPortalResponseDto updateVendorData(String data, Integer vendorIdStr, String flag);
    public ProviderPortalResponseDto getFirmLogo(Integer vendorId);
	boolean updateVendorServiceOfferings(String vendorIdStr);
	public String deleteCoverageArea(Coverage coverage);
	public List<CoverageLocationVO> getCoverageAreas(String vendorIdStr);
	ProviderPortalResponseDto getNotOptedSKUList(String vendorId);
	ProviderPortalResponseDto getLookupServiceDays();
	ProviderPortalResponseDto getRateCardPriceDetails(Integer primaryIndustryId,Integer vendorId);
	ProviderPortalResponseDto updateOrDeleteOffersOn(VendorServiceOfferedOnVO vendorServiceOfferedOnVO);
	ProviderPortalResponseDto saveRateCardPrice(RateCardRequestVO rateCardRequestVO,Integer vendorId);	public ProviderPortalResponseDto addressValidation(DispatchLocationVO dispatchLocationVO);
	ProviderPortalResponseDto deleteFirmLogo(Integer vendorId);	
}
