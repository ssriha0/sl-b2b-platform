/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 01-Jan-2013	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.SOGetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOGetEligibleProvidersService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOGetEligibleProvidersService.class);

	private OrderManagementService managementService;
	private IServiceOrderBO serviceOrderBo;
	private OrderManagementMapper omMapper ;

	public SOGetEligibleProvidersService() {
		super(null,
				PublicAPIConstant.SO_GET_ELIGIBLE_PRO_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				null, SOGetEligibleProviderResponse.class);
	}


	/**
	 * This method handle the main logic for fetching eligible providers.
	 * @param apiVO
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside SOGetEligibleProvidersService.execute()");
		Results results = null;
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		SOGetEligibleProviderResponse soGetEligibleProvResponse = new SOGetEligibleProviderResponse();
		String firmId = apiVO.getProviderId();
		String soId = apiVO.getSOId();
		String groupIndParam = requestMap.get(PublicAPIConstant.GROUP_IND_PARAM);
		
		if (null != firmId && null != soId) {
			try{
				List<ProviderResultVO> providers;
				if (StringUtils.isNotBlank(groupIndParam) && PublicAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupIndParam)) {
					providers = managementService.getEligibleProvidersForGroup(firmId,soId);
				}else{
					providers = managementService.getEligibleProviders(firmId, soId);
				}
				List<EligibleProvider> providerDetails = mapProviders(providers);
				//sort the list based on distance in miles
				sortProviderList(providerDetails);
				//To get assigned resource in case of re-assign provider
				EligibleProvider provider = managementService.getAssignedResource(soId);
				soGetEligibleProvResponse = omMapper.mapEligibleProviderResponse(providerDetails);
				if(null != provider){
					soGetEligibleProvResponse.setAssignedResourceId(provider.getResourceId().toString());
					soGetEligibleProvResponse.setAssignedResource(provider.getProviderFirstName()+" "+provider.getProviderLastName());
				}
			}
			catch (Exception ex) {
				logger.error("Exception in getting routed provider list",ex);	
				results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				soGetEligibleProvResponse.setResults(results);
				return soGetEligibleProvResponse;
			}
		}
		logger.info("Leaving SOGetEligibleProvidersService.execute()");
		return soGetEligibleProvResponse;
	}
	
	/**
	 * Method to sort the list of providers based on the distance from service location.
	 * @param providerDetails : List of {@link EligibleProvider}
	 * **/
	private void sortProviderList(List<EligibleProvider> providerDetails) {
		Collections.sort(providerDetails, new Comparator<Object>(){
			public int compare(final Object obj1, final Object obj2){
				final EligibleProvider provider1 = (EligibleProvider)obj1;
				final EligibleProvider provider2 = (EligibleProvider)obj2;
				final double distance1 = provider1.getDistancefromSOLocation();
				final double distance2 = provider2.getDistancefromSOLocation();
				if(distance1 < distance2){
					return -1;
				}
				else{
					return (distance1 > distance2)? 1 : 0;
				}
			}
		});
	}

	/**
	 * Maper method for {@link EligibleProvider}. Returns a lit of {@link EligibleProvider} object <br>
	 * from a list of {@link ProviderResultVO}
	 * @param providers : List of {@link ProviderResultVO}
	 * @return List of {@link EligibleProvider}
	 * **/
	private List<EligibleProvider> mapProviders(List<ProviderResultVO> providers){
		List<EligibleProvider> providerDetails = new ArrayList<EligibleProvider>();
		if(null != providers){
			for(ProviderResultVO provider : providers){
				EligibleProvider eligibleProvider = new EligibleProvider();
				eligibleProvider.setResourceId(provider.getResourceId());
				eligibleProvider.setDistancefromSOLocation(provider.getDistanceFromBuyer());
				eligibleProvider.setProviderFirstName(provider.getProviderFirstName());
				eligibleProvider.setProviderLastName(provider.getProviderLastName());
				eligibleProvider.setProviderRespid(provider.getProviderRespid());
				providerDetails.add(eligibleProvider);
			}
		}
		return providerDetails;
	}
	

	public OrderManagementService getManagementService() {
		return managementService;
	}



	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}



	public OrderManagementMapper getOmMapper() {
		return omMapper;
	}



	public void setOmMapper(OrderManagementMapper omMapper) {
		this.omMapper = omMapper;
	}

	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}



	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}



}