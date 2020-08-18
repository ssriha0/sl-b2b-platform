/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.GetReleaseResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo.ReleaseDetails;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Get Release Info
 * 
 * @author Infosys
 * @version 1.0
 */
public class GetReleaseInfoService extends SOBaseService {
	private Logger logger = Logger.getLogger(GetReleaseInfoService.class);

	private OrderManagementService managementService;



	private OrderManagementMapper omMapper ;
	ProcessResponse processResponse= new ProcessResponse();

	public GetReleaseInfoService() {
		super(null,
				PublicAPIConstant.GET_RELEASE_INFO_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				null, GetReleaseResponse.class);
	}



	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		Results results = null;
		GetReleaseResponse releaseResponse = new GetReleaseResponse();
		logger.info("Entering GetReleaseInfoService.execute()");
		String firmId = (String) apiVO
				.getProviderId();
		Integer resourceId = apiVO.getProviderResourceId();
		String soId = (String) apiVO.getSOId();
		if (null != firmId && null != soId) {
			try {
				securityContext = getSecurityContextForVendorAdmin(new Integer(firmId));
			} catch (NumberFormatException nme) {
				/*logger.error("CompleteRequestService.execute(): "
						+ "Number Format Exception occurred for resourceId:"
						+ resourceId, nme);*/
			} 

			
			ReleaseDetails releaseDetails = managementService.getReleaseDetails(resourceId,soId) ;
			
			
			releaseResponse = omMapper.mapReleaseInfoResponse(releaseDetails);



		}
		logger.info("Leaving GetReleaseInfoService.execute()");

		return releaseResponse;
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



	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
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




}