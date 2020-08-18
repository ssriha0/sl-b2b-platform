package com.newco.marketplace.business.iBusiness.d2cproviderportal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
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
import com.newco.marketplace.vo.provider.CompanyProfileVO;
/**
 * D2C provider portal business object
 * 
 * @author rranja1
 *
 */
public interface ID2CProviderPortalBO extends Serializable {

	List<LookupVO> getPrimaryIndustryBOImpl(Integer vendorId) throws BusinessServiceException;
	List<DispatchLocationVO> getDispatchLocation(String vendorId) throws BusinessServiceException;
	boolean addUpdateDispatchLocation(DispatchLocationVO dispatchLocationVO) throws BusinessServiceException;
	PrimaryIndustryOffersOnDetailsVO getServiceOfferedDetail(String vendorId, List<Integer> primaryIndustries)  throws BusinessServiceException;
	List<BuyerSkuVO> getSkuDetailsForVendorAndPrimaryIndustries(Integer vendorIds, List<Integer> primaryIndustery)throws BusinessServiceException;
	void updateDailyLimitAndPrice(VendorServiceOfferingVO vendorServiceOfferVo,Integer vendorId,String vendorName)throws BusinessServiceException;
	public void updateCoverageZipCodes(List<ZipCodesData> zipCodesData, Coverage coverage);
	public void saveZipCoverageRadius(Coverage coverage);
	public List<CoverageLocationVO> getCoverageAreas(String vendorIdStr);
	public List<ZipCodesData> getDBSavedServiceAreaZipCodes(Coverage coverage);
    boolean updateVendorData(String data, Integer vendorId, String flag) throws BusinessServiceException;
	boolean updateVendorServiceOfferings(String vendorIdStr) throws BusinessServiceException;
	CompanyProfileVO getCompleteProfile(String vendorId) throws BusinessServiceException;
	public String  deleteCoverageArea(Coverage coverage);
	List<D2CProviderAPIVO> getFirmDetailsList(D2CPortalAPIVORequest d2CPortalAPIVORequest) throws BusinessServiceException;
	List<BuyerSkuVO> getNotOptedSKUList(String vendorId) throws BusinessServiceException;
	RateCardVO getLookupServiceDays() throws BusinessServiceException;
	List<RateCardVO> getRateCardPriceDetails(Integer primaryIndustryId,Integer vendorId) throws BusinessServiceException;
	String updateOrDeleteOffersOn(VendorServiceOfferedOnVO vendorServiceOfferedOnVO)throws BusinessServiceException;
	String saveRateCardPrice(RateCardRequestVO rateCardRequestVO,Integer vendorId) throws BusinessServiceException;
	public List<String> getStates();
	String deleteFirmLogo(Integer vendorId);
	List<D2CProviderAPIVO> getFirmDetailsWithRetailPriceList(D2CPortalAPIVORequest d2CPortalAPIVORequest) throws BusinessServiceException;
	public Map<Integer, Long> getSoCompletedList(List<String> vendorIds);
	public Map<Integer, BigDecimal> getFirmRatings(List<String> vendorIds);
	public Double getBuyerRetailPrice(String skuId, String buyerId);
}
