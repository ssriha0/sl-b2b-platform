package com.servicelive.wallet.serviceinterface.requestbuilder;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IWalletRequestBuilder.
 */
/**
 * @author nsanzer
 *
 */
public interface IWalletRequestBuilder {

	/**
	 * adminCreditToBuyer.
	 * 
	 * @param userName 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param serviceLiveSL3AccountNumber 
	 * @param transactionNote 
	 * @param creditAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO adminCreditToBuyer(String userName, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long serviceLiveSL3AccountNumber, String transactionNote,
		Double creditAmount);

	/**
	 * adminCreditToProvider.
	 * 
	 * @param userName 
	 * @param providerId 
	 * @param providerState 
	 * @param providerV1AccountNumber 
	 * @param serviceLiveSL3AccountNumber 
	 * @param transactionNote 
	 * @param creditAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO adminCreditToProvider(String userName, Long providerId, String providerState, Long providerV1AccountNumber, Long serviceLiveSL3AccountNumber,
		String transactionNote, Double creditAmount);

	/**
	 * adminDebitFromBuyer.
	 * 
	 * @param userName 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param serviceLiveSL3AccountNumber 
	 * @param transactionNote 
	 * @param debitAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO adminDebitFromBuyer(String userName, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long serviceLiveSL3AccountNumber, String transactionNote,
		Double debitAmount);

	/**
	 * adminEscheatmentFromBuyer.
	 * 
	 * @param userName 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param serviceLiveSL6AccountNumber 
	 * @param transactionNote 
	 * @param debitAmount 
	 * @param accountId
	 * 
	 * @return WalletVO
	 */
	
	
	
	public WalletVO adminEscheatmentFromBuyer(String userName, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long serviceLiveSL6AccountNumber, String transactionNote,
			Double debitAmount,Long accountId);
	/**
	 * adminDebitFromProvider.
	 * 
	 * @param userName 
	 * @param providerId 
	 * @param providerState 
	 * @param providerV1AccountNumber 
	 * @param serviceLiveSL3AccountNumber 
	 * @param transactionNote 
	 * @param debitAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO adminDebitFromProvider(String userName, Long providerId, String providerState, Long providerV1AccountNumber, Long serviceLiveSL3AccountNumber,
		String transactionNote, Double debitAmount);

	/**
	 * adminEscheatmentFromProvider.
	 * 
	 * @param userName 
	 * @param providerId 
	 * @param providerState 
	 * @param providerV1AccountNumber 
	 * @param serviceLiveSL6AccountNumber 
	 * @param transactionNote 
	 * @param debitAmount 
	 * @param accountId
	 * 
	 * @return WalletVO
	 */
	
	public WalletVO adminEscheatmentFromProvider(String userName, Long providerId, String providerState, Long providerV1AccountNumber, Long serviceLiveSL6AccountNumber,
			String transactionNote, Double debitAmount,Long accountId);
	/**
	 * cancelServiceOrder.
	 * 
	 * @param userName 
	 * @param buyerId
	 * @param accountId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param providerId 
	 * @param providerState 
	 * @param providerV1AccountNumber 
	 * @param serviceOrderId 
	 * @param fundingTypeId 
	 * @param transactionNote 
	 * @param laborSpendLimit 
	 * @param partsSpendLimit 
	 * @param addOnTotal 
	 * @param cancellationFee 
	 * 
	 * 
	 * @return WalletVO
	 */
	public WalletVO cancelServiceOrder(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long providerId,
			String providerState, Long providerV1AccountNumber, String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit,
			Double addOnTotal, Double cancellationFee, Double postingFee,Long serviceLiveSL1AccountNumber);

	/**
	 * @param userName
	 * @param buyerId
	 * @param buyerState
	 * @param providerId
	 * @param providerState
	 * @param serviceOrderId
	 * @param fundingTypeId
	 * @param transactionNote
	 * @param laborSpendLimit
	 * @param partsSpendLimit
	 * @param addOnTotal
	 * @param cancellationFee
	 * @param postingFee
	 * @return
	 */
	public WalletVO cancelServiceOrder(String userName, Long buyerId, String buyerState, Long providerId,
			String providerState, String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit,
			Double addOnTotal, Double cancellationFee, Double postingFee);

	/**
	 * cancelServiceOrderWithoutPenalty.
	 * 
	 * @param userName 
	 * @param buyerId
	 * @param accountId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param serviceOrderId 
	 * @param fundingTypeId 
	 * @param transactionNote 
	 * @param laborSpendLimit 
	 * @param partsSpendLimit 
	 * @param addOnTotal 
	 * 
	 * @return WalletVO
	 */
	public WalletVO cancelServiceOrderWithoutPenalty(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double addOnTotal, Double postingFee,Long serviceLiveSL1AccountNumber);

	/**
	 * @param userName
	 * @param buyerId
	 * @param buyerState
	 * @param serviceOrderId
	 * @param fundingTypeId
	 * @param transactionNote
	 * @param laborSpendLimit
	 * @param partsSpendLimit
	 * @param addOnTotal
	 * @return
	 */
	public WalletVO cancelServiceOrderWithoutPenalty(String userName, Long buyerId, String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double addOnTotal, Double postingFee);

	/**
	 * closeServiceOrder.
	 * 
	 * @param userName 
	 * @param buyerId
	 * @param accountId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param providerId 
	 * @param providerState 
	 * @param providerV1AccountNumber 
	 * @param sl1AccountNumber 
	 * @param serviceOrderId 
	 * @param fundingTypeId 
	 * @param transactionNote 
	 * @param laborSpendLimit 
	 * @param partsSpendLimit 
	 * @param laborCost 
	 * @param partsCost 
	 * @param addOnTotal 
	 * @param serviceFeePercentage 
	 * 
	 * @return WalletVO
	 */
	public WalletVO closeServiceOrder(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long providerId,
			String providerState, Long providerV1AccountNumber, Long sl1AccountNumber, String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit,
			Double partsSpendLimit, Double laborCost, Double partsCost, Double addOnTotal, Double serviceFeePercentage, Double serviceFee, Double addonServiceFee, boolean permitInd);

	/**
	 * @param userName
	 * @param buyerId
	 * @param buyerState
	 * @param providerId
	 * @param providerState
	 * @param serviceOrderId
	 * @param fundingTypeId
	 * @param transactionNote
	 * @param laborSpendLimit
	 * @param partsSpendLimit
	 * @param laborCost
	 * @param partsCost
	 * @param addOnTotal
	 * @param serviceFeePercentage
	 * @return
	 */
	public WalletVO closeServiceOrder(String userName, Long buyerId, String buyerState, Long providerId,
			String providerState, String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit,
			Double partsSpendLimit, Double laborCost, Double partsCost, Double addOnTotal, Double serviceFeePercentage, Double serviceFee, Double addonServiceFee, boolean permitInd);

	/**
	 * decreaseProjectSpendLimit.
	 * 
	 * @param userName
	 * @param accountId 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param serviceOrderId 
	 * @param fundingTypeId 
	 * @param transactionNote 
	 * @param decreaseAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO decreaseProjectSpendLimit(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double decreaseAmount);

	/**
	 * @param userName
	 * @param buyerId
	 * @param buyerState
	 * @param serviceOrderId
	 * @param fundingTypeId
	 * @param transactionNote
	 * @param decreaseAmount
	 * @return
	 */
	public WalletVO decreaseProjectSpendLimit(String userName, Long buyerId, String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double decreaseAmount);

	/**
	 * depositBuyerFundsAtValueLink.
	 * 
	 * @param serviceOrderId 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param fundingTypeId 
	 * @param depositAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO depositBuyerFundsAtValueLink(String serviceOrderId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long fundingTypeId,
		Double depositAmount);

	/**
	 * depositBuyerFundsWithCash.
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
	 * 
	 * @return WalletVO
	 */
	public WalletVO depositBuyerFundsWithCash(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, String transactionNote, Double depositAmount);

	/**
	 * depositBuyerFundsWithCreditCard.
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
	 * 
	 * @return WalletVO
	 */
	public WalletVO depositBuyerFundsWithCreditCard(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, String transactionNote, Double depositAmount);

	/**
	 * depositBuyerFundsWithInstantACH.
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
	 * 
	 * @return WalletVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public WalletVO depositBuyerFundsWithInstantACH(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String serviceOrderId, String transactionNote, Double depositAmount) throws SLBusinessServiceException;

	/**
	 * depositSLOperationFundsAtValueLink.
	 * 
	 * @param buyerV1AccountNumber 
	 * @param depositAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO depositSLOperationFundsAtValueLink(Long buyerV1AccountNumber, Double depositAmount);

	/**
	 * increaseProjectSpendLimit.
	 * 
	 * @param userName
	 * @param accountId 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param serviceOrderId 
	 * @param fundingTypeId
	 * @param transactionNote 
	 * @param increaseSpendLimitAmount
	 * @param upsellAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO increaseProjectSpendLimit(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double increaseSpendLimitAmount, Double upsellAmount);

	/**
	 * @param userName
	 * @param buyerId
	 * @param buyerState
	 * @param serviceOrderId
	 * @param fundingTypeId
	 * @param transactionNote
	 * @param increaseSpendLimitAmount
	 * @param upsellAmount
	 * @return
	 */
	public WalletVO increaseProjectSpendLimit(String userName, Long buyerId, String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double increaseSpendLimitAmount, Double upsellAmount);

	//for HSR
	public WalletVO increaseProjectSpendLimitForHSR(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double increaseSpendLimitAmount, Double upsellAmount, Double retailPrice,
			Double reimbursementRetailPrice, Double partsSLGrossup, Double retailPriceSLGrossup,boolean hasAddon,boolean hasParts,double addOnAmount,double partsAmount);

	//for HSR
	public WalletVO increaseProjectSpendLimitForHSR(String userName,
			Long buyerId, String buyerState, String serviceOrderId, long fundingTypeId,
			String transactionNote, double increaseSpendLimitAmount, double upsellAmount, double retailPrice,
			double reimbursementRetailPrice, double partsSLGrossup, double retailPriceSLGrossup,boolean hasAddon,boolean hasParts,double addOnAmount,double partsAmount);
	/**
	 * postServiceOrder.
	 * 
	 * @param userName
	 * @param accountId 
	 * @param buyerId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param sl1AccountNumber 
	 * @param serviceOrderId 
	 * @param fundingTypeId
	 * @param transactionNote 
	 * @param laborSpendLimit 
	 * @param partsSpendLimit 
	 * @param postingFee 
	 * @param achAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO postServiceOrder(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, Long sl1AccountNumber,
			String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double postingFee, Double achAmount);

	/**
	 * @param userName
	 * @param buyerId
	 * @param buyerState
	 * @param serviceOrderId
	 * @param fundingTypeId
	 * @param transactionNote
	 * @param laborSpendLimit
	 * @param partsSpendLimit
	 * @param postingFee
	 * @param achAmount
	 * @return
	 */
	public WalletVO postServiceOrder(String userName, Long buyerId, String buyerState,
			String serviceOrderId, Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double postingFee, Double achAmount);

	/**
	 * voidServiceOrder.
	 * 
	 * @param userName 
	 * @param buyerId
	 * @param accountId 
	 * @param buyerState 
	 * @param buyerV1AccountNumber 
	 * @param buyerV2AccountNumber 
	 * @param serviceOrderId 
	 * @param fundingTypeId 
	 * @param transactionNote 
	 * @param laborSpendLimit 
	 * @param partsSpendLimit 
	 * @param addOnTotal 
	 * 
	 * @return WalletVO
	 */
	public WalletVO voidServiceOrder(String userName, Long buyerId, Long accountId, String buyerState, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double addOnTotal, Double postingFee,Long serviceLiveSL1AccountNumber);

	/**
	 * @param userName
	 * @param buyerId
	 * @param buyerState
	 * @param serviceOrderId
	 * @param fundingTypeId
	 * @param transactionNote
	 * @param laborSpendLimit
	 * @param partsSpendLimit
	 * @param addOnTotal
	 * @return
	 */
	public WalletVO voidServiceOrder(String userName, Long buyerId, String buyerState, String serviceOrderId,
			Long fundingTypeId, String transactionNote, Double laborSpendLimit, Double partsSpendLimit, Double addOnTotal, Double postingFee);

	/**
	 * withdrawProviderFunds.
	 * 
	 * @param userName 
	 * @param accountId 
	 * @param providerId 
	 * @param providerState 
	 * @param providerV1AccountNumber 
	 * @param withdrawalAmount
	 * @param providerMaxWithdrawalNo
	 * @param providerMaxWithdrawalLimit 
	 * 
	 * @return WalletVO
	 */
	public WalletVO withdrawProviderFunds(String userName, Long accountId, Long providerId, String providerState, Long providerV1AccountNumber, Double withdrawalAmount,Integer providerMaxWithdrawalNo, Double providerMaxWithdrawalLimit);

	/**
	 * withdrawProviderFundsReversal.
	 * 
	 * @param userName 
	 * @param accountId 
	 * @param providerId 
	 * @param providerState 
	 * @param providerV1AccountNumber 
	 * @param withdrawalAmount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO withdrawProviderFundsReversal(String userName, Long accountId, Long providerId, String providerState, Long providerV1AccountNumber, Double withdrawalAmount);

	
	
	public WalletVO withdrawBuyerFundsReversal(String userName, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Double withdrawalAmount);
	
	
	/**
	 * depositOperationFund.
	 * 
	 * @param userName 
	 * @param accountId 
	 * @param transactionNote 
	 * @param amount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO depositOperationFund(String userName, Long accountId, String transactionNote, Double amount,Long serviceLiveSL3AccountNumber);
	
	/**
	 * withdrawOperationFund.
	 * 
	 * @param userName 
	 * @param accountId 
	 * @param transactionNote 
	 * @param amount 
	 * 
	 * @return WalletVO
	 */
	public WalletVO withdrawOperationFund(String userName, Long accountId, String transactionNote, Double amount, Long serviceLiveSL3AccountNumber);

	/**
	 * @param userName
	 * @param fundingTypeId
	 * @param accountId
	 * @param buyerId
	 * @param buyerState
	 * @param buyerV1AccountNumber
	 * @param withdrawalAmount
	 * @param transactionNote
	 * @return
	 */
	public WalletVO withdrawBuyerFunds(String userName, Long fundingTypeId, Long accountId, Long buyerId, String buyerState, Long buyerV1AccountNumber, Double withdrawalAmount, String transactionNote);
	
	/**
	 * Description: Used when new buyers register on the site to create their ValueLink V1 and V2 accounts.
	 * These accounts are necessary to do business on the system and used to be created at the time of their
	 * first financial transaction. 
	 * 
	 * @param buyerId
	 * @param fundingTypeId
	 * @param providerState
	 * @return <code>WalletVO</code>
	 */
	public WalletVO activateBuyer(Long buyerId, long fundingTypeId, String buyerState);
	
	
	/**
	 * Description: Used to validate Credit Cards.  
	 * Cards must pass an authorization for zero dollars before being persisted.
	 * The CVV value could be an empty string.
	 *  
	 * @param transAmount
	 * @param cvv
	 * @param cardTypeId
	 * @param expDate
	 * @param cardNo
	 * @param zip
	 * @param address1
	 * @param address2
	 * @return <code>WalletVO</code>
	 */
	public WalletVO authCCForDollarNoCVV(double transAmount, String cvv, long cardTypeId,
			String expDate, String cardNo, String zip, String address1, String address2);

}

