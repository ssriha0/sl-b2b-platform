package com.servicelive.marketplatform.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AutoRoutingProviderSearchBOTest{
	private AutoRoutingProviderSearchBO searchBo;
	
	@Test
	public void testIsNotOverflowTier(){
		searchBo = new AutoRoutingProviderSearchBO();
		Integer tierId = 2;
		boolean isNotOverflowTier = searchBo.isNotOverflowTier(tierId);
		assertTrue(isNotOverflowTier);
	}

	
}