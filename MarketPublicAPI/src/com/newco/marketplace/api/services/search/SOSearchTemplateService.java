package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderResults;
import com.newco.marketplace.api.beans.search.soSearchTemplate.SOSearchTemplateResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.server.v1_1.SearchRequestProcessor;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.search.SearchTemplateMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
/**
 * This class would act as a service class for getting providers by zip Code Search.
 * 
 * API     : /providers/zip
 * APIType : Get
 * Request Processor  : {@link SearchRequestProcessor#getProvidersByZipCode}
 * Spring Bean Name   : ZipCodeService
 * Spring Context XML : apiApplicationContext.xml
 * 
 * @author Infosys & Shekhar Nirkhe(chandra@servicelive.com)
 * @since 10/10/2009
 * @version 1.0
 * 
 */

@Namespace("http://www.servicelive.com/namespaces/byzip")
@APIResponseClass(ProviderResults.class)
public class SOSearchTemplateService extends BaseService {
	
	private IServiceOrderSearchBO serviceOrderSearchBO;
	private SearchTemplateMapper searchTemplateMapper;
	private Logger logger = Logger.getLogger(SOSearchTemplateService.class);
	
	public SOSearchTemplateService() {
		super(null,
				PublicAPIConstant.SOSearchTemplate.SEARCHTEMPLATERESPONSE_XSD,
				PublicAPIConstant.SOSearchTemplate.SEARCHTEMPLATERESPONSE_NAMESPACE,
				PublicAPIConstant.SOSearchTemplate.RESOURCES_SCHEMAS,
				PublicAPIConstant.SOSearchTemplate.RESOURCES_SCHEMAS,
				null, SOSearchTemplateResponse.class);
	}
	
	/**
	 * This method is for retrieving providers based on zipCode.
	 * @see BaseService
	 * 
	 * @param zipMap  HashMap<String,String>
	 * @return String
	 */
	public IAPIResponse execute(APIRequestVO searchRequestVO) {
		logger.info("Entering dispatchProviderSearchByZipCodeRequest method");
		SOSearchTemplateResponse soSearchTemplateResponse=new SOSearchTemplateResponse();
		int buyerId = searchRequestVO.getBuyerIdInteger();
		SearchFilterVO searchFilterVO=new SearchFilterVO();
		List<SearchFilterVO> searchFilterList=new ArrayList<SearchFilterVO>();
		searchFilterVO.setEntityId(buyerId);
		SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
		searchFilterVO.setRoleId(securityContext.getRoleId());
		try{
			searchFilterList=serviceOrderSearchBO.getSearchFilters(searchFilterVO);
			soSearchTemplateResponse=searchTemplateMapper.mapSearchTemplates(searchFilterList);
		}catch (Exception exception) {
			logger.error("Exception occurred while accessing security " +
			"context using resourceId");
}
		
		return soSearchTemplateResponse;
	}

	public void setServiceOrderSearchBO(ServiceOrderSearchBO serviceOrderSearchBO) {
		this.serviceOrderSearchBO = serviceOrderSearchBO;
	}

	public SearchTemplateMapper getSearchTemplateMapper() {
		return searchTemplateMapper;
	}

	public void setSearchTemplateMapper(SearchTemplateMapper searchTemplateMapper) {
		this.searchTemplateMapper = searchTemplateMapper;
	}

	public IServiceOrderSearchBO getServiceOrderSearchBO() {
		return serviceOrderSearchBO;
	}

	public void setServiceOrderSearchBO(IServiceOrderSearchBO serviceOrderSearchBO) {
		this.serviceOrderSearchBO = serviceOrderSearchBO;
	}
	

	
	
	

}
