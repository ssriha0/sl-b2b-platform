package com.servicelive.wallet.ledger;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ledger.dao.ITransactionDao;
import com.servicelive.wallet.serviceinterface.requestbuilder.LedgerRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.LedgerVO;
import com.servicelive.wallet.serviceinterface.vo.OrderPricingVO;

/**
 * Class LedgerTest.
 */
	public class LedgerTest extends AbstractTransactionalDataSourceSpringContextTests {
	// Domain Objects needed used in the tests
	/** ledgerBO. */
	private ILedgerBO ledgerBO;
	
	/** ledgerRequestBuilder. */
	private LedgerRequestBuilder ledgerRequestBuilder;
	
	/** transactionDao. */
	private ITransactionDao transactionDao;

	// Dummy variables used in the tests
	/** BUYER_ID. */
	private static final long BUYER_ID = 10724L;
	
	/** BUYER_ACCOUNT_ID. */
	private static final long BUYER_ACCOUNT_ID = 771343L;

	/** PROVIDER_ID. */
	private static final long PROVIDER_ID = 1942648L;
	
	/** PROVIDER_ACCOUNT_ID. */
	private static final long PROVIDER_ACCOUNT_ID = 500635L;

	/** SERVICE_LIVE_ID. */
	private static final long SERVICE_LIVE_ID = 30;

	/** SERVICE_ORDER_ID. */
	private static final String SERVICE_ORDER_ID = "156-7329-6781-72";
	
	/** USER_NAME. */
	private static final String USER_NAME = "junit User";
	
	/** TRANSACTION_NOTE. */
	private static final String TRANSACTION_NOTE = null;

	/** ledgerVOBuyerPrefund. */
	private LedgerVO ledgerVOBuyerPrefund = ledgerRequestBuilder.createBuyerLedgerRequest(CommonConstants.FUNDING_TYPE_PRE_FUNDED,
			SERVICE_ORDER_ID, BUYER_ID, USER_NAME, BUYER_ACCOUNT_ID, TRANSACTION_NOTE);

	/** ledgerVOBuyerSHC. */
	private LedgerVO ledgerVOBuyerSHC = ledgerRequestBuilder.createBuyerLedgerRequest(CommonConstants.SHC_FUNDING_TYPE,
			SERVICE_ORDER_ID, BUYER_ID, USER_NAME, BUYER_ACCOUNT_ID, TRANSACTION_NOTE);

	/** ledgerVOProviderPrefund. */
	private LedgerVO ledgerVOProviderPrefund = ledgerRequestBuilder.createBuyerLedgerRequest(CommonConstants.FUNDING_TYPE_PRE_FUNDED,
			SERVICE_ORDER_ID, PROVIDER_ID, USER_NAME, PROVIDER_ACCOUNT_ID, TRANSACTION_NOTE);

	/** ledgerVOProviderSHC. */
	private LedgerVO ledgerVOProviderSHC = ledgerRequestBuilder.createBuyerLedgerRequest(CommonConstants.SHC_FUNDING_TYPE,
			SERVICE_ORDER_ID, PROVIDER_ID, USER_NAME, PROVIDER_ACCOUNT_ID, TRANSACTION_NOTE);

	/** AMOUNT. */
	private static final double AMOUNT = 45.0;
	
	/** CANCELLATION_FEE. */
	private static final double CANCELLATION_FEE = 20.0;
	
	/** SETPOSTING_FEE. */
	private static final double SETPOSTING_FEE = 10.0;
	
	/** orderPricingVO. */
	private OrderPricingVO orderPricingVO = new OrderPricingVO();
	{
		orderPricingVO.setAmount(AMOUNT);
		orderPricingVO.setCancellationFee(CANCELLATION_FEE);
		orderPricingVO.setPostingFee(SETPOSTING_FEE);
	}
		
	/**
	 * LedgerTest.
	 */
	public LedgerTest() {
		this.setAutowireMode(AUTOWIRE_BY_NAME);
	}

 	/* (non-Javadoc)
	  * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	  */
	 @Override
 	protected String[] getConfigLocations() {
 		return new String[] {"com/servicelive/wallet/creditcard/testCreditCardApplicationContext.xml"};
 	}

	/* (non-Javadoc)
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUp()
	 */
	@Override
	public void onSetUp() throws Exception {

		ledgerBO = (ILedgerBO) getApplicationContext().getBean("ledgerTransacted");
		ledgerRequestBuilder = (LedgerRequestBuilder) getApplicationContext().getBean("ledgerRequestBuilder");
		transactionDao = (ITransactionDao) getApplicationContext().getBean("transactionDao");
	}

	/* (non-Javadoc)
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onTearDown()
	 */
	@Override
	protected void onTearDown() throws Exception {
		this.transactionManager.rollback(this.transactionStatus);
	}

	
	/**
	 * getBuyerAvailableBalance.
	 * 
	 * @return double
	 * 
	 * @throws DataServiceException 
	 */
	private double getBuyerAvailableBalance() throws DataServiceException
	{
		return transactionDao.getAvailableBalanceByEntityId(BUYER_ID, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, false).doubleValue();
	}
	
	/**
	 * getProviderAvailableBalance.
	 * 
	 * @return double
	 * 
	 * @throws DataServiceException 
	 */
	private double getProviderAvailableBalance() throws DataServiceException
	{
		return transactionDao.getAvailableBalanceByEntityId(PROVIDER_ID, CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, false).doubleValue();
	}
	
	/**
	 * getServiceLiveMainBalance.
	 * 
	 * @return double
	 * 
	 * @throws DataServiceException 
	 */
	private double getServiceLiveMainBalance() throws DataServiceException
	{
		return transactionDao.getAvailableBalanceByEntityId(SERVICE_LIVE_ID, CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN, false)
			.doubleValue();
	}
	
	/**
	 * getServiceLiveOperationBalance.
	 * 
	 * @return double
	 * 
	 * @throws DataServiceException 
	 */
	private double getServiceLiveOperationBalance() throws DataServiceException
	{
		return transactionDao.getAvailableBalanceByEntityId(SERVICE_LIVE_ID, CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION, false)
			.doubleValue();
	}

	/**
	 * testBusinessTransactionBuyerDepositFromCC.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionBuyerDepositFromCC() throws Exception { //1001
		deposit(CommonConstants.CREDIT_CARD_SEARS, CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_SEARS);
		deposit(CommonConstants.CREDIT_CARD_AMEX, CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_AMEX);
		deposit(CommonConstants.CREDIT_CARD_MC, CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX);
		deposit(CommonConstants.CREDIT_CARD_VISA, CommonConstants.BUSINESS_TRANSACTION_BUYER_DEPOSITS_FROM_V_MX);
	}
	
	/**
	 * deposit.
	 * 
	 * @param creditCardTypeId 
	 * @param businessTransaction 
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	private void deposit(long creditCardTypeId, long businessTransaction) throws Exception {
		// set business transaction id
		ledgerVOBuyerPrefund.setCreditCardTypeId(creditCardTypeId);
		ledgerVOBuyerPrefund.setBusinessTransactionId(businessTransaction);
		
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		double afterBuyerAmt = getBuyerAvailableBalance();
		Assert.assertEquals(afterBuyerAmt, beforeBuyerAmt - AMOUNT); // Buyer Pays
		double afterServiceLiveAmt = getServiceLiveMainBalance();
		Assert.assertEquals(afterServiceLiveAmt, beforeServiceLiveAmt + AMOUNT); // SL Receives
	}

	/**
	 * testBusinessTransactionBuyerInstantAchDeposit.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionBuyerInstantAchDeposit() throws Exception { //1002
		ledgerVOBuyerPrefund.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt - AMOUNT); // Buyer Pays
		double afterServiceLiveAmt = getServiceLiveMainBalance();
		Assert.assertEquals(afterServiceLiveAmt, beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionBuyerInstantAchRefund.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionBuyerInstantAchRefund() throws Exception {
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_REFUND);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt + AMOUNT); // Buyer Receives
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt - AMOUNT); // SL Pays
	}
	
	/**
	 * testBusinessTransactionCancelSo.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionCancelSo() throws Exception {
		//TODO
	}
	
	/**
	 * testBusinessTransactionCancelSoWoPenalty.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionCancelSoWoPenalty() throws Exception {
		//TODO
	}
	
	/**
	 * testBusinessTransactionDecreaseSoEscrow.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionDecreaseSoEscrow() throws Exception { //1005
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DECREASE_SO_ESCROW);

		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt + AMOUNT); // Buyer Receives
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt - AMOUNT); // SL Pays
		}
	
	/**
	 * testBusinessTransactionDepositsFundsBuyer.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionDepositsFundsBuyer() throws Exception { //1006
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER);
		
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt - AMOUNT); // Buyer Pays
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionIncreaseSoEscrow.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionIncreaseSoEscrow() throws Exception { //1007
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt + AMOUNT); // Buyer Receives
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt - AMOUNT); // SL Pays
	}
	
	/**
	 * testBusinessTransactionPostSo.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionPostSo() throws Exception { //1008
//TODO Check
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_POST_SO);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt - AMOUNT); // Buyer Pays
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionReleaseSoPayment.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionReleaseSoPayment() throws Exception { //1009
//TODO Check fees
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerPrefund, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt - AMOUNT); // Buyer Pays
		double afterServiceLiveAmt = getServiceLiveMainBalance();
		Assert.assertEquals(afterServiceLiveAmt, beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionSLaCreditsSLbToBuyer.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionSLaCreditsSLbToBuyer() throws Exception { //1010
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);
		
		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt + AMOUNT); // Buyer Receives
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt - AMOUNT); // SL Pays
	}
	
	/**
	 * testBusinessTransactionSLaCreditsSLbToProvider.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionSLaCreditsSLbToProvider() throws Exception { //1011
		ledgerVOProviderSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER);
			
		double beforeProviderAmt = getProviderAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getProviderAvailableBalance(), beforeProviderAmt + AMOUNT); // Provider Receives
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt - AMOUNT); // SL Pays
	}
	
	/**
	 * testBusinessTransactionSLaDebitsSLbFromBuyer.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionSLaDebitsSLbFromBuyer() throws Exception { //1012
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt - AMOUNT); // Buyer Pays
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionSLaDeditsSLbFromProvider.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionSLaDeditsSLbFromProvider() throws Exception { //1013
		ledgerVOProviderSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER);
			
		double beforeProviderAmt = getProviderAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getProviderAvailableBalance(), beforeProviderAmt - AMOUNT); // Provider Pays
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionDepositsToOperations.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionDepositsToOperations() throws Exception { //1014
		ledgerVOBuyerPrefund.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveOperationBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt - AMOUNT); // Buyer Pays
		Assert.assertEquals(getServiceLiveOperationBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionWithdrawsFromOperations.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionWithdrawsFromOperations() throws Exception { //1015
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS);
			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveOperationBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt + AMOUNT); // Buyer Receives
		Assert.assertEquals(getServiceLiveOperationBalance(), beforeServiceLiveAmt - AMOUNT); // SL Pays
	}
	
	/**
	 * testBusinessTransactionVoidSo.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionVoidSo() throws Exception { //1016
		ledgerVOBuyerSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_VOID_SO);
//TODO Complicated			
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt - AMOUNT); // Buyer Pays
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionWithdrawFundsBuyer.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */

	@Test
	public void testBusinessTransactionWithdrawFundsBuyer() throws Exception { //1017
	//TODO This should not be implemented.
	}

	/**
	 * testBusinessTransactionWithdrawFundsProvider.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionWithdrawFundsProvider() throws Exception {
		ledgerVOProviderSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER); //TODO Not found in WalletBO
			
		double beforeProviderAmt = getProviderAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getProviderAvailableBalance(), beforeProviderAmt - AMOUNT); // Buyer Pays
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionWithdrawFundsProviderReversal.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionWithdrawFundsProviderReversal() throws Exception {
		ledgerVOProviderSHC.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL); //TODO Not found in WalletBO
			
		double beforeProviderAmt = getProviderAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getProviderAvailableBalance(), beforeProviderAmt - AMOUNT); // Provider Pays
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt + AMOUNT); // SL Receives
	}
	
	/**
	 * testBusinessTransactionFundsToBuyersCC.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testBusinessTransactionFundsToBuyersCC() throws Exception {
		fundsToBuyers(CommonConstants.CREDIT_CARD_AMEX, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_AMEX);
		fundsToBuyers(CommonConstants.CREDIT_CARD_MC, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_V_MC);
		fundsToBuyers(CommonConstants.CREDIT_CARD_VISA, CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_V_MC);
//		fundsToBuyers(CommonConstants.CREDIT_CARD_VISA, WalletConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_TO_BUYERS_SEARS); //TODO Not found in WalletBO
	}
	
	/**
	 * fundsToBuyers.
	 * 
	 * @param creditCardTypeId 
	 * @param businessTransaction 
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	private void fundsToBuyers(long creditCardTypeId, long businessTransaction) throws Exception {
		ledgerVOProviderPrefund.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL);

		// set business transaction id
		ledgerVOBuyerPrefund.setCreditCardTypeId(creditCardTypeId);
		ledgerVOBuyerPrefund.setBusinessTransactionId(businessTransaction);
		
		double beforeBuyerAmt = getBuyerAvailableBalance();
		double beforeServiceLiveAmt = getServiceLiveMainBalance();

		ledgerBO.postLedgerTransaction(ledgerVOBuyerSHC, orderPricingVO);

		Assert.assertEquals(getBuyerAvailableBalance(), beforeBuyerAmt + AMOUNT); // Buyer Receives
		Assert.assertEquals(getServiceLiveMainBalance(), beforeServiceLiveAmt - AMOUNT); // SL Pays
	}
}
