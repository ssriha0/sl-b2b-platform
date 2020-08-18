/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-August-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.BeanUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.skillTree.Categories;
import com.newco.marketplace.api.beans.search.skillTree.Category;
import com.newco.marketplace.api.beans.search.skillTree.SkillTreeResponse;
import com.newco.marketplace.api.beans.search.skillTree.SkuDetail;
import com.newco.marketplace.api.beans.search.skillTree.SkuList;
import com.newco.marketplace.api.beans.search.skillTree.Task;
import com.newco.marketplace.api.beans.search.skillTree.Tasks;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;

/**
 * This class would act as a mapper class for SearchProvider By SkillTree
 * 
 * 
 * @author Infosys
 * @version 1.0
 */
public class SearchProviderSkillTreeMapper {
	
	private Logger logger = Logger.getLogger(SearchProviderSkillTreeMapper.class);
	/**
	 * This method is for mapping providers returned from MarketProviderSearch
	 *  to SkillTree objects
	 *  
	 * @param skillTreeResponseList List<SkillTreeResponseVO>
	 * @return SkillTreeResponse
	 */
	public SkillTreeResponse mapSearchProviderBySkillTreeRequest(
			List<SkillTreeResponseVO> skillTreeResponseList) {
		logger.info("Entering mapSearchProviderBySkillTreeRequest method");
		SkillTreeResponse skillTreeResponse = new SkillTreeResponse();
		Categories categories = new Categories();
		List<Category> categoryList = new ArrayList<Category>();
		if((null!=skillTreeResponseList) && (!skillTreeResponseList.isEmpty())){
			for (SkillTreeResponseVO skillTreeResponseVO : skillTreeResponseList) {
				Category category = new Category();
				category.setId(skillTreeResponseVO.getNodeId());
				category.setName(skillTreeResponseVO.getNodeName());
				category.setParentId(skillTreeResponseVO.getParentNodeId());
				category.setParentName(skillTreeResponseVO.getParentNodeName());
				categoryList.add(category);
			}
		}
		
		if (categoryList.size() > 0) {
			categories.setCategoryList(categoryList);
			skillTreeResponse.setCategories(categories);
		}
		Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		if(null==skillTreeResponseList){
			result.setMessage(PublicAPIConstant.NO_MATCH);
		}else if(skillTreeResponseList.isEmpty()){
			result.setMessage(PublicAPIConstant.NO_MATCH);
		}else if(skillTreeResponseList.size()==1){
			result.setMessage(PublicAPIConstant.MATCH_FOUND);
		}else{
			result.setMessage(PublicAPIConstant.MULTIPLE_MATCH);
		}
		result.setCode(PublicAPIConstant.ONE);
		errorResult.setCode(PublicAPIConstant.ZERO);
		errorResult.setMessage("");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		skillTreeResponse.setResults(results);

		skillTreeResponse
				.setSchemaLocation(PublicAPIConstant.PROVIDER_SKILLTREE_RESPONSE_NAMESPACE);
		skillTreeResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		skillTreeResponse
				.setNamespace(PublicAPIConstant.PROVIDER_SKILLTREE_RESPONSE_NAMESPACE);
		logger.info("Exiting mapSearchProviderBySkillTreeRequest method");
		return skillTreeResponse;
	}
	
	
	public List<Category> mapLevelThreeAndTwoCategorySkus(List<SkuDetailsVO> skuDetails,
			List<Category> categoryList) {

		//construct a map from the resultset
		Map<Integer, List<SkuDetail>> levelThreeandTwoCategorySkuMap = new HashMap<Integer, List<SkuDetail>>();
		
		for(SkuDetailsVO skuDetailVO:skuDetails){
			
			SkuDetail skuDetail = new SkuDetail();
			org.springframework.beans.BeanUtils.copyProperties(skuDetailVO, skuDetail);			
			Task task = new Task();
			task.setTaskName(skuDetailVO.getTaskName());
			task.setTaskComment(skuDetailVO.getTaskComments());
			task.setServiceType(skuDetailVO.getSkill());			
			List<Task> taskList = new ArrayList<Task>();
			taskList.add(task);			
			Tasks tasks = new Tasks();
			tasks.setTaskList(taskList);			
			skuDetail.setTasks(tasks);
			logger.info("skuDetails nodeId :"+skuDetailVO.getNodeId());
			logger.info("skuDetails sku :"+skuDetail.getSku());

			if(null!=levelThreeandTwoCategorySkuMap.get(skuDetailVO.getNodeId())){
				
			List<SkuDetail> skuDetailList=levelThreeandTwoCategorySkuMap.get(skuDetailVO.getNodeId());	
			skuDetailList.add(skuDetail);
			levelThreeandTwoCategorySkuMap.put(skuDetailVO.getNodeId(), skuDetailList);
			
			}else{
				
			List<SkuDetail> skuDetailList=new ArrayList<SkuDetail>();	
			skuDetailList.add(skuDetail);
			levelThreeandTwoCategorySkuMap.put(skuDetailVO.getNodeId(), skuDetailList);
			}
			
		}
		
		for(Category category:categoryList){
			if(new Integer(3).equals(category.getLevel())){
				Integer subCatId = category.getSubCategoryId();
				if(levelThreeandTwoCategorySkuMap.containsKey(subCatId)){
					if(null == category.getSkuList()){
						List<SkuDetail> skuDetailList = levelThreeandTwoCategorySkuMap.get(subCatId);
						SkuList skuList = new SkuList();
						skuList.setSkuList(skuDetailList);
						category.setSkuList(skuList);
						// appear only once
						levelThreeandTwoCategorySkuMap.remove(subCatId);
					}

				}
			}else if( new Integer(2).equals(category.getLevel()) ){
				Integer catId = category.getCategoryId();
				if(levelThreeandTwoCategorySkuMap.containsKey(catId)){
					if(null == category.getSkuList()){
						List<SkuDetail> skuDetailList = levelThreeandTwoCategorySkuMap.get(catId);
						SkuList skuList = new SkuList();
						skuList.setSkuList(skuDetailList);
						category.setSkuList(skuList);
						// appear only once
						levelThreeandTwoCategorySkuMap.remove(catId);
					}

				}
			}
		}  
		
		return categoryList;
	}

}
