package com.servicelive.wallet.common;

import org.junit.Assert;
import org.junit.Test;

public class IdentifierDaoTest {

	private IdentifierDao dao;
	
@Test
	public void testGetNextIdentifier() {
	dao = new IdentifierDao();
	
		String key = "STAN_ID";
		Long nextId = null;
		nextId = dao.getNextIdentifier(key);
		
		Assert.assertNotNull(nextId);
	}
}
