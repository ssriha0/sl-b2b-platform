package com.servicelive.routingrules.services.impl;

import org.junit.Test;

import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.services.impl.ValidationServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ValidationServiceImplTest {
	
	private ValidationServiceImpl impl;
	private RoutingRuleHdrDao routingRuleHdrDao;
	
	@Test
	public void testValidateRuleName(){
		routingRuleHdrDao = mock(RoutingRuleHdrDao.class);
		impl = new ValidationServiceImpl();
		impl.setRoutingRuleHdrDao(routingRuleHdrDao);
		
		RoutingRuleHdr hdr = new RoutingRuleHdr();
		hdr.setRoutingRuleHdrId(1234);
		hdr.setRuleName("rule1");
		
		String ruleName = "rule1";
		Integer buyerId = 1000;
		when(routingRuleHdrDao
				.getRoutingRuleByName(ruleName, buyerId)).thenReturn(hdr);
		boolean isValid = impl.validateRuleName(ruleName, buyerId);
		assertTrue(isValid);
	}

}
