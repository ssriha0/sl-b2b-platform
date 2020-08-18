package com.servicelive.wallet.batch;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.wallet.batch.ach.balanced.BalancedFileProcessor;
import com.servicelive.wallet.creditcard.mocks.MockCreditCardBO;
import com.servicelive.wallet.service.WalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

/**
 * Class TestBalancedProcessor.
 */
public class TestBalancedProcessor {
	
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
			_context = new ClassPathXmlApplicationContext("com/servicelive/wallet/batch/testWalletBatchApplicationContext.xml");
		}
		return _context;
	}

	/** walletClientBO. */
	private WalletBO wallet;
	
	private WalletRequestBuilder walletReqBuilder = new WalletRequestBuilder();
	
	/** balanced. */
	private BalancedFileProcessor balanced;
	
	/** lookupBO. */
	private ILookupBO lookupBO;
	
	
	/**
	 * setup.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Before
	public void setup() throws SLBusinessServiceException{
		ApplicationContext context = getAppContext();

		MockCreditCardBO mockCreditCard = (MockCreditCardBO) context.getBean("mockCreditCard");
		WalletBO wallet = (WalletBO) context.getBean("wallet");
		wallet.setCreditCard(mockCreditCard);

		wallet = (WalletBO)context.getBean("wallet");
		lookupBO = (ILookupBO) context.getBean("lookup");
		balanced = (BalancedFileProcessor)context.getBean("balancedFileProcessor");
		
		balanced.process();	
		
		BuyerLookupVO buyer = lookupBO.lookupBuyer(502484L);
		
		WalletVO request = walletReqBuilder.depositBuyerFundsWithCash(
			"SYSTEM", 1352057100L, 502484L, buyer.getBuyerState(), buyer.getBuyerV1AccountNumber()
			,buyer.getBuyerV2AccountNumber(), "junit-service-order", "Depositing money", 300.00);

		wallet.depositBuyerFundsWithCash(request);
		
		buyer = lookupBO.lookupBuyer(502485L);
		SLCreditCardVO credit = lookupBO.lookupCreditCard(new Long(1311175870L));
		mockCreditCard.setAuthorizationCode(generateAuthCode());
		mockCreditCard.setAuthorizing(true);
		SLCreditCardVO creditCardVO = new SLCreditCardVO();
		BeanUtils.copyProperties(credit, creditCardVO);

		walletReqBuilder.depositBuyerFundsWithCreditCard(
			"SYSTEM", 1311175870L, 502485L, buyer.getBuyerState(), buyer.getBuyerV1AccountNumber()
			,buyer.getBuyerV2AccountNumber(), "junit-service-order", "Depositing money",
			302.00);
		
		wallet.depositBuyerFundsWithCreditCard(request);

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
	 * testProcess.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testProcess() throws SLBusinessServiceException{
		balanced.process();		
	}
}
