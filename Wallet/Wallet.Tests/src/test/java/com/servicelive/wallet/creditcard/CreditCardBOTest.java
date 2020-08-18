 package com.servicelive.wallet.creditcard;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.creditcard.mocks.MockCreditCardDao;

/**
 * Class CreditCardBOTest.
 */
public class CreditCardBOTest extends AbstractTransactionalDataSourceSpringContextTests {

 	/* (non-Javadoc)
	  * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	  */
	 @Override
 	protected String[] getConfigLocations() {
 		return new String[] {"com/servicelive/wallet/creditcard/testCreditCardApplicationContext.xml"};
 	}
 
	/** amount_Declined. */
	SLCreditCardVO amount_Declined = null;

	/** cid_NoMatch. */
	SLCreditCardVO cid_NoMatch = null;

	/** creditCardBO. */
	private ICreditCardBO creditCardBO = null;

	/** mockCreditCardDao. */
	private MockCreditCardDao mockCreditCardDao = null;

	/** sears_Approved. */
	SLCreditCardVO sears_Approved = null;

	/** sears_Declined. */
	SLCreditCardVO sears_Declined = null;

	/** visa_Approved. */
	SLCreditCardVO visa_Approved = null;

	/** visa_Declined. */
	SLCreditCardVO visa_Declined = null;

	/**
	 * CreditCardBOTest.
	 */
	public CreditCardBOTest() {
		this.setAutowireMode(AUTOWIRE_BY_NAME);
	}
	
	/**
	 * amountApproved.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void amountApproved(SLCreditCardVO cc) {

		cc.setTransactionAmount(500.00);
	}

	/**
	 * amountDeclined.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void amountDeclined(SLCreditCardVO cc) {

		cc.setTransactionAmount(101.00);
	}

	/**
	 * assertAuthorized.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void assertAuthorized(SLCreditCardVO cc) {

		Assert.assertTrue(cc.isAuthorized());
	}

	/**
	 * assertAuthorizedResponse.
	 * 
	 * @param req 
	 * @param resp 
	 * 
	 * @return void
	 */
	private void assertAuthorizedResponse(SLCreditCardVO req, SLCreditCardVO resp) {

		Assert.assertEquals(req.getCardNo(), resp.getCardNo());
	}

	/**
	 * assertLastSaved.
	 * 
	 * @param req 
	 * 
	 * @return void
	 */
	private void assertLastSaved(SLCreditCardVO req) {

		Assert.assertSame(req.getCardNo(), this.mockCreditCardDao.getLastSavedAuthResponse().getCardNo());
	}

	/**
	 * assertNotAuthorized.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void assertNotAuthorized(SLCreditCardVO cc) {

		Assert.assertFalse(cc.isAuthorized());
	}

	/**
	 * assertNotAuthorizedResponse.
	 * 
	 * @param req 
	 * @param resp 
	 * 
	 * @return void
	 */
	private void assertNotAuthorizedResponse(SLCreditCardVO req, SLCreditCardVO resp) {

		Assert.assertEquals(req.getCardNo(), resp.getCardNo());
	}

	/**
	 * cidMatch.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void cidMatch(SLCreditCardVO cc) {

		cc.setCardVerificationNo("111");
	}

	/**
	 * cidNoMatch.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void cidNoMatch(SLCreditCardVO cc) {

		cc.setCardVerificationNo("444");
	}

	/**
	 * defaultApproved.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void defaultApproved(SLCreditCardVO cc) {

		defaultCardInfo(cc);
		amountApproved(cc);
		cidMatch(cc);
		visaApproved(cc);
	}

	/**
	 * defaultCardInfo.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void defaultCardInfo(SLCreditCardVO cc) {
		cc.setCardId(1L);
		cc.setCardHolderName("Jake");
		cc.setExpireDate("0912");
		cc.setBillingAddress1("123 Woodhaven Lane");
		cc.setBillingAddress2("");
		cc.setBillingCity("Beverly Hills");
		cc.setBillingState("MI");
		cc.setZipcode("48025");
	}


	/**
	 * searsApproved.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void searsApproved(SLCreditCardVO cc) {

		cc.setCardTypeId(4L);
		cc.setCardNo("5111111111111111");
	}


	
	/* (non-Javadoc)
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUp()
	 */
	@Override
	public void onSetUp() throws Exception {
		
		// setup test data
		visa_Approved = new SLCreditCardVO();
		defaultApproved(visa_Approved);

		amount_Declined = new SLCreditCardVO();
		defaultApproved(amount_Declined);
		amountDeclined(amount_Declined);

		cid_NoMatch = new SLCreditCardVO();
		defaultApproved(cid_NoMatch);
		cidNoMatch(cid_NoMatch);

		visa_Declined = new SLCreditCardVO();
		defaultApproved(visa_Declined);
		visaDeclined(visa_Declined);

		sears_Approved = new SLCreditCardVO();
		defaultApproved(sears_Approved);
		searsApproved(sears_Approved);

		sears_Declined = new SLCreditCardVO();
		defaultApproved(sears_Declined);
		searsApproved(sears_Declined);
		amountDeclined(sears_Declined);

		// wire up the mock dao
		this.mockCreditCardDao = (MockCreditCardDao) getApplicationContext().getBean("mockCreditCardDao");
		((CreditCardBO) getApplicationContext().getBean("searsCreditCard")).setCreditCardDao(mockCreditCardDao);
		((CreditCardBO) getApplicationContext().getBean("thirdPartyCreditCard")).setCreditCardDao(mockCreditCardDao);

		this.creditCardBO = (ICreditCardBO) getApplicationContext().getBean("creditCard");

		startNewTransaction();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onTearDown()
	 */
	@Override
	protected void onTearDown() throws Exception {
		this.transactionManager.rollback(this.transactionStatus);
	}
	
	/**
	 * testAmountDeclined.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testAmountDeclined() throws SLBusinessServiceException {
		testCardNotAuthorized(amount_Declined);
	}

	/**
	 * testCardAuthorized.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void testCardAuthorized(SLCreditCardVO cc) throws SLBusinessServiceException {

		this.mockCreditCardDao.reset();
		SLCreditCardVO resp = creditCardBO.authorizeCardTransaction(cc);

		assertAuthorized(resp);
		assertAuthorizedResponse(cc, resp);
		assertLastSaved(cc);
		Assert.assertEquals(1, this.mockCreditCardDao.getInvocationCount());
	}

	/**
	 * testCardNotAuthorized.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void testCardNotAuthorized(SLCreditCardVO cc) throws SLBusinessServiceException {

		this.mockCreditCardDao.reset();
		SLCreditCardVO resp = creditCardBO.authorizeCardTransaction(cc);

		assertNotAuthorized(resp);
		assertNotAuthorizedResponse(cc, resp);
		assertLastSaved(cc);
		Assert.assertEquals(1, this.mockCreditCardDao.getInvocationCount());
	}

	/**
	 * testCidNoMatch.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testCidNoMatch() throws SLBusinessServiceException {

		testCardNotAuthorized(cid_NoMatch);
	}

	/**
	 * testSearsApproved.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testSearsApproved() throws SLBusinessServiceException {

		testCardAuthorized(sears_Approved);

	}

	/**
	 * testSearsDeclined.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testSearsDeclined() throws SLBusinessServiceException {

		testCardNotAuthorized(sears_Declined);

	}

	/**
	 * testVisaApproved.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testVisaApproved() throws SLBusinessServiceException {

		testCardAuthorized(visa_Approved);

	}
	
	@Test
	public void testMissingNameAndExire() throws SLBusinessServiceException {
		
		SLCreditCardVO cc = new SLCreditCardVO();
		BeanUtils.copyProperties(visa_Approved, cc);
		cc.setCardHolderName(null);
		cc.setExpireDate(null);
		testCardNotAuthorized(cc);
	}

	/**
	 * testVisaDeclined.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testVisaDeclined() throws SLBusinessServiceException {

		testCardNotAuthorized(visa_Declined);
	}

	/**
	 * visaApproved.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void visaApproved(SLCreditCardVO cc) {

		cc.setCardTypeId(6L);
		cc.setCardNo("4111111111111111");
	}

	/**
	 * visaDeclined.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	private void visaDeclined(SLCreditCardVO cc) {

		cc.setCardTypeId(6L);
		cc.setCardNo("4012000033330021");
	}

}
