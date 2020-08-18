package com.servicelive.wallet.valuelink;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.requestbuilder.ValueLinkRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;
import com.servicelive.wallet.valuelink.mocks.MockValueLinkDao;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkRulesBOTest.
 */
public class ValueLinkRulesBOTest {

	/** _context. */
	private static ApplicationContext _context = null;

	/**
	 * getAppContext.
	 * 
	 * @return ApplicationContext
	 * 
	 * @throws BeansException 
	 */
	private static ApplicationContext getAppContext() throws BeansException {

		if (_context == null) {
			_context = new ClassPathXmlApplicationContext("com/servicelive/wallet/valuelink/testValueLinkApplicationContext.xml");
		}
		return _context;
	}

	/** BUYER_ID. */
	private final Long BUYER_ID = 725L;

	/** BUYER_STATE. */
	private final String BUYER_STATE = "MI";

	/** BUYER_V1_ACCOUNT_NUMBER. */
	private final Long BUYER_V1_ACCOUNT_NUMBER = 7777008242729343L;

	/** BUYER_V2_ACCOUNT_NUMBER. */
	private final Long BUYER_V2_ACCOUNT_NUMBER = 7777008247256835L;

	/** mockValueLinkDao. */
	private MockValueLinkDao mockValueLinkDao;

	/** PROVIDER_ID. */
	private final Long PROVIDER_ID = 5630L;

	/** PROVIDER_STATE. */
	private final String PROVIDER_STATE = "OH";

	/** PROVIDER_V1_ACCOUNT_NUMBER. */
	private final Long PROVIDER_V1_ACCOUNT_NUMBER = 7777007860532586L;

	/** SERVICE_ORDER_ID. */
	private final String SERVICE_ORDER_ID = "526-5046-4982-15";

	/** SL1_ACCOUNT_NUMBER. */
	private final Long SL1_ACCOUNT_NUMBER = 7777008250310755L;

	/** SL3_ACCOUNT_NUMBER. */
	private final Long SL3_ACCOUNT_NUMBER = 7777009239100382L;

	/** valueLink. */
	protected IValueLinkBO valueLink = null;

	/** valueLinkRequestBuilder. */
	protected ValueLinkRequestBuilder valueLinkRequestBuilder = null;

	/**
	 * assertActivationMessage.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertActivationMessage(ValueLinkEntryVO entry) {

		Assert.assertEquals(CommonConstants.MESSAGE_TYPE_ACTIVATION, (long) entry.getMessageTypeId());
		Assert.assertEquals(CommonConstants.ACTIVATION_RELOAD_REQUEST, entry.getMessageIdentifier());
		Assert.assertEquals(CommonConstants.MESSAGE_DESC_ID_ACTIVATION_RELOAD, (long) entry.getMessageDescId());
	}

	/**
	 * assertBuyer.
	 * 
	 * @param entry 
	 * @param buyerId 
	 * 
	 * @return void
	 */
	private void assertBuyer(ValueLinkEntryVO entry, Long buyerId) {

		Assert.assertEquals(CommonConstants.LEDGER_ENTITY_TYPE_BUYER, (long) entry.getEntityTypeId());
		Assert.assertEquals(buyerId, entry.getLedgerEntityId());
	}

	/**
	 * assertBuyerEscrow.
	 * 
	 * @param entry 
	 * @param buyerId 
	 * 
	 * @return void
	 */
	private void assertBuyerEscrow(ValueLinkEntryVO entry, Long buyerId) {

		Assert.assertEquals(CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_ESCROW, (long) entry.getEntityTypeId());
		Assert.assertEquals(buyerId, entry.getLedgerEntityId());
	}

	/**
	 * assertCreateBuyerV1.
	 * 
	 * @param entry 
	 * @param depositAmount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreateBuyerV1(ValueLinkEntryVO entry, Double depositAmount, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertBuyer(entry, BUYER_ID);
		assertOrigin(entry, null, BUYER_STATE);
		assertDestination(entry, null, BUYER_STATE);
		assertCredit(entry, depositAmount);
		assertNewBuyerV1PromoCode(entry);
		assertActivationMessage(entry);
	}

	/**
	 * assertCreateBuyerV2.
	 * 
	 * @param entry 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreateBuyerV2(ValueLinkEntryVO entry, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertBuyerEscrow(entry, BUYER_ID);
		assertOrigin(entry, null, BUYER_STATE);
		assertDestination(entry, null, BUYER_STATE);
		assertCredit(entry, 0.0d);
		assertNewBuyerV2PromoCode(entry);
		assertActivationMessage(entry);
	}

	/**
	 * assertCredit.
	 * 
	 * @param entry 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void assertCredit(ValueLinkEntryVO entry, Double amount) {

		Assert.assertEquals(amount, entry.getTransAmount());
		Assert.assertEquals(CommonConstants.ENTRY_TYPE_CREDIT, (long) entry.getEntryTypeId());
	}

	/**
	 * assertCreditBuyerV1.
	 * 
	 * @param entry 
	 * @param buyerTotal 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditBuyerV1(ValueLinkEntryVO entry, Double buyerTotal, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertBuyer(entry, BUYER_ID);
		assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertCredit(entry, buyerTotal);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditBuyerV1FromSL3.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditBuyerV1FromSL3(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertBuyer(entry, BUYER_ID);
		assertOrigin(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertDestination(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditBuyerV1FromV2.
	 * 
	 * @param entry 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditBuyerV1FromV2(ValueLinkEntryVO entry, Double amount, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertBuyer(entry, BUYER_ID);
		assertOrigin(entry, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditBuyerV2FromV1.
	 * 
	 * @param entry 
	 * @param orderAmount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditBuyerV2FromV1(ValueLinkEntryVO entry, Double orderAmount, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertBuyerEscrow(entry, BUYER_ID);
		assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE);
		assertCredit(entry, orderAmount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditProviderV1.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditProviderV1(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertProvider(entry, PROVIDER_ID);
		assertOrigin(entry, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE);
		assertDestination(entry, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE);
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditProviderV1FromBuyerV2.
	 * 
	 * @param entry 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditProviderV1FromBuyerV2(ValueLinkEntryVO entry, Double amount, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertProvider(entry, PROVIDER_ID);
		assertOrigin(entry, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE);
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditProviderV1FromSL3.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditProviderV1FromSL3(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertProvider(entry, PROVIDER_ID);
		assertOrigin(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertDestination(entry, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE);
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditSL1FromBuyerV1.
	 * 
	 * @param entry 
	 * @param buyerTotal 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditSL1FromBuyerV1(ValueLinkEntryVO entry, Double buyerTotal, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertSLMain(entry);
		assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, SL1_ACCOUNT_NUMBER, "IL");
		assertCredit(entry, buyerTotal);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditSL1FromBuyerV2.
	 * 
	 * @param entry 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditSL1FromBuyerV2(ValueLinkEntryVO entry, Double amount, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertSLMain(entry);
		assertOrigin(entry, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, SL1_ACCOUNT_NUMBER, "IL");
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditSL3.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditSL3(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertSLOperations(entry);
		assertOrigin(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertDestination(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditSL3FromBuyerV1.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param buyerTotal 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditSL3FromBuyerV1(ValueLinkEntryVO entry, String serviceOrderId, Double buyerTotal, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertSLOperations(entry);
		assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertCredit(entry, buyerTotal);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertCreditSL3FromProviderV1.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertCreditSL3FromProviderV1(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertSLOperations(entry);
		assertOrigin(entry, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE);
		assertDestination(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertCredit(entry, amount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * assertDebit.
	 * 
	 * @param entry 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void assertDebit(ValueLinkEntryVO entry, Double amount) {

		Assert.assertEquals(amount, entry.getTransAmount());
		Assert.assertEquals(CommonConstants.ENTRY_TYPE_DEBIT, (long) entry.getEntryTypeId());
	}

	/**
	 * assertDebitBuyerV1.
	 * 
	 * @param entry 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertDebitBuyerV1(ValueLinkEntryVO entry, Double amount, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertBuyer(entry, BUYER_ID);
		assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDebit(entry, amount);
		assertNoPromoCode(entry);
		assertRedeemMessage(entry);
	}

	/**
	 * assertDebitBuyerV1.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertDebitBuyerV1(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertBuyer(entry, BUYER_ID);
		assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDebit(entry, amount);
		assertNoPromoCode(entry);
		assertRedeemMessage(entry);
	}

	/**
	 * assertDebitBuyerV2.
	 * 
	 * @param entry 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertDebitBuyerV2(ValueLinkEntryVO entry, Double amount, long sortOrder) {

		assertEntry(entry, SERVICE_ORDER_ID, sortOrder);
		assertBuyerEscrow(entry, BUYER_ID);
		assertOrigin(entry, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE);
		assertDebit(entry, amount);
		assertNoPromoCode(entry);
		assertRedeemMessage(entry);
	}

	/**
	 * assertDebitProviderV1.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertDebitProviderV1(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertProvider(entry, PROVIDER_ID);
		assertOrigin(entry, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE);
		assertDestination(entry, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE);
		assertDebit(entry, amount);
		assertNoPromoCode(entry);
		assertRedeemMessage(entry);
	}

	/**
	 * assertDebitSL1.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertDebitSL1(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertSLMain(entry);
		assertOrigin(entry, SL1_ACCOUNT_NUMBER, "IL");
		assertDestination(entry, SL1_ACCOUNT_NUMBER, "IL");
		assertDebit(entry, amount);
		assertNoPromoCode(entry);
		assertRedeemMessage(entry);
	}

	/**
	 * assertDebitSL3.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param amount 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertDebitSL3(ValueLinkEntryVO entry, String serviceOrderId, Double amount, long sortOrder) {

		assertEntry(entry, serviceOrderId, sortOrder);
		assertSLOperations(entry);
		assertOrigin(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertDestination(entry, SL3_ACCOUNT_NUMBER, "IL");
		assertDebit(entry, amount);
		assertNoPromoCode(entry);
		assertRedeemMessage(entry);
	}

	/**
	 * assertDestination.
	 * 
	 * @param entry 
	 * @param accountNumber 
	 * @param state 
	 * 
	 * @return void
	 */
	private void assertDestination(ValueLinkEntryVO entry, Long accountNumber, String state) {

		if (accountNumber == null) {
			Assert.assertNull(entry.getDestinationPan());
			Assert.assertNull(entry.getPrimaryAccountNumber());
		} else {
			Assert.assertEquals(accountNumber, entry.getDestinationPan());
			Assert.assertEquals(accountNumber, entry.getPrimaryAccountNumber());
		}
		Assert.assertEquals(state, entry.getDestStateCode());
	}

	/*
	 * ASSERTIONS
	 */
	/**
	 * assertEntry.
	 * 
	 * @param entry 
	 * @param serviceOrderId 
	 * @param sortOrder 
	 * 
	 * @return void
	 */
	private void assertEntry(ValueLinkEntryVO entry, String serviceOrderId, Long sortOrder) {

		if (serviceOrderId == null) {
			Assert.assertNull(entry.getSoId());
		} else {
			Assert.assertEquals(serviceOrderId, entry.getSoId());
		}
		Assert.assertEquals(serviceOrderId, entry.getReferenceNo());
		Assert.assertEquals(sortOrder, entry.getSortOrder());
	}

	/**
	 * assertNewBuyerV1PromoCode.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertNewBuyerV1PromoCode(ValueLinkEntryVO entry) {

		Assert.assertEquals(1, (long) entry.getPromoCodeId());
		Assert.assertEquals("12594", entry.getPromoCode());
	}

	/**
	 * assertNewBuyerV2PromoCode.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertNewBuyerV2PromoCode(ValueLinkEntryVO entry) {

		Assert.assertEquals(2, (long) entry.getPromoCodeId());
		Assert.assertEquals("12595", entry.getPromoCode());
	}

	/**
	 * assertNewProviderV1PromoCode.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertNewProviderV1PromoCode(ValueLinkEntryVO entry) {

		Assert.assertEquals(3, (long) entry.getPromoCodeId());
		Assert.assertEquals("12596", entry.getPromoCode());
	}

	/**
	 * assertNoPromoCode.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertNoPromoCode(ValueLinkEntryVO entry) {

		Assert.assertNull(entry.getPromoCode());
	}

	/**
	 * assertOrigin.
	 * 
	 * @param entry 
	 * @param accountNumber 
	 * @param state 
	 * 
	 * @return void
	 */
	private void assertOrigin(ValueLinkEntryVO entry, Long accountNumber, String state) {

		if (accountNumber == null) {
			Assert.assertNull(entry.getOriginatingPan());
		} else {
			Assert.assertEquals(accountNumber, entry.getOriginatingPan());
		}
		Assert.assertEquals(state, entry.getOrigStateCode());
	}

	/**
	 * assertProvider.
	 * 
	 * @param entry 
	 * @param providerId 
	 * 
	 * @return void
	 */
	private void assertProvider(ValueLinkEntryVO entry, Long providerId) {

		Assert.assertEquals(CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, (long) entry.getEntityTypeId());
		Assert.assertEquals(providerId, entry.getLedgerEntityId());
	}

	/**
	 * assertRedeemMessage.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertRedeemMessage(ValueLinkEntryVO entry) {

		Assert.assertEquals(CommonConstants.MESSAGE_TYPE_REDEMPTION, (long) entry.getMessageTypeId());
		Assert.assertEquals(CommonConstants.REDEMPTION_REQUEST, entry.getMessageIdentifier());
		Assert.assertEquals(CommonConstants.MESSAGE_DESC_ID_REDEMPTION, (long) entry.getMessageDescId());
	}

	/**
	 * assertReloadMessage.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertReloadMessage(ValueLinkEntryVO entry) {

		Assert.assertEquals(CommonConstants.MESSAGE_TYPE_RELOAD, (long) entry.getMessageTypeId());
		Assert.assertEquals(CommonConstants.ACTIVATION_RELOAD_REQUEST, entry.getMessageIdentifier());
		Assert.assertEquals(CommonConstants.MESSAGE_DESC_ID_ACTIVATION_RELOAD, (long) entry.getMessageDescId());
	}

	/**
	 * assertSLMain.
	 * s
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertSLMain(ValueLinkEntryVO entry) {

		Assert.assertEquals(CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_MAIN, (long) entry.getEntityTypeId());
		Assert.assertEquals(CommonConstants.ENTITY_ID_SERVICELIVE, (long) entry.getLedgerEntityId());
	}

	/**
	 * assertSLOperations.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	private void assertSLOperations(ValueLinkEntryVO entry) {

		Assert.assertEquals(CommonConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION, (long) entry.getEntityTypeId());
		Assert.assertEquals(CommonConstants.ENTITY_ID_SERVICELIVE_OPERATION, (long) entry.getLedgerEntityId());
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

		valueLink = (IValueLinkBO) context.getBean("valueLink");
		valueLinkRequestBuilder = (ValueLinkRequestBuilder) context.getBean("valueLinkRequestBuilder");

		mockValueLinkDao = (MockValueLinkDao) context.getBean("mockValueLinkDao");
		mockValueLinkDao.reset();

		// inject mocks into bo's

		ValueLinkBO valueLink = (ValueLinkBO) context.getBean("valueLink");
		valueLink.setValueLinkDao(mockValueLinkDao);
	}

	/**
	 * testAdminCreditBuyer.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminCreditBuyer() throws SLBusinessServiceException {

		Double creditAmount = 15.00d;

		ValueLinkVO request = valueLinkRequestBuilder.adminCreditBuyer(BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE, SL3_ACCOUNT_NUMBER, creditAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitSL3(entries.get(0), null, creditAmount, 1);

		assertCreditBuyerV1FromSL3(entries.get(1), null, creditAmount, 2);
	}

	/**
	 * testAdminCreditProvider.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminCreditProvider() throws SLBusinessServiceException {

		Double creditAmount = 45.00d;

		ValueLinkVO request = valueLinkRequestBuilder.adminCreditProvider(PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, SL3_ACCOUNT_NUMBER, creditAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitSL3(entries.get(0), null, creditAmount, 1);

		assertCreditProviderV1FromSL3(entries.get(1), null, creditAmount, 2);

	}

	/**
	 * testAdminDebitBuyer.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminDebitBuyer() throws SLBusinessServiceException {

		Double debitAmount = 17.00d;

		ValueLinkVO request = valueLinkRequestBuilder.adminDebitBuyer(BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE, SL3_ACCOUNT_NUMBER, debitAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitBuyerV1(entries.get(0), null, debitAmount, 1);

		assertCreditSL3FromBuyerV1(entries.get(1), null, debitAmount, 2);
	}

	/**
	 * testAdminDebitProvider.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAdminDebitProvider() throws SLBusinessServiceException {

		Double debitAmount = 75.00d;

		ValueLinkVO request = valueLinkRequestBuilder.adminDebitProvider(PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, SL3_ACCOUNT_NUMBER, debitAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitProviderV1(entries.get(0), null, debitAmount, 1);

		assertCreditSL3FromProviderV1(entries.get(1), null, debitAmount, 2);
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

		Double orderAmount = 275.00d;
		Double cancellationPenalty = 50.00d;

		ValueLinkVO request =
			valueLinkRequestBuilder.cancelServiceOrder(CommonConstants.FUNDING_TYPE_PRE_FUNDED, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER,
				BUYER_STATE, PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, orderAmount, cancellationPenalty);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(3, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditProviderV1FromBuyerV2(entries.get(1), cancellationPenalty, 2);

		assertCreditBuyerV1FromV2(entries.get(2), (orderAmount - cancellationPenalty), 3);

		mockValueLinkDao.reset();

		request =
			valueLinkRequestBuilder.cancelServiceOrder(CommonConstants.SHC_FUNDING_TYPE, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE,
				PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, orderAmount, cancellationPenalty);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO);

		valueLink.sendValueLinkRequest(request);
		entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(4, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditProviderV1FromBuyerV2(entries.get(1), cancellationPenalty, 2);

		assertCreditBuyerV1FromV2(entries.get(2), (orderAmount - cancellationPenalty), 3);

		assertDebitBuyerV1(entries.get(3), SERVICE_ORDER_ID, orderAmount, 4);
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

		Double orderAmount = 275.00d;

		ValueLinkVO request =
			valueLinkRequestBuilder.cancelServiceOrderWithoutPenalty(CommonConstants.FUNDING_TYPE_PRE_FUNDED, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER,
				BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE, orderAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditBuyerV1FromV2(entries.get(1), (orderAmount), 2);

		mockValueLinkDao.reset();

		request =
			valueLinkRequestBuilder.cancelServiceOrderWithoutPenalty(CommonConstants.SHC_FUNDING_TYPE, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER,
				BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE, orderAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_CANCEL_SO_WO_PENALTY);

		valueLink.sendValueLinkRequest(request);
		entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(3, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditBuyerV1FromV2(entries.get(1), orderAmount, 2);

		assertDebitBuyerV1(entries.get(2), SERVICE_ORDER_ID, orderAmount, 3);
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

		Double orderAmount = 275.00d;
		Double finalPrice = 250.00d;
		Double serviceFeeAmount = 25.00d;

		ValueLinkVO request =
			valueLinkRequestBuilder.closeServiceOrder(CommonConstants.FUNDING_TYPE_PRE_FUNDED, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER,
				BUYER_STATE, PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, SL1_ACCOUNT_NUMBER, orderAmount, finalPrice, serviceFeeAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(4, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditSL1FromBuyerV2(entries.get(1), serviceFeeAmount, 2);

		assertCreditProviderV1FromBuyerV2(entries.get(2), finalPrice - serviceFeeAmount, 3);

		assertCreditBuyerV1FromV2(entries.get(3), (orderAmount - finalPrice), 4);

		mockValueLinkDao.reset();

		request =
			valueLinkRequestBuilder.closeServiceOrder(CommonConstants.SHC_FUNDING_TYPE, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE,
				PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, SL1_ACCOUNT_NUMBER, orderAmount, finalPrice, serviceFeeAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT);

		valueLink.sendValueLinkRequest(request);
		entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(5, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditSL1FromBuyerV2(entries.get(1), serviceFeeAmount, 2);

		assertCreditProviderV1FromBuyerV2(entries.get(2), finalPrice - serviceFeeAmount, 3);

		assertCreditBuyerV1FromV2(entries.get(3), (orderAmount - finalPrice), 4);

		assertDebitBuyerV1(entries.get(4), SERVICE_ORDER_ID, (orderAmount - finalPrice), 5);
	}

	/**
	 * testCreateBuyer.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testCreateBuyer() throws SLBusinessServiceException {

		Double depositAmount = 150.0d;

		long[] fundingTypes = new long[] { CommonConstants.FUNDING_TYPE_PRE_FUNDED, CommonConstants.SHC_FUNDING_TYPE };

		for (long fundingType : fundingTypes) {

			mockValueLinkDao.reset();

			ValueLinkVO request = valueLinkRequestBuilder.createBuyer(fundingType, SERVICE_ORDER_ID, BUYER_ID, BUYER_STATE, depositAmount);

			request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_NEW_BUYER);

			valueLink.sendValueLinkRequest(request);

			List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
			Assert.assertEquals(2, entries.size());

			assertCreateBuyerV1(entries.get(0), depositAmount, 1);

			assertCreateBuyerV2(entries.get(1), 2);
		}
	}

	/**
	 * testCreateProvider.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testCreateProvider() throws SLBusinessServiceException {

		Double depositAmount = 0.0d;

		long[] fundingTypes = new long[] { CommonConstants.FUNDING_TYPE_PRE_FUNDED, CommonConstants.SHC_FUNDING_TYPE };

		for (long fundingType : fundingTypes) {

			mockValueLinkDao.reset();

			ValueLinkVO request = valueLinkRequestBuilder.createProvider(fundingType, SERVICE_ORDER_ID, PROVIDER_ID, PROVIDER_STATE, depositAmount);

			request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_NEW_PROVIDER);

			valueLink.sendValueLinkRequest(request);

			List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
			Assert.assertEquals(1, entries.size());

			ValueLinkEntryVO entry = entries.get(0);

			assertEntry(entry, SERVICE_ORDER_ID, 1L);
			assertProvider(entry, PROVIDER_ID);
			assertOrigin(entry, null, PROVIDER_STATE);
			assertDestination(entry, null, PROVIDER_STATE);
			assertCredit(entry, depositAmount);
			assertNewProviderV1PromoCode(entry);
			assertActivationMessage(entry);
		}
	}

	/**
	 * testDecreaseProjectFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDecreaseProjectFunds() throws SLBusinessServiceException {

		Double amount = 350.0d;

		ValueLinkVO request =
			valueLinkRequestBuilder.decreaseProjectSpendLimit(CommonConstants.FUNDING_TYPE_PRE_FUNDED, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER,
				BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE, amount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DECREASE_SO_ESCROW);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitBuyerV2(entries.get(0), amount, 1);

		assertCreditBuyerV1FromV2(entries.get(1), amount, 2);

		mockValueLinkDao.reset();

		request =
			valueLinkRequestBuilder.decreaseProjectSpendLimit(CommonConstants.SHC_FUNDING_TYPE, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER,
				BUYER_STATE, amount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DECREASE_SO_ESCROW);

		valueLink.sendValueLinkRequest(request);
		entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(3, entries.size());

		assertDebitBuyerV2(entries.get(0), amount, 1);

		assertCreditBuyerV1FromV2(entries.get(1), amount, 2);

		assertDebitBuyerV1(entries.get(2), amount, 3);
	}

	/**
	 * testDepositBuyerFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDepositBuyerFunds() throws SLBusinessServiceException {

		Double depositAmount = 250.0d;

		long[] fundingTypes = new long[] { CommonConstants.FUNDING_TYPE_DIRECT_FUNDED, CommonConstants.FUNDING_TYPE_PRE_FUNDED, CommonConstants.SHC_FUNDING_TYPE };

		for (long fundingTypeId : fundingTypes) {
			mockValueLinkDao.reset();

			ValueLinkVO request =
				valueLinkRequestBuilder.depositBuyerFunds(fundingTypeId, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE, depositAmount);

			request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER);

			valueLink.sendValueLinkRequest(request);
			List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();

			Assert.assertEquals(1, entries.size());

			ValueLinkEntryVO entry = entries.get(0);

			assertEntry(entry, SERVICE_ORDER_ID, 1L);
			assertBuyer(entry, BUYER_ID);
			assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
			assertDestination(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
			assertCredit(entry, depositAmount);
			assertNoPromoCode(entry);
			assertReloadMessage(entry);
		}
	}

	/**
	 * testDepositBuyerFundsAutoCreateBuyer.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDepositBuyerFundsAutoCreateBuyer() throws SLBusinessServiceException {

		Double depositAmount = 250.0d;

		long[] fundingTypes = new long[] { CommonConstants.FUNDING_TYPE_DIRECT_FUNDED, CommonConstants.FUNDING_TYPE_PRE_FUNDED, CommonConstants.SHC_FUNDING_TYPE };

		for (long fundingTypeId : fundingTypes) {

			mockValueLinkDao.reset();

			ValueLinkVO request = valueLinkRequestBuilder.depositBuyerFunds(fundingTypeId, SERVICE_ORDER_ID, BUYER_ID, null, null, BUYER_STATE, depositAmount);

			request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_DEPOSITS_FUNDS_BUYER);

			valueLink.sendValueLinkRequest(request);

			List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
			Assert.assertEquals(2, entries.size());

			assertCreateBuyerV1(entries.get(0), depositAmount, 1);

			assertCreateBuyerV2(entries.get(1), 2);
		}
	}

	/**
	 * testDepositBuyerFundsInstantACH.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDepositBuyerFundsInstantACH() throws SLBusinessServiceException {

		Double depositAmount = 250.0d;

		ValueLinkVO request =
			valueLinkRequestBuilder.depositBuyerFundsInstantACH(CommonConstants.SHC_FUNDING_TYPE, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER,
				BUYER_STATE, depositAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_BUYER_INSTANT_ACH_DEPOSIT);

		valueLink.sendValueLinkRequest(request);
		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(1, entries.size());

		ValueLinkEntryVO entry = entries.get(0);

		assertEntry(entry, SERVICE_ORDER_ID, 1L);
		assertBuyer(entry, BUYER_ID);
		assertOrigin(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertDestination(entry, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE);
		assertCredit(entry, depositAmount);
		assertNoPromoCode(entry);
		assertReloadMessage(entry);
	}

	/**
	 * testDepositServiceLiveOperationsFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testDepositServiceLiveOperationsFunds() throws SLBusinessServiceException {

		Double depositAmount = 950.00d;

		ValueLinkVO request = valueLinkRequestBuilder.depositServiceLiveOperationsFunds(SL3_ACCOUNT_NUMBER, depositAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(1, entries.size());

		assertCreditSL3(entries.get(0), null, depositAmount, 1);
	}

	/**
	 * testIncreaseProjectFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testIncreaseProjectFunds() throws SLBusinessServiceException {

		Double amount = 250.0d;

		ValueLinkVO request =
			valueLinkRequestBuilder.increaseProjectSpendLimit(CommonConstants.FUNDING_TYPE_PRE_FUNDED, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER,
				BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE, amount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitBuyerV1(entries.get(0), SERVICE_ORDER_ID, amount, 1);

		assertCreditBuyerV2FromV1(entries.get(1), amount, 2);

		mockValueLinkDao.reset();

		request =
			valueLinkRequestBuilder.increaseProjectSpendLimit(CommonConstants.SHC_FUNDING_TYPE, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER,
				BUYER_STATE, amount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_INCREASE_SO_ESCROW);

		valueLink.sendValueLinkRequest(request);
		entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(3, entries.size());

		assertCreditBuyerV1(entries.get(0), amount, 1);

		assertDebitBuyerV1(entries.get(1), SERVICE_ORDER_ID, amount, 2);

		assertCreditBuyerV2FromV1(entries.get(2), amount, 3);
	}

	/**
	 * testRouteServiceOrder.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testRouteServiceOrder() throws SLBusinessServiceException {

		Double orderAmount = 350.00d;
		Double postingFeeAmount = 10.00d;
		Double buyerTotal = orderAmount + postingFeeAmount;

		ValueLinkVO request =
			valueLinkRequestBuilder.postServiceOrder(CommonConstants.FUNDING_TYPE_PRE_FUNDED, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER,
				BUYER_STATE, SL1_ACCOUNT_NUMBER, orderAmount, postingFeeAmount, null);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_POST_SO);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(3, entries.size());

		assertDebitBuyerV1(entries.get(0), SERVICE_ORDER_ID, buyerTotal, 1);

		assertCreditSL1FromBuyerV1(entries.get(1), postingFeeAmount, 2);

		assertCreditBuyerV2FromV1(entries.get(2), orderAmount, 3);

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

		Double orderAmount = 275.00d;

		ValueLinkVO request =
			valueLinkRequestBuilder.voidServiceOrder(CommonConstants.FUNDING_TYPE_PRE_FUNDED, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER,
				BUYER_STATE, orderAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_VOID_SO);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(2, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditBuyerV1FromV2(entries.get(1), (orderAmount), 2);

		mockValueLinkDao.reset();

		request =
			valueLinkRequestBuilder.voidServiceOrder(CommonConstants.SHC_FUNDING_TYPE, SERVICE_ORDER_ID, BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_V2_ACCOUNT_NUMBER, BUYER_STATE,
				orderAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_VOID_SO);

		valueLink.sendValueLinkRequest(request);
		entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(3, entries.size());

		assertDebitBuyerV2(entries.get(0), orderAmount, 1);

		assertCreditBuyerV1FromV2(entries.get(1), orderAmount, 2);

		assertDebitBuyerV1(entries.get(2), SERVICE_ORDER_ID, orderAmount, 3);
	}

	/**
	 * testWithdrawBuyerFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testWithdrawBuyerFunds() throws SLBusinessServiceException {

		Double withdrawalAmount = 250.0d;

		ValueLinkVO request = valueLinkRequestBuilder.withdrawBuyerFunds(BUYER_ID, BUYER_V1_ACCOUNT_NUMBER, BUYER_STATE, withdrawalAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(1, entries.size());

		ValueLinkEntryVO entry = entries.get(0);

		assertDebitBuyerV1(entry, null, withdrawalAmount, 1);
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

		Double withdrawalAmount = 130.00d;

		ValueLinkVO request = valueLinkRequestBuilder.withdrawProviderFunds(PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, withdrawalAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(1, entries.size());

		assertDebitProviderV1(entries.get(0), null, withdrawalAmount, 1);

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

		Double withdrawalAmount = 130.00d;

		ValueLinkVO request = valueLinkRequestBuilder.withdrawProviderFundsReversal(PROVIDER_ID, PROVIDER_V1_ACCOUNT_NUMBER, PROVIDER_STATE, withdrawalAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_PROVIDER_REVERSAL);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(1, entries.size());

		assertCreditProviderV1(entries.get(0), null, withdrawalAmount, 1);
	}

	/**
	 * testWithdrawServiceLiveOperationsFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testWithdrawServiceLiveOperationsFunds() throws SLBusinessServiceException {

		Double withdrawalAmount = 725.00d;

		ValueLinkVO request = valueLinkRequestBuilder.withdrawServiceLiveOperationsFunds(SL3_ACCOUNT_NUMBER, withdrawalAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();
		Assert.assertEquals(1, entries.size());

		assertDebitSL3(entries.get(0), null, withdrawalAmount, 1);

	}

	/**
	 * testWithdrawServiceLiveRevenueFunds.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testWithdrawServiceLiveRevenueFunds() throws SLBusinessServiceException {

		Double withdrawalAmount = 725.00d;

		ValueLinkVO request = valueLinkRequestBuilder.withdrawServiceLiveRevenueFunds(SL1_ACCOUNT_NUMBER, withdrawalAmount);

		request.setBusinessTransactionId(CommonConstants.BUSINESS_TRANSACTION_MARKETPLACE_WITHDRAW_FUNDS);

		valueLink.sendValueLinkRequest(request);

		List<ValueLinkEntryVO> entries = mockValueLinkDao.getValueLinkEntries();

		Assert.assertEquals(2, entries.size());

		assertDebitSL1(entries.get(0), null, withdrawalAmount, 1);
	}
}
