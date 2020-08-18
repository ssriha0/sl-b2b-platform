package com.servicelive.wallet.alert;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * Interface IAlertBO.
 */
public interface IAlertBO {

	/**
	 * sendACHFailureAlertForProvider.
	 * 
	 * @param providerId 
	 * @param transId 
	 * @param amount 
	 * @param returnDesc 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendACHFailureAlertForProvider(long providerId, String transId, Double amount, String returnDesc, int templateId);

	/**
	 * sendEscheatmentACHFailuretoProdSupp.
	 * 
	 * @param transId 
	 * @param amount 
	 * @param returnDesc 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendEscheatmentACHFailuretoProdSupp(String transId, Double amount, String returnDesc, int templateId);
	
	/**
	 * sendSettlementConfirmationToBuyer.
	 * 
	 * @param buyerId 
	 * @param ledgerId 
	 * @param amount 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendSettlementConfirmationToBuyer(long buyerId, String ledgerId, Double amount, int templateId, String roleType);

	/**
	 * sendSettlementConfirmationToProvider.
	 * 
	 * @param providerId 
	 * @param ledgerId 
	 * @param amount 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendSettlementConfirmationToProvider(long providerId, String ledgerId, Double amount, int templateId);
	
	/**
	 * sendEmailVLMessageFailure.
	 * 
	 * @param ledgerEntityId 
	 * @param role 
	 * @param actionCode 
	 * @param actionCodeDesc 
	 * @param fullfillmentGroupId 
	 * @param fullfillmentEntryId 
	 * @param entryDate 
	 * @param messageIdentifier 
	 * @param primaryAccountNumber 
	 * @param entityTypeDesc 
	 * @param transactionAmount 
	 * @param serviceOrderId 
	 * @param templateId 
	 * 
	 * @return void
	 */
	public void sendEmailVLMessageFailure(long ledgerEntityId, String role, String actionCode, String actionCodeDesc,
		long fullfillmentGroupId, long fullfillmentEntryId, Timestamp entryDate,
		String messageIdentifier, long primaryAccountNumber, String entityTypeDesc, 
		double transactionAmount, String serviceOrderId, int templateId);
}
