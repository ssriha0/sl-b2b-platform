package com.newco.marketplace.servicesImpl.d2cproviderportal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.PrimaryIndustryDetailsVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.Coverage;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.CoverageLocationVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.LuOffersOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.PrimaryIndustryOffersOnDetailsVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ServiceDayVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ServiceRatePeriodVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesData;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.d2cproviderportal.ID2CProviderPortalDao;
import com.newco.marketplace.persistence.iDao.document.IProviderProfileDocumentDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILicensesAndCertDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.services.d2cproviderportal.ID2CProviderPortalService;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.web.d2cproviderportal.utils.RateCardPriceMapper;
import com.newco.marketplace.web.d2cproviderportal.utils.ServiceDayMapper;
import com.newco.marketplace.web.d2cproviderportal.utils.ServiceRatePeriodMapper;
import com.newco.marketplace.web.d2cproviderportal.utils.SkuDetailMapper;
import com.servicelive.domain.common.Location;
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
 * service impl for D2C Provider portal flow
 * 
 * @author sboddu
 * 
 */
public class D2CProviderPortalServiceImpl implements ID2CProviderPortalService {

	private static final Logger logger = Logger.getLogger(D2CProviderPortalServiceImpl.class);

	private final SkuDetailMapper skuDetailMapper = new SkuDetailMapper();
	private final ServiceDayMapper serviceDayMapper = new ServiceDayMapper();  
	private final ServiceRatePeriodMapper serviceRatePeriodMapper = new ServiceRatePeriodMapper();
	private final RateCardPriceMapper rateCardPriceMapper=new RateCardPriceMapper();
	private ID2CProviderPortalDao d2cProviderPortalDao;
	private IVendorHdrDao vendorHdrDao;
	private ILocationDao locationDao;
	private IContactDao iContactDao;
	private ILookupDAO lookupDAO;
	private IVendorResourceDao vendorResourceDao;
	private ILicensesAndCertDao licensesAndCertDao;
	private IProviderProfileDocumentDao profileDocDAO;
	private IDocumentBO documentBO;
	
	//SLT-2848 Code changes START
		private IProviderSearchBO providerSearchBO;
		
		public IProviderSearchBO getProviderSearchBO() {
			return providerSearchBO;
		}

		public void setProviderSearchBO(IProviderSearchBO providerSearchBO) {
			this.providerSearchBO = providerSearchBO;
		}
		
		public D2CPortalAPIVORequest populateVendorDetails(D2CPortalAPIVORequest d2CPortalAPIVO){
			
			//Fetch skill node ids for the SKU
			ArrayList<Integer> skillNodeIds = (ArrayList<Integer>) d2cProviderPortalDao.getSkillNodeIdForSku(d2CPortalAPIVO.getSkuId());
			
			//Create Search criteria
			ProviderSearchCriteriaVO searchCriteria = new ProviderSearchCriteriaVO();
			searchCriteria.setSkillNodeIds(skillNodeIds);
			LocationVO serviceLocation = new LocationVO();
			serviceLocation.setZip(d2CPortalAPIVO.getZipCode());
			searchCriteria.setServiceLocation(serviceLocation);
			
			String firmIdFromReq = d2CPortalAPIVO.getFirmId();
			if(firmIdFromReq != null){
				Integer firmId = Integer.parseInt(firmIdFromReq);
				if(firmId != null){
					List<Integer> firmIds = new ArrayList<Integer>();
					firmIds.add(firmId);
					searchCriteria.setFirmId(firmIds);
				}
			}
			
			//Get provider list based on zip and skills
			ArrayList<ProviderResultVO> providerList = providerSearchBO.getProviderList(searchCriteria);
			
			//Populate D2C VO from providerList
			List<Integer> firmIds = providerList.stream().map(ProviderResultVO::getVendorID)
					.collect(Collectors.toList());
			
			d2CPortalAPIVO.setFirmIdsFromDb(firmIds);
			
			return d2CPortalAPIVO;
		}
		//SLT-2848 Code changes END

	/**
	 * get vendor basic details for profile page
	 * 
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public CompanyProfileVO getCompleteProfile(int vendorId) throws BusinessServiceException {
		logger.info("start getCompleteProfile of D2CProviderPortaServiceImpl");
		CompanyProfileVO companyProfileVO = new CompanyProfileVO();

		VendorHdr vendorHdr = new VendorHdr();
		com.newco.marketplace.vo.provider.Location locationVO = new com.newco.marketplace.vo.provider.Location();
		Contact contact = new Contact();
		Contact tmpContact = null;
		VendorResource vendorResource = new VendorResource();
		vendorHdr.setVendorId(vendorId);
		List<LicensesAndCertVO> licensesList = null;

		try {
			vendorHdr = vendorHdrDao.query(vendorHdr);

			if (vendorHdr != null) {
				companyProfileVO.setServiceLiveStatus(vendorHdr.getServiceliveStatus());
				companyProfileVO.setBusinessFax(vendorHdr.getBusinessFax());
				companyProfileVO.setBusinessName(vendorHdr.getBusinessName());
				companyProfileVO.setBusinessStartDate(vendorHdr.getBusinessStartDate());
				companyProfileVO.setVendorId(vendorHdr.getVendorId());
				companyProfileVO.setBusinessPhone(vendorHdr.getBusinessPhone());
				companyProfileVO.setDbaName(vendorHdr.getDbaName());
				companyProfileVO.setDunsNo(vendorHdr.getDunsNo());
				companyProfileVO.setForeignOwnedInd(vendorHdr.getForeignOwnedInd());
				companyProfileVO.setBusinessDesc(vendorHdr.getBusinessDesc());

				if (vendorHdr.getPrimaryIndustryId() > 0) {
					companyProfileVO.setPrimaryIndustryId(vendorHdr.getPrimaryIndustryId());
					companyProfileVO.setPrimaryIndustry(lookupDAO.getPrimaryIndustry(vendorHdr.getPrimaryIndustryId()));
				} else {
					logger.warn("Error while retriving primary industry with id: " + vendorHdr.getPrimaryIndustryId());
				}

				// under a firm-- Start
				Integer noOfEmp = 0;
				/*
				 * try { noOfEmp =
				 * vendorHdrDao.getApprovedmembers(vendorHdr.getVendorId()); }
				 * catch (Exception e) {
				 * logger.error("Exception in fetching noOf employees under a firm"
				 * + e.getMessage()); throw new BusinessServiceException(
				 * "Exception in fetching noOf employees under a firm" ,e); }
				 */
				companyProfileVO.setNoOfEmployees(noOfEmp);
				// under a firm-- End

				companyProfileVO.setMemberSince(vendorHdr.getCreatedDate());

				locationVO = locationDao.queryVendorLocation(vendorHdr.getVendorId());
				if (locationVO != null) {
					companyProfileVO.setBusCity(locationVO.getCity());
					companyProfileVO.setBusStateCd(locationVO.getStateCd());
					companyProfileVO.setBusStreet1(locationVO.getStreet1());
					companyProfileVO.setBusStreet2(locationVO.getStreet2());
					companyProfileVO.setBusZip(locationVO.getZip());
				} else {
					logger.warn("Error while retriving vendor location: " + vendorHdr.getVendorId());
				}
				vendorResource = vendorResourceDao.getQueryByVendorId(vendorHdr.getVendorId());

				if (vendorResource != null) {
					// get the location details

					// get the contact details
					contact.setContactId(vendorResource.getContactId());
					tmpContact = iContactDao.query(contact);
					if (tmpContact != null) {
						companyProfileVO.setAltEmail(tmpContact.getAltEmail());
						companyProfileVO.setEmail(tmpContact.getEmail());
						companyProfileVO.setFirstName(tmpContact.getFirstName());
						companyProfileVO.setLastName(tmpContact.getLastName());
						companyProfileVO.setMi(tmpContact.getMi());
						if (tmpContact.getRole() != null && tmpContact.getRole() != "0") {
							companyProfileVO.setRole(lookupDAO.getCompanyRole(Integer.parseInt(tmpContact.getRole())));
						}
						companyProfileVO.setSuffix(tmpContact.getSuffix());
						companyProfileVO.setTitle(tmpContact.getTitle());
					} else {
						logger.warn("Error while retriving contact details: " + contact.getContactId());
					}
				} else {
					logger.warn("Error while retriving vendor resource: " + vendorHdr.getVendorId());
				}

				licensesList = licensesAndCertDao.getVendorCredentialsList(vendorHdr.getVendorId());
				if (licensesList != null) {
					for (LicensesAndCertVO license : licensesList) {
						switch (license.getWfStateId()) {
						case 13:
							license.setWfStatus("Pending Approval");
							break;
						case 14:
							license.setWfStatus("Approved");
							break;
						case 25:
							license.setWfStatus("Out of Compliance");
							break;
						case 210:
							license.setWfStatus("Reviewed");
							break;
						default:
							logger.warn("No matching wf state: " + license.getWfStateId());
							license.setWfStatus("");
						}
					}
					companyProfileVO.setLicensesList(licensesList);
				} else {
					logger.warn("Error while retriving licenses: " + vendorHdr.getVendorId());
				}

				Date businessStartDate = companyProfileVO.getBusinessStartDate();
				if (businessStartDate != null) {
					long numMilBusStart;
					long numMilToday;
					long dateDiff = 0;
					float numYears;
					String numYearsStr;
					Date todayDate = new Date();

					numMilBusStart = businessStartDate.getTime();
					numMilToday = todayDate.getTime();

					dateDiff = numMilToday - numMilBusStart;

					numYears = (float) dateDiff / 1000 / 60 / 60 / 24 / 365;
					numYearsStr = String.valueOf(numYears);

					companyProfileVO.setYearsInBusiness(numYearsStr);
				} else {
					logger.warn("Error while converting years in business: " + businessStartDate);
				}
			} else {
				logger.warn("No data from vendorhdr table");
			}
		} catch (Exception ex) {
			logger.error("General Exception D2CProviderPortalServiceImpl.getCompleteProfile() due to" + ex.getMessage());
			throw new BusinessServiceException("General Exception D2CProviderPortalServiceImpl.getCompleteProfile() due to "
					+ ex.getMessage());
		}
		logger.info("end getCompleteProfile of D2CProviderPortaServiceImpl");
		return companyProfileVO;
	}

	/**
	 * This method will fetch primary industries
	 */
	@Transactional
	public List<LookupVO> getPrimaryIndustryServiceImpl(Integer vendorId) throws BusinessServiceException {
		logger.info("start getPrimaryIndustryServiceImpl of D2CProviderPortaServiceImpl");
		List<LookupVO> primaryIndustries = new ArrayList<LookupVO>();

		try {
			List<LookupPrimaryIndustry> lookupPrimaryIndustries = d2cProviderPortalDao.getPrimaryIndustryDaoImpl(vendorId);
			logger.debug("Total count of primary industries : " + (null != lookupPrimaryIndustries ? lookupPrimaryIndustries.size() : 0));

			for (LookupPrimaryIndustry lookupPrimaryIndustry : lookupPrimaryIndustries) {
				LookupVO primaryIndustry = new LookupVO();
				primaryIndustry.setdId(lookupPrimaryIndustry.getId() + 0.0);
				primaryIndustry.setDescr(lookupPrimaryIndustry.getDescription());
				primaryIndustries.add(primaryIndustry);
			}

		} catch (DataServiceException e) {
			logger.debug("Exception in D2CProviderPortaServiceImpl.getPrimaryIndustryServiceImpl due to " + e.getMessage());
			throw new BusinessServiceException("Exception in fetching primary Industry", e);
		}

		logger.info("end getPrimaryIndustryServiceImpl of D2CProviderPortaServiceImpl");
		return primaryIndustries;
	}

	@Transactional
	public List<DispatchLocationVO> getDispatchLocation(String vendorId) throws BusinessServiceException {
		logger.info("start getDispatchLocation of D2CProviderPortaServiceImpl");
		List<DispatchLocationVO> locationVOs = new ArrayList<DispatchLocationVO>();

		try {
			List<ProviderFirmLocation> providerLocs = d2cProviderPortalDao.getDispatchLocation(vendorId);
			for (ProviderFirmLocation providerLoc : providerLocs) {
				if (null != providerLoc.getId() && null != providerLoc.getId().getLocation()) {
					Location loc = providerLoc.getId().getLocation();

					DispatchLocationVO locationVO = new DispatchLocationVO(loc.getLocnId(), loc.getZip(), loc.getCity(),
							(null != loc.getLookupStates()) ? loc.getLookupStates().getId() : "", loc.getStreet1(), loc.getStreet2(),
							loc.getCountry(), loc.getLocnName(),
							Double.toString(null != loc.getGisLatitude() ? loc.getGisLatitude() : 0.0), Double.toString(null != loc
									.getGisLongitude() ? loc.getGisLongitude() : 0.0), "", null, Integer.toString(providerLoc.getId()
									.getProviderFirm().getId()));
					locationVOs.add(locationVO);
				}
			}
		} catch (DataServiceException exception) {
			logger.error("Error occured while getDispatchLocation of D2CProviderPortaServiceImpl", exception);
			throw new BusinessServiceException("Error occured while getDispatchLocation of D2CProviderPortaServiceImpl", exception);
		}

		logger.info("end getDispatchLocation of D2CProviderPortaServiceImpl");
		return locationVOs;
	}

	/**
	 * save dispatch location
	 * 
	 * @throws BusinessServiceException
	 */
	@Transactional
	public boolean addUpdateDispatchLocation(DispatchLocationVO dispatchLocationVO) throws BusinessServiceException {
		logger.info("start addUpdateDispatchLocation of D2CProviderPortaServiceImpl");

		boolean isSaved;
		try {
			isSaved = d2cProviderPortalDao.addUpdateDispatchLocation(dispatchLocationVO);
		} catch (DataServiceException e) {
			logger.error("Exception occeured in addDispatchLocation" + e);
			throw new BusinessServiceException("Exception occeured in addUpdateDispatchLocation", e);
		}

		logger.info("start addUpdateDispatchLocation of D2CProviderPortaServiceImpl");
		return isSaved;
	}

	/**
	 * get service offering details
	 */
	@Transactional
	public PrimaryIndustryOffersOnDetailsVO getServiceOfferedDetail(String vendorId, List<Integer> primaryIndustries)
			throws BusinessServiceException {

		logger.info("start getServiceOfferedDetail of D2CProviderPortaServiceImpl");
	    List<LuOffersOn> luOffersOn = null;
		try {
			luOffersOn = d2cProviderPortalDao.getLuoffersOnDetails();
		} catch (DataServiceException e) {
			logger.error("Error during fetching LookUpOffersOn", e);
			e.printStackTrace();
		}
		List<PrimaryIndustryDetailsVO> serviceOfferDetailsData = new ArrayList<PrimaryIndustryDetailsVO>();
		PrimaryIndustryOffersOnDetailsVO serviceOfferDetails = new PrimaryIndustryOffersOnDetailsVO();
		//List listOfPrimaryIndustryData = new ArrayList();
		
		List<LuOffersOnVO> luOffersOnVO = new ArrayList<LuOffersOnVO>();
		if(null != luOffersOn){
		for(LuOffersOn luOffersOnData:luOffersOn){
			LuOffersOnVO luOffersOnVOdata = new LuOffersOnVO();
			luOffersOnVOdata.setId(luOffersOnData.getId());
			luOffersOnVOdata.setName(luOffersOnData.getName());
			luOffersOnVO.add(luOffersOnVOdata);
		}
	}
		try {

			Map<Integer, PrimaryIndustryDetailsVO> primaryIndustryMap = new HashMap<Integer, PrimaryIndustryDetailsVO>();
			// returns all buyer skus
			List<BuyerSku> buyerSkus = d2cProviderPortalDao.getBuyerSkusDetail(vendorId, primaryIndustries);
			for (BuyerSku buyerSku : buyerSkus) {
				// this will return single element list of vendor price details
				List<VendorServiceOfferPrice> vendorOfferings = d2cProviderPortalDao.getVendorSkuDetail(Integer.parseInt(vendorId),
						buyerSku.getSkuId());
				Integer primaryIndustryId = buyerSku.getBuyerSoTemplate().getLookupPrimaryIndustry().getId();
				List<VendorCategoryPriceCard> categoryPriceCard=d2cProviderPortalDao.getRateCardPrice(primaryIndustryId, Integer.parseInt(vendorId));
				if (!primaryIndustryMap.containsKey(primaryIndustryId)) {
					LookupPrimaryIndustry primaryIndustryDto = buyerSku.getBuyerSoTemplate().getLookupPrimaryIndustry();
					
					PrimaryIndustryDetailsVO primaryIndustryDetailTemp = new PrimaryIndustryDetailsVO();
					
					List<VendorServiceOfferedOn> vendorServiceOfferedOn = d2cProviderPortalDao.getOffersOn(vendorId, primaryIndustryId);
					VendorServiceOfferedOnVO vendorServiceOfferedOnVO = new VendorServiceOfferedOnVO();
					List<LuOffersOnVO> luOffersOnVOList = new ArrayList<LuOffersOnVO>();
					if (null != vendorServiceOfferedOn) {
						for (VendorServiceOfferedOn luOffersOndata : vendorServiceOfferedOn) {
							LuOffersOn luOffersOnDetails = luOffersOndata.getLuOffersOn();
							LuOffersOnVO LuOffersOnVoData = new LuOffersOnVO();
							LuOffersOnVoData.setId(luOffersOnDetails.getId());
							LuOffersOnVoData.setName(luOffersOnDetails.getName());
							luOffersOnVOList.add(LuOffersOnVoData);
							vendorServiceOfferedOnVO.setLuOffersOnVO(luOffersOnVOList);
							vendorServiceOfferedOnVO.setId(luOffersOndata.getId());
							//vendorServiceOfferedOnVO.setVendorCategoryMap(luOffersOndata.getVendorCategoryMap());
							
						}
					}
					primaryIndustryDetailTemp.setPrimaryIndustryId(primaryIndustryDto.getId());
					primaryIndustryDetailTemp.setPrimaryIndustryName(primaryIndustryDto.getDescription());
					primaryIndustryDetailTemp.setPrimaryIndustryType(primaryIndustryDto.getType());
					primaryIndustryDetailTemp.setServicesOfferedCount(0);
					primaryIndustryDetailTemp.setServicesOptedCount(0);
					primaryIndustryDetailTemp.setVendorServiceOfferedOnVO(vendorServiceOfferedOnVO);
					primaryIndustryDetailTemp.setLuOffersOnVO(luOffersOnVO);
					//primaryIndustryDetailTemp.setRateCardAdded(true);

					primaryIndustryMap.put(primaryIndustryId, primaryIndustryDetailTemp);
				}
			
				PrimaryIndustryDetailsVO primaryIndustryDetail = primaryIndustryMap.get(primaryIndustryId);
				primaryIndustryDetail.setServicesOfferedCount(primaryIndustryDetail.getServicesOfferedCount() + 1);

				if (null != vendorOfferings && vendorOfferings.size() > 0) {
					primaryIndustryDetail.setServicesOptedCount(primaryIndustryDetail.getServicesOptedCount() + 1);
				}
				
				if(null != categoryPriceCard && categoryPriceCard.size()>0){
					primaryIndustryDetail.setRateCardAdded(true);
				}

				logger.debug("New count of services offered for : " + primaryIndustryDetail.getPrimaryIndustryName() + " is : "
						+ primaryIndustryDetail.getServicesOfferedCount() + " and opted services count is :"
						+ primaryIndustryDetail.getServicesOptedCount());

			}
			
			//serviceOfferDetails.setLuOffersOnVO(luOffersOnVO);
			serviceOfferDetails.setPrimaryIndustryDetailsVO(new ArrayList<PrimaryIndustryDetailsVO>(primaryIndustryMap.values()));
		} catch (DataServiceException exception) {
			logger.error("Exception occeured in getServiceOfferedDetail of getServiceOfferedDetail due to " + exception);
			throw new BusinessServiceException("Exception occeured in getServiceOfferedDetail of getServiceOfferedDetail", exception);
		}

		logger.info("end getServiceOfferedDetail of D2CProviderPortaServiceImpl");
		return serviceOfferDetails;
	}

	@Transactional
	public List<BuyerSkuVO> getSkuDetailsForVendorAndPrimaryIndustries(Integer vendorIds, List<Integer> primaryIndustry)
			throws BusinessServiceException {
		logger.info("Inside getSkuDetailsForVendorAndPrimaryIndustries()");
		List<BuyerSkuVO> buyerSkuVO = new ArrayList<BuyerSkuVO>();
		try {
			List<BuyerSku> buyerSkus = d2cProviderPortalDao.getBuyerSkusDetail(Integer.toString(vendorIds), primaryIndustry);
			BuyerSkuVO skuVo = new BuyerSkuVO();
			List<VendorServiceOfferPrice> vendorOfferings = new ArrayList<VendorServiceOfferPrice>();
			for (BuyerSku buyerSku : buyerSkus) {
				// this will return single element list of vendor price details
				vendorOfferings = d2cProviderPortalDao.getVendorSkuDetail(vendorIds, buyerSku.getSkuId());
				skuVo = skuDetailMapper.convertDTOtoVO(vendorOfferings, buyerSku);
				buyerSkuVO.add(skuVo);
			}
		} catch (DataServiceException e) {
			logger.error("Error while retrieving sku details getSkuDetailsForVendorAndPrimaryIndustries()", e);
			throw new BusinessServiceException(e);
		}
		return buyerSkuVO;
	}
	
	/**
	 *  get not opted sku list for a vendor
	 * @throws BusinessServiceException 
	 */
	@Transactional
	public List<BuyerSkuVO> getNotOptedSKUList(String vendorId) throws BusinessServiceException {
		logger.info("Inside D2CProviderPortalServiceImpl.getNotOptedSKUList()");
		List<BuyerSkuVO> buyerSkuVO = new ArrayList<BuyerSkuVO>();
		try {
			int totalCount = 0;
			int notOptedCount = 0;
			List<BuyerSku> buyerSkus = d2cProviderPortalDao.getBuyerSkusDetail(vendorId, null);
			BuyerSkuVO skuVo = new BuyerSkuVO();
			List<VendorServiceOfferPrice> vendorOfferingsPrice = new ArrayList<VendorServiceOfferPrice>();
			for (BuyerSku buyerSku : buyerSkus) {
				totalCount++;
				
				// this will return single element list of vendor price details
				vendorOfferingsPrice = d2cProviderPortalDao.getVendorSkuDetail(Integer.parseInt(vendorId), buyerSku.getSkuId());
				
				// filter only not opted skus
				if (null == vendorOfferingsPrice || vendorOfferingsPrice.isEmpty()) {
					notOptedCount++;
					skuVo = skuDetailMapper.convertDTOtoVO(vendorOfferingsPrice, buyerSku);
					buyerSkuVO.add(skuVo);
				} else {
					logger.debug("Skipping already opted sku: "+ buyerSku.getSku()+ " for vendor: "+ vendorId);
				}
			}
			
			logger.info("Total sku count: "+ totalCount + " , Not opted count: "+ notOptedCount);
		} catch (DataServiceException e) {
			logger.error("Error while retrieving sku details D2CProviderPortalServiceImpl().getNotOptedSKUList", e);
			throw new BusinessServiceException(e);
		}
		
		logger.info("Inside D2CProviderPortalServiceImpl.getNotOptedSKUList()");
		return buyerSkuVO;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDailyLimitAndPrice(VendorServiceOfferingVO vendorServiceOfferVo, Integer vendorId, String vendorName)
			throws BusinessServiceException {
		logger.info("Inside D2CProviderPortalServiceImpl.updateDailyLimitAndPrice");
		try {
			d2cProviderPortalDao.updateDailyLimitAndPrice(vendorServiceOfferVo, vendorId, vendorName);
		} catch (Exception e) {
			logger.error("Exception in D2CProviderPortalServiceImpl.updateDailyLimitAndPrice()", e);
			throw new BusinessServiceException(e);
		}
	}

	public void updateCoverageZipCodes(List<ZipCodesData> zipCodesData, Coverage coverage) {
		logger.info("entering updateCoverageZipCodes of D2CProviderPortaServiceImpl");
		// List<ZipCodesData> zipCodesWithCvgId = new ArrayList<ZipCodesData>();

		// saveZipCoverageRadius(coverage);
		// Coverage coverageWithId = getCoverageById(coverage);

		/*
		 * if (null != coverageWithId && null != coverageWithId.getId()) { for
		 * (ZipCodesData ZipCodeData : zipCodesData) {
		 * ZipCodeData.setCoverageId(coverageWithId.getId());
		 * zipCodesWithCvgId.add(ZipCodeData);
		 * 
		 * }
		 * 
		 * }
		 */
		d2cProviderPortalDao.updateCoverageZipCodes(zipCodesData, coverage);
		logger.info("exiting updateCoverageZipCodes of D2CProviderPortaServiceImpl");
	}

	public void saveZipCoverageRadius(Coverage coverage) {
		logger.info("entering saveZipCoverageRadius of D2CProviderPortaServiceImpl");
		d2cProviderPortalDao.saveZipCoverageRadius(coverage);
		logger.info("exiting saveZipCoverageRadius of D2CProviderPortaServiceImpl");

	}

	public List<CoverageLocationVO> getCoverageAreas(String vendorIdStr) {

		return d2cProviderPortalDao.getCoverageAreas(vendorIdStr);
	}

	public List<ZipCodesData> getDBSavedServiceAreaZipCodes(Coverage coverage) {
		return d2cProviderPortalDao.getDBSavedServiceAreaZipCodes(coverage);
	}

	public Coverage getCoverageById(Coverage coverage) {
		return d2cProviderPortalDao.getCoverageById(coverage);
	}

	@Transactional
	public boolean updateVendorData(String data, Integer vendorId, String flag) throws BusinessServiceException {
		logger.info("Inside D2CProviderPortaServiceImpl.updatePrimaryIndustryData");

		boolean success = false;
		try {
			success = d2cProviderPortalDao.updateVendorData(data, vendorId, flag);
		} catch (DataServiceException e) {
			logger.error("Exception in updateVendorData", e);
			throw new BusinessServiceException("Exception occeured in updateVendorData of D2CProviderPortaServiceImpl", e);
		}
		logger.info("Exiting Inside D2CProviderPortaServiceImpl.updateVendorData");
		return success;
	}

	/**
	 * update service offering table
	 * 
	 * @throws BusinessServiceException
	 */
	@Transactional
	public boolean updateVendorServiceOfferings(String vendorIdStr) throws BusinessServiceException {
		logger.info("start D2CProviderPortaServiceImpl.updateVendorServiceOfferings");

		boolean isSuccess = false;
		try {
			isSuccess = d2cProviderPortalDao.updateVendorServiceOfferings(vendorIdStr);
		} catch (DataServiceException e) {
			logger.error("Exception occeured in updateVendorServiceOfferings of D2CProviderPortaServiceImpl due to " + e);
			throw new BusinessServiceException("Exception occeured in updateVendorServiceOfferings of D2CProviderPortaServiceImpl", e);
		}

		logger.info("end D2CProviderPortaServiceImpl.updateVendorServiceOfferings");
		return isSuccess;
	}

	// Added For Api call
	public List<D2CProviderAPIVO> getFirmDetailsList(D2CPortalAPIVORequest d2CPortalAPIVO) throws BusinessServiceException {
		logger.info("Inside D2CProviderPortaServiceImpl.getFirmDetailsList");
		List<D2CProviderAPIVO> basicFirmDetailsVO = null;

		try {
			//SLT-2848 Code changes 
			populateVendorDetails(d2CPortalAPIVO);
			List<Integer> firmIdsFromDB = d2CPortalAPIVO.getFirmIdsFromDb();
			if(firmIdsFromDB != null && firmIdsFromDB.size() > 0){
			basicFirmDetailsVO = d2cProviderPortalDao.getFirmDetailsList(d2CPortalAPIVO);
			}
		} catch (DataServiceException e) {
			logger.info("Exception in  D2CProviderPortaServiceImpl.getFirmDetailsList");
			e.printStackTrace();
		}

		logger.info("Exiting D2CProviderPortaServiceImpl.getFirmDetailsList");
		return basicFirmDetailsVO;
	}
	
	/**
	 * get LookupserviceDays and LookupServiceRateCardPeriod  
	 * 
	 * @throws BusinessServiceException
	 */
	@Transactional
	public RateCardVO getLookupServiceDays()
			throws BusinessServiceException {
		logger.info("start getLookupServiceDays() of D2CProviderPortaServiceImpl");
		RateCardVO rateCardVo = new RateCardVO();
		try {
			List<LookupServiceDay> serviceDays = d2cProviderPortalDao.getLookupServiceDays();
			//serviceDays
			List<ServiceDayVO>dayVOs=new ArrayList<ServiceDayVO>();
			for (LookupServiceDay serviceDay : serviceDays) {
				ServiceDayVO serviceDayVO=new ServiceDayVO();
				serviceDayVO = serviceDayMapper.convertDTOtoVO(serviceDayVO, serviceDay);
				dayVOs.add(serviceDayVO);
			}
			
			//serviceRate
			List<ServiceRatePeriodVO>serviceRatePeriodVOs=new ArrayList<ServiceRatePeriodVO>();
			List<LookupServiceRatePeriod> lookupServiceRatePeriods = d2cProviderPortalDao.getLookupServiceRatePeriod();
			for (LookupServiceRatePeriod lookupServiceRatePeriod : lookupServiceRatePeriods) {
				ServiceRatePeriodVO serviceRatePeriodVO=new ServiceRatePeriodVO();
				serviceRatePeriodVO = serviceRatePeriodMapper.convertDTOtoVO(serviceRatePeriodVO, lookupServiceRatePeriod);
				serviceRatePeriodVOs.add(serviceRatePeriodVO);
			}
			rateCardVo.setServiceDaysVOs(dayVOs);
			rateCardVo.setServiceRatePeriodVOs(serviceRatePeriodVOs);
			
		} catch (DataServiceException e) {
			logger.error("Error while retrieving rate card lookup details getLookupServiceDays()", e);
			throw new BusinessServiceException(e);
		}
		logger.info("end getLookupServiceDays() of D2CProviderPortaServiceImpl");
		return rateCardVo;
	}
	
	/**
	 * get RateCardPrice Details  
	 * 
	 * @throws BusinessServiceException
	 */
	@Transactional
	public List<RateCardVO> getRateCardPriceDetails(Integer primaryIndustryId,Integer vendorId)
			throws BusinessServiceException {
		logger.info("start getRateCardPriceDetails() of D2CProviderPortaServiceImpl");
	
		List<RateCardVO> rateCards = new ArrayList<RateCardVO>();
		try{
			List<VendorCategoryPriceCard> rateCardPrices = d2cProviderPortalDao.getRateCardPrice(primaryIndustryId, vendorId);
			for(VendorCategoryPriceCard rateCardPrice:rateCardPrices){
				RateCardVO rateCardVo = new RateCardVO();
				rateCardVo = rateCardPriceMapper.convertDTOtoVO(rateCardVo, rateCardPrice);
				rateCards.add(rateCardVo);
			}
		}catch(DataServiceException e) {
			logger.error("Error while retrieving rate card price details getRateCardPriceDetails()", e);
			throw new BusinessServiceException(e);
		}
		logger.info("end getRateCardPriceDetails() of D2CProviderPortaServiceImpl");
		return rateCards;
	}
	
	/**
	 * save RateCardPrice Details  
	 * 
	 * @throws BusinessServiceException
	 */
	@Transactional
	public String saveRateCardPrice(RateCardRequestVO rateCardRequestVO, Integer vendorId) throws BusinessServiceException{
		logger.info("start saveRateCardPrice() of D2CProviderPortaServiceImpl");
		String rateCardAdded="";
		try{
			 rateCardAdded=d2cProviderPortalDao.saveRateCardPrice(rateCardRequestVO, vendorId);
		}catch(DataServiceException e) {
			logger.error("Error while saving rate card price details saveRateCardPrice()", e);
			throw new BusinessServiceException(e);
		}
		logger.info("end saveRateCardPrice() of D2CProviderPortaServiceImpl");
		return rateCardAdded;
	}
	
	@Transactional
	public String updateOrDeleteOffersOn(VendorServiceOfferedOnVO vendorServiceOfferedOnVO)throws BusinessServiceException {
		logger.info("Inside D2CProviderPortaServiceImpl.updateOrDeleteOffersOn");
		String status = null;
		try {
			status = d2cProviderPortalDao.updateOrDeleteOffersOn(vendorServiceOfferedOnVO);
		} catch (DataServiceException e) {
			logger.error("error during updateOrDeleteOffersOn data", e);
			e.printStackTrace();
		}
		logger.info("Exiting D2CProviderPortaServiceImpl.updateOrDeleteOffersOn");
		return status;
	}
	public List<String> getStates(){
		
		return d2cProviderPortalDao.getStates();
	}
	public String deleteCoverageArea(Coverage coverage) {
		return d2cProviderPortalDao.deleteCoverageArea(coverage);
	}

	/**
	 * delete firm logo  
	 * 
	 *  @throws BusinessServiceException
	 */
	public String deleteFirmLogo(Integer vendorId) throws BusinessServiceException {
		logger.info("Inside D2CProviderPortaServiceImpl.deleteFirmLogo");
		VendorDocumentVO vendorLogo = null;
		try {
			vendorLogo = profileDocDAO.getLogoForFirm(vendorId);
		} catch (DataServiceException e) {
			logger.error("error during getting the Firm logo", e);
		}
			if(null!=vendorLogo && null!=vendorLogo.getDocumentId()){
				//Soft delete the firm logo.
				try {
					documentBO.deleteVendorDocument(vendorLogo.getDocumentId());
				} catch (DataServiceException e) {
					logger.error("error during soft deleting the Firm logo", e);
				}
				// delete the logo
				try {
					Integer i = profileDocDAO.delete(vendorLogo);
				} catch (DataServiceException e) {
					logger.error("error during deleting the Firm logo", e);
				}
		}
			logger.info("Exiting D2CProviderPortaServiceImpl.deleteFirmLogo");
		return "success";
	}
	
	//START Below code added for new API to get provider list(Standard+Non-Standard)
	
	public List<D2CProviderAPIVO> getFirmDetailsWithRetailPriceList(D2CPortalAPIVORequest d2CPortalAPIVO) throws BusinessServiceException {
		logger.info("Inside D2CProviderPortaServiceImpl.getFirmDetailsList");
		List<D2CProviderAPIVO> basicFirmDetailsVO = null;

		try {
			basicFirmDetailsVO = d2cProviderPortalDao.getFirmDetailsWithRetailPriceList(d2CPortalAPIVO);
		} catch (DataServiceException e) {
			logger.info("Exception in  D2CProviderPortaServiceImpl.getFirmDetailsList");
			e.printStackTrace();
		}

		logger.info("Exiting D2CProviderPortaServiceImpl.getFirmDetailsList");
		return basicFirmDetailsVO;
	}
	
	public Map<Integer, Long> getSoCompletedList(List<String> vendorIds){
		Map<Integer, Long> soCompletedMap = null;
		soCompletedMap = d2cProviderPortalDao.getSoCompletedList(vendorIds);
		return soCompletedMap;
	}
	
	public Map<Integer, BigDecimal> getFirmRatings(List<String> vendorIds){
		Map<Integer, BigDecimal> firmRatings = null;
		firmRatings = d2cProviderPortalDao.getFirmRatings(vendorIds);
		return firmRatings;
	}
	
	public Double getBuyerRetailPrice(String skuId, String buyerId){
		return d2cProviderPortalDao.getBuyerRetailPrice(skuId, buyerId);
	}
	
	//END
	
	public ID2CProviderPortalDao getD2cProviderPortalDao() {
		return d2cProviderPortalDao;
	}

	public void setD2cProviderPortalDao(ID2CProviderPortalDao d2cProviderPortalDao) {
		this.d2cProviderPortalDao = d2cProviderPortalDao;
	}

	public IVendorHdrDao getVendorHdrDao() {
		return vendorHdrDao;
	}

	public void setVendorHdrDao(IVendorHdrDao vendorHdrDao) {
		this.vendorHdrDao = vendorHdrDao;
	}

	public ILocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(ILocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public IContactDao getiContactDao() {
		return iContactDao;
	}

	public void setiContactDao(IContactDao iContactDao) {
		this.iContactDao = iContactDao;
	}

	public ILookupDAO getLookupDAO() {
		return lookupDAO;
	}

	public void setLookupDAO(ILookupDAO lookupDAO) {
		this.lookupDAO = lookupDAO;
	}

	public IVendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}

	public void setVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}

	public ILicensesAndCertDao getLicensesAndCertDao() {
		return licensesAndCertDao;
	}

	public void setLicensesAndCertDao(ILicensesAndCertDao licensesAndCertDao) {
		this.licensesAndCertDao = licensesAndCertDao;
	}

	public SkuDetailMapper getSkuDetailMapper() {
		return skuDetailMapper;
	}

	public IProviderProfileDocumentDao getProfileDocDAO() {
		return profileDocDAO;
	}

	public void setProfileDocDAO(IProviderProfileDocumentDao profileDocDAO) {
		this.profileDocDAO = profileDocDAO;
	}

	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}
	
	public List<D2CProviderAPIVO> getFirmDetailsListByMultipleCriteriaSearch(D2CPortalAPIVORequest d2cPortalAPIVO) throws DataServiceException {
		logger.info("Inside D2CProviderPortaServiceImpl.getFirmDetailsListByMultipleCriteriaSearch");
		List<D2CProviderAPIVO> basicFirmDetailsVO = null;
		//SLT-2848 Code changes 
		populateVendorDetails(d2cPortalAPIVO);
		List<Integer> firmIdsFromDB = d2cPortalAPIVO.getFirmIdsFromDb();
		if (firmIdsFromDB != null && firmIdsFromDB.size() > 0) {
			basicFirmDetailsVO = d2cProviderPortalDao.getFirmDetailsListByMultipleCriteriaSearch(d2cPortalAPIVO);
		}
		logger.info("Exiting D2CProviderPortaServiceImpl.getFirmDetailsListByMultipleCriteriaSearch");
		return basicFirmDetailsVO;
	}
	
	public List<D2CProviderAPIVO> getFirmDetailsWithBuyerRetailPrice(String buyerId, String sku, String zip, List<String> firmList) throws DataServiceException {
		logger.info("Inside D2CProviderPortaServiceImpl.getFirmDetailsListByMultipleCriteriaSearch");
		List<D2CProviderAPIVO> basicFirmDetailsVO = null;
		basicFirmDetailsVO = d2cProviderPortalDao.getFirmDetailsWithBuyerPrice(buyerId, sku, zip, firmList);
		logger.info("Exiting D2CProviderPortaServiceImpl.getFirmDetailsListByMultipleCriteriaSearch");
		return basicFirmDetailsVO;
	}
}