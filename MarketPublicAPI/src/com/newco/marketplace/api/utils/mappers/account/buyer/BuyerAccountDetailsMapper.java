/*
 *	Date        Project    	  Author       	 Version
 * -----------  --------- 	-----------  	---------
 * 12-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 *
 */
package com.newco.marketplace.api.utils.mappers.account.buyer;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.buyer.BuyerAccountDetails;
import com.newco.marketplace.api.beans.account.buyer.GetBuyerAccountResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.core.DataServiceException;

public class BuyerAccountDetailsMapper {
	private static final Logger logger = Logger
			.getLogger(BuyerAccountDetailsMapper.class);

	public GetBuyerAccountResponse adaptRequest(Contact buyerAccountDetails)
										throws DataServiceException {
		logger.info("Entering BuyerAccountDetailsMapper.adaptRequest()");
		GetBuyerAccountResponse getBuyerAccountResponse =
			new GetBuyerAccountResponse();
		BuyerAccountDetails buyerAccountDetail = new BuyerAccountDetails();
		Results results = new Results();

		if (null != buyerAccountDetails) {
			buyerAccountDetail.setAddress1(buyerAccountDetails.getStreet_1());
			buyerAccountDetail.setAddress2(buyerAccountDetails.getStreet_2());
			buyerAccountDetail.setAlternatePhone(buyerAccountDetails
					.getPhoneNoExt());
			if (PublicAPIConstant.THREE == buyerAccountDetails.getRole_id()) {
				buyerAccountDetail.setBuyerType("Pro");
			} else if (PublicAPIConstant.FIVE == buyerAccountDetails
					.getRole_id()) {
				buyerAccountDetail.setBuyerType("Simple");
			}
			buyerAccountDetail.setCity(buyerAccountDetails.getCity());
			buyerAccountDetail.setEmail(buyerAccountDetails.getEmail());
			buyerAccountDetail.setFirstName(buyerAccountDetails.getFirstName());
			buyerAccountDetail.setLastName(buyerAccountDetails.getLastName());
			buyerAccountDetail.setPrimaryPhone(buyerAccountDetails.getPhoneNo());
			buyerAccountDetail.setAlternatePhone(buyerAccountDetails.getCellNo());
			if (PublicAPIConstant.INTEGER_ONE == buyerAccountDetails.getOpt_in()) {
				buyerAccountDetail.setSendSpecialOffer("1");
			} else {
				buyerAccountDetail.setSendSpecialOffer("0");
			}
			buyerAccountDetail.setStateCode(buyerAccountDetails.getStateCd());
			buyerAccountDetail.setZip(buyerAccountDetails.getZip());
			getBuyerAccountResponse.setBuyerAccountDetails(buyerAccountDetail);
			results = Results.getSuccess(ResultsCode.GET_BUYER_ACCOUNT_SUCCESSFUL.getMessage());
			
		} else {
			results=Results.getError(
					ResultsCode.GET_BUYER_ACCOUNT_USER_DOES_NOT_EXIST.getMessage(),
					ResultsCode.GET_BUYER_ACCOUNT_USER_DOES_NOT_EXIST.getCode());
		}
		
		getBuyerAccountResponse.setResults(results);
		logger.info("Leaving BuyerAccountDetailsMapper.adaptRequest()");
		return getBuyerAccountResponse;
	}
}
