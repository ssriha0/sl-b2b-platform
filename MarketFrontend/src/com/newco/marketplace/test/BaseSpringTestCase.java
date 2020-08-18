package com.newco.marketplace.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
public class BaseSpringTestCase extends
		AbstractTransactionalDataSourceSpringContextTests { 
	
	protected String[] getConfigLocations() {
		
		setAutowireMode(AUTOWIRE_BY_NAME);
		setDependencyCheck(false);		
		return new String[]{"resources/spring/applicationContext.xml"};
	} 

	
}
