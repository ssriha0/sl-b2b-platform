/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
/**
 * @author Infosys
 *
 */
public class GetRecievedOrdersTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mapper = new MobileGenericMapper();
		
		
	}
	@Test
	public void mapCriteriaAndValidatePageSize(){
		RecievedOrdersCriteriaVO vo = mapper.mapRecievedOrdersCriteria(3, "10202", 10254, "2", "300","false");
		
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(300, vo.getPageSize());
		Assert.assertEquals(300, vo.getPageLimit()); 
		
		Results results = validator.validateGetRecievedOrders(vo, 0, false); 
		
		Assert.assertNotNull(results);
		Assert.assertNotNull(results.getError());
		Assert.assertEquals("0204", results.getError().get(0).getCode());
		Assert.assertEquals("Invalid Page Size.", results.getError().get(0).getMessage());
		
	}
	
	
	@Test
	public void mapCriteriaAndValidateCount(){
		RecievedOrdersCriteriaVO vo = mapper.mapRecievedOrdersCriteria(3, "10202", 10254, "2", "25","false");
		
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(25, vo.getPageSize());
		Assert.assertEquals(25, vo.getPageLimit()); 
		
		Results results = validator.validateGetRecievedOrders(vo, 0, true); 
		
		Assert.assertNotNull(results);
		Assert.assertNotNull(results.getError());
		Assert.assertEquals("0200", results.getError().get(0).getCode());
		Assert.assertEquals("No service orders are available for provider.", results.getError().get(0).getMessage());
		
	}
	
	@Test
	public void mapCriteriaAndValidatePageNo(){
		RecievedOrdersCriteriaVO vo = mapper.mapRecievedOrdersCriteria(3, "10202", 10254, "2", "25","false");
		
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(25, vo.getPageSize());
		Assert.assertEquals(25, vo.getPageLimit()); 
		
		Results results = validator.validateGetRecievedOrders(vo, 10, true); 
		
		Assert.assertNotNull(results);
		Assert.assertNotNull(results.getError());
		Assert.assertEquals("0201", results.getError().get(0).getCode());
		Assert.assertEquals("Page number does not exist.", results.getError().get(0).getMessage());
		
	}
}
