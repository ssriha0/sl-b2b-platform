package com.newco.marketplace.api.services.so;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.buyerEventCallback.BuyerDetailsEventCallbackResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.orderfulfillment.client.OFHelper;

@APIResponseClass(BuyerDetailsEventCallbackResponse.class)
public class BuyerDetailsEventCallbackService extends BaseService {

	private IBuyerBO buyerBO;
	
	private static final Logger logger = Logger
			.getLogger(BuyerDetailsEventCallbackService.class);
	private OFHelper ofHelper = new OFHelper();

	/**
	 * public constructor
	 */
	public BuyerDetailsEventCallbackService() {
		super(null, PublicAPIConstant.BUYER_CALLBACK_API_DETAILS_XSD_RESP,
				PublicAPIConstant.BUYER_CALLBACK_API_DETAILS_RESPONSE_NAMESPACE,
				PublicAPIConstant.BuyerCallback_RESOURCES_SCHEMAS,
				PublicAPIConstant.BUYER_CALLBACK_API_DETAILS_RESPONSE_SCHEMALOCATION,
				null, BuyerDetailsEventCallbackResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {

		logger.info("Entering into method : BuyerDetailsEventCallbackService.execute()");
		Integer buyerId = apiVO.getBuyerIdInteger();
		BuyerDetailsEventCallbackResponse response = new BuyerDetailsEventCallbackResponse();
		try {
			BuyerDetailsEventCallbackVO buyerDetailsEventCallbackVO = buyerBO.getBuyerCallbackAPIDetails(buyerId);
			if(null == buyerDetailsEventCallbackVO || null == buyerDetailsEventCallbackVO.getBuyerId()){
				
			}
			// mappinng response object
			response.setUrl(buyerDetailsEventCallbackVO.getUrl());
			response.setMethod(buyerDetailsEventCallbackVO.getMethod());
			response.setHttpHeaderParameters(buyerDetailsEventCallbackVO.getHttpHeaderParameters());
			response.setAuthenticationType(buyerDetailsEventCallbackVO.getAuthenticationType());
			response.setApiKey(buyerDetailsEventCallbackVO.getApiKey());
			response.setApiSecret(buyerDetailsEventCallbackVO.getApiSecret());
			response.setCallbackType(buyerDetailsEventCallbackVO.getCallbackType());
			response.setResults(Results.getSuccess(ResultsCode.SUCCESS
					.getMessage()));
		} catch (BusinessServiceException e) {
			logger.error("Error occured while fetching the buyer callback api details");
			e.printStackTrace();
			response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getCode(), ResultsCode.INTERNAL_SERVER_ERROR.getMessage()));
		}
		response.setResults(Results.getSuccess(ResultsCode.SUCCESS
				.getMessage()));

		return response;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}
}
