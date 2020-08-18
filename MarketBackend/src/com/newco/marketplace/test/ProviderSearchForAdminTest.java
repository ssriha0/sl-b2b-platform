package com.newco.marketplace.test;

import java.util.List;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class ProviderSearchForAdminTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IProviderSearchBO search = (IProviderSearchBO)MPSpringLoaderPlugIn.getCtx().getBean("providerSearchBO");
		
		ProviderResultForAdminVO searchCriteria = new ProviderResultForAdminVO();
		searchCriteria.setSoId("182-5813-9023-10");
		List<ProviderResultForAdminVO> providers = null;
		CriteriaMap criteriaMap = null;
		
		try{
			providers = search.getProviderListForAdmin(searchCriteria, criteriaMap);
		}catch (BusinessServiceException bse){
			System.out.println ("Exception occured.  Bad.  No providers gotten cause of a database error.");
		}
		if (providers.size() > 0){
			ProviderResultForAdminVO provider = providers.get(0);
			System.out.println("Size of the list is " + providers.size());
			System.out.println("Vendor Status : " + provider.getVendorStatus());
			System.out.println("City: " + provider.getCity());
		} else {
			System.out.println("No records matched your search criteria.");
		}
	}

}
