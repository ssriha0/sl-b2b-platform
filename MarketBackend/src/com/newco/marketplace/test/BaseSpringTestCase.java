package com.newco.marketplace.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
public class BaseSpringTestCase extends
		AbstractTransactionalDataSourceSpringContextTests {
	
	@Override
	protected String[] getConfigLocations() {
		
		setAutowireMode(AUTOWIRE_BY_NAME);
		setDependencyCheck(false);		
		return new String[]{"resources/spring/testApplicationContext.xml"};
	}
	
	@Override
	protected void injectDependencies() throws Exception { 
		super.injectDependencies(); 
		this.transactionManager = (PlatformTransactionManager) applicationContext.getBean("dsTransactionManagerMain");
		MPSpringLoaderPlugIn.setCtx(applicationContext);
	}
}
