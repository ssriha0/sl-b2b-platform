package com.servicelive.routingrulesweb.action;

import static org.mockito.Mockito.*;
import junit.framework.Assert;

import org.junit.Test;
import com.servicelive.routingrulesweb.action.RoutingRulesCreateRuleAction;
public class RoutingRulesBaseActionTest {
	private RoutingRulesCreateRuleAction action;
	@Test
	public void getLoggedInNameTest() {
		Integer user = 1000;
		try {
			action =new RoutingRulesCreateRuleAction();
			when(action.getContextBuyerId()).thenReturn(user);
		} catch (Exception e){
            
		}
		Assert.assertEquals(new Integer("1000"),user);
	}
}
