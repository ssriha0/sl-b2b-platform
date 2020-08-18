package com.newco.marketplace.business.businessImpl.BuyerFileUpload;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.utils.UIUtils;

public class UIUtilsTest {
	private UIUtils utils;

	@Test
	public final void testFormatPhoneNumber() {
		String phoneNo = "6303299893";
		utils = new UIUtils();
		@SuppressWarnings("static-access")
		String formatedNum = utils.formatPhoneNumber(phoneNo);
		String expectedNum = "630-329-9893";
		Assert.assertEquals(expectedNum, formatedNum);
	}


}
