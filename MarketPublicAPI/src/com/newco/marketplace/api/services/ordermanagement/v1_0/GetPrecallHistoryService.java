package com.newco.marketplace.api.services.ordermanagement.v1_0;

import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistoryResponse;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;

public class GetPrecallHistoryService extends SOBaseService {
	private Logger logger = Logger.getLogger(GetPrecallHistoryService.class);

	private OrderManagementService managementService;
	private OrderManagementMapper omMapper ;

	public GetPrecallHistoryService() {
		super(null,
				PublicAPIConstant.GET_PRECALL_HISTORY_RESPONCE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				null, PreCallHistoryResponse.class);
	}



	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside GetPrecallHistoryService.execute()");
		PreCallHistoryResponse preCallHistoryResponse = new PreCallHistoryResponse();
		String firmId = (String) apiVO
				.getProviderId();
	
		String soId = (String) apiVO.getSOId();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		String source = (String) requestMap.get("source");
		
		if (null != firmId && null != soId && null != source) {

			PreCallHistoryDetails preCallHistoryDetails = managementService.getPrecallHistoryDetails(firmId,soId,source);
			preCallHistoryResponse = omMapper.preCallHistoryDetailsResponse(preCallHistoryDetails);
		}
		logger.info("Leaving GetPrecallHistoryService.execute()");

		return preCallHistoryResponse;
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




}
