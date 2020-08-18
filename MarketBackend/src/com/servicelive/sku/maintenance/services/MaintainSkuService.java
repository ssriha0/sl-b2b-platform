package com.servicelive.sku.maintenance.services;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.sku.maintenance.*;

public interface MaintainSkuService {
	public List<BuyerSkuCategory>  fetchBuyerSkuCategories(Integer buyerId, Integer skuCategoryId);
	public List<BuyerSkuCategory>  fetchBuyerSkuCategories(Integer buyerId);	
	public List<BuyerSKUEntity> fetchBuyerSkuNameByCategory(Integer categoryId, Integer currentSkuId, Integer buyerId);
	public List<Map<Integer, String>> fetchMainCategory();
	public List<Map<Integer, String>> fetchTemplates(int nodeId,int buyerId);
	public List<SkillTree> fetchCategory(int nodeId);
	public List<LuServiceTypeTemplate> fetchSkills(int nodeId);
	public BuyerSkuTask fetchSkillTreeBySkuId(Integer skuId);
	public SkillTree fetchSkillTreeBySkuIdByNodeId(Integer nodeId);
	public BuyerSKUEntity skuDetailsBySkuIdAndCategoryId(Integer skuId,Integer categoryId);
	void updateSkuCategory(String updatedSkuName, String descr, int skuCatId, SecurityContext soContxt);
	public BuyerSoTemplate findTemplate(int templateId);
	public LuServiceTypeTemplate findServiceType(int id);
	public SkillTree findSkillTree(int nodeId);
	public void addSkuCategory(Object object);
	public Buyer findBuyer(int buyerId);
	public BuyerSkuCategory findBuyerSkuCategory(int catgoryId);
	public BuyerSKUEntity findBuyerSku();
	public void deleteSku(List<Integer> skuIds, String categoryName,String categoryId);
	public List<Integer> checkForDeleteSku(List<String> skuIds, String skuCategoryId);
	public int checkForDeleteSkuCategory(String skuCategoryId);		
	public List<SkuCategoryHistoy> fetchSkuCategoryHistory(Integer categoryId);
	public List<SkuHistory> fetchSkuHistory(Integer skuId);
	public boolean validateBuyerSKUCategory(Integer buyerId, Integer skuCategoryId, String updatedSkuName);
	
	/***Description:Checking non funded feature set is enabled for the buyer
	 * @param buyerId
	 * @return
	 */
	public boolean isNonFundedBuyer(Integer buyerId);
}
