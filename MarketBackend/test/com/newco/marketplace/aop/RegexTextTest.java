package com.newco.marketplace.aop;

import com.newco.marketplace.utils.ServiceLiveStringUtils;

import junit.framework.TestCase;

public class RegexTextTest extends TestCase {

	public void testShouldReplaceImageMicro() {
		String assertString = "something from providder as buyer";
		String comments1 = "something from providder as ImageMicro";
		String comments2 = "something from providder as IMAGEMICRO";
		String comments3 = "something from providder as IMAGE MICRO";
		String comments4 = "something from providder as IMAGE MiCRO";
		String comments5 = "something from providder as imagemicro";
		String comments6 = "something from providder as image micro";

		assertEquals(assertString, ServiceLiveStringUtils.scrubImageMicroReferences(comments1));
		assertEquals(assertString, ServiceLiveStringUtils.scrubImageMicroReferences(comments2));
		assertEquals(assertString, ServiceLiveStringUtils.scrubImageMicroReferences(comments3));
		assertEquals(assertString, ServiceLiveStringUtils.scrubImageMicroReferences(comments4));
		assertEquals(assertString, ServiceLiveStringUtils.scrubImageMicroReferences(comments5));
		assertEquals(assertString, ServiceLiveStringUtils.scrubImageMicroReferences(comments6));
	}
}
