package com.newco.marketplace.api.services.wallet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.wallet.AddFundsToWalletRequest;
import com.newco.marketplace.api.beans.wallet.AddFundsToWalletResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.server.v1_1.WalletRequestProcessor;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.AccountType;
import com.newco.marketplace.api.utils.mappers.wallet.WalletHistoryMapper;
import com.newco.marketplace.api.utils.validators.RequestValidator;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.daoImpl.ledger.TransactionDaoImpl;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.exception.SLBusinessServiceException;

/**
 * This is a service class for following API. It calls back-end methods to add funds to buyer's account. 
 * 
 * API     : /buyers/{buyerId}/wallet/funds/{fundingSource}
 * APIType : PUT
 * 
 * RequestProcessor   : {@link WalletRequestProcessor#addFundsToWallet}
 * Spring Bean Name   : AddFundsToWalletService
 * Spring Context XML : apiApplicationContext.xml
 * 
 * @author Shekhar Nirkhe
 * @since 10/01/2009
 *
 */
@Namespace("http://www.servicelive.com/namespaces/wallet/funds")
@APIRequestClass(AddFundsToWalletRequest.class)
@APIResponseClass(AddFundsToWalletResponse.class)
public class AddFundsToWalletService extends BaseService {
	  private IFinanceManagerBO financeManagerBO;
	  private PromoBO promoBO;
	  private WalletHistoryMapper walletHistoryMapper;
	  private XStreamUtility conversionUtility;
	  private ICreditCardBO creditCardBO;
	  private RequestValidator requestValidator;
	
	private Logger logger = Logger.getLogger(AddFundsToWalletService.class);
	/**
	 * This method is for adding funds to  wallet.
	 * 
	 * @param fromDate String,toDate String
	 * @return String
	 */
	
	public AddFundsToWalletService () {		
		addRequiredURLParam(APIRequestVO.BUYERID, DataTypes.INTEGER);
	}	
	
	
	/**
	 * This method calls back-end methods to add funds to requested account.
	 * Based on accountType parameter in request it can add funds to Bank Account as well as 
	 * Credit Card. Generally funds to bank are added immediately. In case of Credit Card it goes to
	 * pending. autoACHInd indicator is always set to false. It needs following parameter to add funds :	  
	 * 		buyerId, buyerResourceId, accountId(fundingSource), 
	 * 		roleType (currently only 5), CVV (only for CC).
	 * 
	 * Note that request will be rejected if buyerId + resourceId received in the message does not match with
	 * buyerId + resourceId present in the DB.
	 * 
	 * After adding funds to account it goes to accounts.ledger_balance table (via {@link TransactionDaoImpl})
	 * to get latest balance.
	 * 
	 * @param APIRequestVO apiVO
	 * @return IAPIResponse : In response it returns following values.
 	 *	projectBalance   : Total balance
 	 *	pendingBalance   : Pending balance.
 	 *  availableBalance : Balance available for use. (projectBalance - pendingBalance)
	 * 
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {		
		logger.info("Entering execute method");	
		AddFundsToWalletRequest request  = (AddFundsToWalletRequest) apiVO.getRequestFromPostPut();	
		Results results = null;
		Integer buyerResourceId = request.getIdentification().getId();
		
		String autoACHInd = "false", user = "test";	
		SecurityContext securityContext = super.getSecurityContextForBuyer(buyerResourceId);
		Integer companyId = securityContext.getCompanyId();
		String roleType = securityContext.getRole();
		FundingVO fundingVO = new FundingVO();
		fundingVO.setAmtToFund(request.getAmount());	
	
		Integer receivedCompanyId =  apiVO.getBuyerIdInteger();
		Integer fundingSource = (Integer)apiVO.getProperty("fundingSource");
		
	
//		if (receivedCompanyId.intValue() != companyId.intValue()) {
//			String error = "Buyer Id " + receivedCompanyId + " is not associated with resource " 
//						+  buyerResourceId;
//			results = Results.getError(error, ResultsCode.INVALIDXML_ERROR_CODE.getCode());
//			return new AddFundsToWalletResponse(results);
//		}
		
		/*if (request.getAmount() == 0.0) {
			String error = "Amount value must be more than 0.";
			results = Results.getError(error, ResultsCode.INVALIDXML_ERROR_CODE.getCode());
			return new AddFundsToWalletResponse(results);
		}*/
		
    	user =  securityContext.getUsername();
		
		AddFundsToWalletResponse response = null;	
		AccountType accType = AccountType.getObject(request.getAccountType());
		boolean promoWalletCredit = false;

	    if (accType.equals(AccountType.Bank)&&request.getAmount() > 0.0) {
	      ProcessResponse pr = this.financeManagerBO.depositfunds(companyId, roleType, fundingVO, fundingSource.intValue(), user, autoACHInd);

	      if (pr.getMessages().size() > 0) {
	        for (String err : pr.getMessages()) {
	          this.logger.error("Error in depositing buyer funds:" + err);
	          ErrorResult errorResult = new ErrorResult(ResultsCode.GENERIC_ERROR.getCode(), err);
	          results = new Results();
	          results.addError(errorResult);
	        }
	      }
	      else
	        promoWalletCredit = true;
	    }
	    else if(request.getAmount() > 0.0){
	      CreditCardVO creditCardVO = new CreditCardVO();
	      creditCardVO.setCardVerificationNo(request.getCvv());
	      creditCardVO.setTransactionAmount(request.getAmount());
	      creditCardVO.setCardId(Long.valueOf(fundingSource.longValue()));
	      creditCardVO.setEntityId(companyId);

	      creditCardVO.setUserName(user);
	      creditCardVO.setSendFulfillment(true);
	      creditCardVO.setEnabled_ind(Boolean.TRUE.booleanValue());
	      try
	      {
	        creditCardVO = this.creditCardBO.authorizeCardTransaction(creditCardVO, fundingVO.getSoId(), roleType);

	        if (creditCardVO.isAuthorized()) {
	        	promoWalletCredit = true;
	        } else {
	          String error = null;
	          if (StringUtils.isNotBlank(creditCardVO.getAnsiResponseCode())) {
	            error = creditCardVO.getAnsiResponseCode();
	          }

	          if (StringUtils.isNotBlank(creditCardVO.getCidResponseCode())) {
	            error = creditCardVO.getCidResponseCode();
	          }
	          results = Results.getError("Error in authorizing card : Auth service error code is :" + error, ResultsCode.AUTHORIZATION_ERROR_CODE.getCode());
	        }
	      }
	      catch (BusinessServiceException e) {
	        e.printStackTrace();
	        String error = e.getLocalizedMessage();
	        results = Results.getError(error, ResultsCode.INTERNAL_SERVER_ERROR.getCode());
	      }

	    }	
	    else if(request.getAmount() <= 0.0){
	    	promoWalletCredit=true;
	    }
		if (promoWalletCredit) {
		      if (securityContext.getRoleId().intValue() == 5 && null!=request.getPromotion() && null!=request.getPromotion().getPromotype()
		    		  && request.getPromotion().getValue()>0.0) {
		        this.logger.info("** Promotional Credit for the Simple Buyer ***);\n");
		        this.logger.info("Promotype = " + request.getPromotion().getPromotype() + "\n" + "Buyer id  = " + apiVO.getBuyerId() + "\n" + "Value     = " + request.getPromotion().getValue());

		        Buyer buyer = new Buyer();
		        buyer.setBuyerId(new Integer(apiVO.getBuyerId()));
		        buyer.setSourceId(buyerResourceId.toString());
		        buyer.setRoleId(securityContext.getRoleId());
		        buyer.setUserName(securityContext.getUsername());
		        try
		        {
		          promoBO.applyPromoWalletCredit(request.getPromotion().getPromocode(),request.getPromotion().getPromotype(),buyer,request.getPromotion().getValue());
		          results = Results.getSuccess();
		        }
		        catch (SLBusinessServiceException exp) {
		          results = Results.getError(ResultsCode.WALLET_CREDIT_PROMO_ERROR.getMessage(), ResultsCode.WALLET_CREDIT_PROMO_ERROR.getCode());
		        }
		      }
		      else {
		        results = Results.getSuccess();
		      }

		}
		// Get value from GetWalletBalanceAPI when it is ready.
		
		AjaxCacheVO ajaxCacheVO = new AjaxCacheVO();
		ajaxCacheVO.setCompanyId(companyId);
		ajaxCacheVO.setRoleType(roleType);
		
		double availableBalance = financeManagerBO.getavailableBalance(ajaxCacheVO);		
		double projectBalance = financeManagerBO.getcurrentBalance(ajaxCacheVO);
		double pendingBalance = MoneyUtil.subtract(projectBalance,availableBalance);
		
		response = new AddFundsToWalletResponse(results, pendingBalance, projectBalance, availableBalance);		
		
		logger.info("Exiting execute method");	
		return response;
	}

	public PromoBO getPromoBO() {
		    return this.promoBO;
    }

	public void setPromoBO(PromoBO promoBO) {
	   this.promoBO = promoBO;
	}
		  
	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	public WalletHistoryMapper getWalletHistoryMapper() {
		return walletHistoryMapper;
	}

	public void setWalletHistoryMapper(WalletHistoryMapper walletHistoryMapper) {
		this.walletHistoryMapper = walletHistoryMapper;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public ICreditCardBO getCreditCardBO() {
		return creditCardBO;
	}

	public void setCreditCardBO(ICreditCardBO creditCardBO) {
		this.creditCardBO = creditCardBO;
	}

	public RequestValidator getRequestValidator() {
		return requestValidator;
	}

	public void setRequestValidator(RequestValidator requestValidator) {
		this.requestValidator = requestValidator;
	}
}



