package com.servicelive.orderfulfillment.common;

import java.math.BigDecimal;

import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public interface IWalletGateway {
	
	public WalletResponseVO postServiceOrder(String userName, ServiceOrder so, String buyerState);
	public void voidServiceOrder(String userName, ServiceOrder serviceOrder, String buyerState);
    public WalletResponseVO cancelServiceOrderWithPenalty(String userName, ServiceOrder serviceOrder, String buyerState, String providerState);
    public ReceiptVO getCancellationPenaltyReceipt(Long providerId, String serviceOrderId);
    public void cancelServiceOrderWithoutPenalty(String userName, ServiceOrder serviceOrder, String buyerState);
    public WalletResponseVO closeServiceOrder(String userName, ServiceOrder serviceOrder, String buyerState, String providerState, BigDecimal addOnTotal, BigDecimal serviceFeePercentage);
    public double getCurrentSpendingLimit(String soId);
	public double getBuyerAvailableBalance(Long buyerId);
    public ReceiptVO getBuyerPostingFeeReceipt(Long buyerId, String serviceOrderId);
    public ReceiptVO getBuyerSpendLimitRsrvReceipt(Long buyerId, String serviceOrderId);
	public WalletResponseVO decreaseSpendLimit(String userName, ServiceOrder so, String buyerState, double amount );
	public WalletResponseVO increaseSpendLimit(String userName, ServiceOrder so, String buyerState, double amount );
	public WalletResponseVO increaseSpendOnCompletion(String userName, ServiceOrder so, String buyerState, double amount);
	//for HSR
	public WalletResponseVO increaseSpendOnCompletionForHSR(String userName, ServiceOrder so, String buyerState, double amount, double retailPrice, double reimbursementRetailPrice, double partsSLGrossup,
			double retailPriceSLGrossup,boolean hasAddon,boolean hasParts,double addOnAmount,double partsAmount);
	public boolean areAllValueLinkTransactionsReconciled(String serviceOrderId);
	public ReceiptVO getTransactionReceipt(Long entityId, Integer entityTypeId,	LedgerEntryType entryType, String serviceOrderId);
	double getRoundedMoney(double doubleNumber);

	public WalletResponseVO getWalletMessageResult(String messageId);

	public WalletResponseVO addFundsToBuyerAccount(String userName, Long buyerId, Long accountId, String buyerState, ServiceOrder so, BigDecimal amount);

    public Boolean isBuyerAutoFunded(Long buyerId);
    
    public WalletResponseVO getCreditCardInformation(Long accountId);
    public double getTransactionAmountById(Long transactionId);
    public boolean isACHTransPending(String serviceOrderId);
    public boolean hasPreviousAddOn(String serviceOrderId);
    public long getAccountId(long buyerId);
}
