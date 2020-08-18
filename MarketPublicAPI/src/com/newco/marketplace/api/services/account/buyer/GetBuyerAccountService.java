/*
 *	Date        Project    	  Author       	 Version
 * -----------  --------- 	-----------  	---------
 * 12-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 *
 */
package com.newco.marketplace.api.services.account.buyer;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.buyer.GetBuyerAccountResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.account.buyer.BuyerAccountDetailsMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.business.iBusiness.location.ISimpleBuyerLocationBO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;

public class GetBuyerAccountService extends BaseService {
	private ISimpleBuyerLocationBO simpleBuyerLocationBO;
	private Logger logger = Logger.getLogger(GetBuyerAccountService.class);
	private BuyerAccountDetailsMapper buyerAccountDetailsMapper;
	private XStreamUtility conversionUtility;

	/**
	 * This method is for getting buyer account.
	 * 
	 * @param fromDate
	 *            String,toDate String
	 * @return String
	 */
	public GetBuyerAccountService() {
		super(null, PublicAPIConstant.BuyerAccount.Get.RESPONSE_XSD,
				PublicAPIConstant.BuyerAccount.NAMESPACE,
				PublicAPIConstant.BuyerAccount.RESOURCES_SCHEMAS,
				PublicAPIConstant.BuyerAccount.Get.SCHEMALOCATION, null,
				GetBuyerAccountResponse.class);
	}

	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		Contact buyerAccountDetails = null;
		Results results = null;
		GetBuyerAccountResponse getBuyerAccountResponse = null;
		try {
			buyerAccountDetails = simpleBuyerLocationBO
					.getBuyerResourceInfo(apiVO.getBuyerResourceIdInteger());
			getBuyerAccountResponse = buyerAccountDetailsMapper
					.adaptRequest(buyerAccountDetails);
		} catch (Exception e) {
			logger.error("GetBuyerAccountService.execute(): Exception occurred: ", e);
			results = Results.getError(e.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			getBuyerAccountResponse.setResults(results);
		}
		return getBuyerAccountResponse;
	}

	public ISimpleBuyerLocationBO getSimpleBuyerLocationBO() {
		return simpleBuyerLocationBO;
	}

	public void setSimpleBuyerLocationBO(
			ISimpleBuyerLocationBO simpleBuyerLocationBO) {
		this.simpleBuyerLocationBO = simpleBuyerLocationBO;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public BuyerAccountDetailsMapper getBuyerAccountDetailsMapper() {
		return buyerAccountDetailsMapper;
	}

	public void setBuyerAccountDetailsMapper(
			BuyerAccountDetailsMapper buyerAccountDetailsMapper) {
		this.buyerAccountDetailsMapper = buyerAccountDetailsMapper;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

}
