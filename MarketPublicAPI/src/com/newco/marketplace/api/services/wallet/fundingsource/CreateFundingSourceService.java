package com.newco.marketplace.api.services.wallet.fundingsource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.fundingsources.AccountSourceType;
import com.newco.marketplace.api.beans.fundingsources.BankAccount;
import com.newco.marketplace.api.beans.fundingsources.BankAccounts;
import com.newco.marketplace.api.beans.fundingsources.CreateFundingSourceRequest;
import com.newco.marketplace.api.beans.fundingsources.CreateFundingSourceResponse;
import com.newco.marketplace.api.beans.fundingsources.CreditCardAccount;
import com.newco.marketplace.api.beans.fundingsources.CreditCards;
import com.newco.marketplace.api.beans.fundingsources.GetFundingSourceResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.APIValidatationException;
import com.newco.marketplace.api.common.APIValidator;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;

/**
 * @author ndixit
 * Creates funding sources based on the resource Ids.Create funding source calls 
 * getFundingSources API after creating, to return all the funding sources exists in 
 * the backend for that buyer Id. 
 */
public class CreateFundingSourceService extends BaseService{
	private Logger logger = Logger.getLogger(CreateFundingSourceService.class);
	private XStreamUtility conversionUtility;
	private IFinanceManagerBO financeManagerBO;
	private ICreditCardBO creditCardBO;
	private GetFundingSourceService getFundingSourceService;
	public static final String BANK= "Bank";
	public static final String CreditCard= "CreditCard";
	
	@Resource
	protected HttpServletRequest httpRequest;
	
	/**
	 * Calls base constructor with namespace,schema,xsd class information.  
	 */
	public CreateFundingSourceService() {
		super (PublicAPIConstant.CREATE_FUNDING_SOURCE_REQUEST_XSD,
				PublicAPIConstant.CREATE_FUNDING_SOURCE_RESPONSE_XSD, 
				PublicAPIConstant.CREATE_FUNDING_SOURCE_NAMESPACE, 
				PublicAPIConstant.CREATE_FUNDING_SOURCE_RESOURCES_SCHEMAS,
				PublicAPIConstant.CREATE_FUNDING_SOURCE_RESOURCES_SCHEMASLOCATION,	
				CreateFundingSourceRequest.class,
				GetFundingSourceResponse.class);
		super.addMoreClass(AccountSourceType.class);
		super.addMoreClass(BankAccounts.class);
		super.addMoreClass(CreditCards.class);
		super.addMoreClass(BankAccount.class);
		super.addMoreClass(CreditCardAccount.class);
	}
	
	/**
	 * @param APIRequestVO  
	 * @return
	 * Generic execute with API request as param.
	 */
	public IAPIResponse execute(APIRequestVO apiVO){

		logger.info("Entering CreateFundingSourceService.execute()");
		boolean status = true; //Flag to indicate Success or failure
		CreateFundingSourceResponse fundingSources = new CreateFundingSourceResponse();
		CreateFundingSourceRequest request = (CreateFundingSourceRequest)apiVO.getRequestFromPostPut();
		SecurityContext securityContext = super.getSecurityContextForBuyerAdmin(apiVO.getBuyerIdInteger());
		Integer entityId = securityContext.getCompanyId();
		
		IAPIResponse response = null;

		
		String accType = request.getTypeOfSource();
		Map<String,String> map = new HashMap<String,String>();
		APIRequestVO getVO = new APIRequestVO(httpRequest);
		
		//Create source for bank.
		if (accType.equals(BANK)) {
			Account account = new Account();
			account.setAccount_holder_name(request.getAccountSourceType().getBankAccountHolderName());
			account.setBank_name(request.getAccountSourceType().getBankName());
			account.setRouting_no(request.getAccountSourceType().getRoutingNumber().toString());
			account.setAccount_no(request.getAccountSourceType().getAccountNumber().toString());
			if (OrderConstants.BUYER.equalsIgnoreCase(securityContext.getRole()) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(securityContext.getRole())) {
				account.setOwner_entity_type_id((long)OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER); 
			}
			account.setCountry_id((long)OrderConstants.COUNTRY_ID_US); 
			account.setAccount_status_id((long)OrderConstants.ACCOUNT_STATUS_ACTIVE);
			account.setAccount_descr(request.getAccountSourceType().getAccountDescription());
			account.setAccount_type_id((long)request.getAccountSourceType().getAccountTypeId());
			account.setOwner_entity_id(entityId.longValue());
			
			try{
				if(validateBank(account)){
					financeManagerBO.saveAccountDetails(account);
				}else{
					fundingSources.setResults(Results.getError("Bank validation failure", ResultsCode.CREATE_FUNDING_SOURCE_FAILED.getCode()));
					status = false;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				fundingSources.setResults(Results.getError("Creating funding source failed" + ex.getMessage(), ResultsCode.CREATE_FUNDING_SOURCE_FAILED.getCode()));
				status = false;
			}
		} else{ //Create source for Credit Card.
			try{
				CreditCardVO creditCardVO = new CreditCardVO();
				creditCardVO.setCardHolderName(request.getAccountSourceType().getAccountHolderName());				
				creditCardVO.setBillingAddress1(request.getAccountSourceType().getBillingAddress1());
				creditCardVO.setBillingAddress2(request.getAccountSourceType().getBillingAddress2());
				creditCardVO.setBillingCity(request.getAccountSourceType().getCity());
				creditCardVO.setBillingState(request.getAccountSourceType().getState());
				creditCardVO.setZipcode(request.getAccountSourceType().getZip());
				creditCardVO.setCardNo(request.getAccountSourceType().getCardNumber());
				creditCardVO.setCardTypeId(new Integer(request.getAccountSourceType().getCardType()).longValue());
				creditCardVO.setExpireDate(request.getAccountSourceType().getExpirationDate());
				if (OrderConstants.BUYER.equalsIgnoreCase(securityContext.getRole()) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(securityContext.getRole())) {
					creditCardVO.setEntityTypeId(OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER);
				}
				creditCardVO.setEntityId(entityId);
				creditCardVO.setCountryId(OrderConstants.COUNTRY_ID_US); 
				creditCardVO.setAccountTypeId(LedgerConstants.CC_ACCOUNT_TYPE);
				creditCardVO.setAccountStatusId(OrderConstants.ACCOUNT_STATUS_ACTIVE);
				creditCardVO.setActive_ind(Boolean.TRUE);
				creditCardVO.setDefault_ind(Boolean.TRUE);
			    creditCardVO.setEnabled_ind(Boolean.TRUE);
				creditCardVO.setLocationTypeId(5);
				
				try {
					APIValidator.getInstance().validateCreditCard(creditCardVO);
					creditCardBO.saveAsDefaultCard(creditCardVO);
				} catch (APIValidatationException e) {				
					fundingSources.setResults(Results.getError(e.getMessage(), ResultsCode.INVALIDXML_ERROR_CODE.getCode()));
					status = false;
				}
			} catch(Exception ex) {
				ex.printStackTrace();
				fundingSources.setResults(Results.getError("Creating funding source failed", ResultsCode.CREATE_FUNDING_SOURCE_FAILED.getCode()));
				status = false;
			}
		}
		//Set request for getFundingSource call. Create should returns all the existing funding sources.
		//map.put(PublicAPIConstant.Wallet.BUYER_ID, entityId.toString());
		getVO.setRequestFromGetDelete(map);
		getVO.setRequestType(RequestType.Get);
		getVO.setBuyerId(entityId.toString());
		
		//Returns success/failure in the response.
		if(status == true){
			response = (GetFundingSourceResponse)getFundingSourceService.execute(getVO);
		}else{
			response = (GetFundingSourceResponse)new GetFundingSourceResponse(fundingSources.getResults(),null,null);
			//response = createAccountFailure();
		}

		logger.info("Exiting CreateFundingSourceService.execute()");
		return response;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	public ICreditCardBO getCreditCardBO() {
		return creditCardBO;
	}

	public void setCreditCardBO(ICreditCardBO creditCardBO) {
		this.creditCardBO = creditCardBO;
	}

	public GetFundingSourceService getGetFundingSourceService() {
		return getFundingSourceService;
	}

	public void setGetFundingSourceService(
			GetFundingSourceService getFundingSourceService) {
		this.getFundingSourceService = getFundingSourceService;
	}

	/*
	private GetFundingSourceResponse createAccountFailure(){
		String resultMessage = CommonUtility.getMessage(PublicAPIConstant.CREATE_FUNDING_SOURCE_ERROR_CODE);
		
		Results results = Results.getSuccess();
		List<Result> resultList=new ArrayList<Result>();
		Result result = new Result();
		result.setCode(PublicAPIConstant.ZERO);
		result.setMessage("");
		resultList.add(result);
		results.setResult(resultList);
		results.setError(Results.getError(resultMessage,PublicAPIConstant.ZERO).getError());
		
		return (new GetFundingSourceResponse(results,null,null));
	} */


}
