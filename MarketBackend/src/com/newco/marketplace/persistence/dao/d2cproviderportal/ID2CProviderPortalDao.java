package com.newco.marketplace.persistence.dao.d2cproviderportal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.Coverage;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.CoverageLocationVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesData;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.d2cproviderportal.BuyerSku;
import com.servicelive.domain.d2cproviderportal.LookupServiceDay;
import com.servicelive.domain.d2cproviderportal.LookupServiceRatePeriod;
import com.servicelive.domain.d2cproviderportal.LuOffersOn;
import com.servicelive.domain.d2cproviderportal.VendorCategoryPriceCard;
import com.servicelive.domain.d2cproviderportal.VendorServiceOfferPrice;
import com.servicelive.domain.d2cproviderportal.VendorServiceOfferedOn;
import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.domain.provider.ProviderFirmLocation;

/**
 * 
 * @author rranja1
 *
 */
public interface ID2CProviderPortalDao extends Serializable {

	List<LookupPrimaryIndustry> getPrimaryIndustryDaoImpl(Integer vendorId) throws DataServiceException;
	List<VendorServiceOfferPrice> getSkuList(Integer vendorIds, Integer primaryIndustry) throws DataServiceException;
	List<ProviderFirmLocation> getDispatchLocation(String vendorId) throws DataServiceException;
	boolean addUpdateDispatchLocation(DispatchLocationVO dispatchLocationVO) throws DataServiceException;
	List<BuyerSku> getBuyerSkusDetail(String vendorId, List<Integer> primaryIndustries)  throws DataServiceException;
    void updateDailyLimitAndPrice(VendorServiceOfferingVO vendorServiceOfferVo,Integer vendorId,String vendorName)throws DataServiceException;
    public void saveZipCoverageRadius(Coverage coverage);
	public void updateCoverageZipCodes(List<ZipCodesData> zipCodesData, Coverage coverage);
	public List<CoverageLocationVO> getCoverageAreas(String vendorIdStr);
	public List<ZipCodesData> getDBSavedServiceAreaZipCodes(Coverage coverage);
	public Coverage getCoverageById(Coverage coverage);
	boolean updateVendorData(String data, Integer vendorId, String flag) throws DataServiceException;
	boolean updateVendorServiceOfferings(String vendorIdStr) throws DataServiceException;
	public String deleteCoverageArea(Coverage coverage);
	List<VendorServiceOfferPrice> getVendorSkuDetail(Integer vendorId, Long skuId) throws DataServiceException;
	List<D2CProviderAPIVO> getFirmDetailsList(D2CPortalAPIVORequest d2CPortalAPIVO) throws DataServiceException;
	List<LookupServiceDay> getLookupServiceDays()throws DataServiceException;
	List<LookupServiceRatePeriod> getLookupServiceRatePeriod()throws DataServiceException;
	List<VendorCategoryPriceCard> getRateCardPrice(Integer primaryIndustryId,Integer vendorId) throws DataServiceException;
	String updateOrDeleteOffersOn(VendorServiceOfferedOnVO vendorServiceOfferedOnVO) throws DataServiceException;
	List<VendorServiceOfferedOn> getOffersOn(String vendorId,Integer primaryIndustryId) throws DataServiceException;
	List<LuOffersOn> getLuoffersOnDetails() throws DataServiceException;
	String saveRateCardPrice(RateCardRequestVO rateCardRequestVO, Integer vendorId) throws DataServiceException;
	public List<String> getStates();
	List<D2CProviderAPIVO> getFirmDetailsWithRetailPriceList(D2CPortalAPIVORequest d2CPortalAPIVO) throws DataServiceException;
	public Map<Integer, Long> getSoCompletedList(List<String> vendorIds);
	public Map<Integer, BigDecimal> getFirmRatings(List<String> vendorIds);
	public Double getBuyerRetailPrice(String skuId, String buyerId);
	public List<D2CProviderAPIVO> getFirmDetailsListByMultipleCriteriaSearch(D2CPortalAPIVORequest d2cPortalAPIVO) throws DataServiceException;
	public List<D2CProviderAPIVO> getFirmDetailsWithBuyerPrice(String buyerId, String sku, String zip, List<String> firmList) throws DataServiceException;
	public Integer getSpnIdBuyerSoTemplateBySkuBuyerId(final HashMap<String,Object> skuBuyerIdMap);
	
	//SLT-2848
	public List<Integer> getSkillNodeIdForSku(String sku);
	
}