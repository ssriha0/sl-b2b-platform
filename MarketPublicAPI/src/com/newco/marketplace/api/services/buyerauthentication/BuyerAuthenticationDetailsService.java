package com.newco.marketplace.api.services.buyerauthentication;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.buyerauthenticationdetails.BuyerAuthenticationDetailsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.dto.vo.buyerauthenticationdetails.BuyerAuthenticationDetailsVO;
import com.newco.marketplace.exception.BusinessServiceException;

@APIResponseClass(BuyerAuthenticationDetailsResponse.class)
public class BuyerAuthenticationDetailsService extends BaseService{
	
	private Logger logger = Logger.getLogger(BuyerAuthenticationDetailsService.class);
	private IBuyerBO buyerBO;
	
	public BuyerAuthenticationDetailsService() {
		super(null, PublicAPIConstant.BUYER_AUTHENTICATION_API_DETAILS_XSD_RESP,
				PublicAPIConstant.BUYER_AUTHENTICATION_API_DETAILS_RESPONSE_NAMESPACE,
				PublicAPIConstant.BuyerAUTHENTICATION_RESOURCES_SCHEMAS,
				PublicAPIConstant.BUYER_AUTHENTICATION_API_DETAILS_RESPONSE_SCHEMALOCATION,
				null, BuyerAuthenticationDetailsResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		Integer buyerId   = apiVO.getBuyerIdInteger();
		BuyerAuthenticationDetailsResponse response =new BuyerAuthenticationDetailsResponse();
		
		try {
			BuyerAuthenticationDetailsVO buyerAuthenticationDetailsVO=buyerBO.getBuyerAuthenticationdetails(buyerId);
			if(buyerAuthenticationDetailsVO!=null){
				response.setBuyerResourceId(buyerAuthenticationDetailsVO.getBuyerResourceId());
				response.setConsumerKey(buyerAuthenticationDetailsVO.getConsumerKey());
				response.setSecretKey(buyerAuthenticationDetailsVO.getSecretKey());
			}
			response.setResults(Results.getSuccess(ResultsCode.SUCCESS
					.getMessage()));
		} catch (BusinessServiceException e) {
			logger.error("BuyerAuthorizationDetailsService-->execute()-->Exception-->" + e.getMessage(), e);
			response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getCode(), ResultsCode.INTERNAL_SERVER_ERROR.getMessage()));
		}
		logger.info("[Exiting BuyerAuthorizationDetailsService.execute method]");
		return response;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}
	
	

}
