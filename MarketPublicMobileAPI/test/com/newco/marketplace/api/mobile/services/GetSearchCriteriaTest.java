package com.newco.marketplace.api.mobile.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.beans.searchCriteria.SearchCriterias;
import com.newco.marketplace.api.criteria.vo.SearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoSearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoStatusVO;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;


public class GetSearchCriteriaTest {
	
	private MobileGenericMapper mapper;
	
	private SoSearchCriteriaVO soSearchCriteriaVO ;
	private SearchCriteriaVO searchCriteriaVO;
	private SearchCriteriaVO searchCriteriavo;
	private SoStatusVO statusReceived;
	private SoStatusVO statusActive;
	private SoStatusVO statusAccepted;
	private SoStatusVO statusProblem;
	private SearchCriterias searchCriterias;

	@Before
	public void setUp() {
		
		soSearchCriteriaVO= new SoSearchCriteriaVO();			
		searchCriterias =new SearchCriterias();
		
	}
	@Test
	public void getSearchCriteria(){
		Map<Integer , List<SoStatusVO>> statusMap = new HashMap<Integer ,List<SoStatusVO>>();
		List<SearchCriteriaVO> searchCriteriaVOs = new ArrayList<SearchCriteriaVO>();
		List<SoStatusVO> resultVO = new ArrayList<SoStatusVO>();
		List<SoStatusVO> resultVO1 = new ArrayList<SoStatusVO>();
		List<SoStatusVO> resultVO2= new ArrayList<SoStatusVO>();
		List<SoStatusVO> resultVO3 = new ArrayList<SoStatusVO>();
		statusReceived= new SoStatusVO();
		statusActive= new SoStatusVO();
		statusAccepted= new SoStatusVO();
		statusProblem= new SoStatusVO();
		mapper = new MobileGenericMapper();
		searchCriteriavo = new SearchCriteriaVO();
		searchCriteriaVO = new SearchCriteriaVO();
		
		statusReceived.setSoStatus("Received");
		statusReceived.setSoStatusId(110);
		statusReceived.setSoSubStatus("Parts Needed");
		statusReceived.setSoSubStatusId(31);
		
		statusAccepted.setSoStatus("Accepted");
		statusAccepted.setSoStatusId(150);
		statusAccepted.setSoSubStatus("Schedule Needed");
		statusAccepted.setSoSubStatusId(64);
		
		statusActive.setSoStatus("Active");
		statusActive.setSoStatusId(155);
		statusActive.setSoSubStatus("Schedule Needed");
		statusActive.setSoSubStatusId(64);				
		
		statusProblem.setSoStatus("Problem");
		statusProblem.setSoStatusId(170);
		statusProblem.setSoSubStatus("Schedule Needed");
		statusProblem.setSoSubStatusId(64);
		
		searchCriteriavo.setMarketId(127);
		searchCriteriavo.setMarketName("abc");
		searchCriteriavo.setResourceId(23768);
		searchCriteriavo.setRoutedProvider("retc");
				
		searchCriteriaVO.setMarketId(127);
		searchCriteriaVO.setMarketName("abc");
		searchCriteriaVO.setResourceId(23768);
		searchCriteriaVO.setRoutedProvider("retc");
		searchCriteriaVOs.add(searchCriteriaVO);
		searchCriteriaVOs.add(searchCriteriavo);
		
		resultVO.add(statusReceived);
		resultVO1.add(statusAccepted);
		resultVO2.add(statusActive);	
		resultVO3.add(statusProblem);
						
		statusMap.put(110, resultVO);
		statusMap.put(150, resultVO1);
		statusMap.put(155, resultVO2);
		statusMap.put(170, resultVO3);
	
		soSearchCriteriaVO.setSearchCriteriaVOs(searchCriteriaVOs);
		soSearchCriteriaVO.setStatusMap(statusMap);
		
		searchCriterias = mapper.mapGetSearchCriteriaResponse(soSearchCriteriaVO);
	
		Assert.assertNotNull(searchCriterias);
	}
	
}
