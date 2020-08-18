package com.newco.marketplace.api.services.buyerEventCallback;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.buyerEventCallback.BuyerEventCallbackResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;



@APIResponseClass(BuyerEventCallbackResponse.class)
public class BuyerEventCallbackService extends BaseService{
	
	private Logger logger = Logger.getLogger(BuyerEventCallbackService.class);
	private IBuyerBO buyerBO;
	
	public BuyerEventCallbackService() {
		super(null, PublicAPIConstant.BUYER_EVENT_CALLBACK_RESPONSE_XSD,
				PublicAPIConstant.BUYER_EVENT_CALLBACK_RESPONSE_NAMESPACE,
				PublicAPIConstant.BUYER_EVENT_CALLBACK_RESOURCES_SCHEMAS,
				PublicAPIConstant.BUYER_EVENT_CALLBACK_RESPONSE_SCHEMALOCATION,
				null, BuyerEventCallbackResponse.class);
		addRequiredURLParam(APIRequestVO.ACTION_ID, DataTypes.INTEGER);
	}
	/**
	 * This method returns the buyerEventResponse
	 * @param APIRequestVO
	 * @return buyerEventCallbackResponse
	 */
	@SuppressWarnings("unchecked")
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering BuyerEventCallbackService.execute()");
		//Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		Integer buyerId   = apiVO.getBuyerIdInteger();
		Integer actionId = apiVO.getActionIdInteger();
		BuyerEventCallbackResponse buyerEventCallbackResponse = new BuyerEventCallbackResponse();
		try {
			BuyerCallbackEvent buyerCallbackEvent=buyerBO.getBuyerCallbackEventDetails(buyerId,actionId);
			if(null !=buyerCallbackEvent){
				buyerEventCallbackResponse.setEventId(buyerCallbackEvent.getEventId());
				buyerEventCallbackResponse.setFilterNames(buyerCallbackEvent.getFilterNames());
			}
			Results results = Results.getSuccess(ResultsCode.GET_BUYER_EVENT_CALLBACK_SUCCESS.getMessage());
			buyerEventCallbackResponse.setResults(results);
			
		} catch(Exception e){
			logger.error("BuyerEventCallbackService-->execute()-->Exception-->" + e.getMessage(), e);
			Results results = Results.getError(e.getMessage(), ResultsCode.GET_BUYER_EVENT_CALLBACK_ERROR.getCode());
			buyerEventCallbackResponse.setResults(results);
		} 
		logger.info("[Exiting BuyerEventCallbackService.execute method]");
		return buyerEventCallbackResponse;
	}
	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}
	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

}
