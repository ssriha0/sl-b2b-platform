package com.servicelive.wallet.serviceinterface;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import com.servicelive.wallet.service.WalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

/**
 * Class WalletServiceInterfaceBONoMockTest.
 */
public class WalletServiceInterfaceBONoMockTest extends AbstractTransactionalDataSourceSpringContextTests {

	/** walletInterface. */
	private WalletBO wallet;
	private WalletRequestBuilder walletReqBuilder = new WalletRequestBuilder();
	
	/** lookup. */
	private ILookupBO lookup;
	
	/** BUYER_INFO. */
	private BuyerLookupVO BUYER_INFO;
	
	/** PROVIDER_INFO. */
	private ProviderLookupVO PROVIDER_INFO;
	
	/** ADMIN_INFO. */
	private AdminLookupVO ADMIN_INFO;
	
	/** CREDIT_CARD_INFO. */
	private SLCreditCardVO CREDIT_CARD_INFO;
	
	/** BANK_ACCOUNT_ID. */
	private final Long BANK_ACCOUNT_ID = 63199074L;

	/** BUYER_ID. */
	private final Long BUYER_ID = 1993L;

	/** BUYER_STATE. */
	private final String BUYER_STATE = "MI";

	/** BUYER_V1_ACCOUNT_NUMBER. */
	private final Long BUYER_V1_ACCOUNT_NUMBER = 7777008374704775L;

	/** BUYER_V2_ACCOUNT_NUMBER. */
	private final Long BUYER_V2_ACCOUNT_NUMBER = 7777008393032388L;
	
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

	
	/**
	 * WalletServiceInterfaceBONoMockTest.
	 */
	public WalletServiceInterfaceBONoMockTest() {
		this.setAutowireMode(AUTOWIRE_BY_NAME);
		this.setDefaultRollback(true);
	}

 	/* (non-Javadoc)
	  * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	  */
	 @Override
 	protected String[] getConfigLocations() {
 		return new String[] {"com/servicelive/wallet/serviceinterface/testWalletServiceInterfaceApplicationContext.xml"};
 	}
	
	/* (non-Javadoc)
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUp()
	 */
	@Override
	public void onSetUp() throws Exception {
		this.wallet = (WalletBO)this.getApplicationContext().getBean("wallet");
		this.lookup = (ILookupBO)this.getApplicationContext().getBean("lookup");

		//ADMIN_INFO = lookup.lookupAdmin();
		//BUYER_INFO = lookup.lookupBuyer(BUYER_ID);
		//PROVIDER_INFO = lookup.lookupProvider(PROVIDER_ID);
		//CREDIT_CARD_INFO = lookup.lookupCreditCard(VISA_CREDIT_CARD_ACCOUNT_ID);
		
	}

	/**
	 * testDepositBuyerFundsWithCash.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void _testDepositBuyerFundsWithCash() throws SLBusinessServiceException {

		double depositAmount = 150.00d;

		WalletVO request = walletReqBuilder.depositBuyerFundsWithCash(
			USER_NAME, BANK_ACCOUNT_ID,  BUYER_ID, BUYER_INFO.getBuyerState(), 
			null, null,
			SERVICE_ORDER_ID, null, depositAmount);
		
		WalletResponseVO response = wallet.depositBuyerFundsWithCash(request);
		
		Assert.assertFalse(response.isError());
	}

	/**
	 * testDepositBuyerFundsWithCreditCard.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void _testDepositBuyerFundsWithCreditCard() throws SLBusinessServiceException {
	
		double depositAmount = 500.00d;

		SLCreditCardVO creditCardVO = new SLCreditCardVO();
		BeanUtils.copyProperties(CREDIT_CARD_INFO, creditCardVO);
		creditCardVO.setCardVerificationNo("111");
		creditCardVO.setCardNo("5111111111111111");
		creditCardVO.setTransactionAmount(depositAmount);

		WalletVO request = walletReqBuilder.depositBuyerFundsWithCreditCard(
			USER_NAME, VISA_CREDIT_CARD_ACCOUNT_ID,  BUYER_ID, BUYER_INFO.getBuyerState(), 
			null, null,
			SERVICE_ORDER_ID, null, depositAmount);

		request.setCreditCard(creditCardVO);
		
		WalletResponseVO response = wallet.depositBuyerFundsWithCreditCard(request);
		
		Assert.assertFalse(response.isError());
	}
	
	/**
	 * Description:  Test for strictly activation of Buyer.
	 * @throws SLBusinessServiceException
	 */
	@Test
	public void testActivateBuyer() throws SLBusinessServiceException {
		WalletVO request = walletReqBuilder.activateBuyer(BUYER_ID, 30, BUYER_STATE);
		WalletResponseVO response = wallet.activateBuyer(request);
		Assert.assertFalse(response.isError());
	}

	/**
	 * Description:  Test for auth of CC for $1 and no CVV number .
	 * @throws SLBusinessServiceException
	 */
	@Test
	public void _testAuthCCForDollarNoCVV() throws SLBusinessServiceException {
		//    Parameters are:(transAmount, cvv, cardTypeId, expDate, cardNo, zip, address)
		WalletVO request = walletReqBuilder.authCCForDollarNoCVV(1.00,"",5l,"2010-12-31","4111111111111111","60177","597 Oak", "South Elgin IL");
		WalletResponseVO response = wallet.authCCForDollarNoCVV(request);
		Assert.assertFalse(response.isError());
	}
	
	/**
	 * testWithdrawProviderFunds.
	 * 
	 * @return void
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void _testWithdrawProviderFunds() throws SLBusinessServiceException {

		double withdrawalAmount = 1.00d;

		WalletVO request = walletReqBuilder.withdrawProviderFunds(
			USER_NAME, BANK_ACCOUNT_ID, PROVIDER_ID, PROVIDER_INFO.getProviderState(), null,
			withdrawalAmount, 6, 500.00);
		
		WalletResponseVO response = wallet.withdrawProviderFunds(request);
		
		Assert.assertFalse(response.isError());

	}
	/**
	 * testWidthrawOperationFund.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void _testWidthrawOperationFund() throws SLBusinessServiceException {
		
		double widthdrawAmount = 250.0d;
		
		WalletVO request = walletReqBuilder.withdrawOperationFund(
			USER_NAME, 0L, "Test withdraw from Operation", widthdrawAmount, SL3_ACCOUNT_NUMBER);

		WalletResponseVO response = wallet.withdrawOperationFunds(request);
		
		Assert.assertFalse(response.isError());

	}

	public void _testGetTransactionReceipt(){
		try {
			ReceiptVO receipt = wallet.getTransactionReceipt(BUYER_ID, (int)CommonConstants.LEDGER_ENTITY_TYPE_BUYER, LedgerEntryType.LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE, "536-345-332-98");
			Assert.assertNull(receipt);
		} catch (SLBusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
