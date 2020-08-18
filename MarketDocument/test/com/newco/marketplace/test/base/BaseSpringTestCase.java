package com.newco.marketplace.test.base;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Based off of Spring JUnit support that lets us test without needing a JNDI lookup
 */
public class BaseSpringTestCase extends
		AbstractTransactionalDataSourceSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		setDependencyCheck(false);
		return new String[]{"resources/spring/documentAppContext.xml"};
	}


}
