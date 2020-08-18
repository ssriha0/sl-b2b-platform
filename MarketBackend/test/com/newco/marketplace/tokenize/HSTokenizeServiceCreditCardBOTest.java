package com.newco.marketplace.tokenize;


import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import com.servicelive.common.exception.SLBusinessServiceException;

public class HSTokenizeServiceCreditCardBOTest {

	private HSTokenizeServiceCreditCardBO hSTokenizeServiceCreditCardBO;
	
	@Before
	public void setUp() {
		hSTokenizeServiceCreditCardBO = new HSTokenizeServiceCreditCardBO();
	}

	@Test(expected = SLBusinessServiceException.class)
	public void testAuthorizeHSSTransaction() throws SLBusinessServiceException {
		TokenizeResponse tokenizeResponse = new TokenizeResponse();
		String userName = "athakkar";
		String accNum = "9550101903578";
		tokenizeResponse =hSTokenizeServiceCreditCardBO.tokenizeHSSTransaction(accNum, userName);
		assertNull(tokenizeResponse);
		
	}
}
