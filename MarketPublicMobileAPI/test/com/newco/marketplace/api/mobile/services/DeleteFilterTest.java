/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.deleteFilter.DeleteFilterResponse;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;

/**
 * @author Infosys
 *
 */
public class DeleteFilterTest {
	private MobileGenericValidator validator;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		
	}
	@Test
	public void validateMandatoryFilterId(){
		
		try {
			DeleteFilterResponse response = validator.validateDeleteFilterParam("");
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());
			Assert.assertEquals("3050", response.getResults().getError().get(0).getCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateFilterIdInteger(){
		
		try {
			DeleteFilterResponse response = validator.validateDeleteFilterParam("abc");
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getResults().getError().get(0).getCode());
			Assert.assertEquals("3050", response.getResults().getError().get(0).getCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void validateSuccess(){
		
		try {
			DeleteFilterResponse response = validator.validateDeleteFilterParam("1");
			Assert.assertNull(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
