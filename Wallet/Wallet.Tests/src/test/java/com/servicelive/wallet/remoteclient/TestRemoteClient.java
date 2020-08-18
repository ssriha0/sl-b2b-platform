package com.servicelive.wallet.remoteclient;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

public class TestRemoteClient {
	
	private static ApplicationContext context = new ClassPathXmlApplicationContext("com/servicelive/wallet/client/testremoteClientAppContext.xml");

	@Test
	public void testPostOrder() throws Exception{
		
		IWalletBO walletBO = (IWalletBO) context.getBean("walletRemoteClient");
		WalletRequestBuilder builder = new WalletRequestBuilder();
		ILookupBO lookup = (ILookupBO)context.getBean("lookup");
		BuyerLookupVO buyer = lookup.lookupBuyer(505330L);
		WalletVO request = builder.postServiceOrder("yburhani", null, 505330L, buyer.getBuyerState(), 
				buyer.getBuyerV1AccountNumber(), buyer.getBuyerV2AccountNumber(), 
				7777008250310755L, "576-5063-0622-39", buyer.getBuyerFundingTypeId(), 
				"", 250.00, 0.0, 0.0, 0.0);
		
		WalletResponseVO response = walletBO.postServiceOrder(request);
		Assert.notNull(response.getMessageId());
	}
	
	
	@Test
	public void testWebService() throws SLBusinessServiceException{
		IWalletBO walletBO = (IWalletBO) context.getBean("walletRemoteClient");
		double amount = walletBO.getBuyerAvailableBalance(505330);
		Assert.isTrue(amount > 0);
	}
}
