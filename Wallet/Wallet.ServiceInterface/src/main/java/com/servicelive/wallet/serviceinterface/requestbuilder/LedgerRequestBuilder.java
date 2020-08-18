package com.servicelive.wallet.serviceinterface.requestbuilder;

import com.servicelive.common.CommonConstants;
import com.servicelive.wallet.serviceinterface.vo.LedgerVO;

// TODO: Auto-generated Javadoc
/**
 * Class LedgerRequestBuilder.
 */
public class LedgerRequestBuilder extends ABaseRequestBuilder {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.ILedgerRequestBuilder#createBuyerLedgerRequest(java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String)
	 */
	public LedgerVO createBuyerLedgerRequest(Long fundingTypeId, String serviceOrderId, Long buyerId, String userName, Long accountId, String transactionNote) {

		LedgerVO request = new LedgerVO();
		setRequest(request, fundingTypeId, serviceOrderId, buyerId, null, userName);
		request.setAccountId(accountId);
		request.setTransactionNote(transactionNote);
		return request;
	}
 
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.ILedgerRequestBuilder#createLedgerRequest(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Integer)
	 */
	public LedgerVO createLedgerRequest(String userName, Long fundingTypeId, String serviceOrderId, Long buyerId, Long providerId, Long accountId, String transactionNote,
		Integer transferReasonCode) {

		LedgerVO request = new LedgerVO();
		setRequest(request, fundingTypeId, serviceOrderId, buyerId, providerId, userName);
		request.setAccountId(accountId);
		request.setTransactionNote(transactionNote);
		request.setTransferReasonCode(transferReasonCode);
		return request;
	}
 
	/* (non-Javadoc) 
	 * @see com.servicelive.wallet.client.ILedgerRequestBuilder#createProviderLedgerRequest(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public LedgerVO createProviderLedgerRequest2(String serviceOrderId, Long providerId, String userName, Long accountId, String transactionNote) {

		LedgerVO request = new LedgerVO();
		setRequest(request, CommonConstants.NO_FUNDING_TYPE_ACTION, serviceOrderId, null, providerId, userName); 
		request.setAccountId(accountId);
		request.setTransactionNote(transactionNote); 
		return request;

	}
	
	public LedgerVO createProviderLedgerRequest(String serviceOrderId, Long providerId, String userName, Long accountId) {

		LedgerVO request = new LedgerVO();
		setRequest(request, CommonConstants.NO_FUNDING_TYPE_ACTION, serviceOrderId, null, providerId, userName);
		request.setAccountId(accountId);
		return request;

	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.service.requestbuilder.ILedgerRequestBuilder#createAdminLedgerRequest(java.lang.String, java.lang.String, java.lang.Long)
	 */
	public LedgerVO createAdminLedgerRequest(String userName, String transactionNote, Long accountId){
		
		LedgerVO request = new LedgerVO();
		setRequest(request, CommonConstants.NO_FUNDING_TYPE_ACTION, null, null, null, userName);
		request.setTransactionNote(transactionNote);
		request.setAccountId(accountId);
		return request;
	}
}
