package com.servicelive.wallet.serviceinterface.requestbuilder;

import com.servicelive.common.CommonConstants;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkRequestBuilder.
 */
public class ValueLinkRequestBuilder extends ABaseRequestBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#adminCreditBuyer(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	public ValueLinkVO adminCreditBuyer(Long buyerId, Long buyerV1AccountNumber, String buyerState, Long serviceLiveSL3AccountNumber, Double creditAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, null, creditAmount);
		setServiceLiveOpsRequest(request, serviceLiveSL3AccountNumber, creditAmount, null);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#adminCreditProvider(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	public ValueLinkVO adminCreditProvider(Long providerId, Long providerV1AccountNumber, String providerState, Long serviceLiveSL3AccountNumber, Double creditAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setProviderRequest(request, providerId, providerState);
		setProviderV1Request(request, providerV1AccountNumber, null, creditAmount);
		setServiceLiveOpsRequest(request, serviceLiveSL3AccountNumber, creditAmount, null);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#adminDebitBuyer(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	public ValueLinkVO adminDebitBuyer(Long buyerId, Long buyerV1AccountNumber, String buyerState, Long serviceLiveSL3AccountNumber, Double debitAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, debitAmount, null);
		setServiceLiveOpsRequest(request, serviceLiveSL3AccountNumber, null, debitAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#adminDebitProvider(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double)
	 */
	public ValueLinkVO adminDebitProvider(Long providerId, Long providerV1AccountNumber, String providerState, Long serviceLiveSL3AccountNumber, Double debitAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setProviderRequest(request, providerId, providerState);
		setProviderV1Request(request, providerV1AccountNumber, debitAmount, null);
		setServiceLiveOpsRequest(request, serviceLiveSL3AccountNumber, null, debitAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#cancelServiceOrder(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double)
	 */
	public ValueLinkVO cancelServiceOrder(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String buyerState,
		Long providerId, Long providerV1AccountNumber, String providerState, Double orderAmount, Double cancellationPenalty ,
		Double postingFee,Long serviceLiveSL1AccountNumber) { // Adding Posting due to fullfilment issue for 5566

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		
		if (fundingTypeId == CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER){
			setBuyerV1Request(request, buyerV1AccountNumber,  orderAmount - cancellationPenalty, (orderAmount - cancellationPenalty));
			setServiceLiveMainRequest(request, serviceLiveSL1AccountNumber, postingFee, null); // setServiceLiveMainRequest
		}else{
			setBuyerV1Request(request, buyerV1AccountNumber,  orderAmount - cancellationPenalty, (orderAmount - cancellationPenalty));
		}
		
		setBuyerV2Request(request, buyerV2AccountNumber, orderAmount, null);
		setProviderRequest(request, providerId, providerState);
		setProviderV1Request(request, providerV1AccountNumber, null, cancellationPenalty);
		
		//Long serviceLiveSL1AccountNumber
		// setBuyerV1Request(postingfee);
		return request;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#cancelServiceOrderWithoutPenalty(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO cancelServiceOrderWithoutPenalty(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String buyerState, Double orderAmount ,Double postingFee,Long serviceLiveSL1AccountNumber) {  // Adding Posting due to fullfilment issue for 5566

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		
		if (fundingTypeId == CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER){
			setBuyerV1Request(request, buyerV1AccountNumber, orderAmount+postingFee, orderAmount);
			setServiceLiveMainRequest(request, serviceLiveSL1AccountNumber, postingFee,null); // setServiceLiveMainRequest
		}else{
			setBuyerV1Request(request, buyerV1AccountNumber, orderAmount, orderAmount);
		}
		
		setBuyerV2Request(request, buyerV2AccountNumber, orderAmount, null);
		
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#closeServiceOrder(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	public ValueLinkVO closeServiceOrder(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String buyerState,
		Long providerId, Long providerV1AccountNumber, String providerState, Long serviceLiveSL1AccountNumber, Double orderAmount, Double finalPrice, Double serviceFeeAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV2Request(request, buyerV2AccountNumber, orderAmount, null);
		setBuyerV1Request(request, buyerV1AccountNumber, orderAmount - finalPrice, orderAmount - finalPrice);
		setProviderRequest(request, providerId, providerState);
		setProviderV1Request(request, providerV1AccountNumber, null, finalPrice - serviceFeeAmount);
		setServiceLiveMainRequest(request, serviceLiveSL1AccountNumber, null, serviceFeeAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#createBuyer(int, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO createBuyer(Long fundingTypeId, String serviceOrderId, Long buyerId, String buyerState, Double depositAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, null, null, depositAmount);
		setBuyerV2Request(request, null, null, 0.0d);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#createProvider(int, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO createProvider(Long fundingTypeId, String serviceOrderId, Long providerId, String providerState, Double depositAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setProviderRequest(request, providerId, providerState);
		setProviderV1Request(request, null, null, depositAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#decreaseProjectFunds(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO decreaseProjectSpendLimit(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String buyerState,
		Double decreaseAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, decreaseAmount, decreaseAmount);
		setBuyerV2Request(request, buyerV2AccountNumber, decreaseAmount, null);
		return request;
	}

	/**
	 * defaultFundingType.
	 * 
	 * @param request 
	 * @param fundingTypeId 
	 * 
	 * @return void
	 */
	void defaultFundingType(ValueLinkVO request, long fundingTypeId) {

		if (request.getFundingTypeId() == null) {
			request.setFundingTypeId(fundingTypeId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#depositBuyerFunds(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO depositBuyerFunds(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String buyerState,
		Double depositAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, null, depositAmount);
		setBuyerV2Request(request, buyerV2AccountNumber, null, null);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#depositBuyerFundsInstantACH(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO depositBuyerFundsInstantACH(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber,
		String buyerState, Double depositAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, null, depositAmount);
		setBuyerV2Request(request, buyerV1AccountNumber, null, depositAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#depositServiceLiveOperationsFunds(java.lang.Long, java.lang.Double)
	 */
	public ValueLinkVO depositServiceLiveOperationsFunds(Long serviceLiveSL3AccountNumber, Double depositAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setServiceLiveOpsRequest(request, serviceLiveSL3AccountNumber, null, depositAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#increaseProjectFunds(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO increaseProjectSpendLimit(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String buyerState,
		Double increaseAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, increaseAmount, increaseAmount);
		setBuyerV2Request(request, buyerV2AccountNumber, null, increaseAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#routeServiceOrder(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	public ValueLinkVO postServiceOrder(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String buyerState,
		Long serviceLiveSL1AccountNumber, Double orderAmount, Double postingFeeAmount, Double achFundingAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, (orderAmount + postingFeeAmount), achFundingAmount);
		setServiceLiveMainRequest(request, serviceLiveSL1AccountNumber, null, postingFeeAmount);
		setBuyerV2Request(request, buyerV2AccountNumber, null, orderAmount);
		defaultFundingType(request, CommonConstants.FUNDING_TYPE_NON_FUNDED);
		return request;
	}

	/**
	 * setBuyerRequest.
	 * 
	 * @param request 
	 * @param buyerId 
	 * @param buyerState 
	 * 
	 * @return void
	 */
	void setBuyerRequest(ValueLinkVO request, Long buyerId, String buyerState) {

		request.setBuyerId(buyerId);
		request.setBuyerState(buyerState);
	}

	/**
	 * setBuyerV1Request.
	 * 
	 * @param request 
	 * @param buyerV1AccountNumber 
	 * @param buyerV1DebitAmount 
	 * @param buyerV1CreditAmount 
	 * 
	 * @return void
	 */
	void setBuyerV1Request(ValueLinkVO request, Long buyerV1AccountNumber, Double buyerV1DebitAmount, Double buyerV1CreditAmount) {

		request.setBuyerV1AccountNumber(buyerV1AccountNumber);
		request.setBuyerV1DebitAmount(buyerV1DebitAmount);
		request.setBuyerV1CreditAmount(buyerV1CreditAmount);
	}

	/**
	 * setBuyerV2Request.
	 * 
	 * @param request 
	 * @param buyerV2AccountNumber 
	 * @param buyerV2DebitAmount 
	 * @param buyerV2CreditAmount 
	 * 
	 * @return void
	 */
	void setBuyerV2Request(ValueLinkVO request, Long buyerV2AccountNumber, Double buyerV2DebitAmount, Double buyerV2CreditAmount) {

		request.setBuyerV2AccountNumber(buyerV2AccountNumber);
		request.setBuyerV2DebitAmount(buyerV2DebitAmount);
		request.setBuyerV2CreditAmount(buyerV2CreditAmount);
	}

	/**
	 * setProviderRequest.
	 * 
	 * @param request 
	 * @param providerId 
	 * @param providerState 
	 * 
	 * @return void
	 */
	void setProviderRequest(ValueLinkVO request, Long providerId, String providerState) {

		request.setProviderId(providerId);
		request.setProviderState(providerState);
	}

	/**
	 * setProviderV1Request.
	 * 
	 * @param request 
	 * @param providerV1AccountNumber 
	 * @param providerV1DebitAmount 
	 * @param providerV1CreditAmount 
	 * 
	 * @return void
	 */
	void setProviderV1Request(ValueLinkVO request, Long providerV1AccountNumber, Double providerV1DebitAmount, Double providerV1CreditAmount) {

		request.setProviderV1AccountNumber(providerV1AccountNumber);
		request.setProviderV1DebitAmount(providerV1DebitAmount);
		request.setProviderV1CreditAmount(providerV1CreditAmount);
	}

	/**
	 * setServiceLiveMainRequest.
	 * 
	 * @param request 
	 * @param serviceLiveSL1AccountNumber 
	 * @param serviceLiveSL1DebitAmount 
	 * @param serviceLiveSL1CreditAmount 
	 * 
	 * @return void
	 */
	void setServiceLiveMainRequest(ValueLinkVO request, Long serviceLiveSL1AccountNumber, Double serviceLiveSL1DebitAmount, Double serviceLiveSL1CreditAmount) {

		request.setServiceLiveSL1AccountNumber(serviceLiveSL1AccountNumber);
		request.setServiceLiveSL1DebitAmount(serviceLiveSL1DebitAmount);
		request.setServiceLiveSL1CreditAmount(serviceLiveSL1CreditAmount);
	}

	/**
	 * setServiceLiveOpsRequest.
	 * 
	 * @param request 
	 * @param serviceLiveSL3AccountNumber 
	 * @param serviceLiveSL3DebitAmount 
	 * @param serviceLiveSL3CreditAmount 
	 * 
	 * @return void
	 */
	void setServiceLiveOpsRequest(ValueLinkVO request, Long serviceLiveSL3AccountNumber, Double serviceLiveSL3DebitAmount, Double serviceLiveSL3CreditAmount) {

		request.setServiceLiveSL3AccountNumber(serviceLiveSL3AccountNumber);
		request.setServiceLiveSL3DebitAmount(serviceLiveSL3DebitAmount);
		request.setServiceLiveSL3CreditAmount(serviceLiveSL3CreditAmount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#voidServiceOrder(int, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO voidServiceOrder(Long fundingTypeId, String serviceOrderId, Long buyerId, Long buyerV1AccountNumber, Long buyerV2AccountNumber, String buyerState,
		Double orderAmount,Double postingFee,Long serviceLiveSL1AccountNumber) {  // Adding Posting due to fullfilment issue for 5566

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, fundingTypeId, serviceOrderId);
		setBuyerRequest(request, buyerId, buyerState);
		
		if (fundingTypeId == CommonConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER){
			setBuyerV1Request(request, buyerV1AccountNumber, orderAmount+postingFee, orderAmount);
			setServiceLiveMainRequest(request, serviceLiveSL1AccountNumber, postingFee, null); // setServiceLiveMainRequest
		}else{
			setBuyerV1Request(request, buyerV1AccountNumber, orderAmount, orderAmount);
		}
		setBuyerV2Request(request, buyerV2AccountNumber, orderAmount, null);
		
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#withdrawBuyerFunds(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO withdrawBuyerFunds(Long buyerId, Long buyerV1AccountNumber, String buyerState, Double withdrawalAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setBuyerRequest(request, buyerId, buyerState);
		setBuyerV1Request(request, buyerV1AccountNumber, withdrawalAmount, null);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#withdrawProviderFunds(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO withdrawProviderFunds(Long providerId, Long providerV1AccountNumber, String providerState, Double withdrawalAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setProviderRequest(request, providerId, providerState);
		setProviderV1Request(request, providerV1AccountNumber, withdrawalAmount, null);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#withdrawProviderFundsReversal(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Double)
	 */
	public ValueLinkVO withdrawProviderFundsReversal(Long providerId, Long providerV1AccountNumber, String providerState, Double withdrawalAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setProviderRequest(request, providerId, providerState);
		setProviderV1Request(request, providerV1AccountNumber, null, withdrawalAmount);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#withdrawServiceLiveOperationsFunds(java.lang.Long, java.lang.Double)
	 */
	public ValueLinkVO withdrawServiceLiveOperationsFunds(Long serviceLiveSL3AccountNumber, Double withdrawalAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setServiceLiveOpsRequest(request, serviceLiveSL3AccountNumber, withdrawalAmount, null);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestBuilder#withdrawServiceLiveRevenueFunds(java.lang.Long, java.lang.Double)
	 */
	public ValueLinkVO withdrawServiceLiveRevenueFunds(Long serviceLiveSL1AccountNumber, Double withdrawalAmount) {

		ValueLinkVO request = new ValueLinkVO();
		setRequest(request, null, null);
		setServiceLiveMainRequest(request, serviceLiveSL1AccountNumber, withdrawalAmount, null);
		return request;
	}

	/**
	 * @param buyerId
	 * @param fundingTypeId
	 * @param providerState
	 * @return <code>ValueLinkVO</code>
	 */
	public ValueLinkVO activateBuyer(Long buyerId, long fundingTypeId, String buyerState) {
		ValueLinkVO request = new ValueLinkVO();
		request.setFundingTypeId(fundingTypeId);
		request.setBuyerId(buyerId);
		request.setBuyerState(buyerState);
		request.setBuyerV1CreditAmount(0.0);
		request.setBuyerV2CreditAmount(0.0);
		request.setBuyerV1DebitAmount(0.0);
		request.setBuyerV2DebitAmount(0.0);
		return request;
	}

	// public void adminCreditToBuyer(
	// long buyerId, String buyerState, double creditAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	// ValueLinkAccountsVO vlAccountsMain = this.getVLAccountsForSLOperations();
	//	
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.adminCreditBuyer(
	// buyerId, vlAccountsBuyer.getV1AccountNo(), buyerState,
	// vlAccountsMain.getV1AccountNo(), creditAmount);
	//	
	// sendValueLinkEntries(entries);
	// }
	// public void adminCreditToProvider(long providerId,
	// String providerState, double creditAmount) {
	//
	// ValueLinkAccountsVO vlAccountsProvider = this.getVLAccountsForProvider(providerId);
	// ValueLinkAccountsVO vlAccountsMain = this.getVLAccountsForSLOperations();
	//		
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.adminCreditProvider(
	// providerId, vlAccountsProvider.getV1AccountNo(), providerState,
	// vlAccountsMain.getV1AccountNo(), creditAmount);
	//		
	// sendValueLinkEntries(entries);
	//			
	// }
	// public void adminDebitFromBuyer(long buyerId, String buyerState, double debitAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	// ValueLinkAccountsVO vlAccountsMain = this.getVLAccountsForSLOperations();
	//		
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.adminDebitBuyer(
	// buyerId, vlAccountsBuyer.getV1AccountNo(), buyerState,
	// vlAccountsMain.getV1AccountNo(), debitAmount);
	//		
	// sendValueLinkEntries(entries);
	//			
	// }
	// public void adminDebitFromProvider(long providerId, String providerState, double debitAmount)
	// throws BusinessServiceException {
	//	
	// ValueLinkAccountsVO vlAccountsProvider = this.getVLAccountsForProvider(providerId);
	// ValueLinkAccountsVO vlAccountsMain = this.getVLAccountsForSLOperations();
	//		
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.adminDebitProvider(
	// providerId, vlAccountsProvider.getV1AccountNo(),
	// providerState, vlAccountsMain.getV1AccountNo(), debitAmount);
	//		
	// sendValueLinkEntries(entries);
	// }

	// public void cancelServiceOrder(
	// int fundingTypeId, String serviceOrderId,
	// long buyerId, String buyerState,
	// long providerId, String providerState,
	// double orderAmount, double cancellationPenalty)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	// ValueLinkAccountsVO vlAccountsProvider = this.getVLAccountsForProvider(providerId);
	//
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.cancelServiceOrder(
	// fundingTypeId, serviceOrderId,
	// buyerId, vlAccountsBuyer.getV1AccountNo(), vlAccountsBuyer.getV2AccountNo(), buyerState,
	// providerId, vlAccountsProvider.getV1AccountNo(), providerState,
	// orderAmount, cancellationPenalty);
	//
	// sendValueLinkEntries(entries);
	// }
	// public void cancelServiceOrderWithoutPenalty(int fundingTypeId,
	// String serviceOrderId, long buyerId, String buyerState, double orderAmount)
	// throws BusinessServiceException {
	//		
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	//		
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.cancelServiceOrderWithoutPenalty(
	// fundingTypeId, serviceOrderId,
	// buyerId, vlAccountsBuyer.getV1AccountNo(), vlAccountsBuyer.getV2AccountNo(), buyerState,
	// orderAmount);
	//		
	// sendValueLinkEntries(entries);
	// }
	// public void closeServiceOrder(int fundingTypeId, String serviceOrderId,
	// long buyerId, String buyerState, long providerId, String providerState,
	// double orderAmount, double finalPrice, double serviceFeeAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	// ValueLinkAccountsVO vlAccountsProvider = this.getVLAccountsForProvider(providerId);
	// ValueLinkAccountsVO vlAccountsRevenue = this.getVLAccountsForSLRevenue();
	//		
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.closeServiceOrder(
	// fundingTypeId, serviceOrderId,
	// buyerId, vlAccountsBuyer.getV1AccountNo(), vlAccountsBuyer.getV2AccountNo(), buyerState,
	// providerId, vlAccountsProvider.getV1AccountNo(), providerState,
	// vlAccountsRevenue.getV1AccountNo(), orderAmount, finalPrice, serviceFeeAmount);
	//
	// sendValueLinkEntries(entries);
	// }
	// public void createBuyer(int fundingTypeId, long buyerId, String buyerState,
	// double depositAmount) {
	//
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.createBuyer(fundingTypeId, null, buyerId, buyerState, depositAmount);
	//
	// sendValueLinkEntries(entries);
	// }

	// public void createProvider(int fundingTypeId, long providerId, String providerState, double depositAmount)
	// throws BusinessServiceException {
	//
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.createProvider(
	// fundingTypeId, null, providerId, providerState, depositAmount);
	//		
	// sendValueLinkEntries(entries);
	// }
	// public void decreaseProjectSpendLimit(int fundingTypeId, String serviceOrderId,
	// long buyerId, String buyerState, double decreaseAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	//		
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.decreaseSpendingLimit(
	// fundingTypeId, serviceOrderId,
	// buyerId, vlAccountsBuyer.getV1AccountNo(), vlAccountsBuyer.getV2AccountNo(),
	// buyerState, decreaseAmount);
	//		
	// sendValueLinkEntries(entries);
	// }
	// public void depositBuyerFunds(int fundingTypeId, long buyerId, String buyerState, String serviceOrderId, double depositAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO accounts = getVLAccountsForBuyer(buyerId);
	//		
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.depositBuyerFunds(
	// fundingTypeId, serviceOrderId, buyerId, accounts.getV1AccountNo(), accounts.getV2AccountNo(), buyerState, depositAmount);
	//		
	// sendValueLinkEntries(entries);
	// }
	//
	// public void depositBuyerFundsInstantACH(int fundingTypeId, long buyerId, String buyerState, String serviceOrderId, double depositAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO accounts = getVLAccountsForBuyer(buyerId);
	//	
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.depositBuyerFundsInstantACH(
	// fundingTypeId, null, buyerId, accounts.getV1AccountNo(), accounts.getV2AccountNo(), buyerState, depositAmount);
	//		
	// sendValueLinkEntries(entries);
	// }
	// public void depositServiceLiveOperationsFunds(double depositAmount)
	// throws BusinessServiceException {
	//		
	// ValueLinkAccountsVO accounts = getVLAccountsForSLOperations();
	//		
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.depositServiceLiveOperationsFunds(
	// accounts.getV1AccountNo(), depositAmount);
	//		
	// sendValueLinkEntries(entries);
	// }
	// public void increaseProjectSpendLimit(int fundingTypeId, String serviceOrderId,
	// long buyerId, String buyerState, double increaseAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO accounts = getVLAccountsForBuyer(buyerId);
	//		
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.increaseSpendingLimit(
	// fundingTypeId, serviceOrderId, buyerId,
	// accounts.getV1AccountNo(), accounts.getV2AccountNo(),
	// buyerState, increaseAmount);
	//
	// sendValueLinkEntries(entries);
	// }

	// public void postServiceOrder(
	// int fundingTypeId, String serviceOrderId,
	// long buyerId, String buyerState,
	// double orderAmount, double postingFeeAmount, double achFundingAmount)
	// throws BusinessServiceException {
	//		
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	// ValueLinkAccountsVO vlAccountsRevenue = this.getVLAccountsForSLRevenue();
	//		
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.routeServiceOrder(
	// fundingTypeId, serviceOrderId,
	// buyerId, vlAccountsBuyer.getV1AccountNo(), vlAccountsBuyer.getV2AccountNo(),
	// buyerState, vlAccountsRevenue.getV1AccountNo(),
	// orderAmount, postingFeeAmount, achFundingAmount);
	//
	// sendValueLinkEntries(entries);
	// }

	// public void voidServiceOrder(int fundingTypeId, String serviceOrderId,
	// long buyerId, long buyerV1AccountNumber, long buyerV2AccountNumber,
	// String buyerState, double orderAmount)
	// throws BusinessServiceException {
	// // TODO do i need this?
	//		
	// }
	// public void withdrawBuyerFunds(
	// long buyerId, String buyerState, double withdrawalAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsBuyer = this.getVLAccountsForBuyer(buyerId);
	//	
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.withdrawBuyerFunds(
	// buyerId, vlAccountsBuyer.getV1AccountNo(),
	// buyerState, withdrawalAmount);
	//
	// sendValueLinkEntries(entries);
	// }
	// public void withdrawProviderFunds(
	// long providerId, String providerState, double withdrawalAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsProvider = this.getVLAccountsForProvider(providerId);
	//
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.withdrawProviderFunds(
	// providerId, vlAccountsProvider.getV1AccountNo(), providerState, withdrawalAmount);
	//
	// sendValueLinkEntries(entries);
	// }
	// public void withdrawProviderFundsReversal(
	// long providerId, String providerState, double withdrawalAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsProvider = this.getVLAccountsForProvider(providerId);
	//
	// List<ValueLinkEntryVO> entries = this.valueLinkRules.withdrawProviderFundsReversal(
	// providerId, vlAccountsProvider.getV1AccountNo(), providerState, withdrawalAmount);
	//
	// sendValueLinkEntries(entries);
	//
	// }

	// public void withdrawServiceLiveOperationsFunds(double withdrawalAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsSLOps = this.getVLAccountsForSLOperations();
	//
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.withdrawServiceLiveOperationsFunds(vlAccountsSLOps.getV1AccountNo(), withdrawalAmount);
	//
	// sendValueLinkEntries(entries);
	// }

	// public void withdrawServiceLiveRevenueFunds(double withdrawalAmount)
	// throws BusinessServiceException {
	//
	// ValueLinkAccountsVO vlAccountsSLRevenue = this.getVLAccountsForSLRevenue();
	//
	// List<ValueLinkEntryVO> entries =
	// this.valueLinkRules.withdrawServiceLiveRevenueFunds(
	// vlAccountsSLRevenue.getV1AccountNo(), withdrawalAmount);
	//
	// sendValueLinkEntries(entries);
	//
	// }
}
