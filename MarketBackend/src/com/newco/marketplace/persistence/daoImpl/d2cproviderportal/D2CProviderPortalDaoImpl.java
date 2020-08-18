package com.newco.marketplace.persistence.daoImpl.d2cproviderportal;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferPriceVo;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.Coverage;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.CoverageLocationVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.LuOffersOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardJsonRequest;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesData;
import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.persistence.dao.d2cproviderportal.ID2CProviderPortalDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.autoclose.dao.impl.BaseDao;
import com.servicelive.domain.common.Location;
import com.servicelive.domain.d2cproviderportal.BuyerSku;
import com.servicelive.domain.d2cproviderportal.LookupServiceDay;
import com.servicelive.domain.d2cproviderportal.LookupServiceRatePeriod;
import com.servicelive.domain.d2cproviderportal.LuOffersOn;
import com.servicelive.domain.d2cproviderportal.VendorCategoryMap;
import com.servicelive.domain.d2cproviderportal.VendorCategoryPriceCard;
import com.servicelive.domain.d2cproviderportal.VendorServiceOfferPrice;
import com.servicelive.domain.d2cproviderportal.VendorServiceOfferedOn;
import com.servicelive.domain.d2cproviderportal.VendorServiceOffering;
import com.servicelive.domain.lookup.LookupLocationTypes;
import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.lookup.LookupZipGeocode;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ProviderFirmLocation;
import com.servicelive.domain.provider.ProviderFirmLocationPk;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author rranja1
 * 
 */
public class D2CProviderPortalDaoImpl extends ABaseImplDao implements ID2CProviderPortalDao, BaseDao {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(D2CProviderPortalDaoImpl.class);

	private static final int BUYER_ID_3333 = 3333;

	private EntityManager em;

	private List<Long> getBuyerIds() {
		String buyers = PropertiesUtils.getFMPropertyValue("d2cPortalActiveBuyerIds");
		
		List<Long> buyerList =  new ArrayList<Long>();
		
		 if(buyers != null && buyers.length() > 0) {
			 for (String buyerId : buyers.split("_")) {
				 buyerList.add(Long.valueOf(buyerId));
			 }
		 }
		 
		return buyerList;
	}	
	
	/**
	 * method to retrive primary industries for vendor
	 */
	@SuppressWarnings("unchecked")
	public List<LookupPrimaryIndustry> getPrimaryIndustryDaoImpl(Integer vendorId) throws DataServiceException {
		logger.info("start getPrimaryIndustryDaoImpl of D2CProviderPortalDaoImpl");

		List<LookupPrimaryIndustry> lookupPrimaryIndustry = null;
		try {
			String hql = "from LookupPrimaryIndustry lpi where lpi.id in (select distinct vso.buyerSku.buyerSoTemplate.lookupPrimaryIndustry.id "
					+ "from VendorServiceOffering vso where vso.providerFirm.id=:vendorId and vso.buyerSku.buyerId in (:buyerId))";

			Query query = getEntityManager().createQuery(hql);
			// for relay buyer
			query.setParameter("buyerId", getBuyerIds());
			query.setParameter("vendorId", vendorId);

			lookupPrimaryIndustry = query.getResultList();
		} catch (Exception e) {
			logger.error("Exception in fetching getPrimaryIndustryDaoImpl from D2CProviderPortalDaoImpl", e);
			throw new DataServiceException("Exception in fetching primary Industry", e);
		}

		logger.info("end getPrimaryIndustryDaoImpl of D2CProviderPortalDaoImpl");
		return lookupPrimaryIndustry;
	}

	/**
	 * get prices for sku on basis of vendor and primary industry
	 */
	@SuppressWarnings("unchecked")
	public List<VendorServiceOfferPrice> getSkuList(Integer vendorId, Integer primaryIndustry) throws DataServiceException {
		logger.info("Inisde getSkuList() of D2CProviderPortalDaoImpl");
		List<VendorServiceOfferPrice> skuPriceList = null;
		try {
			String hql = "from VendorServiceOfferPrice vsop where vsop.vendorServiceOffering.providerFirm.id=:vendorId and vsop.vendorServiceOffering.buyerSku.buyerId in (:buyerId) and vsop.vendorServiceOffering.buyerSku.buyerSoTemplate.lookupPrimaryIndustry.id = :primaryIndustries";

			Query query = getEntityManager().createQuery(hql);
			query.setParameter("vendorId", vendorId);
			query.setParameter("buyerId", getBuyerIds());
			query.setParameter("primaryIndustries", primaryIndustry);

			skuPriceList = query.getResultList();

		} catch (Exception e) {
			logger.debug("Exception in D2CDaoImpl.getSkuList due to " + e.getMessage());
			throw new DataServiceException(e.getMessage(), e);
		}

		return skuPriceList;
	}

	/**
	 * get prices for sku on basis of vendor and sku id
	 * 
	 * @param vendorId
	 * @param skuId
	 * @return
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<VendorServiceOfferPrice> getVendorSkuDetail(Integer vendorId, Long skuId) throws DataServiceException {
		logger.info("Inisde getSkuList() of D2CProviderPortalDaoImpl");
		List<VendorServiceOfferPrice> skuPriceList = null;
		try {
			String hql = "from VendorServiceOfferPrice vsop where vsop.vendorServiceOffering.providerFirm.id=:vendorId "
					+ "and vsop.vendorServiceOffering.buyerSku.buyerId in (:buyerId) and vsop.vendorServiceOffering.buyerSku.skuId = :skuId";

			Query query = getEntityManager().createQuery(hql);
			query.setParameter("vendorId", vendorId);
			query.setParameter("buyerId", getBuyerIds());
			query.setParameter("skuId", skuId);

			skuPriceList = query.getResultList();
		} catch (Exception e) {
			logger.debug("Exception in D2CDaoImpl.getSkuList due to " + e.getMessage());
			throw new DataServiceException(e.getMessage(), e);
		}
		return skuPriceList;
	}

	/**
	 * get dispatch locations
	 */
	public List<ProviderFirmLocation> getDispatchLocation(String vendorId) throws DataServiceException {
		logger.info("start getDispatchLocation of D2CProviderPortalDaoImpl");
		String hql = "from ProviderFirmLocation pfl where pfl.id.providerFirm.id=:vendor_id and pfl.id.location.lookupLocationTypes.id=:location_type_id";
		List<ProviderFirmLocation> providerLocs = null;
		try {
			Query vendorLoc = getEntityManager().createQuery(hql);
			vendorLoc.setParameter("vendor_id", Integer.parseInt(vendorId));
			vendorLoc.setParameter("location_type_id", 4);
			providerLocs = vendorLoc.getResultList();

		} catch (Exception excep) {
			logger.error("Error while retriving dispatch locations", excep);
			throw new DataServiceException("Error while retriving dispatch locations", excep);
		}
		logger.info("end getDispatchLocation of D2CProviderPortalDaoImpl");
		return providerLocs;
	}

	/**
	 * get service offerings details
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSku> getBuyerSkusDetail(String vendorId, List<Integer> primaryIndustries) throws DataServiceException {
		logger.info("start getBuyerSkusDetail of D2CProviderPortalDaoImpl");

		StringBuilder hqlBuyerSku = new StringBuilder("from BuyerSkuD2C buyerSku where buyerSku.buyerId in (:buyerId)");
		if (null != primaryIndustries && primaryIndustries.size() > 0) {
			hqlBuyerSku.append(" and buyerSku.buyerSoTemplate.lookupPrimaryIndustry.id in (:primaryIndustries)");
		}

		List<BuyerSku> buyerSkus = null;
		try {
			Query queryBuyerSku = getEntityManager().createQuery(hqlBuyerSku.toString());
			// serviceOfferingQuery.setParameter("vendorId", Integer.parseInt(vendorId));
			queryBuyerSku.setParameter("buyerId", getBuyerIds());
			if (null != primaryIndustries && primaryIndustries.size() > 0) {
				queryBuyerSku.setParameter("primaryIndustries", primaryIndustries);
			}
			buyerSkus = queryBuyerSku.getResultList();

			logger.debug("Total buyer skus fetched: " + (null != buyerSkus ? buyerSkus.size() : 0));
		} catch (Exception excep) {
			logger.error("Error while retriving getBuyerSkusDetail", excep);
			throw new DataServiceException("Error while retriving getBuyerSkusDetail", excep);
		}
		logger.info("end getBuyerSkusDetail of D2CProviderPortalDaoImpl");

		return buyerSkus;
	}

	/**
	 * addUpdateDelete dispatch loc
	 */
	public boolean addUpdateDispatchLocation(DispatchLocationVO dispatchLocationVO) throws DataServiceException {
		logger.info("start addUpdateDispatchLocation of D2CProviderPortalDaoImpl");
		boolean isUpdated = false;
		boolean validData = false;
		try {
			if (!dispatchLocationVO.isUpdateDeleteFlag()) {
				// delete
				String providerFirmLocationHQL = "from ProviderFirmLocation pfl where pfl.id.location.locnId=:locn_id and pfl.id.providerFirm.id=:vendor_id";
				Query providerFirmLocationQuery = getEntityManager().createQuery(providerFirmLocationHQL);
				providerFirmLocationQuery.setParameter("locn_id", dispatchLocationVO.getLocnId());
				providerFirmLocationQuery.setParameter("vendor_id", Integer.parseInt(dispatchLocationVO.getVendorId()));

				ProviderFirmLocation providerFirmLocation = (ProviderFirmLocation) providerFirmLocationQuery.getSingleResult();

				if (null != providerFirmLocation) {
					if (null == providerFirmLocation.getModifiedBy() || null == providerFirmLocation.getModifiedDate()
							|| null == providerFirmLocation.getCreatedDate()) {
						providerFirmLocation.setModifiedBy(dispatchLocationVO.getVendorId());
						providerFirmLocation.setModifiedDate(new Date());
						providerFirmLocation.setCreatedDate(new Date());
						logger.warn("location modifiedby is empty. Setting it to default.");

						getEntityManager().persist(providerFirmLocation);
						getEntityManager().flush();
					}

					// logger.info("removing location.");
					// getEntityManager().remove(providerFirmLocation.getId().getLocation());
					// getEntityManager().flush();

					// delete coverage
					Coverage coverage = new Coverage();
					coverage.setLocId(dispatchLocationVO.getLocnId().toString());
					coverage.setVendorId(dispatchLocationVO.getVendorId());
					if (null != coverage && null != coverage.getLocId() && null != coverage.getVendorId()) {
						this.deleteCoverageArea(coverage);
					}

					getEntityManager().remove(providerFirmLocation);
					isUpdated = true;
				}
			} else {
				Date date = new Date();

				ProviderFirmLocation providerFirmLocation = null;

				if (null != dispatchLocationVO.getLocnId() && !dispatchLocationVO.getLocnId().equals(new Integer(0))) {
					// update

					String providerFirmLocationHQL = "from ProviderFirmLocation pfl where pfl.id.location.locnId=:locn_id";
					Query providerFirmLocationQuery = getEntityManager().createQuery(providerFirmLocationHQL);
					providerFirmLocationQuery.setParameter("locn_id", dispatchLocationVO.getLocnId());

					providerFirmLocation = (ProviderFirmLocation) providerFirmLocationQuery.getSingleResult();

					if (null == providerFirmLocation) {
						return isUpdated;
					}

				} else {
					// insert

					String hql = "from ProviderFirm pf where pf.id=:vendor_id";
					Query query = getEntityManager().createQuery(hql);
					query.setParameter("vendor_id", Integer.parseInt(dispatchLocationVO.getVendorId()));

					ProviderFirm firm = (ProviderFirm) query.getSingleResult();

					providerFirmLocation = new ProviderFirmLocation();
					providerFirmLocation.setId(new ProviderFirmLocationPk());

					Location location = new Location();

					String loc_hql = "from LookupLocationTypes llt where llt.id=:id";
					Query loc_query = getEntityManager().createQuery(loc_hql);
					loc_query.setParameter("id", 4);
					LookupLocationTypes lookupLocationTypes = (LookupLocationTypes) loc_query.getSingleResult();
					location.setLookupLocationTypes(lookupLocationTypes);

					location.setGisLatitude(0.0);
					location.setGisLongitude(0.0);
					location.setCreatedDate(date);

					providerFirmLocation.getId().setLocation(location);
					providerFirmLocation.getId().setProviderFirm(firm);
				}

				String zipCode_hql = "from LookupZipGeocode zc where zc.zip=:zipcode";
				String lookUpState_hql = "from LookupStates ls where ls.id=:state_cd";

				providerFirmLocation.setModifiedBy(dispatchLocationVO.getVendorId());
				providerFirmLocation.setModifiedDate(date);
				providerFirmLocation.getId().getLocation().setLocnName(dispatchLocationVO.getLocName());
				providerFirmLocation.getId().getLocation().setStreet1(dispatchLocationVO.getStreet1());
				providerFirmLocation.getId().getLocation().setStreet2(dispatchLocationVO.getStreet2());
				providerFirmLocation.getId().getLocation().setModifiedDate(date);
				providerFirmLocation.getId().getLocation().setModifiedBy(dispatchLocationVO.getVendorId());
				providerFirmLocation.getId().getLocation().setCity(dispatchLocationVO.getCity());

				if (!dispatchLocationVO.getZip().equals(providerFirmLocation.getId().getLocation().getZip())) {

					logger.info("geting zip info from zipGeoCode table");

					Query zipCode_query = getEntityManager().createQuery(zipCode_hql);
					zipCode_query.setParameter("zipcode", dispatchLocationVO.getZip());

					LookupZipGeocode lookupZipGeocode = (LookupZipGeocode) zipCode_query.getSingleResult();

					if (null != lookupZipGeocode && null != lookupZipGeocode.getZip()) {
						logger.debug("values from ZipGeoCode: " + lookupZipGeocode.getZip() + " " + lookupZipGeocode.getCity());
						logger.info("geting zip info from lookupStates table");

						Query lookUpState_query = getEntityManager().createQuery(lookUpState_hql);
						lookUpState_query.setParameter("state_cd", lookupZipGeocode.getStateCd());

						LookupStates lookupStates = (LookupStates) lookUpState_query.getSingleResult();

						if (null != lookupStates) {
							logger.debug("values from state_cd: " + lookupStates.getDescription());

							providerFirmLocation.getId().getLocation().setLookupStates(lookupStates);
							providerFirmLocation.getId().getLocation().setZip(lookupZipGeocode.getZip());
							// providerFirmLocation.getId().getLocation().setCity(lookupZipGeocode.getCity());
							validData = true;
						}
					}
				} else {
					validData = true;
				}

				if (validData) {
					getEntityManager().persist(providerFirmLocation.getId().getLocation());
					getEntityManager().persist(providerFirmLocation);

					isUpdated = (null != providerFirmLocation.getId() && null != providerFirmLocation.getId().getLocation());
				}
			}
		} catch (Exception excep) {
			logger.error("Error while saving dispatch location", excep);
			throw new DataServiceException("Error while saving dispatch location", excep);
		}
		logger.info("end addUpdateDispatchLocation of D2CProviderPortalDaoImpl");
		return isUpdated;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void updateDailyLimitAndPrice(VendorServiceOfferingVO vendorServiceOfferVo, Integer vendorId, String vendorName)
			throws DataServiceException {
		logger.info("Inside D2CProviderPortalDaoImpl.updateDailyLimitAndPrice()");
		try {
			List<VendorServiceOfferPriceVo> vendorServiceOfferPriceVo = vendorServiceOfferVo.getVendorServiceOfferPriceVo();

			String hql2 = "from VendorServiceOfferPrice vsop where vsop.vendorServiceOffering.providerFirm.id=:vendor_id"
					+ " and vsop.vendorServiceOffering.buyerSku.buyerSoTemplate.lookupPrimaryIndustry.id = :primaryIndustryId";
			Query query = getEntityManager().createQuery(hql2);
			query.setParameter("vendor_id", vendorId);

			if (null != vendorServiceOfferVo.getPrimaryIndustryId()) {
				query.setParameter("primaryIndustryId", Integer.parseInt(vendorServiceOfferVo.getPrimaryIndustryId()));
			} else {
				logger.warn("Primary industry was blank.");
				return;
			}

			List<VendorServiceOfferPrice> existingPriceDBList = query.getResultList();

			// delete
			for (VendorServiceOfferPrice vsopDB : existingPriceDBList) {
				boolean modifiedPrice = false;
				for (VendorServiceOfferPriceVo vendorServiceOfferingPriceVoUI : vendorServiceOfferPriceVo) {
					if (vsopDB.getId().equals(vendorServiceOfferingPriceVoUI.getId())) {
						// update daily limit and price
						if (!(vsopDB.getPrice().equals(vendorServiceOfferingPriceVoUI.getPrice()) && vsopDB.getDailyLimit().equals(
								vendorServiceOfferingPriceVoUI.getDailyLimit()))) {
							vsopDB.setDailyLimit(vendorServiceOfferingPriceVoUI.getDailyLimit());
							vsopDB.setPrice(vendorServiceOfferingPriceVoUI.getPrice());
							vsopDB.setModifiedBy(vendorName);
							getEntityManager().merge(vsopDB);
						}
						modifiedPrice = true;
						break;
					}
				}
				if (!modifiedPrice) {
					// delete price
					getEntityManager().remove(vsopDB);
					// delete vendorServiceOfferingDB
					getEntityManager().remove(vsopDB.getVendorServiceOffering());
				}
			}

			for (VendorServiceOfferPriceVo vendorServiceOfferingPriceVoUI : vendorServiceOfferPriceVo) {
				// insert new
				if (null == vendorServiceOfferingPriceVoUI.getId() && null == vendorServiceOfferingPriceVoUI.getVendorOfferingId()) {

					String hqlBuyerSkus = "from BuyerSkuD2C bsku where bsku.skuId=:skuId";
					Query queryBuyerSku = getEntityManager().createQuery(hqlBuyerSkus);
					queryBuyerSku.setParameter("skuId", Long.valueOf(vendorServiceOfferingPriceVoUI.getSkuId()));
					BuyerSku buyerSkus = (BuyerSku) queryBuyerSku.getSingleResult();

					String hqlVendorHdr = "from ProviderFirm pf where pf.id=:vendorId";
					Query queryVendorHdr = getEntityManager().createQuery(hqlVendorHdr);
					queryVendorHdr.setParameter("vendorId", vendorId);
					ProviderFirm providerFirm = (ProviderFirm) queryVendorHdr.getSingleResult();

					// save vendor service offering
					VendorServiceOffering vendorServiceOffering = new VendorServiceOffering();
					vendorServiceOffering.setBuyerSku(buyerSkus);
					vendorServiceOffering.setProviderFirm(providerFirm);
					getEntityManager().persist(vendorServiceOffering);

					// save sku price
					VendorServiceOfferPrice vendorServiceOfferPrice = new VendorServiceOfferPrice();
					vendorServiceOfferPrice.setDailyLimit(vendorServiceOfferingPriceVoUI.getDailyLimit());
					vendorServiceOfferPrice.setPrice(vendorServiceOfferingPriceVoUI.getPrice());
					vendorServiceOfferPrice.setVendorServiceOffering(vendorServiceOffering);
					getEntityManager().persist(vendorServiceOfferPrice);
				}
			}

		} catch (Exception e) {
			logger.error("Exception in d2CProviderPortalDaoImpl.updateDailyLimitAndPrice due to " + e.getMessage());
			throw new DataServiceException("Exception in d2CProviderPortalDaoImpl.updateDailyLimitAndPrice", e);
		}
		logger.info("Exiting D2CProviderPortalDaoImpl.updateDailyLimitAndPrice");
	}

	public void updateCoverageZipCodes(List<ZipCodesData> zipCodesData, Coverage coverage) {
		logger.info("entering updateCoverageZipCodes of D2CProviderPortalDaoImpl");

		try {
			Coverage coverageWithId = getCoverageById(coverage);
			deleteServiceZipCodes(coverageWithId);
			coverageWithId.setCoverageRadius(coverage.getCoverageRadius());
			// updateCoverageRadius(coverageWithId);
			List<ZipCodesData> zipCodesWithCvgId = new ArrayList<ZipCodesData>();
			if (null != coverageWithId && null != coverageWithId.getId()) {
				for (ZipCodesData ZipCodeData : zipCodesData) {
					ZipCodeData.setCoverageId(coverageWithId.getId());
					zipCodesWithCvgId.add(ZipCodeData);

				}

			}
			Object o = getSqlMapClientTemplate().insert("coverage.updateCoverageZipCodes", zipCodesWithCvgId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("exiting updateCoverageZipCodes of D2CProviderPortalDaoImpl");
	}

	public void saveZipCoverageRadius(Coverage coverage) {
		try {
			logger.info("entering saveZipCoverageRadius of D2CProviderPortalDaoImpl");
			Object o = getSqlMapClientTemplate().insert("coverage.saveZipCoverageRadius", coverage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("exiting saveZipCoverageRadius of D2CProviderPortalDaoImpl");
	}

	public List<CoverageLocationVO> getCoverageAreas(String vendorIdStr) {
		List<CoverageLocationVO> coverageLocationVOList = new ArrayList<CoverageLocationVO>();
		List<CoverageLocationVO> coverageLocationVOListWithServiceCount = new ArrayList<CoverageLocationVO>();

		try {
			logger.info("entering getCoverageAreas of D2CProviderPortalDaoImpl");

			coverageLocationVOList = getSqlMapClientTemplate().queryForList("coverage.getCoverageAreas", vendorIdStr);

			for (CoverageLocationVO tmpCvgLoc : coverageLocationVOList) {
				tmpCvgLoc.setCount(this.getServiceAreaCount(tmpCvgLoc.getId()));
				coverageLocationVOListWithServiceCount.add(tmpCvgLoc);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("exiting getCoverageAreas of D2CProviderPortalDaoImpl");
		return coverageLocationVOListWithServiceCount;
	}

	public void updateCoverageRadius(Coverage coverage) {
		try {

			getSqlMapClientTemplate().update("coverage.updateCoverageRadius", coverage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Integer getServiceAreaCount(String id) {
		logger.info("entering getServiceAreaCount of D2CProviderPortalDaoImpl");
		Integer count = 0;
		try {
			count = (Integer) getSqlMapClientTemplate().queryForObject("coverage.getServiceAreaCount", id);
		} catch (Exception e) {
			logger.info("Execption occured while executing  getServiceAreaCount of D2CProviderPortalDaoImpl", e);

		}

		logger.info("exiting getServiceAreaCount of D2CProviderPortalDaoImpl");
		return count;

	}

	public void deleteServiceZipCodes(Coverage coverage) {
		logger.info("entering deleteServiceZipCodes of D2CProviderPortalDaoImpl");

		try {

			getSqlMapClientTemplate().delete("coverage.deleteServiceZipCodes", coverage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("exiting deleteServiceZipCodes of D2CProviderPortalDaoImpl");

	}

	public List<ZipCodesData> getDBSavedServiceAreaZipCodes(Coverage coverage) {
		List<ZipCodesData> dbZipCodesData = new ArrayList<ZipCodesData>();

		try {
			logger.info("entering getDBSavedServiceAreaZipCodes of D2CProviderPortalDaoImpl");

			dbZipCodesData = getSqlMapClientTemplate().queryForList("coverage.getDBSavedServiceAreaZipCodes", coverage);
		} catch (Exception ex) {
			logger.error("Exception occured while executing getDBSavedServiceAreaZipCodes of D2CProviderPortalDaoImpl", ex);
		}
		logger.info("exiting getDBSavedServiceAreaZipCodes of D2CProviderPortalDaoImpl");

		return dbZipCodesData;

	}

	public Coverage getCoverageById(Coverage coverage) {
		Coverage cvgWithId = null;

		try {
			cvgWithId = new Coverage();
			logger.info("entering getDBSavedServiceAreaZipCodes of D2CProviderPortalDaoImpl");

			cvgWithId = (Coverage) getSqlMapClientTemplate().queryForObject("coverage.getCoverageById", coverage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("exiting getDBSavedServiceAreaZipCodes of D2CProviderPortalDaoImpl");

		return cvgWithId;
	}

	public boolean updateVendorData(String data, Integer vendorId, String flag) throws DataServiceException {
		logger.info("Inside D2CProviderPortalDaoImpl.updateVendorData");

		boolean success = false;
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("vendorId", vendorId);
			params.put("data", data);
			if (flag.equalsIgnoreCase("businessName")) {
				getSqlMapClientTemplate().update("updateVendorData.businessName", params);
			} else if (flag.equalsIgnoreCase("primaryIndustry")) {
				getSqlMapClientTemplate().update("updateVendorData.primaryIndustryName", params);
			} else if (flag.equalsIgnoreCase("yearsInBusiness")) {
				Double dateData = Double.parseDouble(data);
				String convertedDate = getYearsInBusiness(dateData);
				HashMap<String, Object> dateToUpdate = new HashMap<String, Object>();
				dateToUpdate.put("convertedDate", convertedDate);
				dateToUpdate.put("vendorId", vendorId);
				getSqlMapClientTemplate().update("updateVendorData.yearsInBusiness", dateToUpdate);
			} else if (flag.equalsIgnoreCase("overview")) {
				getSqlMapClientTemplate().update("updateVendorData.overview", params);

			}

			success = true;
		} catch (Exception e) {
			logger.error("Exception during updating of updateVendorData", e);
			throw new DataServiceException("Exception in d2CProviderPortalDaoImpl.updateVendorData", e);
		}

		logger.info("Exiting D2CProviderPortalDaoImpl.updateVendorData");
		return success;
	}

	/**
	 * insert sku data into service offerings table for vendor
	 * 
	 * @throws DataServiceException
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public boolean updateVendorServiceOfferings(String vendorIdStr) throws DataServiceException {
		logger.info("start D2CProviderPortalDaoImpl.updateVendorServiceOfferings");

		boolean isSuccess = false;

		String hqlBuyerSkus = "from BuyerSkuD2C bsku where bsku.buyerId in (:buyer_id)";
		Query queryBuyerSku = getEntityManager().createQuery(hqlBuyerSkus);
		queryBuyerSku.setParameter("buyer_id", getBuyerIds());

		String hqlVendorServiceOfferings = "from VendorServiceOffering vso where vso.providerFirm.id=:vendorId and vso.buyerSku.buyerId in (:buyerId)";
		Query queryVendorServiceOfferings = getEntityManager().createQuery(hqlVendorServiceOfferings);
		queryVendorServiceOfferings.setParameter("buyerId", getBuyerIds());
		queryVendorServiceOfferings.setParameter("vendorId", Integer.parseInt(vendorIdStr));

		String hqlVendorHdr = "from ProviderFirm pf where pf.id=:vendorId";
		Query queryVendorHdr = getEntityManager().createQuery(hqlVendorHdr);
		queryVendorHdr.setParameter("vendorId", Integer.parseInt(vendorIdStr));

		try {

			List<BuyerSku> buyerSkus = queryBuyerSku.getResultList();
			List<VendorServiceOffering> vendorServiceOfferings = queryVendorServiceOfferings.getResultList();
			ProviderFirm providerFirm = (ProviderFirm) queryVendorHdr.getSingleResult();

			Set<VendorServiceOffering> existingServiceOfferings = new HashSet<VendorServiceOffering>();

			if (null != buyerSkus && !buyerSkus.isEmpty()) {
				for (BuyerSku buyerSkuTemp : buyerSkus) {
					boolean isVSOPresentForBSku = false;
					if (null != vendorServiceOfferings && !vendorServiceOfferings.isEmpty()) {
						for (VendorServiceOffering vendorServiceOfferingTemp : vendorServiceOfferings) {
							if (vendorServiceOfferingTemp.getBuyerSku().getSkuId().equals(buyerSkuTemp.getSkuId())) {
								isVSOPresentForBSku = true;
								existingServiceOfferings.add(vendorServiceOfferingTemp);
								break;
							}
						}
					}

					if (!isVSOPresentForBSku) {
						// insert new vendor svc offerings

						VendorServiceOffering vendorServiceOfferingNew = new VendorServiceOffering();
						vendorServiceOfferingNew.setBuyerSku(buyerSkuTemp);
						vendorServiceOfferingNew.setProviderFirm(providerFirm);

						getEntityManager().persist(vendorServiceOfferingNew);
					}
				}
			}

			// remove offering for deleted skus
			// for (VendorServiceOffering vendorServiceOfferingTemp :
			// vendorServiceOfferings) {
			// if
			// (!existingServiceOfferings.contains(vendorServiceOfferingTemp)) {
			// getEntityManager().remove(vendorServiceOfferingTemp);
			// }
			// }

			isSuccess = true;

		} catch (Exception e) {
			logger.error("Exception during updating of VendorServiceOfferings", e);
			throw new DataServiceException("Exception in d2CProviderPortalDaoImpl.updateVendorServiceOfferings", e);
		}

		logger.info("end D2CProviderPortalDaoImpl.updateVendorServiceOfferings");
		return isSuccess;
	}

	/**
	 * get list of firm details on basis of SkuIds and Zip code
	 * 
	 * @param skuId
	 * @param zip
	 * @return
	 * @throws DataServiceException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<D2CProviderAPIVO> getFirmDetailsList(D2CPortalAPIVORequest d2CPortalAPIVO) throws DataServiceException {
		logger.info("start D2CProviderPortalDaoImpl.getFirmDetailsList");

		List<D2CProviderAPIVO> basicFirmDetailsVO = new ArrayList<D2CProviderAPIVO>();
		String skuId = d2CPortalAPIVO.getSkuId();
		String zip = d2CPortalAPIVO.getZipCode();
		String date = d2CPortalAPIVO.getDate();
		String firmId = d2CPortalAPIVO.getFirmId();

		try {
			HashMap<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("zipCode", zip);
			// 3- Sears Providers Approved, 34- ServiceLive Approved.
			paramData.put("slApprovedState", Arrays.asList(3, 34)); 
			
			paramData.put("buyerId", Long.valueOf(BUYER_ID_3333));
			paramData.put("skuId", skuId);
			
			//SLT-2848
			List<Integer> firmIds = d2CPortalAPIVO.getFirmIdsFromDb();
			if(firmIds != null){
				paramData.put("firmIds", firmIds);
			}

			basicFirmDetailsVO = getSqlMapClientTemplate().queryForList("firm.limitedListOfFirmsData", paramData);

			if (!StringUtils.isEmpty(date) && basicFirmDetailsVO.size() > 0) {
				logger.debug("date is: " + date);

				paramData.put("date", date);
				paramData.put("vendorList", basicFirmDetailsVO);
				paramData.put("wfStateId", Arrays.asList(150, 155));
				List<D2CProviderAPIVO> basicFirmCountDetailsVO = getSqlMapClientTemplate().queryForList("firm.filterAndCountSOOrderforSKU",
						paramData);

				for (D2CProviderAPIVO d2cProviderAPIVO : basicFirmDetailsVO) {
					for (D2CProviderAPIVO d2cProviderCount : basicFirmCountDetailsVO) {
						if (d2cProviderAPIVO.getFirmId().equals(d2cProviderCount.getFirmId())) {
							d2cProviderAPIVO.setAcceptedCount(d2cProviderCount.getAcceptedCount());
							break;
						}
					}
				}
			}

			logger.debug("Length Of firmList" + basicFirmDetailsVO.size());
		} catch (Exception e) {
			logger.error("Exception during fetching firm detail list", e);
			throw new DataServiceException("Exception in d2CProviderPortalDaoImpl.getFirmDetailsList", e);
		}
		logger.info("end D2CProviderPortalDaoImpl.getFirmDetailsList");
		return basicFirmDetailsVO;
	}

	// Added To convert yearOfBusiness of Date from double to Date
	private String getYearsInBusiness(Double updatedDate) {
		int months = (int) (updatedDate * 12);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, months * -1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(cal.getTime());
		return date;
	}

	public String deleteCoverageArea(Coverage coverage) {
		logger.info("entering deleteCoverageArea of D2CProviderPortalDaoImpl");
		Coverage coverageWithId = getCoverageById(coverage);
		String response = "FAIL";

		try {
			deleteServiceZipCodes(coverageWithId);
			getSqlMapClientTemplate().delete("coverage.deleteCoverageArea", coverageWithId);
			response = "success";
		} catch (Exception ex) {
			logger.error("Error occured while deleting coverage area:", ex);

		}
		logger.info("exiting deleteCoverageArea of D2CProviderPortalDaoImpl");

		return response;
	}

	@SuppressWarnings("unchecked")
	public List<LookupServiceDay> getLookupServiceDays() {

		logger.info("start of getLookupServiceDays of D2CProviderPortalDaoImpl");
		List<LookupServiceDay> lookupServiceDay = null;
		String hqlLookupQuery = "from LookupServiceDay order by id asc";
		try {
			Query query = getEntityManager().createQuery(hqlLookupQuery);
			lookupServiceDay = query.getResultList();

		} catch (Exception ex) {
			logger.error("Error occured while getLookupServiceDays of D2CProviderPortalDaoImpl", ex);
		}
		logger.info("end of getLookupServiceDays of D2CProviderPortalDaoImpl");
		return lookupServiceDay;
	}

	@SuppressWarnings("unchecked")
	public List<LookupServiceRatePeriod> getLookupServiceRatePeriod() {
		logger.info("start of getLookupServiceRatePeriod of D2CProviderPortalDaoImpl");
		List<LookupServiceRatePeriod> lookupServiceRatePeriods = null;
		String hqlLookupQuery2 = "from LookupServiceRatePeriod order by id asc";
		try {
			Query query = getEntityManager().createQuery(hqlLookupQuery2);
			lookupServiceRatePeriods = query.getResultList();
		} catch (Exception ex) {
			logger.error("Error occured while getLookupServiceRatePeriod of D2CProviderPortalDaoImpl", ex);
		}
		logger.info("end of getLookupServiceRatePeriod of D2CProviderPortalDaoImpl");
		return lookupServiceRatePeriods;
	}

	@SuppressWarnings("unchecked")
	public List<VendorCategoryPriceCard> getRateCardPrice(Integer primaryIndustryId, Integer vendorId) {
		logger.info("start of getRateCardPrice of D2CProviderPortalDaoImpl");
		List<VendorCategoryPriceCard> vendorCategoryPriceCard = null;
		String hqlRateCardPriceQuery = "from VendorCategoryPriceCard vcpc where vcpc.vendorCategoryMap.lookupPrimaryIndustry.id=:primaryIndustryId and vcpc.vendorCategoryMap.providerFirm.id=:vendorId";

		try {
			Query queryRateCardPrice = getEntityManager().createQuery(hqlRateCardPriceQuery);
			queryRateCardPrice.setParameter("primaryIndustryId", primaryIndustryId);
			queryRateCardPrice.setParameter("vendorId", vendorId);
			vendorCategoryPriceCard = queryRateCardPrice.getResultList();
		} catch (Exception ex) {
			logger.error("Error occured while getRateCardPrice of D2CProviderPortalDaoImpl", ex);
		}
		logger.info("end of getRateCardPrice of D2CProviderPortalDaoImpl");
		return vendorCategoryPriceCard;
	}

	public List<VendorServiceOfferedOn> getOffersOn(String vendorIds, Integer primaryIndustryId) throws DataServiceException {
		logger.info("Inside of D2CProviderPortalDaoImpl.getOffersOn");
		List<VendorServiceOfferedOn> getOffersOnData = new ArrayList<VendorServiceOfferedOn>();
		String getOffersOn = "from VendorServiceOfferedOn vsoo where vsoo.vendorCategoryMap.providerFirm.id=:vendor_id "
				+ "and vsoo.vendorCategoryMap.lookupPrimaryIndustry.id=:primaryIndustries";
		try {
			Query query = getEntityManager().createQuery(getOffersOn);

			query.setParameter("vendor_id", Integer.parseInt(vendorIds));
			query.setParameter("primaryIndustries", primaryIndustryId);

			getOffersOnData = query.getResultList();
		} catch (Exception e) {
			logger.error("Exception during fetching the VendorServiceOfferedOn", e);
		}
		logger.debug("Size of VendorServiceOfferedOn according to LuPffersOn data:" + getOffersOnData.size());
		logger.info("Exiting D2CProviderPortalDaoImpl.getOffersOn");
		return getOffersOnData;
	}

	/**
	 * get list of lookupOffers details.
	 * 
	 * @throws DataServiceException
	 */
	public List<LuOffersOn> getLuoffersOnDetails() throws DataServiceException {
		logger.info("Inside of D2CProviderPortalDaoImpl.getLuoffersOnDetails");
		List<LuOffersOn> luOffersOn = new ArrayList<LuOffersOn>();
		String offersOn = "From LuOffersOn";
		try {
			Query query = getEntityManager().createQuery(offersOn);
			luOffersOn = query.getResultList();
		} catch (Exception e) {
			logger.error("Error during fetching offersOn Lokkup data", e);
		}
		logger.info("Exiting  D2CProviderPortalDaoImpl.getLuoffersOnDetails");
		logger.debug("Size Of LuOffers on data:" + luOffersOn.size());
		return luOffersOn;
	}

	/**
	 * updateOrDeleteOffersOn on basis of VendorServiceOfferedOnVO data
	 * 
	 * @param VendorServiceOfferedOnVO
	 *            data
	 * @return
	 * @throws DataServiceException
	 */
	public String updateOrDeleteOffersOn(VendorServiceOfferedOnVO vendorServiceOfferedOnVO) throws DataServiceException {
		logger.info("Inside of D2CProviderPortalDaoImpl.updateOrDeleteOffersOn");
		logger.debug("vendorServiceOfferedOnVO data from UI :" + vendorServiceOfferedOnVO);
		VendorServiceOfferedOn vendorServiceOfferedOnData = new VendorServiceOfferedOn();
		List<VendorServiceOfferedOn> vendorServiceOfferedOn = new ArrayList<VendorServiceOfferedOn>();
		LuOffersOn luOffersOn = new LuOffersOn();

		Integer primaryIndustryId = Integer.parseInt(vendorServiceOfferedOnVO.getPrimaryIndustry());
		Integer vendorId = Integer.parseInt(vendorServiceOfferedOnVO.getVendorId());
		LuOffersOnVO luoffersOnVo = vendorServiceOfferedOnVO.getLuOffersOnVOforUpdate();

		// Insert
		try {
			if (vendorServiceOfferedOnVO.isOffersOnFlag()) {
				luOffersOn.setId(luoffersOnVo.getId());
				luOffersOn.setName(luoffersOnVo.getName());
				vendorServiceOfferedOnData.setLuOffersOn(luOffersOn);
				List<VendorCategoryMap> vendorCategoryMap = getVendorCategoryMapDetails(primaryIndustryId, vendorId);
				vendorServiceOfferedOnData.setVendorCategoryMap(vendorCategoryMap.get(0));
				getEntityManager().persist(vendorServiceOfferedOnData);
				return "success";
			}
		} catch (Exception e) {
			logger.error("Error in persisting OffersOn data", e);

		}

		// Remove
		try {
			if (!vendorServiceOfferedOnVO.isOffersOnFlag()) {
				String VendorOfferedOnHql = "from VendorServiceOfferedOn vsoo where vsoo.vendorCategoryMap.providerFirm.id= :vendor_id "
						+ "and vsoo.vendorCategoryMap.lookupPrimaryIndustry.id= :primaryIndustries and vsoo.luOffersOn.id= :luOffersOnId";
				Query query = getEntityManager().createQuery(VendorOfferedOnHql);
				query.setParameter("primaryIndustries", primaryIndustryId);
				query.setParameter("vendor_id", vendorId);
				query.setParameter("luOffersOnId", luoffersOnVo.getId());
				vendorServiceOfferedOn = query.getResultList();
				if (null != vendorServiceOfferedOn) {
					getEntityManager().remove(vendorServiceOfferedOn.get(0));
					return "success";
				}
			}
		} catch (Exception e) {
			logger.error("Error in updateOrDeleteOffersOn during remove data", e);
		}
		logger.info("Exiting of D2CProviderPortalDaoImpl.updateOrDeleteOffersOn");
		return "Sucess";
	}

	public String saveRateCardPrice(RateCardRequestVO rateCardRequestVO, Integer vendorId) {
		logger.info("start of saveRateCardPrice of D2CProviderPortalDaoImpl");
		String rateCardAdded = "";

		List<VendorCategoryMap> vendorCategoryMapList = getVendorCategoryMapDetails(
				Integer.parseInt(rateCardRequestVO.getPrimaryIndustry()), vendorId);
		List<VendorCategoryPriceCard> vendorCategoryPriceCards = getRateCardPrice(Integer.parseInt(rateCardRequestVO.getPrimaryIndustry()),
				vendorId);
		List<RateCardJsonRequest> rateCardVOs = rateCardRequestVO.getRateCardPriceUIModal();
		List<LookupServiceDay> svcDays = getLookupServiceDays();
		List<LookupServiceRatePeriod> svcRatePeriods = getLookupServiceRatePeriod();

		for (VendorCategoryPriceCard vendorCategoryPriceCard : vendorCategoryPriceCards) {
			boolean modifiedPrice = false;
			for (RateCardJsonRequest rateCardVO : rateCardVOs) {
				if (vendorCategoryPriceCard.getId().equals(rateCardVO.getVendorCategoryPriceId())) {
					// update price
					if (!vendorCategoryPriceCard.getPrice().equals(rateCardVO.getPrice())) {
						vendorCategoryPriceCard.setPrice(Double.parseDouble(rateCardVO.getPrice()));
						getEntityManager().merge(vendorCategoryPriceCard);
					}

					modifiedPrice = true;
					break;
				}
			}
			if (!modifiedPrice) {
				// delete price
				getEntityManager().remove(vendorCategoryPriceCard);
			}

		}

		for (RateCardJsonRequest rateCardVO : rateCardVOs) {
			if (null == rateCardVO.getVendorCategoryPriceId()) {
				// Insert
				VendorCategoryPriceCard categoryPriceCard = new VendorCategoryPriceCard();
				for (LookupServiceDay svcDay : svcDays) {
					if (svcDay.getId().equals(rateCardVO.getServiceDayVOs().getId())) {
						categoryPriceCard.setServiceDay(svcDay);
						break;
					}
				}
				for (LookupServiceRatePeriod svcRatePeriod : svcRatePeriods) {
					if (svcRatePeriod.getId().equals(rateCardVO.getServiceRatePeriodVO().getId())) {
						categoryPriceCard.setServiceRatePeriod(svcRatePeriod);
						break;
					}
				}
				categoryPriceCard.setVendorCategoryMap(vendorCategoryMapList.get(0));
				categoryPriceCard.setPrice(Double.parseDouble(rateCardVO.getPrice()));

				try {
					getEntityManager().merge(categoryPriceCard);
					rateCardAdded = "success";
				} catch (Exception ex) {
					logger.error("Error occured while saveRateCardPrice of D2CProviderPortalDaoImpl", ex);
				}
			}
		}
		logger.info("end of saveRateCardPrice of D2CProviderPortalDaoImpl");
		return rateCardAdded;
	}

	// Generic method for vendorCategoryMap data
	@SuppressWarnings("unchecked")
	private List<VendorCategoryMap> getVendorCategoryMapDetails(Integer primaryIndustryId, Integer vendorId) {
		logger.info("Inside D2CProviderPortalDaoImpl.getVendorCategoryMapDetails");
		List<VendorCategoryMap> vendorCategoryMapList = new ArrayList<VendorCategoryMap>();
		String hqlvendorCategoryMapQuery = "from VendorCategoryMap vcm where vcm.lookupPrimaryIndustry.id=:primaryIndustryId and vcm.providerFirm.id=:vendorId";
		try {
			Query queryRateCardPrice = getEntityManager().createQuery(hqlvendorCategoryMapQuery);
			queryRateCardPrice.setParameter("primaryIndustryId", primaryIndustryId);
			queryRateCardPrice.setParameter("vendorId", vendorId);
			vendorCategoryMapList = queryRateCardPrice.getResultList();
		} catch (Exception ex) {
			logger.error("Error occured while getRateCardPrice of D2CProviderPortalDaoImpl", ex);
		}

		if (vendorCategoryMapList.size() < 1) {
			VendorCategoryMap vendorCategoryMap = new VendorCategoryMap();

			// lookupPrimaryIndustry
			LookupPrimaryIndustry lookupPrimaryIndustry = new LookupPrimaryIndustry();
			lookupPrimaryIndustry.setId(primaryIndustryId);
			vendorCategoryMap.setLookupPrimaryIndustry(lookupPrimaryIndustry);

			// provider Firm
			ProviderFirm providerFirm = new ProviderFirm();
			providerFirm.setId(vendorId);
			vendorCategoryMap.setProviderFirm(providerFirm);
			try {
				getEntityManager().persist(vendorCategoryMap);
				vendorCategoryMapList.add(vendorCategoryMap);
				return vendorCategoryMapList;
			} catch (Exception ex) {
				logger.error("Error occured while saving vandorCategoryMap of D2CProviderPortalDaoImpl", ex);
			}
		}
		logger.info("Exiting D2CProviderPortalDaoImpl.getVendorCategoryMapDetails");
		return vendorCategoryMapList;
	}

	public void refresh(Object entity) throws Exception {
		getEntityManager().refresh(entity);
	}

	public EntityManager getEntityManager() {
		return em;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public List<String> getStates() {
		logger.info("entering getStates of D2CProviderPortalDaoImpl");

		List<String> states = null;
		try {
			states = getSqlMapClient().queryForList("coverage.getStates", null);
		} catch (Exception ex) {
			logger.error("Error occured while deleting coverage area:", ex);
		}
		logger.info("exiting getStates of D2CProviderPortalDaoImpl");

		return states;

	}

	// Below code added for new API to get provider list(Standard+Non-Standard)
	/**
	 * get list of firm details on basis of SkuIds and Zip code new API call
	 * 
	 * @param skuId
	 * @param zip
	 * @return
	 * @throws DataServiceException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<D2CProviderAPIVO> getFirmDetailsWithRetailPriceList(D2CPortalAPIVORequest d2CPortalAPIVO) throws DataServiceException {
		logger.info("start D2CProviderPortalDaoImpl.getFirmDetailsList");

		List<D2CProviderAPIVO> basicFirmDetailsVO = new ArrayList<D2CProviderAPIVO>();
		String skuId = d2CPortalAPIVO.getSkuId();
		String zip = d2CPortalAPIVO.getZipCode();
		String date = d2CPortalAPIVO.getDate();
		String firmId = d2CPortalAPIVO.getFirmId();
		int providerListCount = d2CPortalAPIVO.getCount();
		try {
			HashMap<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("zipCode", zip);
			// 3- Sears Providers  Approved,  34- ServiceLive Approved.
			paramData.put("slApprovedState", Arrays.asList(3, 34)); 
			// paramData.put("buyerId", Long.valueOf(BUYER_ID_3333));
			paramData.put("buyerId", Long.valueOf(d2CPortalAPIVO.getBuyerId()));
			paramData.put("skuId", skuId);
			paramData.put("count", providerListCount);
			if (!StringUtils.isEmpty(firmId)) {
				paramData.put("firmId", Integer.parseInt(firmId));
			}

			double marketIndex = Double.valueOf(1.0);
			if (d2CPortalAPIVO.isMarketIndexFlag()) {
				Double buyerMarketIndex = (Double) getSqlMapClientTemplate().queryForObject("firm.getMarketIndex", paramData);
				if (null != buyerMarketIndex) {
					paramData.put("marketIndex", buyerMarketIndex);
				} else {
					paramData.put("marketIndex", marketIndex);
				}
			}

			basicFirmDetailsVO = getSqlMapClientTemplate().queryForList("firm.limitedListOfFirmsDataWithBuyerRetailPrice", paramData);

			if (!StringUtils.isEmpty(date) && basicFirmDetailsVO.size() > 0) {
				logger.debug("date is: " + date);

				paramData.put("date", date);
				paramData.put("vendorList", basicFirmDetailsVO);
				paramData.put("wfStateId", Arrays.asList(150, 155));
				List<D2CProviderAPIVO> basicFirmCountDetailsVO = getSqlMapClientTemplate().queryForList("firm.filterAndCountSOOrderforSKU",
						paramData);

				for (D2CProviderAPIVO d2cProviderAPIVO : basicFirmDetailsVO) {
					for (D2CProviderAPIVO d2cProviderCount : basicFirmCountDetailsVO) {
						if (d2cProviderAPIVO.getFirmId().equals(d2cProviderCount.getFirmId())) {
							d2cProviderAPIVO.setAcceptedCount(d2cProviderCount.getAcceptedCount());
							break;
						}
					}
				}
			}

			logger.debug("Length Of firmList" + basicFirmDetailsVO.size());

		} catch (Exception e) {
			logger.error("Exception during fetching firm detail list", e);
			throw new DataServiceException("Exception in d2CProviderPortalDaoImpl.getFirmDetailsList", e);
		}
		logger.info("end D2CProviderPortalDaoImpl.getFirmDetailsList");
		return basicFirmDetailsVO;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Long> getSoCompletedList(List<String> vendorIds) {
		/*
		 * paramData.put("firmIds", firmIdAndDetailsMap.keySet()); Map<Integer,
		 * BigDecimal> aggregateRating = null; try{ aggregateRating =
		 * (Map<Integer,
		 * BigDecimal>)getSqlMapClient().queryForMap("getProviderFirmRating.query"
		 * , firmIdAndDetailsMap.keySet(),"vendor_id", "rating"); }
		 * catch(Exception e){ throw new DataServiceException(
		 * "Exception in ServiceOrderDaoImpl.getNoOfEmployees.query() due to " +
		 * e.getMessage(), e); }
		 */
		Map<Integer, Long> soCompletedMap = null;
		soCompletedMap = (Map<Integer, Long>) getSqlMapClientTemplate().queryForMap("firm.getSoCompletedList", vendorIds, "firmId",
				"soCompleted");
		return soCompletedMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, BigDecimal> getFirmRatings(List<String> vendorIds) {
		Map<Integer, BigDecimal> aggregateRating = null;
		aggregateRating = (Map<Integer, BigDecimal>) getSqlMapClientTemplate().queryForMap("getProviderFirmRating.query", vendorIds,
				"vendor_id", "rating");
		return aggregateRating;
	}

	@SuppressWarnings("unchecked")
	public Double getBuyerRetailPrice(String sku, String buyerId) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("sku", sku);
		// paramData.put("buyerId", Long.valueOf(BUYER_ID_3333));
		paramData.put("buyerId", Long.valueOf(buyerId));
		Double buyerRetailPrice = (Double) getSqlMapClientTemplate().queryForObject("firm.getBuyerRetailPrice", paramData);
		if (null == buyerRetailPrice) {
			buyerRetailPrice = 0.0;
		}
		return buyerRetailPrice;
	}

	// END

	public List<D2CProviderAPIVO> getFirmDetailsListByMultipleCriteriaSearch(D2CPortalAPIVORequest d2CPortalAPIVO)
			throws DataServiceException {

		logger.info("---START: DAO - D2CProviderPortalDaoImpl.getFirmDetailsListByMultipleCriteriaSearch");

		List<D2CProviderAPIVO> basicFirmDetailsVO = new ArrayList<D2CProviderAPIVO>();
		String skuId = d2CPortalAPIVO.getSkuId();
		String zip = d2CPortalAPIVO.getZipCode();
		String date = d2CPortalAPIVO.getDate();
		String firmId = d2CPortalAPIVO.getFirmId();
		int providerListCount = d2CPortalAPIVO.getCount();

		try {

			HashMap<String, Object> paramData = new HashMap<String, Object>();
			paramData.put("zipCode", zip);
			// 3- Sears Providers  Approved,  34- ServiceLive Approved.
			paramData.put("slApprovedState", Arrays.asList(3, 34)); 
			paramData.put("buyerId", Long.valueOf(d2CPortalAPIVO.getBuyerId()));
			paramData.put("skuId", skuId);
			paramData.put("count", providerListCount);
			
			List<Integer> firmIds = d2CPortalAPIVO.getFirmIdsFromDb();
			if(firmIds != null){
				paramData.put("firmId", firmIds);
			}

			if (d2CPortalAPIVO.isMarketIndexFlag()) {
				Double buyerMarketIndex = (Double) getSqlMapClientTemplate().queryForObject("firm.getMarketIndex", paramData);
				if (null != buyerMarketIndex) {
					paramData.put("marketIndex", buyerMarketIndex);
				} else {
					paramData.put("marketIndex", Double.valueOf(1.0));
				}
			}

			// 1. Firm sears/SL approved 2. Dispatch zone(coverage area) 3. Has agreed to SKU pricing
			basicFirmDetailsVO = getSqlMapClientTemplate().queryForList("firm.limitedListOfFirmsDataWithBuyerRetailPrice", paramData);
			logger.info("Provider firm size from criteria 1:2:3 = " + basicFirmDetailsVO.size());

			// 4. SPN Member: this.buyer_id and member of any SPN created by this buyer.
			if (null != basicFirmDetailsVO && basicFirmDetailsVO.size() > 0) {

				// do not clear by default
				Boolean clearAllFirmsFetchedFromBasicFirmDetailsVO = false;

				// get spn_id(SKU -> template -> spn_id(getIsNewSpn = true))
				Integer spnId = getSpnIdBuyerSoTemplateBySkuBuyerId(paramData);
				logger.info("spnId = " + spnId + "...with SKU = " + skuId + "...and Buyer_Id = " + d2CPortalAPIVO.getBuyerId());

				if (null != spnId) {

					paramData.put("vendorList", basicFirmDetailsVO);
					paramData.put("spnId", spnId);
					paramData.put("providerWfState", SPNConstants.SPN_MEMBER);
					// 4. SPN Member:
					List<D2CProviderAPIVO> filteredSpnMemberMarketReadyProviderList = getSqlMapClientTemplate().queryForList(
							"firm.spnMembers", paramData);
					logger.info("Provider firm size from criteria 4 (SPN Member) = " + filteredSpnMemberMarketReadyProviderList.size());

						if (null != filteredSpnMemberMarketReadyProviderList && filteredSpnMemberMarketReadyProviderList.size() > 0) {

							List<D2CProviderAPIVO> tempBasicFirmDetailsVO = new ArrayList<D2CProviderAPIVO>();

							// 1. Firm sears/SL approved 2. Dispatch zone(coverage area) 3. Has agreed to SKU pricing
							for (int i = 0; i < basicFirmDetailsVO.size(); i++) {

								// 4 and 5 ("SPN Member" and "Atleast 1 provider in Approved(Market ready) status")
								for (int j = 0; j < filteredSpnMemberMarketReadyProviderList.size(); j++) {

									String basicFirmId = ((basicFirmDetailsVO.get(i).getFirmId()).toString()).trim();
									String filteredSpnMemberMarketReadyFirmId = ((filteredSpnMemberMarketReadyProviderList.get(j)
											.getFirmId()).toString()).trim();

									logger.info("---- basicFirmId = " + basicFirmId);
									logger.info("---- filteredSpnMemberMarketReadyFirmId = " + filteredSpnMemberMarketReadyFirmId);

									// if found add that object to tempList
									if (basicFirmId.equals(filteredSpnMemberMarketReadyFirmId)) {
										logger.info("---- Both firm id's are equal. ----");
										tempBasicFirmDetailsVO.add(basicFirmDetailsVO.get(i));
									}
								}
							}

							// finally assign the temp list to the main/final list.
							if (tempBasicFirmDetailsVO.size() > 0) {
								basicFirmDetailsVO = tempBasicFirmDetailsVO;
							} else {
								logger.info("cleared due to no matching firm id's found.");
								clearAllFirmsFetchedFromBasicFirmDetailsVO = true;
							}
						
					} else {
						logger.info("cleared due to filteredSpnMemberMarketReadyProviderList criteria 4 (SPN Member)");
						clearAllFirmsFetchedFromBasicFirmDetailsVO = true;
					}
				} else {
					logger.info("cleared due to SPN id is null");
					clearAllFirmsFetchedFromBasicFirmDetailsVO = true;
				}

				// data should get fetched in all the given below steps:
				// 4. SPN Member
				// 5. Atleast 1 provider in Approved(Market ready) status.
				if (clearAllFirmsFetchedFromBasicFirmDetailsVO) {
					basicFirmDetailsVO.clear();
				}
			}

			if (!StringUtils.isEmpty(date) && basicFirmDetailsVO.size() > 0) {
				logger.debug("date is: " + date);

				paramData.put("date", date);
				paramData.put("vendorList", basicFirmDetailsVO);
				paramData.put("wfStateId", Arrays.asList(150, 155));
				List<D2CProviderAPIVO> basicFirmCountDetailsVO = getSqlMapClientTemplate().queryForList("firm.filterAndCountSOOrderforSKU",
						paramData);

				for (D2CProviderAPIVO d2cProviderAPIVO : basicFirmDetailsVO) {
					for (D2CProviderAPIVO d2cProviderCount : basicFirmCountDetailsVO) {
						if (d2cProviderAPIVO.getFirmId().equals(d2cProviderCount.getFirmId())) {
							d2cProviderAPIVO.setAcceptedCount(d2cProviderCount.getAcceptedCount());
							break;
						}
					}
				}
			}

			logger.debug("Length Of firmList: " + basicFirmDetailsVO.size());

		} catch (Exception e) {
			logger.error("Exception during fetching firm detail list", e);
			throw new DataServiceException("Exception in d2CProviderPortalDaoImpl.getFirmDetailsList", e);
		}

		logger.info("---END: DAO - D2CProviderPortalDaoImpl.getFirmDetailsListByMultipleCriteriaSearch");

		return basicFirmDetailsVO;

	}

	public List<D2CProviderAPIVO> getFirmDetailsWithBuyerPrice(String buyerId, String sku, String zip, List<String> firmList)
			throws DataServiceException {
		logger.info("---START: DAO - D2CProviderPortalDaoImpl.getFirmDetailsWithBuyerPrice");

		List<D2CProviderAPIVO> basicFirmDetailsVO = new ArrayList<D2CProviderAPIVO>();
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("buyerId", buyerId);
		paramData.put("zipCode", zip);
		paramData.put("skuId", sku);
		paramData.put("firmList", firmList);
		paramData.put("count", firmList.size());
		
		try
		{
			basicFirmDetailsVO = getSqlMapClientTemplate().queryForList("firm.firmsDataWithBuyerRetailPrice", paramData);
		}catch (Exception e) {
			logger.error("Exception during fetching firm detail list", e);
			throw new DataServiceException("Exception in d2CProviderPortalDaoImpl.getFirmDetailsWithBuyerPrice", e);
		}
		logger.info("---END: DAO - D2CProviderPortalDaoImpl.getFirmDetailsWithBuyerPrice");

		return basicFirmDetailsVO;
	}
	
	public Integer getSpnIdBuyerSoTemplateBySkuBuyerId(final HashMap<String, Object> skuBuyerIdMap) {

		Integer spnId = null;
		BuyerSOTemplateDTO dto = null;

		BuyerSOTemplateVO vo = (BuyerSOTemplateVO) getSqlMapClientTemplate().queryForObject("query.getBuyerSoTemplateBySkuBuyerId",
				skuBuyerIdMap);
		if (null != vo) {
			dto = getBuyerSOTemplateXMLAsDTO(vo.getTemplateData());
			if (null != dto) {
				if (null != dto.getIsNewSpn()) {
					// spn should be new(true)... i.e: from spnet_hdr table
					if (dto.getIsNewSpn()) {
						spnId = dto.getSpnId();
					}
				}
			}
		}
		return spnId;
	}

	private BuyerSOTemplateDTO getBuyerSOTemplateXMLAsDTO(String xml) {

		XStream xstream = new XStream(new DomDriver());
		BuyerSOTemplateDTO dto = null;
		try {
			xstream.alias("buyerTemplate", BuyerSOTemplateDTO.class);
			dto = (BuyerSOTemplateDTO) xstream.fromXML(xml);

		} catch (Exception e) {
			logger.error("Exception loading alternate buyer contact");
		}
		return dto;
	}

	@Override
	public List<Integer> getSkillNodeIdForSku(String sku) {
		
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		
		paramData.put("buyerId", Long.valueOf(BUYER_ID_3333));
		paramData.put("sku", sku);
		
		//SLT-2848 Fetch skill node ids for the SKU
		List<Integer> skillNodeId = getSqlMapClientTemplate().queryForList("getSkillNodeIdsForSku", paramData);
		return skillNodeId;
	}
}