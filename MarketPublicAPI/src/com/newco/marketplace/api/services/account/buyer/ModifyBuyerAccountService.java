/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
 * 
 * 
 */

package com.newco.marketplace.api.services.account.buyer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.account.buyer.ModifyBuyerAccountRequest;
import com.newco.marketplace.api.beans.account.buyer.ModifyBuyerAccountResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.account.buyer.BuyerAccountMapper;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;


/**
 * This class is responsible for modifying existing BuyerAccount.
 * 
 * @author priti
 *
 */
public class ModifyBuyerAccountService extends BaseService {
	private IBuyerRegistrationBO buyerRegistrationBO;
	private Logger logger = Logger.getLogger(ModifyBuyerAccountService.class);
	private ILookupBO lookupBO;

	/**
	 * This method is for creating buyer account.
	 * 
	 * @param fromDate String,toDate String
	 * @return String
	 */
	public ModifyBuyerAccountService () {
		super (PublicAPIConstant.BuyerAccount.Modify.REQUEST_XSD,
				PublicAPIConstant.BuyerAccount.Modify.RESPONSE_XSD, 
				PublicAPIConstant.BuyerAccount.NAMESPACE, 
				PublicAPIConstant.BuyerAccount.RESOURCES_SCHEMAS,
				PublicAPIConstant.BuyerAccount.Modify.SCHEMALOCATION,	
				ModifyBuyerAccountRequest.class,
				ModifyBuyerAccountResponse.class);
	}	
	
	
	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {		
		logger.info("Entering execute method");	
		ModifyBuyerAccountRequest request  = null;	
		ModifyBuyerAccountResponse response = new ModifyBuyerAccountResponse();
		BuyerRegistrationVO buyerRegistrationVO = null;
		try {
			request  = (ModifyBuyerAccountRequest) apiVO.getRequestFromPostPut();
			
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
			
			//If the zip code is invalid, send request xml validation failed message back to the user.
			if (!validateZipCode(request.getZip())) {
				response = new ModifyBuyerAccountResponse();
				response.setResults(				
					Results.getError(ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE.getMessage(), 
						ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE.getCode()));
				return response;
			}
			
			Integer buyerResourceId = apiVO.getBuyerResourceIdInteger();
			request.setBuyerResourceId(buyerResourceId);
			
			buyerRegistrationVO = BuyerAccountMapper.getVOForModifyBuyerAccount(request);
			buyerRegistrationVO = buyerRegistrationBO.updateSimpleBuyerInformation(buyerRegistrationVO);
			response.setResults(Results.getSuccess());
		} catch(DataServiceException e) {
			//This exception is thrown when zip code validation fails.
			response = new ModifyBuyerAccountResponse();
			response.setResults(
				Results.getError(ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE.getMessage(), 
					ResultsCode.SEARCH_VALIDATION_ZIP_ERROR_CODE.getCode()));
		}catch(DataException e) {
			logger.error("ModifyBuyerService exception detail: " + e.getMessage());
			response = new ModifyBuyerAccountResponse();
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
						ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		} catch(BusinessServiceException e) {
			logger.error("ModifyBuyerService exception detail: " + e.getMessage());
			response = new ModifyBuyerAccountResponse();
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
						ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		logger.info("Exiting execute method");	
		return response;
	}
	
	/**
	 * Method to validate zip code
	 * @param zip
	 * @return
	 * @throws DataServiceException
	 */
	private boolean validateZipCode(String zip) throws DataServiceException {
		try {
			 if(StringUtils.isEmpty(zip)) {
				 return true;
			 }
			 if(lookupBO.checkIFZipExists(zip) != null) {
				 return true;
			 }
		} catch(DataServiceException e) {
			logger.error("zip code validation failed for zip: " + zip + ", error message: " + e.getMessage());
			throw e;
		}
		return false;
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



