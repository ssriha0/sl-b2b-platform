package com.servicelive.sku.maintenance.dao;

import java.util.List;
import java.util.Map;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.sku.maintenance.*;


public interface MaintainSkuDao {

	
	public List<BuyerSkuCategory> fetchBuyerSkuCategories(int buyerId, int skuCategoryId);		
	public List<BuyerSkuCategory> fetchBuyerSkuCategories(int buyerId);			
	public List<Map<Integer, String>> fetchMainCategory();
	public List<Map<Integer, String>> fetchTemplates(int nodeId, int buyerId);
	public List<SkillTree> fetchCategory(int nodeId);
	public List<BuyerSKUEntity> fetchBuyerSkuNameByCategory(Integer categoryId, Integer currentSkuId, Integer buyerId);
	public List<LuServiceTypeTemplate> fetchSkills(int nodeId);
	public BuyerSkuTask fetchSkillTreeBySkuId(Integer skuId);
	public SkillTree fetchSkillTreeBySkuIdByNodeId(Integer nodeId);
	public BuyerSKUEntity skuDetailsBySkuIdAndCategoryId(Integer skuId,Integer categoryId);
	public BuyerSkuCategory saveBuyerSkuCategory(BuyerSkuCategory buyerSkuCategory);	
	public BuyerSkuCategory findBuyerSkuCategoryById(int skuCatId);		
	public BuyerSoTemplate findTemplate(int templateId);
	public LuServiceTypeTemplate findServiceType(int id);
	public SkillTree findSkillTree(int nodeId);
	public void addSkuCategory(Object object);
	public Buyer findBuyer(int buyerId);
	public BuyerSkuCategory findBuyerSkuCategory(int catgoryId);
	public BuyerSKUEntity findBuyerSku();
	public void deleteSku(List<Integer> skuList,String categoryName,String categoryId);
	public List<Integer> checkForDeleteSku(List<String> skuIds, String skuCategoryId);
	public int checkForDeleteSkuCategory(String skuCategoryId);	
	public List<SkuCategoryHistoy> fetchSkuCategoryHistory(Integer categoryId);
	public List<SkuHistory> fetchSkuHistory(Integer skuId);
	public BuyerSkuCategory validateBuyerSKUCategory(Integer buyerId, Integer skuCategoryId, String updatedSkuName);
	public boolean isNonFundedBuyer(Integer buyerId);
	
}
