package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderSearchBO;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class SearchBOPBtest implements OrderConstants {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("entered in main");
		SearchBOPBtest searchBOPBtest = new SearchBOPBtest();
		ProcessResponse processResp = new ProcessResponse();
		processResp = searchBOPBtest.TestPB();

	}

	public ProcessResponse TestPB() {
		ServiceOrderSearchBO serviceoderSerachBO = (ServiceOrderSearchBO) MPSpringLoaderPlugIn.getCtx()
				.getBean("soSearchBO");

		CriteriaMap newMap = new CriteriaMap();
		ProcessResponse processResp = new ProcessResponse();
		PagingCriteria pc = new PagingCriteria();
		SortCriteria sc = new SortCriteria();
		FilterCriteria fc = new FilterCriteria(null, null,null,null, null);
		fc.setPbfilterId(1);
		OrderCriteria oc = new OrderCriteria();
		oc.setRoleId(3);
		SearchCriteria searchCriteria = new SearchCriteria();
		String type = "7";
		searchCriteria.setSearchType(type);
		searchCriteria.setSearchValue("WORKFLOWFILTER_SEARCH");
		newMap.put(FILTER_CRITERIA_KEY, fc);
		newMap.put(SORT_CRITERIA_KEY, sc);
		newMap.put(PAGING_CRITERIA_KEY, pc);
		newMap.put(ORDER_CRITERIA_KEY, oc);
		newMap.put(SEARCH_CRITERIA_KEY, searchCriteria);

		try {
			processResp = serviceoderSerachBO
					.retrieveSOSearchResultsIds(newMap);
		} catch (DataServiceException e) {
			e.getStackTrace();
		}
		return processResp;
	}

}
