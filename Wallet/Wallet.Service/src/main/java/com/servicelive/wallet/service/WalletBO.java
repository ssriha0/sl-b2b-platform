package com.servicelive.wallet.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.wallet.ach.IAchBO;
import com.servicelive.wallet.creditcard.ICreditCardBO;
import com.servicelive.wallet.creditcard.IHSAuthServiceCreditCardBO;
import com.servicelive.wallet.creditcard.dao.ICreditCardDao;
import com.servicelive.wallet.ledger.ILedgerBO;
import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.ledger.vo.TransactionVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ProviderWithdrawalErrorVO;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.servicelive.wallet.valuelink.IValueLinkBO;

public class WalletBO implements IWalletBO {

	private IAchBO achBO;

	private ICreditCardBO creditCardBO;

	private ILedgerBO ledgerBO;

	private IValueLinkBO valueLinkBO;

	private ILookupBO lookupBO;

	private IApplicationProperties applicationProperties;

	private IHSAuthServiceCreditCardBO hsAuthServiceCreditCardBo;


	private Logger logger = Logger.getLogger(WalletBO.class);

	private void logEnterMethod( String methodName, WalletVO walletVO ) {
		if( logger.isInfoEnabled() ) {
			StringBuffer message = new StringBuffer();
			message.append(methodName);
			if( walletVO != null && walletVO.getOrderPricing() != null && walletVO.getOrderPricing().getAmount() != null ) {
				message.append(", amount =").append(walletVO.getOrderPricing().getAmount());
			}
			if(walletVO!= null && walletVO.getServiceOrderId() != null){
				message.append(", service order id = ").append(walletVO.getServiceOrderId());
			}
			logger.info(message.toString());
		}
	}

	public double getCurrentSpendingLimit(String serviceOrderId) throws SLBusinessServiceException {
		return ledgerBO.getPostSOLedgerAmount(serviceOrderId);
	}

	public double getCompletedSOLedgerAmount(long vendorId) throws SLBusinessServiceException
	{
		return valueLinkBO.getCompletedAmount(vendorId);
	}

	public boolean checkValueLinkReconciledIndicator(String soId) throws SLBusinessServiceException {
		return valueLinkBO.checkValueLinkReconciledIndicator(soId);
	}

	public boolean isACHTransPending(String  serviceOrderId) throws SLBusinessServiceException {

		return valueLinkBO.isACHTransPending(serviceOrderId);

	}


	public boolean hasPreviousAddOn(String serviceOrderId) throws SLBusinessServiceException{
		return valueLinkBO.hasPreviousAddOn(serviceOrderId);

	}


	public double getBuyerTotalDeposit(Long buyerId) throws SLBusinessServiceException {
		return ledgerBO.getBuyerTotalDeposit(buyerId);
	}

	public boolean isBuyerAutoFunded(Long buyerId) throws SLBusinessServiceException {
		return achBO.isEntityAutoFunded(buyerId, (int)CommonConstants.LEDGER_ENTITY_TYPE_BUYER);
	}
	public WalletResponseVO adminCreditToBuyer(WalletVO request) throws SLBusinessServiceException {
		logEnterMethod("adminCreditToBuyer",request);
		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER);
		request.getValueLink().setAdminCredit(true);
		return executeLedgerAndValueLink(request);
	}

	public WalletResponseVO withdrawBuyerdebitReversal(WalletVO request) throws SLBusinessServiceException {
		logEnterMethod("withdrawBuyerdebitReversal",request);
		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_ESCHEATMENT_BUYER_DEBIT_REVERSAL);
		return executeLedgerAndValueLink(request);
	}

	public WalletResponseVO adminCreditToProvider(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("adminCreditToProvider",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER);
		return executeLedgerAndValueLink(request);

		// double providerBalanceBefore = getProviderAvailableBalance(providerId);
		// get available balance for provider before posting transactions
		// double providerBalanceBefore = getProviderAvailableBalance(providerId);
	}

	public WalletResponseVO adminDebitFromBuyer(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("adminDebitFromBuyer",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER);
		return executeLedgerAndValueLink(request);
	}

	public WalletResponseVO adminEscheatmentFromBuyer(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("adminEscheatmentFromBuyer",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError()) {
			achBO.escheatBuyerFunds(request.getAch());
		}
		return response; 
	}

	public WalletResponseVO adminDebitFromProvider(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("adminDebitFromProvider",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER);
		return executeLedgerAndValueLink(request);
	}

	public WalletResponseVO adminEscheatmentFromProvider(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("adminEscheatmentFromProvider",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError()) {
			achBO.escheatProviderFunds(request.getAch());
		}
		return response; 
	}

	public WalletResponseVO cancelServiceOrder(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("cancelServiceOrder",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER)  
				&& (request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			// auto ach if SHC funding type Added changes for SLT SLT-1277 @bkumar2
			logger.info(request.getFundingTypeId());
			achBO.withdrawBuyerFundsWithInstantACH(request.getAch());
			logger.info(request.getFundingTypeId());
		}
		return response;
	}

	public WalletResponseVO cancelServiceOrderWithoutPenalty(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("cancelServiceOrderWithoutPenalty",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER) &&
				(request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			// // auto ach if SHC funding type Added changes for SLT SLT-1277 @bkumar2
			logger.info(request.getFundingTypeId());
			achBO.withdrawBuyerFundsWithInstantACH(request.getAch());
			logger.info(request.getFundingTypeId());
		}
		return response;
	}

	public WalletResponseVO closeServiceOrder(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("closeServiceOrder",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER)
				&& (request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			// auto ach if SHC funding type
			achBO.withdrawBuyerFundsWithInstantACH(request.getAch());
		}
		return response;
	}

	public WalletResponseVO decreaseProjectSpendLimit(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("decreaseProjectSpendLimit",request);
		double spendLimit=getCurrentSpendingLimit(request.getServiceOrderId());
		if(spendLimit<0.0)
		{
			logger.error("spend limit cannot be negative");
			throw new SLBusinessServiceException("There was an error in the payment processing for this service order."); 

		}   
		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DECREASE_SO_ESCROW);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER)
				&& (request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			// auto ach if SHC funding type
			achBO.withdrawBuyerFundsWithInstantACH(request.getAch());
		}
		return response;
	}

	public void depositBuyerFundsAtValueLink(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("depositBuyerFundsAtValueLink",request);
		ValueLinkVO vlRequest = request.getValueLink();
		vlRequest.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER);
		valueLinkBO.sendValueLinkRequest(vlRequest);
	}

	public WalletResponseVO depositBuyerFundsWithCash(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("depositBuyerFundsWithCash",request);

		WalletResponseVO response = new WalletResponseVO();

		if (request.getLedger().getAccountId() == null) {
			response.addErrorMessage("Unable to deposit funds due invalid account selection");
		}

		if( response.isError() ) { return response; }

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER);

		//execute ledger and populate Ledger Id for ACH
		executeLedger(request, response);		

		if( response.isError()) { return response; }

		achBO.depositBuyerFundsWithCash(request.getAch());

		return response;
	}

	public WalletResponseVO getCreditCardInformation(Long accountId) throws SLBusinessServiceException {

		WalletResponseVO response = new WalletResponseVO();
		if (accountId == null) {
			response.addErrorMessage("Account ID cannot be null.");
		}
		if( response.isError() ) { return response; }

		SLCreditCardVO ccvo = lookupBO.lookupCreditCard(accountId);
		response.setCreditCard(ccvo);

		return response;
	}

	public Double getTransactionAmount(Long transactionId) throws SLBusinessServiceException {
		try {
			return ledgerBO.getTransactionAmountById(transactionId);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e);
		}
	}

	public WalletResponseVO depositBuyerFundsWithCreditCard(WalletVO request) throws SLBusinessServiceException {
		logEnterMethod("depositBuyerFundsWithCreditCard",request);
		SLCreditCardVO ccResponse = null;
		String userName = null;
		WalletResponseVO response = createWalletResponseVOCheckCC(request);
		if( response.isError() ) {
			return response; 
		}
		/** SL-20852 : New Method to invoke HS Authorization Web service which returns token,settlement
		 *  key and Masked account no while adding fund using credit card
		 * */
		if(null!= request.getHsWebFlag() && request.getHsWebFlag()){
			try{
				logger.info("User Name from Card"+ request.getCreditCard().getUserName());
				if(StringUtils.isNotBlank(request.getCreditCard().getUserName())){
					userName = request.getCreditCard().getUserName();
				}
				ccResponse = hsAuthServiceCreditCardBo.authorizeHSSTransaction(request.getCreditCard(),userName);
			}catch (Exception e) {
				logger.error("Unable to invoke the HSS Web Service");
				response.addErrorMessage("This transaction was not approved; please verify the card information you provided and try again or contact your credit card company");
				return response;
			}
		}else{
			try{
				ccResponse = creditCardBO.authorizeCardTransaction(request.getCreditCard());
			}catch (Exception e) {
				logger.error("Unable to invoke the RTCA  Web Service");
				response.addErrorMessage("This transaction was not approved; please verify the card information you provided and try again or contact your credit card company");
				return response;
			}
		}
		response.setCreditCard(ccResponse);
		if (ccResponse.isAuthorized()) {
			if(null!= request.getHsWebFlag() && request.getHsWebFlag()){
				//Setting resp id to update ach_process_queue id in account_hs_auth_resp
				request.getAch().setHsAuthRespId(ccResponse.getRespId());
				//Setting HS flag to update ach_process_queue id in account_hs_auth_resp
				request.getAch().setHsWebLive( request.getHsWebFlag());
				//setting approval code from response for cc authorization number.
				request.getLedger().setCreditCardAuthorizationNumber(ccResponse.getApprovalCode());
			}else{
				request.getLedger().setCreditCardAuthorizationNumber(ccResponse.getAuthorizationCode());
			}
			request.getLedger().setCreditCardTypeId(ccResponse.getCardTypeId());
			// determine the ledger business transaction id, based on the credit card type
			long businessTransactionId = CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX;
			if (request.getCreditCard().getCardTypeId() == CommonConstants.CREDIT_CARD_VISA || request.getCreditCard().getCardTypeId() == CommonConstants.CREDIT_CARD_MC) {
				businessTransactionId = CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX;
			} // SLT-2591 and SLT-2592: Disable Amex
			/*else if (request.getCreditCard().getCardTypeId() == CommonConstants.CREDIT_CARD_AMEX) {
				businessTransactionId = CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_AMEX;
			}*/ else {
				businessTransactionId = CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_SEARS;
			}

			//Now that we know the business transaction Id lets set it on the request
			request.setBusinessTransactionId(businessTransactionId);

			logger.info("inside depositBuyerFundsWithCreditCard method");
			executeLedgerAndValueLink(request, CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER, response);
			if (!response.isError()) {
				achBO.depositBuyerFundsWithCreditCard(request.getAch());
			}
		}

		return response;
	}

	public WalletResponseVO depositBuyerFundsWithInstantACH(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("depositBuyerFundsWithInstantACH",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError()) {
			achBO.depositBuyerFundsWithInstantACH(request.getAch());
		}
		return response;
	}

	public void depositSLOperationFundsAtValueLink(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("depositSLOperationFundsAtValueLink",request);
		ValueLinkVO vlRequest = request.getValueLink();
		vlRequest.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS);
		valueLinkBO.sendValueLinkRequest(vlRequest);
	}

	public double getBuyerAvailableBalance(long entityId) throws SLBusinessServiceException {

		return ledgerBO.getAvailableBalance(entityId, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, false);
	}


	//SL-21117: Revenue Pull Code change starts

	public List <String> getPermittedUsers() throws BusinessServiceException{

		return ledgerBO.getPermittedUsers(CommonConstants.REVENUE_PULL_USER_IND);
	}

	public double getAvailableBalanceForRevenuePull() throws SLBusinessServiceException {

		return ledgerBO.getAvailableBalanceForRevenuePull(CommonConstants.ENTITY_ID_SERVICELIVE, CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN);
	}


	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws SLBusinessServiceException {

		return ledgerBO.getAvailableDateCheckForRevenuePull(calendarOnDate);
	}

	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user) throws SLBusinessServiceException {

		Date todayDate = new Date();

		ledgerBO.insertEntryForRevenuePull(amount,revenuePullDate,note,user,CommonConstants.REVENUE_PULL_STATUS_IND,todayDate,CommonConstants.REVENUE_PULL_INITIAL_STATUS);
	}

	public List <String> getPermittedUsersEmail() throws BusinessServiceException{

		return ledgerBO.getPermittedUsersEmail(CommonConstants.REVENUE_PULL_USER_IND);		
	}

	//Code change ends


	public double getBuyerCurrentBalance(long entityId) throws SLBusinessServiceException {

		return ledgerBO.getCurrentBalance(entityId, CommonConstants.LEDGER_ENTITY_TYPE_BUYER);
	}

	public double getProviderBalance(long entityId) throws SLBusinessServiceException {

		return ledgerBO.getAvailableBalance(entityId, CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, false);
	}

	public double getSLOperationBalance() throws SLBusinessServiceException {

		return ledgerBO.getAvailableBalance(CommonConstants.ENTITY_ID_SERVICELIVE_OPERATION, CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION, false);
	}

	private void getTransactionIdsFromResponse(WalletResponseVO response, LedgerBusinessTransactionVO ledgerTransaction) {

		if (ledgerTransaction != null) {
			int nEntries = ledgerTransaction.getTransactions().size();
			if (nEntries > 0) {
				TransactionVO transactionVO = ledgerTransaction.getTransactions().get(0);
				response.setLedgerEntryId(transactionVO.getLedgerEntryId());


				if(transactionVO.getTransactions()!=null && transactionVO.getTransactions().size() > 0)
				{
					if(ledgerTransaction.getBusinessTransId()!=null && 
							ledgerTransaction.getBusinessTransId().longValue()==CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER){
						response.setLedgerEntryId(transactionVO.getTransactions().get(1).getLedgerEntryId());
						response.setTransactionId(transactionVO.getTransactions().get(1).getTransactionId());
					} else {
						response.setLedgerEntryId(transactionVO.getTransactions().get(0).getLedgerEntryId());
						response.setTransactionId(transactionVO.getTransactions().get(0).getTransactionId());
					}

					//see if the part of the transaction is for buyer or provider and if so populate the appropriate id
					for(TransactionEntryVO te : transactionVO.getTransactions()){
						if(te.getLedgerEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_BUYER && null == response.getBuyerTransactionId()){
							response.setBuyerTransactionId(te.getTransactionId());
						}else if(te.getLedgerEntityTypeId() == CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER && null == response.getProviderTransactionId()){
							response.setProviderTransactionId(te.getTransactionId());
						}
					}
				}
			}
		} else {
			logger.info("Invalid transaction occured");
			response.addErrorMessage("Invalid transaction occurred");
		}
	}

	public WalletResponseVO increaseProjectSpendLimit(WalletVO request) throws SLBusinessServiceException {
		request.getOrderPricing().setAmount(MoneyUtil.getRoundedMoney(request.getOrderPricing().getAmount()));
		logEnterMethod("increaseProjectSpendLimit",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER) 
				&& (request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			// auto ach if auto funding type
			achBO.depositBuyerFundsWithInstantACH(request.getAch());
		}
		return response;
	} 



	/* (non-Javadoc)
	 * @see com.servicelive.wallet.serviceinterface.IWalletBO#increaseProjectSpendCompletion(com.servicelive.wallet.serviceinterface.vo.WalletVO)
	 */
	public WalletResponseVO increaseProjectSpendCompletion(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("increaseProjectSpendCompletion",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_COMPLETION);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER)
				&& (request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			//if the buyer is auto funded than issue instant ach to fund the project
			achBO.depositBuyerFundsWithInstantACH(request.getAch());
		}
		return response;
	} 

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.IWalletBO#postServiceOrder(com.servicelive.wallet.vo.WalletVO)
	 */

	public WalletResponseVO postServiceOrder(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("postServiceOrder",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_POST_SO);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER)
				&& (request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			// auto ach if auto funding type
			achBO.depositBuyerFundsWithInstantACH(request.getAch());
		}
		return response;
	}

	public void setAch(IAchBO achBO) {
		this.achBO = achBO;
	}

	public void setCreditCard(ICreditCardBO creditCardBO) {
		this.creditCardBO = creditCardBO;
	}

	public void setLedger(ILedgerBO ledgerBO) {
		this.ledgerBO = ledgerBO;
	}

	public void setValueLink(IValueLinkBO valueLinkBO) {
		this.valueLinkBO = valueLinkBO;
	}

	public WalletResponseVO voidServiceOrder(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("voidServiceOrder",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_VOID_SO);
		WalletResponseVO response = executeLedgerAndValueLink(request);
		if (!response.isError() && achBO.isEntityAutoFunded(request.getBuyerId(), CommonConstants.LEDGER_ENTITY_TYPE_BUYER)
				&& (request.getFundingTypeId().equals(CommonConstants.SHC_FUNDING_TYPE) ||  request.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))) {
			// auto ach if auto funding type
			achBO.withdrawBuyerFundsWithInstantACH(request.getAch());
		}
		return response;
	}

	public WalletResponseVO withdrawBuyerCashFunds(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("withdrawBuyerCashFunds",request);

		WalletResponseVO response = new WalletResponseVO();

		if( request.getLedger().getAccountId() == null ) {
			logger.error("Ledger account ID is null");
			response.addErrorMessage("Unable to withdraw funds due invalid account selection");
		}

		if( response.isError() ) { return response; }

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER); //look in dep

		response = executeLedgerAndValueLink(request);
		if (!response.isError()) {
			achBO.withdrawBuyerFunds(request.getAch());
		}
		return response;
	}

	public WalletResponseVO withdrawBuyerCreditCardFunds(WalletVO request) throws SLBusinessServiceException {
		String userName =null;
		logEnterMethod("withdrawBuyerCreditCardFunds",request);

		WalletResponseVO response = createWalletResponseVOCheckCC(request);
		if( response.isError() ) { return response; }

		SLCreditCardVO ccResponse = request.getCreditCard();
		
		if(null!= request.getHsWebFlag() && request.getHsWebFlag()){
			try{
				logger.info("User Name from Card"+ request.getCreditCard().getUserName());
				if(StringUtils.isNotBlank(request.getCreditCard().getUserName())){
					userName = request.getCreditCard().getUserName();
				}
				ccResponse = hsAuthServiceCreditCardBo.refundHSTransaction(request.getCreditCard(),userName);
			}catch (Exception e) {
				logger.error("Unable to invoke the HSS Web Service");
				response.addErrorMessage("This transaction was not approved; please verify the card information you provided and try again or contact your credit card company");
				return response;
			}
	}
			else{
			try{
				ccResponse = creditCardBO.authorizeCardTransaction(request.getCreditCard());
			}catch (Exception e) {
				logger.error("Unable to invoke the HS  Web Service");
				response.addErrorMessage("This transaction was not approved; please verify the card information you provided and try again or contact your credit card company");
				return response;
			}
		}
		response.setCreditCard(ccResponse);
		if (ccResponse.isAuthorized()) {
			if(null!= request.getHsWebFlag() && request.getHsWebFlag()){
				//Setting resp id to update ach_process_queue id in account_hs_auth_resp
				request.getAch().setHsAuthRespId(ccResponse.getRespId());
				//Setting HS flag to update ach_process_queue id in account_hs_auth_resp
				request.getAch().setHsWebLive( request.getHsWebFlag());
				//setting approval code from response for cc authorization number.
				request.getLedger().setCreditCardAuthorizationNumber(ccResponse.getApprovalCode());
			}else{
				request.getLedger().setCreditCardAuthorizationNumber(ccResponse.getAuthorizationCode());
			}
		request.getLedger().setCreditCardTypeId(ccResponse.getCardTypeId());

		// determine the ledger business transaction id, based on the credit card type
		long businessTransactionId = CommonConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_AMEX; // Default is AMEX
		if (request.getCreditCard().getCardTypeId() == CommonConstants.CREDIT_CARD_VISA || request.getCreditCard().getCardTypeId() == CommonConstants.CREDIT_CARD_MC) {
			businessTransactionId = CommonConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_V_MC;
		}

		//Now that we know the business transaction Id lets set it on the request
		request.setBusinessTransactionId(businessTransactionId);

		response = executeLedgerAndValueLink(request);
		if (!response.isError()) {
			achBO.withdrawBuyerFundsCreditCard(request.getAch());
		}
		
		}
		return response;
	}
	
	public WalletResponseVO withdrawProviderFunds(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("withdrawProviderFunds",request);

		WalletResponseVO response = new WalletResponseVO();

		double withdrawalLimit = request.getProviderMaxWithdrawalLimit();
		int withdrawalNumberLimit = request.getProviderMaxWithdrawalNo();
		Integer timeIntervalSec =  getProviderSameAmtTimeInterval();

		HashMap<String,Double> hash = ledgerBO.getPastWithdrawalsByEntityId(request.getProviderId());
		Double amount = request.getOrderPricing().getAmount();
		double pastTransactionAmount = hash.get(CommonConstants.HASHMAP_AMOUNT) + amount;
		double pastTransactionCount = hash.get(CommonConstants.HASHMAP_COUNT);

		//If these values are not read from session correctly, default it to value in application_properties
		//app_key provider_max_withdrawal
		if(withdrawalLimit<=0)
		{
			withdrawalLimit = getProviderMaxWithdrawal();
			request.setProviderMaxWithdrawalLimit(withdrawalLimit);
		}
		//app_key provider_max_withdrawal_no
		if(withdrawalNumberLimit<=0)
		{
			withdrawalNumberLimit = getProviderMaxWithdrawalNo();
			request.setProviderMaxWithdrawalNo(withdrawalNumberLimit);
		}

		if (pastTransactionAmount > withdrawalLimit ) {
			response.addErrorMessage("Unable to withdraw funds due to " + withdrawalLimit + "$ daily limit");
			insertWithdrawalErrorLogging(request,new Double(hash.get(CommonConstants.HASHMAP_AMOUNT)),new Double(pastTransactionCount),CommonConstants.WITHDRAWAL_LIMIT_ERROR_MESSAGE,amount,null);
		}
		if(pastTransactionCount >= withdrawalNumberLimit) {
			response.addErrorMessage("Unable to withdraw funds due to daily limit of " + withdrawalNumberLimit + " withdrawals");
			insertWithdrawalErrorLogging(request,new Double(hash.get(CommonConstants.HASHMAP_AMOUNT)),new Double(pastTransactionCount),CommonConstants.WITHDRAWAL_COUNT_ERROR_MESSAGE,amount,null);
		}

		//To handle the provider double clicking the withdraw button, submitting multiple HTTP requests
		synchronized(this) {
			//Fix for issue:SL-20179 : Provider fired a withdrawal twice at the same time with 2 different amounts.
			double availableBalanceWallet = ledgerBO.getAvailableBalanceFromLedger(request.getProviderId(), 
					CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, true);

			if ( availableBalanceWallet < amount) {
				response.addErrorMessage("Unable to withdraw funds - Withdrawal must be no greater than account balance");
			}

			Date dt = Calendar.getInstance().getTime();
			logger.debug("Provider withdrawal txn start time "+dt);
			Long duplicateRequestCount = ledgerBO.getPastWithdrawalsWithSameAmt(request.getLedger().getAccountId(), timeIntervalSec, amount);
			Double vlbcBalance = ledgerBO.getValueLinkBalanceByEntityId(request.getProviderId());

			if ( vlbcBalance != null && vlbcBalance != -1) {
				if (vlbcBalance < amount) {
					response.addErrorMessage("We are unable to process your withdrawal request at this time which may be related to a technical connectivity delay.  Please retry your withdrawal request in 30 minutes.  If this issue persists please contact ServiceLive support at 888-549-0640 and report you were unable to withdraw your funds on the second attempt after waiting 30 minutes.");
					insertWithdrawalErrorLogging(request,new Double(hash.get(CommonConstants.HASHMAP_AMOUNT)),new Double(pastTransactionCount),CommonConstants.WITHDRAWAL_VL_BALANCE_ERROR_MESSAGE,amount,vlbcBalance);
				}
			}

			if( request.getLedger().getAccountId() == null ) {
				response.addErrorMessage("Unable to withdraw funds due invalid account selection");
			}

			if( response.isError()) {
				String error = "";
				List<String> errorMessages =  response.getErrorMessages();
				if(errorMessages != null && !errorMessages.isEmpty()) {
					for( String msg :  errorMessages) {
						error += msg + "\n";
					}
				}
				logger.debug("Provider withdrawal failed for txn start time "+dt+" Errors:"+error);
				return response; 
			}
			if(duplicateRequestCount >0) {
				logger.debug("Provider withdrawal failed for txn start time "+dt+" due to duplicateRequestCount:"+duplicateRequestCount);
				response.addErrorMessage("Unable to withdraw funds - Kindly wait for 5 seconds before the next attempt");
				return response; 
			}

			request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER);

			response = executeLedgerAndValueLink(request);
			if (!response.isError()) {
				achBO.withdrawProviderFunds(request.getAch());
			}
			logger.debug("Provider withdrawal ends for txn start time "+dt);
		}
		return response;
	}

	public WalletResponseVO withdrawProviderFundsReversal(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("withdrawProviderFundsReversal",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL);

		return executeLedgerAndValueLink(request);
	}

	public WalletResponseVO depositOperationFunds(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("depositOperationFunds",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS);
		WalletResponseVO response = new WalletResponseVO();
		//SL-20168:changing executeLedger() to executeLedgerAndValueLink()
		//to insert entry into ledger and fulfillment entries
		executeLedgerAndValueLink(request, request.getBusinessTransactionId(), response);
		if (!response.isError()) {
			achBO.depositOperationFunds(request.getAch());
		}
		return response;
	}

	public WalletResponseVO withdrawOperationFunds(WalletVO request) throws SLBusinessServiceException {

		logEnterMethod("withdrawOperationFunds",request);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS);
		WalletResponseVO response = new WalletResponseVO(); 
		response = executeLedgerAndValueLink(request);
		if (!response.isError()) {
			achBO.withdrawOperationFunds(request.getAch());
		}
		return response;
	}

	public int getProviderMaxWithdrawal() throws SLBusinessServiceException {
		try {
			return Integer.parseInt(this.applicationProperties.getPropertyValue(CommonConstants.PROVIDER_MAX_WITHDRAWAL));
		} catch (DataNotFoundException e) {
			throw new SLBusinessServiceException(e);
		}
	}

	public int getProviderMaxWithdrawalNo() throws SLBusinessServiceException { 
		try {
			return Integer.parseInt(this.applicationProperties.getPropertyValue(CommonConstants.PROVIDER_MAX_WITHDRAWAL_NO));
		} catch (DataNotFoundException e) {
			throw new SLBusinessServiceException(e);
		}
	}

	public int getProviderSameAmtTimeInterval() throws SLBusinessServiceException {
		try {
			return Integer.parseInt(this.applicationProperties.getPropertyValue(CommonConstants.PROVIDER_WITHDRAWAL_AMT_TIME_INTERVAL));
		} catch (DataNotFoundException e) {
			throw new SLBusinessServiceException(e);
		}
	}


	public void setApplicationProperties(IApplicationProperties applicationProperties) {

		this.applicationProperties = applicationProperties;
	}

	private WalletResponseVO executeLedgerAndValueLink(WalletVO request) throws SLBusinessServiceException {

		long businessTransactionId = request.getBusinessTransactionId();

		WalletResponseVO response = new WalletResponseVO();

		executeLedgerAndValueLink(request, businessTransactionId, response);

		return response;
	}

	private LedgerBusinessTransactionVO executeLedger(WalletVO request, WalletResponseVO response) throws SLBusinessServiceException {

		request.getLedger().setBusinessTransactionId(request.getBusinessTransactionId());
		
		logger.info("service.WalletBo.executeLedger:hash: ledgerBO = " + ledgerBO + " AND BusinessTransactionId = " + request.getBusinessTransactionId());
		
		LedgerBusinessTransactionVO ledgerTransaction = ledgerBO.postLedgerTransaction(request.getLedger(), request.getOrderPricing());
		getTransactionIdsFromResponse(response, ledgerTransaction);

		if (request.getAch() != null && !response.isError() && response.getLedgerEntryId()!=null) {
			request.getAch().setLedgerEntryId(response.getLedgerEntryId());
		}
		return ledgerTransaction;
	}

	private void executeLedgerAndValueLink(WalletVO request, long valueLinkBusinessTransactionId, WalletResponseVO response)
			throws SLBusinessServiceException {

		//prepare Valuelink entries, but do not send them.
		request.getValueLink().setBusinessTransactionId(valueLinkBusinessTransactionId);
		//In some cases,the funding type id is null in the request, 
		//in this case we should not update the value of the valuelink object
		if(request.getFundingTypeId()!=null)
		{
			request.getValueLink().setFundingTypeId(request.getFundingTypeId());
		}

		if (valueLinkHasNegativeAmount(request)) { //Do nothing with any of the transactions
			logger.error("A negative amount was found in the valuelink transaction for bus_trans_id: " + valueLinkBusinessTransactionId);
			throw new SLBusinessServiceException("There was an error in the payment processing for this service order.  Please contact customer support.");
		} else { 
			// execute ledger and put Ledger response in the ACH request object
			LedgerBusinessTransactionVO ledgerTransaction = executeLedger(request, response);
			
			if(null != request.getLedger().getBuyerId() && request.getLedger().getBuyerId() == 3000 && null != request.getValueLink()){
				//If there is penny variance, adjust it
				adjustPennyVarianceProviderV1Credit(ledgerTransaction,request);
			}

			// execute fullfillment, AKA value link, only if ledger has executed.
			if (ledgerTransaction.isLedgerEntries()) {
				//If funding type id is null in valuelink object, read from the ledger object.
				if(request.getValueLink()!=null && request.getValueLink().getFundingTypeId()==null)
				{
					request.getValueLink().setFundingTypeId(request.getLedger().getFundingTypeId());
				}
				valueLinkBO.sendValueLinkRequest(request.getValueLink());
			}
		}
	}
	
	List<Long> ruleIdsForSum = Arrays.asList(new Long[] { 10005L, 2139L });
	List<Double> pennyValuesToCorrect = Arrays.asList(new Double[] { 0.01, 0.02, 0.03, -0.01, -0.02, -0.03 });
	private void adjustPennyVarianceProviderV1Credit(LedgerBusinessTransactionVO ledgerTransVO, WalletVO walletVO){
		
		try{
			Double providerV1CreditAmount = walletVO.getValueLink().getProviderV1CreditAmount();
			if(null != providerV1CreditAmount && providerV1CreditAmount > 0){
				String soId = walletVO.getLedger().getServiceOrderId();
				logger.info("---adjustPennyVarianceProviderV1Credit checking pennyvariance for soId- " + soId);
				double ledgerSum = 0.00;
				ArrayList<TransactionVO> transactionVOs = ledgerTransVO.getTransactions();
				for (int i = 0; i < transactionVOs.size(); i++) {
					TransactionVO transactionVO = transactionVOs.get(i);
					if (ruleIdsForSum.contains(transactionVO.getLedgerEntryRuleId())) {
						ArrayList<TransactionEntryVO> transactionEntries = transactionVO.getTransactions();
						if(transactionEntries != null && transactionEntries.size() > 0){
							TransactionEntryVO transactionEntry = transactionEntries.get(0);
							ledgerSum += transactionEntry.getTransactionAmount();
						}
					}
				}
				
				if(ledgerSum > 0){
					// Checking for variance
					Double varianceAmt = MoneyUtil.getRoundedMoney(providerV1CreditAmount - MoneyUtil.getRoundedMoney(ledgerSum));
					logger.info("Original ProviderV1CreditAmount- " + providerV1CreditAmount + "         VarianceAmount- " + varianceAmt);
										
					// Variance amount other than these values will not be corrected
					if (pennyValuesToCorrect.contains(varianceAmt)) {
						
						// Set corrected value
						walletVO.getValueLink().setProviderV1CreditAmount(ledgerSum);
						
						logger.info("---ProviderV1CreditAmount after penny variance adjusted for soId- " + soId + " is- "+ ledgerSum );
					} else {
						logger.info("---NoPennyVarianceAdjustmentRequired in ProviderV1CreditAmount for soId- " + soId);
					}
				}
			}
		}catch(Exception e){
			logger.error("Exception in adjustPennyVarianceProviderV1Credit()", e);
		}
	}

	/**
	 * Description: An additional safegaurd against negative numbers getting into the 
	 * fullfillment_entry table.
	 * @param request
	 * @return <code>boolean</code> if a negative exists.
	 */
	private boolean valueLinkHasNegativeAmount(WalletVO request) {
		boolean neg = false;
		if ((request.getValueLink().getBuyerV1CreditAmount() != null && request.getValueLink().getBuyerV1CreditAmount() < 0) || 
				(request.getValueLink().getBuyerV1DebitAmount() != null && request.getValueLink().getBuyerV1DebitAmount() < 0) || 
				(request.getValueLink().getBuyerV2CreditAmount() != null && request.getValueLink().getBuyerV2CreditAmount() < 0) || 
				(request.getValueLink().getBuyerV2DebitAmount() != null && request.getValueLink().getBuyerV2DebitAmount() < 0) || 
				(request.getValueLink().getProviderV1CreditAmount() != null && request.getValueLink().getProviderV1CreditAmount() < 0) ||
				(request.getValueLink().getProviderV1DebitAmount() != null && request.getValueLink().getProviderV1DebitAmount() < 0) ||
				(request.getValueLink().getServiceLiveSL1CreditAmount() != null && request.getValueLink().getServiceLiveSL1CreditAmount() < 0) ||
				(request.getValueLink().getServiceLiveSL1DebitAmount() != null && request.getValueLink().getServiceLiveSL1DebitAmount() < 0) ||
				(request.getValueLink().getServiceLiveSL3CreditAmount() != null && request.getValueLink().getServiceLiveSL3CreditAmount() < 0) ||
				(request.getValueLink().getServiceLiveSL3DebitAmount() != null && request.getValueLink().getServiceLiveSL3DebitAmount() < 0)) {
			neg = true;	
		}
		return neg;
	}

	public List<ValueLinkEntryVO> getValueLinkEntries(String[] valueLinkEntryIds, Boolean groupId) throws SLBusinessServiceException {
		return valueLinkBO.getValueLinkEntries(valueLinkEntryIds, groupId);
	}

	public List<ValueLinkEntryVO> processGroupResend(
			String[] fulfillmentGroupIds, String comments, String userName) throws SLBusinessServiceException {
		return valueLinkBO.processGroupResend(fulfillmentGroupIds, comments, userName);
	}
	public Map<String, Long> reverseValueLinkTransaction(String[] valueLinkIds, String comments, String userName) throws SLBusinessServiceException {
		return valueLinkBO.reverseValueLinkTransaction(valueLinkIds, comments, userName);
	}
	public Map<String, Long> createValueLinkWithNewAmount(
			String fulfillmentEntryId, Double newAmount, String comments, String userName) throws SLBusinessServiceException {
		return valueLinkBO.createValueLinkWithNewAmount(fulfillmentEntryId, newAmount, comments, userName);
	}

	private WalletResponseVO createWalletResponseVOCheckCC(WalletVO request) throws SLBusinessServiceException {
		WalletResponseVO response = new WalletResponseVO();
		SLCreditCardVO ccVo = request.getCreditCard();
		if (ccVo == null || ccVo.getCardId() == null) {
			logger.error("Credit card is invalid");
			response.addErrorMessage("Unable to withdraw funds due invalid account selection");
		}else if(ccVo.getCardNo() == null){
			//get the credit card object from the database
			SLCreditCardVO ccvo = lookupBO.lookupCreditCard(ccVo.getCardId());
			if (ccvo == null){
				logger.error("Unable to get the credit card from database");
				response.addErrorMessage("Unable to get the credit card from database");
			}else{
				ccvo.setTransactionAmount(ccVo.getTransactionAmount());
				request.setCreditCard(ccvo);
			}
		}
		return response;
	}

	public WalletResponseVO getWalletMessageResult(String messageId) throws SLBusinessServiceException {
		throw new SLBusinessServiceException("Method is not implemented because the intend of this message is to be used when making remote calls");
	}

	public WalletResponseVO activateBuyer(WalletVO request) throws SLBusinessServiceException{
		logEnterMethod("activateBuyer",request);
		WalletResponseVO walletResponse = new WalletResponseVO();
		request.getValueLink().setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER);
		valueLinkBO.sendValueLinkRequest(request.getValueLink());
		return walletResponse;
	}

	public ReceiptVO getTransactionReceipt(Long entityId, Integer entityTypeId,
			LedgerEntryType entryType, String serviceOrderId)throws SLBusinessServiceException {
		logger.info("getTransactionReceipt method inside wallet bo");
		ReceiptVO receiptVO = ledgerBO.getTransactionReceipt(entityId, entityTypeId, entryType, serviceOrderId);
		return receiptVO;
	}

	public WalletResponseVO authCCForDollarNoCVV(WalletVO request) throws SLBusinessServiceException {
		SLCreditCardVO ccResponse = null;
		//String applicationFlagForHSWebService = getApplicationFlagForHSWebService(CommonConstants.HS_WEBSERVICE_APP_KEY);
		logEnterMethod("authCCForDollarNoCVV",request);
		WalletResponseVO response = new WalletResponseVO();
		// auth the card
		request.getCreditCard().setDollarAuth(true);
		//PCI-Vault Changes--START
		logger.info("authorizeHSSTransaction start-->");
		//creditCardDao
		//Removing the flag to switch between RTCA and HS --START
		ccResponse = hsAuthServiceCreditCardBo.authorizeHSSTransaction(request.getCreditCard(),request.getCreditCard().getUserName());
		/*if(null!=applicationFlagForHSWebService && CommonConstants.HS_WEBSERVICE_FLAG_ON.equalsIgnoreCase(applicationFlagForHSWebService)){
			 ccResponse = hsAuthServiceCreditCardBo.authorizeHSSTransaction(request.getCreditCard(),request.getCreditCard().getUserName());	
		}else{
			 ccResponse = creditCardBO.authorizeCardTransaction(request.getCreditCard());
		}*/
		//Removing the flag to switch between RTCA and HS --END

		logger.info("authorizeHSSTransaction end-->");
		//PCI-Vault Changes--END
		response.setCreditCard(ccResponse);
		return response;
	}


	private void insertWithdrawalErrorLogging(WalletVO request, Double pastTransactionAmount,
			Double pastTransactioncount,Integer errorMessageId,Double withdrawalAmt,Double vlbcBalance)
					throws SLBusinessServiceException {
		ProviderWithdrawalErrorVO providerWithdrawalErrorVO = new ProviderWithdrawalErrorVO();
		providerWithdrawalErrorVO.setProviderId(request.getProviderId());
		providerWithdrawalErrorVO.setPastWithdrawalAmt(pastTransactionAmount);
		providerWithdrawalErrorVO.setPastWithdrawalCount(pastTransactioncount);
		providerWithdrawalErrorVO.setSessionWithdrawalAmt(request.getProviderMaxWithdrawalLimit());
		providerWithdrawalErrorVO.setSessionWithdrawalCount(request.getProviderMaxWithdrawalNo());
		providerWithdrawalErrorVO.setErrorMessageId(errorMessageId);
		providerWithdrawalErrorVO.setWithdrawalAmt(withdrawalAmt);
		providerWithdrawalErrorVO.setVlbcBalance(vlbcBalance);
		ledgerBO.insertWithdrawalErrorLogging(providerWithdrawalErrorVO);
	}
	public long getAccountId(long buyerId)
			throws SLBusinessServiceException{
		Long accountId=null;

		try{
			// get creditcard account id from DB
			accountId=lookupBO.getCreditcardAccountNoForNonAutofundedBuyer(buyerId);
			logger.info("Returned Account id:"+ accountId);
			return accountId;
		}catch (Exception e) {
			throw new SLBusinessServiceException(e);
		}
	}

	/**
	 * @param appKey
	 * @return
	 * @throws SLBusinessServiceException
	 */
	public String getApplicationFlagForHSWebService(String appKey)throws SLBusinessServiceException{
		String applicationFlagForHSWebService =null;
		try {
			applicationFlagForHSWebService = creditCardBO.getApplicationFlag(appKey);
		} catch (Exception e) {
			logger.error("Exception obtained in fetching application flag"+e.getMessage());
			throw new SLBusinessServiceException(e.getMessage());
		}
		return applicationFlagForHSWebService;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public String getLedgerEntryNonce(long busTransId) throws SLBusinessServiceException {
		return ledgerBO.getLedgerEntryNonce(busTransId);
	}

	public IHSAuthServiceCreditCardBO getHsAuthServiceCreditCardBo() {
		return hsAuthServiceCreditCardBo;
	}

	public void setHsAuthServiceCreditCardBo(
			IHSAuthServiceCreditCardBO hsAuthServiceCreditCardBo) {
		this.hsAuthServiceCreditCardBo = hsAuthServiceCreditCardBo;
	}

}
