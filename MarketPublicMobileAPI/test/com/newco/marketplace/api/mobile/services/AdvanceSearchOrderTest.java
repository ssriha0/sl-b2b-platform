/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.so.search.SearchIntegerElement;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchCriteria;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
/**
 * @author Infosys
 *
 */
public class AdvanceSearchOrderTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mapper = new MobileGenericMapper();
		
		
	}
	@Test
	public void mapCriteriaAndValidatePageSize(){
		MobileSOAdvanceSearchRequest request = formMobileSearchRequest();
		SOAdvanceSearchCriteriaVO vo = mapper.mapSoAdvanceSearchRequest(request, 10254, 10202, 3);
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getAcceptedResourceId());
		Assert.assertEquals(1, vo.getPageNo());
		Assert.assertEquals(300, vo.getPageSize());
		Assert.assertEquals(0, vo.getPageLimit()); 
		SecurityContext securityContext = new SecurityContext("melrose1");
		MobileSOAdvanceSearchResponse advanceSearchResponse = validator.validateAdvanceSearchRequest(request, securityContext,3);
		Assert.assertNotNull(advanceSearchResponse);
		Assert.assertNotNull(advanceSearchResponse.getResults());		
		Assert.assertNotNull(advanceSearchResponse.getResults().getError());		
		Assert.assertNotNull(advanceSearchResponse.getResults().getError().get(0));	
		Assert.assertNotNull(advanceSearchResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3001",advanceSearchResponse.getResults().getError().get(0).getCode());	
	}
	
	@Test
	public void mapCriteriaAndValidateStatusIdsErrorCase(){
		MobileSOAdvanceSearchRequest request = formMobileSearchRequestNoError();
		SOAdvanceSearchCriteriaVO vo = mapper.mapSoAdvanceSearchRequest(request, 10254, 10202, 1);
		Assert.assertNotNull(vo);
		Assert.assertEquals(1, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getAcceptedResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(250, vo.getPageSize());
		Assert.assertEquals(250, vo.getPageLimit()); 
		SecurityContext securityContext = new SecurityContext("melrose1");
		MobileSOAdvanceSearchResponse advanceSearchResponse = validator.validateAdvanceSearchRequest(request, securityContext,1);
		Assert.assertNotNull(advanceSearchResponse);
		Assert.assertNotNull(advanceSearchResponse.getResults());		
		Assert.assertNotNull(advanceSearchResponse.getResults().getError());		
		Assert.assertNotNull(advanceSearchResponse.getResults().getError().get(0));	
		Assert.assertNotNull(advanceSearchResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("4010",advanceSearchResponse.getResults().getError().get(0).getCode());	
	}
	@Test
	public void mapCriteriaAndValidateResourceIdErrorCase(){
		MobileSOAdvanceSearchRequest request = formMobileSearchRequestNoError();
		SOAdvanceSearchCriteriaVO vo = mapper.mapSoAdvanceSearchRequest(request, 23006, 10202, 1);
		Assert.assertNotNull(vo);
		Assert.assertEquals(1, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(23006, vo.getAcceptedResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(250, vo.getPageSize());
		Assert.assertEquals(250, vo.getPageLimit()); 
		SecurityContext securityContext = null;
		MobileSOAdvanceSearchResponse advanceSearchResponse = validator.validateAdvanceSearchRequest(request, securityContext,3);
		Assert.assertNotNull(advanceSearchResponse);
		Assert.assertNotNull(advanceSearchResponse.getResults());		
		Assert.assertNotNull(advanceSearchResponse.getResults().getError());		
		Assert.assertNotNull(advanceSearchResponse.getResults().getError().get(0));	
		Assert.assertNotNull(advanceSearchResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("0006",advanceSearchResponse.getResults().getError().get(0).getCode());	
	}
	
	@Test
	public void mapCriteriaAndValidateNoErrorCase(){
		MobileSOAdvanceSearchRequest request = formMobileSearchRequestNoError();
		SOAdvanceSearchCriteriaVO vo = mapper.mapSoAdvanceSearchRequest(request, 104411, 10202, 2);
		Assert.assertNotNull(vo);
		Assert.assertEquals(2, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(104411, vo.getAcceptedResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(250, vo.getPageSize());
		Assert.assertEquals(250, vo.getPageLimit()); 
		SecurityContext securityContext = new SecurityContext("melrose1");
		MobileSOAdvanceSearchResponse advanceSearchResponse = validator.validateAdvanceSearchRequest(request, securityContext,3);
		Assert.assertNull(advanceSearchResponse);
	}
	
	
	private MobileSOAdvanceSearchRequest formMobileSearchRequest() {
		MobileSOAdvanceSearchRequest mobileSOSearchRequest = new MobileSOAdvanceSearchRequest();
		mobileSOSearchRequest.setPageNo(1);
		mobileSOSearchRequest.setPageSize(300);
		MobileSOAdvanceSearchCriteria mobileSOSearchCriteria = new MobileSOAdvanceSearchCriteria();
		SearchIntegerElement statuses = new SearchIntegerElement();
		List<Integer> values = new ArrayList<Integer>();
		values.add(110);
		values.add(150);
		statuses.setValue(values);
		mobileSOSearchCriteria.setStatuses(statuses);
		mobileSOSearchRequest.setAdvanceSearchCriteria(mobileSOSearchCriteria);
		return mobileSOSearchRequest;
	}
	private MobileSOAdvanceSearchRequest formMobileSearchRequestNoError() {
		MobileSOAdvanceSearchRequest mobileSOSearchRequest = new MobileSOAdvanceSearchRequest();
		mobileSOSearchRequest.setPageNo(2);
		mobileSOSearchRequest.setPageSize(250);
		MobileSOAdvanceSearchCriteria mobileSOSearchCriteria = new MobileSOAdvanceSearchCriteria();
		SearchIntegerElement statuses = new SearchIntegerElement();
		List<Integer> values = new ArrayList<Integer>();
		values.add(110);
		values.add(150);
		statuses.setValue(values);
		mobileSOSearchCriteria.setStatuses(statuses);
		mobileSOSearchRequest.setAdvanceSearchCriteria(mobileSOSearchCriteria);
		return mobileSOSearchRequest;
	}
	
}
