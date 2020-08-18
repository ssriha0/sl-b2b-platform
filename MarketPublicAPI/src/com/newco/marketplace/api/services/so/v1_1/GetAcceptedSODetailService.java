package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.serviceOrderDetail.GetServiceOrdersResponse;
import com.newco.marketplace.api.beans.serviceOrderDetail.ServiceOrder;
import com.newco.marketplace.api.beans.serviceOrderDetail.ServiceOrders;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.provider.ProviderDetailWithSOAccepted;
import com.newco.marketplace.exception.core.BusinessServiceException;

@APIResponseClass(GetServiceOrdersResponse.class)
public class GetAcceptedSODetailService extends BaseService {
	private static final Logger logger = Logger
			.getLogger(GetAcceptedSODetailService.class);

	private IServiceOrderBO serviceOrderBO;

	public GetAcceptedSODetailService() {
		super(null, PublicAPIConstant.SERVICE_ORDER_RESPONSE_XSD,
				PublicAPIConstant.PROVIDER_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.PROVIDER_RESPONSE_SCHEMALOCATION, null,
				GetServiceOrdersResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		GetServiceOrdersResponse response = new GetServiceOrdersResponse();
		List<ProviderDetailWithSOAccepted> providerDetailWithSOAccepted = new ArrayList<ProviderDetailWithSOAccepted>();
		Results results = new Results();

		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();

		String dayCount = apiVO.getRequestParamFromGetDelete("noOfdays");
		if (dayCount == null)
			dayCount = "1";
		try {
			providerDetailWithSOAccepted = serviceOrderBO
					.providerDetailWithSOAccepted(apiVO.getBuyerIdInteger(),
							apiVO.StringToInt(dayCount));
		} catch (BusinessServiceException e) {
			logger.error(
					"Error occurred while retrieving Provider Details...Track back there",
					e);
			return setErrorResponse(ResultsCode.FAILURE.getMessage(),
					ResultsCode.FAILURE.getCode());

		}
		response.setServiceOrders(new ServiceOrders());
		mapVoToServiceResponse(providerDetailWithSOAccepted, response);

		results = Results.getSuccess();

		if (null != results) {
			response.setResults(results);
		}
		logger.info("Leaving execute method");
		return response;
	}

	private GetServiceOrdersResponse setErrorResponse(String message,
			String code) {
		GetServiceOrdersResponse response = new GetServiceOrdersResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);
		return response;
	}

	private GetServiceOrdersResponse setSuccessResponse(String message,
			String code) {
		GetServiceOrdersResponse response = new GetServiceOrdersResponse();
		Results results = Results.getSuccess(message, code);
		response.setResults(results);
		return response;
	}

	@SuppressWarnings("unused")
	private void mapVoToServiceResponse(
			List<ProviderDetailWithSOAccepted> providerDetailList,
			GetServiceOrdersResponse response) {

		List<ServiceOrder> ServiceOrderList = new ArrayList<ServiceOrder>();

		for (ProviderDetailWithSOAccepted providerDetailWithSOAccepted : providerDetailList) {
			// ProviderDetail providerDetail = new ProviderDetail();
			// FirmDetail firmDetail = new FirmDetail();
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setSoId(providerDetailWithSOAccepted
					.getServiceOrder());
			serviceOrder.setServiceStartTime(providerDetailWithSOAccepted
					.getServiceStartTime());
			serviceOrder.setServiceEndTime(providerDetailWithSOAccepted
					.getServiceEndTime());

			// serviceOrder.setServiceOrder(providerDetailWithSOAccepted.getServiceOrder());
			// providerDetail.setFirstName(providerDetailWithSOAccepted.getFirstName());
			// providerDetail.setLastName(providerDetailWithSOAccepted.getLastName());
			// providerDetail.setEmail(providerDetailWithSOAccepted.getEmail());
			// providerDetail.setProviderId(providerDetailWithSOAccepted.getProviderId().toString());
			// providerDetail.setPhoneNo(providerDetailWithSOAccepted.getPhoneNo());
			// firmDetail.setFirmId(providerDetailWithSOAccepted.getVendorId());
			// firmDetail.setFirmId(providerDetailWithSOAccepted.getVendorName());

			// serviceOrder.setProviderDetail(providerDetail);
			// serviceOrder.setFirmDetail(firmDetail);
			ServiceOrderList.add(serviceOrder);
		}

		response.getServiceOrders().setServiceOrder(ServiceOrderList);
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

}
