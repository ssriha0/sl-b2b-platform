package com.newco.spring.jms;

import junit.framework.TestCase;

public class MockSpringJMSFacilityTest extends TestCase {

	public void testShouldReceiveNull() {
		MockSpringJMSFacility mock = new MockSpringJMSFacility();
		assertNull(mock.Receive());
	}

}
