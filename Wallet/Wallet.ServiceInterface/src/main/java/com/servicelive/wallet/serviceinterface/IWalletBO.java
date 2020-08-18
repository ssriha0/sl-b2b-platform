package com.servicelive.wallet.serviceinterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

public interface IWalletBO {

	public double getCurrentSpendingLimit(String serviceOrderId) throws SLBusinessServiceException;

	public boolean checkValueLinkReconciledIndicator(String soId) throws SLBusinessServiceException;
	
	public boolean isACHTransPending(String  serviceOrderId) throws SLBusinessServiceException;
	
    public boolean hasPreviousAddOn(String serviceOrderId)throws SLBusinessServiceException;

	public double getBuyerTotalDeposit(Long buyerId) throws SLBusinessServiceException;

    public boolean isBuyerAutoFunded(Long buyerId) throws SLBusinessServiceException;
    
	public WalletResponseVO adminCreditToBuyer(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO adminCreditToProvider(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO adminDebitFromBuyer(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO withdrawBuyerdebitReversal(WalletVO request) throws SLBusinessServiceException;


	public WalletResponseVO adminEscheatmentFromBuyer(WalletVO request) throws SLBusinessServiceException;
	
	public WalletResponseVO adminDebitFromProvider(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO adminEscheatmentFromProvider(WalletVO request) throws SLBusinessServiceException;
	
	public WalletResponseVO cancelServiceOrder(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO cancelServiceOrderWithoutPenalty(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO closeServiceOrder(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO decreaseProjectSpendLimit(WalletVO request) throws SLBusinessServiceException;

	public void depositBuyerFundsAtValueLink(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO depositBuyerFundsWithCash(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO depositBuyerFundsWithCreditCard(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO depositBuyerFundsWithInstantACH(WalletVO request) throws SLBusinessServiceException;

	public void depositSLOperationFundsAtValueLink(WalletVO request) throws SLBusinessServiceException;

	public double getBuyerAvailableBalance(long entityId) throws SLBusinessServiceException;
	
	//SL-21117: Revenue Pull Code change Starts
	
	public List <String> getPermittedUsers() throws BusinessServiceException;
		
	public double getAvailableBalanceForRevenuePull() throws SLBusinessServiceException;
		
	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws SLBusinessServiceException;
		
	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user) throws SLBusinessServiceException;
		
	public List <String> getPermittedUsersEmail() throws BusinessServiceException;
				
	//Code change ends

	public double getBuyerCurrentBalance(long entityId) throws SLBusinessServiceException;

	public double getProviderBalance(long entityId) throws SLBusinessServiceException;

	public double getSLOperationBalance() throws SLBusinessServiceException;

	public WalletResponseVO increaseProjectSpendLimit(WalletVO request) throws SLBusinessServiceException;

	/**
	 * Decription: increaseProjectSpendCompletion, should only fire rules at completion. 
	 * 
	 * @param request 
	 * 
	 * @return WalletResponseVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public WalletResponseVO increaseProjectSpendCompletion(WalletVO request) throws SLBusinessServiceException;
	/**
	 * postServiceOrder.
	 * 
	 * @param request 
	 * 
	 * @return WalletResponseVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public WalletResponseVO postServiceOrder(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO voidServiceOrder(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO withdrawBuyerCashFunds(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO withdrawBuyerCreditCardFunds(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO withdrawProviderFunds(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO withdrawProviderFundsReversal(WalletVO request) throws SLBusinessServiceException;

	public WalletResponseVO depositOperationFunds(WalletVO request) throws SLBusinessServiceException;
	
	public WalletResponseVO withdrawOperationFunds(WalletVO request) throws SLBusinessServiceException;

	public List<ValueLinkEntryVO> getValueLinkEntries(String[] valueLinkEntryId, Boolean groupId) throws SLBusinessServiceException;

	public List<ValueLinkEntryVO> processGroupResend(String[] fulfillmentGroupIds, String comments, String userName) throws SLBusinessServiceException;

	public Map<String, Long> reverseValueLinkTransaction(String[] valueLinkIds, String comments, String userName) throws SLBusinessServiceException;

	public Map<String, Long> createValueLinkWithNewAmount(String fulfillmentEntryId, Double newAmount, String comments, String userName) throws SLBusinessServiceException;

	public WalletResponseVO getWalletMessageResult(String messageId) throws SLBusinessServiceException;
	
	public WalletResponseVO activateBuyer(WalletVO request) throws SLBusinessServiceException;
	
	public ReceiptVO getTransactionReceipt(Long entityId, Integer entityTypeId, LedgerEntryType entryType, String serviceOrderId) throws SLBusinessServiceException;
	
	public WalletResponseVO authCCForDollarNoCVV(WalletVO request) throws SLBusinessServiceException;
	
	public String getLedgerEntryNonce(long busTransId) throws SLBusinessServiceException;
	public WalletResponseVO getCreditCardInformation(Long accountId) throws SLBusinessServiceException;
	
	public Double getTransactionAmount(Long transactionId) throws SLBusinessServiceException;
	
	public double getCompletedSOLedgerAmount(long vendorId) throws SLBusinessServiceException;
	
    public long getAccountId(long buyerId) throws SLBusinessServiceException;
     /**
	 * @param hsWebserviceAppKey 
	 * @return
	 * @throws SLBusinessServiceException
	 */
    public String getApplicationFlagForHSWebService(String hsWebserviceAppKey) throws SLBusinessServiceException;
}
