package com.servicelive.wallet.ledger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.ledger.vo.LedgerBusinessTransactionVO;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.LedgerVO;
import com.servicelive.wallet.serviceinterface.vo.OrderPricingVO;
import com.servicelive.wallet.serviceinterface.vo.ProviderWithdrawalErrorVO;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;

public interface ILedgerBO {

	public double getBuyerTotalDeposit(Long buyerId) throws SLBusinessServiceException;

	double getAvailableBalance(long entityId, long entityTypeId, boolean lockForUpdate) throws SLBusinessServiceException;

	double getCurrentBalance(long entityId, long entityTypeId) throws SLBusinessServiceException;

	double getPostSOLedgerAmount(String serviceOrderId) throws SLBusinessServiceException;

	double getProjectBalance(long accountOwnerId, long ledgerEntityTypeId) throws SLBusinessServiceException;

	double getSLOperationBalance() throws SLBusinessServiceException;

	double getTotalDepositByEntityId(long accountOwnerId, long ledgerEntityTypeId) throws SLBusinessServiceException;

	public LedgerBusinessTransactionVO postLedgerTransaction(LedgerVO ledgerVO, OrderPricingVO orderPricingVO) throws SLBusinessServiceException;

	public HashMap<String,Double> getPastWithdrawalsByEntityId(long entityId) throws SLBusinessServiceException;

	public Double getValueLinkBalanceByEntityId(long entityId);
	
	public Long getPastWithdrawalsWithSameAmt(Long accountId,Integer timeIntervalSec,Double transactionAmount);

	public ReceiptVO getTransactionReceipt(Long entityId, Integer entityTypeId, LedgerEntryType entryType, String serviceOrderId) throws SLBusinessServiceException;
	
	public void insertWithdrawalErrorLogging(ProviderWithdrawalErrorVO providerWithdrawalErrorVO) throws SLBusinessServiceException;
	
    public String getLedgerEntryNonce(long busTransId) throws SLBusinessServiceException;
	public Double getTransactionAmountById(Long transactionId) throws DataServiceException;
	
	public double getAvailableBalanceFromLedger(long accountOwnerId, long ledgerEntityTypeId, boolean lockForUpdate) throws SLBusinessServiceException;
	
	//SL-21117: Revenue Pull Code change Starts
	
	public List <String> getPermittedUsers(long ind) throws SLBusinessServiceException;
		
	public double getAvailableBalanceForRevenuePull(long ledgerEntityId, long ledgerEntityTypeId) throws SLBusinessServiceException;
		
	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws SLBusinessServiceException;
		
	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user,long ind,Date todayDate,String status) throws SLBusinessServiceException;
		
	public List <String> getPermittedUsersEmail(long ind) throws SLBusinessServiceException;
		
	//Code change ends
	
}
