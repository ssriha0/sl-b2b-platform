package com.newco.marketplace.web.delegatesImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newco.marketplace.business.iBusiness.d2cproviderportal.ID2CProviderPortalBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.dto.vo.DispatchLocationVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.AddressValidationData;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.AddressValidationDetails;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.Coverage;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.CoverageLocationVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.PrimaryIndustryOffersOnDetailsVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.RateCardRequestVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZIPDetails;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZIPDetailsWrapper;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesData;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.ZipCodesWrapper;
import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.dto.vo.provider.VendorDocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.web.delegates.ID2CProviderPortalDelegate;
import com.newco.marketplace.web.dto.d2cproviderportal.DispatchLocationDto;
import com.newco.marketplace.web.dto.d2cproviderportal.ErrorDto;
import com.newco.marketplace.web.dto.d2cproviderportal.FirmDetailsDispatchLoc;
import com.newco.marketplace.web.dto.d2cproviderportal.ProviderPortalResponseDto;
import com.newco.marketplace.web.dto.provider.CompanyProfileDto;
import com.newco.marketplace.web.utils.FirmDetailsMapper;

/**
 * Delegate to intercept d2c provider portal calls
 * 
 * @author rranja1
 * 
 */
public class D2CProviderPortalDelegateImpl implements ID2CProviderPortalDelegate {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(D2CProviderPortalDelegateImpl.class);

	private final ID2CProviderPortalBO d2cProviderPortalBO;
	private final IProviderInfoPagesBO providerInfoPagesBOImpl;

	private final FirmDetailsMapper firmDetailsMapper;
	// To Fetch primary primaryIndustryList
	private final ILookupBO lookupBO;

	public D2CProviderPortalDelegateImpl(ID2CProviderPortalBO d2cProviderPortalBO, ILookupBO lookupBO,IProviderInfoPagesBO providerInfoPagesBOImpl) {
		this.d2cProviderPortalBO = d2cProviderPortalBO;
		this.lookupBO = lookupBO;
		this.providerInfoPagesBOImpl=providerInfoPagesBOImpl;
		this.firmDetailsMapper = new FirmDetailsMapper();
	}

	/**
	 * This method will fetch primary industries
	 */
	public ProviderPortalResponseDto getPrimaryIndustryDeleImpl(Integer vendorId) {
		logger.info("start getPrimaryIndustryDeleImpl method of D2CProviderPortalDelegateImpl");
		List<LookupVO> lookupPrimaryIndustry = null;
		ProviderPortalResponseDto response = new ProviderPortalResponseDto();
		try {
			lookupPrimaryIndustry = d2cProviderPortalBO.getPrimaryIndustryBOImpl(vendorId);
			response.setResult(lookupPrimaryIndustry);
		} catch (BusinessServiceException exception) {
			logger.error("Error occured in getprimaryIndustry D2CProviderPortalDelegateImpl : ", exception);
			response.setError(getErrorObject("0001", "Error occured"));
		}

		logger.info("end getPrimaryIndustryDeleImpl method of D2CProviderPortalDelegateImpl");
		return response;
	}

	public ProviderPortalResponseDto getSkuDetailsForVendorAndPrimaryIndustries(Integer vendorIds, List<Integer> primaryIndustery) {
		logger.info("entering getSkuDetailsForVendorAndPrimaryIndustries method of D2CProviderPortalDelegateImpl");

		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.getSkuDetailsForVendorAndPrimaryIndustries(vendorIds, primaryIndustery));
		} catch (BusinessServiceException exception) {
			logger.error("Error occured in getprimaryIndustry D2CProviderPortalDelegateImpl : ", exception);
			responseDto.setError(getErrorObject("0001", exception.getMessage()));
		}
		logger.info("exiting getSkuDetailsForVendorAndPrimaryIndustries of D2CProviderPortalDelegateImpl");
		return responseDto;
	}
	
	/**
	 * get not opted sku list for a vendor
	 */
	public ProviderPortalResponseDto getNotOptedSKUList(String vendorId) {
		logger.info("entering getNotOptedSKUList method of D2CProviderPortalDelegateImpl");

		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.getNotOptedSKUList(vendorId));
		} catch (BusinessServiceException exception) {
			logger.error("Error occured in getNotOptedSKUList D2CProviderPortalDelegateImpl : ", exception);
			responseDto.setError(getErrorObject("0001", exception.getMessage()));
		}
		logger.info("exiting getNotOptedSKUList of D2CProviderPortalDelegateImpl");
		return responseDto;
	}

	/**
	 * This method is called to get provider profile data
	 */
	public ProviderPortalResponseDto getProviderProfileDetails(List<String> vendorIds, List<String> filters) {
		logger.info("entering getProviderProfileDetails method of D2CProviderPortalDelegateImpl");

		FirmDetailRequestVO firmDetailsReq = new FirmDetailRequestVO();
		firmDetailsReq.setFirmId(vendorIds);
		firmDetailsReq.setFilter(filters);

		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();

		try {
			CompanyProfileVO companyProfileVO = d2cProviderPortalBO.getCompleteProfile(vendorIds.get(0));

			if (null != companyProfileVO) {
				// map basic details
				CompanyProfileDto companyProfileDto = new CompanyProfileDto();
				firmDetailsMapper.convertVOtoDTO(companyProfileVO, companyProfileDto);
				// Get dispatch locations
				List<DispatchLocationVO> dispatchLocs = d2cProviderPortalBO.getDispatchLocation(vendorIds.get(0));
				// Get service Coverage Area details
				List<CoverageLocationVO> coverageLocs = d2cProviderPortalBO.getCoverageAreas(vendorIds.get(0));
				// Get service offering details
				PrimaryIndustryOffersOnDetailsVO serviceOfferings = d2cProviderPortalBO.getServiceOfferedDetail(vendorIds.get(0), null);
				//List<PrimaryIndustryDetailsVO> serviceOfferings = null;
				// To fetch primary industry
				List<LookupVO> primaryIndustryList = lookupBO.loadProviderPrimaryIndustry();
				// get image data - removed for performance improvement
				String imageBase64 = "";//getVendorLogoIntoBase64(new Integer(vendorIds.get(0)));
				// service offer help link
				String serviceOfferHelpLink = PropertiesUtils.getFMPropertyValue("serviceOfferD2CHelpLink");
				// coverage help link
				String coverageHelpLink = PropertiesUtils.getFMPropertyValue("coverageD2CHelpLink");

				FirmDetailsDispatchLoc newFirmDetailsDisLocVo = new FirmDetailsDispatchLoc();
				newFirmDetailsDisLocVo.setFirmDetails(companyProfileDto);
				newFirmDetailsDisLocVo.setDispatchLoc(dispatchLocs);
				newFirmDetailsDisLocVo.setCoverageLoc(coverageLocs);
				newFirmDetailsDisLocVo.setServiceOfferDetailVO(serviceOfferings);
				newFirmDetailsDisLocVo.setPrimaryIndustryList(primaryIndustryList);
				newFirmDetailsDisLocVo.setImageBase64(imageBase64);
				newFirmDetailsDisLocVo.setCoverageHelpURL(coverageHelpLink);
				newFirmDetailsDisLocVo.setServiceOfferHelpURL(serviceOfferHelpLink);

				responseDto.setResult(newFirmDetailsDisLocVo);

				// responseDto.setError(getErrorObject(responseDto.getError().getErrorCode(),
				// responseDto.getError().getErrorMessage()));
			} else {
				responseDto.setError(getErrorObject("0001", "No results found!"));
			}
		} catch (BusinessServiceException exception) {
			logger.error("Error occured in getProviderProfileDetails D2CProviderPortalDelegateImpl : ", exception);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}

		logger.info("exiting getProviderProfileDetails method of D2CProviderPortalDelegateImpl");
		return responseDto;
	}
	
	/**
	 * This method is called to get FrimcLogo
	 */
	public ProviderPortalResponseDto getFirmLogo(Integer vendorId) {
		logger.info("entering getFrimLogo method of D2CProviderPortalDelegateImpl");
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		responseDto.setResult(getVendorLogoIntoBase64(vendorId));
		logger.info("exiting getFrimLogo method of D2CProviderPortalDelegateImpl");
		return responseDto;
	}
	
	/**
	 * This method is called to get FrimcLogo in base64
	 */
	private String getVendorLogoIntoBase64(Integer vendorId) {
		logger.info("start convertIntoBase64 method of D2CProviderPortalDelegateImpl");
		String returnImage = null;
		VendorDocumentVO documentVO;
		try {
			documentVO = providerInfoPagesBOImpl.getFirmLogo(vendorId);
			if (documentVO != null) {
				DocumentVO selectedImage = documentVO.getDocDetails();
				if (selectedImage != null && selectedImage.getBlobBytes() != null) {
					byte[] sourceImage = selectedImage.getBlobBytes();
					BASE64Encoder encoder = new BASE64Encoder();
					returnImage = encoder.encode(sourceImage);
					returnImage = null != returnImage && returnImage.trim().length() > 0 ? ("data:"+selectedImage.getFormat()+";base64,"+returnImage) : "";
					logger.debug("Comverted Image: " + returnImage);
				}
			}
		} catch (com.newco.marketplace.exception.BusinessServiceException exception) {
			logger.error("Error occured in convertIntoBase64 D2CProviderPortalDelegateImpl : ", exception);
		}

		logger.info("end convertIntoBase64 method of D2CProviderPortalDelegateImpl");
		return returnImage;
	}

	/**
	 * This method is called to get dispatch location
	 */
	public ProviderPortalResponseDto getDispatchLocation(String vendorId) {
		logger.info("start getDispatchLocation method of D2CProviderPortalDelegateImpl");

		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();

		List<DispatchLocationVO> list;
		try {
			list = d2cProviderPortalBO.getDispatchLocation(vendorId);

			DispatchLocationDto dispatchLocDto = new DispatchLocationDto();
			dispatchLocDto.setDispatchLocs(list);
			responseDto.setResult(dispatchLocDto);
		} catch (BusinessServiceException exception) {
			logger.error("Error occured while getDispatchLocation in D2CProviderPortalDelegateImpl", exception);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}

		logger.info("end getDispatchLocation method of D2CProviderPortalDelegateImpl");
		return responseDto;
	}

	/**
	 * This method is called to add/update dispatch location
	 */
	public ProviderPortalResponseDto addUpdateDispatchLocation(DispatchLocationVO dispatchLoc) {
		logger.info("start addUpdateDispatchLocation method of D2CProviderPortalDelegateImpl");

		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.addUpdateDispatchLocation(dispatchLoc));
		} catch (BusinessServiceException e) {
			logger.error("Error occured while addUpdateDispatchLocation in D2CProviderPortalDelegateImpl", e);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}

		logger.info("end addUpdateDispatchLocation method of D2CProviderPortalDelegateImpl");
		return responseDto;
	}

	/**
	 * to get service offered details
	 */
	public ProviderPortalResponseDto getServiceOfferedDetail(String vendorId, List<Integer> primaryIndustries) {
		logger.info("start getServiceOfferedDetail method of D2CProviderPortalDelegateImpl");

		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.getServiceOfferedDetail(vendorId, primaryIndustries));

		} catch (BusinessServiceException exception) {
			logger.error("Error occured while getServiceOfferedDetail in D2CProviderPortalDelegateImpl", exception);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}
		logger.info("end getServiceOfferedDetail method of D2CProviderPortalDelegateImpl");
		return responseDto;
	}

	public ProviderPortalResponseDto updateDailyLimitAndPrice(VendorServiceOfferingVO vendorServiceOfferVo, Integer vendorId, String vendorName) {
		logger.info("entering updateDailyLimitAndPrice method of D2CProviderPortalDelegateImpl");
		
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			d2cProviderPortalBO.updateDailyLimitAndPrice(vendorServiceOfferVo, vendorId, vendorName);
			responseDto.setResult(true);
		} catch (BusinessServiceException e) {
			logger.error("Exception in D2CProviderPortalDelegateImpl.updateDailyLimitAndPrice", e);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}
		logger.info("exiting updateDailyLimitAndPrice of D2CProviderPortalDelegateImpl");
		return responseDto;
	}

	/**
	 * Used to create error dto object
	 * 
	 * @param errorCode
	 * @param message
	 * @return
	 */
	private ErrorDto getErrorObject(String errorCode, String message) {
		ErrorDto errorDto = null;
		if (null != errorCode || null != message) {
			errorDto = new ErrorDto();
			errorDto.setErrorCode(errorCode);
			errorDto.setErrorMessage(message);
		}
		return errorDto;
	}

	public void updateCoverageZipCodes(List<ZipCodesData> zipCodesData, Coverage coverage) {
		logger.info("entering updateCoverageZipCodes of D2CProviderPortalDelegateImpl");
		d2cProviderPortalBO.updateCoverageZipCodes(zipCodesData, coverage);
		logger.info("exiting updateCoverageZipCodes of D2CProviderPortalDelegateImpl");

	}

	public void saveZipCoverageRadius(Coverage coverage) {
		logger.info("entering saveZipCoverageRadius of D2CProviderPortalDelegateImpl");
		d2cProviderPortalBO.saveZipCoverageRadius(coverage);
		logger.info("exiting saveZipCoverageRadius of D2CProviderPortalDelegateImpl");
	}

	public String findZipCodesInRadius(Coverage coverage) {
		logger.info("entering findZipCodesInRadius of D2CProviderPortalDelegateImpl");
		Gson gson = new Gson();

		String jsonString = findZipCodesInRadiusFromAPI(coverage.getZip(), coverage.getCoverageRadius());
		jsonString = jsonString.replace("DataList","zipCodesData");
		ZipCodesWrapper zipCodesWrapper = gson.fromJson(jsonString, new TypeToken<ZipCodesWrapper>() {}.getType());
		
		//List<ZipCodesData> zipCodesData= zipCodesWrapper.getZipCodesData();
		List<String> states = this.getStates();
		List<ZipCodesData> zipCodesData = new ArrayList<ZipCodesData>();
		List<ZipCodesData> apiIzipCodesData= zipCodesWrapper.getZipCodesData();
		logger.info("apiIzipCodesData data size: "+apiIzipCodesData.size());
		
		if(null !=states && states.size() > 0){
			for(ZipCodesData zipData : apiIzipCodesData){
				if(states.contains(zipData.getState())){
					zipCodesData.add(zipData);
				}
			}
		}else{
			zipCodesData = apiIzipCodesData;
		}
		
		logger.info("zipCodesData data size after excluding ouside US state codes: "+zipCodesData.size());
		
		List<ZipCodesData> mergedzipCodesData = new ArrayList<ZipCodesData>();

		List<ZipCodesData> dbZipCodesData= getDBSavedServiceAreaZipCodes(coverage);
		//get the full details of coverage root zip  from API and add it to the list
		String zipDetailString = getZipCodeDetailsFromAPI(coverage.getZip());
		
		if(null != zipDetailString && StringUtils.isNotBlank(zipDetailString)){
			ZIPDetailsWrapper zipDetailsWrapper = gson.fromJson(zipDetailString, new TypeToken<ZIPDetailsWrapper>() {}.getType());
			
			if (null != zipDetailsWrapper.getItem()) {
				ZIPDetails zipDetails= zipDetailsWrapper.getItem();
				ZipCodesData coverageRootZip = new ZipCodesData();
				
				coverageRootZip.setCode(zipDetails.getZipCode());
				coverageRootZip.setCity(zipDetails.getCity());
				coverageRootZip.setCounty(zipDetails.getCountyName());
				coverageRootZip.setState(zipDetails.getState());
				coverageRootZip.setLatitude(zipDetails.getLatitude());
				coverageRootZip.setLongitude(zipDetails.getLongitude());
				coverageRootZip.setDistance("0");
				coverageRootZip.setSelected(true);
				
				mergedzipCodesData.add(coverageRootZip);
			} else {
				logger.warn("Did not get data for root zip in API response: "+ zipDetailString);
			}
		}
		
		
		ZipCodesWrapper mergedZipCodesDataWrapper = new ZipCodesWrapper();;
		if(dbZipCodesData.size()>0){
			for(ZipCodesData apiZipCodeData:zipCodesData){
				for(ZipCodesData dbzipCodeData:dbZipCodesData){
					if(apiZipCodeData.getCode().equals(dbzipCodeData.getZipCode())){
						apiZipCodeData.setSelected(true);
						break;
					}
				}
				mergedzipCodesData.add(apiZipCodeData);
			}
			mergedZipCodesDataWrapper.setZipCodesData(mergedzipCodesData);
			logger.info("exiting findZipCodesInRadius of D2CProviderPortalDelegateImpl");
			return gson.toJson(mergedZipCodesDataWrapper);
		}
		else{
			for(ZipCodesData apiZipCodeData:zipCodesData){
				apiZipCodeData.setSelected(true);
				mergedzipCodesData.add(apiZipCodeData);
			}
			mergedZipCodesDataWrapper.setZipCodesData(mergedzipCodesData);
			
			logger.info("exiting findZipCodesInRadius of D2CProviderPortalDelegateImpl");
			return gson.toJson(mergedZipCodesDataWrapper);
		}

	}

	public String findZipCodesInRadiusFromAPI(String zip, String coverageRadius) {
		logger.info("entering FindZipCodesInRadius of D2CProviderPortalDelegateImpl");
		String zipCodeAPIKey = "zipCodeAPIKey";
		String zipCodeAPIKeyVal = PropertiesUtils.getFMPropertyValue(zipCodeAPIKey);
		logger.debug("zipCodeAPIKeyVal from the db"+zipCodeAPIKeyVal);
		String apiUrlKey = "zipCodeAPIURL";
		String zipCodeAPIURL = PropertiesUtils.getFMPropertyValue(apiUrlKey);
		logger.debug("zipCodeAPIURL from the db"+zipCodeAPIURL);
		String apiurl = zipCodeAPIURL+"?zipcode=" + zip
				+ "&minimumradius=0&maximumradius=" + coverageRadius + "&key="+zipCodeAPIKeyVal;
		
		String zipCodeData = "";
		try {
			long startTime;
			long endTime;
			URL url = new URL(apiurl);
			String data = "";
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
		
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			startTime = Calendar.getInstance().getTimeInMillis();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			endTime = Calendar.getInstance().getTimeInMillis();
			logger.info("Time taken to get the data in buffer reader from zipcode api:"+(endTime-startTime)+"ms");
			
			StringBuffer buffer = new StringBuffer();
			int letter;
			startTime = Calendar.getInstance().getTimeInMillis();
			while ((letter = br.read()) !=-1){
		          buffer.append((char) letter);
		      }
			endTime = Calendar.getInstance().getTimeInMillis();
			logger.info("Time taken to iterate while with zipcode data:"+(endTime-startTime)+"ms");
			data=buffer.toString();
			
			/*String output;

			while ((output = br.readLine()) != null) {

				data = data + output;
			}*/
			zipCodeData = null != data ? data.substring(data.indexOf("{")) : "{}";
			//zipCodeData = null != data ? data.substring(3) : "{}";
			// zipCodeData = data.substring(3, data.length());
			System.out.println(zipCodeData);

			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.error("Error occured while processing zip code data in findZipCodesInRadiusFromAPI ",e);

		} catch (IOException ex) {

			logger.error("Error occured while processing zip code data in findZipCodesInRadiusFromAPI ",ex);

		}
		
		logger.info("exiting FindZipCodesInRadius of D2CProviderPortalDelegateImpl");
		return zipCodeData;

	}
	
	public String getZipCodeDetailsFromAPI(String zip) {
		logger.info("entering getZipCodeDetailsFromAPI of D2CProviderPortalDelegateImpl");
		String zipCodeAPIKey = "zipCodeAPIKey";
		String zipCodeAPIKeyVal = PropertiesUtils.getFMPropertyValue(zipCodeAPIKey);
		logger.debug("zipCodeAPIKeyVal from the db"+zipCodeAPIKeyVal);
		String apiUrlKey = "getZipCodeDetailsURL";
		String getZipCodeDetailsURL = PropertiesUtils.getFMPropertyValue(apiUrlKey);
		logger.debug("zipCodeAPIURL from the db"+getZipCodeDetailsURL);
		String apiurl = getZipCodeDetailsURL+"/"+zip + "?key="+zipCodeAPIKeyVal;
		
		String zipDetailsData = "";
		try {
			URL url = new URL(apiurl);
			String data = "";
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			StringBuffer buffer = new StringBuffer();
			int letter;
			while ((letter = br.read()) !=-1){
		          buffer.append((char) letter);
		      }
			data=buffer.toString();
			/*String output;

			while ((output = br.readLine()) != null) {

				data = data + output;
			}*/
			zipDetailsData = null != data ? data.substring(data.indexOf("{")) : "{}";
			//zipCodeData = null != data ? data.substring(3) : "{}";
			// zipCodeData = data.substring(3, data.length());
			System.out.println(zipDetailsData);

			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.error("Error occured while processing zip details data in getZipCodeDetailsFromAPI ",e);

		} catch (IOException ex) {

			logger.error("Error occured while processing zip details data in getZipCodeDetailsFromAPI",ex);

		}
		logger.debug("zip details from  API:" + zipDetailsData);
		logger.info("exiting getZipCodeDetailsFromAPI of D2CProviderPortalDelegateImpl");
		return zipDetailsData;

	}

	public List<ZipCodesData> getDBSavedServiceAreaZipCodes(Coverage coverage) {
		return d2cProviderPortalBO.getDBSavedServiceAreaZipCodes(coverage);
	}

	public ProviderPortalResponseDto updateVendorData(String data, Integer vendorId, String flag) {
		logger.info("Inside D2CProviderPortalDelegateImpl.updateVendorData");
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		
		try {
			responseDto.setResult(d2cProviderPortalBO.updateVendorData(data, vendorId, flag));
		} catch (BusinessServiceException e) {
			logger.error("Exception in D2CProviderPortalDelegateImpl.updateVendorData", e);
			responseDto.setResult(false);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}
		
		logger.info("exiting D2CProviderPortalDelegateImpl.updateVendorData");
		return responseDto;
	}

	/**
	 * update vendor service offerings table
	 */
	public boolean updateVendorServiceOfferings(String vendorIdStr) {
		logger.info("start updateVendorServiceOfferings.updateVendorData");
		
		
		boolean isSuccess = false;
		try {
			isSuccess = d2cProviderPortalBO.updateVendorServiceOfferings(vendorIdStr);
		} catch (BusinessServiceException e) {
			logger.error("Exception in D2CProviderPortalDelegateImpl.updateVendorServiceOfferings", e);
		}

		logger.info("end updateVendorServiceOfferings.updateVendorData");
		return isSuccess;
		
	}
	
	public ProviderPortalResponseDto getLookupServiceDays(){
		logger.info("start getLookupServiceDays of D2CProviderPortalDelegateImpl");
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.getLookupServiceDays());
		} catch (BusinessServiceException e) {
			logger.error("Exception in getLookupServiceDays of D2CProviderPortalDelegateImpl", e);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}

		logger.info("end getLookupServiceDays of D2CProviderPortalDelegateImpl");
		return responseDto;
	}
	
	public ProviderPortalResponseDto getRateCardPriceDetails(Integer primaryIndustryId,Integer vendorId){
		logger.info("start getRateCardPriceDetails of D2CProviderPortalDelegateImpl");
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.getRateCardPriceDetails(primaryIndustryId, vendorId));
		} catch (BusinessServiceException e) {
			logger.error("Exception in getRateCardPriceDetails of D2CProviderPortalDelegateImpl", e);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}

		logger.info("end getRateCardPriceDetails of D2CProviderPortalDelegateImpl");
		return responseDto;
	}
	
	/**
	 * update delete offers on for a vendor
	 */
	public ProviderPortalResponseDto updateOrDeleteOffersOn(VendorServiceOfferedOnVO vendorServiceOfferedOnVO) {
		logger.info("Inside D2CProviderPortalDelegateImpl.updateOrDeleteOffersOn");
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.updateOrDeleteOffersOn(vendorServiceOfferedOnVO));
		} catch (BusinessServiceException e) {
			logger.error("Error In D2CProviderPortalDelegateImpl.updateOrDeleteOffersOn", e);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}
		logger.info("Exiting D2CProviderPortalDelegateImpl.updateOrDeleteOffersOn");
		return responseDto;
	}
	public String deleteCoverageArea(Coverage coverage){
		return d2cProviderPortalBO.deleteCoverageArea(coverage);
	}
	
	public List<CoverageLocationVO> getCoverageAreas(String vendorIdStr){
		return d2cProviderPortalBO.getCoverageAreas(vendorIdStr);
	}
	
	public ProviderPortalResponseDto saveRateCardPrice(RateCardRequestVO rateCardRequestVO,Integer vendorId){
		logger.info("start saveRateCardPrice of D2CProviderPortalDelegateImpl");
		
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		try {
			responseDto.setResult(d2cProviderPortalBO.saveRateCardPrice(rateCardRequestVO, vendorId));
		} catch (BusinessServiceException e) {
			logger.error("Exception in saveRateCardPrice of D2CProviderPortalDelegateImpl", e);
			responseDto.setError(getErrorObject("0001", "Error occured"));
		}

		logger.info("end saveRateCardPrice of D2CProviderPortalDelegateImpl");
		return responseDto;
	}
    public List<String> getStates(){	
		return d2cProviderPortalBO.getStates();	
	}
    
    
    /**
	 * This method validates dispatch location address
	 */
    public ProviderPortalResponseDto addressValidation(DispatchLocationVO dispatchLocationVO) {   
        logger.info("start addressValidation method of D2CProviderPortalDelegateImpl");
        ProviderPortalResponseDto response = new ProviderPortalResponseDto();
        List<AddressValidationDetails> addressValidationDetailsList = new ArrayList<AddressValidationDetails>();
        Gson gson = new Gson();
        String validData = "";
        validData = getValidAddressData(dispatchLocationVO);
        logger.info("valid data details from  API:" + validData);
        
        if(null != validData && StringUtils.isNotBlank(validData)){
			List<AddressValidationData> addressValidationData = gson.fromJson(validData, new TypeToken<List<AddressValidationData>>() {}.getType());			
			if (addressValidationData.size() > 0) {	
				for (AddressValidationData addressValidationdata : addressValidationData) {
					AddressValidationDetails addressValidation = new AddressValidationDetails();
					
					addressValidation.setStreet_name(addressValidationdata.getComponents().getStreet_name());
					addressValidation.setStreet_suffix(addressValidationdata.getComponents().getStreet_suffix());
					addressValidation.setCity_name(addressValidationdata.getComponents().getCity_name());
					addressValidation.setState_abbreviation(addressValidationdata.getComponents().getState_abbreviation());
					addressValidation.setZipcode(addressValidationdata.getComponents().getZipcode());
					addressValidation.setPlus4_code(addressValidationdata.getComponents().getPlus4_code());
					
					addressValidationDetailsList.add(addressValidation);
				}
				logger.info("Received get data from address validation API response: "+ validData);
				logger.debug("Size of data from address validation API response: "+ validData.length());
			} else {
				logger.warn("Did not get data from address validation API response: "+ validData);
			}
			response.setResult(addressValidationDetailsList);
		}
        
        logger.info("exiting addressValidation of D2CProviderPortalDelegateImpl");
        return response;
    }
    
    /**
	 * This method is called to get valid address
	 */
    private String getValidAddressData(DispatchLocationVO dispatchLocationVO) {	
    	logger.info("start getValidAddressData method of D2CProviderPortalDelegateImpl");  	
    	String validData = "";
		String addressValidationAPIKeyValue = PropertiesUtils.getFMPropertyValue("addressValidationAPIKey");
		logger.debug("addressValidationAPIKeyValue from the DB - "+addressValidationAPIKeyValue);
		
		String addressValidationAPIURLValue = PropertiesUtils.getFMPropertyValue("addressValidationAPIURL");

		//String addressValidationAPIURL = "https://us-street.api.smartystreets.com/street-address/?auth-token=2587552093259509234&street=ABC&city=hoffman+estate&zipcode=60179";
		
		try {
			String queryParams ="?auth-token=" + addressValidationAPIKeyValue  
					+ "&street=" + URLEncoder.encode(dispatchLocationVO.getStreet1(),"UTF-8")  + "&city="+ URLEncoder.encode(dispatchLocationVO.getCity(),"UTF-8") +"&zipcode="+ dispatchLocationVO.getZip();
			
			String addressValidationAPIURL = addressValidationAPIURLValue + queryParams;
			logger.debug("addressValidationAPIURLValue from the DB - "+addressValidationAPIURL);
			
			URL url = new URL(addressValidationAPIURL);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != 200) {
				logger.error("Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
				conn.disconnect();
				return validData;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			StringBuffer buffer = new StringBuffer();
			int letter;
			while ((letter = br.read()) !=-1){
				buffer.append((char) letter);
			}
			validData = buffer.toString();
			logger.debug("valid data details from  API:" + validData);
			
			conn.disconnect();

		} catch (MalformedURLException e) {
			logger.error("Error occured while processing data in getValidAddressData ",e);			
		} catch (IOException ex) {
			logger.error("Error occured while processing data in getValidAddressData",ex);			
		}
		
		logger.info("exiting getValidAddressData of D2CProviderPortalDelegateImpl");
		return validData;
	}
    
    /**
     * delete firm logo
     */
	public ProviderPortalResponseDto deleteFirmLogo(Integer vendorId) {
		ProviderPortalResponseDto responseDto = new ProviderPortalResponseDto();
		logger.info("Inside D2CProviderPortalDelegateImpl.deleteFirmLogo");
		responseDto.setResult(d2cProviderPortalBO.deleteFirmLogo(vendorId));
		logger.info("Exiting D2CProviderPortalDelegateImpl.deleteFirmLogo");
		return responseDto;
	}
}



