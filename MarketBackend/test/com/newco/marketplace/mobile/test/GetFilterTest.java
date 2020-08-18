package com.newco.marketplace.mobile.test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.dto.vo.serviceorder.FilterCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;

public class GetFilterTest {

	private static final Logger LOGGER = Logger.getLogger(GetFilterTest.class);
    private MobileGenericBOImpl mobileBO;
    private IMobileGenericDao genericDao;
    Integer providerId;
    Map<String,List<FilterCriteriaVO>> criteriaMap;
    List<FiltersVO> filters;
    List<FilterCriteriaVO> criteriaList;
    FilterCriteriaVO filterCriteriaVO;
    FiltersVO filter;
	@Before
	public void setUp() {
		mobileBO=new MobileGenericBOImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		filters=new ArrayList<FiltersVO>();
		filterCriteriaVO=new FilterCriteriaVO();
		criteriaList=new ArrayList<FilterCriteriaVO>();
		criteriaMap=new HashMap<String, List<FilterCriteriaVO>>();
		filter=new FiltersVO();
		filter.setFilterId(1);
		filter.setFilterName("filter 1");
		filter.setResourceId(54366);
		filterCriteriaVO.setFilterId(1);
		filterCriteriaVO.setFlaggedSo(true);
		filterCriteriaVO.setSearchCriteria("MARKET");
		filterCriteriaVO.setSearchCriteriaValueId("357");
		filterCriteriaVO.setSearchCriteriaValueString("Aberdeen, WA");
		filterCriteriaVO.setSearchCriteria("MARKET");
		filterCriteriaVO.setSearchCriteriaValueId("358");
		filterCriteriaVO.setSearchCriteriaValueString("Abilene, TX");
		criteriaList.add(filterCriteriaVO);
		filter.setCriteriaList(criteriaList);
		criteriaMap.put(MPConstants.MARKET, criteriaList);
		filter.setCriteriaMap(criteriaMap);
		filters.add(filter);
		
		try {
			when(genericDao.getSearchFilterDetails(providerId)).thenReturn(filters);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
	
	}
	@Test
	public void getFilterDetails(){
		Map<Integer,FiltersVO > result=null;
		providerId=10254;
		try {
			
			 result=mobileBO.getSearchFilterDetails(providerId);
		} catch (BusinessServiceException e) {
			LOGGER.error("Exception in Junit execution for Get Filter"+ e.getMessage());
		}
		Assert.assertNotNull(result);
	}
}
