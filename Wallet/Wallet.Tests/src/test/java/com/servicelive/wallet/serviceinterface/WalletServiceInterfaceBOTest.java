package com.servicelive.wallet.serviceinterface;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import com.servicelive.wallet.ach.AchBO;
import com.servicelive.wallet.ach.mocks.MockAchBatchRequestDao;
import com.servicelive.wallet.creditcard.mocks.MockCreditCardBO;
import com.servicelive.wallet.ledger.LedgerBO;
import com.servicelive.wallet.ledger.mocks.MockTransactionDao;
import com.servicelive.wallet.ledger.vo.TransactionEntryVO;
import com.servicelive.wallet.service.WalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.servicelive.wallet.valuelink.ValueLinkBO;
import com.servicelive.wallet.valuelink.mocks.MockValueLinkDao;

/**
 * Class WalletServiceInterfaceBOTest.
 */
public class WalletServiceInterfaceBOTest {

	/** context. */
	private static ApplicationContext context = new ClassPathXmlApplicationContext("com/servicelive/wallet/serviceinterface/testWalletServiceInterfaceApplicationContext.xml");

	/**
	 * getAppContext.
	 * 
	 * @return ApplicationContext
	 * 
	 * @throws BeansException 
	 */
	private static ApplicationContext getAppContext() throws BeansException {

		return context;
	}

	/** BANK_ACCOUNT_ID. */
	private final Long BANK_ACCOUNT_ID = 63199074L;

	/** BUYER_ID. */
	private final Long BUYER_ID = 1000L;

	/** BUYER_STATE. */
	private final String BUYER_STATE = "MI";

	/** BUYER_V1_ACCOUNT_NUMBER. */
	private final Long BUYER_V1_ACCOUNT_NUMBER = 7777008374704775L;

	/** BUYER_V2_ACCOUNT_NUMBER. */
	private final Long BUYER_V2_ACCOUNT_NUMBER = 7777008393032388L;

	/** mockAchBatchRequestDao. */
	private MockAchBatchRequestDao mockAchBatchRequestDao;

//	/** The mock client lookup. */
//	private MockClientLookup mockClientLookup;

	/** mockCreditCard. */
	private MockCreditCardBO mockCreditCard;

	/** mockTransactionDao. */
	private MockTransactionDao mockTransactionDao;

	/** mockValueLinkDao. */
	private MockValueLinkDao mockValueLinkDao;

	/** lookup. */
	private ILookupBO lookup;
	
	/** PROVIDER_ID. */
	private final Long PROVIDER_ID = 10289L;

	/** PROVIDER_STATE. */
	private final String PROVIDER_STATE = "MI";

	/** PROVIDER_V1_ACCOUNT_NUMBER. */
	private final Long PROVIDER_V1_ACCOUNT_NUMBER = 7777008405952446L;

	/** SERVICE_ORDER_ID. */
	private final String SERVICE_ORDER_ID = "576-5063-0622-39";

	/** SL1_ACCOUNT_NUMBER. */
	private final Long SL1_ACCOUNT_NUMBER = 7777008250310755L;

	/** SL3_ACCOUNT_NUMBER. */
	private final Long SL3_ACCOUNT_NUMBER = 7777009239100382L;

	/** USER_NAME. */
	private final String USER_NAME = "junit";

	/** VISA_CREDIT_CARD_ACCOUNT_ID. */
	private final Long VISA_CREDIT_CARD_ACCOUNT_ID = 299469419L;
	
	/** BUYER_INFO. */
	private BuyerLookupVO BUYER_INFO;
	
	/** PROVIDER_INFO. */
	private ProviderLookupVO PROVIDER_INFO;
	
	/** ADMIN_INFO. */
	private AdminLookupVO ADMIN_INFO;
	
	/** CREDIT_CARD_INFO. */
	private SLCreditCardVO CREDIT_CARD_INFO;
	
	/** walletInterface. */
	private WalletRequestBuilder walletReqBuilder = new WalletRequestBuilder();
	private WalletBO wallet;

	/**
	 * assertAchEntryCount.
	 * 
	 * @param count 
	 * 
	 * @return void
	 */
	private void assertAchEntryCount(int count) {

		Assert.assertEquals(count, mockAchBatchRequestDao.getAchInserts().size());
		Assert.assertEquals(count, mockAchBatchRequestDao.getAchUpdates().size());
	}

	/**
	 * assertAchEntryInsert.
	 * 
	 * @param index 
	 * @param entityTypeId 
	 * @param ledgerEntryTypeId 
	 * @param transactionTypeId 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void assertAchEntryInsert(int index, long entityTypeId, long ledgerEntryTypeId, long transactionTypeId, double amount) {

		Assert.assertEquals(entityTypeId, mockAchBatchRequestDao.getAchInserts().get(index).getEntityTypeId());
		Assert.assertEquals(ledgerEntryTypeId, (long) mockAchBatchRequestDao.getAchInserts().get(index).getTransactionEntryTypeId());
		Assert.assertEquals(transactionTypeId, mockAchBatchRequestDao.getAchInserts().get(index).getTransactionTypeId());
		Assert.assertEquals(amount, mockAchBatchRequestDao.getAchInserts().get(index).getTransactionAmount());
	}

	/**
	 * assertCreditCard.
	 * 
	 * @param index 
	 * @param authCode 
	 * 
	 * @return void
	 */
	private void assertCreditCard(int index, String authCode) {

		Assert.assertEquals(authCode, mockCreditCard.getCreditCards().get(index).getAuthorizationCode());
	}

	/**
	 * assertCreditCardCount.
	 * 
	 * @param count 
	 * 
	 * @return void
	 */
	private void assertCreditCardCount(int count) {

		Assert.assertEquals(count, mockCreditCard.getCreditCards().size());
	}

	/**
	 * assertCreditEntry.
	 * 
	 * @param index 
	 * @param ruleId 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void assertCreditEntry(int index, long ruleId, double amount) {

		assertLedgerEntry(index, ruleId, CommonConstants.ENTRY_TYPE_CREDIT, amount);
	}

	/**
	 * assertDebitEntry.
	 * 
	 * @param index 
	 * @param ruleId 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void assertDebitEntry(int index, long ruleId, double amount) {

		assertLedgerEntry(index, ruleId, CommonConstants.ENTRY_TYPE_DEBIT, amount);
	}

	/**
	 * assertIsoMessage.
	 * 
	 * @param index 
	 * @param messageTypeId 
	 * @param primaryAccountNumber 
	 * 
	 * @return void
	 */
//	private void assertIsoMessage(int index, long messageTypeId, long primaryAccountNumber) {
//
//		Assert.assertEquals(primaryAccountNumber, (long) this.mockValueLinkDao.getValueLinkEntries().get(index).getPrimaryAccountNumber());
//		Assert.assertEquals(messageTypeId, (long) this.mockValueLinkDao.getValueLinkEntries().get(index).getMessageTypeId());
//	}

	/**
	 * assertLedgerEntry.
	 * 
	 * @param index 
	 * @param ruleId 
	 * @param entryTypeId 
	 * 
	 * @return void
	 */
	private void assertLedgerEntry(int index, long ruleId, long entryTypeId) {

		List<TransactionEntryVO> transactionEntries = mockTransactionDao.getTransactionEntries();
		Assert.assertEquals(ruleId, (int) transactionEntries.get(index).getLedgerEntryRuleId());
		Assert.assertEquals(entryTypeId, (int) transactionEntries.get(index).getEntryTypeId());
	}

	/**
	 * assertLedgerEntry.
	 * 
	 * @param index 
	 * @param ruleId 
	 * @param entryTypeId 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void assertLedgerEntry(int index, long ruleId, long entryTypeId, double amount) {

		List<TransactionEntryVO> transactionEntries = mockTransactionDao.getTransactionEntries();
		assertLedgerEntry(index, ruleId, entryTypeId);
		Assert.assertEquals(amount, transactionEntries.get(index).getTransactionAmount());
	}

	/**
	 * assertLedgerEntryCount.
	 * 
	 * @param count 
	 * 
	 * @return void
	 */
	private void assertLedgerEntryCount(int count) {

		Assert.assertEquals(count, mockTransactionDao.getTransactionEntries().size());
	}

	/**
	 * assertValueLinkEntry.
	 * 
	 * @param index 
	 * @param ruleId 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void assertValueLinkEntry(int index, long ruleId, Double amount) {

		List<ValueLinkEntryVO> valueLinkEntries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(ruleId, (long) valueLinkEntries.get(index).getFullfillmentEntryRuleId());
		Assert.assertEquals(amount, valueLinkEntries.get(index).getTransAmount());
	}

	/**
	 * assertValueLinkEntryCount.
	 * 
	 * @param count 
	 * 
	 * @return void
	 */
	private void assertValueLinkEntryCount(int count) {

		Assert.assertEquals(count, mockValueLinkDao.getValueLinkEntries().size());
	}

	/**
	 * generateAuthCode.
	 * 
	 * @return String
	 */
	private String generateAuthCode() {

		return String.valueOf(new Date().getTime());
	}

	/**
	 * setUp.
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {

		ApplicationContext context = getAppContext();
		wallet = (WalletBO)context.getBean("wallet");

		// lookup some info
		lookup = (ILookupBO)context.getBean("lookup");
		ADMIN_INFO = lookup.lookupAdmin();
		BUYER_INFO = lookup.lookupBuyer(BUYER_ID);
		PROVIDER_INFO = new ProviderLookupVO(); // lookup.lookupProvider(PROVIDER_ID);
		CREDIT_CARD_INFO = lookup.lookupCreditCard(VISA_CREDIT_CARD_ACCOUNT_ID);
		
		// get the mocks
		mockTransactionDao = (MockTransactionDao) context.getBean("mockTransactionDao");
		mockTransactionDao.reset();
		mockValueLinkDao = (MockValueLinkDao) context.getBean("mockValueLinkDao");
		mockValueLinkDao.reset();
		mockCreditCard = (MockCreditCardBO) context.getBean("mockCreditCard");
		mockCreditCard.reset();
		mockAchBatchRequestDao = (MockAchBatchRequestDao) context.getBean("mockAchBatchRequestDao");
		mockAchBatchRequestDao.reset();

		// inject mocks into bo's
		LedgerBO ledger = (LedgerBO) context.getBean("ledger");
		ledger.setTransactionDao(mockTransactionDao);

		ValueLinkBO valueLink = (ValueLinkBO) context.getBean("valueLink");
		valueLink.setValueLinkDao(mockValueLinkDao);
		//valueLink.setRequestHandler(mockInterimValueLinkRequestHandler);

		AchBO ach = (AchBO) context.getBean("ach");
		ach.setAchBatchRequestDao(mockAchBatchRequestDao);

		WalletBO wallet = (WalletBO) context.getBean("wallet");
		wallet.setCreditCard(mockCreditCard);
	}

	/**
	 * testAdminCreditToBuyer.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminCreditToBuyer() throws SLBusinessServiceException {

		double creditAmount = 500.0d;
		String transactionNote = "Test Note";		

		WalletVO request = walletReqBuilder.adminCreditToBuyer(
			USER_NAME, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), ADMIN_INFO.getSl3AccountNumber(), "", creditAmount);

		wallet.adminCreditToBuyer(request);
		
		assertLedgerEntryCount(4);
		assertCreditEntry(0, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER, creditAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER, creditAmount);
		assertCreditEntry(2, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER_GL, creditAmount);
		assertDebitEntry(3, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_BUYER_GL, creditAmount);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_TRANSFER_SLB_TO_BUYER_REDEEM_SL3, creditAmount);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_TRANSFER_SLB_TO_BUYER_RELOAD_BUYER_V1, creditAmount);

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testAdminCreditToProvider.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminCreditToProvider() throws SLBusinessServiceException {

		double creditAmount = 500.0d;
		String transactionNote = "Test Note";

		WalletVO request = walletReqBuilder.adminCreditToProvider(
			USER_NAME, PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(),
			ADMIN_INFO.getSl3AccountNumber(), transactionNote, creditAmount);

		wallet.adminCreditToProvider(request);
		
		assertLedgerEntryCount(4);
		assertCreditEntry(0, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER, creditAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER, creditAmount);
		assertCreditEntry(2, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER_GL, creditAmount);
		assertDebitEntry(3, CommonConstants.RULE_ID_TRANSFER_FROM_SLA_OPERATIONS_TO_PROVIDER_GL, creditAmount);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_TRANSFER_SLB_TO_PROVIDER_REDEEM_SL3, creditAmount);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_TRANSFER_SLB_TO_PROVIDER_RELOAD_PROVIDER_V1, creditAmount);

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testAdminDebitFromBuyer.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminDebitFromBuyer() throws SLBusinessServiceException {

		double debitAmount = 500.0d;
		String transactionNote = "Test Note";

		WalletVO request = walletReqBuilder.adminDebitFromBuyer(
			USER_NAME, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(),
			ADMIN_INFO.getSl3AccountNumber(), transactionNote, debitAmount);

		wallet.adminDebitFromBuyer(request);
		
		assertLedgerEntryCount(4);
		assertCreditEntry(0, CommonConstants.RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS, debitAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS, debitAmount);
		assertCreditEntry(2, CommonConstants.RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS_GL, debitAmount);
		assertDebitEntry(3, CommonConstants.RULE_ID_TRANSFER_FROM_BUYER_TO_SLA_OPERATIONS_GL, debitAmount);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_TRANSFER_SLB_FROM_BUYER_REDEEM_BUYER_V1, debitAmount);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_TRANSFER_SLB_FROM_BUYER_RELOAD_SL3, debitAmount);


		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testAdminDebitFromProvider.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminDebitFromProvider() throws SLBusinessServiceException {

		double debitAmount = 500.0d;
		String transactionNote = "Test Note";

		WalletVO request = walletReqBuilder.adminDebitFromProvider(
			USER_NAME, PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(),
			ADMIN_INFO.getSl3AccountNumber(), transactionNote, debitAmount);

		wallet.adminDebitFromProvider(request);
		
		assertLedgerEntryCount(4);
		assertCreditEntry(0, CommonConstants.RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS, debitAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS, debitAmount);
		assertCreditEntry(2, CommonConstants.RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS_GL, debitAmount);
		assertDebitEntry(3, CommonConstants.RULE_ID_TRANSFER_FROM_PROVIDER_TO_SLA_OPERATIONS_GL, debitAmount);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_TRANSFER_SLB_FROM_PROVIDER_REDEEM_PROVIDER_V1, debitAmount);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_TRANSFER_SLB_FROM_PROVIDER_RELOAD_SL3, debitAmount);


		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testCancelServiceOrder.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testCancelServiceOrder() throws SLBusinessServiceException {

		String transactionNote = "Test Note";

		double laborSpendLimit = 300.00d;
		double partsSpendLimit = 600.00d;
		double addOnTotal = 90.00d;
		double cancellationPenalty = 15.00d;

		double totalSpendLimit = (laborSpendLimit + partsSpendLimit + addOnTotal);

		WalletVO request = walletReqBuilder.cancelServiceOrder(
			USER_NAME, BUYER_ID, BANK_ACCOUNT_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(), 
			PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(), 
			SERVICE_ORDER_ID, CommonConstants.FUNDING_TYPE_PRE_FUNDED, 
			transactionNote, laborSpendLimit, partsSpendLimit, addOnTotal, cancellationPenalty);

		wallet.cancelServiceOrder(request);
		
		assertLedgerEntryCount(6);
		assertCreditEntry(0, CommonConstants.RULE_ID_RELEASE_PENALITY_PAYMENT, cancellationPenalty);
		assertDebitEntry(1, CommonConstants.RULE_ID_RELEASE_PENALITY_PAYMENT, cancellationPenalty);
		assertCreditEntry(2, CommonConstants.RULE_ID_SHC_RELEASE_PENALITY_PAYMENT, cancellationPenalty);
		assertDebitEntry(3, CommonConstants.RULE_ID_SHC_RELEASE_PENALITY_PAYMENT, cancellationPenalty);
		assertCreditEntry(4, CommonConstants.RULE_ID_RETRUN_SO_FUNDING, (totalSpendLimit - cancellationPenalty));
		assertDebitEntry(5, CommonConstants.RULE_ID_RETRUN_SO_FUNDING, (totalSpendLimit - cancellationPenalty));

		assertValueLinkEntryCount(3);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_CANCEL_SO_PENALTY_REDEEM_BUYER_V2, totalSpendLimit);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_CANCEL_SO_PENALTY_RELOAD_PROVIDER_V1, cancellationPenalty);
		assertValueLinkEntry(2, CommonConstants.RULE_ID_CANCEL_SO_PENALTY_RELOAD_BUYER_V1, (totalSpendLimit - cancellationPenalty));

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testCancelServiceOrderWithoutPenalty.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testCancelServiceOrderWithoutPenalty() throws SLBusinessServiceException {

		String transactionNote = "Test Note";

		double laborSpendLimit = 300.00d;
		double partsSpendLimit = 600.00d;
		double addOnTotal = 90.00d;

		double totalSpendLimit = (laborSpendLimit + partsSpendLimit + addOnTotal);

		WalletVO request = walletReqBuilder.cancelServiceOrderWithoutPenalty(
			USER_NAME, BUYER_ID, BANK_ACCOUNT_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, CommonConstants.FUNDING_TYPE_PRE_FUNDED, transactionNote, 
			laborSpendLimit, partsSpendLimit, addOnTotal);

		wallet.cancelServiceOrderWithoutPenalty(request);
		
		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_REFUND_ESCROW_CANCEL_SO_WO_PENALTY, totalSpendLimit);
		assertDebitEntry(1, CommonConstants.RULE_ID_REFUND_ESCROW_CANCEL_SO_WO_PENALTY, totalSpendLimit);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_CANCEL_SO_WO_PENALTY_REDEEM_BUYER_V2, totalSpendLimit);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_CANCEL_SO_WO_PENALTY_RELOAD_BUYER_V1, totalSpendLimit);

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testCloseServiceOrder.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testCloseServiceOrder() throws SLBusinessServiceException {

		String transactionNote = "Test Note";

		double laborSpendLimit = 300.00d;
		double partsSpendLimit = 600.00d;
		double laborCost = 215.00d;
		double partsCost = 475.00d;
		double addOnTotal = 90.00d;
		double serviceFeePercentage = 0.10d;

		double spendLimit = laborSpendLimit + partsSpendLimit + addOnTotal;
		double finalCost = laborCost + partsCost + addOnTotal;
		double serviceFee = ((finalCost) * serviceFeePercentage);

		WalletVO request = walletReqBuilder.closeServiceOrder(
			USER_NAME, BUYER_ID, BANK_ACCOUNT_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(), 
			PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(), 
			ADMIN_INFO.getSl1AccountNumber(),
			SERVICE_ORDER_ID, CommonConstants.FUNDING_TYPE_PRE_FUNDED, transactionNote,
			laborSpendLimit, partsSpendLimit, laborCost, partsCost, addOnTotal, serviceFeePercentage);

		wallet.closeServiceOrder(request);
		
		assertLedgerEntryCount(12);
		assertCreditEntry(0, CommonConstants.RULE_ID_DECLARE_FINAL_PRICE, finalCost);
		assertDebitEntry(1, CommonConstants.RULE_ID_DECLARE_FINAL_PRICE, finalCost);
		assertCreditEntry(2, CommonConstants.RULE_ID_APPLY_SERVICE_FEE, serviceFee);
		assertDebitEntry(3, CommonConstants.RULE_ID_APPLY_SERVICE_FEE, serviceFee);
		assertCreditEntry(4, CommonConstants.RULE_ID_SERVICE_FEE_RECEIVABLE, serviceFee);
		assertDebitEntry(5, CommonConstants.RULE_ID_SERVICE_FEE_RECEIVABLE, serviceFee);
		assertCreditEntry(6, CommonConstants.RULE_ID_RELEASE_SO_PAYMENT, (finalCost - serviceFee));
		assertDebitEntry(7, CommonConstants.RULE_ID_RELEASE_SO_PAYMENT, (finalCost - serviceFee));
		assertCreditEntry(8, CommonConstants.RULE_ID_PROVIDER_PAYMENT_PAYABLE, (finalCost - serviceFee));
		assertDebitEntry(9, CommonConstants.RULE_ID_PROVIDER_PAYMENT_PAYABLE, (finalCost - serviceFee));
		assertCreditEntry(10, CommonConstants.RULE_ID_REFUND_ESCROW, (spendLimit - finalCost));
		assertDebitEntry(11, CommonConstants.RULE_ID_REFUND_ESCROW, (spendLimit - finalCost));

		assertValueLinkEntryCount(4);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_RELEASE_SO_PAYMENT_REDEEM_BUYER_V2, spendLimit);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_RELEASE_SO_PAYMENT_RELOAD_SL1, serviceFee);
		assertValueLinkEntry(2, CommonConstants.RULE_ID_RELEASE_SO_PAYMENT_RELOAD_PROVIDER_V1, (finalCost - serviceFee));
		assertValueLinkEntry(3, CommonConstants.RULE_ID_RELEASE_SO_PAYMENT_RELOAD_BUYER_V1, (spendLimit - finalCost));

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testDecreaseProjectSpendLimit.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDecreaseProjectSpendLimit() throws SLBusinessServiceException {

		double decreaseAmount = 90.00d;

		WalletVO request = walletReqBuilder.decreaseProjectSpendLimit(
			USER_NAME, BANK_ACCOUNT_ID, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, CommonConstants.FUNDING_TYPE_PRE_FUNDED, "", decreaseAmount);

		wallet.decreaseProjectSpendLimit(request);
		
		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_VIRTUAL_REFUND_ESCROW_DECREASE_SPEND_LIMIT, decreaseAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_VIRTUAL_REFUND_ESCROW_DECREASE_SPEND_LIMIT, decreaseAmount);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_DECREASE_ESCROW_REDEEM_BUYER_V2, decreaseAmount);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_DECREASE_ESCROW_RELOAD_BUYER_V1, decreaseAmount);

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testDepositBuyerFundsWithCash.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDepositBuyerFundsWithCash() throws SLBusinessServiceException {

		double depositAmount = 150.00d;

		WalletVO request = walletReqBuilder.depositBuyerFundsWithCash(
			USER_NAME, BANK_ACCOUNT_ID, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, null, depositAmount);

		WalletResponseVO response = wallet.depositBuyerFundsWithCash(request);
		
		Assert.assertFalse(response.isError());

		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_DEPOSIT_CASH, depositAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_DEPOSIT_CASH, depositAmount);

		// no VL messages are sent until funds are confirmed
		assertValueLinkEntryCount(0);

		assertValueLinkEntryCount(0);

		assertCreditCardCount(0);

		// confirm a single entry into the ach queue
		assertAchEntryCount(1);
		assertAchEntryInsert(0, CommonConstants.LEDGER_ENTITY_TYPE_BUYER,
			CommonConstants.ENTRY_TYPE_DEBIT, 
			CommonConstants.TRANSACTION_TYPE_ID_DEPOSIT_CASH,
			depositAmount);
	}
	
	@Test
	public void testDepositBuyerFundsWithCashFailNoAccount() throws SLBusinessServiceException {

		double depositAmount = 150.00d;

		WalletVO request = walletReqBuilder.depositBuyerFundsWithCash(
			USER_NAME, 0L, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, null, depositAmount);
	
		WalletResponseVO response = wallet.depositBuyerFundsWithCash(request);
		
		Assert.assertTrue(response.isError());
		
		Assert.assertEquals("Unable to deposit funds due invalid account selection", response.getErrorMessages().get(0));
	}

	/**
	 * testDepositBuyerFundsWithInstantACH.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDepositBuyerFundsWithInstantACH() throws SLBusinessServiceException {

		double depositAmount = 150.00d;

		WalletVO request = walletReqBuilder.depositBuyerFundsWithInstantACH(
			USER_NAME, BANK_ACCOUNT_ID, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, null, depositAmount);

		wallet.depositBuyerFundsWithInstantACH(request);
		
		// confirm four ledger entries (two debit/credit pairs)
		assertLedgerEntryCount(4);
		assertCreditEntry(0, CommonConstants.RULE_ID_INSTANT_ACH_DEPOSIT, depositAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_INSTANT_ACH_DEPOSIT, depositAmount);
		assertCreditEntry(2, CommonConstants.RULE_ID_SHC_PREPAID_INSTANT_ACH, depositAmount);
		assertDebitEntry(3, CommonConstants.RULE_ID_SHC_PREPAID_INSTANT_ACH, depositAmount);

		// confirm one reload message was sent
		assertValueLinkEntryCount(1);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_ACH_ACTIVATE_RELOAD_BUYER_V1, depositAmount);

		// confirm no credit card auth occurred
		assertCreditCardCount(0);

		// confirm a single entry into the ach queue
		assertAchEntryCount(1);
		assertAchEntryInsert(0, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, CommonConstants.ENTRY_TYPE_DEBIT, CommonConstants.TRANSACTION_TYPE_ID_INSTANT_ACH_DEPOSIT, depositAmount);

	}

	/**
	 * testDespositBuyerFundsWithCreditCard.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDespositBuyerFundsWithCreditCard() throws SLBusinessServiceException {

		double depositAmount = 150.00d;

		// set the mock to auth the card
		mockCreditCard.reset();
		mockCreditCard.setAuthorizing(true);
		String authCode = generateAuthCode();
		mockCreditCard.setAuthorizationCode(authCode);

		SLCreditCardVO creditCardVO = new SLCreditCardVO();
		BeanUtils.copyProperties(CREDIT_CARD_INFO, creditCardVO);
		
		WalletVO request = walletReqBuilder.depositBuyerFundsWithCreditCard(
			USER_NAME, VISA_CREDIT_CARD_ACCOUNT_ID, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, null, depositAmount);

		wallet.depositBuyerFundsWithCreditCard(request);
		
		// confirm two ledger entries (debit/credit)
		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_DEPOSIT_CASH_CC_SEARS, depositAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_DEPOSIT_CASH_CC_SEARS, depositAmount);

		// confirm a reload V1 message
		assertValueLinkEntryCount(1);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_BUYER_DEPOSIT_RELOAD_V1, depositAmount);

		// confirm the credit card auth occurred
		assertCreditCardCount(1);
		assertCreditCard(0, authCode);

		// confirm a single entry into the ach queue
		assertAchEntryCount(1);
		assertAchEntryInsert(0, CommonConstants.LEDGER_ENTITY_TYPE_BUYER, CommonConstants.ENTRY_TYPE_DEBIT, CommonConstants.TRANSACTION_TYPE_ID_CREDIT_DEPOSIT, depositAmount);
	}

	/**
	 * testDespositBuyerFundsWithCreditCardNoAuth.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDespositBuyerFundsWithCreditCardNoAuth() throws SLBusinessServiceException {

		double depositAmount = 150.00d;
		
		// set the mock to auth the card
		mockCreditCard.reset();
		mockCreditCard.setAuthorizing(false);
		String authCode = generateAuthCode();
		mockCreditCard.setAuthorizationCode(authCode);

		SLCreditCardVO creditCardVO = new SLCreditCardVO();
		BeanUtils.copyProperties(CREDIT_CARD_INFO, creditCardVO);

		WalletVO request = walletReqBuilder.depositBuyerFundsWithCreditCard(
			USER_NAME, VISA_CREDIT_CARD_ACCOUNT_ID, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, null, depositAmount);

		wallet.depositBuyerFundsWithCreditCard(request);
		
		// confirm zero ledger entries (debit/credit)
		assertLedgerEntryCount(0);

		// confirm zero messages
		assertValueLinkEntryCount(0);

		assertValueLinkEntryCount(0);

		// confirm the credit card auth occurred
		assertCreditCardCount(1);
		assertCreditCard(0, authCode);

		// confirm zero ach entries
		assertAchEntryCount(0);
	}

	/**
	 * testIncreaseProjectSpendLimit.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testIncreaseProjectSpendLimit() throws SLBusinessServiceException {

		double increaseAmount = 90.00d;
		double upsellAmount = 0d;

		WalletVO request = walletReqBuilder.increaseProjectSpendLimit(
			USER_NAME, BANK_ACCOUNT_ID, BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, CommonConstants.FUNDING_TYPE_PRE_FUNDED, null, increaseAmount, upsellAmount);

		wallet.increaseProjectSpendLimit(request);
		
		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_INCREASE_SPEND_LIMIT, increaseAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_INCREASE_SPEND_LIMIT, increaseAmount);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_INCREASE_ESCROW_REDEEM_BUYER_V1, increaseAmount);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_INCREASE_ESCROW_RELOAD_BUYER_V2, increaseAmount);

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testPostServiceOrder.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testPostServiceOrder() throws SLBusinessServiceException {

		double laborSpendLimit = 90.00d;
		double partsSpendLimit = 190.00d;
		double postingFee = 10.00d;

		double reserveAmount = (laborSpendLimit + partsSpendLimit);

		WalletVO request = walletReqBuilder.postServiceOrder(
			USER_NAME, VISA_CREDIT_CARD_ACCOUNT_ID, 
			BUYER_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			ADMIN_INFO.getSl1AccountNumber(), 
			SERVICE_ORDER_ID, CommonConstants.FUNDING_TYPE_PRE_FUNDED, 
			null, 
			laborSpendLimit, partsSpendLimit, postingFee, 0.0d);

		wallet.postServiceOrder(request);
		
		assertLedgerEntryCount(6);
		assertCreditEntry(0, CommonConstants.RULE_ID_APPLY_POSTING_FEE, postingFee);
		assertDebitEntry(1, CommonConstants.RULE_ID_APPLY_POSTING_FEE, postingFee);
		assertCreditEntry(2, CommonConstants.RULE_ID_POSTING_FEE_RECEIVABLE, postingFee);
		assertDebitEntry(3, CommonConstants.RULE_ID_POSTING_FEE_RECEIVABLE, postingFee);
		assertCreditEntry(4, CommonConstants.RULE_ID_RESERVE_SO_FUNDING, reserveAmount);
		assertDebitEntry(5, CommonConstants.RULE_ID_RESERVE_SO_FUNDING, reserveAmount);

		assertValueLinkEntryCount(3);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_POST_SO_REDEEM_BUYER_V1, (reserveAmount + postingFee));
		assertValueLinkEntry(1, CommonConstants.RULE_ID_POST_SO_RELOAD_SL1, postingFee);
		assertValueLinkEntry(2, CommonConstants.RULE_ID_POST_SO_RELOAD_BUYER_V2, reserveAmount);

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testVoidServiceOrder.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testVoidServiceOrder() throws SLBusinessServiceException {

		String transactionNote = "Test Note";

		double laborSpendLimit = 300.00d;
		double partsSpendLimit = 600.00d;
		double addOnTotal = 90.00d;

		double totalSpendLimit = (laborSpendLimit + partsSpendLimit + addOnTotal);

		WalletVO request = walletReqBuilder.voidServiceOrder(
			USER_NAME, BUYER_ID, BANK_ACCOUNT_ID, BUYER_INFO.getBuyerState(), BUYER_INFO.getBuyerV1AccountNumber(), BUYER_INFO.getBuyerV2AccountNumber(),
			SERVICE_ORDER_ID, CommonConstants.FUNDING_TYPE_PRE_FUNDED, transactionNote, laborSpendLimit, partsSpendLimit, addOnTotal);

		wallet.voidServiceOrder(request);
		
		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_REFUND_ESCROW_VOID_SO, totalSpendLimit);
		assertDebitEntry(1, CommonConstants.RULE_ID_REFUND_ESCROW_VOID_SO, totalSpendLimit);

		assertValueLinkEntryCount(2);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_VOID_SO_REDEEM_BUYER_V2, totalSpendLimit);
		assertValueLinkEntry(1, CommonConstants.RULE_ID_VOID_SO_RELOAD_BUYER_V1, totalSpendLimit);

		assertCreditCardCount(0);

		assertAchEntryCount(0);
	}

	/**
	 * testWithdrawProviderFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testWithdrawProviderFunds() throws SLBusinessServiceException {

		double withdrawalAmount = 250.0d;

		this.mockTransactionDao.setAvailableBalance(300.00d);
		this.mockTransactionDao.setValueLinkBalance(300.00d);

		WalletVO request = walletReqBuilder.withdrawProviderFunds(
			USER_NAME, BANK_ACCOUNT_ID, 
			PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(),
			withdrawalAmount, null, null);

		wallet.withdrawProviderFunds(request);
		
		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_WITHDRAWAL_CASH_PROVIDER, withdrawalAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_WITHDRAWAL_CASH_PROVIDER, withdrawalAmount);

		assertValueLinkEntryCount(1);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_PROVIDER_WITHDRAW_FUNDS_REDEEM_PROVIDER_V1, withdrawalAmount);

		// confirm a single entry into the ach queue
		assertAchEntryCount(1);
		assertAchEntryInsert(0, CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, CommonConstants.ENTRY_TYPE_CREDIT, CommonConstants.TRANSACTION_TYPE_ID_WITHDRAW_CASH,
			withdrawalAmount);

		assertCreditCardCount(0);
	}

	@Test
	public void testWithdrawProviderFundsFailBalance() throws SLBusinessServiceException {

		double withdrawalAmount = 250.0d;

		this.mockTransactionDao.setAvailableBalance(100.00d);
		this.mockTransactionDao.setValueLinkBalance(100.00d);
		
		WalletVO request = walletReqBuilder.withdrawProviderFunds(
			USER_NAME, BANK_ACCOUNT_ID, PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(),
			withdrawalAmount, null, null);
		
		WalletResponseVO response = wallet.withdrawProviderFunds(request);
		
		Assert.assertTrue(response.isError());
		
		Assert.assertEquals("Unable to withdraw funds - Withdrawal must be no greater than account balance",response.getErrorMessages().get(0));
		Assert.assertEquals("We are unable to process your withdrawal request at this time which may be related to a technical connectivity delay.  Please retry your withdrawal request in 30 minutes.  If this issue persists please contact ServiceLive support at 888-549-0640 and report you were unable to withdraw your funds on the second attempt after waiting 30 minutes.",
			response.getErrorMessages().get(1));
	}
	
	@Test
	public void testWithdrawProviderFundsFailLimit() throws SLBusinessServiceException {

		double withdrawalAmount = 7500.0d;

		this.mockTransactionDao.setAvailableBalance(10000.00d);
		this.mockTransactionDao.setValueLinkBalance(10000.00d);
		
		WalletVO request = walletReqBuilder.withdrawProviderFunds(
			USER_NAME, BANK_ACCOUNT_ID, PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(),
			withdrawalAmount, null, null);
		
		WalletResponseVO response = wallet.withdrawProviderFunds(request);
		
		Assert.assertTrue(response.isError());
		
		Assert.assertEquals("Unable to withdraw funds due to 5000.0$ daily limit", response.getErrorMessages().get(0));		
	}
	
	@Test
	public void testWithdrawProviderFundsFailNoAccount() throws SLBusinessServiceException {

		double withdrawalAmount = 250.0d;

		this.mockTransactionDao.setAvailableBalance(300.00d);
		this.mockTransactionDao.setValueLinkBalance(300.00d);
		
		WalletVO request = walletReqBuilder.withdrawProviderFunds(
			USER_NAME, 0L, PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(),
			withdrawalAmount, null, null );
		
		WalletResponseVO response = wallet.withdrawProviderFunds(request);
		
		Assert.assertTrue(response.isError());
		
		Assert.assertEquals("Unable to withdraw funds due invalid account selection",response.getErrorMessages().get(0));	
	}
	
	/**
	 * testWithdrawProviderFundsReversal.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testWithdrawProviderFundsReversal() throws SLBusinessServiceException {

		double withdrawalAmount = 250.0d;

		WalletVO request = walletReqBuilder.withdrawProviderFundsReversal(
			USER_NAME, 12345L, PROVIDER_ID, PROVIDER_INFO.getProviderState(), PROVIDER_INFO.getProviderV1AccountNumber(), withdrawalAmount);

		
		wallet.withdrawProviderFundsReversal(request);
		
		assertLedgerEntryCount(2);
		assertCreditEntry(0, CommonConstants.RULE_ID_WITHDRAWAL_CASH_PROVIDER_REVERSAL, withdrawalAmount);
		assertDebitEntry(1, CommonConstants.RULE_ID_WITHDRAWAL_CASH_PROVIDER_REVERSAL, withdrawalAmount);

		assertValueLinkEntryCount(1);
		assertValueLinkEntry(0, CommonConstants.RULE_ID_PROVIDER_WITHDRAW_REVERSAL_RELOAD_PROVIDER_V1, withdrawalAmount);

		assertAchEntryCount(0);

		assertCreditCardCount(0);
	}
	
	/**
	 * testDepositOperationFund.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDepositOperationFund() throws SLBusinessServiceException {
		
		double depositAmount = 250.0d;
		
		WalletVO request = walletReqBuilder.depositOperationFund(
				USER_NAME, 0L, "Test deposit to Operation", depositAmount,0L);
		wallet.depositOperationFunds(request);
		
		assertLedgerEntryCount(2);
		
		assertAchEntryCount(1);
		assertValueLinkEntryCount(0);
		assertCreditCardCount(0);
		assertValueLinkEntryCount(0);

	}

	/**
	 * testWidthrawOperationFund.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testWidthrawOperationFund() throws SLBusinessServiceException {
		
		double widthdrawAmount = 250.0d;
		
		WalletVO request = walletReqBuilder.withdrawOperationFund(
				USER_NAME, 0L, "Test deposit to Operation", widthdrawAmount, SL3_ACCOUNT_NUMBER);
		
		wallet.withdrawOperationFunds(request);
		
		assertLedgerEntryCount(2);
		
		assertAchEntryCount(1);
		assertValueLinkEntryCount(0);
		assertCreditCardCount(0);
		assertValueLinkEntryCount(0);

	}
}
