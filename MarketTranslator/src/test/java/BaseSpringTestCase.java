/*
 * @(#)BaseSpringTestCase.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
public class BaseSpringTestCase extends
		AbstractTransactionalDataSourceSpringContextTests { 
	
	@Override
	protected String[] getConfigLocations() {
		
		setAutowireMode(AUTOWIRE_BY_NAME);
		setDependencyCheck(false);		
		return new String[]{"applicationContext.xml"};
	} 

	
}
