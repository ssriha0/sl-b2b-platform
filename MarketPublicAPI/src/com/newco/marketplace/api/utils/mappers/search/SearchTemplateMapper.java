package com.newco.marketplace.api.utils.mappers.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.soSearchTemplate.SOSearchTemplateResponse;
import com.newco.marketplace.api.beans.search.soSearchTemplate.TemplateNames;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;

public class SearchTemplateMapper {
	private Logger logger = Logger.getLogger(SearchTemplateMapper.class);
	/**
	 * This method is for mapping the Attachments Section of Create Request for
	 * Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param attachments
	 *            Attachments
	 * @return void
	 */
	public SOSearchTemplateResponse mapSearchTemplates(List<SearchFilterVO> searchFilterList) {

		logger.info("Mapping: Document --->Starts");
		List<String> templateNameList = new ArrayList<String>();
		TemplateNames templateNames=new TemplateNames();		
		SOSearchTemplateResponse soSearchTemplateResponse=new SOSearchTemplateResponse();
		Results results;
		if(null!=searchFilterList&&searchFilterList.size()!=0) {
			for (SearchFilterVO searchFilterVO : searchFilterList) {
				String templateName=new String();
				templateName=searchFilterVO.getFilterName();
				templateNameList.add(templateName);
					}
			templateNames.setTemplateName(templateNameList);
			soSearchTemplateResponse.setTemplateNames(templateNames);
			results = Results.getSuccess(templateNameList.size()+ 
					PublicAPIConstant.SEARCH_SUCCESSFUL);
		
		}else
		{
			results= Results.getError(ResultsCode.
					SEARCH_TEMPLATE_NO_RECORDS.getMessage(),
					ResultsCode.
					SEARCH_TEMPLATE_NO_RECORDS.getCode());
			
		}
		soSearchTemplateResponse.setResults(results);
		return soSearchTemplateResponse;
	}
}
