package com.servicelive.wallet.valuelink.ejb;

import javax.ejb.MessageDrivenContext;

import org.junit.Test;


public class ValueLinkResponseBeanTest {
	private ValueLinkResponseBean bean;
	
	@Test
	public void testSetMessageDrivenContext(){
		bean = new ValueLinkResponseBean();
		MessageDrivenContext context = null;
		bean.setMessageDrivenContext(context);
	}
	
}
