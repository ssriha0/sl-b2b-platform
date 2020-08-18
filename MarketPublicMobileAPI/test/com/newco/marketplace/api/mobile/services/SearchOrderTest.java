/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchCriteria;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchResponse;
import com.newco.marketplace.api.mobile.beans.so.search.SearchStringElement;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
/**
 * @author Infosys
 *
 */
public class SearchOrderTest {
	private MobileGenericValidator validator;
	private MobileGenericMapper mapper;
	
	@Before
	public void setUp() {
		validator = new MobileGenericValidator();
		mapper = new MobileGenericMapper();
		
		
	}
	@Test
	public void mapCriteriaAndValidatePageSize(){
		MobileSOSearchRequest request = formMobileSearchRequest();
		SOSearchCriteriaVO vo = mapper.mapSoSearchRequest(request, 10254, 10202, 3);
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getAcceptedResourceId());
		Assert.assertEquals(1, vo.getPageNo());
		Assert.assertEquals(300, vo.getPageSize());
		Assert.assertEquals(0, vo.getPageLimit()); 
		SecurityContext securityContext = new SecurityContext("melrose1");
		MobileSOSearchResponse mobileSOSearchResponse = validator.validate(vo.getPageSize(),securityContext);
		Assert.assertNotNull(mobileSOSearchResponse);
		Assert.assertNotNull(mobileSOSearchResponse.getResults());		
		Assert.assertNotNull(mobileSOSearchResponse.getResults().getError());		
		Assert.assertNotNull(mobileSOSearchResponse.getResults().getError().get(0));	
		Assert.assertNotNull(mobileSOSearchResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("3001",mobileSOSearchResponse.getResults().getError().get(0).getCode());	
	}
	@Test
	public void mapCriteriaAndValidateResource(){
		MobileSOSearchRequest request = formMobileSearchRequestNoError();
		SOSearchCriteriaVO vo = mapper.mapSoSearchRequest(request, 10254, 10202, 3);
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getAcceptedResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(250, vo.getPageSize());
		Assert.assertEquals(250, vo.getPageLimit()); 
		SecurityContext securityContext = null;
		MobileSOSearchResponse mobileSOSearchResponse = validator.validate(vo.getPageSize(),securityContext);
		Assert.assertNotNull(mobileSOSearchResponse);
		Assert.assertNotNull(mobileSOSearchResponse.getResults());		
		Assert.assertNotNull(mobileSOSearchResponse.getResults().getError());		
		Assert.assertNotNull(mobileSOSearchResponse.getResults().getError().get(0));	
		Assert.assertNotNull(mobileSOSearchResponse.getResults().getError().get(0).getCode());	
		Assert.assertEquals("0006",mobileSOSearchResponse.getResults().getError().get(0).getCode());	
	}
	@Test
	public void mapCriteriaAndValidatePageSizeNoErrorCase(){
		MobileSOSearchRequest request = formMobileSearchRequestNoError();
		SOSearchCriteriaVO vo = mapper.mapSoSearchRequest(request, 10254, 10202, 3);
		Assert.assertNotNull(vo);
		Assert.assertEquals(3, vo.getRoleId());
		Assert.assertEquals(10202, vo.getFirmId());
		Assert.assertEquals(10254, vo.getAcceptedResourceId());
		Assert.assertEquals(2, vo.getPageNo());
		Assert.assertEquals(250, vo.getPageSize());
		Assert.assertEquals(250, vo.getPageLimit()); 
		SecurityContext securityContext = new SecurityContext("melrose1");
		MobileSOSearchResponse mobileSOSearchResponse = validator.validate(vo.getPageSize(),securityContext);
		Assert.assertNull(mobileSOSearchResponse);
	}
	
	
	private MobileSOSearchRequest formMobileSearchRequest() {
		MobileSOSearchRequest mobileSOSearchRequest = new MobileSOSearchRequest();
		mobileSOSearchRequest.setPageNo(1);
		mobileSOSearchRequest.setPageSize(300);
		MobileSOSearchCriteria mobileSOSearchCriteria = new MobileSOSearchCriteria();
		SearchStringElement zipCOdes = new SearchStringElement();
		List<String> values = new ArrayList<String>();
		values.add("60913");
		values.add("60110");
		zipCOdes.setValue(values);
		mobileSOSearchCriteria.setZipCodes(zipCOdes);
		mobileSOSearchRequest.setSearchCriteria(mobileSOSearchCriteria);
		return mobileSOSearchRequest;
	}
	private MobileSOSearchRequest formMobileSearchRequestNoError() {
		MobileSOSearchRequest mobileSOSearchRequest = new MobileSOSearchRequest();
		mobileSOSearchRequest.setPageNo(2);
		mobileSOSearchRequest.setPageSize(250);
		MobileSOSearchCriteria mobileSOSearchCriteria = new MobileSOSearchCriteria();
		SearchStringElement zipCOdes = new SearchStringElement();
		List<String> values = new ArrayList<String>();
		values.add("60913");
		values.add("60110");
		zipCOdes.setValue(values);
		mobileSOSearchCriteria.setZipCodes(zipCOdes);
		mobileSOSearchRequest.setSearchCriteria(mobileSOSearchCriteria);
		return mobileSOSearchRequest;
	}
	
}
