package com.servicelive.wallet.remoteclient;

import javax.xml.registry.ConnectionFactory;

import org.junit.Test;
import org.springframework.jms.core.JmsTemplate102;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;


public class WalletBOTest{
	private WalletBO bo;
	private JmsTemplate102 jmsTemplate;
	
	@Test
	public void testWalletBO(){
		bo = new WalletBO();
		jmsTemplate = new JmsTemplate102();
		jmsTemplate.setDefaultDestinationName("test");
		ConnectionFactory connectionFactory = null ;
		jmsTemplate.setConnectionFactory((javax.jms.ConnectionFactory) connectionFactory);
		bo.setJmsTemplate(jmsTemplate);
		
		WalletVO request = new WalletVO();
		WalletResponseVO resultVo = new WalletResponseVO();
		try {
			resultVo = bo.adminCreditToBuyer(request);
		} catch (SLBusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
