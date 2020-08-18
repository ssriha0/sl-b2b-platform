package com.servicelive.wallet.serviceinterface.requestbuilder;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.serviceinterface.requestbuilder.AchRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.AchVO;
import com.servicelive.wallet.serviceinterface.vo.LedgerVO;
import com.servicelive.wallet.serviceinterface.vo.OrderPricingVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.servicelive.common.util.MoneyUtil;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * Class WalletRequestBuilder.
 */
public class WalletRequestBuilder extends ABaseRequestBuilder implements IWalletRequestBuilder {
	private static final Logger logger = Logger.getLogger(WalletRequestBuilder.class);

	/** achRequestBuilder. */
	AchRequestBuilder achRequestBuilder = new AchRequestBuilder();

	/** creditCardRequestBuilder. */
	CreditCardRequestBuilder creditCardRequestBuilder = new CreditCardRequestBuilder();

	/** ledgerRequestBuilder. */
	LedgerRequestBuilder ledgerRequestBuilder = new LedgerRequestBuilder();

	/** valueLinkRequestBuilder. */
	ValueLinkRequestBuilder valueLinkRequestBuilder = new ValueLinkRequestBuilder();

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#adminCreditToBuyer(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	
	public WalletVO adminCreditToBuyer(String userName, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long serviceLiveSL3AccountNumber, String transactionNote,
		Double creditAmount) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, CommonConstants.NO_FUNDING_TYPE_ACTION, null, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createBuyerLedgerRequest(null, null, buyerId, userName, null, transactionNote);

		setTransactionAmount(wallet, creditAmount);

		ValueLinkVO valueLink = valueLinkRequestBuilder.adminCreditBuyer(buyerId, buyerV1AccountNumber, buyerState, serviceLiveSL3AccountNumber, creditAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#adminCreditToProvider(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public WalletVO adminCreditToProvider(String userName, Long providerId, String providerState, Long providerV1AccountNumber, Long serviceLiveSL3AccountNumber,
		String transactionNote, Double creditAmount) {
 
		WalletVO wallet = new WalletVO();

		setRequest(wallet, CommonConstants.NO_FUNDING_TYPE_ACTION, null, null, providerId, userName);

		LedgerVO ledger = ledgerRequestBuilder.createProviderLedgerRequest2(null, providerId, userName, null, transactionNote);  

		setTransactionAmount(wallet, creditAmount);

		ValueLinkVO valueLink = valueLinkRequestBuilder.adminCreditProvider(providerId, providerV1AccountNumber, providerState, serviceLiveSL3AccountNumber, creditAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#adminDebitFromBuyer(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public WalletVO adminDebitFromBuyer(String userName, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long serviceLiveSL3AccountNumber, String transactionNote,
		Double debitAmount) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, CommonConstants.NO_FUNDING_TYPE_ACTION, null, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createBuyerLedgerRequest(null, null, buyerId, userName, null, transactionNote);

		setTransactionAmount(wallet, debitAmount);

		ValueLinkVO valueLink = valueLinkRequestBuilder.adminDebitBuyer(buyerId, buyerV1AccountNumber, buyerState, serviceLiveSL3AccountNumber, debitAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);

		return wallet;
	}

	  
	public WalletVO adminEscheatmentFromBuyer(String userName, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long serviceLiveSL3AccountNumber, String transactionNote,
			Double debitAmount,Long accountId) {

			WalletVO wallet = new WalletVO();

			setRequest(wallet, CommonConstants.NO_FUNDING_TYPE_ACTION, null, buyerId, null, userName);

			LedgerVO ledger = ledgerRequestBuilder.createBuyerLedgerRequest(null, null, buyerId, userName, null, transactionNote);

			setTransactionAmount(wallet, debitAmount);

			ValueLinkVO valueLink = valueLinkRequestBuilder.adminDebitBuyer(buyerId, buyerV1AccountNumber, buyerState, serviceLiveSL3AccountNumber, debitAmount);

			wallet.setLedger(ledger);
			wallet.setValueLink(valueLink);
			AchVO ach=achRequestBuilder.createBuyerAchRequest(CommonConstants.NO_FUNDING_TYPE_ACTION, buyerId, accountId, debitAmount);
			wallet.setAch(ach);
			return wallet;       
		}
	
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#adminDebitFromProvider(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public WalletVO adminDebitFromProvider(String userName, Long providerId, String providerState, Long providerV1AccountNumber, Long serviceLiveSL3AccountNumber,
		String transactionNote, Double debitAmount) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, CommonConstants.NO_FUNDING_TYPE_ACTION, null, null, providerId, userName);

		LedgerVO ledger = ledgerRequestBuilder.createProviderLedgerRequest2(null, providerId, userName, null, transactionNote);

		setTransactionAmount(wallet, debitAmount);

		ValueLinkVO valueLink = valueLinkRequestBuilder.adminDebitProvider(providerId, providerV1AccountNumber, providerState, serviceLiveSL3AccountNumber, debitAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);

		return wallet;
	}
        
	public WalletVO adminEscheatmentFromProvider(String userName, Long providerId, String providerState, Long providerV1AccountNumber, Long serviceLiveSL3AccountNumber,
			String transactionNote, Double debitAmount,Long accountId) {

			WalletVO wallet = new WalletVO();

			setRequest(wallet, CommonConstants.NO_FUNDING_TYPE_ACTION, null, null, providerId, userName);

			LedgerVO ledger = ledgerRequestBuilder.createProviderLedgerRequest2(null, providerId, userName, null, transactionNote);

			setTransactionAmount(wallet, debitAmount);

			ValueLinkVO valueLink = valueLinkRequestBuilder.adminDebitProvider(providerId, providerV1AccountNumber, providerState, serviceLiveSL3AccountNumber, debitAmount);

			wallet.setLedger(ledger);
			wallet.setValueLink(valueLink);
			AchVO ach=achRequestBuilder.createProviderAchRequest(accountId, providerId, debitAmount, CommonConstants.NO_FUNDING_TYPE_ACTION);
			wallet.setAch(ach);
			return wallet;
			
		}
	
        
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#cancelServiceOrder(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	public WalletVO cancelServiceOrder(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long providerId,
		String providerState, Long providerV1AccountNumber, String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit,
		Double addOnTotal, Double cancellationFee, Double postingFee ,Long serviceLiveSL1AccountNumber) {// As per SLT-942 : Posting Fee added

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, providerId, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, providerId, null, transactionNote, null);

		OrderPricingVO orderPricing = new OrderPricingVO();
		orderPricing.setSpendLimitLabor(laborSpendLimit);
		orderPricing.setSpendLimitParts(partsSpendLimit);
		orderPricing.setAddOnTotal(addOnTotal);
		orderPricing.setCancellationFee(cancellationFee);
		orderPricing.setPostingFee(postingFee);

		ValueLinkVO valueLink =	valueLinkRequestBuilder.cancelServiceOrder(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, providerId,
				providerV1AccountNumber, providerState, orderPricing.getTotalSpendLimit(), cancellationFee ,postingFee, serviceLiveSL1AccountNumber);
					
		AchVO ach = null;         // @bkumar2setting posting fee for ach for funding 90      
		if(ledger.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER)){
			// Ugly fix - In cancellation workflow, once provider accepts cancellation charge, spend limit and cancellation charges becomes same.
			// The posting fee is already taken care in earlier ACH entry when cancellation request was triggered.
			logger.info("Order spend limit="+orderPricing.getTotalSpendLimit());
			logger.info("Order cancellation fee="+orderPricing.getCancellationFee());
			logger.info("Order posting fee ="+orderPricing.getPostingFee());
			Double achAmount = orderPricing.getTotalSpendLimit() - orderPricing.getCancellationFee() + orderPricing.getPostingFee();
			if(achAmount.compareTo(postingFee) == 0){
				achAmount = 0d;
			}
			
			logger.info("ACH Amount="+achAmount);
		    ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, achAmount);
		}else{
			ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit() - orderPricing.getCancellationFee());
		}

		wallet.setOrderPricing(orderPricing);
		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#cancelServiceOrderWithoutPenalty(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	public WalletVO cancelServiceOrderWithoutPenalty(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
		Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double addOnTotal, Double postingFee,Long serviceLiveSL1AccountNumber) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, null, null, transactionNote, null);

		OrderPricingVO orderPricing = new OrderPricingVO();
		orderPricing.setSpendLimitLabor(laborSpendLimit);
		orderPricing.setSpendLimitParts(partsSpendLimit);
		orderPricing.setAddOnTotal(addOnTotal);
		orderPricing.setPostingFee(postingFee);
		wallet.setOrderPricing(orderPricing);

		ValueLinkVO valueLink =
			valueLinkRequestBuilder.cancelServiceOrderWithoutPenalty(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, orderPricing
				.getTotalSpendLimit(),postingFee,serviceLiveSL1AccountNumber);
		 AchVO ach = null;         // setting posting fee for ach for funding 90      
		if(ledger.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))
		{
			//AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit() + orderPricing.getPostingFee());
		 ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit() + orderPricing.getPostingFee());
		}
		else
		{
			 ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit()); // setting posting fee for ach for funding 90  
		}
		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#closeServiceOrder(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	public WalletVO closeServiceOrder(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long providerId,
		String providerState, Long providerV1AccountNumber, Long sl1AccountNumber, String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit,
		Double partsSpendLimit, Double laborCost, Double partsCost, Double addOnTotal, Double serviceFeePercentage, Double serviceFee, Double addonServiceFee, boolean permitInd) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, providerId, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, providerId, null, transactionNote, null);

		OrderPricingVO orderPricing = new OrderPricingVO();
		orderPricing.setSpendLimitLabor(laborSpendLimit);
		orderPricing.setSpendLimitParts(partsSpendLimit);
		orderPricing.setLaborFinalPrice(laborCost);
		orderPricing.setPartsFinalPrice(partsCost);
		orderPricing.setAddOnTotal(addOnTotal);
		orderPricing.setServiceFeePercentage(serviceFeePercentage);
		orderPricing.setSoServiceFee(serviceFee);
		orderPricing.setAddonServiceFeeWithoutPermits(addonServiceFee);
		orderPricing.setTaskLevelPricing(permitInd);
		if(3333==buyerId.intValue()){
			orderPricing.setRelay(true);
		}
		
		wallet.setOrderPricing(orderPricing);
		//to chcek if the amount is zero for rules 1420,1470
		double amount = MoneyUtil.getRoundedMoney(laborSpendLimit+partsSpendLimit-laborCost-partsCost);
		logger.info("check if zero::"+amount);
		if(amount < 0.01){
			logger.info("inside 0.01");
			orderPricing.setPriceNotValid(true);
		}
		logger.info("orderPricing.setPriceNotValid::"+orderPricing.isPriceNotValid());
		ValueLinkVO valueLink =new ValueLinkVO();
		
		
		if(permitInd || 3333==buyerId.intValue()){
			valueLink =
				valueLinkRequestBuilder.closeServiceOrder(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, providerId,
						providerV1AccountNumber, providerState, sl1AccountNumber, orderPricing.getTotalSpendLimit(), orderPricing.getFinalCost(), orderPricing.getSoServiceFee());
		}else{
			valueLink =
				valueLinkRequestBuilder.closeServiceOrder(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, providerId,
					providerV1AccountNumber, providerState, sl1AccountNumber, orderPricing.getTotalSpendLimit(), orderPricing.getFinalCost(), orderPricing.getServiceFee());
		}
		AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit() - orderPricing.getFinalCost());

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#decreaseProjectSpendLimit(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public WalletVO decreaseProjectSpendLimit(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
		Long fundingTypeId, String transactionNote, Double decreaseAmount) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, null, null, transactionNote, null);

		setTransactionAmount(wallet, decreaseAmount);

		ValueLinkVO valueLink =
			valueLinkRequestBuilder.decreaseProjectSpendLimit(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, decreaseAmount);

		AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, decreaseAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		return wallet;
	}

	/**
	 * depositBuyerFunds.
	 * 
	 * @param userName 
	 * @param accountId 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param serviceOrderId 
	 * @param transactionNote 
	 * @param depositAmount 
	 * @param fundingTypeId 
	 * @param isInstantAch 
	 * 
	 * @return WalletVO
	 */
	public WalletVO depositBuyerFunds(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, String transactionNote, Double depositAmount, Long fundingTypeId, boolean isInstantAch) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, null, serviceOrderId, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createBuyerLedgerRequest(fundingTypeId, serviceOrderId, buyerId, userName, accountId, transactionNote);

		ValueLinkVO valueLink = null;

		if (isInstantAch) {
			valueLink =
				valueLinkRequestBuilder.depositBuyerFundsInstantACH(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, depositAmount);
		} else {
			valueLink = valueLinkRequestBuilder.depositBuyerFunds(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, depositAmount);
		}
		AchVO ach = null;
		if( accountId != null ) {
			ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, depositAmount);
		}

		setTransactionAmount(wallet, depositAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#depositBuyerFundsAtValueLink(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Double)
	 */
	public WalletVO depositBuyerFundsAtValueLink(String serviceOrderId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long fundingTypeId,
		Double depositAmount) {

		WalletVO wallet = new WalletVO();
		ValueLinkVO valueLink =
			valueLinkRequestBuilder.depositBuyerFunds(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, depositAmount);
		wallet.setValueLink(valueLink);
		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#depositBuyerFundsWithCash(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	public WalletVO depositBuyerFundsWithCash(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, String transactionNote, Double depositAmount) {

		long fundingTypeId = CommonConstants.FUNDING_TYPE_PRE_FUNDED;

		WalletVO wallet =
			depositBuyerFunds(userName, accountId, buyerId, buyerState, buyerV1AccountNumber, buyerV2AccountNumber, serviceOrderId, transactionNote, depositAmount, fundingTypeId,
				false);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#depositBuyerFundsWithCreditCard(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	public WalletVO depositBuyerFundsWithCreditCard(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, String transactionNote, Double depositAmount) {

		long fundingTypeId = CommonConstants.FUNDING_TYPE_PRE_FUNDED;

		WalletVO wallet =
			depositBuyerFunds(userName, accountId, buyerId, buyerState, buyerV1AccountNumber, buyerV2AccountNumber, serviceOrderId, transactionNote, depositAmount, fundingTypeId,
				false);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#depositBuyerFundsWithInstantACH(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Double)
	 */
	public WalletVO depositBuyerFundsWithInstantACH(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, String transactionNote, Double depositAmount) {

		long fundingTypeId = CommonConstants.SHC_FUNDING_TYPE;

		WalletVO wallet =
			depositBuyerFunds(userName, accountId, buyerId, buyerState, buyerV1AccountNumber, buyerV2AccountNumber, serviceOrderId, transactionNote, depositAmount, fundingTypeId,
				true);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#depositSLOperationFundsAtValueLink(java.lang.Long, java.lang.Double)
	 */
	public WalletVO depositSLOperationFundsAtValueLink(Long serviceLiveSL3AccountNumber, Double depositAmount) {

		WalletVO wallet = new WalletVO();
		ValueLinkVO valueLink = valueLinkRequestBuilder.depositServiceLiveOperationsFunds(serviceLiveSL3AccountNumber, depositAmount);
		wallet.setValueLink(valueLink);
		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#increaseProjectSpendLimit(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public WalletVO increaseProjectSpendLimit(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
		Long fundingTypeId, String transactionNote, Double increaseSpendLimitAmount, Double upsellAmount) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, null, null, transactionNote, null);

		setTransactionAmount(wallet, increaseSpendLimitAmount);
		wallet.getOrderPricing().setAddOnTotal(upsellAmount);

		ValueLinkVO valueLink =
			valueLinkRequestBuilder.increaseProjectSpendLimit(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, increaseSpendLimitAmount + upsellAmount);

		//AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, increaseAmount);
		AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, increaseSpendLimitAmount + upsellAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);
		
		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#postServiceOrder(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	public WalletVO postServiceOrder(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long sl1AccountNumber,
		String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double postingFee, Double achAmount) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, null, null, transactionNote, null);

		OrderPricingVO orderPricing = new OrderPricingVO();
		orderPricing.setSpendLimitLabor(laborSpendLimit);
		orderPricing.setSpendLimitParts(partsSpendLimit);
		orderPricing.setPostingFee(postingFee);
		orderPricing.setAchAmount(achAmount);
		wallet.setOrderPricing(orderPricing);

		ValueLinkVO valueLink =
			valueLinkRequestBuilder.postServiceOrder(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, sl1AccountNumber, orderPricing
				.getSpendLimit(), orderPricing.getPostingFee(), orderPricing.getAchAmount());

		AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, achAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);
		
		return wallet;
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.service.requestbuilder.IWalletRequestBuilder#depositOrWithdrawOperationFund(java.lang.String, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public WalletVO depositOperationFund(String userName, Long accountId, String transactionNote, Double amount,Long serviceLiveSL3AccountNumber){
		WalletVO request = new WalletVO();
		
		LedgerVO ledger = ledgerRequestBuilder.createAdminLedgerRequest(userName, transactionNote, accountId);
		AchVO ach = achRequestBuilder.createAdminAchRequest(accountId, amount);
		
		ValueLinkVO valueLink = valueLinkRequestBuilder.depositServiceLiveOperationsFunds(serviceLiveSL3AccountNumber, amount);
		setTransactionAmount(request, amount);

		request.setAch(ach);
		request.setLedger(ledger);
		request.setValueLink(valueLink);
		
		return request;
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.service.requestbuilder.IWalletRequestBuilder#depositOrWithdrawOperationFund(java.lang.String, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public WalletVO withdrawOperationFund(String userName, Long accountId, String transactionNote, Double amount, Long serviceLiveSL3AccountNumber){
		WalletVO request = new WalletVO();
		
		LedgerVO ledger = ledgerRequestBuilder.createAdminLedgerRequest(userName, transactionNote, accountId);
		AchVO ach = achRequestBuilder.createAdminAchRequest(accountId, amount);
		ValueLinkVO valueLink = valueLinkRequestBuilder.withdrawServiceLiveOperationsFunds(serviceLiveSL3AccountNumber, amount);
		setTransactionAmount(request, amount);

		request.setAch(ach);
		request.setLedger(ledger);
		request.setValueLink(valueLink);
		
		return request;
	}
	

	/**
	 * setTransactionAmount.
	 * 
	 * @param wallet 
	 * @param transactionAmount 
	 * 
	 * @return void
	 */
	void setTransactionAmount(WalletVO wallet, Double transactionAmount) {

		if (wallet.getOrderPricing() == null) {
			wallet.setOrderPricing(new OrderPricingVO());
		}
		wallet.getOrderPricing().setAmount(transactionAmount);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#voidServiceOrder(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	public WalletVO voidServiceOrder(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
		Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double addOnTotal, Double postingFee,Long serviceLiveSL1AccountNumber) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, null, null, transactionNote, null);

		OrderPricingVO orderPricing = new OrderPricingVO();
		orderPricing.setSpendLimitLabor(laborSpendLimit);
		orderPricing.setSpendLimitParts(partsSpendLimit);
		orderPricing.setAddOnTotal(addOnTotal);
		orderPricing.setPostingFee(postingFee);

		wallet.setOrderPricing(orderPricing);

		ValueLinkVO valueLink =
			valueLinkRequestBuilder.voidServiceOrder(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, orderPricing
				.getTotalSpendLimit(),postingFee,serviceLiveSL1AccountNumber);
			AchVO ach = null;            
		if(ledger.getFundingTypeId().equals(CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER))
		{
			// AchVo ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit()); // Changing for nacha issue.
			 ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit() + orderPricing.getPostingFee());
		}
		else
		{
		 ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, orderPricing.getTotalSpendLimit());
		}
		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#withdrawProviderFunds(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double, java.lang.String, java.lang.String)
	 */
	public WalletVO withdrawProviderFunds(String userName, Long accountId, Long providerId, String providerState, Long providerV1AccountNumber, Double withdrawalAmount,Integer providerMaxWithdrawalNo, Double providerMaxWithdrawalLimit) {

		WalletVO wallet = new WalletVO();

		long fundingTypeId = CommonConstants.NO_FUNDING_TYPE_ACTION;

		setRequest(wallet, fundingTypeId, null, null, providerId, userName);

		LedgerVO ledger = ledgerRequestBuilder.createProviderLedgerRequest(null, providerId, userName, accountId);

		ValueLinkVO valueLink = valueLinkRequestBuilder.withdrawProviderFunds(providerId, providerV1AccountNumber, providerState, withdrawalAmount);

		
		AchVO ach = null;		
		if( accountId != null ) {
			ach = achRequestBuilder.createProviderAchRequest(accountId, providerId, withdrawalAmount, fundingTypeId);
		}

		setTransactionAmount(wallet, withdrawalAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		if(providerMaxWithdrawalLimit !=null){
			wallet.setProviderMaxWithdrawalLimit(providerMaxWithdrawalLimit);
		}
		if(providerMaxWithdrawalNo != null){
			wallet.setProviderMaxWithdrawalNo(providerMaxWithdrawalNo);
		}
		return wallet;
	}

	public WalletVO withdrawBuyerFunds(String userName, Long fundingTypeId, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Double withdrawalAmount, String transactionNote) {

		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, null, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createBuyerLedgerRequest(fundingTypeId, "", buyerId, userName, accountId, transactionNote);

		ValueLinkVO valueLink = valueLinkRequestBuilder.withdrawBuyerFunds(buyerId, buyerV1AccountNumber, buyerState, withdrawalAmount);

		
		AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, withdrawalAmount);

		setTransactionAmount(wallet, withdrawalAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IWalletRequestBuilder#withdrawProviderFundsReversal(java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	public WalletVO withdrawProviderFundsReversal(String userName, Long accountId, Long providerId, String providerState, Long providerV1AccountNumber, Double withdrawalAmount) {

		WalletVO wallet = new WalletVO();

		// Fix SL-11967: Change the funding type
		// It was FUNDING_TYPE_NON_FUNDED caused to fail all the Provider withdrawal reversal transactions.
		// Now set to NO_FUNDING_TYPE_ACTION to be able to post the VL entries so GL, Wallet and VL balances be in sync. 
		long fundingTypeId = CommonConstants.NO_FUNDING_TYPE_ACTION;

		setRequest(wallet, fundingTypeId, null, null, providerId, userName);

		LedgerVO ledger = ledgerRequestBuilder.createProviderLedgerRequest(null, providerId, userName, accountId);

		ValueLinkVO valueLink = valueLinkRequestBuilder.withdrawProviderFundsReversal(providerId, providerV1AccountNumber, providerState, withdrawalAmount);

		setTransactionAmount(wallet, withdrawalAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);

		return wallet;
	}

	
	public WalletVO withdrawBuyerFundsReversal(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Double withdrawalAmount) {

		WalletVO wallet = new WalletVO();

		// Fix SL-11967: Change the funding type
		// It was FUNDING_TYPE_NON_FUNDED caused to fail all the Provider withdrawal reversal transactions.
		// Now set to NO_FUNDING_TYPE_ACTION to be able to post the VL entries so GL, Wallet and VL balances be in sync. 
		long fundingTypeId = CommonConstants.NO_FUNDING_TYPE_ACTION;

		setRequest(wallet, fundingTypeId, null, null, buyerId, userName);

		LedgerVO ledger = ledgerRequestBuilder.createBuyerLedgerRequest(null,null, buyerId, userName, accountId,null);

		

		ValueLinkVO valueLink = valueLinkRequestBuilder.adminCreditBuyer(buyerId, buyerV1AccountNumber, buyerState, null, withdrawalAmount);

		setTransactionAmount(wallet, withdrawalAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);

		return wallet;
	}
	
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.serviceinterface.requestbuilder.IWalletRequestBuilder#activateBuyer(java.lang.Long, long, java.lang.String)
	 */
	public WalletVO activateBuyer(Long buyerId, long fundingTypeId, String buyerState) {
		WalletVO wallet = new WalletVO();
		setRequest(wallet, fundingTypeId, null, null, null, null);

		ValueLinkVO valueLink = valueLinkRequestBuilder.activateBuyer(buyerId, fundingTypeId, buyerState);

		wallet.setValueLink(valueLink);

		return wallet;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.serviceinterface.requestbuilder.IWalletRequestBuilder#authCCForDollarNoCVV(double, java.lang.String, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public WalletVO authCCForDollarNoCVV(double transAmount, String cvv, long cardTypeId, String expDate, String cardNo, String zip, String address1, String address2) {
		
		WalletVO wallet = new WalletVO();
		SLCreditCardVO cc = new SLCreditCardVO();
		
		cc.setTransactionAmount(transAmount);
		cc.setCardVerificationNo(cvv);
		cc.setCardTypeId(cardTypeId);
		cc.setExpireDate(expDate);
		cc.setCardNo(cardNo);
		cc.setZipcode(zip);
		cc.setBillingAddress1(address1);
		cc.setBillingAddress2(address2);

		wallet.setCreditCard(cc);

		return wallet;
	}

	public WalletVO cancelServiceOrder(String userName, Long buyerId,
			String buyerState, Long providerId,
			String providerState, String serviceOrderId, Long fundingTypeId,
			String transactionNote, Double laborSpendLimit,
			Double partsSpendLimit, Double addOnTotal, Double cancellationFee, Double postingFee) {
		//pass in value link accounts as null and value will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return cancelServiceOrder(userName, buyerId, null, buyerState, null, null, providerId, providerState, null, 
				serviceOrderId, fundingTypeId, transactionNote, laborSpendLimit, partsSpendLimit, addOnTotal, cancellationFee, postingFee,null);
	}

	public WalletVO cancelServiceOrderWithoutPenalty(String userName,
			Long buyerId, String buyerState,
			String serviceOrderId, Long fundingTypeId, String transactionNote,
			Double laborSpendLimit, Double partsSpendLimit, Double addOnTotal, Double postingFee) {
		//pass in value link accounts as null and value will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return cancelServiceOrderWithoutPenalty(userName, buyerId, null, buyerState, null, null, 
				serviceOrderId, fundingTypeId, transactionNote, laborSpendLimit, partsSpendLimit, addOnTotal, postingFee,null);
	}

	public WalletVO closeServiceOrder(String userName, Long buyerId,
			String buyerState, Long providerId,
			String providerState, String serviceOrderId, Long fundingTypeId,
			String transactionNote, Double laborSpendLimit,
			Double partsSpendLimit, Double laborCost, Double partsCost,
			Double addOnTotal, Double serviceFeePercentage, Double serviceFee, Double addonServiceFee, boolean permitInd) {
		//pass in value link accounts as null and value will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return closeServiceOrder(userName, buyerId, null, buyerState, null, null, 
				providerId, providerState, null, null, serviceOrderId, fundingTypeId, transactionNote, 
				laborSpendLimit, partsSpendLimit, laborCost, partsCost, addOnTotal, serviceFeePercentage, serviceFee, addonServiceFee, permitInd);
	}

	public WalletVO decreaseProjectSpendLimit(String userName,
			Long buyerId, String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double decreaseAmount) {
		//pass in value link accounts as null and value will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return decreaseProjectSpendLimit(userName, null, buyerId, buyerState, null, null, 
				serviceOrderId, fundingTypeId, transactionNote, decreaseAmount);
	}

	public WalletVO increaseProjectSpendLimit(String userName, 
			Long buyerId, String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote,
			Double increaseSpendLimitAmount, Double upsellAmount) {
		//pass in value link accounts as null and value will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return increaseProjectSpendLimit(userName, null, buyerId, buyerState, null, null, 
				serviceOrderId, fundingTypeId, transactionNote, increaseSpendLimitAmount, upsellAmount);
	}

	public WalletVO postServiceOrder(String userName, 
			Long buyerId, String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double laborSpendLimit,
			Double partsSpendLimit, Double postingFee, Double achAmount) {
		//pass in value link accounts as null and value will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return postServiceOrder(userName, null, buyerId, buyerState, null, null, null, 
				serviceOrderId, fundingTypeId, transactionNote, laborSpendLimit, partsSpendLimit, postingFee, achAmount);
	}

	public WalletVO voidServiceOrder(String userName, Long buyerId,
			String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double laborSpendLimit,
			Double partsSpendLimit, Double addOnTotal, Double postingFee) {
		//pass in value link accounts as null and value link will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return voidServiceOrder(userName, buyerId, null, buyerState, null, null,
				serviceOrderId, fundingTypeId, transactionNote, laborSpendLimit, partsSpendLimit, addOnTotal, postingFee,null);
	}

	//for HSR
	public WalletVO increaseProjectSpendLimitForHSR(String userName,Long accountId, Long buyerId, String buyerState,Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, Long fundingTypeId, String transactionNote, Double increaseSpendLimitAmount, Double upsellAmount, Double retailPrice, Double reimbursementRetailPrice,
		Double partsSLGrossup, Double retailPriceSLGrossup,boolean hasAddon,boolean hasParts,double addOnAmount,double partsAmount) {
		
		WalletVO wallet = new WalletVO();

		setRequest(wallet, fundingTypeId, serviceOrderId, buyerId, null, userName);

		LedgerVO ledger = ledgerRequestBuilder.createLedgerRequest(userName, fundingTypeId, serviceOrderId, buyerId, null, null, transactionNote, null);

		setTransactionAmount(wallet, increaseSpendLimitAmount);
		//upsellAmount include both addOn and finalPartsPrice
		upsellAmount=addOnAmount+partsAmount;
		
		if(upsellAmount<0)
		{
			upsellAmount=0.0d;
		}
		
			if (addOnAmount < 0) {
			partsAmount = upsellAmount;
			addOnAmount = 0.0d;
		}

			// if addOn Amount is greater than 0 then only rule 1135,1136 should be fired.
		if(addOnAmount<=0)
		{
			hasAddon=false;
	
		}
		
		// if all the price for rules 1137-1140 is less  than or equal to zero then rules 1137-1140 should not be fired.

		if(retailPrice<=0 && reimbursementRetailPrice<=0 && partsSLGrossup<=0 && retailPriceSLGrossup<=0)
		{
			hasParts=false;

		}
		
		// for HSR
		if(3000 == buyerId.intValue()){
			wallet.getOrderPricing().setHSR(true);			
			wallet.getOrderPricing().setRetailPrices(retailPrice);
			wallet.getOrderPricing().setReimbursementRetailPrice(reimbursementRetailPrice);
			wallet.getOrderPricing().setPartsSLGrossup(partsSLGrossup);
			wallet.getOrderPricing().setRetailPriceSLGrossup(retailPriceSLGrossup);
			wallet.getOrderPricing().setHasAddon(hasAddon);
			wallet.getOrderPricing().setHasParts(hasParts);
			wallet.getOrderPricing().setAddOnTotal(addOnAmount);
			wallet.getOrderPricing().setPartsAmount(partsAmount);
			
		}		

		ValueLinkVO valueLink =
			valueLinkRequestBuilder.increaseProjectSpendLimit(fundingTypeId, serviceOrderId, buyerId, buyerV1AccountNumber, buyerV2AccountNumber, buyerState, increaseSpendLimitAmount + upsellAmount);

		//AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, increaseAmount);
		AchVO ach = achRequestBuilder.createBuyerAchRequest(fundingTypeId, buyerId, accountId, increaseSpendLimitAmount + upsellAmount);

		wallet.setLedger(ledger);
		wallet.setValueLink(valueLink);
		wallet.setAch(ach);
		
		return wallet;

	}

	//for HSR
	public WalletVO increaseProjectSpendLimitForHSR(String userName,
			Long buyerId, String buyerState, String serviceOrderId,
			long fundingTypeId, String transactionNote,
			double increaseSpendLimitAmount, double upsellAmount,
			double retailPrice, double reimbursementRetailPrice,
			double partsSLGrossup, double retailPriceSLGrossup,boolean hasAddon,boolean hasParts,double addOnAmount,double partsAmount) {
		//pass in value link accounts as null and value will figure out the account numbers
		//accountId for the instant ach is also figured out by ACH so no need to pass it in
		return increaseProjectSpendLimitForHSR(userName, null, buyerId, buyerState, null, null, 
				serviceOrderId, fundingTypeId, transactionNote, increaseSpendLimitAmount, upsellAmount, retailPrice, reimbursementRetailPrice,
				partsSLGrossup, retailPriceSLGrossup,hasAddon,hasParts,addOnAmount,partsAmount);
	}


}
