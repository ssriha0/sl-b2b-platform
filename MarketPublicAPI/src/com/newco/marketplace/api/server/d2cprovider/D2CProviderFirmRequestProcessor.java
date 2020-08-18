package com.newco.marketplace.api.server.d2cprovider;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.JAXBException;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.firmDetail.FirmIds;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.D2CProviderPortalService.D2CFirmDetailsServiceAPI;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.search.SearchProvidersByZipMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CGetFirmDetailsResponse;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.iBusiness.d2cproviderportal.ID2CProviderPortalBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.search.types.ResultMode;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;


@Path("getFirmsWithRetailPrice")
public class D2CProviderFirmRequestProcessor {

	private final Logger logger = Logger.getLogger(D2CProviderFirmRequestProcessor.class);

	private static final String JSON_MEDIA_TYPE = "application/json";

	protected ID2CProviderPortalBO d2CProviderPortalBO;
	private D2CFirmDetailsServiceAPI d2CFirmDetailsServiceAPI;
	
	protected ISearchProvider providerSearch;
	private SearchProvidersByZipMapper searchByZipMapper;

	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;

	@POST
	@Path("/skuId/{skuId}/zipCode/{zipCode}")
	@Consumes({ "application/xml", "application/json", "text/xml", "text/plain", "*/*" })
	@Produces({"application/json"})
	public D2CGetFirmDetailsResponse getFirmsWithRetailPriceBySKU(@PathParam("skuId") String skuId, @PathParam("zipCode") String zipCode, @QueryParam("date") String date,@QueryParam("firmId") String firmId,@QueryParam("count") Integer count,@QueryParam("marketIndexFlag") Boolean marketIndexFlag)
 throws BusinessServiceException,
			JAXBException, ParseException {
		logger.info("Inside D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKUD2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
		Results results = null;
		D2CPortalAPIVORequest d2CPortalAPIVO = new D2CPortalAPIVORequest();		
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		
		d2CPortalAPIVO.setRequestFromPost(reqMap);
		d2CPortalAPIVO.setSkuId(skuId);
		d2CPortalAPIVO.setZipCode(zipCode);
		d2CPortalAPIVO.setBuyerId("3333");
		
		logger.debug("D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU Date is : "+ date);
		if (!StringUtils.isEmpty(date)) {
			d2CPortalAPIVO.setDate(date);
		}
		
		if (!StringUtils.isEmpty(firmId)) {
			d2CPortalAPIVO.setFirmId(firmId);
		}
		
		if (null != count) {
			d2CPortalAPIVO.setCount(count);
		}else{
			d2CPortalAPIVO.setCount(5);
		}
		
		if (null != marketIndexFlag) {
			d2CPortalAPIVO.setMarketIndexFlag(marketIndexFlag);
		}else{
			d2CPortalAPIVO.setMarketIndexFlag(true);
		}
		
		// double buyerRetailPrice = d2CFirmDetailsServiceAPI.getBuyerRetailPrice(skuId);
		double buyerRetailPrice = d2CFirmDetailsServiceAPI.getBuyerRetailPrice(d2CPortalAPIVO.getSkuId(), d2CPortalAPIVO.getBuyerId());

		if (!StringUtils.isEmpty(getHttpRequest().getContentType())) {
			String content = getHttpRequest().getContentType().split(";")[0];

			logger.debug("D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU content type is : " + content);
			if (!JSON_MEDIA_TYPE.equalsIgnoreCase(content)) {
				D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
				results = Results.getError(
						ResultsCode.ONLY_JSON_FORMAT_IS_ACCEPTED.getMessage(),
						ResultsCode.ONLY_JSON_FORMAT_IS_ACCEPTED.getCode());
				firmDetailsXML.setResults(results);
				logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
				return firmDetailsXML;
			}
		} else {
			logger.info(" D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU content type is : null");
			D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
			results = new Results();
			results = Results.getError(
					ResultsCode.CONTENT_TYPE_CAN_NOT_BE_BLANK.getMessage(),
					ResultsCode.CONTENT_TYPE_CAN_NOT_BE_BLANK.getCode());
			firmDetailsXML.setResults(results);
			logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
			return firmDetailsXML;
		}
		try {
			
			List<D2CProviderAPIVO> d2CProviderAPIVO = d2CFirmDetailsServiceAPI.getFirmDetailsWithRetailPriceList(d2CPortalAPIVO);
			
			if(d2CProviderAPIVO.size() < d2CPortalAPIVO.getCount()){
				getZeroPriceProviderList(d2CPortalAPIVO.getBuyerId(), skuId,d2CProviderAPIVO,zipCode,d2CPortalAPIVO.getCount()-d2CProviderAPIVO.size());
			}		

			Map<String, D2CProviderAPIVO> d2CProviderAPIVOMap = new HashMap<String, D2CProviderAPIVO>();

			if (null == d2CProviderAPIVO || d2CProviderAPIVO.isEmpty()) {
				D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
				results = Results.getSuccess(ResultsCode.FIRM_DETAIL_SUCCESS.getMessage());
				firmDetailsXML.setResults(results);
				logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
				if(buyerRetailPrice != 0.0){
					firmDetailsXML.setBuyerRetailPrice(buyerRetailPrice);
				}
				return firmDetailsXML;
			}

			logger.debug("received count of providers from getFirmDetailsList: "
					+ d2CProviderAPIVO.size());
			List<String> list = new ArrayList<String>();
			for (D2CProviderAPIVO d2CProviderAPIVOData : d2CProviderAPIVO) {
				Integer providerId = d2CProviderAPIVOData.getFirmId();
				String str = Integer.toString(providerId);
				d2CProviderAPIVOMap.put(str, d2CProviderAPIVOData);
				list.add(str);
				/*if(d2CProviderAPIVOData.getOptIn()){
					buyerRetailPrice = d2CProviderAPIVOData.getBuyerRetailPrice();
				}*/
			}

			GetFirmDetailsRequest getFirmDetailsRequest = new GetFirmDetailsRequest();

			FirmIds firmIds = new FirmIds();
			firmIds.setFirmId(list);
			getFirmDetailsRequest.setFirmIds(firmIds);

			APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
			apiVO.setRequestFromPostPut(getFirmDetailsRequest);
			apiVO.setRequestType(RequestType.Post);
			apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap);

			D2CGetFirmDetailsResponse firmDetailsXML = d2CFirmDetailsServiceAPI
					.getFirmsBySKU(apiVO, d2CProviderAPIVOMap);
			logger.debug("D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU reply is : "
					+ firmDetailsXML);
			if (null != firmDetailsXML && null != firmDetailsXML.getFirms()
					&& null != firmDetailsXML.getFirms().getFirmDetails()
					&& firmDetailsXML.getFirms().getFirmDetails().size() > 0) {
				logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
				firmDetailsXML.setBuyerRetailPrice(buyerRetailPrice);
                   return firmDetailsXML;
			} else {
				results = new Results();
				results = Results.getSuccess(ResultsCode.FIRM_DETAIL_SUCCESS.getMessage());
				firmDetailsXML.setResults(results);
				if(buyerRetailPrice != 0.0){
					firmDetailsXML.setBuyerRetailPrice(buyerRetailPrice);
				}
				logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
				return firmDetailsXML;
				}
		} catch (Exception e) {
			logger.error("error during getting the firm details", e);
			D2CGetFirmDetailsResponse firmDetailsXML1 = new D2CGetFirmDetailsResponse();
			results = Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			e.printStackTrace();
			firmDetailsXML1.setResults(results);
			return firmDetailsXML1;
		}
	}

	/**
	 * @param skuId
	 * @throws DataServiceException
	 */
	private void getZeroPriceProviderList(String buyerId, String skuId, List<D2CProviderAPIVO> basicFirmDetailsVO, String zipCode, int count)
			throws DataServiceException {

		//Below code is to find the Provider's which have provided zero Price and Provider's will get selected
		//on the basis of their ratings. It will get executed in case of size of basicFirmDetailsVO is less than 5
		List<Integer> skuCategoryIdList =  new ArrayList<Integer>();
		
		try {
			skuCategoryIdList = providerSearch.getSkuCategoryIds(buyerId, new ArrayList<String>(Arrays.asList(skuId)));
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(null != skuCategoryIdList && skuCategoryIdList.size()>0){
			ProviderSearchRequestVO providerSearchRequestVO = new ProviderSearchRequestVO();
			providerSearchRequestVO.setCategoryIds(skuCategoryIdList);
			providerSearchRequestVO.setMaxDistance(40);
			providerSearchRequestVO.setZipCode(zipCode);
			providerSearchRequestVO.setMaxRating(5);
			providerSearchRequestVO.setPageSize(400);
			providerSearchRequestVO.setResultMode(ResultMode.getObj("MINIMUM"));			
			ProviderListVO response = providerSearch.getProviders(providerSearchRequestVO);			
			if(null != response && response.getTotalProvidersFound()>0){
				List<String> vendorIdList = new ArrayList<String>();		
				List<D2CProviderAPIVO> zeroPriceFirmDetailsVO = new ArrayList<D2CProviderAPIVO>();				
				for (ProviderSearchResponseVO providerSearchResponseVO : response.getProviderSearchResponseVO()) {
					if(providerSearchResponseVO.getZip().equals(zipCode)){
						D2CProviderAPIVO d2cProviderAPIVO = new D2CProviderAPIVO();
						d2cProviderAPIVO.setFirmId(providerSearchResponseVO.getCompanyId());
						d2cProviderAPIVO.setPrice(0.0);
						d2cProviderAPIVO.setOptIn(false);
						d2cProviderAPIVO.setRating(0.00);
						d2cProviderAPIVO.setSoCompleted(0);
						if (!(vendorIdList.contains(providerSearchResponseVO.getCompanyId()))){
							vendorIdList.add(new Integer(providerSearchResponseVO.getCompanyId()).toString());
						}
						zeroPriceFirmDetailsVO.add(d2cProviderAPIVO);
					}			
				}
				
				if(zeroPriceFirmDetailsVO.size()==0){
					return;
				}else if(zeroPriceFirmDetailsVO.size()<=count){
					basicFirmDetailsVO.addAll(zeroPriceFirmDetailsVO);
				}else{					
					Map<Integer, BigDecimal> aggregateRating = null;
					Map<Integer, Long> soCompletedMap = null;
					if (!vendorIdList.isEmpty()){			
						aggregateRating	= d2CFirmDetailsServiceAPI.getFirmRatings(vendorIdList);
						soCompletedMap = d2CFirmDetailsServiceAPI.getSoCompletedList(vendorIdList);
					}
					
					int maxSoCompleted = (soCompletedMap.entrySet().isEmpty()) ? 1 : soCompletedMap.entrySet().iterator().next().getValue().intValue();
					
					if(null != aggregateRating && null != soCompletedMap){
						for (D2CProviderAPIVO d2cProviderAPIVO : zeroPriceFirmDetailsVO) {
							if(aggregateRating.containsKey(d2cProviderAPIVO.getFirmId())){
								d2cProviderAPIVO.setRating(aggregateRating.get(d2cProviderAPIVO.getFirmId()).doubleValue());
							}
							if(soCompletedMap.containsKey(d2cProviderAPIVO.getFirmId())){
								d2cProviderAPIVO.setSoCompleted(soCompletedMap.get(d2cProviderAPIVO.getFirmId()).intValue());
							}
							double totalWeightage = (((d2cProviderAPIVO.getRating()/5)/2))+((d2cProviderAPIVO.getSoCompleted()/maxSoCompleted)/2);
							d2cProviderAPIVO.setTotalWeigtage(totalWeightage);
						}
					}					
					Collections.sort(zeroPriceFirmDetailsVO, new D2CProviderAPIVO());
					
					for(int i=zeroPriceFirmDetailsVO.size()-1;count!=0;i--,count--){
						basicFirmDetailsVO.add(zeroPriceFirmDetailsVO.get(i));
					}					
				}			
			}			
		}	
	}
	
	
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public ID2CProviderPortalBO getD2CProviderPortalBO() {
		return d2CProviderPortalBO;
	}

	public void setD2CProviderPortalBO(ID2CProviderPortalBO d2cProviderPortalBO) {
		d2CProviderPortalBO = d2cProviderPortalBO;
	}

	public D2CFirmDetailsServiceAPI getD2CFirmDetailsServiceAPI() {
		return d2CFirmDetailsServiceAPI;
	}

	public void setD2CFirmDetailsServiceAPI(D2CFirmDetailsServiceAPI d2cFirmDetailsServiceAPI) {
		d2CFirmDetailsServiceAPI = d2cFirmDetailsServiceAPI;
	}

	public ISearchProvider getProviderSearch() {
		return providerSearch;
	}

	public void setProviderSearch(ISearchProvider providerSearch) {
		this.providerSearch = providerSearch;
	}

	public SearchProvidersByZipMapper getSearchByZipMapper() {
		return searchByZipMapper;
	}

	public void setSearchByZipMapper(SearchProvidersByZipMapper searchByZipMapper) {
		this.searchByZipMapper = searchByZipMapper;
	}

}
