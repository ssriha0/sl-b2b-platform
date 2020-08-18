/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
 * 
 * 
 */

package com.newco.marketplace.api.services.account.buyer;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.buyer.CreateBuyerAccountRequest;
import com.newco.marketplace.api.beans.account.buyer.CreateBuyerAccountResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.account.buyer.BuyerAccountMapper;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
/**
 * This class is responsible for creating new BuyerAccount.
 * 
 * @author priti
 *
 */
public class CreateBuyerAccountService extends BaseService {
	private IBuyerRegistrationBO buyerRegistrationBO;
	private Logger logger = Logger.getLogger(CreateBuyerAccountService.class);
	private ILookupBO lookupBO;
	/**
	 * This method is for creating buyer account.
	 * 
	 */
	public CreateBuyerAccountService () {
		super (PublicAPIConstant.BuyerAccount.Create.REQUEST_XSD,
				PublicAPIConstant.BuyerAccount.Create.RESPONSE_XSD, 
				PublicAPIConstant.BuyerAccount.NAMESPACE, 
				PublicAPIConstant.BuyerAccount.RESOURCES_SCHEMAS,
				PublicAPIConstant.BuyerAccount.Create.SCHEMALOCATION,	
				CreateBuyerAccountRequest.class,
				CreateBuyerAccountResponse.class);
	}	
	

	/**
	 * Subclass needs to implement API specific logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {		
		logger.info("Entering CreateBuyerAccount API's execute method");	
		CreateBuyerAccountRequest request  = null;	
		CreateBuyerAccountResponse response = null;
		
		request  = (CreateBuyerAccountRequest) apiVO.getRequestFromPostPut();	
		
		//R11.2 SL-19704
		if(null!=request){
			if(StringUtils.isNotBlank(request.getFirstName())){
				request.setFirstName(request.getFirstName().trim());
			}
			if(StringUtils.isNotBlank(request.getLastName())){
				request.setLastName(request.getLastName().trim());
			}
			if(StringUtils.isNotBlank(request.getUserName())){
				request.setUserName(request.getUserName().trim());
			}
		}
		
		BuyerRegistrationVO buyerRegistrationVO = null;
		try {
			//If the zip code is invalid, send request xml validation failed message back to the user.
			
			int zipValidation = LocationUtils.checkIfZipAndStateValid(request.getZip(),request.getStateCode());
			response = new CreateBuyerAccountResponse();
			boolean isError = false;
			switch (zipValidation) {
				case Constants.LocationConstants.ZIP_NOT_VALID:
					response.setResults(				
							Results.getError(ResultsCode.ZIP_CODE_INVALID.getMessage(), 
								ResultsCode.ZIP_CODE_INVALID.getCode()));
					isError = true;
					break;

				case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
					response.setResults(				
							Results.getError(ResultsCode.CREATE_BUYER_INVALID_ZIPCODE_STATE.getMessage(), 
								ResultsCode.CREATE_BUYER_INVALID_ZIPCODE_STATE.getCode()));
					isError = true;
					break;
			}
			if (isError)
				return response;
			
			buyerRegistrationVO = BuyerAccountMapper.getVOForCreateBuyerAccount(request);
			buyerRegistrationVO = buyerRegistrationBO.saveBuyerRegistration(buyerRegistrationVO);
			response = BuyerAccountMapper.convertVOToPOJOForCreateBuyerAccount(buyerRegistrationVO);
		} catch (DuplicateUserException e) {
			logger.error("Duplicate User Exists- CreateBuyerAccountService: error: " + e.getMessage());
			ArrayList<Object> argumentList = new ArrayList<Object>();
			argumentList.add("(" + request.getUserName() + ")");
			response = new CreateBuyerAccountResponse();
			response.setResults(				
				Results.getError(ResultsCode.CREATE_BUYER_USERNAME_ALREADY_EXISTS.getMessage(argumentList), 
					ResultsCode.CREATE_BUYER_USERNAME_ALREADY_EXISTS.getCode()));
		} catch(DataException e) {
			logger.error("CreateBuyerService exception detail: " + e.getMessage());
			response = new CreateBuyerAccountResponse();
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
						ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		} catch(BusinessServiceException e) {
			logger.error("CreateBuyerService exception detail: " + e.getMessage());
			response = new CreateBuyerAccountResponse();
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
						ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		
		logger.info("Exiting CreateBuyerAccount API's execute method");	
		return response;
	}
 
	
	public IBuyerRegistrationBO getBuyerRegistrationBO() {
		return buyerRegistrationBO;
	}

	public void setBuyerRegistrationBO(IBuyerRegistrationBO buyerRegistrationBO) {
		this.buyerRegistrationBO = buyerRegistrationBO;
	}


	public ILookupBO getLookupBO() {
		return lookupBO;
	}


	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}
	
	

}



