package com.newco.marketplace.util.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.utils.UIUtils;

public class UIUtilsTest {

	@Test
	public final void testFormatPhoneNumber() {
		String phoneNo = null;
		for (int i = 0; i < 12; ++i) {
			if (phoneNo == null) {
				String formattedPhoneNo = SOPDFUtils.formatPhoneNumber(phoneNo);
				org.apache.log4j.Logger.getLogger(SOPDFUtils.class).info("["+formattedPhoneNo+"]");
				phoneNo = StringUtils.EMPTY;
			}
			phoneNo += String.valueOf(i);
			String formattedPhoneNo = SOPDFUtils.formatPhoneNumber(phoneNo);
			org.apache.log4j.Logger.getLogger(SOPDFUtils.class).info("["+formattedPhoneNo+"]");
		}
	}
	
	//@Test
	/*public final void testIsCreditCardValid() {
		//Good VISA cards
		assertTrue(UIUtils.isCreditCardValid("4539419274140", 6));
		assertTrue(UIUtils.isCreditCardValid("4556667354689", 6));
		assertTrue(UIUtils.isCreditCardValid("4485523743168", 6));
		assertTrue(UIUtils.isCreditCardValid("4485556391752", 6));
		assertTrue(UIUtils.isCreditCardValid("4539322871123", 6));
		assertTrue(UIUtils.isCreditCardValid("4012000033330026", 6));
		assertTrue(UIUtils.isCreditCardValid("4500600000000061", 6));
		assertTrue(UIUtils.isCreditCardValid("4012000077777777", 6));
		assertTrue(UIUtils.isCreditCardValid("4716020524043200", 6));
		assertTrue(UIUtils.isCreditCardValid("4716088657593234", 6));
		assertTrue(UIUtils.isCreditCardValid("4485824139783129", 6));
		assertTrue(UIUtils.isCreditCardValid("4485140474001781", 6));
		assertTrue(UIUtils.isCreditCardValid("4716645773309801", 6));
		assertTrue(UIUtils.isCreditCardValid("4024007165282178", 6));
		assertTrue(UIUtils.isCreditCardValid("4539903865511387", 6));
		assertTrue(UIUtils.isCreditCardValid("4716489754936112", 6));
		assertTrue(UIUtils.isCreditCardValid("4556109854967816", 6));
		assertTrue(UIUtils.isCreditCardValid("4716128778049219", 6));
		
		//Good AMEX cards
		assertTrue(UIUtils.isCreditCardValid("344988141589086", 8));
		assertTrue(UIUtils.isCreditCardValid("373692777810097", 8));
		assertTrue(UIUtils.isCreditCardValid("371055933185675", 8));
		assertTrue(UIUtils.isCreditCardValid("375549142910721", 8));
		assertTrue(UIUtils.isCreditCardValid("347289384888294", 8));

		//Good VISA numbers, wrong card type
		assertFalse(UIUtils.isCreditCardValid("4556667354689", 7));
		assertFalse(UIUtils.isCreditCardValid("4485523743168", 7));
		assertFalse(UIUtils.isCreditCardValid("4485556391752", 7));
		assertFalse(UIUtils.isCreditCardValid("4539322871123", 7));
		
		//Good AMEX numbers, wrong card type
		assertFalse(UIUtils.isCreditCardValid("344988141589086", 7));
		assertFalse(UIUtils.isCreditCardValid("373692777810097", 7));
		assertFalse(UIUtils.isCreditCardValid("371055933185675", 7));
		assertFalse(UIUtils.isCreditCardValid("375549142910721", 7));
		assertFalse(UIUtils.isCreditCardValid("347289384888294", 7));
		assertFalse(UIUtils.isCreditCardValid("4012000033330026", 7));
		assertFalse(UIUtils.isCreditCardValid("4500600000000061", 7));
		assertFalse(UIUtils.isCreditCardValid("4012000077777777", 7));
		
		//Good VISA numbers, no card type
		assertFalse(UIUtils.isCreditCardValid("4556667354689", -1));
		assertFalse(UIUtils.isCreditCardValid("4485523743168", -1));
		assertFalse(UIUtils.isCreditCardValid("4485556391752", -1));
		assertFalse(UIUtils.isCreditCardValid("4539322871123", -1));

		//bum cards
		assertFalse(UIUtils.isCreditCardValid("4539419223140", 6));
		assertFalse(UIUtils.isCreditCardValid("411111111111111", 8));
		assertFalse(UIUtils.isCreditCardValid("4111111111111111", 8));

	}*/

}
