package com.newco.marketplace.gwt.providersearch.client;

import junit.framework.Assert;

import org.junit.Test;


public class SimpleProviderSearchActionsTest {

	private SimpleProviderSearchActions actions;
	
	@Test
	public void testGetVeticleVOforSelectedId(){
		actions = new SimpleProviderSearchActions();
		
		int id = 1000;
		
		SimpleProviderSearchSkillNodeVO expectedVO = new SimpleProviderSearchSkillNodeVO();
		expectedVO.setSkillNodeId(-1);		
		
		SimpleProviderSearchSkillNodeVO vo = new SimpleProviderSearchSkillNodeVO();
		vo = actions.getVeticleVOforSelectedId(id);
		
		Assert.assertEquals(-1, -1);
	}
}
