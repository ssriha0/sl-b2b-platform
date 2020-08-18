package com.newco.marketplace.api.services.D2CProviderPortalService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.firmDetail.FirmIds;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.firm.validators.GetFirmValidator;
import com.newco.marketplace.api.utils.mappers.d2c.D2CFirmDetailsMapper;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CGetFirmDetailsResponse;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.iBusiness.d2cproviderportal.ID2CProviderPortalBO;
import com.newco.marketplace.business.iBusiness.provider.searchmatchrank.IProviderSearchMatchRankBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailsResponseVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class D2CFirmDetailsServiceAPI{

		private static Logger logger = Logger.getLogger(D2CFirmDetailsServiceAPI.class);

		private D2CFirmDetailsMapper d2CFirmDetailsMapper;
		private GetFirmValidator firmValidator;
		private IServiceOrderBO serviceOrderBO;
		ID2CProviderPortalBO d2CProviderPortalBO;
		
		// SEARCH_MATCH_RANK
		private IProviderSearchMatchRankBO providerSearchMatchRankBO;
		
		public IProviderSearchMatchRankBO getProviderSearchMatchRankBO() {
			return providerSearchMatchRankBO;
		}
		
		public void setProviderSearchMatchRankBO(IProviderSearchMatchRankBO providerSearchMatchRankBO) {
			this.providerSearchMatchRankBO = providerSearchMatchRankBO;
		}

		public List<D2CProviderAPIVO> getFirmDetailsList(D2CPortalAPIVORequest d2CPortalAPIVO) throws BusinessServiceException{
			
			List<D2CProviderAPIVO> d2CProviderAPIVO = null;
			d2CProviderAPIVO = d2CProviderPortalBO.getFirmDetailsList(d2CPortalAPIVO);
		return d2CProviderAPIVO;
		}
		
		@SuppressWarnings("unchecked")
		public D2CGetFirmDetailsResponse getFirmsBySKU(APIRequestVO apiVO,Map<String, D2CProviderAPIVO> d2cProviderAPIVOMap) {
			GetFirmDetailsRequest getFirmDetailRequest = (GetFirmDetailsRequest) apiVO.getRequestFromPostPut();
			D2CGetFirmDetailsResponse response = new D2CGetFirmDetailsResponse();
			FirmDetailRequestVO firmDetailRequestVO = null;
			FirmDetailsResponseVO firmDetailsResponseVO = null;
			String requestFilter="";
			Results results = null;
			Map<String, String> reqMap =  (Map<String, String>) apiVO.getProperty(PublicAPIConstant.REQUEST_MAP);
			try {
				//setting the response filters entered by the user
				if(null != reqMap && reqMap.containsKey(PublicAPIConstant.RESPONSE_FILTER)){
					requestFilter = reqMap.get(PublicAPIConstant.RESPONSE_FILTER);
				}
				//mapping the request parameters to the VO 
				firmDetailRequestVO = d2CFirmDetailsMapper.mapFirmDetailRequest(getFirmDetailRequest,requestFilter);
				//method to validate the filter criteria and firms
				if(null != firmDetailRequestVO){
					List<ErrorResult> validationErrors = firmValidator.validateFirmDetailsRequest(firmDetailRequestVO);
					if(!validationErrors.isEmpty()){				
						results = new Results();
						results.setError(validationErrors);
					}
					else{
						//method to fetch the firm details
						firmDetailsResponseVO = serviceOrderBO.getFirmDetails(firmDetailRequestVO);
						if(null != firmDetailsResponseVO){
							results = Results.getSuccess(ResultsCode.FIRM_DETAIL_SUCCESS.getMessage());
							// method to map the response to response object from VO
							
							response = d2CFirmDetailsMapper.mapFirmDetailsResponse(firmDetailsResponseVO,firmDetailRequestVO,d2cProviderAPIVOMap);
							
						}
					}
				}
			} 
			catch (BusinessServiceException e) {
				results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				e.printStackTrace();
				logger.error("Exception in Execute method of getFirmDetails"+ e.getMessage());
			}
			if(null != results){
				response.setResults(results);
			}
			return response;
		}
		

		public IServiceOrderBO getServiceOrderBO() {
			return serviceOrderBO;
		}
		public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
			this.serviceOrderBO = serviceOrderBO;
		}
		
		public GetFirmValidator getFirmValidator() {
			return firmValidator;
		}
		public void setFirmValidator(GetFirmValidator firmValidator) {
			this.firmValidator = firmValidator;
		}

		public ID2CProviderPortalBO getD2CProviderPortalBO() {
			return d2CProviderPortalBO;
		}

		public void setD2CProviderPortalBO(ID2CProviderPortalBO d2cProviderPortalBO) {
			d2CProviderPortalBO = d2cProviderPortalBO;
		}

		public D2CFirmDetailsMapper getD2CFirmDetailsMapper() {
			return d2CFirmDetailsMapper;
		}

		public void setD2CFirmDetailsMapper(D2CFirmDetailsMapper d2cFirmDetailsMapper) {
			d2CFirmDetailsMapper = d2cFirmDetailsMapper;
		}
		
		//START  Below code added for new API to get provider list(Standard+Non-Standard)
		
		public List<D2CProviderAPIVO> getFirmDetailsWithRetailPriceList(D2CPortalAPIVORequest d2CPortalAPIVO) throws BusinessServiceException{
			List<D2CProviderAPIVO> d2CProviderAPIVO = null;
			d2CProviderAPIVO = d2CProviderPortalBO.getFirmDetailsWithRetailPriceList(d2CPortalAPIVO);
		return d2CProviderAPIVO;
		}
		
		public Map<Integer, Long> getSoCompletedList(List<String> vendorIds){
			Map<Integer, Long> soCompletedMap = null;
			soCompletedMap = d2CProviderPortalBO.getSoCompletedList(vendorIds);
			return soCompletedMap;
		}
		
		public Map<Integer, BigDecimal> getFirmRatings(List<String> vendorIds){
			Map<Integer, BigDecimal> firmRatings = null;
			firmRatings = d2CProviderPortalBO.getFirmRatings(vendorIds);
			return firmRatings;
		}
		
		public Double getBuyerRetailPrice(String skuId, String buyerId){
			return d2CProviderPortalBO.getBuyerRetailPrice(skuId, buyerId);
		}
		
		public D2CGetFirmDetailsResponse getFirms(D2CPortalAPIVORequest d2CPortalAPIVO, APIRequestVO apiVO) {
			
			try {

				logger.debug("Before calling getFirmDetailsListByMultipleCriteriaSearchMatchRank");
				// ------------------------------------------------------------------------------------------ //
				// [A] SEARCH CRITERIA:
				// 1. Firm (Sears approved)/(ServiceLive Approved)
				// 2. DISPTACH ZONE
				// 3. SPN MEMBER(SKU -> Template -> SPN)
				// 4. HAS AGREED TO SKU PRICING
				// 5. AT LEAST 1-PROVIDER IN APPROVED(MARKET READY) STATUS
				// ------------------------------------------------------------------------------------------ //
				// [B] MATCHING CRITERIA:
				// 1. Has atleast 1 provider avail. on requested date and time window.
				// 2. Match to member profile(Details TBD)
				// ------------------------------------------------------------------------------------------ //
				// ****** initial IMPL ****** \\
				// List<D2CProviderAPIVO> d2CProviderAPIVO = getFirmDetailsWithRetailPriceList(d2CPortalAPIVO); 
				// ------------------------------------------------------------------------------------------ //
				// [C] RANK CRITERIA:
				// 1. Matching %
				// 2. Performance metrics.
				// 3. Handicap.
				List<D2CProviderAPIVO> d2CProviderAPIVO = getProviderSearchMatchRankBO().getFirmDetailsListByMultipleCriteriaSearchMatchRank(d2CPortalAPIVO);
				if(null != d2CProviderAPIVO){
					logger.info("Provider list size after complete SEARCH CRITERIA: " + d2CProviderAPIVO.size());
				}
				// ------------------------------------------------------------------------------------------ //
				
				Map<String, D2CProviderAPIVO> d2CProviderAPIVOMap = new HashMap<String, D2CProviderAPIVO>();
				
				// if a buyer have same SKU name multiple times... problem(Exception)
				double buyerRetailPrice = getBuyerRetailPrice(d2CPortalAPIVO.getSkuId(), d2CPortalAPIVO.getBuyerId());

				if (null == d2CProviderAPIVO || d2CProviderAPIVO.isEmpty()) {
					D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
					Results results = Results.getSuccess(ResultsCode.FIRM_DETAIL_SUCCESS.getMessage());
					firmDetailsXML.setResults(results);
					logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
					if (buyerRetailPrice != 0.0) {
						firmDetailsXML.setBuyerRetailPrice(buyerRetailPrice);
					}
					return firmDetailsXML;
				}

				List<String> firmIdList = new ArrayList<String>();

				for (D2CProviderAPIVO d2CProviderAPIVOData : d2CProviderAPIVO) {
					Integer providerId = d2CProviderAPIVOData.getFirmId();
					String str = Integer.toString(providerId);
					d2CProviderAPIVOMap.put(str, d2CProviderAPIVOData);
					firmIdList.add(str);
				}

				FirmIds firmIds = new FirmIds();
				firmIds.setFirmId(firmIdList);
				
				GetFirmDetailsRequest getFirmDetailsRequest = new GetFirmDetailsRequest();
				getFirmDetailsRequest.setFirmIds(firmIds);

				apiVO.setRequestFromPostPut(getFirmDetailsRequest);
				// apiVO.setRequestType(RequestType.Post); // value are already set and passed from previous layer
				// apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap); // value are already set and passed from previous layer

				logger.debug("-- Start getting getFirmsBySKU");
				D2CGetFirmDetailsResponse firmDetailsXML = getFirmsBySKU(apiVO, d2CProviderAPIVOMap);
				logger.debug("-- End getting getFirmsBySKU.firmDetailsXML = " + firmDetailsXML);
				
				// set the CorelationId in response
				firmDetailsXML.setCorelationId(d2CPortalAPIVO.getCorelationId());
				
				if (null != firmDetailsXML && null != firmDetailsXML.getFirms()
						&& null != firmDetailsXML.getFirms().getFirmDetails()
						&& firmDetailsXML.getFirms().getFirmDetails().size() > 0) {
					logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
					firmDetailsXML.setBuyerRetailPrice(buyerRetailPrice);
					return firmDetailsXML;
				} else {
					Results results = new Results();
					results = Results.getSuccess(ResultsCode.FIRM_DETAIL_SUCCESS.getMessage());
					firmDetailsXML.setResults(results);
					if (buyerRetailPrice != 0.0) {
						firmDetailsXML.setBuyerRetailPrice(buyerRetailPrice);
					}
					logger.info("Exiting D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU");
					return firmDetailsXML;
				}

			} catch (Exception e) {
				logger.error("error during getting the firm details", e);
				D2CGetFirmDetailsResponse firmDetailsXML1 = new D2CGetFirmDetailsResponse();
				Results results = Results.getError(
						ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
						ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				e.printStackTrace();
				firmDetailsXML1.setResults(results);
				return firmDetailsXML1;
			}
			
		}

		//END
		
	}
