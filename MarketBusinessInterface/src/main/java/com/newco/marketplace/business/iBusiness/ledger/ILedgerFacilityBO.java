package com.newco.marketplace.business.iBusiness.ledger;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.ach.AchProcessQueueEntryVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.ledger.TransactionEntryVO;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * Description of the Interface
 * 
 * @author dmill03
 * @created August 15, 2007
 */
public interface ILedgerFacilityBO {

	public void decreaseSpendLimit(MarketPlaceTransactionVO service, double amount,double achAmount)  throws BusinessServiceException;

	public boolean closeServiceOrderLedgerAction(MarketPlaceTransactionVO service) throws BusinessServiceException;

	public boolean routeServiceOrderLedgerAction(MarketPlaceTransactionVO service,FundingVO fundVo) throws BusinessServiceException;

	public boolean cancelServiceOrderLedgerAction(MarketPlaceTransactionVO service) throws BusinessServiceException;

	public boolean cancelServiceOrderWOPenaltyLedgerAction(MarketPlaceTransactionVO service) throws BusinessServiceException;

	public double getAvailableBalance(AjaxCacheVO avo) throws BusinessServiceException;


	//SL-21117: Revenue Pull Code starts

	public List <String> getPermittedUsers() throws BusinessServiceException;

	public double getAvailableBalanceForRevenuePull() throws BusinessServiceException;

	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws BusinessServiceException;

	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user) throws BusinessServiceException;

	public List <String> getPermittedUsersEmail() throws BusinessServiceException;

	//Code change ends


	public double getCurrentBalance(AjaxCacheVO avo) throws BusinessServiceException;

	public void increaseSpendLimit(MarketPlaceTransactionVO service,double spendLimitIncreaseAmount, String soId, double achAmount) throws BusinessServiceException ;
	public void increaseSpendLimit(MarketPlaceTransactionVO service, double spendLimitIncreaseAmount, double upsellAmount, String soId, double achAmount)  throws BusinessServiceException ;
	public void increaseSpendLimitCompletion(MarketPlaceTransactionVO service,double spendLimitIncreaseAmount, double upsellAmount, String soId, double achAmount) throws BusinessServiceException;
	public boolean voidServiceOrderLedgerAction(MarketPlaceTransactionVO service) throws BusinessServiceException;

	public void providerWithdrawFundsReversal(AchProcessQueueEntryVO achProcessQueueEntryVO,MarketPlaceTransactionVO service) throws BusinessServiceException;

	public void adminCreditsSLBtoBuyer(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException;
	public void adminCreditsSLBtoProvider(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException;
	public void adminDebitsSLBfromBuyer(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException;
	public void adminDebitsSLBfromProvider(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException;
	public void adminEscheatmentSLBfromBuyer(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException;
	public void adminEscheatmentSLBfromProvider(MarketPlaceTransactionVO service, Double amount, Integer reasonCode) throws BusinessServiceException;


	public double getSLOperationBalance() throws BusinessServiceException;

	public Integer issueRefunds(MarketPlaceTransactionVO service, double amount, long accountId, String userName) throws BusinessServiceException;

	public Integer withdrawfundsSLAOperations(MarketPlaceTransactionVO service, double amount, long accountId,String user)  throws BusinessServiceException;

	public Integer depositfundsSLAOperations(MarketPlaceTransactionVO service, double amount, long accountId,String user)  throws BusinessServiceException;

	public 	void populateEntityId(TransactionEntryVO transactionEntry,MarketPlaceTransactionVO service);

	public boolean postConsumerOrder(MarketPlaceTransactionVO service,FundingVO fundVo)throws BusinessServiceException;


}
