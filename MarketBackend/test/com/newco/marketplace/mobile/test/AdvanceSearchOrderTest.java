/**
 * 
 */
package com.newco.marketplace.mobile.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.mobile.MobileGenericBOImpl;
import com.newco.marketplace.persistence.daoImpl.mobile.MobileGenericDaoImpl;
import com.newco.marketplace.persistence.iDao.mobile.IMobileGenericDao;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultsVO;

/**
 * @author Infosys
 *
 */
public class AdvanceSearchOrderTest {
	private MobileGenericBOImpl  mobileBO;
	private IMobileGenericDao genericDao;
	
	@Before
	public void setUp() {
		mobileBO = new MobileGenericBOImpl();
		genericDao =mock(MobileGenericDaoImpl.class);
		mobileBO.setMobileGenericDao(genericDao);
		
	}

	@Test
	public void mapCriteriaAndValidatePageNumErrorCase(){
		try {
			SOAdvanceSearchCriteriaVO searchCriteriaVO =formSOSearchRequestVO();
			when(genericDao.getTotalCountForServiceOrderAdvanceSearchResults(searchCriteriaVO)).thenReturn(1);
			List<SOSearchResultVO> searchResultVOs = new ArrayList<SOSearchResultVO>();
			SOSearchResultVO resultVO = new SOSearchResultVO();
			resultVO.setSoId("555-6444-4185-52");
			searchResultVOs.add(resultVO);
			when(genericDao.getServiceOrderAdvanceSearchResults(searchCriteriaVO)).thenReturn(searchResultVOs);
			
			SOSearchResultsVO soSearchResultsVO = mobileBO.getServiceOrderAdvanceSearchResults(searchCriteriaVO);
			Assert.assertNotNull(soSearchResultsVO);
			Assert.assertNotNull(soSearchResultsVO.isPageNumberExceededInd());
			Assert.assertTrue(soSearchResultsVO.isPageNumberExceededInd());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private SOAdvanceSearchCriteriaVO formSOSearchRequestVO() {
		SOAdvanceSearchCriteriaVO searchCriteriaVO = new SOAdvanceSearchCriteriaVO();
		searchCriteriaVO.setAcceptedResourceId(10254);
		searchCriteriaVO.setFirmId(10202);
		searchCriteriaVO.setPageLimit(250);
		searchCriteriaVO.setPageNo(2);
		searchCriteriaVO.setPageSize(250);
		searchCriteriaVO.setRoleId(3);
		searchCriteriaVO.setFlaggedInd(false);
		return searchCriteriaVO;
	}

	@Test
	public void mapCriteriaAndValidatePageNoErrorCase(){
		SOAdvanceSearchCriteriaVO searchCriteriaVO =formSOSearchRequestVO();
		try {
			when(genericDao.getTotalCountForServiceOrderAdvanceSearchResults(searchCriteriaVO)).thenReturn(251);
			List<SOSearchResultVO> searchResultVOs = new ArrayList<SOSearchResultVO>();
			SOSearchResultVO resultVO = new SOSearchResultVO();
			resultVO.setSoId("555-6444-4185-52");
			searchResultVOs.add(resultVO);
			when(genericDao.getServiceOrderAdvanceSearchResults(searchCriteriaVO)).thenReturn(searchResultVOs);
			
			SOSearchResultsVO soSearchResultsVO = mobileBO.getServiceOrderAdvanceSearchResults(searchCriteriaVO);
			Assert.assertNotNull(soSearchResultsVO);
			Assert.assertNotNull(soSearchResultsVO.isPageNumberExceededInd());
			Assert.assertFalse(soSearchResultsVO.isPageNumberExceededInd());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}
