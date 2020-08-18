package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.firmDetail.FirmDetails;
import com.newco.marketplace.api.beans.firmDetail.FirmIds;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.FirmDetailsMapper;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CFirmDetails;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CFirms;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CGetFirmDetailsResponse;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.businessImpl.routingDistance.BuyerRoutingDistanceCache;
import com.newco.marketplace.business.iBusiness.d2cproviderportal.ID2CProviderPortalBO;
import com.newco.marketplace.business.iBusiness.jobcode.IJobCodeMappingBO;
import com.newco.marketplace.business.iBusiness.provider.searchmatchrank.IProviderSearchMatchRankBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailsResponseVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.orderfulfillment.client.OFHelper;

@APIResponseClass(D2CGetFirmDetailsResponse.class)
public class SearchFirmsForAutoPostService extends BaseService {
	
	private static final Logger logger = Logger
			.getLogger(SearchFirmsForAutoPostService.class);
	
	private IProviderSearchBO provSearchObj;
	private IBuyerSOTemplateBO templateBO;
	private IJobCodeMappingBO jobCodeMappingBO;
	private BuyerRoutingDistanceCache buyerRoutingDistanceCache;
	private IMasterCalculatorBO masterCalculatorBO;
	private ID2CProviderPortalBO d2CProviderPortalBO;
	private FirmDetailsMapper firmDetailsMapper;
	private IServiceOrderBO serviceOrderBO;
	private IProviderSearchMatchRankBO providerSearchMatchRankBO;
	private OFHelper ofHelper = new OFHelper();

	public SearchFirmsForAutoPostService(){
	super(null, PublicAPIConstant.GET_SO_RESCHEDULE_REASON_XSD_RESP,
			PublicAPIConstant.SO_RESCHEDULE_REASON_RESPONSE_NAMESPACE,
			PublicAPIConstant.SO_RESOURCES_SCHEMAS,
			PublicAPIConstant.SO_RESCHEDULE_REASON_RESPONSE_SCHEMALOCATION, null,
			D2CGetFirmDetailsResponse.class);
	}
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		return null;
	}
	
	public D2CGetFirmDetailsResponse getFirmsDetails(APIRequestVO apiVO) {
		logger.info("Entering into method : SearchFirmsForAutoPostService.execute()");
		String buyerId = apiVO.getBuyerId();
		String zipCode = apiVO.getZip();
		String sku = apiVO.getSku();
		
		D2CGetFirmDetailsResponse response = new D2CGetFirmDetailsResponse();
		Results results;
		ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
		provSearchVO.setBuyerID(Integer.valueOf(buyerId));
        LocationVO serviceLocation = new LocationVO();
        serviceLocation.setZip(zipCode);
		provSearchVO.setServiceLocation(serviceLocation);

		logger.info("search SKU:");
		long start5 = System.currentTimeMillis();
		List<BuyerSkuTaskVO> skuTask = new ArrayList<BuyerSkuTaskVO>();
		try {
			skuTask = jobCodeMappingBO.getSkuTaskList(sku, null, Integer.valueOf(buyerId));
		} catch (BusinessServiceException e) {
			logger.error(" Error occured while fetching task list for the sku");
			e.printStackTrace();
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
			return response;
		} catch (NumberFormatException e){
			logger.error(" Error occured while fetching task list for the sku");
			e.printStackTrace();

			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
			return response;
		}
		if( null == skuTask || 0 >= skuTask.size()){
			results = Results.getError(ResultsCode.INVALID_SKU.getMessage(),ResultsCode.INVALID_SKU.getCode());
			response.setResults(results);
			return response;
		}
		long end5 = System.currentTimeMillis();
		logger.info("Time taken to search SKU:"+(end5-start5));
		// if a buyer have same SKU name multiple times... problem(Exception)
		double buyerRetailPrice = getBuyerRetailPrice(sku, buyerId);
		if (buyerRetailPrice != 0.0) {
			response.setBuyerRetailPrice(buyerRetailPrice);
		}
		
		ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
		List<Integer> skillServiceTypeIds = new ArrayList<Integer>();
		for(BuyerSkuTaskVO task : skuTask){
			skillNodeIds.add(task.getNodeId());
			skillServiceTypeIds.add(task.getSkillId());
		}
		provSearchVO.setSkillNodeIds(skillNodeIds);
		provSearchVO.setSkillServiceTypeId(skillServiceTypeIds);
		logger.info("Loading the template:");
		long start1 = System.currentTimeMillis();
		BuyerSOTemplateDTO templateData = templateBO.loadBuyerSOTemplate(skuTask.get(0).getTemplateId());
		if(null == templateData){
			results = Results.getError(ResultsCode.SEARCH_TEMPLATE_NO_RECORDS.getMessage(),ResultsCode.SEARCH_TEMPLATE_NO_RECORDS.getCode());
			response.setResults(results);
			return response;
		}
		long end1 = System.currentTimeMillis();
		logger.info("Time taken to load the template:"+(end1-start1));
		provSearchVO.setSpnID(templateData.getSpnId());
		
		// set default response success though there are no providers
		results = Results.getSuccess(ResultsCode.FIRM_DETAIL_SUCCESS.getMessage());
		response.setResults(results);
		
		provSearchVO.setSpnID(templateData.getSpnId());
		provSearchVO.setIsNewSpn(templateData.getIsNewSpn());
		
		// Do initial provider search
		ArrayList<ProviderResultVO> providerSearchResults = provSearchObj
				.getProviderList(provSearchVO);
		ArrayList<ProviderResultVO> trimmedProviderSearchResults = new ArrayList<ProviderResultVO>();
		// filter the provider list based on routing distance criteria
		Set<String> firmIdList = new HashSet<String>();
		ArrayList<String> vendorList;
		logger.info("searching firms:");
		long start2 = System.currentTimeMillis();
		if(null!=providerSearchResults && providerSearchResults.size()>0){
			//Changes for SLT-272
			// SLT-2552: commented the trimming logic
			//Integer routingRadius = buyerRoutingDistanceCache.getBuyerRoutingDistance(Integer.valueOf(buyerId));
			//trimmedProviderSearchResults = trimProviderListByPrimarySkillAndLocation(providerSearchResults, Integer.valueOf(templateData.getMainServiceCategory()), zipCode, routingRadius);
			trimmedProviderSearchResults = providerSearchResults;
			if (null!= templateData && templateData.getSpnId() != null) {
	            trimmedProviderSearchResults = trimProviderListByMinSpnPercentMatch(trimmedProviderSearchResults, templateData.getSpnPercentageMatch());
	        }
	        for(ProviderResultVO providerVo : trimmedProviderSearchResults){
	        	firmIdList.add(providerVo.getVendorID().toString());
	        }
		}
		long end2 = System.currentTimeMillis();
		logger.info("Time taken to get the firmIds :"+(end2-start2));
		
		if (null != firmIdList && 0 < firmIdList.size()) {
			vendorList = new ArrayList<String>(firmIdList);
			FirmDetailsResponseVO firmDetailsResponseVO = null;
			List<String> filterList = new ArrayList<String>();
			filterList.add(PublicAPIConstant.BASIC);

			FirmIds firmIds = new FirmIds();
			firmIds.setFirmId(vendorList);

			FirmDetailRequestVO firmDetailRequestVO = new FirmDetailRequestVO();
			GetFirmDetailsResponse firmDetailsResponse = null;

			firmDetailRequestVO.setFirmId(vendorList);
			firmDetailRequestVO.setFilter(filterList);
			logger.info("search firm details :");
			long start3 = System.currentTimeMillis();
			try {
				firmDetailsResponseVO = serviceOrderBO
						.getFirmDetails(firmDetailRequestVO);
			} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
				logger.error("Exception in Execute method of getFirmDetails"
						+ e.getMessage());
				return response;
			}
			long end3 = System.currentTimeMillis();
			logger.info("Time taken to search firm details :"+(end3-start3));
			if (null != firmDetailsResponseVO) {
				firmDetailsResponse = firmDetailsMapper.mapFirmDetailsResponse(
						firmDetailsResponseVO, firmDetailRequestVO);

				// mapping the firm details
				mapFirmDetailsWithD2cFirmDetails(response, firmDetailsResponse);
				List<D2CProviderAPIVO> providerPricingDetails = null;
				logger.info("search firm SKU pricing details:");
				long start4 = System.currentTimeMillis();
				try {
					providerPricingDetails = providerSearchMatchRankBO
							.getFirmDetailsWithBuyerPrice(buyerId, sku,
									zipCode, vendorList);
				} catch (Exception e) {
					logger.error(" Error occured while fetching provider pricing details");
					e.printStackTrace();
					return response;
				}
				long end4 = System.currentTimeMillis();
				logger.info("Time taken to search firm SKU pricing details :"+(end4-start4));
				// map pricing details with firms
				mapFirmsWithPricingDetails(response, providerPricingDetails);
			}
		}
		return response;
	}
	
	public Double getBuyerRetailPrice(String skuId, String buyerId){
		return d2CProviderPortalBO.getBuyerRetailPrice(skuId, buyerId);
	}
	
    ArrayList<ProviderResultVO> trimProviderListByPrimarySkillAndLocation(ArrayList<ProviderResultVO> providerSearchResults, Integer primarySkillCatId, String zipCode, Integer radius) {
    	logger.info("Inside trimProviderListByPrimarySkillAndLocation()");
    	ArrayList<RatingParameterBean> ratingParameterBeans = new ArrayList<RatingParameterBean>();
        ZipParameterBean zipBean = new ZipParameterBean();
        zipBean.setRadius(radius);
        zipBean.setZipcode(zipCode);
        zipBean.setCredentialId(primarySkillCatId);

        ratingParameterBeans.add(zipBean);

        return masterCalculatorBO.getFilteredProviderList(ratingParameterBeans, providerSearchResults);
    }
    
    ArrayList<ProviderResultVO> trimProviderListByMinSpnPercentMatch(ArrayList<ProviderResultVO> providerSearchResults, Integer minSpnMatchValue) {
        ArrayList<ProviderResultVO> providerList = new ArrayList<ProviderResultVO>();
        for (ProviderResultVO vo : providerSearchResults) {
            if (vo.getPercentageMatch()!=null && vo.getPercentageMatch() >= minSpnMatchValue) {
                providerList.add(vo);
            }
        }
        return providerList;
    }
    
    private void mapFirmDetailsWithD2cFirmDetails(D2CGetFirmDetailsResponse response, GetFirmDetailsResponse firmDetailsResponse){
    	D2CFirms firms = new D2CFirms();
    	ArrayList<D2CFirmDetails> d2cFirmDetails = new ArrayList<D2CFirmDetails>();
    	D2CFirmDetails d2cFirmDetail;
    	for(FirmDetails firmDetails : firmDetailsResponse.getFirms().getFirmDetails()){
    		d2cFirmDetail = new D2CFirmDetails();
    		d2cFirmDetail.setFirmId(firmDetails.getFirmId());
    		d2cFirmDetail.setBusinessName(firmDetails.getBusinessName());
    		d2cFirmDetail.setCompanyLogoUrl(firmDetails.getCompanyLogoUrl());
    		d2cFirmDetail.setOverView(firmDetails.getOverView());
    		d2cFirmDetail.setFirmOwner(firmDetails.getFirmOwner());
    		d2cFirmDetail.setFirmAverageTimeToAccept(firmDetails.getFirmAverageTimeToAccept());
    		d2cFirmDetail.setFirmAverageArrivalWindow(firmDetails.getFirmAverageArrivalWindow());
    		d2cFirmDetail.setNumberOfEmployees(firmDetails.getNumberOfEmployees());
    		d2cFirmDetail.setYearsOfService(firmDetails.getYearsOfService());
    		d2cFirmDetail.setHourlyRate(firmDetails.getHourlyRate());
    		d2cFirmDetail.setLocation(firmDetails.getLocation());
    		d2cFirmDetail.setOptIn(true);
    		d2cFirmDetail.setAcceptedSoCount(0);
    		d2cFirmDetails.add(d2cFirmDetail);
    	}
    	firms.setFirmDetails(d2cFirmDetails);
    	response.setFirms(firms);
    }
    
    private void mapFirmsWithPricingDetails(D2CGetFirmDetailsResponse response, List<D2CProviderAPIVO> providerPricingDetails){
		if(null != providerPricingDetails && 0 < providerPricingDetails.size() ){
			for(D2CProviderAPIVO providerVo : providerPricingDetails){
				for(D2CFirmDetails firmDetails : response.getFirms().getFirmDetails()){
					if(firmDetails.getFirmId().equals(providerVo.getFirmId())){
						firmDetails.setPrice(providerVo.getPrice());
						firmDetails.setDailyLimit(providerVo.getDailyLimit());
					}
				}
			}
		}
    }
    
    public IProviderSearchBO getProvSearchObj() {
		return provSearchObj;
	}

	public void setProvSearchObj(IProviderSearchBO provSearchObj) {
		this.provSearchObj = provSearchObj;
	}

	public IBuyerSOTemplateBO getTemplateBO() {
		return templateBO;
	}

	public void setTemplateBO(IBuyerSOTemplateBO templateBO) {
		this.templateBO = templateBO;
	}

	public IJobCodeMappingBO getJobCodeMappingBO() {
		return jobCodeMappingBO;
	}

	public void setJobCodeMappingBO(IJobCodeMappingBO jobCodeMappingBO) {
		this.jobCodeMappingBO = jobCodeMappingBO;
	}

	public BuyerRoutingDistanceCache getBuyerRoutingDistanceCache() {
		return buyerRoutingDistanceCache;
	}

	public void setBuyerRoutingDistanceCache(
			BuyerRoutingDistanceCache buyerRoutingDistanceCache) {
		this.buyerRoutingDistanceCache = buyerRoutingDistanceCache;
	}

	public IMasterCalculatorBO getMasterCalculatorBO() {
		return masterCalculatorBO;
	}

	public void setMasterCalculatorBO(IMasterCalculatorBO masterCalculatorBO) {
		this.masterCalculatorBO = masterCalculatorBO;
	}

	public ID2CProviderPortalBO getD2CProviderPortalBO() {
		return d2CProviderPortalBO;
	}

	public void setD2CProviderPortalBO(ID2CProviderPortalBO d2cProviderPortalBO) {
		d2CProviderPortalBO = d2cProviderPortalBO;
	}

	public FirmDetailsMapper getFirmDetailsMapper() {
		return firmDetailsMapper;
	}

	public void setFirmDetailsMapper(FirmDetailsMapper firmDetailsMapper) {
		this.firmDetailsMapper = firmDetailsMapper;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IProviderSearchMatchRankBO getProviderSearchMatchRankBO() {
		return providerSearchMatchRankBO;
	}

	public void setProviderSearchMatchRankBO(
			IProviderSearchMatchRankBO providerSearchMatchRankBO) {
		this.providerSearchMatchRankBO = providerSearchMatchRankBO;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
	
}
