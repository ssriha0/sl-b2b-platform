package com.newco.marketplace.api.mobile.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.Filter.GetFilterResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.dto.vo.serviceorder.FilterCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.mobile.constants.MPConstants;

public class GetFilterTest {

	private MobileGenericMapper mapper;
	Map<String,List<FilterCriteriaVO>> criteriaMap;
	Map<Integer,FiltersVO> filterDetailsMap;
	List<FilterCriteriaVO> criteriaList;
	FilterCriteriaVO filterCriteriaVO;
	FiltersVO filter;
	@Before
	public void setUp() {
		
		mapper = new MobileGenericMapper();
		filterCriteriaVO=new FilterCriteriaVO();
		criteriaList=new ArrayList<FilterCriteriaVO>();
		criteriaMap=new HashMap<String, List<FilterCriteriaVO>>();
		filterDetailsMap=new HashMap<Integer, FiltersVO>();
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
		filterDetailsMap.put(filter.getFilterId(), filter);
		
	}
	
	@Test
	public void mapFilterDetails(){
		GetFilterResponse response=null;
		response=mapper.mapFilterDetailsResponse(filterDetailsMap);
		Assert.assertNotNull(response);
	}
}
