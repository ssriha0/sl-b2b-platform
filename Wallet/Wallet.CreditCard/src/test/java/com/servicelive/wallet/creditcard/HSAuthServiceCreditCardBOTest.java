package com.servicelive.wallet.creditcard;


import static org.junit.Assert.assertNull;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

public class HSAuthServiceCreditCardBOTest {

	private HSAuthServiceCreditCardBO hSAuthServiceCreditCardBO;
	/** CREDIT_CARD_INFO. */
	private SLCreditCardVO CREDIT_CARD_INFO;
	@Before
	public void setUp() {
		hSAuthServiceCreditCardBO = new HSAuthServiceCreditCardBO();
	}

//	@Test(expected = SLBusinessServiceException.class)
//	public void testAuthorizeHSSTransaction() throws SLBusinessServiceException {
//		SLCreditCardVO sLCreditCardVO = new SLCreditCardVO();
//		String userName = "athakkar";
//		sLCreditCardVO =hSAuthServiceCreditCardBO.authorizeHSSTransaction(sLCreditCardVO, userName);
//		assertNull(sLCreditCardVO);
//		
//	}
//	
//	@Test(expected = SLBusinessServiceException.class)
//	public void testRefundHSTransaction() throws SLBusinessServiceException {
//		SLCreditCardVO sLCreditCardVO = new SLCreditCardVO();
//		String userName = "sllive";
//		sLCreditCardVO =hSAuthServiceCreditCardBO.refundHSTransaction(sLCreditCardVO, userName);
//		assertNull(sLCreditCardVO);
//		
//	}
	
	@Test(expected = SLBusinessServiceException.class)
	public void testRefundHSTransaction_1() throws SLBusinessServiceException {
	double withdrawAmount = 50.00d;

	SLCreditCardVO creditCardVO = new SLCreditCardVO();
	// BeanUtils.copyProperties(CREDIT_CARD_INFO, creditCardVO);
	creditCardVO.setCardVerificationNo("111");
	creditCardVO.setCardNo("4012000033330026");
	creditCardVO.setTransactionAmount(withdrawAmount);
	creditCardVO.setZipcode("22222");
	creditCardVO.setToken("4012000033330026");
	creditCardVO.setUserName("test");
	
	String userName = "test";
	creditCardVO =hSAuthServiceCreditCardBO.refundHSTransaction(creditCardVO, userName);
	Assert.assertEquals(creditCardVO.getResponseMessage(), "SUCCESS");
}
}

//
//<ns2:acctNo>4012000033330026</ns2:acctNo>
//<ns2:transAmt>100</ns2:transAmt>
//<ns2:transTs>160407015359</ns2:transTs>
//<ns2:expirationDate></ns2:expirationDate>
//<ns2:track2></ns2:track2>
//<ns2:termId>07711000</ns2:termId>
//<ns2:zipCode>22222</ns2:zipCode>
//<ns2:inetBased>N</ns2:inetBased>