package com.newco.marketplace.webservices.dispatcher.providerSearch;

import java.util.ArrayList;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.providerSearch.ProviderSearchBO;
import com.newco.marketplace.dto.request.providerSearch.ProviderSearchRequest;
import com.newco.marketplace.dto.response.providerSearch.ProviderSearchResponse;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.webservices.sei.providerSearch.ProviderSearchSEI;



/**
 * @author zizrale
 *
 */
public class ProviderSearchDispatcher extends ABaseRequestDispatcher implements ProviderSearchSEI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8579148323459492456L;
	private ProviderSearchBO providerSearchBO;
	
	public ProviderSearchResponse getProviderList(ProviderSearchRequest request){
		// convert request into ProviderSearchCriteriaVO
		
		// call business obj
		
		// convert response into ProviderSearchResponse
		System.out.println("in dispatcher");
		ProviderSearchCriteriaVO searchCriteria = new ProviderSearchCriteriaVO();
		providerSearchBO = (ProviderSearchBO)MPSpringLoaderPlugIn.getCtx().getBean("providerSearchBO");
		
		searchCriteria.setBuyerZipCode(request.getBuyerZipCode());
		searchCriteria.setProviderRating(request.getProviderRating());
		searchCriteria.setSkillNodeIds(request.getSkillNodeIds());
	
		ArrayList<ProviderResultVO> providerResultVOs = providerSearchBO.getProviderList(searchCriteria);
		
		ProviderSearchResponse providerSearchResponse = new ProviderSearchResponse();
		providerSearchResponse.setProviderResult(providerResultVOs);
		System.out.println("providerSearchResponse="+providerSearchResponse);
		return providerSearchResponse;
		
	}

	public ProviderSearchBO getProviderSearchBO() {
		return providerSearchBO;
	}

	public void setProviderSearchBO(ProviderSearchBO providerSearchBO) {
		this.providerSearchBO = providerSearchBO;
	}
	
}
