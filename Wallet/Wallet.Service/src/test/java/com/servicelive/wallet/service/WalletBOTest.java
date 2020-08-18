package com.servicelive.wallet.service;

import org.junit.Test;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.IValueLinkBO;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class WalletBOTest {
	private WalletBO walletBo;
	private IValueLinkBO valueLinkBO;
	@Test
	public void testHasPreviousAddOn(){
		walletBo = new WalletBO();
		valueLinkBO = mock(IValueLinkBO.class);
		walletBo.setValueLink(valueLinkBO);
	String soId = "541-5430-5650-17";
	boolean hasAddon = false;
	try {
		when(valueLinkBO.hasPreviousAddOn(soId)).thenReturn(true);
		hasAddon = walletBo.hasPreviousAddOn(soId);
	} catch (SLBusinessServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertTrue(hasAddon);
	}
	
	}

