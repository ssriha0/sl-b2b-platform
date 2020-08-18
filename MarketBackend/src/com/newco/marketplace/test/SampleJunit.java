package com.newco.marketplace.test;

import junit.framework.TestCase;

public class SampleJunit extends TestCase {

	public SampleJunit(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testHandleAchRequest() {
		int a=5;
		assertTrue(a>0);
		//fail("Not yet implemented");
	}

}
