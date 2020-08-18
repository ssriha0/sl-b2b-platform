/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-August-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.skillTree.Categories;
import com.newco.marketplace.api.beans.search.skillTree.Category;
import com.newco.marketplace.api.beans.search.skillTree.SkillTreeResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.search.SearchProviderSkillTreeMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;
import org.apache.commons.lang.StringUtils;

/**
 * This class would act as a service class for getting providers by Skill Tree
 *  Search.
 * 
 * 
 * @author Infosys
 * @version 1.0
 */
@Namespace("http://www.servicelive.com/namespaces/skillTree")
@APIResponseClass(SkillTreeResponse.class)
public class SearchProviderByskillTreeService extends BaseService{
	private ISearchProvider providerSearch;
	private SearchProviderSkillTreeMapper skillTreeMapper;
	private XStreamUtility conversionUtility;
	private Logger logger = Logger.getLogger(SearchProviderByskillTreeService.class);
	
	
	public SearchProviderByskillTreeService(){
		super();		
		super.addMoreClass(Categories.class);
		super.addMoreClass(Category.class);
	}
	
	
	/**
	 * This method is for retrieving providers based on skillTree.
	 * 
	 * @param skillTreeRequest String
	 * @return String
	 */
	public IAPIResponse execute(APIRequestVO apiVO)  {
		logger.info("Entering Execute SearchBySkillTree method");
		SkillRequestVO skillTreeRequestVO = new SkillRequestVO();
		boolean hasRecords = false;
		Categories categories = null;
		List<Category> categoryList = new ArrayList<Category>();
		String originalTerm = null;
		String collation = null;
		
		SkillTreeResponse skillTreeResponse = new SkillTreeResponse();
		Results results = new Results();
		
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		String keyword = requestMap.get(PublicAPIConstant.KEYWORD);
		String spellCheck = requestMap.get(PublicAPIConstant.SPELLCHECK);
		
		//SL-21467
		String buyerId = requestMap.get(PublicAPIConstant.BUYERID);
		List<Integer> levelThreeandTwoCategories = new ArrayList<Integer>();
		List<String> mainCategoryIdList = new ArrayList<String>();

		List<Integer> nodeIds = new ArrayList<Integer>();

		skillTreeRequestVO.setSkillTree(keyword);
		
		logger.info("keyword::"+keyword);
	   
		if (spellCheck != null && spellCheck.trim().equalsIgnoreCase("false")) {
			skillTreeRequestVO.setSpellCheck(false);
		}
		List<SkillTreeResponseVO> providerResponseList = providerSearch
												.getSkillTree(skillTreeRequestVO);
		
		logger.info("List from solr::"+providerResponseList.size());
		for(SkillTreeResponseVO bList1 : providerResponseList) {
			logger.info("solr result::"+bList1.toString());
			
		}
		
		//If suggestions exists for searched skill, they will exist on the first element of the list.
		boolean firstObject = true;
		for(SkillTreeResponseVO bList : providerResponseList) {
			if(firstObject) {
				originalTerm = bList.getOriginalTerm();
				if(bList.getCollate() != null) {
					collation = bList.getCollate();
				}
				firstObject = false;
			} else {
				//SL-21467
				int level = bList.getLevel();
				
				hasRecords = true;
				Category category = new Category();
				//category.setId(bList.getNodeId());
				category.setName(bList.getNodeName());
				//category.setParentId(bList.getParentNodeId());
				//category.setParentName(bList.getParentNodeName());
				category.setLevel(level);
				category.setScore(bList.getScore());
				categoryList.add(category);
				
				//SL-21467
				if(null != buyerId && PublicAPIConstant.THREE == level){
					levelThreeandTwoCategories.add(bList.getNodeId());
				}
				if(null != buyerId && PublicAPIConstant.TWO == level){
					levelThreeandTwoCategories.add(bList.getNodeId());
				}
				
				
				if(PublicAPIConstant.THREE == level){
					category.setSubCategoryId(bList.getNodeId());
					category.setSubCategoryName(bList.getNodeName());
					category.setCategoryId(bList.getParentNodeId());
					category.setCategoryName(bList.getParentNodeName());
					nodeIds.add(Integer.valueOf(bList.getParentNodeId()));
				}
				
				if(PublicAPIConstant.TWO == level){
					category.setCategoryId(bList.getNodeId());
					category.setMainCategoryId(bList.getParentNodeId());
					category.setMainCategoryName(bList.getParentNodeName());
					nodeIds.add(Integer.valueOf(bList.getNodeId()));
				}
				if(PublicAPIConstant.INTEGER_ONE == level){
					category.setMainCategoryId(bList.getNodeId());
					category.setMainCategoryName(bList.getNodeName());
					nodeIds.add(Integer.valueOf(bList.getNodeId()));
					//mainCategoryIdList.add(String.valueOf(bList.getNodeId()));
				}
				
			} 
		}
	
		
		
		 String lastWord ="";
		 if(StringUtils.isNotBlank(keyword)){			 
		 lastWord=keyword.substring(keyword.lastIndexOf(" ")+1); 
         logger.info("lastWord"+lastWord);
		 }
         
        
		List<String> nodeIdKeys = new ArrayList<String>();
		Map<Long,Long> nodeIdMap= null;
		Map<Long,String> skillTypeMap=null;
		Map<Long,String> categoryNameMap=null;
		
		try{
		nodeIdMap=providerSearch.getParentNodeIds(nodeIds);
		logger.info("nodeIdMap"+nodeIdMap);	
		skillTypeMap=providerSearch.getSkillTypList();
		logger.info("skillTypeMap"+skillTypeMap);
		//nodeIdKeys = new ArrayList<String>(nodeIdMap.keySet());
		if (null != nodeIdMap) {
				for (Map.Entry<Long, Long> entry : nodeIdMap.entrySet()) {
					if(null!=entry.getValue())
					nodeIdKeys.add(entry.getValue().toString());
				}
		}
		
		mainCategoryIdList.addAll(nodeIdKeys);
		categoryNameMap=providerSearch.getNodeNames(mainCategoryIdList);
		logger.info("categoryNameMap"+categoryNameMap);
		}catch (BusinessServiceException e) {
			logger.info("Exception in getting maincategory Names or skill"+e);
		}
		
		//SL-21467
		if(levelThreeandTwoCategories.size() > 0){
			try {
				List<SkuDetailsVO> skuDetails = providerSearch.getSkusForCategoryIds(buyerId, levelThreeandTwoCategories,keyword);
				skillTreeMapper.mapLevelThreeAndTwoCategorySkus(skuDetails, categoryList);
				
			} catch (BusinessServiceException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}

		}
		if(null!=categoryList && categoryList.size()>1){
		 for(Category category:categoryList){
			if(new Integer(3).equals(category.getLevel())){
				
				
				//category.setSubCategoryId(category.getId());
				//category.setSubCategoryName(category.getName());
				
				//category.setCategoryId(category.getParentId());
				//category.setCategoryName(category.getParentName());
				 
				if(null!=category.getCategoryId() && null!=nodeIdMap && null!=nodeIdMap.get(Long.valueOf(category.getCategoryId().longValue()))){
				category.setMainCategoryId(nodeIdMap.get(Long.valueOf(category.getCategoryId().longValue())).intValue()); 
				} 
				
				if(null!=category.getMainCategoryId() && null!=skillTypeMap && null!=skillTypeMap.get(Long.valueOf(category.getMainCategoryId().longValue()))){
					category.setType(skillTypeMap.get(Long.valueOf(category.getMainCategoryId().longValue())));
				}
				if(null!=category.getMainCategoryId() && null!=categoryNameMap && null!=categoryNameMap.get(Long.valueOf(category.getMainCategoryId().longValue()))){
					category.setMainCategoryName(categoryNameMap.get(Long.valueOf(category.getMainCategoryId().longValue())));
				}
				if(null!=category.getType() && StringUtils.isNotBlank(lastWord) && category.getType().contains(lastWord)){
					 category.setType(lastWord);
				}
				
				
			}			
			if(new Integer(2).equals(category.getLevel())){
				//category.setCategoryId(category.getId());
				category.setCategoryName(category.getName());
				//category.setMainCategoryId(category.getParentId());
				
				if(null!=skillTypeMap && null!=category.getMainCategoryId() && null!=skillTypeMap.get(Long.valueOf(category.getMainCategoryId().longValue()))){
					category.setType(skillTypeMap.get(Long.valueOf(category.getMainCategoryId().longValue())));
				}			
				/*if(null!=categoryNameMap && null!=categoryNameMap && null!=categoryNameMap.get(Long.valueOf(category.getMainCategoryId().longValue()))){
					category.setMainCategoryName(categoryNameMap.get(Long.valueOf(category.getMainCategoryId().longValue())));
				}*/
				if(null!=category.getType() && StringUtils.isNotBlank(lastWord) && category.getType().contains(lastWord)){
					 category.setType(lastWord);
				}
			
			}				
			if(new Integer(1).equals(category.getLevel())){
				
				if(null!=skillTypeMap && null!=category.getMainCategoryId() && null!=skillTypeMap.get(Long.valueOf(category.getMainCategoryId().longValue()))){
					category.setType(skillTypeMap.get(Long.valueOf(category.getMainCategoryId().longValue())));
				}	

			if(null!=category.getType() && StringUtils.isNotBlank(lastWord) && category.getType().contains(lastWord)){
				 category.setType(lastWord);
			}
			
		}
		}
		}
	
		if (categoryList.size() > 0) {
			categories = new Categories();
			categories.setCategoryList(categoryList);
		}
		
		//Create response object based on the data retrieved.
		skillTreeResponse = new SkillTreeResponse(results,categories);
		skillTreeResponse.setOriginalTerm(originalTerm);
		if(collation != null) {
			skillTreeResponse.setCollation(collation);
		}
		
		//Send the number of records found with Result
		if (hasRecords) {
			results = Results.getSuccess("Total Skills "+ categoryList.size());
			skillTreeResponse.setResults(results);
		} else {
			results = Results.getSuccess("No results found.");
			skillTreeResponse.setResults(results);
		}

		logger.info("Exiting Execute SearchBySkillTree method");
		return skillTreeResponse;
	}

	public ISearchProvider getProviderSearch() {
		return providerSearch;
	}

	public void setProviderSearch(ISearchProvider providerSearch) {
		this.providerSearch = providerSearch;
	}

	public SearchProviderSkillTreeMapper getSkillTreeMapper() {
		return skillTreeMapper;
	}

	public void setSkillTreeMapper(SearchProviderSkillTreeMapper skillTreeMapper) {
		this.skillTreeMapper = skillTreeMapper;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}


}
