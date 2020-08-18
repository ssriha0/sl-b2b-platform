package com.newco.marketplace.api.services.wallet.fundingsource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.fundingsources.BankAccount;
import com.newco.marketplace.api.beans.fundingsources.BankAccounts;
import com.newco.marketplace.api.beans.fundingsources.CreditCardAccount;
import com.newco.marketplace.api.beans.fundingsources.CreditCards;
import com.newco.marketplace.api.beans.fundingsources.GetFundingSourceResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.wallet.fundingsource.GetFundingSourceMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * @author ndixit
 * This service class returns all the funding sources for a given buyer Id. 
 */
public class GetFundingSourceService extends BaseService {
	private Logger logger = Logger.getLogger(GetFundingSourceService.class);
	private XStreamUtility conversionUtility;
	private IFinanceManagerBO financeManagerBO;
	private ICreditCardBO creditCardBO;
	private GetFundingSourceMapper getFundingSourceMapper;
	
	/**
	 * 
	 */
	public GetFundingSourceService() {
		super (null,
				PublicAPIConstant.GET_FUNDING_SOURCE_RESPONSE_XSD, 
				PublicAPIConstant.GET_FUNDING_SOURCE_NAMESPACE, 
				PublicAPIConstant.GET_FUNDING_SOURCE_RESOURCES_SCHEMAS,
				PublicAPIConstant.GET_FUNDING_SOURCE_RESOURCES_SCHEMASLOCATION,
				null,
				GetFundingSourceResponse.class);
		super.addMoreClass(BankAccounts.class);
		super.addMoreClass(CreditCards.class);
		super.addMoreClass(BankAccount.class);
		super.addMoreClass(CreditCardAccount.class);
	}

	/**
	 * @param APIRequestVO
	 * 
	 * @return Response object.
	 */
	@SuppressWarnings("null")
	public IAPIResponse execute(APIRequestVO apiVO) { 
		logger.info("Entering GetFundingSourceService.execute()");
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();

		Integer buyerId = apiVO.getBuyerIdInteger();
		
		CreditCardVO creditVO = new CreditCardVO();
		BankAccounts bankAccountss = new BankAccounts();
		CreditCards creditCards = new CreditCards();
		
		
		
		List<Account> fundingSourceList = new ArrayList<Account>();
		List<CreditCardVO> ccVOList = new ArrayList<CreditCardVO>();
		BankAccount bankAccounts = null;
		Results results = null;
		CreditCardAccount ccAccounts = null;
		GetFundingSourceResponse response = null;
		List<BankAccount> bankSources = new ArrayList<BankAccount>();
		List<CreditCardAccount> ccSources = new ArrayList<CreditCardAccount>();
		
		boolean hasRecords = false;
		String resultMessage = null;
		
		
		creditVO.setEntityId(buyerId);
		
		//Get bank and credit card data.
		try{
			fundingSourceList = financeManagerBO.accountDetails(buyerId);
			ccVOList = creditCardBO.getActiveCardListForBuyer(buyerId);
			Set<Long> ccSet = new HashSet<Long>(ccVOList.size());
			
			//Iterate through Bank accounts
			for(Account bList : fundingSourceList){
				int bankAccountType = bList.getAccount_type_id().intValue();
				if(bList.isEnabled_ind() && (bankAccountType==10 || bankAccountType==20)){
					hasRecords = true;					
					bankAccounts = new BankAccount();
					bankAccounts.setAccountNumber(mask(bList.getAccount_no(),SourceType.Bank));
					bankAccounts.setAccountHolderName(bList.getAccount_holder_name());
					bankAccounts.setBankAccountType(bankAccountType);
					bankAccounts.setBankName(bList.getBank_name());
					bankAccounts.setRoutingNumber(bList.getRouting_no());
					bankAccounts.setBankAccountId(bList.getAccount_id());
					bankSources.add(bankAccounts);
				}					
			}
			
			//Iterate through credit card accounts.
			for(CreditCardVO ccList : ccVOList){
				hasRecords = true;
				ccAccounts = new CreditCardAccount();
				ccAccounts.setAccountHolderName(ccList.getCardHolderName());		
				ccAccounts.setBillingAddress1(ccList.getBillingAddress1());
				ccAccounts.setBillingAddress2(ccList.getBillingAddress2());
				ccAccounts.setBillingCity(ccList.getBillingCity());
				ccAccounts.setBillingState(ccList.getBillingState());
				ccAccounts.setBillingZip(ccList.getZipcode());
				ccAccounts.setCardNumber(mask(ccList.getCardNo(),SourceType.CC));
				ccAccounts.setCardType(ccList.getCardTypeId().intValue());
				ccAccounts.setCcAccountId(ccList.getCardId());
				ccAccounts.setExpirationDate(ccList.getExpireDate());
				if(ccList.isDefault_ind())
					ccAccounts.setDefaultCard(true);
				// Introduced to avoid duplicate credit cards
				if(!ccSet.contains(ccAccounts.getCcAccountId())){
					ccSources.add(ccAccounts);
					ccSet.add(ccAccounts.getCcAccountId());
				}
			}
			
			if(!hasRecords){
				results = Results.getError("There are no funding sources", ResultsCode.GET_FUNDING_SOURCE_FAILED_DURING_RETRIVAL.getCode());				
			}
			
		}catch(BusinessServiceException ex){
			logger.error("Funding source retrieval exception" + ex.getMessage(), ex);
			results = Results.getError(ex.getMessage(), ResultsCode.GET_FUNDING_SOURCE_FAILED_DURING_RETRIVAL.getCode());
		}catch(StringIndexOutOfBoundsException ex){
			logger.error("card/account numbers are not correct " + ex.getMessage(), ex);
			results = Results.getError("Please check account/card number "+ex.getMessage(), ResultsCode.GET_FUNDING_SOURCE_FAILED_DURING_RETRIVAL.getCode());
		}
		
		bankAccountss.setBankAccountsList(bankSources);
		creditCards.setCreditCardList(ccSources);
				
		//Send the number of records found with Result
		if(hasRecords){
			if(results == null){
				resultMessage = "Total BankAccounts " + bankSources.size() + " CreditCards "
				+ ccSources.size();
				results = Results.getSuccess(resultMessage);
			}
		}
		
		//Create response object based on the data retrieved.
		response = new GetFundingSourceResponse(results, bankAccountss, creditCards);		

		logger.info("Exiting dispatchProviderSearchByIdRequest method");
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

	public GetFundingSourceMapper getGetFundingSourceMapper() {
		return getFundingSourceMapper;
	}

	public void setGetFundingSourceMapper(
			GetFundingSourceMapper getFundingSourceMapper) {
		this.getFundingSourceMapper = getFundingSourceMapper;
	}
	
}
