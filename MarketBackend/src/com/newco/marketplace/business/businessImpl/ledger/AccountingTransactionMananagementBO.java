package com.newco.marketplace.business.businessImpl.ledger;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.dto.vo.ach.AchProcessQueueEntryVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.ledger.TransactionEntryVO;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.ledger.TransactionDao;
import com.newco.marketplace.utils.MoneyUtil;
import com.sears.os.business.ABaseBO;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

/**
 * Description of the Class
 * 
 * @author dmill03
 * @created August 15, 2007
 */
public class AccountingTransactionMananagementBO extends ABaseBO implements ILedgerFacilityBO {

	private TransactionDao transactionDao = null;
	private static final Logger logger = Logger.getLogger(AccountingTransactionMananagementBO.class);

	private IWalletBO walletBO;
	private WalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
	private ILookupBO lookupBO;
	private PromoBO promoBO;

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}

	// Admin object should not change. So, it should be read once.
	// This does not need to be Synchronized becasue it is only reading. 
	private AdminLookupVO ADMIN_LOOKUP_VO = null;
	private AdminLookupVO getAdminLookupVO() throws BusinessServiceException {
		if (ADMIN_LOOKUP_VO == null) {
			ADMIN_LOOKUP_VO = lookupBO.lookupAdmin();
		}
		return ADMIN_LOOKUP_VO;
	}

	/**
	 * Description of the Method
	 * 
	 * @param service
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean cancelServiceOrderLedgerAction(MarketPlaceTransactionVO service) throws BusinessServiceException  {
		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		Long providerId = service.getVendorId().longValue();
		ProviderLookupVO providerVO = lookupBO.lookupProvider(providerId);

		ServiceOrder so = service.getServiceOrder();
		double addOnTotal = so.getUpsellAmt();

		WalletVO request = walletRequestBuilder.cancelServiceOrder(service.getUserName(),
				buyerId, service.getAccountId(), buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				providerId, providerVO.getProviderState(), providerVO.getProviderV1AccountNumber(),
				so.getSoId(), buyerVO.getBuyerFundingTypeId(), service.getTransactionNote(),
				so.getSpendLimitLabor(), so.getSpendLimitParts(), addOnTotal, so.getCancellationFee(),so.getPostingFee(),getAdminLookupVO().getSl1AccountNumber()); // bkumar2 Asper SLT-942


		WalletResponseVO walletResponseVO = walletBO.cancelServiceOrder(request);
		return !walletResponseVO.isError();
	}

	public boolean cancelServiceOrderWOPenaltyLedgerAction(MarketPlaceTransactionVO service) throws BusinessServiceException {
		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		ServiceOrder so = service.getServiceOrder();
		double addOnTotal = so.getUpsellAmt();

		WalletVO request = walletRequestBuilder.cancelServiceOrderWithoutPenalty(service.getUserName(),
				buyerId, service.getAccountId(), buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				so.getSoId(), buyerVO.getBuyerFundingTypeId(), service.getTransactionNote(),
				so.getSpendLimitLabor(), so.getSpendLimitParts(), addOnTotal, so.getPostingFee(),getAdminLookupVO().getSl1AccountNumber());

		WalletResponseVO walletResponseVO = walletBO.cancelServiceOrderWithoutPenalty(request);
		return !walletResponseVO.isError();
	}

	/**
	 * Description of the Method
	 * 
	 * @param service
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean closeServiceOrderLedgerAction(MarketPlaceTransactionVO service)  throws BusinessServiceException {
		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		Long providerId = service.getVendorId().longValue();
		ProviderLookupVO providerVO = lookupBO.lookupProvider(providerId);

		ServiceOrder so = service.getServiceOrder();

		double laborCost = so.getLaborFinalPrice();
		double partsCost = so.getPartsFinalPrice();
		double addOnTotal = so.getUpsellAmt();
		double serviceFeePercentage = so.getServiceFeePercentage();
		double serviceFee = (laborCost + partsCost + addOnTotal)*serviceFeePercentage;

		WalletVO request = walletRequestBuilder.closeServiceOrder(service.getUserName(),
				buyerId, service.getAccountId(), buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				providerId, providerVO.getProviderState(), providerVO.getProviderV1AccountNumber(), getAdminLookupVO().getSl1AccountNumber(),
				so.getSoId(), buyerVO.getBuyerFundingTypeId(), service.getTransactionNote(),
				so.getSpendLimitLabor(), so.getSpendLimitParts(), laborCost, partsCost, addOnTotal, serviceFeePercentage,serviceFee,addOnTotal,false);

		WalletResponseVO walletResponseVO = walletBO.closeServiceOrder(request);
		return !walletResponseVO.isError();
	}

	/**
	 * Description of the Method
	 * 
	 * @param service
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean routeServiceOrderLedgerAction(MarketPlaceTransactionVO service,FundingVO fundVo)throws BusinessServiceException {
		int fundingTypeId = service.getServiceOrder().getFundingTypeId().intValue();
		if (fundingTypeId == CommonConstants.CONSUMER_FUNDING_TYPE){
			return true; // Consumer funded orders are paid for at the time of acceptance, not at posting here	
		}

		logger.info("AccountingTransactionMananagementBO.routeServiceOrderLedgerAction()");
		logFunding(fundVo);
		logMarketPlaceTxn(service);

		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);
		ServiceOrder so = service.getServiceOrder();
		WalletResponseVO walletResponseVO;

		if( !fundVo.isEnoughFunds() ) {
			return false; 		// not enough funds.  should have already failed by now.
		}
		if (fundVo.getSoProjectBalance() > 0.0) { // This is repost.
			if( fundVo.isDecreaseFunds() ) { // already posted, decreasing spend limit
				walletResponseVO = decreaseSpendLimit(service,buyerId,Math.abs(fundVo.getAmtToRefund()));
				return !walletResponseVO.isError();
			}
			if( fundVo.getIncreaseAmt() > 0.0d ) { // already posted, increasing spend limit for non Sears buyers
				walletResponseVO = increaseSpendLimit(service,buyerId,fundVo.getIncreaseAmt(), 0);
				return !walletResponseVO.isError();
			}
			if( fundVo.getAmtToFund() > 0.0d ) { // already posted, so we need to increase spend limit for Sears buyers
				walletResponseVO = increaseSpendLimit(service,buyerId,fundVo.getAmtToFund(), 0);
				return !walletResponseVO.isError();	
			}
			return true; // This is a repost with no value change which does not need to use wallet.
		}
		else if(fundVo.getSoProjectBalance()== 0.0 && service.getServiceOrder().getWfStateId()==OrderConstants.ROUTED_STATUS)
		{
			return true;
		}

		//Post using Wallet
		WalletVO request = walletRequestBuilder.postServiceOrder(service.getUserName(), service.getAccountId(),
				buyerId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				getAdminLookupVO().getSl1AccountNumber(), so.getSoId(), buyerVO.getBuyerFundingTypeId(), 
				null, so.getSpendLimitLabor(), so.getSpendLimitParts(), so.getPostingFee(), fundVo.getAmtToFund() );

		walletResponseVO = walletBO.postServiceOrder(request);
		return !walletResponseVO.isError();
	}



	/**
	 * Description: Method called at acceptance of a consumer order which does the posting rules
	 * associated with an order which are redeem from buyer's v1, load to V2 and load to SL1's V1.
	 * 
	 * @param service
	 * @param fundVo
	 * @return <code>WalletResponseVO</code>
	 * @throws BusinessServiceException
	 */
	public boolean postConsumerOrder(MarketPlaceTransactionVO service,FundingVO fundVo)throws BusinessServiceException {
		int fundingTypeId = service.getServiceOrder().getFundingTypeId().intValue();
		boolean posted = false;
		logger.info("AccountingTransactionMananagementBO.postConsumerOrder() with so : " + service.getServiceOrder().getSoId());
		logFunding(fundVo);
		logMarketPlaceTxn(service);

		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);
		ServiceOrder so = service.getServiceOrder();
		WalletResponseVO walletResponseVO;

		if (fundingTypeId == CommonConstants.CONSUMER_FUNDING_TYPE){
			WalletVO request = walletRequestBuilder.postServiceOrder(service.getUserName(), service.getAccountId(),
					buyerId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
					getAdminLookupVO().getSl1AccountNumber(), so.getSoId(), buyerVO.getBuyerFundingTypeId(),  
					null, so.getSpendLimitLabor(), so.getSpendLimitParts(), so.getPostingFee(), fundVo.getAmtToFund() );

			walletResponseVO = walletBO.postServiceOrder(request);
			posted =  !walletResponseVO.isError(); 
		}
		return posted;
	}


	private WalletResponseVO decreaseSpendLimit(MarketPlaceTransactionVO service, long buyerId, double amount ) 
			throws BusinessServiceException {

		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);
		WalletVO request = walletRequestBuilder.decreaseProjectSpendLimit(
				service.getUserName(), service.getAccountId(),
				buyerId,
				buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				service.getServiceOrder().getSoId(), buyerVO.getBuyerFundingTypeId(), null, amount);

		return walletBO.decreaseProjectSpendLimit(request);
	}

	private WalletResponseVO increaseSpendLimit(MarketPlaceTransactionVO service, long buyerId, double spendLimitIncreaseAmount, double upsellAmount ) 
			throws BusinessServiceException {

		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		WalletVO request = walletRequestBuilder.increaseProjectSpendLimit(
				service.getUserName(), service.getAccountId(),
				buyerId,
				buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				service.getServiceOrder().getSoId(), buyerVO.getBuyerFundingTypeId(), null, spendLimitIncreaseAmount, upsellAmount);

		return walletBO.increaseProjectSpendLimit(request);
	}

	private WalletResponseVO increaseSpendLimitCompletion(MarketPlaceTransactionVO service, long buyerId, double spendLimitIncreaseAmount, double upsellAmount) throws BusinessServiceException {

		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		WalletVO request = walletRequestBuilder.increaseProjectSpendLimit(service.getUserName(), service.getAccountId(), buyerId, 
				buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(), 
				service.getServiceOrder().getSoId(), buyerVO.getBuyerFundingTypeId(), null, spendLimitIncreaseAmount, upsellAmount);

		return walletBO.increaseProjectSpendCompletion(request); 
	}

	public void decreaseSpendLimit(MarketPlaceTransactionVO service, double amount,double achAmount) 
			throws BusinessServiceException{

		Long buyerId = service.getBuyerID().longValue();
		handleError(decreaseSpendLimit(service,buyerId,amount));
	}

	public void increaseSpendLimit(MarketPlaceTransactionVO service,double spendLimitIncreaseAmount, String soId, double achAmount)
			throws BusinessServiceException {

		increaseSpendLimit(service, spendLimitIncreaseAmount, 0d, soId, achAmount);
	}

	public void increaseSpendLimit(MarketPlaceTransactionVO service,double amount, double upsellAmount, String soId, double achAmount)
			throws BusinessServiceException {

		Long buyerId = service.getBuyerID().longValue();
		if (amount > 0.0){
			handleError(increaseSpendLimit(service, buyerId, amount, upsellAmount));
		}
	}

	public void increaseSpendLimitCompletion(MarketPlaceTransactionVO service,double spendLimitIncreaseAmount, double upsellAmount, String soId, double achAmount)
			throws BusinessServiceException {

		Long buyerId = service.getBuyerID().longValue();
		handleError(increaseSpendLimitCompletion(service, buyerId, spendLimitIncreaseAmount, upsellAmount));
	}

	public double getAvailableBalance(AjaxCacheVO avo) throws BusinessServiceException {
		double availableBalance = 0.0;
		logger.debug("get available balance" + avo.getRoleType());
		if(avo.getRoleType()!= null){
			if (OrderConstants.SIMPLE_BUYER_ROLE.equals(avo.getRoleType().toUpperCase()) ||
					OrderConstants.BUYER_ROLE.equals(avo.getRoleType().toUpperCase()) ||
					"SIMPLEBUYER".equals(avo.getRoleType().toUpperCase()) ){ //SL-6781 fix

				availableBalance = walletBO.getBuyerAvailableBalance(avo.getCompanyId());

			}else if (OrderConstants.NEWCO_ADMIN.toUpperCase().equals(avo.getRoleType().toUpperCase())){// SL-6862

				availableBalance = walletBO.getSLOperationBalance();

			}else if (OrderConstants.PROVIDER.toUpperCase().equals(avo.getRoleType().toUpperCase())){

				availableBalance = walletBO.getProviderBalance(avo.getCompanyId());

			}
		}
		//Jira #19614 - Rounding off the availableBalance
		//return availableBalance;
		return MoneyUtil.getRoundedMoney(availableBalance);
	}


	//SL-21117: Revenue Pull code change starts

	public List <String> getPermittedUsers() throws BusinessServiceException{

		return walletBO.getPermittedUsers();		
	}

	public double getAvailableBalanceForRevenuePull() throws BusinessServiceException {
		double availableBalance = 0.0;

		availableBalance = walletBO.getAvailableBalanceForRevenuePull();

		return MoneyUtil.getRoundedMoney(availableBalance);
	}


	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws BusinessServiceException {
		boolean dbRevenuePullDateCheck=false;

		dbRevenuePullDateCheck = walletBO.getAvailableDateCheckForRevenuePull(calendarOnDate);

		return dbRevenuePullDateCheck;
	}


	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user) throws BusinessServiceException {

		walletBO.insertEntryForRevenuePull(amount,revenuePullDate,note,user);
	}

	public List <String> getPermittedUsersEmail() throws BusinessServiceException{

		return walletBO.getPermittedUsersEmail();		
	}

	//Code change ends


	public double getSLOperationBalance() throws BusinessServiceException {
		return walletBO.getSLOperationBalance();
	}	

	public double getCurrentBalance(AjaxCacheVO avo)  throws BusinessServiceException  {
		double currentBalance = walletBO.getBuyerCurrentBalance(avo.getCompanyId());

		return MoneyUtil.getRoundedMoney(currentBalance);
	}


	public void populateEntityId(TransactionEntryVO transactionEntry,
			MarketPlaceTransactionVO service) {
		if (transactionEntry.getLedgerEntityTypeId() != null) {
			int ledgerEntityTypeId = transactionEntry.getLedgerEntityTypeId().intValue();

			if (ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_BUYER)) {
				transactionEntry.setLedgerEntityId(service.getBuyerID());
			} 
			else if (ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_PROVIDER)) {
				transactionEntry.setLedgerEntityId(service.getVendorId());
			} 
			else if (ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW)) {
				transactionEntry.setLedgerEntityId(LedgerConstants.ENTITY_ID_ESCROW);
			} 
			else if (ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN)) {
				transactionEntry.setLedgerEntityId(LedgerConstants.ENTITY_ID_SERVICELIVE);
			} 
			else if (ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_DEPOSITS_WITHDRAWLS)){
				transactionEntry.setLedgerEntityId(LedgerConstants.ENTITY_ID_DEPOSIT_WITHDRAWL);
			}
			else if (ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_VIRTUAL_CASH)){
				transactionEntry.setLedgerEntityId(LedgerConstants.ENTITY_ID_VIRTUAL_CASH);
			}			
			else if (ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION)){
				transactionEntry.setLedgerEntityId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
			}
			else if(ledgerEntityTypeId == (LedgerConstants.LEDGER_ENTITY_TYPE_MANAGE_SERVICES)){
				transactionEntry.setLedgerEntityId(LedgerConstants.ENTITY_ID_MANAGED_SERVICES);
			}
			// populate originating buyer_id unconditionally	
			transactionEntry.setOriginatingBuyerId(service.getBuyerID());

		}
	}



	public void providerWithdrawFundsReversal(AchProcessQueueEntryVO queueEntry, MarketPlaceTransactionVO service)
			throws BusinessServiceException {

		Long providerId = service.getVendorId().longValue();
		ProviderLookupVO providerVO = lookupBO.lookupProvider(providerId);

		double withdrawalAmount = queueEntry.getTransactionAmount();

		WalletVO request = walletRequestBuilder.withdrawProviderFundsReversal(service.getUserName(), service.getAccountId(),
				providerId, providerVO.getProviderState(), providerVO.getProviderV1AccountNumber(),
				withdrawalAmount);

		handleError(walletBO.withdrawProviderFundsReversal(request));

	}


	public boolean voidServiceOrderLedgerAction(MarketPlaceTransactionVO service) throws BusinessServiceException{
		Long buyerId = service.getBuyerID().longValue();
		logger.info("voiding service order " + service.getServiceOrder().getSoId() + " and buyer id " + buyerId);
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		ServiceOrder so = service.getServiceOrder();

		double addOnTotal = so.getUpsellAmt();

		WalletVO request = walletRequestBuilder.voidServiceOrder(service.getUserName(), 
				buyerId, service.getAccountId(), buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				so.getSoId(), buyerVO.getBuyerFundingTypeId(), service.getTransactionNote(),
				so.getSpendLimitLabor(), so.getSpendLimitParts(), addOnTotal, so.getPostingFee(),getAdminLookupVO().getSl1AccountNumber());//  Asper SLT-942

		WalletResponseVO walletResponseVO = walletBO.voidServiceOrder(request);
		return !walletResponseVO.isError();
	}


	public void adminCreditsSLBtoBuyer(MarketPlaceTransactionVO service, Double amount, Integer reasonCode)
			throws BusinessServiceException {
		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);
		AdminLookupVO adminVO = lookupBO.lookupAdmin();

		WalletVO request = walletRequestBuilder.adminCreditToBuyer(service.getUserName(),
				buyerId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), adminVO.getSl3AccountNumber(),
				service.getTransactionNote(), amount);

		handleError(walletBO.adminCreditToBuyer(request));
	}


	public void adminCreditsSLBtoProvider(MarketPlaceTransactionVO service, Double amount, Integer reasonCode)
			throws BusinessServiceException {
		Long providerId = service.getVendorId().longValue();
		ProviderLookupVO providerVO = lookupBO.lookupProvider(providerId);

		if( providerVO == null ) {
			throw new BusinessServiceException("Provider " + providerId + " not found.");
		}

		WalletVO request = walletRequestBuilder.adminCreditToProvider(service.getUserName(),
				providerId, providerVO.getProviderState(), providerVO.getProviderV1AccountNumber(), getAdminLookupVO().getSl3AccountNumber(),
				service.getTransactionNote(), amount);

		handleError(walletBO.adminCreditToProvider(request));
	}


	public void adminDebitsSLBfromBuyer(MarketPlaceTransactionVO service, Double amount, Integer reasonCode)
			throws BusinessServiceException {

		if( service == null ) {
			throw new BusinessServiceException("Service is null");
		}
		if( service.getBuyerID() == null ) {
			throw new BusinessServiceException("Buyer ID is null");
		}

		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		if( buyerVO == null ) {
			throw new BusinessServiceException("Buyer " + buyerId + " not found");
		}

		WalletVO request = walletRequestBuilder.adminDebitFromBuyer(service.getUserName(),
				buyerId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), getAdminLookupVO().getSl3AccountNumber(),
				service.getTransactionNote(), amount);

		handleError(walletBO.adminDebitFromBuyer(request));
	}


	public void adminEscheatmentSLBfromBuyer(MarketPlaceTransactionVO service, Double amount, Integer reasonCode)
			throws BusinessServiceException {

		if( service == null ) {
			throw new BusinessServiceException("Service is null");
		}
		if( service.getBuyerID() == null ) {
			throw new BusinessServiceException("Buyer ID is null");
		}

		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		if( buyerVO == null ) {
			throw new BusinessServiceException("Buyer " + buyerId + " not found");
		}

		Account account = transactionDao.getEscheatAccountDetails(OrderConstants.NEWCO_ADMIN_COMPANY_ROLE);

		WalletVO request = walletRequestBuilder.adminEscheatmentFromBuyer(service.getUserName(),
				buyerId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), getAdminLookupVO().getSl3AccountNumber(),
				service.getTransactionNote(), amount,account.getAccount_id());

		handleError(walletBO.adminEscheatmentFromBuyer(request));


	}





	public void adminDebitsSLBfromProvider(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException {
		Long providerId = service.getVendorId().longValue();
		ProviderLookupVO providerVO = lookupBO.lookupProvider(providerId);

		WalletVO request = walletRequestBuilder.adminDebitFromProvider(service.getUserName(),
				providerId, providerVO.getProviderState(), providerVO.getProviderV1AccountNumber(), getAdminLookupVO().getSl3AccountNumber(),
				service.getTransactionNote(), amount);

		handleError(walletBO.adminDebitFromProvider(request));
	}

	public void adminEscheatmentSLBfromProvider(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException {
		Long providerId = service.getVendorId().longValue();
		ProviderLookupVO providerVO = lookupBO.lookupProvider(providerId);
		Account account = transactionDao.getEscheatAccountDetails(OrderConstants.NEWCO_ADMIN_COMPANY_ROLE);

		WalletVO request = walletRequestBuilder.adminEscheatmentFromProvider(service.getUserName(),
				providerId, providerVO.getProviderState(), providerVO.getProviderV1AccountNumber(), getAdminLookupVO().getSl3AccountNumber(),
				service.getTransactionNote(), amount,account.getAccount_id());

		handleError(walletBO.adminEscheatmentFromProvider(request));

	}
	public Integer issueRefunds(MarketPlaceTransactionVO service, double amount, long accountId, String user)  throws BusinessServiceException {
		String transactionNote = service.getTransactionNote();
		WalletResponseVO response;
		logUserAndAccount(amount, accountId, user);
		logMarketPlaceTxn(service);
		Long buyerId = service.getBuyerID().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		WalletVO request = walletRequestBuilder.withdrawBuyerFunds(user, buyerVO.getBuyerFundingTypeId(), accountId, 
				buyerId, buyerVO.getBuyerState(),buyerVO.getBuyerV1AccountNumber(), amount, transactionNote);
		if (service.isCCInd()) {
			logger.info("Refunding credit card : " + accountId);

			SLCreditCardVO ccVo = lookupBO.lookupCreditCard( accountId );
			ccVo.setTransactionAmount(amount);
			ccVo.setUserName(user);
			request.setCreditCard(ccVo);
			request.setHsWebFlag(true);
			response = walletBO.withdrawBuyerCreditCardFunds(request);
		} else {
			logger.info("Refunding cash");
			response = walletBO.withdrawBuyerCashFunds(request);
		}
		if( response.isError() ) {
			logger.error("Wallet returned errors");
			String msgString = "";
			List<String> errormessages =  response.getErrorMessages();
			for( String msg :  errormessages) {
				msgString += msg + "\n"; // Most of the time, there is only one message.
			}
			logger.error("--> Wallet error : " + msgString);
			throw new BusinessServiceException(msgString);
		}
		return response.getTransactionId().intValue();
	}


	public Integer withdrawfundsSLAOperations(MarketPlaceTransactionVO service, double amount, long accountId,String user)
			throws BusinessServiceException {
		String transactionNote = service.getTransactionNote();
		AdminLookupVO adminVO = lookupBO.lookupAdmin();
		WalletVO request = walletRequestBuilder.withdrawOperationFund(user, accountId, transactionNote, amount, adminVO.getSl3AccountNumber());
		WalletResponseVO response = walletBO.withdrawOperationFunds(request);
		return response.getTransactionId().intValue();
	}

	public Integer depositfundsSLAOperations(MarketPlaceTransactionVO service, double amount, long accountId,String user)
			throws BusinessServiceException {
		String transactionNote = service.getTransactionNote();
		AdminLookupVO adminVO = lookupBO.lookupAdmin();
		WalletVO request = walletRequestBuilder.depositOperationFund(user, accountId, transactionNote, amount,adminVO.getSl3AccountNumber());
		WalletResponseVO walletResponseVO = walletBO.depositOperationFunds(request);	

		return walletResponseVO.getLedgerEntryId().intValue();
	}

	/*	public double getPostSOAmount(String soId) throws BusinessServiceException{
			return walletBO.getPostSOAmount(soId);
	}
	 */

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	private WalletResponseVO handleError(WalletResponseVO walletResponseVO) throws BusinessServiceException {
		List<String> errorMsgs = walletResponseVO.getErrorMessages();
		StringBuffer msgBuf = new StringBuffer();
		for (String msg: errorMsgs) {
			if (!StringUtils.isEmpty(msg)) {
				msgBuf.append(msg);
				msgBuf.append('\n');
			}
		}
		if (msgBuf.length() > 0) {
			throw new BusinessServiceException(msgBuf.toString());
		}
		return walletResponseVO;
	}
	/**
	 * Sets the wallet client bo.
	 * 
	 * @param walletClientBO the new wallet client bo
	 */
	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}
	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	private void logFunding(FundingVO fundVo) {
		logger.info("FundingVO");
		logger.info("   double amtToFund-> " + fundVo.getAmtToFund() );
		logger.info("   double amtToRefund-> " + fundVo.getAmtToRefund() );
		logger.info("   double availableBalance-> " + fundVo.getAvailableBalance() );
		logger.info("   double soProjectBalance-> " + fundVo.getSoProjectBalance() );
		logger.info("   boolean enoughFunds-> " + fundVo.isEnoughFunds() );
		logger.info("   boolean decreaseFunds-> " + fundVo.isDecreaseFunds() );
		logger.info("   String soId-> " + fundVo.getSoId() );
		logger.info("   double increaseAmt-> " + fundVo.getIncreaseAmt() );
	}

	private void logMarketPlaceTxn(MarketPlaceTransactionVO mptVO) {
		logger.info("MarketPlaceTransactionVO");
		logger.info("   Integer ledgerEntryRuleId-> " + mptVO.getLedgerEntryRuleId() );
		logger.info("   Integer userTypeID-> " + mptVO.getUserTypeID() );
		logger.info("   Integer buyerID-> " + mptVO.getBuyerID() );
		logger.info("   Integer vendorId-> " + mptVO.getVendorId() );
		logger.info("   Integer businessTransId-> " + mptVO.getBusinessTransId() );
		logger.info("   Integer feeTypeID-> " + mptVO.getFeeTypeID() );
		logger.info("   String adminUser-> " + mptVO.getAdminUser() );
		if (mptVO.getServiceOrder() != null) {
			ServiceOrder so = mptVO.getServiceOrder();
			logger.info("   ServiceOrder: String soId-> " + so.getSoId() );
			logger.info("       Double SpendLimitLabor-> " + so.getSpendLimitLabor());
			logger.info("       Double SpendLimitParts-> " + so.getSpendLimitParts());
			logger.info("       Double PostingFee-> " + so.getPostingFee());
		} else {
			logger.info("   ServiceOrder soid-> null" );
		}
		logger.info("   boolean CCInd-> " + mptVO.isCCInd() );
		logger.info("   Integer fundingTypeId-> " + mptVO.getFundingTypeId() );
		logger.info("   String transactionNote-> " + mptVO.getTransactionNote() );
		logger.info("   Long accountTypeId-> " + mptVO.getAccountTypeId() );
		logger.info("   double preTxnAvailableBalance-> " + mptVO.getPreTxnAvailableBalance() );
		logger.info("   String userName-> " + mptVO.getUserName() );
		logger.info("   String autoACHInd-> " + mptVO.getAutoACHInd() );
		logger.info("   Long accountId-> " + mptVO.getAccountId() );
		logger.info("   Double achAmount-> " + mptVO.getAchAmount() );
	}

	private void logUserAndAccount(double amount, long accountId, String user)  {
		logger.info("logUserAndAccount");
		logger.info("   double amount-> " + amount );
		logger.info("   long accountId-> " + accountId );
		logger.info("   String user-> " + user );
	}
}